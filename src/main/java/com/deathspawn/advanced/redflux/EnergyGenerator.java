package com.deathspawn.advanced.redflux;

import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class EnergyGenerator implements IEnergyProvider{
	
	private int capacity;
	private int energy;
	private int maxExtract;
	private int maxReceive;
	
	public EnergyGenerator(int capacity) {
		this(capacity, capacity, capacity);
	}
	public EnergyGenerator(int capacity, int transfer) {
		this(capacity, transfer, transfer);
	}
	
	public EnergyGenerator(int capacity, int maxExtract, int maxReceive) {
		this.capacity = capacity;
		this.maxExtract = maxExtract;
		this.maxReceive = maxReceive;
	}
	
	public void modifyEnergy(int amount) {
		energy += amount;
		energy = (energy < 0) ? 0 : energy;
		energy = (energy > capacity) ? capacity : energy;
	}
	
	public EnergyGenerator readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("Energy");
		this.capacity = nbt.getInteger("Capacity");
		this.maxExtract = nbt.getInteger("MaxExtract");
		this.maxReceive = nbt.getInteger("MaxReceive");
		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (energy < 0 ) {
			energy = 0;
		}
		nbt.setInteger("Energy", energy);
		nbt.setInteger("Capacity", capacity);
		nbt.setInteger("MaxExtract", this.maxExtract);
		nbt.setInteger("MaxReceive", this.maxReceive);
		return nbt;
	}
	
	@Override
	public int getEnergyStored(EnumFacing from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return capacity;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

}
