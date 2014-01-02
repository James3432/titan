package atlas.kingj.roi;

/*
 *  Class of global constants
 */
public final class Consts {

	// Prevent this constructor ever being called
	private Consts(){
	    throw new AssertionError();
	}
	
	// *** ADMIN ACCESS PASSWORD ***     (case sensitive)
	public static final String PASSWORD = "Atlas001";
	
	public static String SETTINGS_FILENAME = System.getenv("USERPROFILE")+"\\Production Calculator\\"+"config.ser";
	public static String SETTINGS_DIR = System.getenv("USERPROFILE")+"\\Production Calculator\\";
	
	public static double TITAN_EFF = 0.950;  // 95%
	public static double CUSTOM_EFF = 0.950; // 95%
	public static double GLOBAL_EFF = 1.000; // 100%
	
	public static double DEFAULT_ACCEL = 10./60;
	public static double DEFAULT_DECEL = 10./60;
	public static double DEFAULT_ADELTA = 0;
	
	public static double REWIND_CORE_THICKNESS = 16; // OD - ID in mm
	public static double UNWIND_CORE_THICKNESS = 16; // OD - ID in mm
	
	// The version of model to use:
	//public static final int MODEL_VERSION = 2;
	
	// Max number of machines to allow in lists
	public static final int MACH_LIST_LIMIT = 10;
	public static final int JOB_LIST_LIMIT = 10;
	
	public static final double EFFICIENCY_FACTOR = 0.95; // safety margin for all calculations
	
	public static final double DOUBLE_ERROR_MARGIN = 0.001; // Margin of error in results which may be caused by floating-point rounding (this is a vast overestimate of error, but fine in this usage)
	
	/*
	 *  Unit conversions
	 */
	public static final double MICRO_TO_M = 0.000001;
	public static final double MM_TO_M = 0.001;
	public static final double MIN_TO_S = 1./60;

	// Format of constant names: {MODEL}_{PURPOSE}_{UNITS}
	
	/*
	 * Machine parameter variant list
	 */
	
	public static final String[] REWIND_CORE_MM = {"70", "76", "152"};
	public static final String   LBL_REWIND_CORE_MM = "Rewind Core (mm)";
	
	public static final String[] REWIND_CORE_IN = {"2.75", "3", "6"};
	public static final String   LBL_REWIND_CORE_IN = "Rewind Core (in)";
	
	public static final String[] UNWIND_CORE_MM = {"76", "152", "254"};
	public static final String[] UNWIND_CORE_IN = {"3", "6", "10"};

	public static final String[] ER_WEB_WIDTH_MM = {"1350", "1650"};

	public static final String[] SR_WEB_WIDTH_MM = {"1650", "2250"};

	public static final String[] ER_SPEED_MM = {"450", "550"};
	
	public static final String[] SR_SPEED_MM = {"1000"};
	
	public static final String[] ER_CORE_POSITION = {"Manual", "Laser"};
	public static final String[] SR_CORE_POSITION = {"Manual", "Laser", "Servo"};
	
	public static final String[] SR_KNIFE_POSITION = {"Manual Stacked", "Manual Inflatable", "Auto"};
	
	public static final String[] SR_UNLOADER = {"Manual", "Pneumatic", "Electric"};
	
	/*
	 * Machine parameter option list
	 */
	
	public static final boolean ER_TURRET_OPTION = false;
	public static final boolean SRDT_TURRET_OPTION = true;
	public static final boolean SRDS_TURRET_OPTION = false;
	public static final boolean SR800_TURRET_OPTION = false;
	
	public static final boolean ER_SPLICE_OPTION = true;
	public static final boolean SRDT_SPLICE_OPTION = true;
	public static final boolean SRDS_SPLICE_OPTION = true;
	public static final boolean SR800_SPLICE_OPTION = true;
	
	public static final boolean ER_ALIGN_OPTION = false;
	public static final boolean SRDT_ALIGN_OPTION = true;
	public static final boolean SRDS_ALIGN_OPTION = true;
	public static final boolean SR800_ALIGN_OPTION = false;
	
	public static final boolean ER_ROLL_OPTION = false;
	public static final boolean SRDT_ROLL_OPTION = true;
	public static final boolean SRDS_ROLL_OPTION = true;
	public static final boolean SR800_ROLL_OPTION = false;
	
	public static final boolean ER_AUTOSTRIP_OPTION = false;
	public static final boolean SRDT_AUTOSTRIP_OPTION = true;
	public static final boolean SRDS_AUTOSTRIP_OPTION = true;
	public static final boolean SR800_AUTOSTRIP_OPTION = true;
	
	public static final boolean ER_FLAG_OPTION = false;
	public static final boolean SRDT_FLAG_OPTION = true;
	public static final boolean SRDS_FLAG_OPTION = true;
	public static final boolean SR800_FLAG_OPTION = true;
	
	public static final boolean ER_CUTOFF_OPTION = false;
	public static final boolean SRDT_CUTOFF_OPTION = true;
	public static final boolean SRDS_CUTOFF_OPTION = true;
	public static final boolean SR800_CUTOFF_OPTION = false;
	
	public static final boolean ER_TAPECORE_OPTION = false;
	public static final boolean SRDT_TAPECORE_OPTION = true;
	public static final boolean SRDS_TAPECORE_OPTION = true;
	public static final boolean SR800_TAPECORE_OPTION = false;
	
	public static final boolean ER_TAPETAIL_OPTION = false;
	public static final boolean SRDT_TAPETAIL_OPTION = true;
	public static final boolean SRDS_TAPETAIL_OPTION = true;
	public static final boolean SR800_TAPETAIL_OPTION = false;
	
	public static final boolean ER_850MM_OPTION = false;
	public static final boolean SRDT_850MM_OPTION = true;
	public static final boolean SRDS_850MM_OPTION = true;
	public static final boolean SR800_850MM_OPTION = false;
	
	/*
	 *  Machine speed model constants
	 */
	public static final int ER_MAX_RPM = 1267;
	public static final int SR_MAX_RPM = 1200;
	
	// Custom machine defaults
	public static final double CUST_MACHINE_SPEED = 500./60;
	public static final double CUST_MACHINE_ACCEL = 10./60;
	public static final double CUST_MACHINE_CHANGEMO = 360;
	public static final double CUST_MACHINE_CHANGEJOB = 600;
	public static final double CUST_MACHINE_CHANGEDIAM = 720;
	public static final double CUST_MACHINE_SPLICE = 360;
	public static final double CUST_MACHINE_CHANGESET = 100;	
	
	// Other	
	public static final double UNWIND_DIAM_DRIVE_LIMIT = 1250; // mm   - Limit of when to display a warning of "job may require double unwind drive"
	
	public static final double ER610_MAXWEB = 1650;
	public static final double SR9DS_MAXWEB = 2250;
	public static final double SR9DT_MAXWEB = 2250;
	public static final double SR800_MAXWEB = 1650;
	
	/*
	 * Materials
	 */
	public enum Material {
		A(1,2), B(3, 4), C(4, 76);
		double thickness;
		double density;
		Material(double thick, double dense){
			thickness = thick;
			density = dense;
		}
	}
	Material paper = Material.A;
	Material film = Material.B;
	Material laminate = Material.C;
	
}
