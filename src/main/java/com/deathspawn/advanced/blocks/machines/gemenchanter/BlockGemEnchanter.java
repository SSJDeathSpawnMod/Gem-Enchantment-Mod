package com.deathspawn.advanced.blocks.machines.gemenchanter;

import com.deathspawn.advanced.blocks.BlockRotatableBase;
import com.deathspawn.advanced.handlers.EnumHandler.GUI_ID;
import com.deathspawn.advanced.main.GemEnchantmentMod;
import com.deathspawn.advanced.tileentities.machines.TileEntityGemEnchanter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGemEnchanter extends BlockRotatableBase{
	
	public BlockGemEnchanter(Material materialIn, String registryName, float resistance, float hardness) {
		super(materialIn, registryName, resistance, hardness);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(GemEnchantmentMod.instance, GUI_ID.GEM_ENCHANTER.getGUI_ID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGemEnchanter();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityGemEnchanter();
	}
	
}
