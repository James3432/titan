package atlas.kingj.roi;

import java.io.Serializable;

/*
 *  Class to represent particular job instances, such as reel sizes and materials
 */
public class Job implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Material material;  // microns, g cm-3
	
	private KnifeType Knives; // razor or rotary
	
	// Unwind settings
	private double WebWidth;   // mm
	private double UnwindCore;  // mm
	private double UnwindLength; // m
	private double UnwindWeight; // kg
	private double UnwindDiam;  // mm
	private int UnwindType; // 0/1/2
	
	// Rewind settings per reel/set
	private int Slits; // = reels per set
	private double SlitWidth; // mm
	private double RewindCore; // mm
	private double RewindLength; // m    Lengths are always linear (ie. not multiplied by the number of slits or anything like that)
	private double RewindWeight; // kg   NB: this isn't actually the weight per reel, it's per set.
	private double RewindDiam; // mm
	private int RewindType; // 0/1/2
	//public TimeDomain Frequency;
	
	// Total job settings
	//public int ReelCount; // reels, not sets...
	private double TotalLength; // m
	private double TotalWeight; // kg
	private int TotalType; // 0/1/2
	// public double TotalTime;
	
	private double FlagRate;
	
	public boolean gsm; // whether gsm used instead of density
	
	private boolean limit_speed;
	private double speed_limit; // m/min/s
	
	private boolean valid = true; // Whether the job is valid TODO: not needed
	
	public Job(){  // null job
		this.name = "null";
		this.material = new Material("null", 1, 1);
		this.Knives = Job.KnifeType.RAZOR;
		this.WebWidth = 0;
		this.UnwindCore = 0;
		this.UnwindLength = 0;
		this.UnwindWeight = 0;
		this.UnwindDiam = 0;
		this.FlagRate = 0;
		this.Slits = 0;
		this.SlitWidth = 0;
		this.RewindCore = 0;
		this.RewindLength = 0;
		this.RewindWeight = 0;
		this.RewindDiam = 0;
		this.TotalLength = 0;
		this.TotalWeight = 0;
		this.limit_speed = false;
		this.speed_limit = 0;
		this.UnwindType = 0;
		this.RewindType = 0;
		this.TotalType = 0;
		this.valid = false;
		this.gsm = false;
	}
	
	public Job(String name){
		this.name = name;
		material = new Material("Custom", 0.92, 20);
	}
	
	public Job(FrmMain form){ // TODO: deprecated
		double Thickness = Double.parseDouble(form.txtThickness.getText()) * Consts.MICRO_TO_M;
		double Density = Double.parseDouble(form.txtDensity.getText());
		String mat_name = form.cmbMaterials.getSelectedItem().toString();
		material = new Material(mat_name, Density, Thickness);
		
		WebWidth = Double.parseDouble(form.txtWebWidth.toString());
		UnwindCore = Double.parseDouble(form.cmbUnwindCore.getSelectedItem().toString()) * Consts.MM_TO_M;
		UnwindLength = Double.parseDouble(form.txtUnwindAmount.getText());
		//UnwindDiam = Double.parseDouble(form.txtUnwindDiam.getText());
		//UnwindWeight = Double.parseDouble(form.txtUnwindWeight.getText());
		
		Slits = Integer.parseInt(form.txtSlits.getText());
		SlitWidth = Double.parseDouble(form.txtSlitWidth.getText());
		RewindCore = Double.parseDouble(form.cmbRewindCore.getSelectedItem().toString()) * Consts.MM_TO_M;
		
		limit_speed = form.chckbxLimitRunSpeed.isSelected();
		speed_limit = Double.parseDouble(form.txtLimitRunSpeed.getText()) * Consts.MIN_TO_S;		
		
		try{
			RewindLength = Double.parseDouble(form.txtRewindAmount.getText());
		}catch(Exception e1 /*NumberFormatException?*/){
			try{
				RewindWeight = Double.parseDouble(form.txtRewindAmount.getText());
			}catch(Exception e2 /*NumberFormatException?*/){
				try{
					RewindDiam = Double.parseDouble(form.txtRewindAmount.getText());
				}finally{
					RewindLength = 0; RewindWeight = 0; RewindDiam = 0;
				}
			}
		}
		
		if(UnwindLength < RewindLength){
			//System.out.println("unwind too short");
			valid = false;
		}
		
		//Frequency = TimeDomain.valueOf(form.cmbJobDomain.getSelectedItem().toString());
	}
	
	public void update(
			Material material, 
			KnifeType knives,
			double WebWidth,  
			double UnwindCore, 
			double UnwindLength,
			double UnwindWeight,
			double UnwindDiam, 
			double FlagRate,
			int Slits,
			double SlitWidth,
			double RewindCore,
			double RewindLength,
			double RewindWeight,
			double RewindDiam,
			double TotalLength,
			double TotalWeight,
			boolean limit_speed,
			double speed_limit,
			int UnwindType,
			int RewindType,
			int TotalType)
	{
		this.material = new Material(material);
		this.Knives = knives;
		this.WebWidth = WebWidth;
		this.UnwindCore = UnwindCore;
		this.UnwindLength = UnwindLength;
		this.UnwindWeight = UnwindWeight;
		this.UnwindDiam = UnwindDiam;
		this.FlagRate = FlagRate;
		this.Slits = Slits;
		this.SlitWidth = SlitWidth;
		this.RewindCore = RewindCore;
		this.RewindLength = RewindLength;
		this.RewindWeight = RewindWeight;
		this.RewindDiam = RewindDiam;
		this.TotalLength = TotalLength;
		this.TotalWeight = TotalWeight;
		this.limit_speed = limit_speed;
		this.speed_limit = speed_limit;
		this.UnwindType = UnwindType;
		this.RewindType = RewindType;
		this.TotalType = TotalType;
		
		if( 	  (material.density <= 0) 
				||(material.thickness <= 0)
				||(WebWidth <= 0)
				||(UnwindCore <= 0)
				||(UnwindLength < 0)
				||(UnwindWeight < 0)
				||(UnwindDiam < 0)
				||(FlagRate < 0)
				||(Slits <= 0)
				||(SlitWidth <= 0)
				||(RewindCore <= 0)
				||(RewindLength < 0)
				||(RewindWeight < 0)
				||(RewindDiam < 0)
				||(TotalLength < 0)
				||(TotalWeight < 0)
				||(limit_speed && (speed_limit <= 0))
				||(Slits * SlitWidth > WebWidth)
		){
			valid = false;
		}else
			valid = true;
	}
	
	public void updateQuantityTypes(int UnwindType, int RewindType, int TotalType){
		this.UnwindType = UnwindType;
		this.RewindType = RewindType;
		this.TotalType = TotalType;
	}
	
	public void setName(String name){ this.name = name; }
	public String getName(){ return name; }
	public boolean isValid(){ return valid; }
	public boolean isSpeedLimited() { return limit_speed; }
	public double  getSpeedLimit()  { return speed_limit; }
	public int getSlits(){ return Slits; }
	public Material getMaterial(){ return material; }
	public double getTotLength(){ return TotalLength; }
	public double getTotWeight(){ return TotalWeight; }
	public double getWebWidth(){ return WebWidth; }
	public double getUnwindCore(){ return UnwindCore; }
	public double getUnwindLength(){ return UnwindLength; }
	public double getUnwindWeight(){ return UnwindWeight; }
	public double getUnwindDiam(){ return UnwindDiam; }
	public double getSlitWidth(){ return SlitWidth; }
	public double getRewindCore(){ return RewindCore; }
	public double getRewindLength(){ return RewindLength; }
	public double getRewindWeight(){ return RewindWeight; }
	public double getRewindDiam(){ return RewindDiam; }
	public int getUnwindType(){ return UnwindType; }
	public int getRewindType(){ return RewindType; }
	public int getTotalType(){ return TotalType; }
	public double getFlagRate(){ return FlagRate; }
	public KnifeType getKnifeType(){ return Knives; }
	
	public double  getSpeedLimitSI()  { return speed_limit / 60; }
	public double getDensitySI(){ return material.density * 1000; }
	public double getThicknessSI(){ return material.thickness * 0.000001 ; }
	public double getDensity(){ return material.density; }
	public double getThickness(){ return material.thickness; }
	public double getTotLengthSI(){ return TotalLength; }
	public double getTotWeightSI(){ return TotalWeight; }
	public double getWebWidthSI(){ return 0.001 * WebWidth; }
	public double getUnwindCoreSI(){ return 0.001 * UnwindCore; }
	public double getUnwindLengthSI(){ return UnwindLength; }
	public double getUnwindWeightSI(){ return UnwindWeight; }
	public double getUnwindDiamSI(){ return 0.001 * UnwindDiam; }
	public double getSlitWidthSI(){ return 0.001 * SlitWidth; }
	public double getRewindCoreSI(){ return 0.001 * RewindCore; }
	public double getRewindLengthSI(){ return RewindLength; }
	public double getRewindWeightSI(){ return RewindWeight; }
	public double getRewindDiamSI(){ return 0.001 * RewindDiam; }
	
	public String toString(){ return getName(); }
	
	public enum KnifeType{
		RAZOR, ROTARY
	}
	
}
