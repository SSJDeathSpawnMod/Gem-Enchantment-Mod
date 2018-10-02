package com.deathspawn.advanced.client.gui;

import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.main.GemEnchantmentMod;
import com.deathspawn.advanced.tileentities.TileEntityGemEnchanter;
import com.deathspawn.advanced.tileentities.container.ContainerGemEnchanter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiGemEnchanter extends GuiContainer{

	private TileEntityGemEnchanter te;
	private IInventory playerInv;
	
	public GuiGemEnchanter(InventoryPlayer playerInv, TileEntityGemEnchanter te) {
		super(new ContainerGemEnchanter(playerInv, te));
		
		this.xSize = 175;
		this.ySize = 165;
		
		this.te = te;
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/gem_enchanter.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = GemEnchantmentMod.proxy.localize(te.getName()); //Gets the formatted name for the block breaker from the language file - NOTE ADD "container.block_breaker=Block Breaker" to the language file (without quotes) and then delete this note
		this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752); 
		this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
		if(this.te.getEnchantTime() > 0) {
			this.drawTexturedModalRect(79, 34, 176, 14, this.getEnchantedAmount(), 17);
		}
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	private int getEnchantedAmount() {
		int currentItemEnchantAmount = this.te.getCurrentItemEnchantTime();
		int enchantTime = this.te.getEnchantTime();
		float percentage = enchantTime/currentItemEnchantAmount * 100;
		int amount = Math.round(percentage) * 24;
		return amount;
	}
	
}
