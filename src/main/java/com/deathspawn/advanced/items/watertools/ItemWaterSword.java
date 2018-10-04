package com.deathspawn.advanced.items.watertools;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ItemWaterSword extends ItemSword implements IHasModel{
	
	public ItemWaterSword(ToolMaterial material, String unlocalizedName, String registryName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
		ModItems.ITEMS.add(this);
		this.setCreativeTab(GemEnchantmentMod.mainTab);
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(this, 0, "inventory");
	}
	
}
