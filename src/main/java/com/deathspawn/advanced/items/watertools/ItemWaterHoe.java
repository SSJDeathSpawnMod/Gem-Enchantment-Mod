package com.deathspawn.advanced.items.watertools;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

public class ItemWaterHoe extends ItemHoe implements IHasModel{

	public ItemWaterHoe(ToolMaterial material, String unlocalizedName, String registryName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
		this.setCreativeTab(GemEnchantmentMod.mainTab);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(this, 0, "inventory");
	}

}
