package de.booze.items;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import java.util.List;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMug extends BoozeItemBase {
	
	TMap<Integer, PotionEffect[]> effects = new THashMap<Integer, PotionEffect[]>();
	
	public ItemMug() {
		super();
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack arg0) {
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onEaten(ItemStack arg0, World arg1, EntityPlayer arg2) {
		arg0.stackSize -= 1;
		PotionEffect[] effect = effects.get(ItemHelper.getItemDamage(arg0));
		if(effect != null)
			for(int i = 0; i < effect.length; i++)
				arg2.addPotionEffect(effect[i]);
		arg1.playSoundAtEntity(arg2, "random.burp", 0.5F, arg1.rand.nextFloat() * 0.1F + 0.9F);
		return arg0;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack arg0, World arg1, EntityPlayer arg2) {
		return onEaten(arg0, arg1, arg2);
	}
	
	public void addPotionEffectForItem(int meta, PotionEffect potion) {
		if(effects.containsKey(meta)) {
			PotionEffect[] effect = effects.get(meta);
			if(effect != null) {
				PotionEffect[] newArray = new PotionEffect[effect.length + 1];
				for(int i = 0; i < effect.length; i++)
					newArray[i] = effect[i];
				newArray[effect.length] = potion;
				effects.put(meta, newArray);
			} else {
				effects.put(meta, new PotionEffect[] { potion });
			}
		} else {
			effects.put(meta, new PotionEffect[] { potion });
		}
	}
}
