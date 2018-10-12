package com.deathspawn.advanced.handlers;

import com.deathspawn.advanced.blocks.BlockRotatableBase;
import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.init.ModFluids;
import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.lib.IHasModel;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.machines.TileEntityGemEnchanter;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		for (Fluid fluid : ModFluids.FLUIDS) {
			boolean done = FluidRegistry.registerFluid(fluid);
			FluidRegistry.addBucketForFluid(fluid);
			if(done) {
				Utils.getLogger().info("Succcessful!");
			}
		}
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel) item).registerModel();
			}
		}
		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel) block).registerModel();
			}
		}
	}
}
