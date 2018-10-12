package com.deathspawn.advanced.tileentities.container;

import com.deathspawn.advanced.tileentities.container.slots.gemenchanter.SlotGemEnchanterBattery;
import com.deathspawn.advanced.tileentities.container.slots.gemenchanter.SlotGemEnchanterOutput;
import com.deathspawn.advanced.tileentities.machines.TileEntityGemEnchanter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGemEnchanter extends Container {

	private TileEntityGemEnchanter te;

	public ContainerGemEnchanter(InventoryPlayer player, TileEntityGemEnchanter te) {
		this.te = te;

		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 56, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 56, 53));
		this.addSlotToContainer(new SlotGemEnchanterOutput(player.player, handler, 2, 116, 35));
		this.addSlotToContainer(new SlotGemEnchanterBattery(handler, 3, 9, 16));

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !playerIn.isSpectator();
	}

}
