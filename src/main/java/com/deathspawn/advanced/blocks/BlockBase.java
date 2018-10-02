package com.deathspawn.advanced.blocks;

import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block implements IHasModel{

	public BlockBase(Material materialIn, String registryName, float resistance, float hardness) {
		super(materialIn);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
		this.setUnlocalizedName(registryName);
		this.setHardness(hardness);
		this.setResistance(resistance);
		
		ModBlocks.BLOCKS.add(this);
		this.setCreativeTab(GemEnchantmentMod.mainTab);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName)).setUnlocalizedName(registryName));
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
