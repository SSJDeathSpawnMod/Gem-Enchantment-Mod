package com.deathspawn.advanced.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

public class CommonProxy {
	
	public void getItemRenderer(Item item, int meta, String variant) {}
	
	public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}
	
	public void init() {}
	
}
