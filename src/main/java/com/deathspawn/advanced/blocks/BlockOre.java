package com.deathspawn.advanced.blocks;

import java.util.Random;

import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class BlockOre extends BlockBase implements IHasModel{
	
	private static Item item;
	
	public BlockOre(Material materialIn, String registryName, float resistance, float hardness, Item itemdropped) {
		super(materialIn, registryName, hardness, hardness);
		this.item = itemdropped;
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return item;
	}
	
}
