package com.deathspawn.advanced.tileentities;

import com.deathspawn.advanced.energy.DynamicEnergyStorage;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerFluidMap;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyGenerator extends TileEntityBase implements ITickable, ICapabilityProvider{
	
	private ItemStackHandler inventory = new ItemStackHandler(1);
	private DynamicEnergyStorage energy = new DynamicEnergyStorage(100000);
	private FluidTank fluids = new FluidTank(100000);
	
	@Override
	public void update() {
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		else if(capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.inventory;
		}
		else if(capability == CapabilityEnergy.ENERGY) {
			return (T) this.energy;
		}
		return super.getCapability(capability, facing);
	}
	
}
