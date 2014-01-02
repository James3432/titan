package atlas.kingj.roi;

import java.io.Serializable;
import java.util.Set;

import javax.swing.ListModel;

public class SaveFile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Production env;
	private ListModel machs;
	private ListModel jobs;
	private Set<String> machnames;
	private Set<String> jobnames;
	private int[] sel;
	public ROIData RoiData;
	
	public Production getEnvironment(){ return env; }
	public ListModel getMachineList(){ return machs; }
	public ListModel getJobList(){ return jobs; }
	public Set<String> getMachNames(){ return machnames; }
	public Set<String> getJobNames(){ return jobnames; }
	public int[] getSelection(){ return sel; }
	public ROIData getRoiData(){ return RoiData; }
	
	public SaveFile(){
		super();
	}
	
	public void add(Production environment, ListModel machines, ListModel jobs, Set<String> mns, Set<String> jns, int[] selection, ROIData roi){
		env = environment;
		machs = machines;
		this.jobs = jobs;
		machnames = mns;
		jobnames = jns;
		sel = selection;
		RoiData = roi;
	}

}
