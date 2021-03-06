package com.deathspawn.advanced.tileentities.machines;

import com.deathspawn.advanced.capabilityhandlers.DynamicFluidTank;
import com.deathspawn.advanced.handlers.EnumHandler.IO;
import com.deathspawn.advanced.init.ModFluids;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.redflux.EnergyGenerator;

import cofh.redstoneflux.api.IEnergyConnection;
import cofh.redstoneflux.api.IEnergyHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyGenerator extends TileEntityMachine implements ITickable, ICapabilityProvider {

	private ItemStackHandler inventory = new ItemStackHandler(3);
	private EnergyGenerator energy = new EnergyGenerator(100000, 100, this);
	private DynamicFluidTank fluid = new DynamicFluidTank(ModFluids.enchantedFluid, 0, 100000);
	private int timeToMake;
	private boolean isGenerating;

	private String generatorCustomName;

	public TileEntityEnergyGenerator() {
		super(IO.OUTPUT);
		this.setFluidAmount(0);
		this.setFluidCapacity(100000);
		this.fluid.setTileEntity(this);
	}

	public boolean isGenerating() {
		return isGenerating;
	}

	public void setGenerating(boolean isGenerating) {
		this.isGenerating = isGenerating;
	}

	@Override
	public void update() {
		super.update();
		boolean update = false;
		if (this.inventory.getStackInSlot(0).isItemEqual(
				FluidUtil.getFilledBucket(new FluidStack(ModFluids.enchantedFluid, Fluid.BUCKET_VOLUME)))) {
			fluid.fill(new FluidStack(ModFluids.enchantedFluid, Fluid.BUCKET_VOLUME), true);
			this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
			Utils.getLogger().info("Filling up");
			update = true;
		}
		if (this.getFluidAmount() >= 1000) {
			this.timeToMake++;
			this.setGenerating(true);
			update = true;
			if (this.timeToMake > 20) {
				this.energy.modifyEnergy(1000);
				this.fluid.drain(new FluidStack(ModFluids.enchantedFluid, 1000), true);
				update = true;
			}
		} else {
			if (this.isGenerating()) {
				update = true;
			}
			this.setGenerating(false);
			this.timeToMake = 0;
		}
		if (update) {
			if (world.isBlockLoaded(getPos())) {
				world.notifyBlockUpdate(getPos(), this.world.getBlockState(getPos()),
						this.world.getBlockState(getPos()), 2);
			}
			this.markDirty();
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.inventory;
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this.fluid;
		}
		return super.getCapability(capability, facing);
	}

	public IEnergyHandler getEnergyHandler(EnumFacing facing) {
		if (facing == null) {
			return this.energy;
		} else if (this.energy.canConnectEnergy(facing)) {
			return this.energy;
		}
		return null;
	}

	public String getName() {
		return this.hasCustomName() ? this.generatorCustomName : "container.generator";
	}

	public boolean hasCustomName() {
		return this.generatorCustomName != null && !this.generatorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String name) {
		this.generatorCustomName = name;
	}

	public int getFluidCapacity() {
		return this.fluid.getCapacity();
	}

	public void setFluidCapacity(int capacity) {
		this.fluid.setCapacity(capacity);
	}

	public int getFluidAmount() {
		return this.fluid.getFluidAmount();
	}

	public void setFluidAmount(int amount) {
		this.fluid.setFluid(new FluidStack(ModFluids.enchantedFluid, amount));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.energy.writeToNBT(compound);
		compound.setTag("inventory", this.inventory.serializeNBT());
		this.fluid.writeToNBT(compound);
		compound.setInteger("Capacity", this.getFluidCapacity());
		compound.setInteger("Amount", this.getFluidAmount());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.energy.readFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		this.fluid.readFromNBT(compound);
		this.setFluidCapacity(compound.getInteger("Capacity"));
		this.setFluidAmount(compound.getInteger("Amount"));
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.energy.writeToNBT(nbt);
		nbt.setTag("inventory", this.inventory.serializeNBT());
		nbt.setInteger("Capacity", this.getFluidCapacity());
		nbt.setInteger("Amount", this.getFluidAmount());
		SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(getPos(), 1, nbt);
		return packet;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		this.energy.readFromNBT(nbt);
		this.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		this.setFluidCapacity(nbt.getInteger("Capacity"));
		this.setFluidAmount(nbt.getInteger("Amount"));
		super.onDataPacket(net, pkt);
	}

	public boolean canConnectEnergy(EnumFacing from) {
		if (from == EnumFacing.NORTH)
			return true;
		else
			Utils.getLogger().info("Only connect from North");
		return false;
	}

}
