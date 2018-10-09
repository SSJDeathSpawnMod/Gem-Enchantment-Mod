package com.deathspawn.advanced.tileentities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.deathspawn.advanced.capabilityhandlers.DynamicEnergyStorage;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.recipes.GemEnchanterRecipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGemEnchanter extends TileEntityBase implements ITickable, ICapabilityProvider {

	private ItemStackHandler inventory = new ItemStackHandler(4);
	private DynamicEnergyStorage energy = new DynamicEnergyStorage(100000);

	private boolean isEnchanting;

	private int enchantingTime;

	private int currentItemEnchantTime;
	private int enchantTime;
	private int shouldEnchantTime;
	private String enchanterCustomName;
	private int counter = 0;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		energy.writeToNBT(compound);
		compound.setInteger("enchantTime", this.enchantTime);
		compound.setInteger("currentItemEnchantTime", this.currentItemEnchantTime);
		compound.setInteger("enchantingTime", this.enchantingTime);
		compound.setInteger("shouldEnchantTime", this.shouldEnchantTime);
		compound.setBoolean("isEnchanting", this.isEnchanting);
		if (this.hasCustomName()) {
			compound.setString("enchanterCustomName", this.enchanterCustomName);
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		energy.readFromNBT(compound);
		this.enchantTime = compound.getInteger("enchantTime");
		this.currentItemEnchantTime = compound.getInteger("currentItemEnchantTime");
		this.enchantingTime = compound.getInteger("enchantingTime");
		this.shouldEnchantTime = compound.getInteger("shouldEnchantTime");
		this.isEnchanting = compound.getBoolean("isEnchanting");
		if (compound.hasKey("CustomName", 8)) {
			this.enchanterCustomName = compound.getString("CustomName");
		}
	}
	
	public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.shouldEnchantTime;
            case 1:
                return this.currentItemEnchantTime;
            case 2:
                return this.enchantTime;
            case 3:
                return this.enchantingTime;
            default:
                return 0;
        }
    }

	public boolean isEmpty() {
		for (int i = 0; i > this.inventory.getSlots(); i++) {
			if (!this.inventory.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public ItemStack decrStackSize(int index, int count) {
		return index >= 0 && index < this.inventory.getSlots()
				&& !((ItemStack) this.inventory.getStackInSlot(index)).isEmpty() && count > 0
						? ((ItemStack) this.inventory.getStackInSlot(index)).splitStack(count)
						: ItemStack.EMPTY;
	}

	public ItemStack removeStackFromSlot(int index) {
		if (index >= 0 && index < this.inventory.getSlots()) {
			this.inventory.setStackInSlot(index, ItemStack.EMPTY);
			return ItemStack.EMPTY;
		}
		return ItemStack.EMPTY;
	}

	public String getName() {
		return this.hasCustomName() ? this.enchanterCustomName : "container.enchanter";
	}

	public boolean hasCustomName() {
		return this.enchanterCustomName != null && !this.enchanterCustomName.isEmpty();
	}

	public void setCustomInventoryName(String name) {
		this.enchanterCustomName = name;
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.inventory.getStackInSlot(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.inventory.setStackInSlot(index, stack);

		if (stack.getCount() > 64) {
			stack.setCount(64);
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	public ItemStack getStackInSlot(int index) {
		return this.inventory.getStackInSlot(index);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.inventory;
		else if (capability == CapabilityEnergy.ENERGY)
			return (T) this.energy;
		else
			return super.getCapability(capability, facing);
	}

	public boolean isEnchanting() {
		return isEnchanting;
	}

	@Override
	public void update() {
			List<EntityItem> items = new ArrayList<EntityItem>();
			items.addAll(world.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(pos.add(3, 3, 3), pos.add(-3, -3, -3))));
			for (EntityItem item : items) {
				if (item.getItem().getItem() == Items.STICK) {
					Utils.getLogger().info("Added Power!");
					this.energy.receiveEnergy(1000, false);
				}
			}
			
			if(this.world.isBlockLoaded(getPos())) { 
				this.world.notifyBlockUpdate(getPos(), this.world.getBlockState(getPos()), this.world.getBlockState(getPos()), 2);
			}

			if (this.canSmelt()) {
				this.currentItemEnchantTime = getEnchantTime(this.inventory.getStackInSlot(0),
						this.inventory.getStackInSlot(1));
				this.shouldEnchantTime = this.currentItemEnchantTime;
				this.isEnchanting = true;
				this.smeltItem();
			} else {
				this.enchantTime = 0;
				this.currentItemEnchantTime = 0;
				this.enchantingTime = 0;
				this.shouldEnchantTime = 0;
				this.isEnchanting = false;
			}
		this.markDirty();
	}

	public int getEnchantTime(ItemStack stack, ItemStack stack2) {
		return 200;
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		ItemStack slot0 = this.inventory.getStackInSlot(0);
		ItemStack slot1 = this.inventory.getStackInSlot(1);
		ItemStack slot2 = this.inventory.getStackInSlot(2);
		boolean canSmelt = false;
		if (!(slot0.isEmpty() && slot1.isEmpty())) {
			ItemStack recipes = GemEnchanterRecipes.instance().getEnchantingResult(slot0, slot1);
			if (recipes != ItemStack.EMPTY) {
				if (((slot2.getItem() == recipes.getItem()
						&& slot2.getCount() < slot2.getItem().getItemStackLimit(slot2)) || slot2.isEmpty())
						&& this.energy.getEnergyStored() > 10) {
					canSmelt = true;
				}
			}
		}
		return canSmelt;
	}

	public void smeltItem() {
		ItemStack slot0 = this.inventory.getStackInSlot(0);
		ItemStack slot1 = this.inventory.getStackInSlot(1);
		ItemStack result = GemEnchanterRecipes.instance().getEnchantingResult(slot0, slot1);
		ItemStack slot2 = this.inventory.getStackInSlot(2);

		if (this.isEnchanting()) {
			if (this.enchantTime >= this.shouldEnchantTime) {
				slot0.shrink(1);
				slot1.shrink(1);
				if (slot2.isEmpty() || slot2.getItem() == Item.getItemFromBlock(Blocks.AIR)) {
					this.inventory.setStackInSlot(2, result.copy());
				} else {
					slot2.grow(result.getCount());
				}
			} else {
				this.enchantTime++;
				this.energy.extractEnergy(10, false);
			}
		}
	}

	public int getEnchantingTime() {
		return this.enchantingTime;
	}

	public void setEnchantingTime(int enchantingTime) {
		this.enchantingTime = enchantingTime;
	}

	public int getCurrentItemEnchantTime() {
		return this.currentItemEnchantTime;
	}

	public void setCurrentItemEnchantTime(int currentItemEnchantTime) {
		this.currentItemEnchantTime = currentItemEnchantTime;
	}

	public int getEnchantTime() {
		return this.enchantTime;
	}

	public void setEnchantTime(int enchantTime) {
		this.enchantTime = enchantTime;
	}

	public int getShouldEnchantTime() {
		return this.shouldEnchantTime;
	}

	public void setShouldEnchantTime(int shouldEnchantTime) {
		this.shouldEnchantTime = shouldEnchantTime;
	}

	public void setEnchanting(boolean isEnchanting) {
		this.isEnchanting = isEnchanting;
	}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 1 || index == 2;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
	    NBTTagCompound nbtTag = new NBTTagCompound();
	    nbtTag.setTag("inventory", inventory.serializeNBT());
		energy.writeToNBT(nbtTag);
		nbtTag.setInteger("enchantTime", this.enchantTime);
		nbtTag.setInteger("currentItemEnchantTime", this.currentItemEnchantTime);
		nbtTag.setInteger("enchantingTime", this.enchantingTime);
		nbtTag.setInteger("shouldEnchantTime", this.shouldEnchantTime);
		nbtTag.setBoolean("isEnchanting", this.isEnchanting);
	    return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
	    NBTTagCompound tag = pkt.getNbtCompound();
	    inventory.deserializeNBT(tag.getCompoundTag("inventory"));
		energy.readFromNBT(tag);
		this.enchantTime = tag.getInteger("enchantTime");
		this.currentItemEnchantTime = tag.getInteger("currentItemEnchantTime");
		this.enchantingTime = tag.getInteger("enchantingTime");
		this.shouldEnchantTime = tag.getInteger("shouldEnchantTime");
		this.isEnchanting = tag.getBoolean("isEnchanting");
	}

}
