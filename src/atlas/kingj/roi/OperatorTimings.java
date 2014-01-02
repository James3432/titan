package atlas.kingj.roi;

import java.io.Serializable;

import atlas.kingj.roi.Job.KnifeType;
import atlas.kingj.roi.Machine.CustomMachine;

public class OperatorTimings implements Serializable {
	
	/*   
	 * 
	 * Measured operator timings for each machine, with and without all options enabled.
	 * 
	 * All values are public to allow overriding from environment settings.
	 * 
	 * All times in seconds. Fractions are permitted.
	 *
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int length = 13;
	public static int optnslength = 18;
	
	// TODO as of 19/08/13, only sr9dt timings were actually measured
	
	// Options with a * mean that the OPTNS timing is per set, and not per reel. Hence the 'upgrade' timing may appear larger than the 'manual' timing on this form.
	
								   //    0           1             2          3         4           5            6                7            8            9              10            11        			12          13             14    15           16           17
	                               // SetMachine  ChangeMother  AlignMother  KnifePos  FlagSplice  RewindCut*  RewindTapeTail  RewindTapeCore RewindLoad  RewindUnload* ChangeDiam    SetCycle(turrets) RewindAlign    align-servo*  flagcam rot knife  rewindclamp unwindclamp
	public double[] ER610_BASIC = {      20,         240,          150,         20,       300,         5,          5,             10,           3,          10,        720,		    	0,         			10      									 						  };
	public double[] ER610_OPTNS = {      20,         240,          120,         20,       180,         10,         5,             10,           3,          60,        720,		    	0,      		    5,     		 12,			30,		 2,		1470. / 60,	10000. / 60	  };
	
	public double[] SR9DS_BASIC = {      20,         240,          150,         20,       300,         5,          5,             10,           3,          10,        720,		    	0,      		    10      	  														  };
	public double[] SR9DS_OPTNS = {      20,         240,          120,         9,        180,         10,         0,             0,            3,          60,        720,		    	0,      		    5,      	 12, 			30,		 2,		1000. / 60, 1262. / 60	  };
	
	public double[] SR9DT_BASIC = {      20,         240,          150,         20,       300,         5,          0,             0,            0,          0,         1440,			60,     		    0      	  															  };
	public double[] SR9DT_OPTNS = {      20,         240,          120,         9,        180,         10,         0,             0,            0,          0,         1440,			35,     		    0,      	 0, 			30,		 2,		1000. / 60, 1262. / 60	  };
	
	public double[] SR800_BASIC = {      20,         240,          150,         20,       300,         5,          5,             5,            3,          10,        720,		    	0,					10     																  };
	public double[] SR800_OPTNS = {      20,         240,          120,         9,        180,         10,         0,             0,            3,          60,        720,		    	0,					5,      	 12, 			30,		 2,		1100. / 60, 750. / 60	  };
	
	public double[] CUSTOM_BASIC = {     40,         300,          150,         20,       300,         5,          5,             5,            3,          10,        720,		    	0,					10     																  };
	public double[] CUSTOM_OPTNS = {     40,         300,          120,         9,        200,         10,         0,             0,            3,          60,        720,		    	0,					5,     		 12, 			30,		 2,		1000. / 60, 1000. / 60	  };
	
	
	// TODO Backup default timings: these need to be manually kept up-to-date
	
	   //    0           1             2          3         4           5            6                7            8            9              10            11        			12          13             14    15           16           17
    // SetMachine  ChangeMother  AlignMother  KnifePos  FlagSplice  RewindCut*  RewindTapeTail  RewindTapeCore RewindLoad  RewindUnload* ChangeDiam    SetCycle(turrets) RewindAlign    align-servo*  flagcam rot knife  rewindclamp unwindclamp
	public double[] ER610_BASIC_BACKUP = {      20,         240,          150,         20,       300,         5,          5,             10,           3,          10,        720,		    	0,         			10      									 						  };
	public double[] ER610_OPTNS_BACKUP = {      20,         240,          120,         20,       180,         10,         5,             10,           3,          60,        720,		    	0,      		    5,     		 12,			30,		 2,		1470. / 60,	10000. / 60	  };
	
	public double[] SR9DS_BASIC_BACKUP = {      20,         240,          150,         20,       300,         5,          5,             10,           3,          10,        720,		    	0,      		    10      	  														  };
	public double[] SR9DS_OPTNS_BACKUP = {      20,         240,          120,         9,        180,         10,         0,             0,            3,          60,        720,		    	0,      		    5,      	 12, 			30,		 2,		1000. / 60, 1262. / 60	  };
	
	public double[] SR9DT_BASIC_BACKUP = {      20,         240,          150,         20,       300,         5,          0,             0,            0,          0,         1440,			60,     		    0      	  															  };
	public double[] SR9DT_OPTNS_BACKUP = {      20,         240,          120,         9,        180,         10,         0,             0,            0,          0,         1440,			35,     		    0,      	 0, 			30,		 2,		1000. / 60, 1262. / 60	  };
	
	public double[] SR800_BASIC_BACKUP = {      20,         240,          150,         20,       300,         5,          5,             5,            3,          10,        720,		    	0,					10     																  };
	public double[] SR800_OPTNS_BACKUP = {      20,         240,          120,         9,        180,         10,         0,             0,            3,          60,        720,		    	0,					5,      	 12, 			30,		 2,		1100. / 60, 750. / 60	  };
	
	public double[] CUSTOM_BASIC_BACKUP = {     40,         300,          150,         20,       300,         5,          5,             5,            3,          10,        720,		    	0,					10     																  };
	public double[] CUSTOM_OPTNS_BACKUP = {     40,         300,          120,         9,        200,         10,         0,             0,            3,          60,        720,		    	0,					5,     		 12, 			30,		 2,		1000. / 60, 1000. / 60	  };

	
	// Operations required for different types of changeover  (by index in above table)
	public int[] START =         { 0, 1, 2, 3, 7, 8, 12                   };
	public int[] REEL_CHANGE =   { 5, 6, 7, 8, 9, 11, 12                  };
	public int[] MOTHER_CHANGE = { 1, 2                                   };
	public int[] JOB_CHANGE =    { 0, 3, 1, 2, 5, 6, 7, 8, 9, 11, 12      };
	public int[] FLAG_SPLICE =   { 4                                      };
	public int[] FINISH =        { 5, 6, 9, 11                            };
	
	// Fixed, measured timings
	//public static double efficiency = 0.95;
	   							
								   //    0           1             2          3         4           5            6                7            8            9             10           11
    							   // TotSetChange  ChangeMother  AlignMother  KnifePos  FlagSplice  Rewin
	//public static double[] SR9DT = {     35                };
	// TODO SR9DS set change should be around 2.50, but obtain by adding up options in the above
	
	public void ResetTimes(Machine.Model model, boolean basic, boolean all){
		if(model == Machine.Model.ER610 || all){
			if(basic)
				ER610_BASIC = ER610_BASIC_BACKUP.clone();
			else
				ER610_OPTNS = ER610_OPTNS_BACKUP.clone();
		}
		if(model == Machine.Model.SR9DS || all){
			if(basic)
				SR9DS_BASIC = SR9DS_BASIC_BACKUP.clone();
			else
				SR9DS_OPTNS = SR9DS_OPTNS_BACKUP.clone();
		}
		if(model == Machine.Model.SR9DT || all){
			if(basic)
				SR9DT_BASIC = SR9DT_BASIC_BACKUP.clone();
			else
				SR9DT_OPTNS = SR9DT_OPTNS_BACKUP.clone();
		}
		if(model == Machine.Model.SR800 || all){
			if(basic)
				SR800_BASIC = SR800_BASIC_BACKUP.clone();
			else
				SR800_OPTNS = SR800_OPTNS_BACKUP.clone();
		}
		if(model == Machine.Model.CUSTOM || all){
			if(basic)
				CUSTOM_BASIC = CUSTOM_BASIC_BACKUP.clone();
			else
				CUSTOM_OPTNS = CUSTOM_OPTNS_BACKUP.clone();
		}
	}
	
	public double getCustomMachineTime(Machine machine, Job j, int type){
		double time = 0.;
		CustomMachine m = machine.getCustomMachine();
		
		/* TODO
		                    //  AutoTape AutoTapeCore DualTurret AutoCut  CorePos  AutoStrip  
		boolean[] options = {     false,    false,      false,    false,    false,   false    };
		
		RewindType rewind_type; (QUICKCORE LOCKCORE DIFF  ???????
		*/
		
		switch(type){
		case 1: // job change
			time += m.change_job;
			break;
		case 2: // set change
			time += m.change_set;
			break;
		case 3: // mother change
			time += m.change_mother;
			break;
		case 4: // splice
			time += m.splice;
			break;
		case 5: // job change + diam change
			time += /*m.change_job +*/ m.change_diam;
			break;
		case 6: // start + finish time
			time += m.change_job;
			break;
		default:
			time += m.change_set;
			break;
		}
		return time;
	}
	
	public double getJobChangeTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
				time = getCustomMachineTime(machine, job, 1);
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<JOB_CHANGE.length; ++i){
				time += times[JOB_CHANGE[i]];
			}
		}
		return time;
	}
	
	public double getDiamChangeTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 5);
		}else{
			double[] times = getBasicTimes(machine, job);
			time += times[10];
		}
		return time;
	}
	
	public double getSetChangeTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 2);
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<REEL_CHANGE.length; ++i){
				time += times[REEL_CHANGE[i]];
			}
			//System.out.println("set: "+time);
		}
		return time;
	}
	
	public double getMotherChangeTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 3);
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<MOTHER_CHANGE.length; ++i){
				time += times[MOTHER_CHANGE[i]];
			}
			//System.out.println("mother: "+time);
		}
		return time;
	}
	
	public double getSpliceTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 4);
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<FLAG_SPLICE.length; ++i){
				time += times[FLAG_SPLICE[i]];
			}
			//System.out.println("splice: "+time);
		}
		return time;
	}
	
	public double getStartTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 6) / 2;
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<START.length; ++i){
				time += times[START[i]];
			}
		}
		return time;
	}
	public double getStopTime(Machine machine, Job job){
		double time = 0.;
		if(machine.isCustom()){
			time = getCustomMachineTime(machine, job, 6) / 2;
		}else{
			double[] times = getBasicTimes(machine, job);
			for(int i=0; i<FINISH.length; ++i){
				time += times[FINISH[i]];
			}
		}
		return time;
	}
	
	public double[] getBasicTimes(Machine machine, Job job){
		Machine.Model mach = machine.model;
		double[] times = null;
		switch(mach){
			case ER610: times = BuildTimes(machine, job, ER610_BASIC, ER610_OPTNS); break;
			case SR9DS: times = BuildTimes(machine, job, SR9DS_BASIC, SR9DS_OPTNS); break;
			case SR9DT: times = BuildTimes(machine, job, SR9DT_BASIC, SR9DT_OPTNS); break;
			case SR800: times = BuildTimes(machine, job, SR800_BASIC, SR800_OPTNS); break;
			case CUSTOM: times = BuildTimes(machine, job, CUSTOM_BASIC, CUSTOM_OPTNS); break;
		}
		return times;
	}
	
	private double[] BuildTimes(Machine m, Job j, double[] basic, double[] optns){
		double[] result = new double[basic.length];
		
		int slits = 0;
		slits = j.getSlits();
		int cores = slits;
		int knives = slits + 1;
		
		//SetMachine  
		result[0] = basic[0];
		
		//ChangeMother  
		result[1] = basic[1];
		
		//AlignMother  
		if(m.alignment)
			result[2] = optns[2];
		else
			result[2] = basic[2];
		
		//KnifePos  
		if(m.knives == Machine.Knives.AUTO)
			result[3] = optns[3] * knives;
		if(m.knives == Machine.Knives.MANUAL){
			result[3] = basic[3] * knives;
			if(j.getKnifeType() == KnifeType.ROTARY)
				result[3] = result[3] * optns[15];
		}
		
		//FlagSplice 
		if(m.splice_table)
			result[4] = optns[4];
		else
			result[4] = basic[4];
		if(m.flags)
			result[4] = result[4] - optns[14];
		
		//RewindCut  
		if(m.cutoff)
			result[5] = optns[5];
		else
			result[5] = basic[5] * cores;
		
		//RewindTapeTail  
		if(m.tapetail)
			result[6] = optns[6];
		else
			result[6] = basic[6] * cores;
		
		//RewindTapeCore  
		if(m.tapecore)
			result[7] = optns[7];
		else
			result[7] = basic[7] * cores;
				
		//RewindLoad  
		result[8] = basic[8] * cores;
				
		//RewindUnload  
		if(m.unloader==Machine.Unloader.ELECTRIC || m.autostrip)
			result[9] = optns[9];
		else
			result[9] = basic[9] * cores;
		
		//ChangeDiam
		result[10] = basic[10];
		
		//TurretSwitch
		if(m.turret)
			result[11] = optns[11];
		else
			result[11] = basic[11];

		//RewindAlign
		if(m.corepos == Machine.Corepos.SERVO)
			result[12] = optns[13] * cores;
		if(m.corepos == Machine.Corepos.LASER)
			result[12] = optns[12] * cores;
		if(m.corepos == Machine.Corepos.MANUAL)
			result[12] = basic[12] * cores;
		
		return result;
	}
	
	public double getRewindSclamp(Machine m, Job j){
		double val = 0.;
		switch(m.model){
			case ER610: val = ER610_OPTNS[16]; break;
			case SR9DS: val = SR9DS_OPTNS[16]; break;
			case SR9DT: val = SR9DT_OPTNS[16]; break;
			case SR800: val = SR800_OPTNS[16]; break;
			case CUSTOM: val = CUSTOM_OPTNS[16]; break;
			default: val = CUSTOM_OPTNS[16]; break;
		}
		double lim = m.getRewindLimit(j.getWebWidthSI(), j.getRewindCoreSI());
		return Math.min(val, lim);
	}
	public double getUnwindSclamp(Machine m, Job j){
		double val = 0.;
		switch(m.model){
			case ER610: val = ER610_OPTNS[17]; break;
			case SR9DS: val = SR9DS_OPTNS[17]; break;
			case SR9DT: val = SR9DT_OPTNS[17]; break;
			case SR800: val = SR800_OPTNS[17]; break;
			case CUSTOM: val = CUSTOM_OPTNS[17]; break;
			default: val = CUSTOM_OPTNS[17]; break;
		}
		double lim = m.getUnwindLimit(j.getWebWidthSI(), j.getUnwindCoreSI());
		return Math.min(val, lim);
	}
	
}
