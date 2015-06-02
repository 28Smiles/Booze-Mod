package de.booze.blocks;

import java.util.ArrayList;

import cofh.core.block.BlockCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.BoozeMod;
import de.booze.blocks.tiles.TileFruidGrinder;
import de.booze.blocks.tiles.TileFruidGrinderIC2;
import de.booze.blocks.tiles.TileFruidPressIC2;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFruidGrinder extends BlockBaseTileProvider {
	
	private IIcon side1;
	private IIcon side2;
	private IIcon bottom;
	private IIcon top;
	
	public BlockFruidGrinder() {
		super(Material.iron);
		setHardness(15.0F);
	    setResistance(25.0F);
		setBlockName("booze.machine.fruidgrinder");
		setCreativeTab(BoozeMod.tabCommon);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		if(BoozeMod.ic2Enabled)
			return new TileFruidGrinderIC2();
		return new TileFruidGrinder();
	}

	@Override
	public boolean initialize() {
		GameRegistry.registerBlock(this, "fruidgrinder");
		return super.initialize();
	}

	@Override
	public boolean postInit() {
		return super.postInit();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		side1 =  register.registerIcon(BoozeMod.MODID + ":machine/grinder_side_1");
		side2 =  register.registerIcon(BoozeMod.MODID + ":machine/grinder_side_2");
		top = 	 register.registerIcon(BoozeMod.MODID + ":machine/grinder_top");
		bottom = register.registerIcon(BoozeMod.MODID + ":machine/grinder_bottom");
		super.registerBlockIcons(register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch(side) {
			case 0: return bottom;
			case 1: return top;
			case 2: return side1;
			case 3: return side1;
			case 4: return side2;
			case 5: return side2;
		}
		return super.getIcon(side, meta);
	}
	
	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer arg0, NBTTagCompound arg1, World arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new ItemStack(this, 1));
	    return localArrayList;
	}
}
