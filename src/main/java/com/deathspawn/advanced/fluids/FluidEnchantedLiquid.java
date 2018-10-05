package com.deathspawn.advanced.fluids;

import com.deathspawn.advanced.init.ModFluids;
import com.deathspawn.advanced.init.ModMaterials;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidEnchantedLiquid extends Fluid{
	
	 protected static int mapColor = 0xFFFFFFFF;
	    protected static float overlayAlpha = 0.2F;
	    protected static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
	    protected static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
	    protected static Material material = Material.WATER;
	 
	    public FluidEnchantedLiquid(String fluidName, ResourceLocation still, ResourceLocation flowing) 
	    {
	        super(fluidName, still, flowing);
	        this.setViscosity(1500);
	        this.setDensity(100);
	        this.setRarity(EnumRarity.UNCOMMON);
	        this.setLuminosity(2);
	        this.setMaterial(ModMaterials.ENCHANTED);
	        this.setTemperature(300);
	        this.setFillSound(SoundEvents.ITEM_BUCKET_FILL);
	        this.setEmptySound(SoundEvents.ITEM_BUCKET_EMPTY);
	        
	        ModFluids.FLUIDS.add(this);
	    }
	 
	    public FluidEnchantedLiquid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor) 
	    {
	        this(fluidName, still, flowing);
	        setColor(mapColor);
	        ModFluids.FLUIDS.add(this);
	    }
	 
	    public FluidEnchantedLiquid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor, float overlayAlpha) 
	    {
	        this(fluidName, still, flowing, mapColor);
	        setAlpha(overlayAlpha);
	        ModFluids.FLUIDS.add(this);
	    }
	 
	    @Override
	    public int getColor()
	    {
	        return mapColor;
	    }
	 
	    public FluidEnchantedLiquid setColor(int parColor)
	    {
	        mapColor = parColor;
	        return this;
	    }
	 
	    public float getAlpha()
	    {
	        return overlayAlpha;
	    }
	 
	    public FluidEnchantedLiquid setAlpha(float parOverlayAlpha)
	    {
	        overlayAlpha = parOverlayAlpha;
	        return this;
	    }
	 
	    @Override
	    public FluidEnchantedLiquid setEmptySound(SoundEvent parSound)
	    {
	        emptySound = parSound;
	        return this;
	    }
	 
	    @Override
	    public SoundEvent getEmptySound()
	    {
	        return emptySound;
	    }
	 
	    @Override
	    public FluidEnchantedLiquid setFillSound(SoundEvent parSound)
	    {
	        fillSound = parSound;
	        return this;
	    }
	 
	    @Override
	    public SoundEvent getFillSound()
	    {
	        return fillSound;
	    }
	 
	    public FluidEnchantedLiquid setMaterial(Material parMaterial)
	    {
	        material = parMaterial;
	        return this;
	    }
	 
	    public Material getMaterial()
	    {
	        return material;
	    }
	 
	    @Override
	    public boolean doesVaporize(FluidStack fluidStack)
	    {
	        if (block == null)
	            return false;
	        return block.getDefaultState().getMaterial() == getMaterial();
	    }
	
}
