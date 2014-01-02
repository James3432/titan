package atlas.kingj.roi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class TimingBreakdown {

	private JFrame frmOperatorTimingBreakdown;
	public JComboBox comboBox;
	private JFreeChart[] charts;
	ChartPanel chart;

	/**
	 * Create the application.
	 */
	public TimingBreakdown(JFreeChart[] bk, String[] ms) {
		initialize(bk, ms);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFreeChart[] breakdown, String[] ms) {
		frmOperatorTimingBreakdown = new JFrame();
		charts = breakdown;
		frmOperatorTimingBreakdown.getContentPane().setBackground(Color.WHITE);
		
		chart = new ChartPanel(breakdown[0]);
		frmOperatorTimingBreakdown.getContentPane().add(chart, BorderLayout.CENTER);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBox.getSelectedIndex();
				ChartPanel cpanel = new ChartPanel(charts[index]);
				frmOperatorTimingBreakdown.getContentPane().remove(chart);
				chart = cpanel;
				frmOperatorTimingBreakdown.getContentPane().add(chart, BorderLayout.CENTER);
				frmOperatorTimingBreakdown.getContentPane().validate();
				frmOperatorTimingBreakdown.getContentPane().repaint();
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 18));
		frmOperatorTimingBreakdown.getContentPane().add(comboBox, BorderLayout.SOUTH);
		comboBox.setMaximumRowCount(10);
		comboBox.setModel(new DefaultComboBoxModel(ms));
		frmOperatorTimingBreakdown.setTitle("Schedule Timing Breakdown");
		frmOperatorTimingBreakdown.setBounds(100, 100, 505, 377);
		frmOperatorTimingBreakdown.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		try{
			frmOperatorTimingBreakdown.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		frmOperatorTimingBreakdown.setBackground(Color.white);
		
		frmOperatorTimingBreakdown.setVisible(true);
	}
	
	public void setLocationRelativeTo(Component c){
		frmOperatorTimingBreakdown.setLocationRelativeTo(c);
	}

}
