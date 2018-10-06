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

	@Override
	public void render(TileEntityGemEnchanter te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		Item item = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0).getItem();
		EntityItem ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0,
				new ItemStack(item));
		if(item != null || item != Items.AIR ) {
			GlStateManager.pushMatrix(); {
				GlStateManager.translate(x+0.5, y+1.1, z+0.5);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0, 0, true);
			}
			GlStateManager.popMatrix();
		}
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}

}
