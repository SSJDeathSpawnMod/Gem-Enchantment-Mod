package com.deathspawn.advanced.init;

import java.util.ArrayList;
import java.util.List;

import com.deathspawn.advanced.blocks.BlockBase;
import com.deathspawn.advanced.blocks.BlockOre;
import com.deathspawn.advanced.blocks.fluids.ModFluidBlock;
import com.deathspawn.advanced.blocks.machines.energygenerator.BlockEnergyGenerator;
import com.deathspawn.advanced.blocks.machines.gemenchanter.BlockGemEnchanter;
import com.deathspawn.advanced.tileentities.TileEntityEnergyGenerator;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	//Blocks
	public static Block fireOre = new BlockOre(Material.ROCK, "fire_ore", 2.0f, 3.5f, ModItems.fireGem);
	public static Block fireBlock = new BlockBase(Material.IRON, "fire_block", 5.0f, 4.0f);
	public static Block enchanterGem = new BlockGemEnchanter(Material.IRON, "gem_enchanter", 6.0f, 5.0f);
	public static Block energyGenerator = new BlockEnergyGenerator(Material.IRON, "energy_generator", 7.0f, 5.0f);
	
	//Fluids
	public static Block enchantedFluidBlock = new ModFluidBlock(ModFluids.enchantedFluid, ModMaterials.ENCHANTED, "enchanted");
}
