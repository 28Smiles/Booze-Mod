package de.booze.xml;

import de.booze.api.data.ItemType;

public class Mash {
	
	private String id;
	private String dependencies;
	private int density;
	private int viscosity;
	
	private String fruid_id;
	private ItemType fruid_type;
	private int mash;
	
	private String residue_damp_id;
	private ItemType residue_damp_type;
	private int residue_damp_mash;
	
	private String residue_id;
	private ItemType residue_type;
	
	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsFull() {
		if(id != null && dependencies != null && fruid_id != null && fruid_type != null && residue_damp_id != null && residue_damp_type != null && residue_id != null && residue_type != null)
			return true;
		return false;
	}

	public String getDependencies() {
		return dependencies;
	}

	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public int getViscosity() {
		return viscosity;
	}

	public void setViscosity(int viscosity) {
		this.viscosity = viscosity;
	}

	public String getFruid_id() {
		return fruid_id;
	}

	public void setFruid_id(String fruid_id) {
		this.fruid_id = fruid_id;
	}

	public ItemType getFruid_type() {
		return fruid_type;
	}

	public void setFruid_type(ItemType fruid_type) {
		this.fruid_type = fruid_type;
	}

	public int getMash() {
		return mash;
	}

	public void setMash(int mash) {
		this.mash = mash;
	}

	public String getResidue_damp_id() {
		return residue_damp_id;
	}

	public void setResidue_damp_id(String residue_damp_id) {
		this.residue_damp_id = residue_damp_id;
	}

	public ItemType getResidue_damp_type() {
		return residue_damp_type;
	}

	public void setResidue_damp_type(ItemType residue_damp_type) {
		this.residue_damp_type = residue_damp_type;
	}

	public int getResidue_damp_mash() {
		return residue_damp_mash;
	}

	public void setResidue_damp_mash(int residue_damp_mash) {
		this.residue_damp_mash = residue_damp_mash;
	}

	public String getResidue_id() {
		return residue_id;
	}

	public void setResidue_id(String residue_id) {
		this.residue_id = residue_id;
	}

	public ItemType getResidue_type() {
		return residue_type;
	}

	public void setResidue_type(ItemType residue_type) {
		this.residue_type = residue_type;
	}

	public String getId() {
		return id;
	}
}
