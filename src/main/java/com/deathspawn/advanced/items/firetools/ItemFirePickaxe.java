package com.deathspawn.advanced.items.firetools;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

public class ItemFirePickaxe extends ItemPickaxe implements IHasModel{
	
	public ItemFirePickaxe(ToolMaterial material, String unlocalizedName, String registryName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
		Utils.getLogger().info(this.isInCreativeTab(GemEnchantmentMod.mainTab));
		
		ModItems.ITEMS.add(this);
		this.setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(this, 0, "inventory");
	}
	
}
