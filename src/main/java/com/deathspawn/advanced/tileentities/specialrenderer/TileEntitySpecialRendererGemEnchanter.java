package com.deathspawn.advanced.tileentities.specialrenderer;

import com.deathspawn.advanced.init.ModItems;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntitySpecialRendererGemEnchanter extends TileEntitySpecialRenderer<TileEntityGemEnchanter> {

	private static final EntityItem ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0,
			new ItemStack(Items.AIR));
	private static final EntityItem ITEM2 = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0,
			new ItemStack(Items.AIR));

	@Override
	public void render(TileEntityGemEnchanter te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		ITEM.setItem(new ItemStack(
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0).getItem()));
		ITEM2.setItem(new ItemStack(
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1).getItem()));
		ITEM.hoverStart = 0f;
		ITEM2.hoverStart = 0f;
		if (te.isEnchanting()) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.disableLighting();
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15 * 16, 15 * 16);
				GlStateManager.translate(x + 0.5, y + 1.1, z + 0.5);
				GlStateManager.translate(-te.getEnchantTime() / 2, 0, 0);
				GlStateManager.rotate(180F, 0f, 1.0f, 0f);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0F, 0f, false);
				GlStateManager.translate(te.getEnchantTime(), 0, 0);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM2, 0, 0, 0, 0F, 0f, false);
				GlStateManager.enableLighting();
			}
			GlStateManager.popMatrix();
		}
	}

}
