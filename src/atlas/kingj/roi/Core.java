package atlas.kingj.roi;

/*
 *  Core methods providing the machine production run model for the Titan ROI Calculator
 *  
 *  Unless stated otherwise, default units are:
 *  		Length | mm
 *          Mass   | g
 *          Time   | s
 *  
 */
public final class Core {
	
	/*
	 *  Imperial/Metric conversions
	 *  
	 *  There are 12 inches in a foot, and 1 in = 2.54 cm
	 */
	public static double InToMM(double inch){
		return 25.4 * inch;
	}
	public static double MMToIn(double mm){
		return mm / 25.4;
	}
	public static double FtToMM(double ft){
		return FtToM(ft) * 1000;
	}
	public static double MMToFt(double mm){
		return MToFt(mm / 1000);
	}
	public static double TonneToKg(double t){
		return t * 1000.;
	}
	public static double TonToKg(double t){
		return t * 907.;
	}
	public static double KgToTonne(double k){
		return k / 1000.;
	}
	public static double KgToTon(double k){
		return k / 907.;
	}
	public static double KgToLbs(double k){
		return KgToTon(k) * 2000;
	}
	public static double LbsToKg(double l){
		return TonToKg(l / 2000);
	}
	public static double TonneToTon(double t){
		return 1000 * t / 907;
	}
	public static double TonToTonne(double t){
		return 907 * t / 1000;
	}
	public static double MicroToMil(double m){
		return m / 25.4; //1000 * MMToIn(m / 1000);
	}
	public static double MilToMicro(double m){
		return m * 25.4;
	}
	public static double MToFt(double m){
		return m * 1000 / (12*25.4);
	}
	public static double FtToM(double m){
		return m * 12 * 25.4 / 1000;
	}
	public static double FtToMi(double f){
		return f / 5280;
	}
	public static double MiToFt(double f){
		return f * 5280;
	}
	public static double KmToMi(double k){
		return FtToMi(MToFt(k * 1000));
	}
	
	/*
	 *  Geometric conversions
	 */
	public static double DiamToCirc(double diam){
		return Math.PI * diam;
	}
	public static double DiamToArea(double diam){
		return Math.PI * 0.25 * diam * diam;
	}
	public static double DiamToLength(double diam, double thickness, double core){
		return (thickness == 0 ? 0 : ( DiamToArea(diam) - DiamToArea(core) ) / thickness);
	}
	public static double LengthToDiam(double length, double thickness, double core){
		return Math.sqrt( (length * thickness) / (Math.PI / 4) + core * core );
	}
	public static double LengthToWeight(double length, double thickness, double width, double density){
		return density * length * thickness * width;
	}
	public static double WeightToLength(double weight, double thickness, double width, double density){
		return ((thickness == 0) || (density == 0) || (width == 0) ? 0 : weight / (thickness * width * density));
	}
	public static double DensityToThickness(double density, double length, double width, double weight){
		return ((length == 0) || (density == 0) || (width == 0) ? 0 : (weight / (density * length * width)));
	}
	public static double ThicknessToDensity(double length, double width, double weight, double thickness){
		return ((thickness == 0) || (length == 0) || (width == 0) ? 0 : weight / (length * width * thickness));
	}
	
	
	/*
	 *  Machine conversions
	 */
	public static double SyncDiam(double MaxSpeed, double MaxRps){
		return (MaxRps == 0 ? Double.POSITIVE_INFINITY : MaxSpeed / (Math.PI * MaxRps));
	}
	
	/*
	 *  Production
	 */
	public static double Rate(double product, double time){
		return (time == 0 ? 0 : product / time);
	}
	public static double Product(double rate, double time){
		return rate * time;
	}
	
	/*
	 *  Timing model v1.
	 *  
	 *  
	 *            ______________________
	 *           /                      \
	 *   Speed  /                        \
	 *         /                          \
	 *        /                            \
	 *       /                              \
	 *                    Time
	 *                        
	 *       |----|   This is t1, during which l1 metres of material run
	 *       
	 *       And correspondingly, this: |----|  is t2, using l1 metres of material
	 *       
	 *       This: |--------------------| , the rest of the time, is t3 (machine at full speed).
	 *       
	 *       
	 */

	// The total 'time' value is the final x-value in the array returned
	public static DataPoint[] MachineRunTime(double length, double vmax, double accel, double decel){
		// Assumes limited velocity has been passed in.
		// This ensures that t3 >= 0 and l1 + l2 <= length  (if V was not limited, l1 + l2 could exceed length as V would never be reached)
		
		if((length==0)||(vmax==0)||(accel==0)||(decel==0))
			return null;
		
		DataPoint[] data = new DataPoint[4];
		
		data[0] = new DataPoint(0, 0);
		
		// Ramp-up time
		double t1 = vmax / accel;
		// Ramp-down time
		double t2 = vmax / decel;
		data[1] = new DataPoint(t1, vmax);
		
		// Material run during t1
		double l1 = vmax * vmax / (2 * accel);
		
		// Material run during t2
		double l2 = vmax * vmax / (2 * decel);
		
		// Linear run time
		double t3 = (length - l1 - l2) / vmax;
		
		data[2] = new DataPoint(t1 + t3, vmax);
		
		// Total is sum of parts t1 - t3
		double time = t1 + t2 + t3;
		
		data[3] = new DataPoint(time, 0);
		
		return data;
	}
	
	/*
	 *  The maximum velocity may be set either by:
	 *  	 - The machine maximum
	 *  	 - The operator/user's choice
	 *       - A limit based on the length of reel and acceleration (because for short lengths we cannot reach Vmax in time)
	 *       
	 *   The effective maximum velocity is the minimum of whichever of these are relevant
	 */
	public static double getMaxVelocity(double vMach, double vLimit){
		return Math.min(vMach, vLimit);
	}
	public static double getMaxVelocity(double vMach, double vUser, double vLimit){
		return Math.min(vLimit, Math.min(vMach, vUser));
	}
	
	/*
	 * Get the highest speed a run can reach for a given length of reel
	 */
	public static double getLimitingVelocity(double length, double accel, double decel){
		if((accel<=0)||(decel<=0))
			return 0;
		return Math.sqrt((2 * accel * decel * length) / (accel + decel));
	}
	
	
	
	/*
	 * 
	 * 
	 *  Timing model v2.
	 *  
	 *  Introduces speed limits due to max shaft rpm, and depleted mother rolls (TODO)
	 * 
	 *   
	 */
	public static DataPoint[] MachineRunTimeV2(double length, double vmax, double accel, double decel, double max_rewind_rps, double max_unwind_rps, double thickness, double rewind_core, double unwind_core, double unwind_length){
		// TODO: does passing a limited velocity in work as before, or are there additional considerations?
		// The problem may just be the way vLimit is calculated: everything in This function should be fine though.
		
		if((length==0)||(vmax==0)||(accel==0)||(decel==0)||(max_rewind_rps==0)||(max_unwind_rps==0)||(thickness==0)||(unwind_length==0))
			return null;
		
		DataPoint[] data = new DataPoint[6];
		data[0] = new DataPoint(0, 0);
		
		// if a >= accel no limiting occurs
		
		// Ramp-up time may now be limited by maxrps
		double a = getShaftLimitedAccel(max_rewind_rps, thickness);
		// t1 is now the time until rps limiting
		double t1 = Math.PI * max_rewind_rps * rewind_core / Math.sqrt(accel * (accel - a)); 
		double t2;
		if(vmax / accel <= t1 || a >= accel) // TODO: check direction of inequality
			t2 = vmax / accel; // no limiting
		else
			t2 = t1 + (vmax - accel * t1) / a;  // limiting

		//double unwind_diam_before = LengthToDiam(unwind_length, thickness, unwind_core);
		double unwind_diam_after = LengthToDiam(unwind_length - length, thickness, unwind_core);
		
		// TODO: this is WRONG! It depends on the INITIAL size of the unwind roll. It's not symmetrical to the rewind case after all. EDIT: unwind_core replaced by unwind_diam_after
		// Ramp-down time may now be limited by maxrps
		double d = getShaftLimitedAccel(max_unwind_rps, thickness);
		// t3 is now the time until rps limiting
		double t3 = Math.PI * max_unwind_rps * unwind_diam_after / Math.sqrt(decel * (decel - d));
		double t4;
		if(vmax / decel <= t3 || d >= decel)
			t4 = vmax / decel; // no limiting
		else
			t4 = t3 + (vmax - decel * t3) / d;  // limiting
		
		//TODO 2 cases presumably, limited or not
		// Material run during t1
		double l1;
		if(vmax / accel <= t1 || a >= accel){
			// no limit
			l1 = vmax * vmax / (2 * accel);
		}else{
			// limit
			l1 = accel * t1 * t1 / 2 + (t2 - t1) * (vmax + accel * t1) / 2;
		}
		
		//TODO units problems already!
		// Material run during t2
		double l2;
		if(vmax / decel <= t3 || d >= decel){
			// no limit
			l2 = vmax * vmax / (2 * decel);
		}else{
			// limit
			l2 = decel * t3 * t3 / 2 + (t4 - t3) * (vmax + decel * t3) / 2;
		}
		
		// Linear run time
		double t5 = (length - l1 - l2) / vmax;
		if(t5 < 0){ 
			if(t5 < - Consts.DOUBLE_ERROR_MARGIN)
				t5=0; //TODO TODO System.out.println("uh oh, less than 0m run length"); 
			t5 = 0;
		}
		
		// Total is sum of parts: start(t2), middle(t5), end(t4)
		double time = t2 + t4 + t5;
		
		if(vmax / accel <= t1 || a >= accel)
			data[1] = new DataPoint(0,0); // no limiting
		else
			data[1] = new DataPoint(t1, t1 * accel);
		data[2] = new DataPoint(t2, vmax);
		data[3] = new DataPoint(t2 + t5, vmax);
		if(vmax / decel <= t3 || d >= decel)
			data[4] = data[3]; // no limiting
		else
			data[4] = new DataPoint(time-t3, t3*decel);
		
		data[5] = new DataPoint(time, 0);
		return data;
		
	}
	
	/*
	 *  The acceleration (constant) in linear speed of a shaft running at constant (maxrps) rotational speed
	 */
	public static double getShaftLimitedAccel(double maxrps, double thickness){
		return 2 * Math.PI * thickness * maxrps * maxrps;
	}
	
	/*
	 * Get the highest speed a run can reach for a given length of reel
	 */
	public static double getLimitingVelocityV2(double length, double vmax, double accel, double decel, double max_rewind_rps, double max_unwind_rps, double thickness, double rewind_core, double unwind_core, double unwind_length){
		// TODO: quite a bit of redundancy here, should really calculate within machineRunTimev2
		
		/*
		 *  The idea is to calculate the maximum speed the machine can possibly reach by considering the total length. 
		 *  The length run over each phase is v^2 / (2*a)  where v is the acceleration delta and a the acceleration.
		 *  
		 *  Unfortunately, this ends up as a messy quadratic to solve, with several edge cases.
		 */
		
		// Record whether we get limited by the rewind or unwind shafts respectively
		//boolean start_lim = false;
		//boolean end_lim = false;
		
		if((length==0)||(vmax==0)||(accel==0)||(decel==0)||(max_rewind_rps==0)||(max_unwind_rps==0)||(thickness==0)||(unwind_length==0))
			return 0;
		
		double a = getShaftLimitedAccel(max_rewind_rps, thickness);
		double d = getShaftLimitedAccel(max_unwind_rps, thickness);
		
		double unwind_diam_after = LengthToDiam(unwind_length - length, thickness, unwind_core);
		
		double t1 = Math.PI * max_rewind_rps * rewind_core / Math.sqrt(accel * (accel - a)); 
		if(vmax / accel <= t1 || a >= accel){ // no limiting
			//start_lim = false;
			t1 = vmax / accel;
		}
		//else
			//start_lim = true;
		
		
		double t2 = Math.PI * max_unwind_rps * unwind_diam_after / Math.sqrt(decel * (decel - d));
		if(vmax / decel <= t2 || d >= decel){ // no limiting
			//end_lim = false;
			t2 = vmax / decel;
		}
		//else
			//end_lim = true;
		
		double v1 = accel * t1;
		double v2 = decel * t2;
		
		double simple_limit = getLimitingVelocity(length, accel, decel);
		
		double l1 = v1 * v1 / (2*accel);
		double l2 = Math.max(0, (vmax*vmax - v1*v1) / (2*a)); // can't be < 0
		double l4 = Math.max(0, (vmax*vmax - v2*v2) / (2*d)); // can't be < 0
		double l5 = v2 * v2 / (2*decel);
		//double l3 = Math.max(0, length - l1 - l2 - l4 - l5);  // can't be < 0
		
		/*System.out.println("vmax: "+vmax);
		System.out.println("v1: "+v1);
		System.out.println("v2: "+v2);
		System.out.println("l1: "+l1);
		System.out.println("l2: "+l2);
		System.out.println("l3: "+l3);
		System.out.println("l4: "+l4);
		System.out.println("l5: "+l5);*/
		
		if(v1 < vmax){
			if(v2 < vmax){
				if(simple_limit <=v1 && simple_limit <=v2){//System.out.println("111");
					return simple_limit;
				}
				else if(l1 + l2 + l4 + l5 <= length){//System.out.println("112");
					return vmax;
				}
				else {
					if(v1 <= v2){//System.out.println("1131");
						double sol1 = Math.sqrt(((length - l1)*2*decel*a + decel*v1*v1)/(decel + a));
						double sol2 = Math.sqrt(((length - l1 - l5)*2*a*d + v1*v1*d + v2*v2*a)/(a + d));
						
						if(sol1 <= v2 && sol1 >= v1)
							return sol1;
						if(sol2 >= v2 && sol2 >= v1 && sol2 <= vmax)
							return sol2;
						
					}else{//System.out.println("1132");
						
						double sol1 = Math.sqrt(((length - l5)*2*accel*d + accel*v2*v2)/(accel + d));
						double sol2 = Math.sqrt(((length - l1 - l5)*2*a*d + v1*v1*d + v2*v2*a)/(a + d));
						
						if(sol1 <= v1 && sol1 >= v2)
							return sol1;
						if(sol2 >= v1 && sol2 >= v2 && sol2 <= vmax)
							return sol2;
						
					}
				}
			}else{
				if(simple_limit <=v1 && simple_limit <=v2){//System.out.println("121");
					return simple_limit;
				}
				else if(l1 + l2 + l5 <= length){//System.out.println("122");
					return vmax; //TODO The distance measurement needs to use the actual velocity, not just the change in velocity!
				}
				else{//System.out.println("123");
					//return v2;
					return Math.sqrt(((length - l1)*2*decel*a + decel*v1*v1)/(decel + a));
				}
			}
		}else{
			if(v2 < vmax){
				if(simple_limit <=v1 && simple_limit <=v2){//System.out.println("211");
					return simple_limit;
				}
				else if(l1 + l4 + l5 <= length){//System.out.println("212");
					return vmax;
				}
				else{//System.out.println("213");
					//return v1;
					return Math.sqrt(((length - l5)*2*accel*d + accel*v2*v2)/(accel + d));
				}
			}else{//System.out.println("221");
				return simple_limit;
			}
		}

		/*double ax = accel * decel * (a + d);
		double bx = -2 * accel * decel * (v1 * d + v2 * a);
		double cx = v1 * v1 * decel * d * (accel + a) + v2 * v2 * accel * a * (decel + d) - length;
		
		double solution = solveQuadratic(ax, bx, cx);
		
		if(Double.isNaN(solution)){
			// uh oh!
			System.out.println("quadratic error");
		}
		if(solution <= 0)
			System.out.println("answer <= 0, possible error...");*/
		
		//return solution;
		
		//System.out.println("Error: didn't return a value");
		return vmax;
		
	}
	
	/*
	 *  Compute the run time for a full job: ie. multiple runs with multiple, depleting mother rolls. Operator timings are taken into account. v2 of model above used.
	 */
	public static double length_delta = 10;  // number of metres to ignore if, for instance, a flag appears at the end of a roll
	public static double[] JobRunTime(Machine m, Job j, double speed, OperatorTimings times, int MODEL_VERSION){
		//[coreruntime  setchanges  mothchanges  splices ]
		double[] results = {0., 0., 0., 0.};
		
		double time = 0.;
		
		double AvgSpliceLength = (j.getFlagRate() < 0 ? Double.POSITIVE_INFINITY : j.getUnwindLengthSI() / (1 + j.getFlagRate())); // expected distance between flags in mother roll
		
		double DistToSetChange = j.getRewindLengthSI();
		double DistToMotherChange = j.getUnwindLengthSI();
		double DistToSplice = AvgSpliceLength;
		double DistToJobEnd = j.getTotLengthSI();
		
		double length = 0.;
		double vLimit = 0.;
		double v = 0.;
		
		boolean finished = false;
		
		while(!finished){
			
			length = Math.min(DistToSetChange, Math.min(DistToMotherChange, Math.min(DistToJobEnd, DistToSplice)));
			
			if(MODEL_VERSION == 1)
				vLimit = getLimitingVelocity(length, m.getAccel(), m.getDecel());
			else if(MODEL_VERSION == 2)
				vLimit = getLimitingVelocityV2(length/*j.getRewindLengthSI()*/, speed, m.getAccel(), m.getDecel(), times.getRewindSclamp(m,j)/* m.getRewindLimit(j.getWebWidthSI(), j.getRewindCoreSI())*/, times.getUnwindSclamp(m,j) /*m.getUnwindLimit(j.getWebWidthSI(), j.getUnwindCoreSI())*/, j.getThicknessSI(), j.getRewindCoreSI(), j.getUnwindCoreSI(), DistToMotherChange/*j.getUnwindLengthSI()*/);
			
			v = Math.min(speed, vLimit);
			DataPoint[] res = null;
			if(MODEL_VERSION == 1)
				res = MachineRunTime(length/*j.getRewindLengthSI()*/, v, m.getAccel(), m.getDecel());
			else if(MODEL_VERSION == 2)
				res = MachineRunTimeV2(length/*j.getRewindLengthSI()*/, v, m.getAccel(), m.getDecel(),times.getRewindSclamp(m,j)/* m.getRewindLimit(j.getWebWidthSI(), j.getRewindCoreSI())*/, times.getUnwindSclamp(m,j)/*m.getUnwindLimit(j.getWebWidthSI(), j.getUnwindCoreSI())*/, j.getThicknessSI(), j.getRewindCoreSI(), j.getUnwindCoreSI(), DistToMotherChange/*j.getUnwindLengthSI()*/);
			time += res[res.length - 1].x;
			
			DistToSetChange -= length;
			DistToMotherChange -= length;
			DistToSplice -= length;
			DistToJobEnd -= length;
			
			if(DistToJobEnd <= 0){
				finished = true;
			}
			if(DistToSetChange <= 0){
				DistToSetChange = j.getRewindLengthSI();
				if(!finished) {
					results[1] += times.getSetChangeTime(m, j);
				}
			}
			if(DistToMotherChange <= 0){
				DistToMotherChange = j.getUnwindLengthSI();
				if(!finished){ 
					results[2] += times.getMotherChangeTime(m, j);
				}
			}
			if(DistToSplice <= 0){ 
				DistToSplice = AvgSpliceLength;
				if(!finished && DistToMotherChange > length_delta && DistToMotherChange != j.getUnwindLengthSI()){  // no splice if job finished, or mother being changed anyway
					results[3] += times.getSpliceTime(m, j);
				}
			}
			//else{
				// TODO ERROR
			//}
		}
		
		results[0] = time;
		return results;
	}
	
	public static double solveQuadratic(double a, double b, double c){
		
		// solutions
		double x, y;
		
		x = -b + Math.sqrt(b*b - 4*a*c);
		y = -b - Math.sqrt(b*b - 4*a*c);
		
		x = x / (2*a);
		y = y / (2*a);
		
		if(Double.isNaN(x) || Double.isInfinite(x)){
			if(Double.isNaN(y) || Double.isInfinite(y)){
				return Double.NaN;
			}
			return y;
		}
		if(Double.isNaN(y) || Double.isInfinite(y)){
			return x;
		}
		// both valid, so hopefully one is negative and we'll return the other one
		return Math.max(x, y);
	}
	
}
