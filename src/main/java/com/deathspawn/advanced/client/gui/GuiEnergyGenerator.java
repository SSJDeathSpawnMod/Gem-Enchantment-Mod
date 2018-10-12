package com.deathspawn.advanced.client.gui;

import com.deathspawn.advanced.capabilityhandlers.DynamicFluidTank;
import com.deathspawn.advanced.lib.Reference;
import com.deathspawn.advanced.lib.Utils;
import com.deathspawn.advanced.main.GemEnchantmentMod;
import com.deathspawn.advanced.tileentities.container.ContainerEnergyGenerator;
import com.deathspawn.advanced.tileentities.container.ContainerGemEnchanter;
import com.deathspawn.advanced.tileentities.machines.TileEntityEnergyGenerator;
import com.deathspawn.advanced.tileentities.machines.TileEntityGemEnchanter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class GuiEnergyGenerator extends GuiContainer {

	private TileEntityEnergyGenerator te;
	private IInventory playerInv;
	private World worldIn;

	public GuiEnergyGenerator(InventoryPlayer playerInv, TileEntityEnergyGenerator te, World worldIn) {
		super(new ContainerEnergyGenerator(playerInv, te));

		this.xSize = 175;
		this.ySize = 165;

		this.te = te;
		this.playerInv = playerInv;
		this.worldIn = worldIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager()
				.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/energy_generator.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		IEnergyStorage energyHandler = this.te.getCapability(CapabilityEnergy.ENERGY, null);

		if (energyHandler.getEnergyStored() > 0) {
			int l = calculateDownward(74, energyHandler.getEnergyStored(), energyHandler.getMaxEnergyStored());
			this.drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 4, 176, 0, 37, l);
		}

		if (this.te.getFluidAmount() > 0) {
			int l = calculateDownward(70.0F, (float)this.te.getFluidAmount(),
					(float)this.te.getFluidCapacity());
			Utils.getLogger().info(((float)this.te.getFluidAmount() / (float)this.te.getFluidCapacity()) * 70.0F);
			this.drawTexturedModalRect(this.guiLeft + 122, this.guiTop + 8, 176, 75, 7, l);
		}
	}

	private int calculateDownward(float scale, float current, float max) {
		return (int)(current / max * scale);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = GemEnchantmentMod.proxy.localize(te.getName());
		this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

}
