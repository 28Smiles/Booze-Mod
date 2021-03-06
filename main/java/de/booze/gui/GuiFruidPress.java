package de.booze.gui;

import java.util.ArrayList;

import cofh.core.gui.GuiBaseAdv;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementFluid;
import cofh.lib.gui.element.ElementFluidTank;
import de.booze.blocks.tiles.TileFruidGrinder;
import de.booze.blocks.tiles.TileFruidPress;
import de.booze.containers.ContainerFruidGrinder;
import de.booze.containers.ContainerFruidPress;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiFruidPress extends GuiBaseAdv {
	
	public static final String TEX_PATH = "booze:textures/gui/press.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);
	
	TileFruidPress tileentity;
	ElementFluid progressFluid;
	ElementDualScaled progressOverlay;
	ElementDualScaled energy;
	
	public GuiFruidPress(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
	    super(new ContainerFruidPress(paramInventoryPlayer, paramTileEntity), TEXTURE);
	    
	    this.tileentity = ((TileFruidPress)paramTileEntity);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		progressOverlay = ((ElementDualScaled)addElement(new ElementDualScaled(this, 88, 41).setMode(1).setSize(24, 16)
				.setTexture("booze:textures/gui/elements/Progress_Press_Right.png", 64, 16)));
		addElement(new ElementFluidTank(this, 116, 17, this.tileentity.getTank()).setGauge(1).setAlwaysShow(true));
		this.energy = ((ElementDualScaled)addElement(new ElementDualScaled(this, 7, 22).setSize(16, 16).setTexture("booze:textures/gui/elements/Scale_Energy.png", 32, 16)));
	}
	
	@Override
	protected void updateElementInformation() {
		super.updateElementInformation();
		int progressQuantityScaled = tileentity.getScaledProcess(24);
		int energyQuantityScaled = tileentity.getScaledEnergyStored(16);
		
		progressOverlay.setQuantity(progressQuantityScaled);
		progressOverlay.setVisible(true);
		energy.setQuantity(energyQuantityScaled);
		energy.setVisible(true);
	}
}
