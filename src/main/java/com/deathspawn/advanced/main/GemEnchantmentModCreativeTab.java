package com.deathspawn.advanced.main;

import com.deathspawn.advanced.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GemEnchantmentModCreativeTab extends CreativeTabs {
	
	private ItemStack tabIcon;
	
	public GemEnchantmentModCreativeTab(String label, ItemStack tabIcon) {
		super(label);
		this.tabIcon = tabIcon;
	}
	
	public GemEnchantmentModCreativeTab(String label) {
		super(label);
		this.tabIcon = new ItemStack(ModItems.fireGem, 1);
	}

	@Override
	public ItemStack getTabIconItem() {
		return this.tabIcon;
	}

}
