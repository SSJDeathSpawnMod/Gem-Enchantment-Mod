package com.deathspawn.advanced.init;

import java.util.ArrayList;
import java.util.List;

import com.deathspawn.advanced.fluids.FluidEnchantedLiquid;
import com.deathspawn.advanced.lib.Reference;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ModFluids {
	
	public static List<Fluid> FLUIDS = new ArrayList<Fluid>();
	
	public static Fluid enchantedFluid = new FluidEnchantedLiquid("enchanted", new ResourceLocation(Reference.MOD_ID, "enchanted_still"), new ResourceLocation(Reference.MOD_ID, "enchanted_flow"));
}
