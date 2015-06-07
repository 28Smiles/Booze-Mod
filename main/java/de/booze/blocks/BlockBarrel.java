package de.booze.blocks;

import cofh.api.tileentity.ISidedTexture;
import cpw.mods.fml.common.registry.GameRegistry;
import de.booze.BoozeMod;
import de.booze.blocks.tiles.TileEntityBarrel;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBarrel extends BlockBaseTileProvider {
	
	public IIcon side;
	public IIcon front;
	
	public BlockBarrel() {
		super(Material.wood);
		setHardness(8.0F);
	    setResistance(15.0F);
		setBlockName("booze.machine.barrel");
		setCreativeTab(BoozeMod.tabCommon);
	}
	
	@Override
	public TileEntity createNewTileEntity(World arg0, int meta) {
		return new TileEntityBarrel();
	}
	
	@Override
	public boolean preInit() {
		GameRegistry.registerBlock(this, "barrel");
		return super.preInit();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		side  =  register.registerIcon(BoozeMod.MODID + ":machine/barrel_side");
		front =  register.registerIcon(BoozeMod.MODID + ":machine/barrel_front");
		super.registerBlockIcons(register);
	}
	
	public IIcon getIcon(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
	{
	    ISidedTexture localISidedTexture = (ISidedTexture)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
	    return localISidedTexture == null ? null : localISidedTexture.getTexture(paramInt4, renderPass);
	}

	public IIcon getIcon(int paramInt1, int paramInt2)
	{
	    return paramInt1 == 4 ? front : paramInt1 == 5 ? front : side;
	}
}
