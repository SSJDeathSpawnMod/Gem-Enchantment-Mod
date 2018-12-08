package com.deathspawn.advanced.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ServerProxy implements CommonProxy {

	@Override
	public void getItemRenderer(Item item, int meta, String variant) {
		
	}

	@Override
	public void init() {
		
	}
	
}
