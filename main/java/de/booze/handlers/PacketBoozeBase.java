package de.booze.handlers;

import cofh.api.tileentity.IRedstoneControl;
import cofh.api.tileentity.IRedstoneControl.ControlMode;
import cofh.api.tileentity.ISecurable;
import cofh.api.tileentity.ISecurable.AccessMode;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.lib.gui.container.IAugmentableContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.apache.logging.log4j.Logger;

import de.booze.BoozeMod;

public class PacketBoozeBase extends PacketCoFHBase
{
  public static void initialize()
  {
    PacketHandler.instance.registerPacket(PacketBoozeBase.class);
  }

  public void handlePacket(EntityPlayer paramEntityPlayer, boolean paramBoolean)
  {
    try
    {
      int i = getByte();
      int[] arrayOfInt;
      IRedstoneControl localIRedstoneControl;
      switch (i) {
      case 0:
        if ((paramEntityPlayer.openContainer instanceof ISecurable)) {
          ((ISecurable)paramEntityPlayer.openContainer).setAccess(ISecurable.AccessMode.values()[getByte()]);
        }
        return;
      case 1:
        if ((paramEntityPlayer.openContainer instanceof IAugmentableContainer)) {
          ((IAugmentableContainer)paramEntityPlayer.openContainer).setAugmentLock(getBool());
        }
        return;
      }
      BoozeMod.log.error("Unknown Packet! Internal: TEPH, ID: " + i);
    }
    catch (Exception localException) {
    	BoozeMod.log.error("Packet payload failure! Please check your config files!");
      localException.printStackTrace();
    }
  }

  public static void sendSecurityPacketToServer(ISecurable paramISecurable)
  {
    PacketHandler.sendToServer(getPacket(PacketTypes.SECURITY_UPDATE).addByte(paramISecurable.getAccess().ordinal()));
  }

  public static void sendTabAugmentPacketToServer(boolean paramBoolean)
  {
    PacketHandler.sendToServer(getPacket(PacketTypes.TAB_AUGMENT).addBool(paramBoolean));
  }

  public static PacketCoFHBase getPacket(PacketTypes paramPacketTypes)
  {
    return new PacketBoozeBase().addByte(paramPacketTypes.ordinal());
  }

  public static enum PacketTypes
  {
    SECURITY_UPDATE, TAB_AUGMENT;
  }
}