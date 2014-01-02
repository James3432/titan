package atlas.kingj.roi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

import atlas.kingj.roi.ResultSet.ResultTime;
import atlas.kingj.roi.ResultSet.ResultType;

/*
 *  Class to compare machine setups given job and production details
 */
public class Comparator {

	//private JFreeChart graph;
	//private JFreeChart timingGraph;
	private JobSchedule schedule;
	private ResultSet results;
	private CategoryDataset dataset;
	private Production prod; // Needed for interpretation of results over different scales of analysis
	private int MODEL_VERSION;
	
	public Comparator(Machine m[], Production p, int model){
		super();
		
		schedule = p.getSchedule();
		
		if(schedule.getSize() < 1)
			return;
		
		results = new ResultSet();
		for(int i=0; i<m.length; ++i){
			results.add(results.new Result(m[i].name, m[i].model.toString()));
		}
		
		MODEL_VERSION = model;
		prod = p;
		// dataset for 1 run under job #1
		dataset = compute(m, schedule, p.timings);
		
		results.HrsPerDay = p.HrsPerDay;
		results.HrsPerYear = p.HrsPerYear;
		results.HrsPerShift = p.HrsPerShift;
		
		// TODO graph labels need changing, and make each graph type, imperial units graphs??
		results.setTimeGraph(ChartFactory.createBarChart("Production Time Comparison", "Machine", "Time / Schedule (hrs)", dataset, PlotOrientation.VERTICAL, false, true, false));
		results.setEffGraph(ChartFactory.createBarChart("Run Percentage Comparison", "Machine", "Machine running (%)", results.CreateDataset(results, ResultType.EFF, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		results.setProdGraph(ChartFactory.createBarChart("Productivity Comparison", "Machine", "Length / Year (km)", results.CreateDataset(results, ResultType.LENGTH, ResultTime.YEAR), PlotOrientation.VERTICAL, false, true, false));
		results.setRateGraph(ChartFactory.createBarChart("Production Rate Comparison", "Machine", "Metres / Hour (m/hr)", results.CreateDataset(results, ResultType.LENGTH, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		results.setProdWGraph(ChartFactory.createBarChart("Productivity Comparison", "Machine", "Weight / Year (tonnes)", results.CreateDataset(results, ResultType.WEIGHT, ResultTime.YEAR), PlotOrientation.VERTICAL, false, true, false));
		results.setRateWGraph(ChartFactory.createBarChart("Production Rate Comparison", "Machine", "Weight / Hour (kg)", results.CreateDataset(results, ResultType.WEIGHT, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		
		results.setTimeGraphPrev(ChartFactory.createBarChart("", "", "Time (hrs)", dataset, PlotOrientation.VERTICAL, false, true, false));
		results.setEffGraphPrev(ChartFactory.createBarChart("", "", "Machine running (%)", results.CreateDataset(results, ResultType.EFF, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		results.setProdGraphPrev(ChartFactory.createBarChart("", "", "km / year", results.CreateDataset(results, ResultType.LENGTH, ResultTime.YEAR), PlotOrientation.VERTICAL, false, true, false));
		results.setRateGraphPrev(ChartFactory.createBarChart("", "", "m / hr", results.CreateDataset(results, ResultType.LENGTH, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		results.setProdWGraphPrev(ChartFactory.createBarChart("", "", "tonnes / year", results.CreateDataset(results, ResultType.WEIGHT, ResultTime.YEAR), PlotOrientation.VERTICAL, false, true, false));
		results.setRateWGraphPrev(ChartFactory.createBarChart("", "", "kg / hr", results.CreateDataset(results, ResultType.WEIGHT, ResultTime.HOUR), PlotOrientation.VERTICAL, false, true, false));
		
		JFreeChart[] breakdowns = new JFreeChart[results.getSize()];
		for(int i=0; i<results.getSize(); i++){
		
			double[] ts = StoHrs(results.get(i).OpTimings);
			DefaultPieDataset pie = new DefaultPieDataset();
			pie.setValue("Machine Running"/*\n"+Double.toString(roundTwoDecimals(results.get(i).ScheduleTime))+" hrs"*/, (results.get(i).IdealMtPerHour == 0 ? 0 : results.get(i).ScheduleLength / results.get(i).IdealMtPerHour));
			pie.setValue("Set Changes"/*\n"+Double.toString(roundTwoDecimals(ts[0]))+" hrs"*/, ts[0]);
			pie.setValue("Mother Roll Changes"/*\n"+Double.toString(roundTwoDecimals(ts[1]))+" hrs"*/, ts[1]);
			pie.setValue("Job Changes"/*\n"+Double.toString(roundTwoDecimals(ts[2]))+" hrs"*/, ts[2]);
			pie.setValue("Splices"/*\n"+Double.toString(roundTwoDecimals(ts[3]))+" hrs"*/, ts[3]);
			pie.setValue("Diameter Changes"/*\n"+Double.toString(roundTwoDecimals(ts[4]))+" hrs"*/, ts[4]);
			JFreeChart chart = ChartFactory.createPieChart3D("Productivity Breakdown", pie, false, true, false);
			breakdowns[i] = chart;
			PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        plot.setBackgroundPaint(Color.white);
	        plot.setToolTipGenerator(new StandardPieToolTipGenerator("{1} hrs, {2}"));
	        plot.setSectionPaint("Machine Running", new Color(0,255,0));
	        plot.setSectionPaint("Set Changes", new Color(0,162,232));
	        plot.setSectionPaint("Mother Roll Changes", new Color(0,128,0));
	        plot.setSectionPaint("Job Changes", new Color(255, 201, 14));
	        plot.setSectionPaint("Splices", new Color(185,122,87));
	        plot.setSectionPaint("Diameter Changes", new Color(0,0,128));
	        plot.setLabelGenerator(new PieSectionLabelGenerator() {
				
				@Override
				public String generateSectionLabel(PieDataset arg0, @SuppressWarnings("rawtypes") Comparable arg1) {
					if(arg1.toString().equals("Machine Running") && arg0.getValue(0).doubleValue() > 0){
						return "Machine Running";
					}else if(arg1.toString().equals("Set Changes") && arg0.getValue(1).doubleValue() > 0){
						return "Set Changes";
					}else if(arg1.toString().equals("Mother Roll Changes") && arg0.getValue(2).doubleValue() > 0){
						return "Mother Roll Changes";
					}else if(arg1.toString().equals("Job Changes") && arg0.getValue(3).doubleValue() > 0){
						return "Job Changes";
					}else if(arg1.toString().equals("Splices") && arg0.getValue(4).doubleValue() > 0){
						return "Splices";
					}else if(arg1.toString().equals("Diameter Changes") && arg0.getValue(5).doubleValue() > 0){
						return "Diameter Changes";
					}
					
					return null;
				}
				
				@Override
				public AttributedString generateAttributedSectionLabel(PieDataset arg0, @SuppressWarnings("rawtypes") Comparable arg1) {
					return null;
				}
			});
        
		}
        results.setBreakdownCharts(breakdowns);
        
        FormatGraph(results.getTimeGraph(),1);
        FormatGraph(results.getEffGraph(),0);
        FormatGraph(results.getProdGraph(),4);
        FormatGraph(results.getRateGraph(),2);
        FormatGraph(results.getProdWGraph(),5);
        FormatGraph(results.getRateWGraph(),3);
        FormatGraph(results.getTimeGraphPrev(),1);
        FormatGraph(results.getEffGraphPrev(),0);
        FormatGraph(results.getProdGraphPrev(),4);
        FormatGraph(results.getRateGraphPrev(),2);
        FormatGraph(results.getProdWGraphPrev(),5);
        FormatGraph(results.getRateWGraphPrev(),3);
        
        ValueAxis rangeAxis = results.getEffGraph().getCategoryPlot().getRangeAxis();
        ValueAxis rangeAxisP = results.getEffGraphPrev().getCategoryPlot().getRangeAxis();
        results.getEffGraph().getCategoryPlot().getRenderer().setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2}%", NumberFormat.getInstance()));
        results.getEffGraphPrev().getCategoryPlot().getRenderer().setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2}%", NumberFormat.getInstance()));

        rangeAxis.setRange(0, 100);
        rangeAxisP.setRange(0, 100);
        rangeAxis.setAutoTickUnitSelection(true);
        rangeAxisP.setAutoTickUnitSelection(true);

	}
	
	private double[] StoHrs(double[] t){
		double[] result = new double[t.length];
		for(int i=0; i<t.length; i++){
			result[i] = t[i] / 3600;
		}
		return result;
	}
	
	double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	
	private void FormatGraph(JFreeChart chart, int type){
		CategoryPlot cp = chart.getCategoryPlot();
        cp.setBackgroundPaint(new Color(240,240,240));
        cp.setRangeGridlinePaint(Color.gray);
        CategoryItemRenderer renderer = new CustomRenderer(
                new Paint[] {new Color(255,85,85), new Color(85,85,255), new Color(85,255,85),
                		new Color(251,251,0), new Color(86,228,200) });
        cp.setRenderer(renderer);
        BarRenderer b = (BarRenderer) cp.getRenderer();
        b.setBarPainter(new StandardBarPainter());
        // type  1=time 2=length 3=weight 4=km 5=tns
        switch(type){
        case 1: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2} hrs", NumberFormat.getInstance())); break;
        case 2: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2} m", NumberFormat.getInstance())); break;
        case 3: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2} kg", NumberFormat.getInstance())); break;
        case 4: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2} km", NumberFormat.getInstance())); break;
        case 5: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2} tonnes", NumberFormat.getInstance())); break;
        default: renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
        	    "{1}, {2}", NumberFormat.getInstance())); break;
        }
	}
	
	class CustomRenderer extends BarRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/** The colors. */
        private Paint[] colors;

        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }

	
	private CategoryDataset compute(Machine m[], JobSchedule js, OperatorTimings times){
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		String series1 = "Time";
		
		int jobCount = js.getSize();
		
		// FOR TIMING
		XYSeries[] timeDataSeries = new XYSeries[m.length];
        final XYSeriesCollection timeData = new XYSeriesCollection();
        
        // Foreach machine
		for(int i=0; i<m.length; ++i){
			
			double[] opTimings = {0., 0., 0., 0., 0.};  // setchange  mochange  jobchange  splice  diamchange
			
			double vMach;
			
			double time = 0.;
			double idealTime = 0.;
			
			timeDataSeries[i] = new XYSeries(m[i].name);
			
			int jobChangeCounter = 0;
			
			// Foreach job
			for(int j=0; j<jobCount; ++j){
				
				Job job = js.getJob(j);
			
				double vLimit = 0, vFinal = 0;
				
				// reset vmach in case only some jobs have speed limits
				vMach = m[i].getSpeed(job.getWebWidthSI());
				
				if(job.isSpeedLimited() && job.getSpeedLimitSI() < vMach)
					vMach = job.getSpeedLimitSI();
				
				// TODO: this needs updating for v2.
				if(MODEL_VERSION == 1)
					vLimit = Core.getLimitingVelocity(job.getRewindLengthSI(), m[i].getAccel(), m[i].getDecel());
				if(MODEL_VERSION == 2){
					double AvgSpliceLength = (job.getFlagRate() < 0 ? Double.POSITIVE_INFINITY : job.getUnwindLengthSI() / (1 + job.getFlagRate()));
					vLimit = Core.getLimitingVelocityV2(Math.min(job.getRewindLengthSI(), Math.min(job.getUnwindLengthSI(), Math.min(job.getTotLengthSI(), AvgSpliceLength)))/*job.getRewindLengthSI()*/, vMach, m[i].getAccel(), m[i].getDecel(), prod.timings.getRewindSclamp(m[i],job)/*m[i].getRewindLimit(job.getWebWidthSI(), job.getRewindCoreSI())*/, prod.timings.getUnwindSclamp(m[i],job)/* m[i].getUnwindLimit(job.getWebWidthSI(), job.getUnwindCoreSI())*/, job.getThicknessSI(), job.getRewindCoreSI(), job.getUnwindCoreSI(), job.getUnwindLengthSI());
				}
				
				//System.out.println("vLimit: "+vLimit);
				
				vFinal = Core.getMaxVelocity(vMach, vLimit);
				//System.out.println("vFinal: "+vFinal);
				// Get run time of machine for a single set
				DataPoint[] res = null;
				if(MODEL_VERSION == 1)
					res = Core.MachineRunTime(Math.min(job.getRewindLengthSI(), Math.min(job.getUnwindLengthSI(), job.getTotLengthSI())), vFinal, m[i].getAccel(), m[i].getDecel());
				else if(MODEL_VERSION == 2){
					double AvgSpliceLength = (job.getFlagRate() < 0 ? Double.POSITIVE_INFINITY : job.getUnwindLengthSI() / (1 + job.getFlagRate()));
					res = Core.MachineRunTimeV2(Math.min(job.getRewindLengthSI(), Math.min(job.getUnwindLengthSI(), Math.min(job.getTotLengthSI(), AvgSpliceLength))), vFinal, m[i].getAccel(), m[i].getDecel(), prod.timings.getRewindSclamp(m[i],job)/*m[i].getRewindLimit(job.getWebWidthSI(), job.getRewindCoreSI())*/, prod.timings.getUnwindSclamp(m[i],job)/* m[i].getUnwindLimit(job.getWebWidthSI(), job.getUnwindCoreSI())*/, job.getThicknessSI(), job.getRewindCoreSI(), job.getUnwindCoreSI(), job.getUnwindLengthSI());
				}
				//time = res[res.length - 1].x;
				
				/////  Changes for job scheduling: timeData is for run timing: we only need to change the value of time here.
				// JobRunTime returns:  [coreruntime  setchanges  mothchanges  splices ]
				double[] timeArr;
				timeArr = Core.JobRunTime(m[i], job, vMach, times, MODEL_VERSION);
				time += timeArr[0] + timeArr[1] + timeArr[2] + timeArr[3];
				idealTime += timeArr[0];
				opTimings[0] += timeArr[1];
				opTimings[1] += timeArr[2];
				opTimings[3] += timeArr[3];
				
				//TODO: start & finish times...
				double jobChangeTime = 0., diamChangeTime = 0.;
				int currjobindex = j;
				int nextjobindex = j+1;
				if(j >= jobCount - 1)
					nextjobindex = 0;
				if((js.getJob(currjobindex).getRewindCoreSI() != js.getJob(nextjobindex).getRewindCoreSI())){
					diamChangeTime = prod.timings.getDiamChangeTime(m[i], js.getJob(currjobindex));
				}
				if(js.getJob(currjobindex) == js.getJob(nextjobindex)){
					// adjacent jobs identical: no job change time
					if(!(prod.StartStopTimes && nextjobindex == 0 && currjobindex == jobCount - 1)) // only if not last & first jobs, since we deal with this later (depending on StartStopTimes)
						jobChangeTime = prod.timings.getSetChangeTime(m[i], js.getJob(currjobindex)) + prod.timings.getMotherChangeTime(m[i], js.getJob(currjobindex));
				}else{
					jobChangeTime = prod.timings.getJobChangeTime(m[i], js.getJob(currjobindex)); // add job changeover delay
				}
				
				/*// Always add at least 1 jobchange, as a setup time
				if(j == 0 && jobChangeTime != prod.timings.getJobChangeTime(m[i], js.getJob(currjobindex))){
					jobChangeTime = prod.timings.getJobChangeTime(m[i], js.getJob(currjobindex));
				}*/
				
				time += jobChangeTime + diamChangeTime;
				jobChangeCounter++;
				
				opTimings[2] += jobChangeTime;
				opTimings[4] += diamChangeTime;
				
				// !!!!! TODO: add on in-between-job times, move data.addValue to outer loop
				
				///// End of changes
				// TODO: don't wan't to calculate timeData for every job... move out of loop
				
				//String name =  m[i].model.toString() +": "+ m[i].name;
				//String category1 = testMachine.model.toString() +": "+ testMachine.name;
				//data.addValue(time, series1, name);
	
				if(j==0){
					// edit 19/12: added 3 lines below to make timing diagram ignore splice stuff. To revert, remove these lines and change label on graph to correct distance
					vLimit = Core.getLimitingVelocityV2(Math.min(job.getRewindLengthSI(), Math.min(job.getUnwindLengthSI(), job.getTotLengthSI()))/*job.getRewindLengthSI()*/, vMach, m[i].getAccel(), m[i].getDecel(), prod.timings.getRewindSclamp(m[i], job)/*m[i].getRewindLimit(job.getWebWidthSI(), job.getRewindCoreSI())*/, prod.timings.getUnwindSclamp(m[i],job)/* m[i].getUnwindLimit(job.getWebWidthSI(), job.getUnwindCoreSI())*/, job.getThicknessSI(), job.getRewindCoreSI(), job.getUnwindCoreSI(), job.getUnwindLengthSI());
					vFinal = Core.getMaxVelocity(vMach, vLimit);
					res = Core.MachineRunTimeV2(Math.min(job.getRewindLengthSI(), Math.min(job.getUnwindLengthSI(), job.getTotLengthSI())), vFinal, m[i].getAccel(), m[i].getDecel(), prod.timings.getRewindSclamp(m[i], job)/*m[i].getRewindLimit(job.getWebWidthSI(), job.getRewindCoreSI())*/, prod.timings.getUnwindSclamp(m[i],job)/* m[i].getUnwindLimit(job.getWebWidthSI(), job.getUnwindCoreSI())*/, job.getThicknessSI(), job.getRewindCoreSI(), job.getUnwindCoreSI(), job.getUnwindLengthSI());
					
					//// TIMING DATA (first job only)
					for(int z=0; z<res.length; ++z){
						DataPoint dp = res[z];
						timeDataSeries[i].add(dp.x, dp.y * 60);
					}
				}
				
			}
			
			if(prod.StartStopTimes){ // apply start/end times
				if(js.getJob(0) == js.getJob(js.getSize() - 1)){
					double extratime = prod.timings.getStartTime(m[i], js.getJob(0)) + prod.timings.getStopTime(m[i], js.getJob(js.getSize() - 1));
					opTimings[2] += extratime;
					time += extratime;
				}
			}
			
			// apply efficiency factors
			if(m[i].model == Machine.Model.CUSTOM){
				time = time / prod.CustomEfficiency;
				idealTime = idealTime / prod.CustomEfficiency;
			}else{
				time = time / prod.TitanEfficiency;
				idealTime = idealTime / prod.TitanEfficiency;
			}
			
			time = time / prod.GlobalEfficiency;
			idealTime = idealTime / prod.GlobalEfficiency;
				
			
			// TODO time measured in s, but results in hrs
			results.get(i).ScheduleTime = time / 3600;
			results.get(i).ScheduleLength = js.getTotalLength();
			results.get(i).ScheduleWeight = js.getTotalWeight();
			results.get(i).IdealMtPerHour = 3600 * js.getTotalLength() / idealTime;
			results.get(i).MtPerHour = 3600 * js.getTotalLength() / time;
			results.get(i).SplicesPerSchedule = (prod.timings.getSpliceTime(m[i], new Job()) == 0 ? 0 : (opTimings[3] / prod.timings.getSpliceTime(m[i], new Job())));
			results.get(i).MothersPerSchedule = (prod.timings.getMotherChangeTime(m[i], new Job()) == 0 ? 0 : (opTimings[1] / prod.timings.getMotherChangeTime(m[i], new Job()) + jobChangeCounter ));
			
			results.get(i).OpTimings = scaleArray(opTimings, (m[i].model == Machine.Model.CUSTOM ? prod.CustomEfficiency * prod.GlobalEfficiency : prod.TitanEfficiency * prod.GlobalEfficiency));
			
			String name =  m[i].model.toString() +": "+ m[i].name;
			data.addValue(time / 3600, series1, name);
			
			timeData.addSeries(timeDataSeries[i]);
			
		}
		
		//// DRAW TIMING DIAGRAM
		//double AvgSpliceLength = (js.getJob(0).getFlagRate() < 0 ? Double.POSITIVE_INFINITY : js.getJob(0).getUnwindLengthSI() / (1 + js.getJob(0).getFlagRate()));
		final JFreeChart chart = ChartFactory.createXYLineChart(
	            "Machine Run Timing Diagram",    // chart title
	            "Time to run " + roundTwoDecimals(Math.min(js.getJob(0).getRewindLengthSI(), Math.min(js.getJob(0).getUnwindLengthSI(),/* Math.min(AvgSpliceLength, */js.getJob(0).getTotLengthSI()))) + "m (s)",   // x axis label
	            "Linear Speed (m/min)",            // y axis label
	            timeData,                        // data
	            PlotOrientation.VERTICAL,
	            true,                            // include legend
	            true,                            // tooltips
	            false                            // urls
	        );
		
		XYPlot plot = (XYPlot) chart.getPlot(); 
		 XYItemRenderer r = plot.getRenderer(); 
		 BasicStroke wideLine = new BasicStroke( 2.5f ); 
		 Color[] colors = {Color.RED, Color.BLUE, new Color(0,128,0), new Color(255,130,0), new Color(100,0,100), Color.YELLOW, Color.BLACK, Color.WHITE, Color.CYAN, Color.MAGENTA, Color.PINK};
		 int series = m.length;
		 for(int i=0;i<series;++i){
			 r.setSeriesStroke(i, wideLine); 
			 r.setSeriesPaint(i, colors[i % colors.length]);
		 }
		
		results.setTimingGraph(chart);
		
		return data;
	}
	
	//public JFreeChart getGraph()        { return graph;       }
	//public JFreeChart getTimings()      { return timingGraph; }
	public CategoryDataset getDataset() { return dataset;     }
	public ResultSet getResults()       { return results;     }
	public Production getProd()         { return prod;        }
	
	private double [] scaleArray(double[] vals, double scale){
		double[] result = new double[vals.length];
		for(int i=0; i<vals.length; ++i)
			result[i] = vals[i] / scale;
		return result;
	}

	// TODO: this is replaced by ResultSet class
	/*class Numerics {
		double TonsPerDay;
		double LengthPerDay;
		TimeDomain frequency;
		public Numerics(double tpd, double lpd, TimeDomain t){
			TonsPerDay = tpd;
			LengthPerDay = lpd;
			frequency = t;
		}
	}
*/
	
	/*
	 *  BACKUP OF COMPUTE FUNCTION, 13/08/2013
	 * 
	private CategoryDataset compute(Machine m[], JobSchedule js, Production p){
		
		Job j = js.getJob(0);
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		String series1 = "Time";
		
		// FOR TIMING
		XYSeries[] timeDataSeries = new XYSeries[m.length];
        final XYSeriesCollection timeData = new XYSeriesCollection();

		for(int i=0; i<m.length; ++i){
			
			double vMach = m[i].speed.speed;
			double vLimit = 0, vFinal = 0;
			
			if(j.isSpeedLimited() && j.getSpeedLimitSI() < vMach)
				vMach = j.getSpeedLimitSI();
			
			// TODO: this needs updating for v2.
			if(MODEL_VERSION == 1)
				vLimit = Core.getLimitingVelocity(j.getRewindLengthSI(), m[i].accel, m[i].decel);
			if(MODEL_VERSION == 2)
				vLimit = Core.getLimitingVelocityV2(j.getRewindLengthSI(), vMach, m[i].accel, m[i].decel, m[i].max_rewind_rps, m[i].max_unwind_rps, j.getThicknessSI(), j.getRewindCoreSI(), j.getUnwindCoreSI(), j.getUnwindLengthSI());

			//System.out.println("vLimit: "+vLimit);
			
			vFinal = Core.getMaxVelocity(vMach, vLimit);
			//System.out.println("vFinal: "+vFinal);
			// Get run time of machine for a single set
			DataPoint[] res = null;
			if(MODEL_VERSION == 1)
				res = Core.MachineRunTime(j.getRewindLengthSI(), vFinal, m[i].accel, m[i].decel);
			else if(MODEL_VERSION == 2)
				res = Core.MachineRunTimeV2(j.getRewindLengthSI(), vFinal, m[i].accel, m[i].decel, m[i].max_rewind_rps, m[i].max_unwind_rps, j.getThicknessSI(), j.getRewindCoreSI(), j.getUnwindCoreSI(), j.getUnwindLengthSI());
			double time = res[res.length - 1].x;
			
			// Simple model: add additional time for less automated machines
			if(m[i].model.toString().equals("ER610") || m[i].model.toString().equals("Other"))
				time += p.TimeLoad;
			
			String name =  m[i].model.toString() +": "+ m[i].name;
			//String category1 = testMachine.model.toString() +": "+ testMachine.name;
			data.addValue(time, series1, name);
			
			
			//// TIMING DATA
			timeDataSeries[i] = new XYSeries(name);
			
			for(int z=0; z<res.length; ++z){
				DataPoint dp = res[z];
				timeDataSeries[i].add(dp.x, dp.y);
			}
			
			/*timeDataSeries[i].add(0.0, 0.0);
			timeDataSeries[i].add(Core.MachineRunTimeT1(j.RewindLength, vFinal, p.accel, p.decel), vFinal);
			timeDataSeries[i].add(Core.MachineRunTimeT2(j.RewindLength, vFinal, p.accel, p.decel), vFinal);
			timeDataSeries[i].add(time, 0.0);*/
		/*	
	        timeData.addSeries(timeDataSeries[i]);

		}
		
		//// DRAW TIMING DIAGRAM
		final JFreeChart chart = ChartFactory.createXYLineChart(
	            "Machine Run Timing Diagram",    // chart title
	            "Time (s)",                      // x axis label
	            "Linear Speed (m/s)",            // y axis label
	            timeData,                        // data
	            PlotOrientation.VERTICAL,
	            true,                            // include legend
	            true,                            // tooltips
	            false                            // urls
	        );
		
		timingGraph = chart;*/
		/*JFrame popGraph = new JFrame();
		
		ChartPanel cpanel = new ChartPanel(chart);
		cpanel.setPreferredSize(new java.awt.Dimension(500, 300));
		popGraph.setContentPane(cpanel);
		try{
		popGraph.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		popGraph.setTitle("Timing Diagram");
		popGraph.setSize(450, 300);
		
		popGraph.pack();
		popGraph.setVisible(true);
		popGraph.setLocationRelativeTo(null);*/
		//// END TIMING DIAGRAM
		
		/*results = new Numerics(0, 0, TimeDomain.Reel);
		
		return data;*/
	//}

}
