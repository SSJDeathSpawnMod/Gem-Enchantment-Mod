package com.deathspawn.advanced.tileentities.specialrenderer;

import com.deathspawn.advanced.blocks.BlockRotatableBase;
import com.deathspawn.advanced.init.ModBlocks;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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
		IBlockState b = this.getWorld().getBlockState(new BlockPos(x, y, z));
		ITEM.setItem(new ItemStack(
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0).getItem()));
		ITEM2.setItem(new ItemStack(
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1).getItem()));
		ITEM.hoverStart = 0f;
		ITEM2.hoverStart = 0f;
		if (te.isEnchanting()) {
			GlStateManager.pushMatrix();
			{
				double x2, z2;
				GlStateManager.disableLighting();
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15 * 16, 15 * 16);
				if (this.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock() == ModBlocks.enchanterGem) {
					switch (this.getWorld().getBlockState(new BlockPos(x, y, z)).getValue(BlockRotatableBase.FACING)) {
					case NORTH:
						z2 = z - calculate(te.getEnchantTime(), te.getShouldEnchantTime());
						GlStateManager.translate(x + 0.5, y + 1.05, z2);
						GlStateManager.rotate(180F, 0f, 1.0f, 0f);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0F, 0f, false);
						GlStateManager.translate(0, 0, calculate(te.getEnchantTime(), te.getShouldEnchantTime()));
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM2, 0, 0, 0, 0F, 0f, false);
						break;
					case EAST:
						x2 = x + calculate(te.getEnchantTime(), te.getShouldEnchantTime());
						GlStateManager.translate(x2, y + 1.05, z + 0.5);
						GlStateManager.rotate(180F, 0f, 1.0f, 0f);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0F, 0f, false);
						GlStateManager.translate(-calculate(te.getEnchantTime(), te.getShouldEnchantTime()), 0, 0);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM2, 0, 0, 0, 0F, 0f, false);
						break;
					case WEST:
						x2 = x - calculate(te.getEnchantTime(), te.getShouldEnchantTime());
						GlStateManager.translate(x2, y + 1.05, z + 0.5);
						GlStateManager.rotate(180F, 0f, 1.0f, 0f);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0F, 0f, false);
						GlStateManager.translate(calculate(te.getEnchantTime(), te.getShouldEnchantTime()), 0, 0);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM2, 0, 0, 0, 0F, 0f, false);
						break;
					case SOUTH:
						z2 = z + calculate(te.getEnchantTime(), te.getShouldEnchantTime());
						GlStateManager.translate(x + 0.5, y + 1.05, z2);
						GlStateManager.rotate(180F, 0f, 1.0f, 0f);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0F, 0f, false);
						GlStateManager.translate(0, 0, -calculate(te.getEnchantTime(), te.getShouldEnchantTime()));
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM2, 0, 0, 0, 0F, 0f, false);
						break;
					case UP:
						break;
					case DOWN:
						break;
					default:
						break;

					}
					GlStateManager.enableLighting();
				}
			}
			GlStateManager.popMatrix();
		}
	}

	private float calculate(float enchantTime, float shouldEnchantTime) {
		Utils.getLogger().info(enchantTime / shouldEnchantTime);
		return (enchantTime / shouldEnchantTime) / 2;
	}

}
