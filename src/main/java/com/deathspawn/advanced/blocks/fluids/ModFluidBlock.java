package com.deathspawn.advanced.blocks.fluids;

import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class ModFluidBlock extends BlockFluidClassic implements IHasModel{

	public ModFluidBlock(Fluid fluid, Material material, String unlocalizedName) {
		super(fluid, material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, unlocalizedName));
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setUnlocalizedName(unlocalizedName).setRegistryName(new ResourceLocation(Reference.MOD_ID, unlocalizedName)));
	}
	
	@Override
	public void registerModel() {
		GemEnchantmentMod.proxy.getItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		this.setCreativeTab(GemEnchantmentMod.mainTab);
	}

}
