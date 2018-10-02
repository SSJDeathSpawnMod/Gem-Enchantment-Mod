package com.deathspawn.advanced.tileentities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.deathspawn.advanced.energy.DynamicEnergyStorage;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.recipes.GemEnchanterRecipes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGemEnchanter extends TileEntityBase implements ITickable, ICapabilityProvider{

	private ItemStackHandler inventory = new ItemStackHandler(4);
	private DynamicEnergyStorage energy = new DynamicEnergyStorage(1000000);

	private boolean isEnchanting;
	
	private int enchantingTime;
	
	private int currentItemEnchantTime;
	private int enchantTime;
	private int shouldEnchantTime = 50;
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
		if (this.hasCustomName())
        {
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
		if (compound.hasKey("CustomName", 8))
        {
            this.enchanterCustomName = compound.getString("CustomName");
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
	
	public void setCustomInventoryName(String name){
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
			this.shouldEnchantTime = 50;
			this.enchantTime = 0;
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
		items.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(3, 3, 3), pos.add(-3, -3, -3))));
		for(EntityItem item: items) {
			Utils.getLogger().info(item.getItem().getItem().toString());
			if(item.getItem().getItem() == Items.STICK) {
				Utils.getLogger().info("Added Power!");
				this.energy.receiveEnergy(1000, false);
			}
		}
		
		Utils.getLogger().info(this.energy.getEnergyStored());
		
		boolean flag = this.isEnchanting();
        boolean flag1 = true;

        if (this.isEnchanting())
        {
            --this.enchantingTime;
        }
        
        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.inventory.getStackInSlot(1);
            if (this.isEnchanting() || !itemstack.isEmpty() && !((ItemStack)this.inventory.getStackInSlot(0)).isEmpty() && !((ItemStack)this.inventory.getStackInSlot(1)).isEmpty())
            {
                if (!this.isEnchanting() && this.canSmelt())
                {
                    this.enchantingTime = 0;
                    this.shouldEnchantTime = 50;
                    this.currentItemEnchantTime = this.enchantingTime;

                    if (this.isEnchanting())
                    {
                        flag1 = true;
                        this.energy.extractEnergy(10, false);
                    }
                }

                if (this.isEnchanting() && this.canSmelt())
                {
                    ++this.enchantTime;
                    Utils.getLogger().info(this.enchantTime + " " + this.shouldEnchantTime);
                    if (this.enchantTime == this.shouldEnchantTime)
                    {
                        this.enchantTime = 0;
                        this.shouldEnchantTime = 50;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.enchantTime = 0;
                }
            }
            else if (!this.isEnchanting() && this.enchantTime > 0)
            {
                this.enchantTime = MathHelper.clamp(this.enchantTime - 2, 0, this.shouldEnchantTime);
            }

            if (flag != this.isEnchanting())
            {
                flag1 = true;
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    public int getEnchantTime(ItemStack stack)
    {
        return 50;
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
    	Utils.getLogger().info(this.energy.getEnergyStored());
        if (((ItemStack)this.inventory.getStackInSlot(0)).isEmpty() || ((ItemStack)this.inventory.getStackInSlot(1)).isEmpty())
        {
            return false;
        }
        else if(this.energy.getEnergyStored() < 10 ) {
        	return false;
        }
        else
        {
            ItemStack itemstack = GemEnchanterRecipes.instance().getEnchantingResult(this.inventory.getStackInSlot(0), this.inventory.getStackInSlot(1));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
            	//Utils.getLogger().info("Reached Here");
                ItemStack itemstack1 = this.inventory.getStackInSlot(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= 64 && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
    
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = this.inventory.getStackInSlot(0);
            ItemStack itemstack1 = this.inventory.getStackInSlot(1);
            ItemStack itemstack2 = GemEnchanterRecipes.instance().getEnchantingResult(itemstack, itemstack1);
            ItemStack itemstack3 = this.inventory.getStackInSlot(2);

            if (itemstack3.isEmpty())
            {
                this.inventory.setStackInSlot(2, itemstack2.copy());
            }
            else if (itemstack3.getItem() == itemstack2.getItem())
            {
                itemstack3.grow(itemstack2.getCount());
            }

            itemstack.shrink(1);
            itemstack1.shrink(1);
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

	public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 1 || index == 2;
    }
		
}
