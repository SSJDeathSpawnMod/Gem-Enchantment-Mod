package com.deathspawn.advanced.tileentities.container;

import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;
import com.deathspawn.advanced.tileentities.container.slots.SlotGemEnchanterBattery;
import com.deathspawn.advanced.tileentities.container.slots.SlotGemEnchanterOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGemEnchanter extends Container{
	
	private TileEntityGemEnchanter te;
	
	public ContainerGemEnchanter(InventoryPlayer player, TileEntityGemEnchanter te ) {
		this.te  = te;
		
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 56, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 56, 53));
		this.addSlotToContainer(new SlotGemEnchanterOutput(player.player, handler, 2, 116, 35));
		this.addSlotToContainer(new SlotGemEnchanterBattery(handler, 3, 9, 16));
		
		for(int y = 0;y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x+y*9+9, 8+x*18, 84+y*18 ));
			}
		}
		
		for(int x=0;x<9;x++) {
			this.addSlotToContainer(new Slot(player, x, 8+x*18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		// TODO Auto-generated method stub
		return super.transferStackInSlot(playerIn, index);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !playerIn.isSpectator();
	}

}
