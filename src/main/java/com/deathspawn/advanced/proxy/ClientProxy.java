package com.deathspawn.advanced.proxy;

import java.awt.Color;

import com.deathspawn.advanced.client.gui.GuiHandler;
import com.deathspawn.advanced.init.ModFluids;
import com.deathspawn.advanced.init.ModMaterials;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy implements CommonProxy {

	@Override
	public void getItemRenderer(Item item, int meta, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(
				new ResourceLocation(Reference.MOD_ID, item.getUnlocalizedName().substring(5)), variant));
	}

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	@Override
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(GemEnchantmentMod.instance, new GuiHandler());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == ElementType.ALL) {

			EntityPlayer thePlayer = Minecraft.getMinecraft().player;

			if (thePlayer.isInsideOfMaterial(ModMaterials.ENCHANTED)) {
				drawFluidOverlay(ModFluids.enchantedFluid.getColor(), 0.3F);
			}
		}
	}

	/**
	 * Draws a rectangle with the specified color
	 */
	@SideOnly(Side.CLIENT)
	public static void drawFluidOverlay(int parColor, float parAlpha) {
		int left = 0;
		int top = 0;
		int right = Minecraft.getMinecraft().displayWidth;
		int bottom = Minecraft.getMinecraft().displayHeight;
		Color color = Color.RED;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), parAlpha);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(left, bottom, 0.0D).endVertex();
		bufferbuilder.pos(right, bottom, 0.0D).endVertex();
		bufferbuilder.pos(right, top, 0.0D).endVertex();
		bufferbuilder.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(FogDensity event) {
		if (event.getEntity().isInsideOfMaterial(ModMaterials.ENCHANTED)) {
			event.setDensity(0.2F);
		} else {
			event.setDensity(0.0001F);
		}

		event.setCanceled(true); // must cancel event for event handler to take effect
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(FogColors event) {
		if (event.getEntity().isInsideOfMaterial(ModMaterials.ENCHANTED)) {
			Color theColor = Color.RED;
			event.setRed(theColor.getRed());
			event.setGreen(theColor.getGreen());
			event.setBlue(theColor.getBlue());
		}
	}

}
