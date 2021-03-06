package com.deathspawn.advanced.items.firetools;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;

public class ItemFireShovel extends ItemSpade implements IHasModel{
	
	public ItemFireShovel(ToolMaterial material, String unlocalizedName, String registryName) {
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
