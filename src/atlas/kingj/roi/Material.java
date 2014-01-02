package atlas.kingj.roi;

import java.io.Serializable;

public class Material implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	double density; // g per cm3
	double thickness; // microns: x 10^-6
	
	public Material(String s, double d, double t){
		name = s; density = d; thickness = t;
	}
	
	public Material(Material other){
		this.name = other.name;
		this.density = other.density;
		this.thickness = other.thickness;
	}
	
	public String getName(){ return name; }
	public void setName(String s){ name = s; }
	
	@Override
	public String toString(){ return getName(); }

	public double getDensity(){ return density; }
	public double getThickness(){ return thickness; }
	
}
