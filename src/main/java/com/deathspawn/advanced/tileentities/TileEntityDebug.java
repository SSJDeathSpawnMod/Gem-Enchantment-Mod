package com.deathspawn.advanced.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class TileEntityDebug extends TileEntityBase implements ITickable{
	
	public TileEntityDebug() {
	}
	
	@Override
	public void update() {
		List<EntityPlayer> surroundingPlayers = new ArrayList<EntityPlayer>();
		surroundingPlayers.addAll(world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.pos.add(-3, -3, -3), this.pos.add(3, 3, 3))));
		if(!surroundingPlayers.isEmpty()) {
			for(EntityPlayer player : surroundingPlayers) {
				if(!world.isRemote) {
					player.sendMessage(new TextComponentString(TextFormatting.RED + "This is only a test TileEntity"));
				}
			}
		}
	}
}
