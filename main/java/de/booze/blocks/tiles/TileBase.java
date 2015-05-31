package de.booze.blocks.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import cofh.core.block.TileCoFHBase;
import cofh.core.network.ITileInfoPacketHandler;
import cofh.core.network.ITilePacketHandler;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import cofh.lib.util.helpers.ServerHelper;
import de.booze.BoozeMod;
import de.booze.BoozeProps;

public class TileBase extends TileCoFHBase implements ITileInfoPacketHandler, ITilePacketHandler {
	
	protected String tileName = "";

	@Override
	public String getName() {
		return tileName;
	}

	@Override
	public int getType() {
		return 0;
	}

	public void setInvName(String nameFromItemStack) {
		tileName = nameFromItemStack;
	}
	
	public boolean hasGui() {
		return true;
	}
	
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {
		super.readFromNBT(paramNBTTagCompound);
		
		if (paramNBTTagCompound.hasKey("Name"))
			this.tileName = paramNBTTagCompound.getString("Name");
	}

	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {
		super.writeToNBT(paramNBTTagCompound);
		if (!this.tileName.isEmpty())
			paramNBTTagCompound.setString("Name", this.tileName);
	}
	
	public boolean openGui(EntityPlayer paramEntityPlayer) {
		if (hasGui()) {
			paramEntityPlayer.openGui(BoozeMod.INSTANCE, BoozeProps.GuiID.BASE.ordinal(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		return false;
	}
	
	public void sendGuiNetworkData(Container paramContainer, ICrafting paramICrafting)
	{
		if ((paramICrafting instanceof EntityPlayer)) {
			PacketCoFHBase localPacketCoFHBase = getGuiPacket();
			if (localPacketCoFHBase != null)
				PacketHandler.sendTo(localPacketCoFHBase, (EntityPlayer)paramICrafting);
		}
	}
	
	public PacketCoFHBase getPacket() {
		PacketCoFHBase localPacketCoFHBase = super.getPacket();
	    localPacketCoFHBase.addString(this.tileName);
	    return localPacketCoFHBase;
	}
	
	public PacketCoFHBase getGuiPacket() {
		PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
		localPacketTileInfo.addByte(BoozeProps.PacketID.GUI.ordinal());
		return localPacketTileInfo;
	}
	
	public PacketCoFHBase getFluidPacket()
	{
		PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
		localPacketTileInfo.addByte(BoozeProps.PacketID.FLUID.ordinal());
		return localPacketTileInfo;
	}

	public PacketCoFHBase getModePacket()
	{
		PacketTileInfo localPacketTileInfo = PacketTileInfo.newPacket(this);
		localPacketTileInfo.addByte(BoozeProps.PacketID.MODE.ordinal());
		return localPacketTileInfo;
	}
	
	protected void handleGuiPacket(PacketCoFHBase paramPacketCoFHBase) {}

	protected void handleFluidPacket(PacketCoFHBase paramPacketCoFHBase) {}

	protected void handleModePacket(PacketCoFHBase paramPacketCoFHBase) { markChunkDirty(); }

	@Override
	public void handleTileInfoPacket(PacketCoFHBase packet, boolean arg1, EntityPlayer arg2) {
		switch (packet.getByte()) {
	    case 0:
	      handleGuiPacket(packet);
	      return;
	    case 1:
	      handleFluidPacket(packet);
	      return;
	    case 2:
	      handleModePacket(packet);
	      return;
	    }
	}

	@Override
	public void handleTilePacket(PacketCoFHBase packet, boolean arg1) {
		if (ServerHelper.isClientWorld(this.worldObj))
			this.tileName = packet.getString();
		else
		    packet.getString();
	}
}
