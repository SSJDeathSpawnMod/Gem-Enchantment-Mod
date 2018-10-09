package com.deathspawn.advanced.blocks;

import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.TileEntityDebug;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockTileEntityBase extends BlockBase implements ITileEntityProvider{
	
	public BlockTileEntityBase(Material materialIn, String registryName, float resistance, float hardness) {
		super(materialIn, registryName, resistance, hardness);
	}

}
