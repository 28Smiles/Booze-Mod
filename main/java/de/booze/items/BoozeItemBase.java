package de.booze.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import cofh.core.item.ItemBase;
import cofh.lib.util.helpers.StringHelper;

public class BoozeItemBase extends ItemBase {
	
	public BoozeItemBase() {
		super("booze");
	}

	public void registerIcons(IIconRegister paramIIconRegister)
	{
	    if (!this.hasTextures) {
	        return;
	    }
	    for (int i = 0; i < this.itemList.size(); i++) {
	        ItemEntry localItemEntry = (ItemEntry)this.itemMap.get(this.itemList.get(i));
	        localItemEntry.icon = paramIIconRegister.registerIcon(this.modName + ":" + getUnlocalizedName().replace(new StringBuilder().append("item.").append(this.modName).append(".").toString(), "").replace(".", "/") + "/" + StringHelper.titleCase(localItemEntry.name));
	    }
	}
}
