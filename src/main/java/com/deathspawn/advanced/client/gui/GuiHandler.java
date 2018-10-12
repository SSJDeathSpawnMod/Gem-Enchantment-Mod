package com.deathspawn.advanced.client.gui;

import com.deathspawn.advanced.tileentities.container.ContainerEnergyGenerator;
import com.deathspawn.advanced.tileentities.container.ContainerGemEnchanter;
import com.deathspawn.advanced.tileentities.machines.TileEntityEnergyGenerator;
import com.deathspawn.advanced.tileentities.machines.TileEntityGemEnchanter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ID.GEM_ENCHANTER.getGUI_ID()) {
			return new ContainerGemEnchanter(player.inventory, (TileEntityGemEnchanter) world.getTileEntity(new BlockPos(x,y,z)));
		}
		if(ID == GUI_ID.ENERGY_GENERATOR.getGUI_ID()) {
			return new ContainerEnergyGenerator(player.inventory, (TileEntityEnergyGenerator) world.getTileEntity(new BlockPos(x,y,z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ID.GEM_ENCHANTER.getGUI_ID()) {
			return new GuiGemEnchanter(player.inventory, (TileEntityGemEnchanter) world.getTileEntity(new BlockPos(x,y,z)), world);
		}
		if(ID == GUI_ID.ENERGY_GENERATOR.getGUI_ID()) {
			return new GuiEnergyGenerator(player.inventory, (TileEntityEnergyGenerator) world.getTileEntity(new BlockPos(x,y,z)), world);
		}
		return null;
	}

}
