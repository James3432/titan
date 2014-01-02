package atlas.kingj.roi;

import java.io.Serializable;

/*
 *  Class to represent machine instances in particular configurations.
 */
public class Machine implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean custom = false;
	private CustomMachine customMach = null;
	
	public boolean isCustom(){ return custom; }
	public void setCustom(boolean f){ custom = f; }
	public CustomMachine getCustomMachine(){ return customMach; }
	public void setCustomMachine(CustomMachine c){ customMach = c; }

	// Model of machine
	public enum Model{
    	ER610("ER610"), SR9DS("SR9-DS"), SR9DT("SR9-DT"), SR800("SR800"), CUSTOM("Custom");
    	String m;
    	Model(String m){
    		this.m = m;
    	}
    	public String toString(){
    		return m;
    	}
    }
	
	// Machine max speed
	public enum Speed {
		A(450), B(550), C(1000), D(700);
		double speed;
		double orig_speed;
		Speed(int a){
			speed = a * Consts.MIN_TO_S;
			orig_speed = a;
		}
		public String toString(){
    		return Double.toString(orig_speed);
    	}
	}
	
	//public double max_rewind_rps;// = 1200. / 60; // TODO work out where to set these, and may need to be set for "other" machines
	//public double max_unwind_rps;// = 1267. / 60;
	
	// Knife setup
	public enum Knives {
		AUTO("Auto"), MANUAL("Manual");
		String s;
		Knives(String s){ this.s = s; }
		public String toString(){  return s; }
	}
	
	// Core positioning system
	public enum Corepos {
		MANUAL, LASER, SERVO
	}
	
	// Reel unloader mechanism
	public enum Unloader {
		MANUAL, PNEUMATIC, ELECTRIC
	}
	
	// Unwind motors
	public enum Unwind {
		SINGLE, DOUBLE
	}
	
	// Rewind control loop
	public enum Rewind {
		OPEN, CLOSED
	}
	
	// VARIANTS
	
	public Model model;
	public Speed speed;
	public Knives knives;
	public Corepos corepos;
	public Unloader unloader;
	public Unwind unwind;
	public Rewind rewind;
	
	// OPTIONS
	
	// Splice table
	public boolean splice_table;
	// Auto alignment guide
	public boolean alignment;
	// Roll conditioning (ovular correction)
	public boolean roll_cond;
	// Turret support (FOR SR9-DT ONLY)
	public boolean turret;
	// Auto reel removal
	public boolean autostrip;
	// Flag detection camera
	public boolean flags;
	// Auto cut-off at reels
	public boolean cutoff;
	// Auto taping of core
	public boolean tapecore;
	// Auto taping of tail (end of reel)
	public boolean tapetail;
	// 850mm rewind diameter support
	public boolean extrarewind;
	
	public double accel = 20./60;
	public double decel = 20./60;
	public double max_accel = 20./60;
	public double max_decel = 20./60;
	
	// User-given name for this machine instance
	public String name;
	
	public Machine() {
		super();
	}
	
	public Machine(String n) {
		super();
		//SetRates(Model.ER610);
		name = n;
		model = Model.ER610;
		knives = Knives.MANUAL;
		corepos = Corepos.MANUAL;
		unloader = Unloader.MANUAL;
		unwind = Unwind.SINGLE;
		rewind = Rewind.OPEN;
		speed = Speed.A;
	}
	
	public Machine(String n, Model m, String spd, String k, String c, String u, String uw, String r, boolean[] options) {
		super();
		//SetRates(m);
		name = n;
		//TODO add null cases for ""
		model = m;
		speed = (spd.equals("450") ? Speed.A : (spd.equals("550") ? Speed.B : (spd.equals("1000") ? Speed.C : null)));
		knives = (k.equals("Auto") ? Knives.AUTO : Knives.AUTO);
		corepos = (c.equals("Manual") ? Corepos.MANUAL : (c.equals("Laser") ? Corepos.LASER : (c.equals("Servo") ? Corepos.SERVO : null)));
		unloader = (u.equals("Manual") ? Unloader.MANUAL : (u.equals("Electric") ? Unloader.ELECTRIC : (u.equals("Pneumatic") ? Unloader.PNEUMATIC : null)));
		unwind = (uw.equals("Single") ? Unwind.SINGLE :  (uw.equals("Double") ? Unwind.DOUBLE : null));
		rewind = (r.equals("Open") ? Rewind.OPEN : (r.equals("Closed") ? Rewind.CLOSED : null));
		
		splice_table = options[0];
		alignment = options[1];
		roll_cond = options[2];
		turret = options[3];
		autostrip = options[4];
		flags = options[5];
		cutoff = options[6];
		tapecore = options[7];
		tapetail = options[8];
		extrarewind = options[9];
	}
	
	public Machine(String n, Model m, Speed spd, Knives k, Corepos c, Unloader u, Unwind uw, Rewind r, boolean[] options) {
		super();
		
		name = n;
		//SetRates(m);
		model = m;
		speed = spd;
		knives = k;
		corepos = c;
		unloader = u;
		unwind = uw;
		rewind = r;
		
		splice_table = options[0];
		alignment = options[1];
		roll_cond = options[2];
		turret = options[3];
		autostrip = options[4];
		flags = options[5];
		cutoff = options[6];
		tapecore = options[7];
		tapetail = options[8];
		extrarewind = options[9];
	}
	
	public Machine(String n, CustomMachine m){
		super();
		//SetRates(Model.CUSTOM);
		name = n;
		model = Model.CUSTOM;
		custom = true;
		customMach = m;
	}
	
	public double getSpeed(double width){
		if(model != Model.SR800){
			// old method
			if(custom && customMach != null)
				return customMach.speed;
			else
				return speed.speed;
		}else{
			// for SR800, speed is width dependent
			if(width >= 2.250)
				return 450/60.;
			else if(width >= 2.050)
				return 450/60.;
			else if(width >= 1.850)
				return 600/60.;
			else if(width >= 1.650)
				return 600/60.;
			else if(width >= 1.350)
				return 700/60.;
			else if(width >= 1.050)
				return 700/60.;
			else
				return 700/60.;
		}
	}
	public double getAccel(){
		if(custom && customMach != null)
			return customMach.accel;
		else
			return accel;
	}
	public double getDecel(){
		if(custom && customMach != null)
			return customMach.accel; //TODO approximation
		else
			return decel;
	}

	public double getRewindLimit(double width, double core){
		switch(model){
		case ER610: 
			if(width >= 1.650)
				return 1320/60.;
			else
				return 1620/60.;
		case SR9DS: 
			if(width >= 2.250)
				return 800/60.;
			else
				return (core > 0.125 ? 1000/60. : 1100/60.);
		case SR9DT: 
			if(width >= 2.250)
				return 800/60.;
			else
				return (core > 0.125 ? 1000/60. : 1100/60.);
		case SR800: 
			if(width >= 2.250)
				return 700/60.;
			else if(width >= 2.050)
				return 800/60.;
			else if(width >= 1.850)
				return 950/60.;
			else if(width >= 1.650)
				return 1150/60.;
			else if(width >= 1.350)
				return 1550/60.;
			else if(width >= 1.050)
				return 1683/60.;
			else
				return 700/60.;
		case CUSTOM: 
			//return 1000. / 60;
			return customMach.rewindLimit;
		default: // assume custom:
			return 1000. / 60;
		}
	}
	public double getUnwindLimit(double width, double core){
		switch(model){
		case ER610: 
			return 10000/60.; // no limit
		case SR9DS: 
			return 1262/60.;
		case SR9DT: 
			return 1262/60.;
		case SR800: 
			return 750/60.;
		case CUSTOM: 
			//return 1000. / 60;
			return customMach.unwindLimit;
		default: // assume custom:
			return 1000. / 60;
		}
	}
	
	// Set the default shaft & accel limits, based on model type
	/*private void SetRates(Model m){
		// TODO: limits depend on width/weight of the rolls in practice. Below are averages for common setups
		
		// TODO redundant
		switch(m){
		case ER610: 
			max_rewind_rps = 1500. / 60;
			max_unwind_rps = 10000. / 60; // braked, no limit
			break;
		case SR9DS: 
			max_rewind_rps = 1100. / 60;
			max_unwind_rps = 1262. / 60;
			break;
		case SR9DT: 
			max_rewind_rps = 1100. / 60;
			max_unwind_rps = 1262. / 60;
			break;
		case SR800: 
			max_rewind_rps = 1500. / 60;
			max_unwind_rps = 750. / 60;
			break;
		case CUSTOM: 
			max_rewind_rps = 1000. / 60;
			max_unwind_rps = 1000. / 60;
			break;
		default: // assume custom:
			max_rewind_rps = 1000. / 60;
			max_unwind_rps = 1000. / 60;
			break;
		}
		
		accel = 20./60;
		decel = 20./60;
		
	}*/
	
	public void update(String n, Model m, String spd, String k, String c, String u, String uw, String r, boolean[] options) {
		name = n;
		//TODO add null cases for ""
		model = m;
		//SetRates(m);
		speed = (spd.equals("450") ? Speed.A : (spd.equals("550") ? Speed.B : (spd.equals("1000") ? Speed.C : (spd.equals("700") ? Speed.D : Speed.A))));
		knives = (k.equals("Auto") ? Knives.AUTO : Knives.MANUAL);
		corepos = (c.equals("Manual") ? Corepos.MANUAL : (c.equals("Laser") ? Corepos.LASER : (c.equals("Servo") ? Corepos.SERVO : Corepos.MANUAL)));
		unloader = (u.equals("Manual") ? Unloader.MANUAL : (u.equals("Electric") ? Unloader.ELECTRIC : (u.equals("Pneumatic") ? Unloader.PNEUMATIC : Unloader.MANUAL)));
		unwind = (uw.equals("Single") ? Unwind.SINGLE :  (uw.equals("Double") ? Unwind.DOUBLE : Unwind.SINGLE));
		rewind = (r.equals("Open") ? Rewind.OPEN : (r.equals("Closed") ? Rewind.CLOSED : Rewind.OPEN));
		
		splice_table = options[0];
		alignment = options[1];
		roll_cond = options[2];
		turret = options[3];
		autostrip = options[4];
		flags = options[5];
		cutoff = options[6];
		tapecore = options[7];
		tapetail = options[8];
		extrarewind = options[9];
	}
	
	// Provide a toString() method so we can display machines neatly in a list
	@Override
	public String toString(){
		String mod = "";
		switch (model) {
			case ER610: mod = "ER610";
				break;
			case SR9DS: mod = "SR9-DS";
				break;
			case SR9DT: mod = "SR9-DT";
				break;
			case SR800: mod = "SR800";
				break;
			case CUSTOM: mod = "Custom";
				break;
			default: mod = "Custom";
				break;
		}
		return mod + ": " + name;
	}
	
	public class CustomMachine implements Serializable {
		
		private static final long serialVersionUID = 1L;
		String descriptor;
		double speed;
		double accel;
		double change_mother;
		double change_job;
		double change_diam;
		double splice;
		double change_set;
		double rewindLimit;
		double unwindLimit;
		                    //  duplexrewind turret  brakedunwind  splicetab autocut  autotape  corepos  autostrip unloadboom
		boolean[] options = {     false,     false,     false,      false,    false,   false,    false,   false,    false    };
		
		RewindType rewind_type;
		
		public CustomMachine(){
			descriptor = "Custom Machine";
			speed = Consts.CUST_MACHINE_SPEED;
			accel = Consts.CUST_MACHINE_ACCEL;
			change_mother = Consts.CUST_MACHINE_CHANGEMO;
			change_job = Consts.CUST_MACHINE_CHANGEJOB;
			change_diam = Consts.CUST_MACHINE_CHANGEDIAM;
			splice = Consts.CUST_MACHINE_SPLICE;
			change_set = Consts.CUST_MACHINE_CHANGESET;
			rewind_type = RewindType.QUICKLOCK;
			rewindLimit = 1000. / 60;
			unwindLimit = 1000. / 60;
		}
	}
	
	public enum RewindType {
		QUICKLOCK, LOCKCORE, DIFFERENTIAL
	}
	
}