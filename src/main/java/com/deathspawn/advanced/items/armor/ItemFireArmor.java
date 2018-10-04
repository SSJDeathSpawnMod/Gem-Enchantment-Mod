package com.deathspawn.advanced.items.armor;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

public class ItemFireArmor extends ItemArmor implements IHasModel{

	public ItemFireArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, unlocalizedName));
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(this, 0, "inventory");
	}

}
