package com.deathspawn.advanced.tileentities.container.slots.energygenerator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotEnergyGeneratorBattery extends SlotItemHandler{

	public SlotEnergyGeneratorBattery(IItemHandler handler, int index, int xPosition, int yPosition) {
		super(handler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	
}
