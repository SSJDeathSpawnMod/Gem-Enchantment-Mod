package com.deathspawn.advanced.main;

import com.deathspawn.advanced.blocks.BlockRotatableBase;
import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.proxy.CommonProxy;
import com.deathspawn.advanced.tileentities.TileEntityEnergyGenerator;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = "1.12.2")
public class GemEnchantmentMod {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("edm");

	@Instance
	public static GemEnchantmentMod instance;

	public static GemEnchantmentModCreativeTab mainTab = new GemEnchantmentModCreativeTab("mainGemEnchantmentMod",
			new ItemStack(ModItems.fireGem, 1));

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		
	}

	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		proxy.init();
		GameRegistry.registerTileEntity(TileEntityGemEnchanter.class,Reference.MOD_ID + ":gem_enchanter");
		GameRegistry.registerTileEntity(TileEntityEnergyGenerator.class,Reference.MOD_ID + ":energy_generator");
	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {

	}

}
