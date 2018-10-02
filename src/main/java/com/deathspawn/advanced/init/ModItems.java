package com.deathspawn.advanced.init;

import java.util.ArrayList;
import java.util.List;

import com.deathspawn.advanced.items.ItemBase;
import com.deathspawn.advanced.items.firetools.ItemFireAxe;
import com.deathspawn.advanced.items.firetools.ItemFireHoe;
import com.deathspawn.advanced.items.firetools.ItemFirePickaxe;
import com.deathspawn.advanced.items.firetools.ItemFireShovel;
import com.deathspawn.advanced.items.firetools.ItemFireSword;
import com.deathspawn.advanced.lib.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static List<Item> ITEMS = new ArrayList<Item>();
	
	//Tool Material
	public static final ToolMaterial FIRE_MAT = EnumHelper.addToolMaterial(Reference.MOD_ID + ":fire", 4, 1600, 9.0F, 4.0F, 15);
	
	//Items
	public static Item fireGem = new ItemBase("fire_ingot");
	
	//Tools
	//Fire
	public static Item fireAxe = new ItemFireAxe(FIRE_MAT, "fire_axe", "fire_axe");
	public static Item fireHoe = new ItemFireHoe(FIRE_MAT, "fire_hoe", "fire_hoe");
	public static Item firePickaxe = new ItemFirePickaxe(FIRE_MAT, "fire_pickaxe", "fire_pickaxe");
	public static Item fireShovel = new ItemFireShovel(FIRE_MAT, "fire_shovel", "fire_shovel");
	public static Item fireSword = new ItemFireSword(FIRE_MAT, "fire_sword", "fire_sword");
	
}