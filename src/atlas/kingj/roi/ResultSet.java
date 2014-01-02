package atlas.kingj.roi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import atlas.kingj.roi.Machine.Model;

public class ResultSet {
	// TODO: what about units, and different time scales, and the choice of length, weight, etc.?
	private List<Result> results;
	public boolean isError;
	public String ErrorMsg;
	
	// Charts
	private JFreeChart timeGraph;      // Time/schedule
	private JFreeChart productGraph;   // Length/year
	private JFreeChart productWGraph;  // Weight/year
	private JFreeChart rateGraph;      // Length/hour
	private JFreeChart rateWGraph;     // Weight/hour
	private JFreeChart efficiencyGraph;// Efficiency
	private JFreeChart timeGraphPrev;      // Time/schedule
	private JFreeChart productGraphPrev;   // Length/year
	private JFreeChart productWGraphPrev;  // Weight/year
	private JFreeChart rateGraphPrev;      // Length/hour
	private JFreeChart rateWGraphPrev;     // Weight/hour
	private JFreeChart efficiencyGraphPrev;// Efficiency
	private JFreeChart RunTimings;     // Detailed timing diagram for 1 run
	private JFreeChart[] BreakdownCharts; // breakdown of timings
	
	// Seconds per time unit, from the environment settings
	public double HrsPerShift;
	public double HrsPerDay;
	public double HrsPerWeek;
	public double HrsPerYear;
	
	public ResultSet(){
		results = new LinkedList<Result>();
	}
	public ResultSet(Result r){
		results = new LinkedList<Result>();
		results.add(r);
	}
	
	public void add(Result r){
		results.add(r);
	}
	
	public Result get(int i){
		return results.get(i);
	}
	
	public int getSize(){
		return results.size();
	}
	
	public CategoryDataset CreateDataset(ResultSet results, ResultType type, ResultTime time){
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		String series1 = "Time";
		double val = 0.;
		String name = "";
		for(int i=0; i < results.getSize(); ++i){
			// TODO if all values > 10000, div by 1000
			
			/*switch(type){
			case EFF:
				break;
			case LENGTH: switch(time){ case YEAR: break; case HOUR: break; default: break; }
				break;
			case WEIGHT: switch(time){ case YEAR: break; case HOUR: break; default: break; }
				break;
			default:
				break;
			}*/
			
			val = results.get(i).getResult(type, time);
			
			if(time == ResultTime.YEAR)
				val = val / 1000;
			
			name = results.get(i).getModel() + ": " + results.get(i).getName();
			data.addValue(val, series1, name);
		}
		return data;
	}
	
	public List<Result> getList(){ return results; }
	public JFreeChart getTimeGraph(){ return timeGraph; }
	public JFreeChart getProdGraph(){ return productGraph; }
	public JFreeChart getProdWGraph(){ return productWGraph; }
	public JFreeChart getRateGraph(){ return rateGraph; }
	public JFreeChart getRateWGraph(){ return rateWGraph; }
	public JFreeChart getEffGraph(){ return efficiencyGraph; }
	public JFreeChart getTimingGraph(){ return RunTimings; }
	public JFreeChart[] getBreakdownCharts(){ return BreakdownCharts; }
	public void setTimeGraph(JFreeChart g){ timeGraph = g; }
	public void setProdGraph(JFreeChart g){ productGraph = g; }
	public void setProdWGraph(JFreeChart g){ productWGraph = g; }
	public void setRateGraph(JFreeChart g){ rateGraph = g; }
	public void setRateWGraph(JFreeChart g){ rateWGraph = g; }
	public void setEffGraph(JFreeChart g){ efficiencyGraph = g; }
	public void setTimingGraph(JFreeChart g){ RunTimings = g; }
	public void setBreakdownCharts(JFreeChart[] g){ BreakdownCharts = g; }
	
	public JFreeChart getTimeGraphPrev(){ return timeGraphPrev; }
	public JFreeChart getProdGraphPrev(){ return productGraphPrev; }
	public JFreeChart getProdWGraphPrev(){ return productWGraphPrev; }
	public JFreeChart getRateGraphPrev(){ return rateGraphPrev; }
	public JFreeChart getRateWGraphPrev(){ return rateWGraphPrev; }
	public JFreeChart getEffGraphPrev(){ return efficiencyGraphPrev; }
	public void setTimeGraphPrev(JFreeChart g){ timeGraphPrev = g; }
	public void setProdGraphPrev(JFreeChart g){ productGraphPrev = g; }
	public void setProdWGraphPrev(JFreeChart g){ productWGraphPrev = g; }
	public void setRateGraphPrev(JFreeChart g){ rateGraphPrev = g; }
	public void setRateWGraphPrev(JFreeChart g){ rateWGraphPrev = g; }
	public void setEffGraphPrev(JFreeChart g){ efficiencyGraphPrev = g; }
	
	public class Result {
		private String MachineName;
		private String MachineModel;
		
		public double ScheduleLength; // m
		public double ScheduleTime;   // hrs
		public double ScheduleWeight; // kg
		
		public double MtPerHour;
		public double IdealMtPerHour; //TODO
		//private double KgPerHour;
		
		public double[] OpTimings;  // setchange  mochange  jobchange  splice  diamchange
		
		public double SplicesPerSchedule;
		public double MothersPerSchedule;
		
		public String getName(){ return MachineName; }
		public String getModel(){ return MachineModel; }
		public Model getModelType(){ 
			if(getModel().toLowerCase().equals("er610"))
				return Model.ER610; 
			if(getModel().toLowerCase().equals("sr9-ds"))
				return Model.SR9DS; 
			if(getModel().toLowerCase().equals("sr9-dt"))
				return Model.SR9DT; 
			if(getModel().toLowerCase().equals("sr800"))
				return Model.SR800; 
			if(getModel().toLowerCase().equals("custom"))
				return Model.CUSTOM; 
			else
				return Model.CUSTOM;
		}
		
		public Result(String MachineName, String MachineModel){
			this.MachineName = MachineName;
			this.MachineModel = MachineModel;
		}
		
		public double getResult(ResultType type, ResultTime time){
			double res = 0.;
			
			switch(type){
				case LENGTH: res = TimeScale(ScheduleLength, time); break;
				case WEIGHT: res = TimeScale(ScheduleWeight, time); break;
				case RATE: res = MtPerHour; break;
				case EFF: res = (IdealMtPerHour==0 ? 0 : 100 * MtPerHour / IdealMtPerHour); break;
				case TIME: res = ScheduleTime;
			}
			
			return res;
		}
		
		private double TimeScale(double quant, ResultTime time){
			double res = 0.;
			
			if(ScheduleTime > 0){
				switch(time){
					case SCHEDULE: res = quant; break;
					case YEAR: res = HrsPerYear * quant / ScheduleTime; break;
					case HOUR: res = quant / ScheduleTime; break;
					case SHIFT: res = HrsPerShift * quant / ScheduleTime; break;
					case DAY: res = HrsPerDay * quant / ScheduleTime; break;
					case WEEK: res = HrsPerWeek * quant / ScheduleTime; break;
				}
			}
			
			return res;
		}
	}
	
	public enum ResultType implements Serializable {
		LENGTH, WEIGHT, RATE, EFF, TIME
	}
	
	public enum ResultTime implements Serializable {
		SCHEDULE, YEAR, HOUR, SHIFT, DAY, WEEK
	}
}
