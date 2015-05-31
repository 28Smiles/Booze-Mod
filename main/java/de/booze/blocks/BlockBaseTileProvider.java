package de.booze.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventBus;
import cofh.core.block.BlockCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import de.booze.blocks.tiles.TileBase;

public class BlockBaseTileProvider extends BlockCoFHBase {
	
	protected boolean basicGui = true;
	
	public BlockBaseTileProvider(Material material) {
		super(material);
	}

	public void onBlockPlacedBy(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack)
	{
		TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
		
		if((localTileEntity instanceof TileBase)) {
			((TileBase)localTileEntity).setInvName(ItemHelper.getNameFromItemStack(paramItemStack));
		}
		super.onBlockPlacedBy(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLivingBase, paramItemStack);
	}
	
	public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
	  {
	    if (paramEntityPlayer.isSneaking()) {
	      return false;
	    }
	    TileBase localTileTEBase = (TileBase)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

	    if (localTileTEBase == null) {
	      return false;
	    }
	    if ((this.basicGui) && (ServerHelper.isServerWorld(paramWorld))) {
	      return localTileTEBase.openGui(paramEntityPlayer);
	    }
	    return this.basicGui;
	  }

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileBase();
	}

	@Override
	public boolean initialize() {
		return true;
	}

	@Override
	public boolean postInit() {
		return true;
	}

	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer arg0, NBTTagCompound arg1, World arg2, int arg3, int arg4, int arg5, boolean arg6, boolean arg7) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new ItemStack(this));
		return localArrayList;
	}
}
