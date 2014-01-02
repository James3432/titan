package atlas.kingj.roi;

import java.io.Serializable;

/*
 *  Class to specify the production environment used, such as shift lengths
 *  
 *  Also holds other application-wide settings such as advanced timing & acceleration options
 *  
 */
public class Production implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean Unlocked = false; // whether admin access is enabled in this session
	
	public double TitanEfficiency = Consts.TITAN_EFF;   // 95%
	public double CustomEfficiency = Consts.CUSTOM_EFF; // 95%
	public double GlobalEfficiency = Consts.GLOBAL_EFF; // 100%
	
	public boolean SaveOnClose = true;
	//public boolean SaveSchedule = false;
	public String SaveFileName = Consts.SETTINGS_FILENAME;//"config.ser";
	
	public boolean metric = true;
	public boolean getUnits(){ return metric; }
	
	public double HrsPerShift = 8;
	public double HrsPerDay = 24;
	public double HrsPerYear = 6000;
	
	public boolean StartStopTimes = true; // whether we simulate start/end times in schedule
	
	public double TimeLoad;
	public double TimeUnload;
	// etc...
	
	/*// default machine speeds (each machine has this already though)
	public double accel;
	public double decel;
	public double accel_delta;*/
	
	public OperatorTimings timings;
	
	private JobSchedule schedule;
	
	// TODO: only valid if the schedule, and all of its jobs, are valid.  e.g >= 1 job.
	private boolean valid = false;
	public boolean isValid(){ return valid; }
	
	public Production(){
		timings = new OperatorTimings();
		schedule = new JobSchedule();
		
		/*accel = Consts.DEFAULT_ACCEL;
		decel = Consts.DEFAULT_DECEL;
		accel_delta = Consts.DEFAULT_ADELTA;*/
	}
	
	public void update(FrmMain form){
		try{
			HrsPerShift = Double.parseDouble(form.txtShiftLength.getText());
			HrsPerDay = Double.parseDouble(form.txtShiftCount.getText()) * Double.parseDouble(form.txtShiftLength.getText());
			double DaysPerYear = Double.parseDouble(form.txtDaysYear.getText());
			HrsPerYear = DaysPerYear * HrsPerDay;
			valid = true;
			if((! getSchedule().isValid()) || HrsPerShift <= 0 || HrsPerDay <= 0 || HrsPerYear <= 0 || DaysPerYear <= 0 || HrsPerShift > 24 || HrsPerDay > 24 || DaysPerYear > 366 || HrsPerYear > 8784)
				valid = false;
		}catch(NumberFormatException e){
			valid = false;
		}

	}
	
	public JobSchedule getSchedule(){ return schedule; }
	public void setSchedule(JobSchedule js){ schedule = js; }
	
	protected Object clone() throws CloneNotSupportedException {

	    Production clone=(Production)super.clone();

	    clone.schedule = (JobSchedule) schedule.clone();
	    clone.timings = timings;//.clone();
	    return clone;

	  }

	
}
