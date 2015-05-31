package de.booze.containers;

import cofh.api.tileentity.IAugmentable;
import cofh.core.block.TileCoFHBase;
import cofh.core.gui.slot.SlotAugment;
import cofh.lib.gui.slot.SlotFalseCopy;
import cofh.lib.util.helpers.AugmentHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import de.booze.handlers.PacketBoozeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBoozeBase extends Container {

	 TileCoFHBase baseTile;
	  protected Slot[] augmentSlots = new Slot[0];
	  protected boolean[] augmentStatus = new boolean[0];

	  protected boolean augmentLock = true;

	  protected boolean hasAugSlots = true;
	  protected boolean hasPlayerInvSlots = true;

	  public ContainerBoozeBase()
	  {
	  }

	  public ContainerBoozeBase(TileEntity paramTileEntity)
	  {
	    this.baseTile = ((TileCoFHBase)paramTileEntity);
	  }

	  public ContainerBoozeBase(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity)
	  {
	    this(paramInventoryPlayer, paramTileEntity, true, true);
	  }

	  public ContainerBoozeBase(InventoryPlayer paramInventoryPlayer, TileEntity paramTileEntity, boolean paramBoolean1, boolean paramBoolean2)
	  {
	    if ((paramTileEntity instanceof TileCoFHBase)) {
	      this.baseTile = ((TileCoFHBase)paramTileEntity);
	    }
	    this.hasAugSlots = paramBoolean1;
	    this.hasPlayerInvSlots = paramBoolean2;

	    if (this.hasAugSlots) {
	      addAugmentSlots();
	    }

	    if (this.hasPlayerInvSlots)
	      addPlayerInventory(paramInventoryPlayer);
	  }

	  protected void addAugmentSlots()
	  {
	    if ((this.baseTile instanceof IAugmentable)) {
	      this.augmentSlots = new Slot[((IAugmentable)this.baseTile).getAugmentSlots().length];
	      for (int i = 0; i < this.augmentSlots.length; i++)
	        this.augmentSlots[i] = addSlotToContainer(new SlotAugment((IAugmentable)this.baseTile, null, i, 0, 0));
	    }
	  }

	  protected void addPlayerInventory(InventoryPlayer paramInventoryPlayer)
	  {
	    for (int i = 0; i < 3; i++) {
	      for (int j = 0; j < 9; j++) {
	        addSlotToContainer(new Slot(paramInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	      }
	    }
	    for (int i = 0; i < 9; i++)
	      addSlotToContainer(new Slot(paramInventoryPlayer, i, 8 + i * 18, 142));
	  }

	  public boolean canInteractWith(EntityPlayer paramEntityPlayer)
	  {
	    return this.baseTile == null ? true : this.baseTile.isUseable(paramEntityPlayer);
	  }

	  public void detectAndSendChanges()
	  {
	    super.detectAndSendChanges();

	    if (this.baseTile == null) {
	      return;
	    }
	    for (int i = 0; i < this.crafters.size(); i++)
	      this.baseTile.sendGuiNetworkData(this, (ICrafting)this.crafters.get(i));
	  }

	  public void updateProgressBar(int paramInt1, int paramInt2)
	  {
	    if (this.baseTile == null) {
	      return;
	    }
	    this.baseTile.receiveGuiNetworkData(paramInt1, paramInt2);
	  }

	  public ItemStack transferStackInSlot(EntityPlayer paramEntityPlayer, int paramInt)
	  {
	    if (!this.hasPlayerInvSlots) {
	      return null;
	    }
	    ItemStack localItemStack1 = null;
	    Slot localSlot = (Slot)this.inventorySlots.get(paramInt);

	    int i = this.augmentSlots.length;
	    int j = i + 27;
	    int k = j + 9;
	    int m = k + (this.baseTile == null ? 0 : this.baseTile.getInvSlotCount());

	    if ((localSlot != null) && (localSlot.getHasStack())) {
	      ItemStack localItemStack2 = localSlot.getStack();
	      localItemStack1 = localItemStack2.copy();

	      if (paramInt < i) {
	        if (!mergeItemStack(localItemStack2, i, k, true))
	          return null;
	      }
	      else if (paramInt < k) {
	        if ((!this.augmentLock) && (i > 0) && (AugmentHelper.isAugmentItem(localItemStack2))) {
	          if (!mergeItemStack(localItemStack2, 0, i, false))
	            return null;
	        }
	        else if (!mergeItemStack(localItemStack2, k, m, false))
	          return null;
	      }
	      else if (!mergeItemStack(localItemStack2, i, k, true)) {
	        return null;
	      }
	      if (localItemStack2.stackSize <= 0)
	        localSlot.putStack((ItemStack)null);
	      else {
	        localSlot.onSlotChanged();
	      }
	      if (localItemStack2.stackSize == localItemStack1.stackSize) {
	        return null;
	      }
	    }
	    return localItemStack1;
	  }

	  public ItemStack slotClick(int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer)
	  {
	    Slot localSlot = paramInt1 < 0 ? null : (Slot)this.inventorySlots.get(paramInt1);
	    if ((localSlot instanceof SlotFalseCopy)) {
	      if (paramInt2 == 2) {
	        localSlot.putStack(null);
	        localSlot.onSlotChanged();
	      } else {
	        localSlot.putStack(paramEntityPlayer.inventory.getItemStack() == null ? null : paramEntityPlayer.inventory.getItemStack().copy());
	      }
	      return paramEntityPlayer.inventory.getItemStack();
	    }
	    return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
	  }

	  protected boolean mergeItemStack(ItemStack paramItemStack, int paramInt1, int paramInt2, boolean paramBoolean)
	  {
	    int i = 0;
	    int j = paramBoolean ? paramInt2 - 1 : paramInt1;
	    Slot localSlot;
	    ItemStack localItemStack;
	    if (paramItemStack.isStackable()) {
	      while ((paramItemStack.stackSize > 0) && (((!paramBoolean) && (j < paramInt2)) || ((paramBoolean) && (j >= paramInt1)))) {
	        localSlot = (Slot)this.inventorySlots.get(j);
	        localItemStack = localSlot.getStack();

	        if ((localSlot.isItemValid(paramItemStack)) && (ItemHelper.itemsEqualWithMetadata(paramItemStack, localItemStack, true))) {
	          int k = localItemStack.stackSize + paramItemStack.stackSize;
	          int m = Math.min(paramItemStack.getMaxStackSize(), localSlot.getSlotStackLimit());

	          if (k <= m) {
	            paramItemStack.stackSize = 0;
	            localItemStack.stackSize = k;
	            localSlot.onSlotChanged();
	            i = 1;
	          } else if (localItemStack.stackSize < m) {
	            paramItemStack.stackSize -= m - localItemStack.stackSize;
	            localItemStack.stackSize = m;
	            localSlot.onSlotChanged();
	            i = 1;
	          }
	        }
	        j += (paramBoolean ? -1 : 1);
	      }
	    }
	    if (paramItemStack.stackSize > 0) {
	      j = paramBoolean ? paramInt2 - 1 : paramInt1;

	      while (((!paramBoolean) && (j < paramInt2)) || ((paramBoolean) && (j >= paramInt1))) {
	        localSlot = (Slot)this.inventorySlots.get(j);
	        localItemStack = localSlot.getStack();

	        if ((localSlot.isItemValid(paramItemStack)) && (localItemStack == null)) {
	          localSlot.putStack(ItemHelper.cloneStack(paramItemStack, Math.min(paramItemStack.stackSize, localSlot.getSlotStackLimit())));
	          localSlot.onSlotChanged();

	          if (localSlot.getStack() == null) break;
	          paramItemStack.stackSize -= localSlot.getStack().stackSize;
	          i = 1; break;
	        }

	        j += (paramBoolean ? -1 : 1);
	      }
	    }
	    return false;
	  }

	  public void setAugmentLock(boolean paramBoolean)
	  {
	    this.augmentLock = paramBoolean;

	    if (ServerHelper.isClientWorld(this.baseTile.getWorldObj()))
	      PacketBoozeBase.sendTabAugmentPacketToServer(paramBoolean);
	  }

	  public Slot[] getAugmentSlots()
	  {
	    return this.augmentSlots;
	  }
}
