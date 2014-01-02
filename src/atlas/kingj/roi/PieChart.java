package atlas.kingj.roi;

import java.awt.Toolkit;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PieDataset dataset;
	JFreeChart chart;
	
	public PieChart(String title, String charTitle){
		super(title);
		dataset = createDataset();
		chart = createChart(dataset, charTitle);
		ChartPanel cpanel = new ChartPanel(chart);
		cpanel.setPreferredSize(new java.awt.Dimension(500, 300));
		setContentPane(cpanel);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(FrmMain.class.getResource("/atlas/logo.png")));
		this.setTitle("Chart 1");
		this.setSize(450, 300);
	}
	
	private PieDataset createDataset(){
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("test1", 50);
		result.setValue("test2", 50);
		return result;
	}
	
	private JFreeChart createChart(PieDataset dataset, String title){
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
