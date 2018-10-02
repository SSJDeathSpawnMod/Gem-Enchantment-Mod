package com.deathspawn.advanced.blocks;

import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.TileEntityDebug;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTileEntityBase extends BlockBase implements ITileEntityProvider{
	
	private static Class tileEntity;
	
	public BlockTileEntityBase(Material materialIn, String registryName, float resistance, float hardness,Class<? extends TileEntity> tileEntity) {
		super(materialIn, registryName, resistance, hardness);
		this.tileEntity = tileEntity;
	}
	
	public Class<? extends TileEntity> getTileEntityClass() {
		return tileEntity;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		TileEntity tile = null;
		try {
			tile = ((TileEntity)tileEntity.newInstance());
		}catch(ClassCastException | InstantiationException | IllegalAccessException e){
			Utils.getLogger().info(e.getMessage());
		}
		if(tile == null) {
			tile = new TileEntityDebug();
		}
		return tile;
	}

}
