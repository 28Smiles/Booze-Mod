package de.booze.blocks;

import java.util.ArrayList;

import cofh.core.block.BlockCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.booze.BoozeMod;
import de.booze.blocks.tiles.TileFruidGrinder;
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

	public BlockFruidGrinder() {
		super(Material.iron);
		setHardness(15.0F);
	    setResistance(25.0F);
		setBlockName("booze.machine");
		setCreativeTab(BoozeMod.tabCommon);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
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
	public void registerBlockIcons(IIconRegister arg0) {
		super.registerBlockIcons(arg0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int arg0, int arg1) {
		return super.getIcon(arg0, arg1);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int arg4) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null) {
			((TileFruidGrinder)tile).onUnloaded();
		}
	}
	
	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer arg0, NBTTagCompound arg1, World arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new ItemStack(this, 1));
	    return localArrayList;
	}
}
