package com.deathspawn.advanced.redflux;

import com.deathspawn.advanced.tileentities.machines.TileEntityEnergyGenerator;

import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class EnergyGenerator implements IEnergyProvider{
	
	private int capacity;
	private int energy;
	private int maxExtract;
	private int maxReceive;
	private TileEntity te;
	
	public EnergyGenerator(int capacity, TileEntity te) {
		this(capacity, capacity, capacity, te);
	}
	public EnergyGenerator(int capacity, int transfer, TileEntity te) {
		this(capacity, transfer, transfer, te);
	}
	
	public EnergyGenerator(int capacity, int maxExtract, int maxReceive, TileEntity te) {
		this.capacity = capacity;
		this.maxExtract = maxExtract;
		this.maxReceive = maxReceive;
		this.te = te;
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
		if(this.te instanceof TileEntityEnergyGenerator) {
			
		}
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
