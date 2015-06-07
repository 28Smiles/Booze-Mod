package de.booze.api.data;

import net.minecraft.item.Item;

public class ItemDamage {
	
	public Item item;
	public int damage;
	
	public ItemDamage(Item item, int damage) {
		this.item = item;
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Item getItem() {
		return item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ItemDamage) {
			ItemDamage idmg = (ItemDamage) obj;
			if(this.damage == idmg.damage)
				if(this.item.equals(idmg.item))
					return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return item.toString() + damage;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public ItemDamage clone() {
		return new ItemDamage(item, damage);
	}
}