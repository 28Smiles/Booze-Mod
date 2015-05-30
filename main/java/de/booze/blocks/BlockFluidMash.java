package de.booze.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.Fluid;
import cofh.core.fluid.BlockFluidCoFHBase;
import de.booze.BoozeMod;

public class BlockFluidMash extends BlockFluidCoFHBase {

	public static final Material materialFluidMash = new MaterialLiquid(MapColor.redColor);
	
	public BlockFluidMash() {
		super("booze", BoozeMod.fluids.fluidMash, Material.water, "mash");
		setTickRate(10);
		setHardness(1.0F);
		setLightOpacity(8);
	}
}
