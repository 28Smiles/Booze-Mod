package de.booze.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cofh.core.gui.GuiBaseAdv;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementFluidTank;
import de.booze.blocks.tiles.TileEntityBarrel;
import de.booze.containers.ContainerBarrel;

public class GuiBarrel extends GuiBaseAdv {

	public static final String TEX_PATH = "booze:textures/gui/barrel.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);
	
	TileEntityBarrel tileentity;
	
	ElementButton mode;
	
	public GuiBarrel(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity) {
		super(new ContainerBarrel(paramInventoryPlayer, paramTileEntity), TEXTURE);
	    
	    this.tileentity = ((TileEntityBarrel)paramTileEntity);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementFluidTank(this, 116, 17, this.tileentity.getTank()).setGauge(1).setAlwaysShow(true));
		this.mode = ((ElementButton)addElement(new ElementButton(this, 97, 62, "Mode", 176, 0, 176, 16, 176, 32, 16, 16, TEX_PATH)));
	}
	
	@Override
	protected void updateElementInformation() {
		super.updateElementInformation();
		if (!this.tileentity.isClosed) {
		    if (this.tileentity.isClosed) {
		        this.mode.setToolTip("info.booze.toggleWait");
		        this.mode.setDisabled();
		    } else {
		        this.mode.setToolTip("info.booze.toggleFill");
		        this.mode.setSheetX(192);
		        this.mode.setHoverX(192);
		        this.mode.setActive();
		    }
		} else if (!this.tileentity.isClosed) {
		        this.mode.setToolTip("info.booze.toggleWait");
		        this.mode.setDisabled();
		    } else {
		        this.mode.setToolTip("info.booze.toggleFerment");
		        this.mode.setSheetX(176);
		        this.mode.setHoverX(176);
		        this.mode.setActive();
		    }
		  }

		  public void handleElementButtonClick(String paramString, int paramInt)
		  {
		    if ((paramString.equals("Mode")) && 
		      (this.tileentity.isClosed == this.tileentity.isClosed)) {
		      if (this.tileentity.isClosed)
		        playSound("random.click", 1.0F, 0.8F);
		      else {
		        playSound("random.click", 1.0F, 0.6F);
		      }
		      this.tileentity.setMode(!this.tileentity.isClosed);
		    }
		  }
}
