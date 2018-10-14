package com.deathspawn.advanced.redflux;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class EnergyMachine implements IEnergyReceiver{
	
	private int capacity;
	private int energy;
	private int maxExtract;
	private int maxReceive;
	
	public EnergyMachine(int capacity) {
		this(capacity, capacity, capacity);
	}
	public EnergyMachine(int capacity, int transfer) {
		this(capacity, transfer, transfer);
	}
	
	public EnergyMachine(int capacity, int maxExtract, int maxReceive) {
		this.capacity = capacity;
		this.maxExtract = maxExtract;
		this.maxReceive = maxReceive;
	}
	
	public void modifyEnergy(int amount) {
		energy += amount;
		energy = (energy < 0) ? 0 : energy;
		energy = (energy > capacity) ? capacity : energy;
	}
	
	public EnergyMachine readFromNBT(NBTTagCompound nbt) {
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
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

}
