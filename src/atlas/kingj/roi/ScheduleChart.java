package atlas.kingj.roi;


import java.awt.Dimension;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

import atlas.kingj.roi.JobSchedule.JobItem;

public class ScheduleChart extends JFrame
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	public JFreeChart getChart(){ return chart; }

public ScheduleChart(JobSchedule schedule)
  {
	super("Job Schedule Diagram");
	final IntervalXYDataset dataset = createDataset(schedule);
    chart = new JFreeChart("Job Schedule", createSubplot(dataset, schedule));
    chart.removeLegend();
    XYBarRenderer b = (XYBarRenderer) chart.getXYPlot().getRenderer();
    b.setBarPainter(new StandardXYBarPainter());
    chart.getXYPlot().getRenderer().setBaseToolTipGenerator(new XYToolTipGenerator() {
		@Override
		public String generateToolTip(XYDataset arg0, int arg1, int arg2) {
			double width = getWidth(dataset, arg1, arg2);
			return "Length: "+Integer.toString((int)Math.round(width))+"m";
		}
	});
    JPanel localJPanel = new ChartPanel(chart);
    localJPanel.setPreferredSize(new Dimension(500, 300));
    setContentPane(localJPanel);
  }

  private static XYPlot createSubplot(IntervalXYDataset paramIntervalXYDataset, JobSchedule schedule)
  {
	NumberAxis localDateAxis = new NumberAxis("Length (m)");
	String[] labels = new String[schedule.getUniqueSize()+1];
	labels[schedule.getUniqueSize()] = "Total";
	ListIterator<JobItem> it = schedule.getIterator();
	int i = schedule.getSize() - 1;
	while(it.hasNext()){
		Job job = it.next().j;
		int pos = schedule.getFirstOccurrence(job);
		if(pos == schedule.getSize() - 1 - i){
			labels[labels.length - 2 - schedule.getJobNum(job)] = job.getName();
		}
		i--;
	}

    SymbolAxis localSymbolAxis = new SymbolAxis("Job", labels);
    localSymbolAxis.setGridBandsVisible(false);
    XYBarRenderer localXYBarRenderer = new XYBarRenderer();
    localXYBarRenderer.setUseYInterval(true); // TODO: may not want this
    XYPlot localXYPlot = new XYPlot(paramIntervalXYDataset, localDateAxis, localSymbolAxis, localXYBarRenderer);
    return localXYPlot;
  }

  private static IntervalXYDataset createDataset(JobSchedule schedule)
  {
	ListIterator<JobItem> it = schedule.getIterator();
	
	TaskSeriesCollection TaskCollection = new TaskSeriesCollection();
	
	TaskSeries[] tasks = new TaskSeries[schedule.getUniqueSize()+1];
	
	tasks[0] = new TaskSeries("Set 1");
	tasks[0].add(new Task("Task 1", new SimpleTimePeriod(0, Math.round(schedule.getTotalLength()))));
	
	int i = 1, uniques = 1;
	Double current_length = 0.;
	
	while(it.hasNext()){
		JobItem j = it.next();
		Double length = j.j.getTotLengthSI();
		Double start, fin;
		
		start = current_length;
		fin = current_length + length;
		current_length = fin;
		
		long begin = Math.round(start);
		long end = Math.round(fin);
		
		int pos = schedule.getFirstOccurrence(j.j);
		
		if(pos == i-1)
			tasks[uniques++] = new TaskSeries("Set "+Integer.toString(i));
		tasks[schedule.getJobNum(j.j) + 1].add(new Task("Task "+Integer.toString(i), new SimpleTimePeriod(begin, end)));
		i++;
	}
	
	// must add in reverse order
	for(int j = schedule.getUniqueSize(); j >= 0; j--)
		TaskCollection.add(tasks[j]);
	    
    XYTaskDataset localXYTaskDataset = new XYTaskDataset(TaskCollection);
    localXYTaskDataset.setTransposed(true);
    localXYTaskDataset.setSeriesWidth(0.6D);
    return localXYTaskDataset;
  }
  
  private static double getWidth(IntervalXYDataset data, int row, int col){
	  double width = data.getEndXValue(row, col) - data.getStartXValue(row, col);
	  return width;
  }
 
}