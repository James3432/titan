package atlas.kingj.roi;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.IntervalXYDataset;

public class testChart extends JFrame
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public testChart(String paramString)
  {
    super(paramString);
    JFreeChart chart = new JFreeChart("Job Schedule", createSubplot2(createDataset2()));
    chart.removeLegend();
    JPanel localJPanel = new ChartPanel(chart);
    localJPanel.setPreferredSize(new Dimension(500, 300));
    setContentPane(localJPanel);
  }

  private static XYPlot createSubplot2(IntervalXYDataset paramIntervalXYDataset)
  {
	NumberAxis localDateAxis = new NumberAxis("Time (%)");
    SymbolAxis localSymbolAxis = new SymbolAxis("Job", new String[] {"Job D", "Job C", "Job B", "Job A", "Total"});
    localSymbolAxis.setGridBandsVisible(false);
    XYBarRenderer localXYBarRenderer = new XYBarRenderer();
    localXYBarRenderer.setUseYInterval(true); // TODO: may not want this
    XYPlot localXYPlot = new XYPlot(paramIntervalXYDataset, localDateAxis, localSymbolAxis, localXYBarRenderer);
    return localXYPlot;
  }

  private static IntervalXYDataset createDataset2()
  {
	TaskSeriesCollection localTaskSeriesCollection = new TaskSeriesCollection();
    TaskSeries localTaskSeries1 = new TaskSeries("Set 1");
	localTaskSeries1.add(new Task("Task 1", new SimpleTimePeriod(0, 100)));
	   
    TaskSeries localTaskSeries2 = new TaskSeries("Set 2");
    localTaskSeries2.add(new Task("Task 1", new SimpleTimePeriod(0, 30)));
    
	TaskSeries localTaskSeries3 = new TaskSeries("Set 3");
	localTaskSeries3.add(new Task("Task 1", new SimpleTimePeriod(30, 45)));
	
	TaskSeries localTaskSeries4 = new TaskSeries("Set 4");
	localTaskSeries4.add(new Task("Task 1", new SimpleTimePeriod(45, 60)));
	
	TaskSeries localTaskSeries5 = new TaskSeries("Set 5");
	localTaskSeries5.add(new Task("Task 1", new SimpleTimePeriod(60, 100)));

	// Note ordering
	localTaskSeriesCollection.add(localTaskSeries5);
	localTaskSeriesCollection.add(localTaskSeries4);
	localTaskSeriesCollection.add(localTaskSeries3);
	localTaskSeriesCollection.add(localTaskSeries2);
	localTaskSeriesCollection.add(localTaskSeries1);
	    
    XYTaskDataset localXYTaskDataset = new XYTaskDataset(localTaskSeriesCollection);
    localXYTaskDataset.setTransposed(true);
    localXYTaskDataset.setSeriesWidth(0.6D);
    return localXYTaskDataset;
  }
 
}