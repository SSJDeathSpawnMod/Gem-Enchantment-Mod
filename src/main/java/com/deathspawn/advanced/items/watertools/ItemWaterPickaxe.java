package com.deathspawn.advanced.items.watertools;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

public class ItemWaterPickaxe extends ItemPickaxe implements IHasModel{
	
	public ItemWaterPickaxe(ToolMaterial material, String unlocalizedName, String registryName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(this, 0, "inventory");
		this.setCreativeTab(GemEnchantmentMod.mainTab);
	}
	
}
