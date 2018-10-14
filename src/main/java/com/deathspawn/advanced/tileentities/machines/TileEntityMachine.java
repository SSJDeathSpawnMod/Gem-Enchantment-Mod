package com.deathspawn.advanced.tileentities.machines;

import java.util.ArrayList;
import java.util.List;

import com.deathspawn.advanced.handlers.EnumHandler.IO;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.TileEntityBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import scala.actors.threadpool.Arrays;

public abstract class TileEntityMachine extends TileEntityBase implements ITickable{
	
	private IO io;
	
	public TileEntityMachine(IO io) {
		this.io = io;
	}
	
	public IO getIo() {
		return io;
	}

	@Override
	public void update() {
		switch(io) {
		case INPUT:
			List<EnumFacing> facingi = new ArrayList<EnumFacing>();
			facingi.addAll(Arrays.asList(EnumFacing.VALUES.clone()));
			BlockPos copyi = getPos();
			for(EnumFacing side : facingi) {
				TileEntity te = world.getTileEntity(copyi.offset(side));
				if(world.getTileEntity(copyi.offset(side)) instanceof TileEntityMachine ) {
					if(((TileEntityMachine)te).getIo() == IO.OUTPUT) { 
						Utils.getLogger().info("Some machines are near each other");
					}
				}
			}
			break;
		case OUTPUT:
			List<EnumFacing> facingo = new ArrayList<EnumFacing>();
			facingo.addAll(Arrays.asList(EnumFacing.VALUES.clone()));
			BlockPos copyo = getPos();
			for(EnumFacing side : facingo) {
				TileEntity te = world.getTileEntity(copyo.offset(side));
				if(world.getTileEntity(copyo.offset(side)) instanceof TileEntityMachine ) {
					if(((TileEntityMachine)te).getIo() == IO.INPUT) { 
						Utils.getLogger().info("Some machines are near each other");
					}
				}
			}
			break;
		default:
			Utils.getLogger().info("How?");
			break;
		}
	}

}
