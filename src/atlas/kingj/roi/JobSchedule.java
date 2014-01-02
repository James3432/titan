package atlas.kingj.roi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class JobSchedule implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JobItem> JobList;
	
	public JobSchedule(){
		JobList = new LinkedList<JobItem>();
	}
	public JobSchedule(List<JobItem> j){
		JobList = j;
	}
	
	public void addJob(JobItem j){
		JobList.add(j);
	}
	
	public void addJob(Job j, double time, double weight, double length){
		JobList.add(new JobItem(j, time, weight, length));
	}
	
	public void insertJob(JobItem j, int index){
		JobList.add(index, j);
	}
	
	public Job getJob(int index){
		return JobList.get(index).j;
	}
	
	public void remove(int i){
		JobList.remove(i);
	}
	
	public void empty(){
		JobList.clear();
	}
	
	public void swap(int x, int y){
		JobItem j = JobList.get(x);
		JobItem k = JobList.get(y);
		JobList.set(x, k);
		JobList.set(y, j);
	}
	
	public double getTotalLength(){
		double length = 0.;
		ListIterator<JobItem> it = getIterator();
		while(it.hasNext()){
			length += it.next().j.getTotLengthSI();
		}
		return length;
	}
	
	public double getTotalWeight(){
		double weight = 0.;
		ListIterator<JobItem> it = getIterator();
		while(it.hasNext()){
			weight += it.next().j.getTotWeightSI();
		}
		return weight;
	}
	
	public int getSize(){
		return JobList.size();
	}
	
	public int getUniqueSize(){
		// number of different jobs
		int count = 0;
		for(int i=0; i < getSize(); ++i){
			if(getFirstOccurrence(getJob(i)) == i)
				count ++;
		}
		return count;
	}
	
	public int getFirstOccurrence(Job j){
		// return index at which job first occurs in schedule
		int result = -1;
		
		for(int i=0; i < getSize(); ++i){
			if(getJob(i) == j){
				result = i;
				break;
			}
		}
		
		if(result == -1)
			result = getSize();
		
		return result;
	}
	
	public int getJobNum(Job j){
		// return number of job, when duplicates ignored
		int count = 0;
		
		for(int i=0; i < getSize(); ++i){
			if(getFirstOccurrence(getJob(i)) == i)
				count ++;
			if(j == getJob(i))
				break;
		}
		return count - 1;
	}
	
	public boolean isValid(){
		boolean state = true;
		
		// Not valid if 0 jobs
		if(getSize() < 1)
			state = false;
		
		// ... or if any 1 job is invalid
		ListIterator<JobItem> it = getIterator();
		while(it.hasNext()){
			if(! it.next().isValid())
				state = false;
		}
		
		return state;
	}
	
	public ListIterator<JobItem> getIterator(){
		return JobList.listIterator();
	}
	
	public class JobItem implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Job j;
		public double time; // These are redundant at the moment...
		public double weight;
		//public double length;
		
		public boolean isValid(){ return j.isValid(); }
		
		public JobItem(Job j, double time, double weight, double length){
			this.j = j;
			this.time = time;
			this.weight = weight;
			//this.length = length;
		}
	}
	
	protected Object clone() throws CloneNotSupportedException{
		JobSchedule js = (JobSchedule)super.clone();
		
		return js;
	}

}
