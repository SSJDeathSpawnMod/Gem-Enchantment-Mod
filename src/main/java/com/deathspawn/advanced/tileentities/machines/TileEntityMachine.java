package com.deathspawn.advanced.tileentities.machines;

import com.deathspawn.advanced.handlers.EnumHandler.IO;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.TileEntityBase;

import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityMachine extends TileEntityBase implements ITickable{
	
	private IO io;
	
	public TileEntityMachine(IO io) {
		this.io = io;
	}
	
	@Override
	public void update() {
		switch(io) {
		case INPUT:
			BlockPos posi = this.getPos();
			Iterable<BlockPos> blocksi = BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1));
			for(BlockPos block : blocksi) {
				if(this.world.getTileEntity(pos) instanceof TileEntityMachine) {
					Utils.getLogger().info("Some machines are near each other");
				}
			}
			break;
		case OUTPUT:
			BlockPos poso = this.getPos();
			Iterable<BlockPos> blockso = BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1));
			for(BlockPos block : blockso) {
				if(this.world.getTileEntity(pos) instanceof TileEntityMachine) {
					Utils.getLogger().info("Some machines are near each other");
				}
			}
			break;
		default:
			Utils.getLogger().info("How?");
			break;
		}
	}

}
