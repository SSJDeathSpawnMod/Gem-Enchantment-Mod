package com.deathspawn.advanced.tileentities;

import com.deathspawn.advanced.capabilityhandlers.DynamicEnergyStorage;
import com.deathspawn.advanced.capabilityhandlers.DynamicFluidTank;
import com.deathspawn.advanced.init.ModFluids;
import com.deathspawn.advanced.lib.Utils;

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
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyGenerator extends TileEntityBase implements ITickable, ICapabilityProvider {

	private ItemStackHandler inventory = new ItemStackHandler(3);
	private DynamicEnergyStorage energy = new DynamicEnergyStorage(100000);
	private DynamicFluidTank fluid = new DynamicFluidTank(ModFluids.enchantedFluid, 0, 100000);
	private String generatorCustomName;

	@Override
	public void update() {
		if (this.inventory.getStackInSlot(0).isItemEqual(
				FluidUtil.getFilledBucket(new FluidStack(ModFluids.enchantedFluid, Fluid.BUCKET_VOLUME)))) {
			fluid.fill(new FluidStack(ModFluids.enchantedFluid, Fluid.BUCKET_VOLUME), true);
			this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
			Utils.getLogger().info("Filling up");
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else if (capability == CapabilityEnergy.ENERGY) {
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
		} else if (capability == CapabilityEnergy.ENERGY) {
			return (T) this.energy;
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this.fluid;
		}
		return super.getCapability(capability, facing);
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

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		energy.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		fluid.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		energy.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		fluid.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		energy.writeToNBT(nbt);
		nbt.setTag("inventory", inventory.serializeNBT());
		fluid.writeToNBT(nbt);
		SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(getPos(), 1, nbt);
		return packet;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		energy.readFromNBT(nbt);
		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		fluid.readFromNBT(nbt);
		super.onDataPacket(net, pkt);
	}

}
