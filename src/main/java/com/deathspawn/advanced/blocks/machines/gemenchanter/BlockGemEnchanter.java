package com.deathspawn.advanced.blocks.machines.gemenchanter;

import com.deathspawn.advanced.blocks.BlockRotatableBase;
import com.deathspawn.advanced.client.gui.GUI_ID;
import com.deathspawn.advanced.energy.DynamicEnergyStorage;
import com.deathspawn.advanced.main.GemEnchantmentMod;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class BlockGemEnchanter extends BlockRotatableBase{
	
	public BlockGemEnchanter(Material materialIn, String registryName, float resistance, float hardness,
			Class tileEntity) {
		super(materialIn, registryName, resistance, hardness, tileEntity);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(GemEnchantmentMod.instance, GUI_ID.GEM_ENCHANTER.getGUI_ID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
