package com.deathspawn.advanced.tileentities.specialrenderer;

import com.deathspawn.advanced.tileentities.TileEntityEnergyGenerator;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntitySpecialRendererGemEnchanter extends TileEntitySpecialRenderer<TileEntityGemEnchanter> {
	
	private static final EntityItem ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, new ItemStack(Items.AIR));
	
	@Override
	public void render(TileEntityGemEnchanter te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		ITEM.setItem(new ItemStack(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0).getItem()));
		GlStateManager.pushMatrix();
		{
			Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, x + 0.5, y + 1, z + 0.5, 0F, partialTicks,
					false);
		}
		GlStateManager.popMatrix();
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}

}
