package atlas.kingj.roi;

import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.JFreeChart;

import atlas.kingj.roi.ResultSet.Result;
import atlas.kingj.roi.ResultSet.ResultTime;
import atlas.kingj.roi.ResultSet.ResultType;

public class ResultSetROI {
	
	private List<ResultROI> results;
	public boolean isError;
	public String ErrorMsg;
	
	// Charts
	private JFreeChart prodGraph;      // Productivity
	private JFreeChart energyGraph;   // Energy savings
	private JFreeChart maintGraph;  // Maintenance costs
	private JFreeChart wasteGraph;      // Waste savings

	public ResultSetROI(){
		results = new LinkedList<ResultROI>();
	}
	public ResultSetROI(ResultROI r){
		results = new LinkedList<ResultROI>();
		results.add(r);
	}
	
	public void add(ResultROI r){
		results.add(r);
	}
	
	public ResultROI get(int i){
		return results.get(i);
	}
	
	public int getSize(){
		return results.size();
	}
	
	public void set(ResultSet rs){
		results = new LinkedList<ResultROI>();
		for(int i=0; i<rs.getSize(); ++i){
			results.add(new ResultROI());
			results.get(i).set(rs.get(i));
		}
	}
	
	public JFreeChart getProdGraph(){ return prodGraph; }
	public JFreeChart getEnergyGraph(){ return energyGraph; }
	public JFreeChart getMaintGraph(){ return maintGraph; }
	public JFreeChart getWasteGraph(){ return wasteGraph; }
	public void setProdGraph(JFreeChart g){ prodGraph = g; }
	public void setEnergyGraph(JFreeChart g){ energyGraph = g; }
	public void setMaintGraph(JFreeChart g){ maintGraph = g; }
	public void setWasteGraph(JFreeChart g){ wasteGraph = g; }
	
	public class ResultROI {
		private String MachineName;
		private String MachineModel;
		
		public double value;
		public double energycost;
		public double prodloss;
		public double partcosts;
		public double maintcost;
		public double wastesave;
		public double wasteval;
		
		public double ScheduleLength; // m
		public double ScheduleTime;   // hrs
		public double ScheduleWeight; // kg
		
		public double AnnualLength; // m
		public double AnnualWeight; // kg
		
		public double SplicesPerSchedule;
		public double MothersPerSchedule;
		
		public String getName(){ return MachineName; }
		public String getModel(){ return MachineModel; }
		
		public ResultROI(){
			
		}
		
		public ResultROI(String MachineName, String MachineModel){
			this.MachineName = MachineName;
			this.MachineModel = MachineModel;
		}
		
		public void set(Result r){
			this.MachineName = r.getName();
			this.MachineModel = r.getModel();
			this.ScheduleLength = r.ScheduleLength;
			this.ScheduleTime = r.ScheduleTime;
			this.ScheduleWeight = r.ScheduleWeight;
			this.AnnualLength = r.getResult(ResultType.LENGTH, ResultTime.YEAR);
			this.AnnualWeight = r.getResult(ResultType.WEIGHT, ResultTime.YEAR);
			this.SplicesPerSchedule = r.SplicesPerSchedule;
			this.MothersPerSchedule = r.MothersPerSchedule;
		}
		
	}
	
}
