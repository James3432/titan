package atlas.kingj.roi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import jxl.Workbook;
import jxl.biff.SheetRangeImpl;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import atlas.kingj.roi.Job.KnifeType;
import atlas.kingj.roi.JobSchedule.JobItem;
import atlas.kingj.roi.ROIData.EnergyData;
import atlas.kingj.roi.ROIData.MaintData;
import atlas.kingj.roi.ResultSet.Result;
import atlas.kingj.roi.ResultSet.ResultTime;
import atlas.kingj.roi.ResultSet.ResultType;
import atlas.kingj.roi.ResultSetROI.ResultROI;

/*
 *  Main class for the Titan Production Calculator
 */
public class FrmMain {

	private JFrame frmTitanRoiCalculator;
	/**
	 * @wbp.nonvisual location=42,549
	 */
	private final ButtonGroup btnsUnits = new ButtonGroup();
	private final ButtonGroup rdbtnsMachines = new ButtonGroup();
	private final ButtonGroup rdbtnsPower = new ButtonGroup();
	
	private boolean metric = true;
	
	private boolean formReady = false;
	private boolean jobFormReady = false;
	private boolean initialising = true;
	
	Set<String> machNames;
	Set<String> jobNames;

	private JButton btnMachDelete;
	public JRadioButton rdbtnER610;
	public JTextField txtMachName;
	private JLabel lblMachName;
	public JCheckBox chckbxFlag;
	public JComboBox cmbCorepos;
	public JComboBox cmbKnives;
	private JLabel lblKnifeControl;
	private JLabel lblCorePositioning;
	public JCheckBox chckbxSpliceTable;
	public JCheckBox chckbxAlignmentGuide;
	public JCheckBox chckbxRollConditioning;
	public JCheckBox chckbxTurretSupport;
	public JCheckBox chckbxAutostripping;
	public JCheckBox chckbxExtraRewind;
	public JCheckBox chckbxAutoCutoff;
	public JCheckBox chckbxAutoTapeCore;
	public JCheckBox chckbxAutoTapeTail;
	public JComboBox cmbUnloader;
	private JLabel lblUnloader;
	public JComboBox cmbUnwindDrive;
	private JLabel lblUnwindDrive;
	public JComboBox cmbRewindCtrl;
	private JLabel lblRewindControlLoop;
	private JLabel lblSpeed;
	public JComboBox cmbSpeed;
	private JPanel pnlResults;
	private JPanel pnlProdGraph;
	JList listMachines;
	private JButton btnMachUp;
	private JButton btnMachDown;
	private JButton btnMachReset;
	public JRadioButton rdbtnSR9DS;
	public JRadioButton rdbtnSR9DT;
	private JPanel grpMachines;
	private JPanel grpVariants;
	private JPanel grpOptions;
	private JButton btnCustomMachine;
	
	OBJfilter objfilter;
	
	DefaultListModel jobModel;
	DefaultListModel listModel;
	DefaultListModel scheduleModel;
	
	public int MODEL_VERSION = 2;
	
	ChartPanel pnlGraph;
	JFreeChart chart;
	DefaultPieDataset dataset;
	JFreeChart[] breakdown;
	
	int oldUnwindIndex = 0;
	int oldRewindIndex = 0;
	int oldTotalsIndex = 0;
	int SetReelType = 0;
	int jobIndex = 0;
	int oldMachineIndex = 0;
	
	Comparator comp;
	Job job;
	Production environment;
	ResultSet results;
	ResultSetROI RoiResults;
	ROIData RoiData;
	
	Machine machine;
	
	OpenSaveFileChooser fc = new OpenSaveFileChooser();
	
	public class OpenSaveFileChooser extends JFileChooser {
		
		private static final long serialVersionUID = 1L;
		public int type; // 1=ser, 2=png, 3=xls

		@Override
	    public void approveSelection(){
	        File f = getSelectedFile();
	        if((f.exists() || (addExt(f,".png").exists() && type==2) || (addExt(f,".ser").exists() && type==1) || (addExt(f,".xls").exists() && type==3)) && getDialogType() == SAVE_DIALOG){
	            int result = JOptionPane.showConfirmDialog(this,"The file already exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
	            switch(result){
	                case JOptionPane.YES_OPTION:
	                    super.approveSelection();
	                    return;
	                case JOptionPane.NO_OPTION:
	                    return;
	                case JOptionPane.CLOSED_OPTION:
	                    return;
	                case JOptionPane.CANCEL_OPTION:
	                    cancelSelection();
	                    return;
	            }
	        }
	        super.approveSelection();
	    }
	
	}

	Machine.Model model = Machine.Model.ER610;
	
	private JLabel lblOutputLength;
	private JLabel lblOutputWeight;
	JLabel lblStatus;
	public JComboBox cmbMaterials;
	private JLabel lblPresets;
    public JLabel lblmmin;
	private JLabel lblThickness_1;
	private JLabel lblDensity_1;
	public JTextField txtThickness;
	public JTextField txtDensity;
	private JPanel pnlUnwinds;
	public JTextField txtUnwindAmount;
	private JLabel lblSlitCount;
	private JLabel lblTrimtotal;
	public JTextField txtSlits;
	public JTextField txtSlitWidth;
	public JTextField txtRewindAmount;
	public JLabel lblTrim;
	public JTextField txtShiftLength;
	public JTextField txtShiftCount;
	public JTextField txtDaysYear;
	public JComboBox cmbTimeRef;
	public JLabel lblPer;
	public JLabel lblWebWidthmm;
	public JLabel lblRewindCoremm;
	public JComboBox cmbRewindCore;
	public JLabel lblUnwindCoremm;
	public JComboBox cmbUnwindCore;
	private JLabel lblmicro0;
	private JLabel lblgm3;
	private JLabel label_3;
	private JLabel lblPer_1;
	public JComboBox cmbJobDomain;
	private JLabel lblmm3;
	private JLabel lblmm2;
	private JLabel lblmm0;
	private JLabel lblmm1;
	private JLabel lblDayLength2;
	private JLabel lblHoursYear;
	private JLabel lblHoursYear2;
	public JLabel lblHrs;
	public JList listCompare;
	public JLabel picLabel;
	public JLabel lblNoGraph;
	public JLabel lblNoGraph2;
	public JLabel lblNoGraph3;
	public JLabel lblNoGraph4;
	
	private static FrmMain window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FrmMain();
					window.frmTitanRoiCalculator.setVisible(true);
					window.btnNewMachine.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmTitanRoiCalculator = new JFrame();
		frmTitanRoiCalculator.addWindowListener(new FrmTitanRoiCalculatorWindowListener());
		frmTitanRoiCalculator.setResizable(false);
		frmTitanRoiCalculator.setTitle("Titan Production Calculator");
		try{
			frmTitanRoiCalculator.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));//Toolkit.getDefaultToolkit().getImage(FrmMain.class.getResource("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frmTitanRoiCalculator.setBounds(100, 100, 800, 600);
		frmTitanRoiCalculator.setLocationRelativeTo(null);
		frmTitanRoiCalculator.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmTitanRoiCalculator.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		mntmOpen = new JMenuItem("Open");
		mntmOpen.setMnemonic('O');
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmOpen.addActionListener(new MntmOpenActionListener());
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setMnemonic('S');
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmSave.addActionListener(new MntmSaveActionListener());
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic('X');
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAndClose();
			}
		});
		
		mntmSaveAll = new JMenuItem("Save All");
		mntmSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAll();
			}
		});
		
		mntmOpenAll = new JMenuItem("Open All");
		mntmOpenAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenAll();
			}
		});
		mnFile.add(mntmOpenAll);
		mntmSaveAll.setMnemonic('V');
		mnFile.add(mntmSaveAll);
		
		mnExport = new JMenu("Export");
		mnFile.add(mnExport);
		
		mntmSpreadsheet = new JMenuItem("Spreadsheet");
		mntmSpreadsheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResetStatusLabel();
				
				fc = new OpenSaveFileChooser();
				fc.setFileFilter(new XLSfilter());
				fc.type = 3;
				
				int returnVal = fc.showSaveDialog(frmTitanRoiCalculator);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
		            File file = fc.getSelectedFile();
		            
		            String path = file.getAbsolutePath();

		            String extension = ".xls";

	            	if(!path.endsWith(extension))
		            {
		              file = new File(path + extension);
		            }
                    
	            	StatusDialog a = new StatusDialog("Generating spreadsheet...");
	            	
	            	SaveFileWorker worker = new SaveFileWorker(file, a, false);
		            
		            worker.execute();
	            	
		            a.setLocationRelativeTo(frmTitanRoiCalculator);
                    a.setVisible(true);
		            
                    if(FileSaveError)
                    	ShowMessage("File save error");
                    else
                    	if(FileSaveWarning)
                    		ShowMessage("File saved, but possible errors.");
                    	else
                    		ShowMessageSuccess("File saved.");

				}
			}
		});
		mnExport.add(mntmSpreadsheet);
		mnFile.add(mntmExit);
		
		JMenu mnView = new JMenu("View");
		mnView.setMnemonic('V');
		menuBar.add(mnView);
		
		RoiData = new ROIData();
		
		mntmUnitConverter = new JMenuItem("Unit Converter");
		mntmUnitConverter.setMnemonic('C');
		mntmUnitConverter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		mntmUnitConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UnitConverter();
			}
		});
		mnView.add(mntmUnitConverter);
		
		mnSettings = new JMenu("Settings");
		mnSettings.setMnemonic('E');
		menuBar.add(mnSettings);
		
		mnUnits = new JMenu("Units");
		mnUnits.setMnemonic('U');
		mnSettings.add(mnUnits);
		
		rdbtnmntmImperial = new JRadioButtonMenuItem("Imperial");
		rdbtnmntmImperial.setMnemonic('I');
		rdbtnmntmImperial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		rdbtnmntmImperial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(metric){
					// Change units from metric to imperial
					ChangeUnits();
				}
			}
		});
		mnUnits.add(rdbtnmntmImperial);
		
		rdbtnmntmMetric = new JRadioButtonMenuItem("Metric");
		rdbtnmntmMetric.setMnemonic('M');
		rdbtnmntmMetric.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		rdbtnmntmMetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!metric){
					// Change units from imperial to metric
					ChangeUnits();
				}
			}
		});
		rdbtnmntmMetric.setSelected(true);
		mnUnits.add(rdbtnmntmMetric);
		
		btnsUnits.add(rdbtnmntmMetric);
		btnsUnits.add(rdbtnmntmImperial);
		
		JMenuItem mntmOptions_1 = new JMenuItem("Options");
		mntmOptions_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mntmOptions_1.setMnemonic('O');
		mntmOptions_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Machine machine = null;
				if(listMachines.getSelectedIndex() > -1)
					machine = (Machine) listMachines.getSelectedValue();
				OptionDialog options = new OptionDialog(environment, machine);
				options.setLocationRelativeTo(frmTitanRoiCalculator);
				options.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosed(WindowEvent e) {
				        UpdateAnalysis();
				    }
				});
				options.setVisible(true);
			}
		});
		
		mnTimings = new JMenu("Timings");
		mnSettings.add(mnTimings);
		
		mntmSaveToFile = new JMenuItem("Save to File");
		mntmSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OperatorTimings times = environment.timings;

				fc = new OpenSaveFileChooser();
				fc.setFileFilter(new OBJfilter(3));
    			fc.type = 1;
    			
    			int returnVal = fc.showSaveDialog(frmTitanRoiCalculator);
    			if (returnVal == JFileChooser.APPROVE_OPTION) {
    				
    	            File file = fc.getSelectedFile();
    	            
    	            String path = file.getAbsolutePath();

    	            String extension = ".ser";

    	            if(!path.endsWith(extension))
    	            {
    	              file = new File(path + extension);
    	            }

    	            try {
    	            	FileOutputStream fout = new FileOutputStream(file);
    	                ObjectOutputStream oos = new ObjectOutputStream(fout);
    	                oos.writeObject(times);
    	                oos.close();
    	                ShowMessageSuccess("File saved.");
    				} catch (Exception e1) {
    					JOptionPane.showMessageDialog(frmTitanRoiCalculator, "Error writing file.", "Error", JOptionPane.ERROR_MESSAGE);
    				}
    			}
			}
		});
		mnTimings.add(mntmSaveToFile);
		
		mntmLoadFromFile = new JMenuItem("Load from File");
		mntmLoadFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fc = new OpenSaveFileChooser();
				fc.setFileFilter(new OBJfilter(3));
    			fc.type = 1;
				
				int returnVal = fc.showOpenDialog(frmTitanRoiCalculator);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
		            File file = fc.getSelectedFile();
		            
		            try {
		            	FileInputStream fin = new FileInputStream(file);
		                ObjectInputStream ois = new ObjectInputStream(fin);
		                environment.timings = (OperatorTimings) ois.readObject();
		                ois.close();
		                
		                
		                ShowMessageSuccess("File loaded.");
					
					} catch (Exception e1) {
						ShowMessage("File error.");
					} 
				}
			}
		});
		mnTimings.add(mntmLoadFromFile);
		mnSettings.add(mntmOptions_1);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		menuBar.add(mnHelp);
		
		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.setMnemonic('I');
		mntmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InstructDialog instructions = new InstructDialog();
				instructions.setLocationRelativeTo(frmTitanRoiCalculator);
				instructions.setVisible(true);
			}
		});
		mnHelp.add(mntmInstructions);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setMnemonic('A');
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog about = new AboutDialog();
				about.setLocationRelativeTo(frmTitanRoiCalculator);
				about.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(TabChangeListener);
		frmTitanRoiCalculator.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		pnlMachine = new JPanel();
		tabbedPane.addTab("Machine Selection", null, pnlMachine, "Add and configure machine setups");
		tabbedPane.setEnabledAt(0, true);
		pnlMachine.setLayout(null);
		
		grpMachines = new JPanel();
		grpMachines.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grpMachines.setBounds(20, 72, 182, 256);
		grpMachines.setBorder(new TitledBorder(null, "Machine Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMachine.add(grpMachines);
		grpMachines.setLayout(null);
		
		try{
			rdbtnER610 = new JRadioButton(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610.png"))));
			rdbtnER610.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_dis.png"))));
			rdbtnER610.setDisabledSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_dis.png"))));
			rdbtnER610.setEnabled(false);
			rdbtnER610.setToolTipText("Titan ER610");
		}catch(NullPointerException e1){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		rdbtnER610.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnClick("ER610");
				UpdateMachine();
				listMachines.repaint();
			}
		});
		rdbtnER610.setSelected(true);
		rdbtnER610.setBounds(13, 24, 155, 40);
		try{
			rdbtnER610.setPressedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_down.png"))));
			rdbtnER610.setRolloverEnabled(true);
			rdbtnER610.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_over.png"))));
			rdbtnER610.setSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_select.png"))));
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		grpMachines.add(rdbtnER610);
		
		try{
		rdbtnSR9DS = new JRadioButton(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds.png"))));
		rdbtnSR9DS.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_dis.png"))));
		rdbtnSR9DS.setEnabled(false);
		rdbtnSR9DS.setToolTipText("Titan SR9-DS");
	}catch(NullPointerException e11){
		System.out.println("Image load error");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
		rdbtnSR9DS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnClick("SR9DS");
				UpdateMachine();
				listMachines.repaint();
			}
		});
		
		try{
		rdbtnSR9DS.setPressedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_down.png"))));
		rdbtnSR9DS.setRolloverEnabled(true);
		rdbtnSR9DS.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_over.png"))));
		rdbtnSR9DS.setSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_select.png"))));
		rdbtnSR9DS.setBounds(13, 68, 155, 40);
		grpMachines.add(rdbtnSR9DS);
		
		rdbtnSR9DT = new JRadioButton(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt.png"))));
		rdbtnSR9DT.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_dis.png"))));
		rdbtnSR9DT.setEnabled(false);
		rdbtnSR9DT.setToolTipText("Titan SR9-DT");
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		rdbtnSR9DT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnClick("SR9DT");
				UpdateMachine();
				listMachines.repaint();
			}
		});
		rdbtnSR9DT.setBounds(13, 112, 155, 40);
		try{
		rdbtnSR9DT.setPressedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_down.png"))));
		rdbtnSR9DT.setRolloverEnabled(true);
		rdbtnSR9DT.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_over.png"))));
		rdbtnSR9DT.setSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_select.png"))));
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		grpMachines.add(rdbtnSR9DT);
		
		
		try {
			rdbtnSR800 = new JRadioButton(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800.png"))));
			rdbtnSR800.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_dis.png"))));
			rdbtnSR800.setEnabled(false);
			rdbtnSR800.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rdbtnClick("SR800");
					UpdateMachine();
					listMachines.repaint();
				}
			});
			rdbtnSR800.setPressedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_down.png"))));
			rdbtnSR800.setRolloverEnabled(true);
			rdbtnSR800.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_over.png"))));
			rdbtnSR800.setSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_select.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		rdbtnSR800.setToolTipText("Titan SR800");
		rdbtnSR800.setRolloverEnabled(true);
		rdbtnSR800.setBounds(13, 156, 155, 40);
		grpMachines.add(rdbtnSR800);
		
		try {
			rdbtnCustom = new JRadioButton(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom.png"))));
			rdbtnCustom.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_dis.png"))));
			rdbtnCustom.setEnabled(false);
			rdbtnCustom.setPressedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_down.png"))));
			rdbtnCustom.setRolloverEnabled(true);
			rdbtnCustom.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_over.png"))));
			rdbtnCustom.setSelectedIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_select.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		rdbtnCustom.setToolTipText("Custom Machine");
		rdbtnCustom.setRolloverEnabled(true);
		rdbtnCustom.setBounds(13, 200, 155, 40);
		grpMachines.add(rdbtnCustom);
		rdbtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnClick("Custom");
				UpdateMachine();
				ResetStatusLabel();
				listMachines.repaint();
			}
		});
		
		rdbtnsMachines.add(rdbtnER610);
		rdbtnsMachines.add(rdbtnSR9DS);
		rdbtnsMachines.add(rdbtnSR9DT);
		rdbtnsMachines.add(rdbtnSR800);
		rdbtnsMachines.add(rdbtnCustom);
		
		grpVariants = new JPanel();
		grpVariants.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grpVariants.setBounds(20, 339, 482, 112);
		grpVariants.setBorder(new TitledBorder(null, "Variants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMachine.add(grpVariants);
		grpVariants.setLayout(null);
		
		cmbCorepos = new JComboBox();
		cmbCorepos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbCorepos.setToolTipText("Set the core positioning system");
		cmbCorepos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbCorepos.setEnabled(false);
		cmbCorepos.setBounds(104, 70, 122, 20);
		grpVariants.add(cmbCorepos);
		cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser"}));
		
		lblCorePositioning = new JLabel("Core Positioning:");
		lblCorePositioning.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCorePositioning.setToolTipText("Set the core positioning system");
		lblCorePositioning.setEnabled(false);
		lblCorePositioning.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCorePositioning.setBounds(12, 73, 88, 14);
		grpVariants.add(lblCorePositioning);
		
		lblKnifeControl = new JLabel("Knife Control:");
		lblKnifeControl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblKnifeControl.setToolTipText("Set the type of knife positioning system");
		lblKnifeControl.setEnabled(false);
		lblKnifeControl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKnifeControl.setBounds(22, 48, 78, 14);
		grpVariants.add(lblKnifeControl);
		
		cmbKnives = new JComboBox();
		cmbKnives.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbKnives.setToolTipText("Set the type of knife positioning system");
		cmbKnives.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbKnives.setEnabled(false);
		cmbKnives.setBounds(104, 45, 122, 20);
		grpVariants.add(cmbKnives);
		cmbKnives.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Auto"}));
		
		cmbUnloader = new JComboBox();
		cmbUnloader.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbUnloader.setToolTipText("Set the unloader type");
		cmbUnloader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbUnloader.setEnabled(false);
		cmbUnloader.setBounds(350, 20, 122, 20);
		grpVariants.add(cmbUnloader);
		cmbUnloader.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Pneumatic", "Electric"}));
		
		lblUnloader = new JLabel("Unloader:");
		lblUnloader.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnloader.setToolTipText("Set the unloader type");
		lblUnloader.setEnabled(false);
		lblUnloader.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnloader.setBounds(259, 23, 87, 14);
		grpVariants.add(lblUnloader);
		
		cmbSpeed = new JComboBox();
		cmbSpeed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbSpeed.setEnabled(false);
		cmbSpeed.setToolTipText("Machine top speed in metres/min");
		cmbSpeed.setBounds(104, 20, 122, 20);
		grpVariants.add(cmbSpeed);
		cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"450", "550"}));
		
		lblSpeed = new JLabel("Speed:");
		lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpeed.setToolTipText("Machine top speed in metres/min");
		lblSpeed.setEnabled(false);
		lblSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSpeed.setBounds(54, 23, 46, 14);
		grpVariants.add(lblSpeed);
		
		cmbUnwindDrive = new JComboBox();
		cmbUnwindDrive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbUnwindDrive.setToolTipText("Unwind drive type");
		cmbUnwindDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbUnwindDrive.setEnabled(false);
		cmbUnwindDrive.setBounds(350, 45, 122, 20);
		grpVariants.add(cmbUnwindDrive);
		cmbUnwindDrive.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double"}));
		
		lblUnwindDrive = new JLabel("Unwind Drive:");
		lblUnwindDrive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnwindDrive.setToolTipText("Unwind drive type");
		lblUnwindDrive.setEnabled(false);
		lblUnwindDrive.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnwindDrive.setBounds(259, 48, 87, 14);
		grpVariants.add(lblUnwindDrive);
		
		lblRewindControlLoop = new JLabel("Rewind Control Loop:");
		lblRewindControlLoop.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRewindControlLoop.setToolTipText("Rewind control loop type");
		lblRewindControlLoop.setEnabled(false);
		lblRewindControlLoop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRewindControlLoop.setBounds(224, 73, 122, 14);
		grpVariants.add(lblRewindControlLoop);
		
		cmbRewindCtrl = new JComboBox();
		cmbRewindCtrl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbRewindCtrl.setToolTipText("Rewind control loop type");
		cmbRewindCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
			}
		});
		cmbRewindCtrl.setEnabled(false);
		cmbRewindCtrl.setBounds(350, 70, 122, 20);
		grpVariants.add(cmbRewindCtrl);
		cmbRewindCtrl.setModel(new DefaultComboBoxModel(new String[] {"Open", "Closed"}));
		
		listModel = new DefaultListModel();
		jobModel = new DefaultListModel();
		scheduleModel = new DefaultListModel();
		listModel.removeAllElements();
		/*listMachines.setModel(new AbstractListModel() {
			String[] values = new String[] {"ER610: My test 1", "SR9-DS: this is another test", "SR9-DT: third test", "ER610:  test 2", "ER610: bla bla", "SR9-DS: this is another test", "SR9-DT: hello", "SR9-DT: third test"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});*/
		
		JLabel lblMachines = new JLabel("Machines");
		lblMachines.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMachines.setBounds(522, 19, 85, 14);
		pnlMachine.add(lblMachines);
		
		btnMachDelete = new JButton("");
		try{
			btnMachDelete.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete.png"))));
			btnMachDelete.setRolloverEnabled(true);
			btnMachDelete.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_over.png"))));
			btnMachDelete.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnMachDelete.setToolTipText("Delete machine");
		btnMachDelete.setEnabled(false);
		btnMachDelete.addActionListener(new DeleteButtonListener());
		btnMachDelete.setBounds(651, 460, 36, 36);
		pnlMachine.add(btnMachDelete);
		
		btnMachUp = new JButton("");
		btnMachUp.setToolTipText("Move machine up");
		btnMachUp.setEnabled(false);
		try{
		btnMachUp.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up.png"))));
		btnMachUp.setRolloverEnabled(true);
		btnMachUp.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_over.png"))));
		btnMachUp.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_dis.png"))));
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnMachUp.addActionListener(new UpListener());
		btnMachUp.setBounds(700, 463, 30, 30);
		pnlMachine.add(btnMachUp);
		
		btnMachDown = new JButton("");
		btnMachDown.setToolTipText("Move machine down");
		btnMachDown.setEnabled(false);
		try{
		btnMachDown.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down.png"))));
		btnMachDown.setRolloverEnabled(true);
		btnMachDown.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_over.png"))));
		btnMachDown.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_dis.png"))));
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnMachDown.addActionListener(new DownListener());
		btnMachDown.setBounds(737, 463, 30, 30);
		pnlMachine.add(btnMachDown);
		
		btnMachReset = new JButton("Reset");
		btnMachReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMachReset.setEnabled(false);
		btnMachReset.setToolTipText("Reset the form");
		btnMachReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResetForm();
			}
		});
		
		btnMachReset.setBounds(20, 460, 100, 36);
		pnlMachine.add(btnMachReset);
		/*txtMachName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();  
				if (e.getKeyCode() == KeyEvent.VK_ENTER)  
					btnAddMachine.doClick();
			}
		});*/
		
		btnNewMachine = new JButton("Add New");
		btnNewMachine.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewMachine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formReady = false;
				
				ResetStatusLabel();
				
				int index = listMachines.getSelectedIndex();
			    int size = listModel.getSize();
			    
			    if(size >= Consts.MACH_LIST_LIMIT){    // Max list size
			    	ShowMessage("Maximum number of machines allocated. Please delete before attempting to add more.");
			    	return;
			    }
			    
			    String newName = getUniqueName("Machine");
			    txtMachName.setText(newName);
			    machine = new Machine(newName); 
			    machNames.add(newName.toLowerCase());
			    
			    //If no selection or if item in last position is selected,
			    //add the new one to end of list, and select new one.
			    if (index == -1 || (index+1 == size)) {
			    	
			        listModel.addElement(machine);
			        listMachines.setSelectedIndex(size);
			
			        RoiData.energies.add(RoiData.new EnergyData());
			        RoiData.maintenance.add(RoiData.new MaintData());
			        listCompare.addSelectionInterval(size, size);
			        
			    //Otherwise insert the new one after the current selection,
			    //and select new one.
			    } else {
			    	int[] sel = listCompare.getSelectedIndices();
			    	int[] selection = new int[sel.length + 1];
			    	System.arraycopy(sel, 0, selection, 0, sel.length);
			    	listCompare.setSelectedIndices(new int[]{});
			    	listModel.insertElementAt(machine, index + 1);
			        
			    	boolean max = false;
			        for(int i=0; i<selection.length; i++){
			        	if(selection[i] >= index + 1 && !max){
			        		if(i < selection.length - 1)
			        			System.arraycopy(selection, i, selection, i+1, selection.length - i - 1);
			        		selection[i] = index + 1;
			        		max = true;
			        	}
			        	else if(selection[i] >= index + 1)
			        		selection[i] = selection[i] + 1;
			        }
			        RoiData.energies.add(index + 1, RoiData.new EnergyData());
			        RoiData.maintenance.add(index + 1, RoiData.new MaintData());
			        listCompare.setSelectedIndices(selection);
			        //listCompareRoi.setSelectedIndices(selection);
			        listMachines.setSelectedIndex(index+1);
			        //listCompare.addSelectionInterval(index + 1, index + 1);
			    }
			    
			    ResetStatusLabel();
			    //UpdateForm();  already triggered in listchanged
			    formReady = true;
			    txtMachName.requestFocusInWindow();
			}
		});
		try {
			btnNewMachine.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/plus.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnNewMachine.setToolTipText("Add new machine");
		btnNewMachine.setBounds(522, 460, 110, 36);
		pnlMachine.add(btnNewMachine);
		
		grpOptions = new JPanel();
		grpOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grpOptions.setBounds(212, 72, 290, 256);
		pnlMachine.add(grpOptions);
		grpOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		grpOptions.setLayout(null);
		
		chckbxFlag = new JCheckBox("Flag Detection");
		chckbxFlag.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFlag.setToolTipText("Whether an automatic flag detection system is used");
		chckbxFlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxFlag.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxFlag.setEnabled(false);
		chckbxFlag.setBounds(155, 104, 109, 23);
		grpOptions.add(chckbxFlag);
		
		chckbxSpliceTable = new JCheckBox("Splice Table");
		chckbxSpliceTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxSpliceTable.setToolTipText("Whether a splice table is fitted");
		chckbxSpliceTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxSpliceTable.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxSpliceTable.setEnabled(false);
		chckbxSpliceTable.setBounds(22, 104, 109, 23);
		grpOptions.add(chckbxSpliceTable);
		
		chckbxAlignmentGuide = new JCheckBox("Alignment Guide");
		chckbxAlignmentGuide.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAlignmentGuide.setToolTipText("Whether an alignment guide is fitted");
		chckbxAlignmentGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxAlignmentGuide.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxAlignmentGuide.setEnabled(false);
		chckbxAlignmentGuide.setBounds(22, 130, 127, 23);
		grpOptions.add(chckbxAlignmentGuide);
		
		chckbxRollConditioning = new JCheckBox("Roll Conditioning");
		chckbxRollConditioning.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxRollConditioning.setToolTipText("Whether the machine supports roll conditioning");
		chckbxRollConditioning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxRollConditioning.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxRollConditioning.setEnabled(false);
		chckbxRollConditioning.setBounds(22, 156, 127, 23);
		grpOptions.add(chckbxRollConditioning);
		
		chckbxTurretSupport = new JCheckBox("Turret Support");
		chckbxTurretSupport.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxTurretSupport.setToolTipText("For dual turret machines: extra turret support");
		chckbxTurretSupport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxTurretSupport.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxTurretSupport.setEnabled(false);
		chckbxTurretSupport.setBounds(22, 182, 127, 23);
		grpOptions.add(chckbxTurretSupport);
		
		chckbxAutostripping = new JCheckBox("Autostripping");
		chckbxAutostripping.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutostripping.setToolTipText("Whether an autostripping feature is present for reel unloading");
		chckbxAutostripping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxAutostripping.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxAutostripping.setEnabled(false);
		chckbxAutostripping.setBounds(22, 208, 97, 23);
		grpOptions.add(chckbxAutostripping);
		
		chckbxExtraRewind = new JCheckBox("850mm Rewind");
		chckbxExtraRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxExtraRewind.setToolTipText("Extra wide 850mm max diameter rewind support");
		chckbxExtraRewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxExtraRewind.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxExtraRewind.setEnabled(false);
		chckbxExtraRewind.setBounds(155, 208, 109, 23);
		grpOptions.add(chckbxExtraRewind);
		
		chckbxAutoCutoff = new JCheckBox("Auto Cut-off");
		chckbxAutoCutoff.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutoCutoff.setToolTipText("Whether the web is cut automatically when a run completes");
		chckbxAutoCutoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxAutoCutoff.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxAutoCutoff.setEnabled(false);
		chckbxAutoCutoff.setBounds(155, 130, 109, 23);
		grpOptions.add(chckbxAutoCutoff);
		
		chckbxAutoTapeCore = new JCheckBox("Auto Tape Core");
		chckbxAutoTapeCore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutoTapeCore.setToolTipText("Whether new reels are automatically taped before a run begins");
		chckbxAutoTapeCore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxAutoTapeCore.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxAutoTapeCore.setEnabled(false);
		chckbxAutoTapeCore.setBounds(155, 156, 109, 23);
		grpOptions.add(chckbxAutoTapeCore);
		
		chckbxAutoTapeTail = new JCheckBox("Auto Tape Tail");
		chckbxAutoTapeTail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutoTapeTail.setToolTipText("Whether the tails of completed reels are automatically taped down");
		chckbxAutoTapeTail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formReady)
					UpdateMachine();
				if(!chckbxAutoTapeTail.isSelected())
					chckbxSelectAll.setSelected(false);
				else
					UpdateSelectAllChckbx();
			}
		});
		chckbxAutoTapeTail.setEnabled(false);
		chckbxAutoTapeTail.setBounds(155, 182, 109, 23);
		grpOptions.add(chckbxAutoTapeTail);
		
		txtMachName = new JTextField();
		txtMachName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMachName.selectAll();
			}
		});
		txtMachName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateMachineName();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateMachineName();
			}
		});
		txtMachName.setEnabled(false);
		txtMachName.setBounds(125, 35, 137, 28);
		grpOptions.add(txtMachName);
		txtMachName.getDocument().addDocumentListener(new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
		}
		public void removeUpdate(DocumentEvent e) {
		// text was deleted
		}
		public void insertUpdate(DocumentEvent e) {
		// text was inserted
		}
		});
		
			txtMachName.setToolTipText("Enter a name to refer to this particular machine by");
			txtMachName.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtMachName.setColumns(10);
			
			lblMachName = new JLabel("Machine name:");
			lblMachName.setToolTipText("Enter a name to refer to this particular machine by");
			lblMachName.setEnabled(false);
			lblMachName.setBounds(26, 36, 91, 24);
			grpOptions.add(lblMachName);
			lblMachName.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			chckbxSelectAll = new JCheckBox("Select All");
			chckbxSelectAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
			chckbxSelectAll.setToolTipText("Select all available options");
			chckbxSelectAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxSelectAll.isSelected()){
						if(chckbxAlignmentGuide.isEnabled())
							chckbxAlignmentGuide.setSelected(true);
						if(chckbxAutoCutoff.isEnabled())
							chckbxAutoCutoff.setSelected(true);
						if(chckbxAutostripping.isEnabled())
							chckbxAutostripping.setSelected(true);
						if(chckbxAutoTapeCore.isEnabled())
							chckbxAutoTapeCore.setSelected(true);
						if(chckbxAutoTapeTail.isEnabled())
							chckbxAutoTapeTail.setSelected(true);
						if(chckbxExtraRewind.isEnabled())
							chckbxExtraRewind.setSelected(true);
						if(chckbxFlag.isEnabled())
							chckbxFlag.setSelected(true);
						if(chckbxRollConditioning.isEnabled())
							chckbxRollConditioning.setSelected(true);
						if(chckbxSpliceTable.isEnabled())
							chckbxSpliceTable.setSelected(true);
						if(chckbxTurretSupport.isEnabled())
							chckbxTurretSupport.setSelected(true);
					}else{
						chckbxAlignmentGuide.setSelected(false);
						chckbxAutoCutoff.setSelected(false);
						chckbxAutostripping.setSelected(false);
						chckbxAutoTapeCore.setSelected(false);
						chckbxAutoTapeTail.setSelected(false);
						chckbxExtraRewind.setSelected(false);
						chckbxFlag.setSelected(false);
						chckbxRollConditioning.setSelected(false);
						chckbxSpliceTable.setSelected(false);
						chckbxTurretSupport.setSelected(false);
					}
					
					if(formReady)
						UpdateMachine();
				}
			});
			chckbxSelectAll.setEnabled(false);
			chckbxSelectAll.setBounds(22, 78, 97, 23);
			grpOptions.add(chckbxSelectAll);
			
			lblMachineConfiguration = new JLabel("Machine Configuration");
			lblMachineConfiguration.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblMachineConfiguration.setBounds(29, 18, 269, 22);
			pnlMachine.add(lblMachineConfiguration);
			
			lblAddNewMachines = new JLabel("Add new machines to the list on the right, then configure their options and variants below");
			lblAddNewMachines.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblAddNewMachines.setBounds(29, 45, 433, 14);
			pnlMachine.add(lblAddNewMachines);
			
			btnCustomMachine = new JButton("Custom Machine Options");
			btnCustomMachine.setEnabled(false);
			btnCustomMachine.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnCustomMachine.setBounds(322, 460, 180, 36);
			pnlMachine.add(btnCustomMachine);
			btnCustomMachine.setToolTipText("Edit settings for a custom machine type");
			       		
			       		scrollPane = new JScrollPane();
			       		scrollPane.setBorder(null);
			       		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			       		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			       		scrollPane.setBounds(522, 44, 245, 405);
			       		pnlMachine.add(scrollPane);
			       		
			       		panel_6 = new JPanel();
			       		panel_6.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(192, 192, 192), null, null, null));
			       		panel_6.setBackground(Color.WHITE);
			       		panel_6.setToolTipText("Select a machine to edit options, re-order, or delete");
			       		scrollPane.setViewportView(panel_6);
			       		panel_6.setLayout(new BorderLayout(0, 0));
			       		
			       		       //Create the list and put it in a scroll pane.
			       		       listMachines = new JList(listModel);
			       		       panel_6.add(listMachines, BorderLayout.NORTH);
			       		       listMachines.addListSelectionListener(new MachineListSelectionListener());      
			       		       listMachines.setCellRenderer(new MachineListRenderer());
			       		       
			       		       		listMachines.setBorder(null);
			       		       		listMachines.setToolTipText("Select a machine to edit options, re-order, or delete");
			       		       		listMachines.setFont(new Font("Tahoma", Font.PLAIN, 13));
			       		       		listMachines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			       		       		
			       		       		btnOverrideDefaultAcceleration = new JButton("Override Default Acceleration");
			       		       		btnOverrideDefaultAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 11));
			       		       		btnOverrideDefaultAcceleration.setToolTipText("Set new values for the acceleration/deceleration of this machine");
			       		       		btnOverrideDefaultAcceleration.addActionListener(new ActionListener() {
			       		       			public void actionPerformed(ActionEvent e) {
			       		       				try{ // check list selected
			       		       					listMachines.getSelectedIndex();
			       		       				}catch(Exception e2){
			       		       					return;
			       		       				}
		       		       					Machine currMachine = (Machine) listMachines.getSelectedValue();
			       		       				AccelDecel dialog = new AccelDecel(currMachine);
			       		       				dialog.setLocationRelativeTo(frmTitanRoiCalculator);
			       		       				dialog.setVisible(true);
			       		       			}
			       		       		});
			       		       		btnOverrideDefaultAcceleration.setEnabled(false);
			       		       		btnOverrideDefaultAcceleration.setBounds(130, 460, 182, 36);
			       		       		pnlMachine.add(btnOverrideDefaultAcceleration);
		
		
		btnCustomMachine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetStatusLabel();
				//rdbtnOther.doClick();
				MachineBuilder newmach = new MachineBuilder(machine);
				newmach.setLocationRelativeTo(frmTitanRoiCalculator);
				newmach.setVisible(true);
			}
		});
		
		pnlJob = new JPanel();
		tabbedPane.addTab("Job Selection", null, pnlJob, "Add and configure machine jobs");
		tabbedPane.setEnabledAt(1, true);
		pnlJob.setLayout(null);
		
		JPanel pnlMaterials = new JPanel();
		pnlMaterials.setBounds(280, 72, 227, 124);
		pnlMaterials.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Material Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlJob.add(pnlMaterials);
		pnlMaterials.setLayout(null);
		
		Material[] materials = {new Material("Custom", 1.0, 20),
								new Material("Aluminium", 2.7, 40),
								new Material("Board", 1.3, 80),
								new Material("Cellophane", 1.5, 20),
								new Material("HDPE/BOPE", 0.94, 20),
								new Material("Laminate", 1.0, 20),
								new Material("LDPE/BOPE", 0.91, 20),
								new Material("LLDPE", 0.9, 20),
								new Material("MDPE", 0.925, 20),
								new Material("Nylon", 1.12, 20),
								new Material("Polypropylene", 0.91, 20),
								new Material("Polystyrene", 1.04, 20),
								new Material("Paper", 0.8, 100),
								new Material("PEEK", 1.3, 20),
								new Material("Polyester", 1.4, 20),
								new Material("PLA", 1.24, 20),
								new Material("Polyolefin", 0.92, 20),
								new Material("PSA Label", 1.1, 20),
								new Material("PVC", 1.36, 20),
								new Material("Shrink label", 0.91, 20),
								new Material("Tri. Lam.", 1.1, 20)
							};

		cmbMaterials = new JComboBox(materials);
		cmbMaterials.setMaximumRowCount(20);
		cmbMaterials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(jobFormReady){
					Material m = (Material) cmbMaterials.getSelectedItem();
					if(!(m.name.equals("Custom"))){
						jobFormReady = false;
						txtThickness.setText(Double.toString(roundTwoDecimals(m.getThickness())));
						if(lblGsm.getText().equals("(gsm)"))
							txtDensity.setText(Double.toString(roundTwoDecimals(m.getDensity())));
						else
							txtDensity.setText(Double.toString(roundTwoDecimals(m.getDensity() * m.getThickness())));
						if(!metric){
							txtThickness.setText(Double.toString(roundTwoDecimals(Core.MicroToMil(m.getThickness()))));
						}
						jobFormReady = true;
					}
					UpdateJob();
				}
			}
		});
		cmbMaterials.setEnabled(false);
		cmbMaterials.setSelectedIndex(0);
		cmbMaterials.setToolTipText("Choose a preset material");
		cmbMaterials.setBounds(81, 26, 117, 22);
		pnlMaterials.add(cmbMaterials);
		
		lblPresets = new JLabel("Presets:");
		lblPresets.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPresets.setToolTipText("Choose a preset material");
		lblPresets.setEnabled(false);
		lblPresets.setBounds(37, 29, 40, 15);
		pnlMaterials.add(lblPresets);
		
		lblThickness_1 = new JLabel("Thickness:");
		lblThickness_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblThickness_1.setToolTipText("Material thickness");
		lblThickness_1.setEnabled(false);
		lblThickness_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblThickness_1.setBounds(7, 61, 70, 14);
		pnlMaterials.add(lblThickness_1);
		
		lblDensity_1 = new JLabel("Density:");
		lblDensity_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDensity_1.setToolTipText("Material density (per volume, not area)");
		lblDensity_1.setEnabled(false);
		lblDensity_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDensity_1.setBounds(31, 86, 46, 14);
		pnlMaterials.add(lblDensity_1);
		
		txtThickness = new JTextField();
		txtThickness.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtThickness.selectAll();
			}
		});
		txtThickness.setEnabled(false);
		txtThickness.getDocument().addDocumentListener(new JobInputChangeListener());
		
		txtThickness.setToolTipText("Material thickness");
		txtThickness.setText("20");
		txtThickness.setBounds(81, 58, 86, 20);
		pnlMaterials.add(txtThickness);
		txtThickness.setColumns(10);
		
		txtDensity = new JTextField();
		txtDensity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtDensity.selectAll();
			}
		});
		txtDensity.setEnabled(false);
		txtDensity.getDocument().addDocumentListener(new JobInputChangeListener());
		
		txtDensity.setToolTipText("Material density (per volume, not area)");
		txtDensity.setText("0.92");
		txtDensity.setBounds(81, 83, 86, 20);
		pnlMaterials.add(txtDensity);
		txtDensity.setColumns(10);
		
		lblmicro0 = new JLabel("(\u00B5m)");
		lblmicro0.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmicro0.setEnabled(false);
		lblmicro0.setBounds(177, 61, 46, 14);
		pnlMaterials.add(lblmicro0);
		
		lblgm3 = new JLabel("(g/cm  )");
		lblgm3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblgm3.setEnabled(false);
		lblgm3.setBounds(177, 86, 46, 14);
		pnlMaterials.add(lblgm3);
		
		label_3 = new JLabel("3");
		label_3.setEnabled(false);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 8));
		label_3.setBounds(204, 83, 14, 14);
		pnlMaterials.add(label_3);
		
		JPanel pnlJobs = new JPanel();
		pnlJobs.setBounds(20, 168, 250, 283);
		pnlJobs.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Rewind Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlJob.add(pnlJobs);
		pnlJobs.setLayout(null);
		
		lblTargetRewindLength = new JLabel("Rewind Output:");
		lblTargetRewindLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTargetRewindLength.setToolTipText("Quantity of rewind output");
		lblTargetRewindLength.setEnabled(false);
		lblTargetRewindLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTargetRewindLength.setBounds(17, 197, 88, 14);
		pnlJobs.add(lblTargetRewindLength);
		
		lblSlitWidth = new JLabel("Slit Width:");
		lblSlitWidth.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSlitWidth.setToolTipText("Width of each output reel");
		lblSlitWidth.setEnabled(false);
		lblSlitWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSlitWidth.setBounds(46, 60, 59, 14);
		pnlJobs.add(lblSlitWidth);
		
		lblSlitCount = new JLabel("Reel Count:");
		lblSlitCount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSlitCount.setToolTipText("Number of reels per set at the output");
		lblSlitCount.setEnabled(false);
		lblSlitCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSlitCount.setBounds(46, 33, 59, 14);
		pnlJobs.add(lblSlitCount);
		
		lblTrimtotal = new JLabel("Trim (total):");
		lblTrimtotal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTrimtotal.setToolTipText("The trim resulting from the given slit widths");
		lblTrimtotal.setEnabled(false);
		lblTrimtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrimtotal.setBounds(20, 114, 85, 14);
		pnlJobs.add(lblTrimtotal);
		
		txtSlits = new JTextField();
		txtSlits.setToolTipText("Number of reels per set at the output");
		txtSlits.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSlits.selectAll();
			}
		});
		txtSlits.getDocument().addDocumentListener(new JobInputChangeListener());
		txtSlits.setEnabled(false);
		
		txtSlits.setText("1");
		txtSlits.setBounds(109, 30, 86, 20);
		pnlJobs.add(txtSlits);
		txtSlits.setColumns(10);
		
		txtSlitWidth = new JTextField();
		txtSlitWidth.setToolTipText("Width of each output reel");
		txtSlitWidth.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSlitWidth.selectAll();
			}
		});
		txtSlitWidth.getDocument().addDocumentListener(new JobInputChangeListener());
		txtSlitWidth.setEnabled(false);
		
		txtSlitWidth.setText("1350");
		txtSlitWidth.setBounds(109, 58, 86, 20);
		pnlJobs.add(txtSlitWidth);
		txtSlitWidth.setColumns(10);
		
		txtRewindAmount = new JTextField();
		txtRewindAmount.setToolTipText("Quantity of rewind output");
		txtRewindAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtRewindAmount.selectAll();
			}
		});
		txtRewindAmount.getDocument().addDocumentListener(new JobInputChangeListener());
		txtRewindAmount.setEnabled(false);
		txtRewindAmount.setName("RewindLength");
		
		txtRewindAmount.setText("1000");
		txtRewindAmount.setBounds(109, 194, 86, 20);
		pnlJobs.add(txtRewindAmount);
		txtRewindAmount.setColumns(10);
		
		lblTrim = new JLabel("0 mm");
		lblTrim.setToolTipText("The trim resulting from the given slit widths");
		lblTrim.setEnabled(false);
		lblTrim.setBounds(111, 114, 65, 14);
		pnlJobs.add(lblTrim);
		
		cmbRewindCore = new JComboBox();
		cmbRewindCore.setToolTipText("Core diameter of rewind reels");
		((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateJob();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateJob();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		/*cmbRewindCore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateJob(jobIndex);
			}
		});*/
		cmbRewindCore.setEnabled(false);

		cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_MM));
		cmbRewindCore.setSelectedIndex(1);
		cmbRewindCore.setBounds(109, 137, 87, 20);
		pnlJobs.add(cmbRewindCore);
		cmbRewindCore.setEditable(true);
		
		lblRewindCoremm = new JLabel("Rewind Core:");
		lblRewindCoremm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRewindCoremm.setToolTipText("Core diameter of rewind reels");
		lblRewindCoremm.setEnabled(false);
		lblRewindCoremm.setBounds(40, 140, 65, 14);
		pnlJobs.add(lblRewindCoremm);
		
		lblPer_1 = new JLabel("per:");
		lblPer_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPer_1.setToolTipText("Set whether rewind output is measured per reel or per set");
		lblPer_1.setEnabled(false);
		lblPer_1.setBounds(85, 248, 20, 14);
		pnlJobs.add(lblPer_1);
		
		cmbJobDomain = new JComboBox();
		cmbJobDomain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateSetReel();
				//UpdateJob();
			}
		});
		cmbJobDomain.setEnabled(false);
		cmbJobDomain.setToolTipText("Set whether rewind output is measured per reel or per set");
		cmbJobDomain.setModel(new DefaultComboBoxModel(new String[] {"Reel", "Set"}));
		cmbJobDomain.setBounds(109, 246, 95, 20);
		pnlJobs.add(cmbJobDomain);
		
		lblmm3 = new JLabel("(mm)");
		lblmm3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmm3.setEnabled(false);
		lblmm3.setBounds(205, 61, 27, 14);
		pnlJobs.add(lblmm3);
		
		lblmm2 = new JLabel("(mm)");
		lblmm2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmm2.setEnabled(false);
		lblmm2.setBounds(206, 140, 27, 14);
		pnlJobs.add(lblmm2);
		
		pnlUnwinds = new JPanel();
		pnlUnwinds.setLayout(null);
		pnlUnwinds.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Unwind Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlUnwinds.setBounds(280, 207, 227, 162);
		pnlJob.add(pnlUnwinds);
		
		lblUnwindLength = new JLabel("Unwind Size:");
		lblUnwindLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnwindLength.setToolTipText("Quantity of material per mother roll");
		lblUnwindLength.setEnabled(false);
		lblUnwindLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnwindLength.setBounds(21, 104, 66, 14);
		pnlUnwinds.add(lblUnwindLength);
		
		txtUnwindAmount = new JTextField();
		txtUnwindAmount.setToolTipText("Quantity of material per mother roll");
		txtUnwindAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtUnwindAmount.selectAll();
			}
		});
		txtUnwindAmount.getDocument().addDocumentListener(new JobInputChangeListener());
		txtUnwindAmount.setEnabled(false);
		txtUnwindAmount.setName("UnwindLength");
		txtUnwindAmount.setText("10000");
		txtUnwindAmount.setBounds(92, 100, 86, 20);
		pnlUnwinds.add(txtUnwindAmount);
		txtUnwindAmount.setColumns(10);
		
		lblWebWidthmm = new JLabel("Web Width:");
		lblWebWidthmm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWebWidthmm.setToolTipText("Width of mother rolls");
		lblWebWidthmm.setEnabled(false);
		lblWebWidthmm.setBounds(31, 29, 57, 14);
		pnlUnwinds.add(lblWebWidthmm);
		
		lblUnwindCoremm = new JLabel("Unwind Core:");
		lblUnwindCoremm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnwindCoremm.setToolTipText("Core diameter of mother rolls");
		lblUnwindCoremm.setEnabled(false);
		lblUnwindCoremm.setBounds(22, 54, 66, 14);
		pnlUnwinds.add(lblUnwindCoremm);
		
		cmbUnwindCore = new JComboBox();
		cmbUnwindCore.setToolTipText("Core diameter of mother rolls");
		((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateJob();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateJob();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		cmbUnwindCore.setEnabled(false);
		
		cmbUnwindCore.setModel(new DefaultComboBoxModel(new String[] {"76", "152", "254"}));
		cmbUnwindCore.setBounds(92, 51, 86, 20);
		pnlUnwinds.add(cmbUnwindCore);
		cmbUnwindCore.setEditable(true);
		
		lblmm0 = new JLabel("(mm)");
		lblmm0.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmm0.setEnabled(false);
		lblmm0.setBounds(189, 29, 32, 14);
		pnlUnwinds.add(lblmm0);
		
		lblmm1 = new JLabel("(mm)");
		lblmm1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmm1.setEnabled(false);
		lblmm1.setBounds(189, 53, 32, 14);
		pnlUnwinds.add(lblmm1);
		
		pnlEnviron = new JPanel();
		tabbedPane.addTab("Job Scheduling", null, pnlEnviron, "Edit the schedule of jobs to be analysed");
		tabbedPane.setEnabledAt(2, true);
		pnlEnviron.setLayout(null);
		
		JPanel pnlShifts = new JPanel();
		pnlShifts.setLayout(null);
		pnlShifts.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shift Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlShifts.setBounds(20, 72, 287, 162);
		pnlEnviron.add(pnlShifts);
		
		JLabel lblShiftLength = new JLabel("Shift Length:");
		lblShiftLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblShiftLength.setToolTipText("Hours per working shift");
		lblShiftLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShiftLength.setBounds(15, 28, 108, 14);
		pnlShifts.add(lblShiftLength);
		
		JLabel lblDayLength = new JLabel("Day Length:");
		lblDayLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDayLength.setToolTipText("Working hours per day");
		lblDayLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDayLength.setBounds(29, 78, 94, 14);
		pnlShifts.add(lblDayLength);
		
		JLabel lblShiftsDay = new JLabel("Shifts per Day:");
		lblShiftsDay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblShiftsDay.setToolTipText("Shifts per day");
		lblShiftsDay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShiftsDay.setBounds(15, 53, 108, 14);
		pnlShifts.add(lblShiftsDay);
		
		JLabel lblDays = new JLabel("Days per Year:");
		lblDays.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDays.setToolTipText("Days per year");
		lblDays.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDays.setBounds(0, 106, 123, 14);
		pnlShifts.add(lblDays);
		
		txtShiftLength = new JTextField();
		txtShiftLength.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtShiftLength.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtShiftLength.selectAll();
			}
		});
		txtShiftLength.setToolTipText("Hours per working shift");
		
		txtShiftLength.setText("8");
		txtShiftLength.setBounds(133, 25, 86, 20);
		pnlShifts.add(txtShiftLength);
		txtShiftLength.setColumns(10);
		
		txtShiftCount = new JTextField();
		txtShiftCount.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtShiftCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtShiftCount.selectAll();
			}
		});
		txtShiftCount.setToolTipText("Shifts per day");
		
		txtShiftCount.setText("3");
		txtShiftCount.setBounds(133, 50, 86, 20);
		pnlShifts.add(txtShiftCount);
		txtShiftCount.setColumns(10);
		
		txtDaysYear = new JTextField();
		txtDaysYear.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateShifts();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtDaysYear.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtDaysYear.selectAll();
			}
		});
		txtDaysYear.setToolTipText("Days per year");
		
		txtDaysYear.setText("250");
		txtDaysYear.setBounds(133, 103, 86, 20);
		pnlShifts.add(txtDaysYear);
		txtDaysYear.setColumns(10);
		
		lblDayLength2 = new JLabel("24 hours");
		lblDayLength2.setToolTipText("Working hours per day");
		lblDayLength2.setBounds(133, 78, 86, 14);
		pnlShifts.add(lblDayLength2);
		
		lblHoursYear = new JLabel("Hours per Year:");
		lblHoursYear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHoursYear.setToolTipText("Working hours per year");
		lblHoursYear.setBounds(47, 131, 76, 14);
		pnlShifts.add(lblHoursYear);
		
		lblHoursYear2 = new JLabel("6000 hours");
		lblHoursYear2.setToolTipText("Working hours per year");
		lblHoursYear2.setBounds(133, 131, 86, 14);
		pnlShifts.add(lblHoursYear2);
		
		lblHrs = new JLabel("hrs");
		lblHrs.setBounds(229, 28, 46, 14);
		pnlShifts.add(lblHrs);
		
		btnAddAll = new JButton("Add All");
		btnAddAll.setToolTipText("Add all jobs to the schedule in order");
		btnAddAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAddAll.setEnabled(false);
		try {
			btnAddAll.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/plus.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnAddAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i < jobModel.getSize(); ++i){
					listJobsAvail.setSelectedIndex(i);
					btnAddJob.doClick();
				}
			}
		});
		btnAddAll.setBounds(327, 460, 110, 36);
		pnlEnviron.add(btnAddAll);
		
		btnAddJob = new JButton("");
		btnAddJob.setToolTipText("Add this job to the schedule");
		btnAddJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AddToSchedule(listJobsAvail.getSelectedValue());
				
				ResetStatusLabel();
				
				int index = listSchedule.getSelectedIndex();
				//int source_index = listJobsAvail.getSelectedIndex();
				Job sel = (Job) listJobsAvail.getSelectedValue();
				JobItem j = environment.getSchedule().new JobItem(sel, 0, sel.getTotWeight(), sel.getTotLength());
			    int size = scheduleModel.getSize();
			    
			    // no limit now there are scroll bars
			     /* if(size >= Consts.JOB_LIST_LIMIT){    // Max list size
			    	ShowMessage("Maximum number of jobs scheduled. Please delete before attempting to add more.");
			    	return;
			    }*/
			    
			    //If no selection or if item in last position is selected,
			    //add the new one to end of list, and select new one.
			    if (index == -1 || (index+1 == size)) {
			    	
			    	scheduleModel.addElement(sel);
			    	listSchedule.setSelectedIndex(size);
			    	environment.getSchedule().addJob(j);
			
			    //Otherwise insert the new one after the current selection,
			    //and select new one.
			    } else {
			    	scheduleModel.insertElementAt(sel, index + 1);
			    	listSchedule.setSelectedIndex(index+1);
			    	environment.getSchedule().insertJob(j, index+1);
			    }
			    
				btnClearSchedule.setEnabled(true);
			}
		});
		btnAddJob.setEnabled(false);
		try {	
			btnAddJob.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/add_job.png"))));
			btnAddJob.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/add_job_dis.png"))));
			btnAddJob.setRolloverEnabled(true);
			btnAddJob.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/add_job_over.png"))));
		} catch (NullPointerException e111) {
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnAddJob.setBounds(521, 178, 54, 54);
		pnlEnviron.add(btnAddJob);
		
		btnRemoveJob = new JButton("");
		btnRemoveJob.setToolTipText("Remove job from schedule");
		btnRemoveJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetStatusLabel();
				
				// Uncomment this code to allow jobs to be saved back after removal from schedule
				/*if(listSchedule.getSelectedIndex() > -1){
					Job j = (Job) listSchedule.getSelectedValue();
					String name = j.getName().toLowerCase();
					if(!jobNames.contains(name) && !jobModel.contains(j)){
						if(jobModel.size() < Consts.JOB_LIST_LIMIT){
							jobNames.add(name);
							jobFormReady = true;
							int index = -1;
							if(listJobsAvail.getSelectedIndex() > -1)
								index = listJobsAvail.getSelectedIndex();
							if(index < 0){
								jobModel.addElement(j);
								listJobsAvail.setSelectedIndex(jobModel.size()-1);
								listJobs.setSelectedIndex(jobModel.size()-1);
							}else{
								jobModel.insertElementAt(j, index+1);
								listJobsAvail.setSelectedIndex(index+1);
								listJobs.setSelectedIndex(index+1);
							}
						}
					}
				}*/
				
	            ListSelectionModel lsm = listSchedule.getSelectionModel();
	            int firstSelected = lsm.getMinSelectionIndex();
	            int lastSelected = lsm.getMaxSelectionIndex();
	            scheduleModel.removeRange(firstSelected, lastSelected);
	            environment.getSchedule().remove(firstSelected);
	 
	            int size = scheduleModel.size();
	 
	            if (size == 0) {
	            //List is empty: disable delete, up, and down buttons.
	                btnClearSchedule.setEnabled(false);
	                btnUpSchedule.setEnabled(false);
	                btnDownSchedule.setEnabled(false);
	                listSchedule.clearSelection();
	                btnRemoveJob.setEnabled(false);
	                btnViewSchedule.setEnabled(false);
	            } else {
	            //Adjust the selection.
	                if (firstSelected == scheduleModel.getSize()) {
	                //Removed item in last position.
	                    firstSelected--;
	                }
	                listSchedule.setSelectedIndex(firstSelected);
	                
	                if(size == 21){  // No longer full list
	                	ResetStatusLabel();
	                }
	                
	            }
			}
		});
		btnRemoveJob.setEnabled(false);
		try {	
			btnRemoveJob.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/del_job.png"))));
			btnRemoveJob.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/del_job_dis.png"))));
			btnRemoveJob.setRolloverEnabled(true);
			btnRemoveJob.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/del_job_over.png"))));
		} catch (NullPointerException e111) {
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnRemoveJob.setBounds(520, 243, 54, 54);
		pnlEnviron.add(btnRemoveJob);
		
		lblJobSchedule_1 = new JLabel("Job Schedule");
		lblJobSchedule_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJobSchedule_1.setBounds(29, 18, 269, 22);
		pnlEnviron.add(lblJobSchedule_1);
		
		lblScheduleJobsTo = new JLabel("Schedule jobs to the right, then set shift options below");
		lblScheduleJobsTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblScheduleJobsTo.setBounds(29, 45, 279, 14);
		pnlEnviron.add(lblScheduleJobsTo);
		
		lblAvailableJobs_1 = new JLabel("Available Jobs");
		lblAvailableJobs_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAvailableJobs_1.setBounds(327, 19, 137, 14);
		pnlEnviron.add(lblAvailableJobs_1);
		
		lblJobSchedule_2 = new JLabel("Job Schedule");
		lblJobSchedule_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblJobSchedule_2.setBounds(577, 19, 110, 14);
		pnlEnviron.add(lblJobSchedule_2);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Schedule Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 245, 287, 104);
		pnlEnviron.add(panel);
		
		btnViewSchedule = new JButton("View Schedule Diagram");
		btnViewSchedule.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnViewSchedule.setToolTipText("View chart of schedule timings");
		btnViewSchedule.setEnabled(false);
		btnViewSchedule.setBounds(33, 27, 170, 29);
		panel.add(btnViewSchedule);
		
		chckbxSimulateScheduleStart = new JCheckBox("Ignore machine config & knife positioning times");
		chckbxSimulateScheduleStart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxSimulateScheduleStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!initialising)
					environment.StartStopTimes = !chckbxSimulateScheduleStart.isSelected();
			}
		});
		chckbxSimulateScheduleStart.setToolTipText("WARNING:  Use ONLY when you wish to model the output of a single job over a long time period \u2013 such that the initial set-up time has no material impact on the production volume");
		chckbxSimulateScheduleStart.setBounds(30, 62, 251, 18);
		panel.add(chckbxSimulateScheduleStart);
		
		lblhoverForInfo = new JLabel("(hover for info)");
		lblhoverForInfo.setToolTipText("WARNING:  Use ONLY when you wish to model the output of a single job over a long time period \u2013 such that the initial set-up time has no material impact on the production volume");
		lblhoverForInfo.setForeground(Color.GRAY);
		lblhoverForInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblhoverForInfo.setBounds(34, 80, 147, 14);
		panel.add(lblhoverForInfo);
		
		btnUpSchedule = new JButton("");
		btnUpSchedule.setToolTipText("Move job earlier in schedule");
		btnUpSchedule.addActionListener(new ScheduleUpListener());
		try{
			btnUpSchedule.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up.png"))));
			btnUpSchedule.setRolloverEnabled(true);
			btnUpSchedule.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_over.png"))));
			btnUpSchedule.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnUpSchedule.setEnabled(false);
		btnUpSchedule.setBounds(700, 463, 30, 30);
		pnlEnviron.add(btnUpSchedule);
		
		btnDownSchedule = new JButton("");
		btnDownSchedule.setToolTipText("Move job later in schedule");
		btnDownSchedule.addActionListener(new ScheduleDownListener());
		try{
			btnDownSchedule.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down.png"))));
			btnDownSchedule.setRolloverEnabled(true);
			btnDownSchedule.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_over.png"))));
			btnDownSchedule.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnDownSchedule.setEnabled(false);
		btnDownSchedule.setBounds(737, 463, 30, 30);
		pnlEnviron.add(btnDownSchedule);
		
		btnClearSchedule = new JButton("Clear");
		btnClearSchedule.setToolTipText("Clear all jobs from the schedule");
		btnClearSchedule.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnClearSchedule.setEnabled(false);
		try{
			btnClearSchedule.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete.png"))));
			btnClearSchedule.setRolloverEnabled(true);
			btnClearSchedule.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_over.png"))));
			btnClearSchedule.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnClearSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scheduleModel.removeAllElements();
				environment.getSchedule().empty();
			}
		});
		btnClearSchedule.setBounds(577, 460, 110, 36);
		pnlEnviron.add(btnClearSchedule);
		//listSchedule.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//listSchedule.setBounds(577, 44, 190, 405);
		//pnlEnviron.add(listSchedule);
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScheduleChart ch = new ScheduleChart(environment.getSchedule());
			    ch.pack();
			    try{
					ch.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
					}catch(NullPointerException e11){
						System.out.println("Image load error");
					} catch (IOException e) {
						e.printStackTrace();
					}
			    ch.setLocationRelativeTo(frmTitanRoiCalculator);
			    ch.setVisible(true);
			}
		});
		
		scrlSchedule = new JScrollPane();
		scrlSchedule.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlSchedule.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		scrlSchedule.setBounds(577, 44, 190, 405);
		scrlSchedule.getVerticalScrollBar().setUnitIncrement(16);
		pnlEnviron.add(scrlSchedule);
		
		panel_8 = new JPanel();
		panel_8.setToolTipText("Select job to re-order or remove from schedule");
		panel_8.setBackground(Color.WHITE);
		panel_8.setBorder(null);
		scrlSchedule.setViewportView(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		listSchedule = new JList(scheduleModel);
		panel_8.add(listSchedule, BorderLayout.NORTH);
		listSchedule.setToolTipText("Select job to re-order or remove from schedule");
		listSchedule.addListSelectionListener(new ScheduleSelectionListener());
		listSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSchedule.setCellRenderer(new JobListRenderer());
		
		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Notes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(20, 360, 287, 136);
		pnlEnviron.add(panel_5);
		
		lblToViewAnalysis = new JLabel("To view analysis for 1 job only, add just that job");
		lblToViewAnalysis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToViewAnalysis.setBounds(24, 24, 241, 14);
		panel_5.add(lblToViewAnalysis);
		
		lblToTheSchedule = new JLabel("to the schedule.");
		lblToTheSchedule.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToTheSchedule.setBounds(24, 38, 241, 14);
		panel_5.add(lblToTheSchedule);
		
		lblToModelMaintenance = new JLabel("To model maintenance or other downtime, edit the");
		lblToModelMaintenance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToModelMaintenance.setBounds(24, 59, 253, 14);
		panel_5.add(lblToModelMaintenance);
		
		lblShiftOptionsAbove = new JLabel("shift options above. This will affect annual output,");
		lblShiftOptionsAbove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblShiftOptionsAbove.setBounds(24, 73, 253, 14);
		panel_5.add(lblShiftOptionsAbove);
		
		lblButNotThe = new JLabel("but not the rates or efficiencies. For these, use the");
		lblButNotThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblButNotThe.setBounds(24, 87, 253, 14);
		panel_5.add(lblButNotThe);
		
		lbladvancedTabIn = new JLabel("'advanced' tab in the options menu box.");
		lbladvancedTabIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbladvancedTabIn.setBounds(24, 101, 253, 14);
		panel_5.add(lbladvancedTabIn);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(null);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(327, 44, 190, 405);
		pnlEnviron.add(scrollPane_2);
		
		panel_9 = new JPanel();
		panel_9.setToolTipText("Select job to be added");
		scrollPane_2.setViewportView(panel_9);
		panel_9.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		panel_9.setBackground(Color.WHITE);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		listJobsAvail = new JList(jobModel);
		panel_9.add(listJobsAvail, BorderLayout.NORTH);
		listJobsAvail.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, 0),
                "add");
		listJobsAvail.getActionMap().put("add", new AddScheduleBtn());
		listJobsAvail.setToolTipText("Select job to be added");
		listJobsAvail.addListSelectionListener(new JobAvailSelectionListener());
		listJobsAvail.setCellRenderer(new JobListRenderer());
		listJobsAvail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listJobsAvail.setBorder(null);
		
		pnlCompare = new JPanel();
		tabbedPane.addTab("Productivity Comparison", null, pnlCompare, "Productivity comparison data & graphs");
		tabbedPane.setEnabledAt(3, true);
		pnlCompare.setLayout(null);
		
		pnlResults = new JPanel();
		pnlResults.setBorder(new TitledBorder(null, "Numerical Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlResults.setBounds(20, 72, 479, 134);
		pnlCompare.add(pnlResults);
		pnlResults.setLayout(null);
		
		lblOutputLength = new JLabel("Output length over time period:");
		lblOutputLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOutputLength.setEnabled(false);
		lblOutputLength.setToolTipText("Quantity produced");
		lblOutputLength.setBounds(220, 54, 152, 14);
		pnlResults.add(lblOutputLength);
		
		lblOutputWeight = new JLabel("Output weight over time period:");
		lblOutputWeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOutputWeight.setEnabled(false);
		lblOutputWeight.setToolTipText("Quantity produced");
		lblOutputWeight.setBounds(220, 79, 162, 14);
		pnlResults.add(lblOutputWeight);
		
		cmbTimeRef = new JComboBox();
		cmbTimeRef.setEnabled(false);
		cmbTimeRef.setToolTipText("Select a time range to display results over");
		cmbTimeRef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Refresh analyses
				UpdateNumericalAnalysis();
			}
		});
		cmbTimeRef.setModel(new DefaultComboBoxModel(new String[] {"Schedule", "Year", "Hour", "Shift", "Day"/*, "Week"*/}));
		cmbTimeRef.setBounds(247, 98, 125, 24);
		pnlResults.add(cmbTimeRef);
		
		lblPer = new JLabel("Per:");
		lblPer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPer.setEnabled(false);
		lblPer.setBounds(220, 103, 20, 14);
		pnlResults.add(lblPer);
		
		lblAverageMmin = new JLabel("Average rate:");
		lblAverageMmin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAverageMmin.setEnabled(false);
		lblAverageMmin.setToolTipText("Average quantity processed");
		lblAverageMmin.setBounds(20, 54, 95, 14);
		pnlResults.add(lblAverageMmin);
		
		lblNumericsWeight = new JLabel("0.0 tons");
		lblNumericsWeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumericsWeight.setEnabled(false);
		lblNumericsWeight.setToolTipText("Quantity produced");
		lblNumericsWeight.setBounds(380, 79, 89, 14);
		pnlResults.add(lblNumericsWeight);
		
		lblNumericsLength = new JLabel("0.0 metres");
		lblNumericsLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumericsLength.setEnabled(false);
		lblNumericsLength.setToolTipText("Quantity produced");
		lblNumericsLength.setBounds(380, 54, 89, 14);
		pnlResults.add(lblNumericsLength);
		
		lblNumericsRate = new JLabel("0.0 m/hr");
		lblNumericsRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumericsRate.setEnabled(false);
		lblNumericsRate.setToolTipText("Average quantity processed");
		lblNumericsRate.setBounds(128, 54, 82, 14);
		pnlResults.add(lblNumericsRate);
		
		lblEfficiency = new JLabel("Machine running:");
		lblEfficiency.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEfficiency.setEnabled(false);
		lblEfficiency.setToolTipText("Proportion of total time for which the machine is running");
		lblEfficiency.setBounds(20, 104, 95, 14);
		pnlResults.add(lblEfficiency);
		
		cmbMachines = new JComboBox();
		cmbMachines.setMaximumRowCount(15);
		cmbMachines.setFont(new Font("Tahoma", Font.BOLD, 11));
		cmbMachines.setEnabled(false);
		cmbMachines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateNumericalAnalysis();
			}
		});
		cmbMachines.setToolTipText("Select machine");
		cmbMachines.setBounds(128, 20, 244, 24);
		pnlResults.add(cmbMachines);
		
		lblMachine = new JLabel("Select Machine:");
		lblMachine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMachine.setEnabled(false);
		lblMachine.setToolTipText("Select machine");
		lblMachine.setBounds(20, 25, 77, 14);
		pnlResults.add(lblMachine);
		
		lblNumericsEff = new JLabel("0.0 %");
		lblNumericsEff.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumericsEff.setEnabled(false);
		lblNumericsEff.setToolTipText("Machine efficiency");
		lblNumericsEff.setBounds(128, 104, 65, 14);
		pnlResults.add(lblNumericsEff);
		
		lblscheduletimelbl = new JLabel("Time to run schedule:");
		lblscheduletimelbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblscheduletimelbl.setToolTipText("Time to run schedule");
		lblscheduletimelbl.setEnabled(false);
		lblscheduletimelbl.setBounds(20, 79, 103, 14);
		pnlResults.add(lblscheduletimelbl);
		
		lblscheduletime = new JLabel("0.0 hr");
		lblscheduletime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblscheduletime.setToolTipText("Time to run schedule");
		lblscheduletime.setEnabled(false);
		lblscheduletime.setBounds(128, 79, 77, 14);
		pnlResults.add(lblscheduletime);
		
		pnlROI = new JPanel();
		tabbedPane.addTab("Return on Investment", (Icon) null, pnlROI, "Return on investment comparison and analysis");
		pnlROI.setLayout(null);
		tabbedPane.setEnabledAt(4, true);
		
		lblProductivityComparison = new JLabel("Productivity Comparison");
		lblProductivityComparison.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductivityComparison.setBounds(29, 18, 269, 22);
		pnlCompare.add(lblProductivityComparison);
		
		lblSelectMultipleMachines = new JLabel("Select multiple machines from the list on the right, then compare them below");
		lblSelectMultipleMachines.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectMultipleMachines.setBounds(29, 45, 433, 14);
		pnlCompare.add(lblSelectMultipleMachines);
		
		label_2 = new JLabel("Machines");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(522, 19, 85, 14);
		pnlCompare.add(label_2);
		
		pnlProdGraph = new JPanel();
		pnlProdGraph.setBounds(20, 222, 479, 274);
		pnlCompare.add(pnlProdGraph);
		pnlProdGraph.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Graphical Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlProdGraph.setLayout(null);
		
		btnShowGraph = new JButton("Open Graph");
		btnShowGraph.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnShowGraph.setToolTipText("Open graph in new window");
		btnShowGraph.setEnabled(false);
		btnShowGraph.setBounds(366, 51, 99, 39);
		pnlProdGraph.add(btnShowGraph);
		
		btnSaveToFile = new JButton("Save to File");
		btnSaveToFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSaveToFile.setToolTipText("Save image to disk");
		btnSaveToFile.setEnabled(false);
		btnSaveToFile.addActionListener(SaveActionListener);
		btnSaveToFile.setBounds(366, 97, 99, 24);
		pnlProdGraph.add(btnSaveToFile);
		
		pnlPreview = new JPanel();
		pnlPreview.setBackground(Color.WHITE);
		pnlPreview.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		pnlPreview.setBounds(21, 51, 335, 205);
		pnlProdGraph.add(pnlPreview);
		pnlPreview.setLayout(null);
		pnlGraph = new ChartPanel(chart);
		pnlGraph.setBounds(2, 2, 331, 201);
		//pnlGraph.setPreferredSize(new java.awt.Dimension(243, 171));
		pnlPreview.add(pnlGraph);
		try {
			picLabel = new JLabel(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/no_preview.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		picLabel.setBounds(2, 2, 331, 201);
		pnlPreview.add(picLabel);
		pnlGraph.setVisible(false);
		
		lblPreview = new JLabel("Graph Preview");
		lblPreview.setForeground(Color.DARK_GRAY);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPreview.setEnabled(false);
		lblPreview.setBounds(21, 24, 99, 16);
		pnlProdGraph.add(lblPreview);
		
		lblType = new JLabel("Graph Type:");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setEnabled(false);
		lblType.setToolTipText("Choose measurement type");
		lblType.setBounds(184, 26, 65, 14);
		pnlProdGraph.add(lblType);
		
		btnShowTimings = new JButton("Machine Runs");
		btnShowTimings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnShowTimings.setToolTipText("Show timing diagram for a single machine run");
		btnShowTimings.setEnabled(false);
		btnShowTimings.addActionListener(new BtnShowTimingsActionListener());
		btnShowTimings.setBounds(366, 201, 99, 24);
		pnlProdGraph.add(btnShowTimings);
		
		btnDowntime = new JButton("Productivity");
		btnDowntime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDowntime.setToolTipText("View productivity breakdown charts for the selected machines");
		btnDowntime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowTimingBreakdown();
			}
		});
		btnDowntime.setEnabled(false);
		btnDowntime.setBounds(366, 232, 99, 24);
		pnlProdGraph.add(btnDowntime);
		
		lblbreakdown1 = new JLabel("Show timing");
		lblbreakdown1.setEnabled(false);
		lblbreakdown1.setBounds(370, 166, 99, 14);
		pnlProdGraph.add(lblbreakdown1);
		
		lblbreakdown2 = new JLabel("breakdown for:");
		lblbreakdown2.setEnabled(false);
		lblbreakdown2.setBounds(370, 182, 99, 14);
		pnlProdGraph.add(lblbreakdown2);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(522, 459, 245, 37);
		pnlCompare.add(panel_3);
		panel_3.setLayout(null);
		
		btnNone = new JButton("None");
		btnNone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNone.setEnabled(false);
		btnNone.setBounds(172, 7, 57, 23);
		panel_3.add(btnNone);
		btnNone.setToolTipText("Clear machine selection");
		
		btnAll = new JButton("All");
		btnAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAll.setEnabled(false);
		btnAll.setBounds(105, 7, 57, 23);
		panel_3.add(btnAll);
		btnAll.setToolTipText("Select all machines");
		
		lblSelect = new JLabel("Select:");
		lblSelect.setEnabled(false);
		lblSelect.setBounds(37, 11, 46, 14);
		panel_3.add(lblSelect);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(null);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(522, 44, 245, 405);
		pnlCompare.add(scrollPane_3);
		
		panel_10 = new JPanel();
		panel_10.setToolTipText("Click a machine to select it. Select multiple machines to compare their performance under the chosen job schedule.");
		panel_10.setBackground(Color.WHITE);
		scrollPane_3.setViewportView(panel_10);
		panel_10.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		panel_10.setLayout(new BorderLayout(0, 0));
		
		listCompare = new JList(listModel);
		panel_10.add(listCompare, BorderLayout.NORTH);
		listCompare.setCellRenderer(new MachineListRenderer());
		listCompare.setToolTipText("Click a machine to select it. Select multiple machines to compare their performance under the chosen job schedule.");
		listCompare.setBorder(null);
		listCompare.addListSelectionListener(new MultiSelectionListener());
		
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] count = new int[listModel.size()];
				for(int i=0; i<listModel.size(); ++i)
					count[i] = i;
				listCompare.setSelectedIndices(count);
			}
		});
		btnNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listCompare.clearSelection();
			}
		});
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO base on cmbGraphType: or not if just using chart
				
				/*PieChart test = new PieChart("title","this is a pie chart");
				test.pack();
				test.setVisible(true);
				test.setLocationRelativeTo(null);*/
				JFrame popGraph = new JFrame();
				
				JFreeChart chartBig = null;
				try {
					chartBig = (JFreeChart) chart.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				//chartBig.setTitle("Productivity Comparison");
				
				ChartPanel cpanel = new ChartPanel(chartBig);
				cpanel.setPreferredSize(new java.awt.Dimension(500, 300));
				popGraph.setContentPane(cpanel);
				try{
					popGraph.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
				}catch(NullPointerException e11){
					System.out.println("Image load error");
				} catch (IOException e) {
					e.printStackTrace();
				}
				popGraph.setTitle("Productivity Graph");
				popGraph.setSize(450, 300);
				
				popGraph.pack();
				popGraph.setVisible(true);
				popGraph.setLocationRelativeTo(frmTitanRoiCalculator);
			}
		});
		
		cmbGraphType = new JComboBox();
		cmbGraphType.setMaximumRowCount(10);
		cmbGraphType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbGraphType.setEnabled(false);
		cmbGraphType.setToolTipText("Choose measurement type");
		cmbGraphType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Refresh analyses
				UpdateGraph();
			}
		});
		cmbGraphType.setModel(new DefaultComboBoxModel(new String[] {"Schedule Time", "Length per Hour", "Weight per Hour", "Length per Year", "Weight per Year", "Run Percentage"}));
		cmbGraphType.setSelectedIndex(0);
		cmbGraphType.setBounds(251, 21, 105, 24);
		pnlProdGraph.add(cmbGraphType);
		
        lblReturnOnInvestment = new JLabel("Return on Investment");
        lblReturnOnInvestment.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblReturnOnInvestment.setBounds(29, 18, 269, 22);
        pnlROI.add(lblReturnOnInvestment);
        
        lblSelectMultipleMachines_1 = new JLabel("Select multiple machines from the list on the right, then compare them below");
        lblSelectMultipleMachines_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSelectMultipleMachines_1.setBounds(29, 45, 433, 14);
        pnlROI.add(lblSelectMultipleMachines_1);
        
        lblCompareroiList = new JLabel("Machines");
        lblCompareroiList.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblCompareroiList.setBounds(522, 19, 85, 14);
        pnlROI.add(lblCompareroiList);
        
        panel_4 = new JPanel();
        panel_4.setLayout(null);
        panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setBounds(522, 459, 245, 37);
        pnlROI.add(panel_4);
        
        btnROIselectnone = new JButton("None");
        btnROIselectnone.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnROIselectnone.setEnabled(false);
        btnROIselectnone.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		listCompareRoi.clearSelection();
        	}
        });
        btnROIselectnone.setToolTipText("Clear machine selection");
        btnROIselectnone.setBounds(172, 7, 57, 23);
        panel_4.add(btnROIselectnone);
        
        btnROIselectall = new JButton("All");
        btnROIselectall.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnROIselectall.setEnabled(false);
        btnROIselectall.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int[] count = new int[listModel.size()];
				for(int i=0; i<listModel.size(); ++i)
					count[i] = i;
				listCompareRoi.setSelectedIndices(count);
        	}
        });
        btnROIselectall.setToolTipText("Select all machines");
        btnROIselectall.setBounds(105, 7, 57, 23);
        panel_4.add(btnROIselectall);
        
        lblROIselect = new JLabel("Select:");
        lblROIselect.setEnabled(false);
        lblROIselect.setBounds(37, 11, 46, 14);
        panel_4.add(lblROIselect);
        
        tabsROI = new JTabbedPane(JTabbedPane.TOP);
        tabsROI.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent arg0) {
        		try{
        			ResetStatusLabel();
        		}catch(NullPointerException e){
        			// Form is still initialising
        			return;
        		}
        		
        		int tab = tabsROI.getSelectedIndex();
        		switch(tab){
	        		case 0: UpdateROIProd(); break;
	        		case 1: UpdateROIEnergy(); break;
	        		case 2: UpdateROIMaint(); break;
	        		case 3: UpdateROIWaste(); break;
        		}
        	}
        });
        tabsROI.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tabsROI.setBounds(29, 72, 470, 424);
        pnlROI.add(tabsROI);
        
        pnlProdROI = new JPanel();
        pnlProdROI.setBackground(Color.WHITE);
        tabsROI.addTab("Productivity", null, pnlProdROI, "ROI based on productivity");
        pnlProdROI.setLayout(null);
        
        lblThisToolIlllustrates = new JLabel("This tool illlustrates the long-term financial benfits of particular machines and options in");
        lblThisToolIlllustrates.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblThisToolIlllustrates.setForeground(Color.GRAY);
        lblThisToolIlllustrates.setBounds(10, 11, 439, 14);
        pnlProdROI.add(lblThisToolIlllustrates);
        
        lblInTermsOf = new JLabel("terms of productivity gains.");
        lblInTermsOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblInTermsOf.setForeground(Color.GRAY);
        lblInTermsOf.setBounds(10, 27, 317, 14);
        pnlProdROI.add(lblInTermsOf);
        
        pnlGraphProd = new JPanel();
        pnlGraphProd.setBackground(Color.WHITE);
        pnlGraphProd.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
        pnlGraphProd.setBounds(10, 100, 439, 230);
        pnlProdROI.add(pnlGraphProd);
        pnlGraphProd.setLayout(null);
        
        pnlGraphProdInner = new ChartPanel(null);
        pnlGraphProdInner.setBounds(2, 2, 435, 226);
        //pnlGraphProd.add(pnlGraphProdInner);
        
        try {
        	lblNoGraph = new JLabel(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/no_preview.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        lblNoGraph.setBounds(2, 2, 435, 226);
		pnlGraphProd.add(lblNoGraph);
        
        lblSellingPrice = new JLabel("Selling price:");
        lblSellingPrice.setEnabled(false);
        lblSellingPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSellingPrice.setBounds(10, 52, 60, 14);
        pnlProdROI.add(lblSellingPrice);
        
        txtsellingprice = new JTextField();
        txtsellingprice.setToolTipText("Average selling price of the material per unit weight");
        txtsellingprice.setEnabled(false);
        txtsellingprice.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txtsellingprice.selectAll();
        	}
        });
        txtsellingprice.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateROIValue();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateROIValue();
			}
        	
        });
        txtsellingprice.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtsellingprice.setBounds(90, 49, 65, 20);
        pnlProdROI.add(txtsellingprice);
        txtsellingprice.setColumns(10);
        
        lblPerTonne = new JLabel("per tonne");
        lblPerTonne.setEnabled(false);
        lblPerTonne.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPerTonne.setBounds(160, 52, 70, 14);
        pnlProdROI.add(lblPerTonne);
        
        lblContribution = new JLabel("Contribution:");
        lblContribution.setEnabled(false);
        lblContribution.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblContribution.setBounds(10, 75, 70, 14);
        pnlProdROI.add(lblContribution);
        
        txtcontribution = new JTextField();
        txtcontribution.setToolTipText("Contribution percentage");
        txtcontribution.setEnabled(false);
        txtcontribution.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtcontribution.selectAll();
        	}
        });
        txtcontribution.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateROIValue();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateROIValue();
			}
		});
        txtcontribution.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtcontribution.setBounds(90, 73, 65, 20);
        pnlProdROI.add(txtcontribution);
        txtcontribution.setColumns(10);
        
        lblpercent = new JLabel("%");
        lblpercent.setEnabled(false);
        lblpercent.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblpercent.setBounds(160, 76, 11, 14);
        pnlProdROI.add(lblpercent);
        
        lblValueAddedlbl = new JLabel("Value added:");
        lblValueAddedlbl.setEnabled(false);
        lblValueAddedlbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblValueAddedlbl.setBounds(279, 52, 70, 14);
        pnlProdROI.add(lblValueAddedlbl);
        
        lblvalueadded = new JLabel("\u00A30.00 / tonne");
        lblvalueadded.setToolTipText("Value added per unit weight");
        lblvalueadded.setEnabled(false);
        lblvalueadded.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblvalueadded.setBounds(279, 71, 170, 20);
        pnlProdROI.add(lblvalueadded);
        
        cmbMarg1 = new JComboBox();
        cmbMarg1.setToolTipText("Old machine");
        cmbMarg1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		UpdateROIProd();
        	}
        });
        cmbMarg1.setEnabled(false);
        cmbMarg1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMarg1.setBounds(10, 359, 111, 20);
        pnlProdROI.add(cmbMarg1);
        
        cmbMarg2 = new JComboBox();
        cmbMarg2.setToolTipText("New machine");
        cmbMarg2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		UpdateROIProd();
        	}
        });
        cmbMarg2.setEnabled(false);
        cmbMarg2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMarg2.setBounds(141, 359, 111, 20);
        pnlProdROI.add(cmbMarg2);
        
        lblMarginalImprovement = new JLabel("\u00A30.00 per annum");
        lblMarginalImprovement.setToolTipText("Marginal improvement");
        lblMarginalImprovement.setEnabled(false);
        lblMarginalImprovement.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMarginalImprovement.setBounds(279, 358, 170, 20);
        pnlProdROI.add(lblMarginalImprovement);
        
        lblSelectMachines = new JLabel("Select 2 machines to view the marginal improvement between them:");
        lblSelectMachines.setForeground(Color.GRAY);
        lblSelectMachines.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblSelectMachines.setBounds(10, 338, 412, 14);
        pnlProdROI.add(lblSelectMachines);
        
        lblpound1 = new JLabel("\u00A3");
        lblpound1.setEnabled(false);
        lblpound1.setBounds(80, 52, 11, 14);
        pnlProdROI.add(lblpound1);
        
        lblTo = new JLabel("to");
        lblTo.setForeground(Color.GRAY);
        lblTo.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTo.setBounds(125, 362, 16, 14);
        pnlProdROI.add(lblTo);
        
        label = new JLabel("=");
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Tahoma", Font.BOLD, 13));
        label.setBounds(259, 362, 11, 14);
        pnlProdROI.add(label);
        
        pnlEnergy = new JPanel();
        pnlEnergy.setBackground(Color.WHITE);
        tabsROI.addTab("Energy Efficiency", null, pnlEnergy, "ROI based on machine power usage");
        pnlEnergy.setLayout(null);
        
        lblThisToolHighlights = new JLabel("This tool highlights power consumption differences between machines, and the resulting");
        lblThisToolHighlights.setForeground(Color.GRAY);
        lblThisToolHighlights.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblThisToolHighlights.setBounds(10, 11, 439, 14);
        pnlEnergy.add(lblThisToolHighlights);
        
        lblImpactOnFinancial = new JLabel("impact on financial returns.");
        lblImpactOnFinancial.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblImpactOnFinancial.setForeground(Color.GRAY);
        lblImpactOnFinancial.setBounds(10, 27, 439, 14);
        pnlEnergy.add(lblImpactOnFinancial);
        
        rdbtnAveragePower = new JRadioButton("Average power");
        rdbtnAveragePower.setToolTipText("Select \"average power\" input type");
        rdbtnAveragePower.setFont(new Font("Tahoma", Font.PLAIN, 11));
        rdbtnAveragePower.setEnabled(false);
        rdbtnAveragePower.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ClearPowerTxts();
        		txtaveragepower.setEnabled(true);
    			txthourlyusage.setEnabled(false);
    			txtannualusage.setEnabled(false);
    			lblKw.setEnabled(true);
    			lblKwh.setEnabled(false);
    			lblKwh_1.setEnabled(false);
    			txtaveragepower.requestFocusInWindow();
    			UpdateEnergyData();
        	}
        });
        rdbtnAveragePower.setSelected(true);
        rdbtnAveragePower.setBackground(Color.WHITE);
        rdbtnAveragePower.setBounds(349, 44, 109, 23);
        pnlEnergy.add(rdbtnAveragePower);
        
        rdbtnHourlyUsage = new JRadioButton("Hourly usage");
        rdbtnHourlyUsage.setToolTipText("Select \"hourly usage\" input type");
        rdbtnHourlyUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
        rdbtnHourlyUsage.setEnabled(false);
        rdbtnHourlyUsage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ClearPowerTxts();
        		txtaveragepower.setEnabled(false);
    			txthourlyusage.setEnabled(true);
    			txtannualusage.setEnabled(false);
    			lblKw.setEnabled(false);
    			lblKwh.setEnabled(true);
    			lblKwh_1.setEnabled(false);
    			txthourlyusage.requestFocusInWindow();
    			UpdateEnergyData();
        	}
        });
        rdbtnHourlyUsage.setBackground(Color.WHITE);
        rdbtnHourlyUsage.setBounds(349, 70, 109, 23);
        pnlEnergy.add(rdbtnHourlyUsage);
        
        rdbtnAnnualUsage = new JRadioButton("Annual usage");
        rdbtnAnnualUsage.setToolTipText("Select \"annual usage\" input type");
        rdbtnAnnualUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
        rdbtnAnnualUsage.setEnabled(false);
        rdbtnAnnualUsage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ClearPowerTxts();
        		txtaveragepower.setEnabled(false);
    			txthourlyusage.setEnabled(false);
    			txtannualusage.setEnabled(true);
    			lblKw.setEnabled(false);
    			lblKwh.setEnabled(false);
    			lblKwh_1.setEnabled(true);
    			txtannualusage.requestFocusInWindow();
    			UpdateEnergyData();
        	}
        });
        rdbtnAnnualUsage.setBackground(Color.WHITE);
        rdbtnAnnualUsage.setBounds(349, 97, 109, 23);
        pnlEnergy.add(rdbtnAnnualUsage);
        
        rdbtnsPower.add(rdbtnAveragePower);
        rdbtnsPower.add(rdbtnHourlyUsage);
        rdbtnsPower.add(rdbtnAnnualUsage);
        
        lblEnergyCostl = new JLabel("Energy cost:");
        lblEnergyCostl.setEnabled(false);
        lblEnergyCostl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEnergyCostl.setBounds(10, 101, 72, 14);
        pnlEnergy.add(lblEnergyCostl);
        
        lblpound2 = new JLabel("\u00A3");
        lblpound2.setEnabled(false);
        lblpound2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblpound2.setBounds(91, 100, 13, 14);
        pnlEnergy.add(lblpound2);
        
        txtenergycost = new JTextField();
        txtenergycost.setToolTipText("Raw energy cost, per kWh");
        txtenergycost.setEnabled(false);
        txtenergycost.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txtenergycost.selectAll();
        	}
        });
        txtenergycost.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateROIEnergy();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateROIEnergy();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
        txtenergycost.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtenergycost.setBounds(102, 98, 74, 20);
        pnlEnergy.add(txtenergycost);
        txtenergycost.setColumns(10);
        
        lblPerKwh = new JLabel("per kWh");
        lblPerKwh.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPerKwh.setEnabled(false);
        lblPerKwh.setBounds(179, 101, 46, 14);
        pnlEnergy.add(lblPerKwh);
        
        txtaveragepower = new JTextField();
        txtaveragepower.setToolTipText("Average power in kW");
        txtaveragepower.setEnabled(false);
        txtaveragepower.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateEnergyData();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateEnergyData();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
        txtaveragepower.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtaveragepower.selectAll();
        	}
        });
        txtaveragepower.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent arg0) {
				if(txtLimitRunSpeed.contains(arg0.getPoint())){
	        		if(!txtaveragepower.isEnabled()){
	        			rdbtnAveragePower.doClick();
	        		}
				}
        	}
        });
        txtaveragepower.setBounds(233, 47, 86, 20);
        pnlEnergy.add(txtaveragepower);
        txtaveragepower.setColumns(10);
        
        txthourlyusage = new JTextField();
        txthourlyusage.setToolTipText("Average hourly energy usage");
        txthourlyusage.setEnabled(false);
        txthourlyusage.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateEnergyData();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateEnergyData();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txthourlyusage.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txthourlyusage.selectAll();
        	}
        });
        txthourlyusage.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent arg0) {
				if(txtLimitRunSpeed.contains(arg0.getPoint())){
	        		if(!txthourlyusage.isEnabled())
	        			rdbtnHourlyUsage.doClick();
				}
        	}
        });
        txthourlyusage.setColumns(10);
        txthourlyusage.setBounds(233, 73, 86, 20);
        pnlEnergy.add(txthourlyusage);
        
        txtannualusage = new JTextField();
        txtannualusage.setToolTipText("Average annual energy usage");
        txtannualusage.setEnabled(false);
        txtannualusage.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateEnergyData();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateEnergyData();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtannualusage.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtannualusage.selectAll();
        	}
        });
        txtannualusage.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent arg0) {
				if(txtLimitRunSpeed.contains(arg0.getPoint())){
	        		if(!txtannualusage.isEnabled())
	        			rdbtnAnnualUsage.doClick();
				}
        	}
        });
        txtannualusage.setColumns(10);
        txtannualusage.setBounds(233, 99, 86, 20);
        pnlEnergy.add(txtannualusage);
        
        cmbMachineEnergy = new JComboBox();
        cmbMachineEnergy.setEnabled(false);
        cmbMachineEnergy.setToolTipText("Select machine to edit");
        cmbMachineEnergy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		UpdateEnergyView();
        	}
        });
        cmbMachineEnergy.setBounds(69, 46, 152, 23);
        pnlEnergy.add(cmbMachineEnergy);
        
        lblMachine_1 = new JLabel("Machine:");
        lblMachine_1.setEnabled(false);
        lblMachine_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblMachine_1.setBounds(10, 50, 59, 14);
        pnlEnergy.add(lblMachine_1);
        
        lblKw = new JLabel("kW");
        lblKw.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblKw.setEnabled(false);
        lblKw.setBounds(323, 50, 15, 14);
        pnlEnergy.add(lblKw);
        
        lblKwh = new JLabel("kWh");
        lblKwh.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblKwh.setEnabled(false);
        lblKwh.setBounds(323, 76, 26, 14);
        pnlEnergy.add(lblKwh);
        
        lblKwh_1 = new JLabel("MWh");
        lblKwh_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblKwh_1.setEnabled(false);
        lblKwh_1.setBounds(323, 102, 26, 14);
        pnlEnergy.add(lblKwh_1);
        
        pnlGraphEnergy = new JPanel();
        pnlGraphEnergy.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
        pnlGraphEnergy.setBackground(Color.WHITE);
        pnlGraphEnergy.setBounds(10, 126, 439, 226);
        pnlEnergy.add(pnlGraphEnergy);
        pnlGraphEnergy.setLayout(null);
        try {
        	lblNoGraph2 = new JLabel(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/no_preview.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        lblNoGraph2.setBounds(2, 2, 435, 222);
		pnlGraphEnergy.add(lblNoGraph2);
        
        lblPleaseSetPower = new JLabel("Please set power usage for each machine");
        lblPleaseSetPower.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPleaseSetPower.setEnabled(false);
        lblPleaseSetPower.setForeground(Color.GRAY);
        lblPleaseSetPower.setBounds(10, 74, 215, 14);
        pnlEnergy.add(lblPleaseSetPower);
        
        lblMarginalEnergy = new JLabel("\u00A30.00 per annum");
        lblMarginalEnergy.setToolTipText("Marginal improvement");
        lblMarginalEnergy.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMarginalEnergy.setEnabled(false);
        lblMarginalEnergy.setBounds(279, 358, 170, 20);
        pnlEnergy.add(lblMarginalEnergy);
        
        label_4 = new JLabel("=");
        label_4.setForeground(Color.GRAY);
        label_4.setFont(new Font("Tahoma", Font.BOLD, 13));
        label_4.setBounds(259, 362, 11, 14);
        pnlEnergy.add(label_4);
        
        cmbMargEnergy2 = new JComboBox();
        cmbMargEnergy2.setToolTipText("New machine");
        cmbMargEnergy2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		UpdateROIEnergy();
        	}
        });
        cmbMargEnergy2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMargEnergy2.setEnabled(false);
        cmbMargEnergy2.setBounds(141, 359, 111, 20);
        pnlEnergy.add(cmbMargEnergy2);
        
        label_5 = new JLabel("to");
        label_5.setForeground(Color.GRAY);
        label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_5.setBounds(125, 362, 16, 14);
        pnlEnergy.add(label_5);
        
        cmbMargEnergy1 = new JComboBox();
        cmbMargEnergy1.setToolTipText("Old machine");
        cmbMargEnergy1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		UpdateROIEnergy();
        	}
        });
        cmbMargEnergy1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMargEnergy1.setEnabled(false);
        cmbMargEnergy1.setBounds(10, 359, 111, 20);
        pnlEnergy.add(cmbMargEnergy1);
        
        pnlMaint = new JPanel();
        pnlMaint.setBackground(Color.WHITE);
        tabsROI.addTab("Maintenance", null, pnlMaint, "ROI based on maintenance costs");
        tabsROI.setMnemonicAt(2, 65);
        pnlMaint.setLayout(null);
        
        lblThisToolDemonstrates_1 = new JLabel("This tool demonstrates the maintenance reduction benefits of machines.");
        lblThisToolDemonstrates_1.setBounds(10, 11, 421, 14);
        lblThisToolDemonstrates_1.setForeground(Color.GRAY);
        lblThisToolDemonstrates_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        pnlMaint.add(lblThisToolDemonstrates_1);
        
        lblMaintenanceHoursPer = new JLabel("Annual downtime:");
        lblMaintenanceHoursPer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblMaintenanceHoursPer.setEnabled(false);
        lblMaintenanceHoursPer.setBounds(10, 64, 86, 14);
        pnlMaint.add(lblMaintenanceHoursPer);
        
        lblRepairCostsPer = new JLabel("Labour costs per hour:");
        lblRepairCostsPer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblRepairCostsPer.setEnabled(false);
        lblRepairCostsPer.setBounds(255, 36, 111, 14);
        pnlMaint.add(lblRepairCostsPer);
        
        lblPartsCostsPer = new JLabel("Parts costs per year:");
        lblPartsCostsPer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPartsCostsPer.setEnabled(false);
        lblPartsCostsPer.setBounds(255, 64, 101, 14);
        pnlMaint.add(lblPartsCostsPer);
        
        cmbMachinesmaintenance = new JComboBox();
        cmbMachinesmaintenance.setEnabled(false);
        cmbMachinesmaintenance.setToolTipText("Please set maintenance costs for each machine");
        cmbMachinesmaintenance.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ViewMaintCosts();
        	}
        });
        cmbMachinesmaintenance.setBounds(79, 30, 152, 23);
        pnlMaint.add(cmbMachinesmaintenance);
        
        txtmaintenancehours = new JTextField();
        txtmaintenancehours.setToolTipText("Hours per year for which machine is not running due to maintenance");
        txtmaintenancehours.setEnabled(false);
        txtmaintenancehours.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtmaintenancehours.selectAll();
        	}
        });
        txtmaintenancehours.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtmaintenancehours.setBounds(106, 61, 67, 20);
        pnlMaint.add(txtmaintenancehours);
        txtmaintenancehours.setColumns(10);
        
        txtmaintenanceperhour = new JTextField();
        txtmaintenanceperhour.setToolTipText("Hourly labour costs for maintenance");
        txtmaintenanceperhour.setEnabled(false);
        txtmaintenanceperhour.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtmaintenanceperhour.selectAll();
        	}
        });
        txtmaintenanceperhour.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtmaintenanceperhour.setColumns(10);
        txtmaintenanceperhour.setBounds(382, 33, 67, 20);
        pnlMaint.add(txtmaintenanceperhour);
        
        txtmaintenanceparts = new JTextField();
        txtmaintenanceparts.setToolTipText("Annual cost of maintenance parts for this machine");
        txtmaintenanceparts.setEnabled(false);
        txtmaintenanceparts.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtmaintenanceparts.selectAll();
        	}
        });
        txtmaintenanceparts.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateMaintCosts();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtmaintenanceparts.setColumns(10);
        txtmaintenanceparts.setBounds(382, 61, 67, 20);
        pnlMaint.add(txtmaintenanceparts);
        
        lbltotalmaintcost = new JLabel("\u00A30.00 / year");
        lbltotalmaintcost.setToolTipText("Total annual spend on maintenance for this machine (labour + parts)");
        lbltotalmaintcost.setForeground(Color.GRAY);
        lbltotalmaintcost.setEnabled(false);
        lbltotalmaintcost.setHorizontalAlignment(SwingConstants.LEFT);
        lbltotalmaintcost.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbltotalmaintcost.setBounds(344, 87, 105, 14);
        pnlMaint.add(lbltotalmaintcost);
        
        lblpound10 = new JLabel("\u00A3");
        lblpound10.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblpound10.setEnabled(false);
        lblpound10.setBounds(373, 36, 11, 14);
        pnlMaint.add(lblpound10);
        
        lblpound11 = new JLabel("\u00A3");
        lblpound11.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblpound11.setEnabled(false);
        lblpound11.setBounds(373, 64, 11, 14);
        pnlMaint.add(lblpound11);
        
        pnlGraphMaint = new JPanel();
        pnlGraphMaint.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
        pnlGraphMaint.setBackground(Color.WHITE);
        pnlGraphMaint.setBounds(10, 110, 439, 242);
        pnlMaint.add(pnlGraphMaint);
        pnlGraphMaint.setLayout(null);
        try {
        	lblNoGraph3 = new JLabel(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/no_preview.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        lblNoGraph3.setBounds(2, 2, 435, 238);
		pnlGraphMaint.add(lblNoGraph3);
        
        cmbMargMaint1 = new JComboBox();
        cmbMargMaint1.setToolTipText("Old machine");
        cmbMargMaint1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UpdateROIMaint();
        	}
        });
        cmbMargMaint1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMargMaint1.setEnabled(false);
        cmbMargMaint1.setBounds(10, 359, 111, 20);
        pnlMaint.add(cmbMargMaint1);
        
        label_8 = new JLabel("to");
        label_8.setForeground(Color.GRAY);
        label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_8.setBounds(125, 362, 16, 14);
        pnlMaint.add(label_8);
        
        cmbMargMaint2 = new JComboBox();
        cmbMargMaint2.setToolTipText("New machine");
        cmbMargMaint2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UpdateROIMaint();
        	}
        });
        cmbMargMaint2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbMargMaint2.setEnabled(false);
        cmbMargMaint2.setBounds(141, 359, 111, 20);
        pnlMaint.add(cmbMargMaint2);
        
        label_9 = new JLabel("=");
        label_9.setForeground(Color.GRAY);
        label_9.setFont(new Font("Tahoma", Font.BOLD, 13));
        label_9.setBounds(259, 362, 11, 14);
        pnlMaint.add(label_9);
        
        lblMarginalMaint = new JLabel("\u00A30.00 per annum");
        lblMarginalMaint.setToolTipText("Marginal improvement");
        lblMarginalMaint.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMarginalMaint.setEnabled(false);
        lblMarginalMaint.setBounds(279, 358, 170, 20);
        pnlMaint.add(lblMarginalMaint);
        
        lblProdLoss = new JLabel("\u00A30.00 / year");
        lblProdLoss.setToolTipText("Loss in production value due to downtime");
        lblProdLoss.setForeground(Color.GRAY);
        lblProdLoss.setEnabled(false);
        lblProdLoss.setHorizontalAlignment(SwingConstants.LEFT);
        lblProdLoss.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblProdLoss.setBounds(106, 87, 125, 14);
        pnlMaint.add(lblProdLoss);
        
        lblHrs_1 = new JLabel("hrs");
        lblHrs_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblHrs_1.setEnabled(false);
        lblHrs_1.setBounds(180, 64, 16, 14);
        pnlMaint.add(lblHrs_1);
        
        label_10 = new JLabel("Machine:");
        label_10.setEnabled(false);
        label_10.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_10.setBounds(10, 34, 59, 14);
        pnlMaint.add(label_10);
        
        label_11 = new JLabel("=");
        label_11.setEnabled(false);
        label_11.setForeground(Color.GRAY);
        label_11.setFont(new Font("Tahoma", Font.BOLD, 13));
        label_11.setBounds(88, 87, 11, 14);
        pnlMaint.add(label_11);
        
        label_12 = new JLabel("=");
        label_12.setEnabled(false);
        label_12.setForeground(Color.GRAY);
        label_12.setFont(new Font("Tahoma", Font.BOLD, 13));
        label_12.setBounds(323, 87, 11, 14);
        pnlMaint.add(label_12);
        
        lblAnnualTotal = new JLabel("Annual total");
        lblAnnualTotal.setEnabled(false);
        lblAnnualTotal.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblAnnualTotal.setBounds(255, 87, 67, 14);
        pnlMaint.add(lblAnnualTotal);
        
        lblProductionLoss = new JLabel("Production loss");
        lblProductionLoss.setEnabled(false);
        lblProductionLoss.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblProductionLoss.setBounds(10, 87, 77, 14);
        pnlMaint.add(lblProductionLoss);
        
        pnlWaste = new JPanel();
        pnlWaste.setBackground(Color.WHITE);
        tabsROI.addTab("Waste Reduction", null, pnlWaste, "ROI based on waste reduction");
        pnlWaste.setLayout(null);
        
        lblThisToolDemonstrates = new JLabel("This tool demonstrates the waste reduction capabilities of particular machines.");
        lblThisToolDemonstrates.setForeground(Color.GRAY);
        lblThisToolDemonstrates.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblThisToolDemonstrates.setBounds(10, 11, 439, 14);
        pnlWaste.add(lblThisToolDemonstrates);
        
        lblWasteSavedPer_1 = new JLabel("Waste saved per splice due to flag detection camera:");
        lblWasteSavedPer_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblWasteSavedPer_1.setEnabled(false);
        lblWasteSavedPer_1.setBounds(10, 39, 256, 14);
        pnlWaste.add(lblWasteSavedPer_1);
        
        txtwastesavedflags = new JTextField();
        txtwastesavedflags.setToolTipText("Saving per splice");
        txtwastesavedflags.setEnabled(false);
        txtwastesavedflags.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtwastesavedflags.selectAll();
        	}
        });
        txtwastesavedflags.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateROIWaste();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateROIWaste();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtwastesavedflags.setBounds(286, 36, 86, 20);
        pnlWaste.add(txtwastesavedflags);
        txtwastesavedflags.setColumns(10);
        
        lblM_1 = new JLabel("m");
        lblM_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblM_1.setEnabled(false);
        lblM_1.setBounds(378, 39, 46, 14);
        pnlWaste.add(lblM_1);
        
        lblWasteSavedPer_2 = new JLabel("Waste saved per mother roll due to alignment guide:");
        lblWasteSavedPer_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblWasteSavedPer_2.setEnabled(false);
        lblWasteSavedPer_2.setBounds(10, 64, 256, 14);
        pnlWaste.add(lblWasteSavedPer_2);
        
        txtwastesavedguide = new JTextField();
        txtwastesavedguide.setToolTipText("Saving per mother roll");
        txtwastesavedguide.setEnabled(false);
        txtwastesavedguide.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateROIWaste();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateROIWaste();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
        txtwastesavedguide.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		txtwastesavedguide.selectAll();
        	}
        });
        txtwastesavedguide.setBounds(286, 61, 86, 20);
        pnlWaste.add(txtwastesavedguide);
        txtwastesavedguide.setColumns(10);
        
        lblM_2 = new JLabel("m");
        lblM_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblM_2.setEnabled(false);
        lblM_2.setBounds(378, 64, 46, 14);
        pnlWaste.add(lblM_2);
        
        pnlGraphWaste = new JPanel();
        pnlGraphWaste.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
        pnlGraphWaste.setBackground(Color.WHITE);
        pnlGraphWaste.setBounds(10, 90, 439, 262);
        pnlWaste.add(pnlGraphWaste);
        pnlGraphWaste.setLayout(null);
        try {
        	lblNoGraph4 = new JLabel(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/no_preview.png"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        lblNoGraph4.setBounds(2, 2, 435, 258);
		pnlGraphWaste.add(lblNoGraph4);
		
		cmbMargWaste1 = new JComboBox();
		cmbMargWaste1.setToolTipText("Old machine");
		cmbMargWaste1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UpdateROIWaste();
        	}
        });
		cmbMargWaste1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbMargWaste1.setEnabled(false);
		cmbMargWaste1.setBounds(10, 359, 111, 20);
		pnlWaste.add(cmbMargWaste1);
		
		label_1 = new JLabel("to");
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(125, 362, 16, 14);
		pnlWaste.add(label_1);
		
		cmbMargWaste2 = new JComboBox();
		cmbMargWaste2.setToolTipText("New machine");
		cmbMargWaste2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UpdateROIWaste();
        	}
        });
		cmbMargWaste2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbMargWaste2.setEnabled(false);
		cmbMargWaste2.setBounds(141, 359, 111, 20);
		pnlWaste.add(cmbMargWaste2);
		
		label_6 = new JLabel("=");
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_6.setBounds(259, 362, 11, 14);
		pnlWaste.add(label_6);
		
		lblMarginalWaste = new JLabel("0.00m per annum");
		lblMarginalWaste.setToolTipText("Marginal improvement");
		lblMarginalWaste.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMarginalWaste.setEnabled(false);
		lblMarginalWaste.setBounds(279, 355, 170, 16);
		pnlWaste.add(lblMarginalWaste);
		
		lblMarginalWasteValue = new JLabel("(\u00A30.00 per annum)");
		lblMarginalWasteValue.setToolTipText("Marginal improvement value");
		lblMarginalWasteValue.setForeground(Color.GRAY);
		lblMarginalWasteValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMarginalWasteValue.setBounds(279, 370, 170, 16);
		pnlWaste.add(lblMarginalWasteValue);
        
        machNames = new HashSet<String>();
        jobNames = new HashSet<String>();
	    
		lblStatus = new JLabel(" Ready.");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmTitanRoiCalculator.getContentPane().add(lblStatus, BorderLayout.SOUTH);
		
		lblmmin = new JLabel("(m/min)");
		lblmmin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblmmin.setEnabled(false);
		lblmmin.setBounds(206, 168, 44, 14);
		pnlJobs.add(lblmmin);
		
		objfilter = new OBJfilter(1);
		
		// labels for unit conversion
		labs = new JLabel[7];
		labs[0] = lblmm0; labs[1] = lblmm1; labs[2] =  lblmm2; labs[3] = lblgm3; labs[4] = lblmm3; labs[5] = lblmicro0;
 
 lblGsm = new JLabel("(gsm)");
 lblGsm.setToolTipText("Switch the input type for density between gsm and g/cc");
 lblGsm.setFont(new Font("Tahoma", Font.PLAIN, 11));
 lblGsm.setEnabled(false);
 lblGsm.addMouseListener(new MouseAdapter() {
 	@Override
 	public void mouseReleased(MouseEvent arg0) {
 		if(lblGsm.isEnabled() && lblGsm.contains(arg0.getPoint())){
	 		jobFormReady = false;
	 		try{
		 		//double oldval = Double.parseDouble(txtDensity.getText());
		 		//double thickness = Double.parseDouble(txtThickness.getText());
		 		double oldval = ((Job)listJobs.getSelectedValue()).getDensity();
		 		double thickness = ((Job)listJobs.getSelectedValue()).getThickness();
		 		if(lblGsm.getText().equals("(gsm)")){
		 			lblGsm.setText("(g/cc)");
		 			label_3.setVisible(false);
		 			lblgm3.setText("(gsm)");
		 			txtDensity.setText(Double.toString(roundTwoDecimals(oldval * thickness)));
		 			((Job)listJobs.getSelectedValue()).gsm = true;
		 		}else{
		 			lblGsm.setText("(gsm)");
		 			label_3.setVisible(true);
		 			lblgm3.setText("(g/cm  )");
		 			txtDensity.setText(Double.toString(roundTwoDecimals(oldval)));
		 			((Job)listJobs.getSelectedValue()).gsm = false;
		 		}
	 		}catch(Exception e){
	 			lblGsm.setText("(gsm)");
	 			label_3.setVisible(true);
	 			lblgm3.setText("(g/cm  )");
	 			txtDensity.setText("0.92");
	 		}
	 		jobFormReady = true;
	 		
 		}
 	}
 	@Override
 	public void mouseEntered(MouseEvent arg0) {
 		lblGsm.setForeground(new Color(0,0,128));
 	}
 	@Override
 	public void mouseExited(MouseEvent e) {
 		lblGsm.setForeground(Color.black);
 	}
 });
 lblGsm.setForeground(new Color(0, 0, 0));
 lblGsm.setCursor(new Cursor(Cursor.HAND_CURSOR));
 lblGsm.setBounds(177, 102, 46, 14);
 pnlMaterials.add(lblGsm);
 
 lblOr = new JLabel("or:");
 lblOr.setFont(new Font("Tahoma", Font.PLAIN, 11));
 lblOr.setEnabled(false);
 lblOr.setBounds(155, 102, 14, 14);
 pnlMaterials.add(lblOr);labs[6] = lblmmin;

		 cmbUnwindType = new JComboBox();
		 cmbUnwindType.setToolTipText("Type of measure to use for the unwind quantity above");
		 cmbUnwindType.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		UpdateUnwindAmount();
		 	}
		 });
		 cmbUnwindType.setEnabled(false);
		 cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
		 cmbUnwindType.setBounds(92, 124, 95, 20);
		 pnlUnwinds.add(cmbUnwindType);
		 
		 txtWebWidth = new JTextField();
		 txtWebWidth.setToolTipText("Width of mother rolls");
		 txtWebWidth.setText("1350");
		 txtWebWidth.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtWebWidth.selectAll();
				}
			});
		 txtWebWidth.setEnabled(false);
		 txtWebWidth.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					UpdateJob();
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					UpdateJob();
				}
				@Override
				public void changedUpdate(DocumentEvent arg0) {
				}
			});
		 txtWebWidth.setBounds(92, 26, 86, 20);
		 pnlUnwinds.add(txtWebWidth);
		 txtWebWidth.setColumns(10);
		 
		 lblAverageFlags = new JLabel("Average Flags:");
		 lblAverageFlags.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblAverageFlags.setToolTipText("The average number of flags in each mother roll");
		 lblAverageFlags.setEnabled(false);
		 lblAverageFlags.setBounds(14, 79, 75, 14);
		 pnlUnwinds.add(lblAverageFlags);
		 
		 txtFlagCount = new JTextField();
		 txtFlagCount.setToolTipText("The average number of flags in each mother roll");
		 txtFlagCount.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtFlagCount.selectAll();
				}
			});
		 txtFlagCount.getDocument().addDocumentListener(new JobInputChangeListener());
		 txtFlagCount.setEnabled(false);
		 txtFlagCount.setText("1");
		 txtFlagCount.setBounds(92, 76, 43, 20);
		 pnlUnwinds.add(txtFlagCount);
		 txtFlagCount.setColumns(10);
		 
		 lblPerRoll = new JLabel("per roll");
		 lblPerRoll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblPerRoll.setEnabled(false);
		 lblPerRoll.setBounds(140, 79, 46, 14);
		 pnlUnwinds.add(lblPerRoll);
		 
		
		txtLimitRunSpeed = new JTextField();
		txtLimitRunSpeed.setToolTipText("Override for top machine speed (may be required for particular materials or environments)");
		txtLimitRunSpeed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(txtLimitRunSpeed.contains(arg0.getPoint())){
					txtLimitRunSpeed.setEnabled(true);
					chckbxLimitRunSpeed.setSelected(true);
					txtLimitRunSpeed.requestFocusInWindow();
					txtLimitRunSpeed.selectAll();
					UpdateJob();
				}
			}
		});
		txtLimitRunSpeed.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtLimitRunSpeed.isEnabled()){
					txtLimitRunSpeed.selectAll();
					chckbxLimitRunSpeed.setSelected(true);
				}
			}
		});
		txtLimitRunSpeed.getDocument().addDocumentListener(new JobInputChangeListener());
		txtLimitRunSpeed.setEnabled(false);
		
		txtLimitRunSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxLimitRunSpeed.setSelected(true);
			}
		});
		txtLimitRunSpeed.setText("800");
		txtLimitRunSpeed.setColumns(10);
		txtLimitRunSpeed.setBounds(130, 165, 65, 20);
		pnlJobs.add(txtLimitRunSpeed);
		
		cmbRewindType = new JComboBox();
		cmbRewindType.setToolTipText("Type of measure to use for rewind output above");
		cmbRewindType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRewindAmount();
				//UpdateJob();
			}
		});
		cmbRewindType.setEnabled(false);
		cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
		cmbRewindType.setBounds(109, 218, 95, 20);
		pnlJobs.add(cmbRewindType);
		
		chckbxLimitRunSpeed = new JCheckBox("");
		chckbxLimitRunSpeed.setToolTipText("Override for top machine speed (may be required for particular materials or environments)");
		chckbxLimitRunSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxLimitRunSpeed.isSelected()){	
					txtLimitRunSpeed.setEnabled(true);
					txtLimitRunSpeed.requestFocusInWindow();
					txtLimitRunSpeed.selectAll();
				}else{
					txtLimitRunSpeed.setEnabled(false);
				}
				
				UpdateJob();
			}
		});
		chckbxLimitRunSpeed.setEnabled(false);
		chckbxLimitRunSpeed.setBounds(105, 164, 20, 20);
		pnlJobs.add(chckbxLimitRunSpeed);
		
		lblLimitRunSpeed = new JLabel("Speed Limit:");
		lblLimitRunSpeed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLimitRunSpeed.setToolTipText("Override for top machine speed (may be required for particular materials or environments)");
		lblLimitRunSpeed.setEnabled(false);
		lblLimitRunSpeed.setBounds(47, 168, 59, 14);
		pnlJobs.add(lblLimitRunSpeed);
		
		lblKnifeType = new JLabel("Knife Type:");
		lblKnifeType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblKnifeType.setToolTipText("Select knife type for this job");
		lblKnifeType.setEnabled(false);
		lblKnifeType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKnifeType.setBounds(46, 89, 59, 14);
		pnlJobs.add(lblKnifeType);
		
		cmbKnifeType = new JComboBox();
		cmbKnifeType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateJob();
			}
		});
		cmbKnifeType.setModel(new DefaultComboBoxModel(new String[] {"Razor in Air", "Rotary Shear"}));
		cmbKnifeType.setToolTipText("Select knife type for this job");
		cmbKnifeType.setEnabled(false);
		cmbKnifeType.setBounds(109, 86, 95, 20);
		pnlJobs.add(cmbKnifeType);
		
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_M);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_J);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_S);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_P);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_R);
		
		tabsROI.setMnemonicAt(0, KeyEvent.VK_D);
		tabsROI.setMnemonicAt(1, KeyEvent.VK_N);
		tabsROI.setMnemonicAt(3, KeyEvent.VK_W);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_4.setBorder(null);
		scrollPane_4.setBounds(522, 44, 245, 405);
		pnlROI.add(scrollPane_4);
		
		panel_11 = new JPanel();
		panel_11.setToolTipText("Click a machine to select it. Select two or more machines to compare their ROI measures.");
		scrollPane_4.setViewportView(panel_11);
		panel_11.setBackground(Color.WHITE);
		panel_11.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		panel_11.setLayout(new BorderLayout(0, 0));
		
		listCompareRoi = new JList(listModel);
		panel_11.add(listCompareRoi, BorderLayout.NORTH);
		listCompareRoi.setToolTipText("Click a machine to select it. Select two or more machines to compare their ROI measures.");
		listCompareRoi.setBorder(null);
		listCompareRoi.setSelectionModel(new DefaultListSelectionModel() {
		    private static final long serialVersionUID = 1L;

		    boolean gestureStarted = false;

		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(!gestureStarted){
		            if (isSelectedIndex(index0)) {
		                super.removeSelectionInterval(index0, index1);
		            } else {
		                super.addSelectionInterval(index0, index1);
		            }
		        }
		        gestureStarted = true;
		    }

		    @Override
		    public void setValueIsAdjusting(boolean isAdjusting) {
		        if (isAdjusting == false) {
		            gestureStarted = false;
		        }
		    }

		});
		listCompareRoi.addListSelectionListener(new ROIListSelectionListener());
		listCompareRoi.setCellRenderer(new MachineListRenderer());
		listCompare.setSelectionModel(listCompareRoi.getSelectionModel());
		
		btnJobUp = new JButton("");
		btnJobUp.setToolTipText("Move job up");
		btnJobUp.addActionListener(new JobUpListener());
		try{
			btnJobUp.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up.png"))));
			btnJobUp.setRolloverEnabled(true);
			btnJobUp.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_over.png"))));
			btnJobUp.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/up_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnJobUp.setEnabled(false);
		btnJobUp.setBounds(700, 463, 30, 30);
		pnlJob.add(btnJobUp);
		
		btnJobDown = new JButton("");
		btnJobDown.setToolTipText("Move job down");
		btnJobDown.addActionListener(new JobDownListener());
		try{
			btnJobDown.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down.png"))));
			btnJobDown.setRolloverEnabled(true);
			btnJobDown.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_over.png"))));
			btnJobDown.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/down_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnJobDown.setEnabled(false);
		btnJobDown.setBounds(737, 463, 30, 30);
		pnlJob.add(btnJobDown);
		
		btnNewJob = new JButton("Add New");
		btnNewJob.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jobFormReady =  false;
				ResetStatusLabel();
				
				EnableJobForm();
				
				// new
				ResetJobForm();
				lblGsm.setText("(gsm)");
	 			label_3.setVisible(true);
	 			lblgm3.setText("(g/cm  )");
	 			txtDensity.setText("0.92");
				
				btnJobDelete.setEnabled(true);
            	
            	btnAddAll.setEnabled(true);
                
                /*if(listJobs.getSelectedIndex() == 0)
                	btnJobUp.setEnabled(false);
                else
                	btnJobUp.setEnabled(true);
                
                if(listJobs.getSelectedIndex() == jobModel.getSize()-1)
                	btnJobDown.setEnabled(false);
                else
                	btnJobDown.setEnabled(true);*/
                
				int index = listJobs.getSelectedIndex();
			    int size = jobModel.getSize();
			    
			    if(size >= Consts.JOB_LIST_LIMIT){    // Max list size
			    	ShowMessage("Maximum number of jobs allocated. Please delete before attempting to add more.");
			    	return;
			    }
			    
			    String newName = getUniqueJobName("Job");
			    txtJobName.setText(newName);
			    job = new Job(newName); 
			    jobNames.add(newName.toLowerCase());
			    
			    //If no selection or if item in last position is selected,
			    //add the new one to end of list, and select new one.
			    if (index == -1 || (index+1 == size)) {
			    	
			    	jobModel.addElement(job);
			    	listJobs.setSelectedIndex(size);
			    	listJobsAvail.setSelectedIndex(size);
			    	if(size > 0)
			    		btnJobUp.setEnabled(true);
			
			    //Otherwise insert the new one after the current selection,
			    //and select new one.
			    } else {
			    	jobModel.insertElementAt(job, index + 1);
			    	listJobs.setSelectedIndex(index+1);
			    	listJobsAvail.setSelectedIndex(index+1);
			    }
			    
			    // TODO don't reset form, or add copy button
			    
			    ResetStatusLabel();
			    jobFormReady = true;
			    
			    UpdateJob();
			    txtJobName.requestFocusInWindow();
			}
		});
		try {
			btnNewJob.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/plus.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnNewJob.setToolTipText("Add new job");
		btnNewJob.setBounds(522, 460, 110, 36);
		pnlJob.add(btnNewJob);
		
		lblJobConfiguration = new JLabel("Job Configuration");
		lblJobConfiguration.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJobConfiguration.setBounds(29, 18, 269, 22);
		pnlJob.add(lblJobConfiguration);
		
		btnJobDelete = new JButton("");
		btnJobDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetStatusLabel();
				 
		        Job selected = (Job) listJobs.getSelectedValue();
		        jobNames.remove(selected.getName().toLowerCase());
		        
	            ListSelectionModel lsm = listJobs.getSelectionModel();
	            int firstSelected = lsm.getMinSelectionIndex();
	            int lastSelected = lsm.getMaxSelectionIndex();
	            jobModel.removeRange(firstSelected, lastSelected);
	 
	            int size = jobModel.size();
	 
	            if (size == 0) {
	            //List is empty: disable delete, up, and down buttons.
	                /*btnJobDelete.setEnabled(false);
	                btnJobUp.setEnabled(false);
	                btnJobDown.setEnabled(false);*/
	                

	            } else {
	            //Adjust the selection.
	                if (firstSelected == jobModel.getSize()) {
	                //Removed item in last position.
	                    firstSelected--;
	                }
	                listJobs.setSelectedIndex(firstSelected);
	                
	                if(size == 21){  // No longer full list
	                	ResetStatusLabel();
	                }
	                
	                job = (Job) listJobs.getSelectedValue();
	            }
			}
		});
		
		try{
			btnJobDelete.setIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete.png"))));
			btnJobDelete.setRolloverEnabled(true);
			btnJobDelete.setRolloverIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_over.png"))));
			btnJobDelete.setDisabledIcon(new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/delete_dis.png"))));
			}catch(NullPointerException e11){
				System.out.println("Image load error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		btnJobDelete.setToolTipText("Delete job");
		btnJobDelete.setRolloverEnabled(true);
		btnJobDelete.setEnabled(false);
		btnJobDelete.setBounds(651, 460, 36, 36);
		pnlJob.add(btnJobDelete);
		
		lblAddNewJobs = new JLabel("Add new jobs to the list on the right, then configure unwind, rewind and material settings below");
		lblAddNewJobs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddNewJobs.setBounds(29, 45, 483, 14);
		pnlJob.add(lblAddNewJobs);
		
		lblJobs = new JLabel("Jobs");
		lblJobs.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblJobs.setBounds(522, 19, 85, 14);
		pnlJob.add(lblJobs);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Total Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(280, 380, 227, 116);
		pnlJob.add(panel_1);
		
		lblTargetOutputFor = new JLabel("Target output for job:");
		lblTargetOutputFor.setEnabled(false);
		lblTargetOutputFor.setBounds(30, 22, 129, 14);
		panel_1.add(lblTargetOutputFor);
		
		txtTargetTotal = new JTextField();
		txtTargetTotal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtTargetTotal.selectAll();
			}
		});
		txtTargetTotal.getDocument().addDocumentListener(new JobInputChangeListener());
		txtTargetTotal.setToolTipText("Total output quantity for this job");
		txtTargetTotal.setText("10000");
		txtTargetTotal.setEnabled(false);
		txtTargetTotal.setColumns(10);
		txtTargetTotal.setBounds(30, 43, 118, 20);
		
		panel_1.add(txtTargetTotal);
		
		cmbTargetTotal = new JComboBox();
		cmbTargetTotal.setToolTipText("Type of measure to use for output quantity above");
		cmbTargetTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTotalsAmount();
			}
		});
		cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Weight (tonnes)"}));
		cmbTargetTotal.setEnabled(false);
		cmbTargetTotal.setBounds(30, 67, 118, 20);
		panel_1.add(cmbTargetTotal);
		
		lblCounts = new JLabel("0 reel(s), 0 set(s), 0 mother(s)");
		lblCounts.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCounts.setForeground(Color.GRAY);
		lblCounts.setBounds(30, 92, 187, 14);
		panel_1.add(lblCounts);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(20, 72, 250, 83);
		pnlJob.add(panel_2);
		
		txtJobName = new JTextField();
		txtJobName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtJobName.selectAll();
			}
		});
		txtJobName.setBounds(90, 30, 145, 28);
		panel_2.add(txtJobName);
		txtJobName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateJobName();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateJobName();
			}
		});
		txtJobName.setToolTipText("Set the name of this job");
		txtJobName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtJobName.setEnabled(false);
		txtJobName.setColumns(10);
		
		lblJobName = new JLabel("Job name:");
		lblJobName.setToolTipText("Set the name of this job");
		lblJobName.setEnabled(false);
		lblJobName.setBounds(22, 31, 60, 24);
		panel_2.add(lblJobName);
		lblJobName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		btnResetJobs = new JButton("Reset");
		btnResetJobs.setBounds(20, 460, 100, 36);
		pnlJob.add(btnResetJobs);
		btnResetJobs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetJobForm();
				UpdateJob();
			}
		});
		btnResetJobs.setToolTipText("Reset the form");
		btnResetJobs.setEnabled(false);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(522, 44, 245, 405);
		pnlJob.add(scrollPane_1);
		
		panel_7 = new JPanel();
		panel_7.setToolTipText("Select a job to edit options, re-order, or delete");
		scrollPane_1.setViewportView(panel_7);
		panel_7.setBackground(Color.WHITE);
		panel_7.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		panel_7.setLayout(new BorderLayout(0, 0));
		
		listJobs = new JList(jobModel);
		listJobs.setSelectionModel(listJobsAvail.getSelectionModel());
		panel_7.add(listJobs, BorderLayout.NORTH);
		listJobs.setToolTipText("Select a job to edit options, re-order, or delete");
		listJobs.addListSelectionListener(new JobListSelectionListener());
		listJobs.setBorder(null);
		listJobs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listJobs.setCellRenderer(new JobListRenderer());
		pnlJob.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cmbMaterials, txtThickness, txtDensity, cmbUnwindCore, txtUnwindAmount, txtSlits, txtSlitWidth, cmbRewindCore, txtRewindAmount, cmbJobDomain, lblPresets, lblThickness_1, lblDensity_1, pnlMaterials, lblWebWidthmm, lblmm0, lblUnwindCoremm, lblmm1, lblUnwindLength, pnlUnwinds, lblmicro0, lblgm3, label_3, pnlJobs, lblTargetRewindLength, lblSlitWidth, lblSlitCount, lblTrimtotal, lblTrim, lblRewindCoremm, lblPer_1, lblmm3, lblmm2}));
		Image img = null;
		try {
			img = ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/refresh.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, 25, 25, null);
		
		LoadSettings();
		
		DoLicenceCheck();
		
		initialising = false;
		
	}
	
	private void LoadSettings(){
		try {
			String saveloc = "";
			if(((new File("config.ser")).exists()) && !(new File(Consts.SETTINGS_FILENAME)).exists())
				saveloc = ((new File("config.ser")).getAbsolutePath());
			else
				saveloc = Consts.SETTINGS_FILENAME;
         	FileInputStream fin = new FileInputStream(saveloc);
            ObjectInputStream ois = new ObjectInputStream(fin);
            environment = (Production) ois.readObject();
            ois.close();
            
            if(environment.SaveOnClose && !environment.SaveFileName.equals(saveloc)){
            	try{
	            	fin = new FileInputStream(environment.SaveFileName);
	                ois = new ObjectInputStream(fin);
	                environment = (Production) ois.readObject();
	                ois.close();
	                if(environment == null)
	                	throw new Exception();
            	}catch(Exception e){
            		// reload original
            		fin = new FileInputStream(saveloc);
                    ois = new ObjectInputStream(fin);
                    environment = (Production) ois.readObject();
                    ois.close();
                    environment.SaveFileName = saveloc;
            	}
            }
            
            environment.Unlocked = false;
            environment.StartStopTimes = true;
            environment.HrsPerDay = 24;
            environment.HrsPerShift = 8;
            environment.HrsPerYear = 6000;
            
            if(!environment.SaveOnClose){
            	environment = new Production();
            	environment.SaveOnClose = false;
            }else{
	            if(environment.getSchedule() == null)
	            	environment.setSchedule(new JobSchedule());
	            else{
	            	int size = environment.getSchedule().getSize();
	            	for(int i=0; i<size; ++i){
	            		scheduleModel.addElement(environment.getSchedule().getJob(i));
	            	}
	            	btnRemoveJob.setEnabled(true);
	            	btnUpSchedule.setEnabled(true);
	            	btnViewSchedule.setEnabled(true);
	            	btnClearSchedule.setEnabled(true);
			    	listSchedule.setSelectedIndex(size);
	            }
	            if(!environment.metric){
	            	ChangeUnits();
	            	rdbtnmntmImperial.setSelected(true);
	            	// manual job form change
    				cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_IN));
    				cmbUnwindCore.setModel(new DefaultComboBoxModel(Consts.UNWIND_CORE_IN));
    				cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)", "Diameter (in)"}));
    				cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (lbs)", "Diameter (in)"}));
    				cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)"}));
	    			cmbRewindCore.setSelectedIndex(1);
    				txtThickness.setText("0.79");
    				txtUnwindAmount.setText("32810");
    				txtRewindAmount.setText("3281.0");
    				txtLimitRunSpeed.setText("2624.8");
    				txtTargetTotal.setText("32810");
    				txtWebWidth.setText("53.15");
    				txtSlitWidth.setText("53.15");
    				lblTrim.setText("0 in");
					lblTrim.setText("0 in");
	            }
            }
		 }catch(Exception e){
			 environment = new Production();
		 }
	}
	
	double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	String formatDecimal(double d){
		boolean neg = false;
		if(d < 0)
			neg = true;
		double rounded = roundTwoDecimals(d);
		String res = Double.toString(rounded);
		if(neg)
			res = res.substring(1);
		String result = "";
		
		if(res.contains("E"))
			return res;
		
		// insert commas
		
		int unitcol = 0;
		if(res.charAt(res.length()-1) == '.'){
			unitcol = 1;
		}else if(res.charAt(res.length()-2) == '.'){
				unitcol = 2;
		}else if(res.charAt(res.length()-3) == '.'){
				unitcol = 3;
		}else{
			unitcol = 0;
		}
		int count = 0;
		for(int i = res.length() - 1; i > res.length() - 1 - unitcol; i--)
			result = result + res.charAt(i);
		for(int i = res.length() - 1 - unitcol; i>=0; i--){
			if(count != 0 && (count % 3 == 0))
				result = result + ",";
			result = result + res.charAt(i);
			count++;
		}
		
		StringBuffer buffer = new StringBuffer(result);  

		//Reverse the contents of the StringBuffer  
		buffer = buffer.reverse();  

		//Convert the StringBuffer back to a String  
		result = buffer.toString();  

		if(neg)
			result = "-" + result;
		
		return result;
	}
	
	double roundNDecimals(double d, int n) {
		DecimalFormat twoDForm;
		switch(n){
		case 1: twoDForm = new DecimalFormat("#.#"); break;
		case 2: twoDForm = new DecimalFormat("#.##"); break;
		case 3: twoDForm = new DecimalFormat("#.###"); break;
		case 4: twoDForm = new DecimalFormat("#.####"); break;
		case 5: twoDForm = new DecimalFormat("#.#####"); break;
		case 6: twoDForm = new DecimalFormat("#.######"); break;
		case 7: twoDForm = new DecimalFormat("#.#######"); break;
		case 8: twoDForm = new DecimalFormat("#.########"); break;
		case 9: twoDForm = new DecimalFormat("#.#########"); break;
		case 10: twoDForm = new DecimalFormat("#.##########"); break;
		default: twoDForm = new DecimalFormat("#.##"); break;
		}
		
		return Double.valueOf(twoDForm.format(d));
	}
	
	public static String getSignificant(double value, int sigFigs) {
        MathContext mc = new MathContext(sigFigs, RoundingMode.DOWN);
        BigDecimal bigDecimal = new BigDecimal(value, mc);
        return bigDecimal.toPlainString();
	}	

	
	class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /* 
             * This method can be called only if
             * there's a valid selection,
             * so go ahead and remove whatever's selected.
             */
        	
        	ResetStatusLabel();
        	
        	listCompare.removeSelectionInterval(listMachines.getSelectedIndex(), listMachines.getSelectedIndex());
        	int[] selection = listCompare.getSelectedIndices();
 
	        Machine selected = (Machine) listMachines.getSelectedValue();
	        machNames.remove(selected.name.toLowerCase());
	        
            ListSelectionModel lsm = listMachines.getSelectionModel();
            int firstSelected = lsm.getMinSelectionIndex();
            int lastSelected = lsm.getMaxSelectionIndex();
            listModel.removeRange(firstSelected, lastSelected);
            
	        for(int i=0; i<selection.length; i++){
	        	if(selection[i] >= firstSelected)
	        		selection[i] = selection[i] - 1;
	        }
	        listCompare.setSelectedIndices(selection);
	        listCompareRoi.setSelectedIndices(selection);
	        
            RoiData.energies.remove(firstSelected);
            RoiData.maintenance.remove(firstSelected);
 
            int size = listModel.size();
 
            if (size == 0) {
            //List is empty: disable delete, up, and down buttons.
                btnMachDelete.setEnabled(false);
                btnMachUp.setEnabled(false);
                btnMachDown.setEnabled(false);
                listMachines.clearSelection();
                formReady = false;
                machine = null;
                UpdateForm();
            } else {
            //Adjust the selection.
                if (firstSelected == listModel.getSize()) {
                //Removed item in last position.
                    firstSelected--;
                }
                listMachines.setSelectedIndex(firstSelected);
                
                if(size == 21){  // No longer full list
                	ResetStatusLabel();
                }
                
                machine = (Machine) listMachines.getSelectedValue();
            }
        }
    }

	
	@SuppressWarnings("unused")
	private void PopulateComboBox(JComboBox c, String[] elements){
		for(int i=0; i < elements.length; ++i){
			c.addItem(elements[i]);
		}
	}
	
	/*private void ConvertComboBox(JComboBox c, double multiplier){
		Double currentVal = Double.parseDouble(((JTextField) c.getEditor().getEditorComponent()).getText());
		currentVal *= multiplier;
		
		String[] m = new String[c.getModel().getSize()];
		for(int i=0; i < c.getModel().getSize(); ++i){
			double val = Double.parseDouble(c.getModel().getElementAt(i).toString());
			val *= multiplier;
			m[i] = Double.toString(roundTwoDecimals(val));
		}
		c.setModel(new DefaultComboBoxModel(m));
		
		((JTextField) c.getEditor().getEditorComponent()).setText(Double.toString(roundTwoDecimals(currentVal)));
		
	}*/
	
	private void ConvertTextBox(JTextField t, double multiplier){
		double val = Double.parseDouble(t.getText());
		val *= multiplier;
		t.setText(Double.toString(roundTwoDecimals(val)));
	}
	
	private void ConvertLabels(JLabel[] labs, boolean toImperial){
		if(toImperial){
			// Metric -> Imperial
			for(int i=0; i < labs.length; ++i){
				if(labs[i].getText().equals("(µm)"))
					labs[i].setText("(mil)");
				if(labs[i].getText().equals("(mm)"))
					labs[i].setText("(in)");
				if(labs[i].getText().equals("(m)"))
					labs[i].setText("(ft)");
				if(labs[i].getText().equals("(kg)"))
					labs[i].setText("(lb)");
				if(labs[i].getText().equals("(g)"))
					labs[i].setText("(oz)");
				if(labs[i].getText().equals("(tonne)"))
					labs[i].setText("(US ton)");
				if(labs[i].getText().equals("(m/min)"))
					labs[i].setText("(ft/min)");
			}
		}else{
			// Imperial -> Metric
			for(int i=0; i < labs.length; ++i){
				if(labs[i].getText().equals("(mil)"))
					labs[i].setText("(µm)");
				if(labs[i].getText().equals("(in)"))
					labs[i].setText("(mm)");
				if(labs[i].getText().equals("(ft)"))
					labs[i].setText("(m)");
				if(labs[i].getText().equals("(lb)"))
					labs[i].setText("(kg)");
				if(labs[i].getText().equals("(oz)"))
					labs[i].setText("(g)");
				if(labs[i].getText().equals("(US ton)"))
					labs[i].setText("(tonne)");
				if(labs[i].getText().equals("(ft/min)"))
					labs[i].setText("(m/min)");
			}
		}
	}
	
	private void ClearComponents(){
		//cmbRewindCore.removeAllItems();
	}
	
	private void SetComponents(){
		//PopulateComboBox(cmbRewindCore, Consts.REWIND_CORE_MM);
		//lblRewindCoremm.setText(Consts.LBL_REWIND_CORE_MM);
	}
	
	@SuppressWarnings("unused")
	private void ResetComponents(){
		ClearComponents();
		SetComponents();
	}
	
	private void ChangeUnits(){
		
		boolean backup = jobFormReady;
		jobFormReady = false;
		
		if(jobModel.getSize() > 0){
		
			//ClearComponents();
			if(metric){
				// Change metric -> imperial
				//PopulateComboBox(cmbRewindCore, Consts.REWIND_CORE_IN);
				//lblRewindCoremm.setText(Consts.LBL_REWIND_CORE_IN);
				ConvertTextBox(txtThickness, Core.MicroToMil(1));
				ConvertTextBox(txtSlitWidth, Core.MMToIn(1));
				ConvertTextBox(txtWebWidth, Core.MMToIn(1));
				ConvertTextBox(txtLimitRunSpeed, Core.MToFt(1));
				ConvertTextBox(txtTargetTotal, (cmbTargetTotal.getSelectedIndex()==0 ? Core.MToFt(1) : (cmbTargetTotal.getSelectedIndex()==1 ? Core.KgToTon(1) : Core.TonneToTon(1))));
				ConvertTextBox(txtRewindAmount, (cmbRewindType.getSelectedIndex()==0 ? Core.MToFt(1) : (cmbRewindType.getSelectedIndex()==1 ? Core.KgToLbs(1) : Core.MMToIn(1))));
				ConvertTextBox(txtUnwindAmount, (cmbUnwindType.getSelectedIndex()==0 ? Core.MToFt(1) : (cmbUnwindType.getSelectedIndex()==1 ? Core.KgToTon(1) : Core.MMToIn(1))));
				
				int[] indices = {cmbUnwindType.getSelectedIndex(), cmbRewindType.getSelectedIndex(), cmbTargetTotal.getSelectedIndex() };
				cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)", "Diameter (in)"}));
				cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (lbs)", "Diameter (in)"}));
				cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)"}));
				if(oldTotalsIndex==2) 
					oldTotalsIndex=1;
				cmbUnwindType.setSelectedIndex(indices[0]);
				cmbRewindType.setSelectedIndex(indices[1]);
				if(indices[2] > 1)
					indices[2] = 1;
				cmbTargetTotal.setSelectedIndex(indices[2]);
				
				//ConvertComboBox(cmbRewindCore, Core.MMToIn(1));
				//ConvertComboBox(cmbUnwindCore, Core.MMToIn(1));
				Double currVal = Double.parseDouble(((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getText());
				Double currVal2 = Double.parseDouble(((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getText());
				currVal *=  Core.MMToIn(1);
				currVal2 *=  Core.MMToIn(1);
				cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_IN));
				cmbUnwindCore.setModel(new DefaultComboBoxModel(Consts.UNWIND_CORE_IN));
				((JTextField) cmbRewindCore.getEditor().getEditorComponent()).setText(Double.toString(roundTwoDecimals(currVal)));
				((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).setText(Double.toString(roundTwoDecimals(currVal2)));
				
				lblTrim.setText("0 in");
			}else{
				// Change imperial -> metric
				//SetComponents();
				ConvertTextBox(txtThickness, Core.MilToMicro(1));
				ConvertTextBox(txtSlitWidth, Core.InToMM(1));
				ConvertTextBox(txtWebWidth, Core.InToMM(1));
				ConvertTextBox(txtLimitRunSpeed, Core.FtToM(1));
				ConvertTextBox(txtTargetTotal, (((Job)listJobs.getSelectedValue()).getTotalType()==0 ? Core.FtToM(1) : (((Job)listJobs.getSelectedValue()).getTotalType()==1 ? Core.TonToKg(1) : Core.TonToTonne(1))));
				ConvertTextBox(txtRewindAmount, (cmbRewindType.getSelectedIndex()==0 ? Core.FtToM(1) : (cmbRewindType.getSelectedIndex()==1 ? Core.LbsToKg(1) : Core.InToMM(1))));
				ConvertTextBox(txtUnwindAmount, (cmbUnwindType.getSelectedIndex()==0 ? Core.FtToM(1) : (cmbUnwindType.getSelectedIndex()==1 ? Core.TonToKg(1) : Core.InToMM(1))));
				
				int[] indices = {cmbUnwindType.getSelectedIndex(), cmbRewindType.getSelectedIndex(), cmbTargetTotal.getSelectedIndex() };
				cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
				cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
				cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Weight (tonnes)"}));
				cmbUnwindType.setSelectedIndex(indices[0]);
				cmbRewindType.setSelectedIndex(indices[1]);
				cmbTargetTotal.setSelectedIndex(((Job)listJobs.getSelectedValue()).getTotalType());//indices[2]);
				oldTotalsIndex = ((Job)listJobs.getSelectedValue()).getTotalType();
				
				//ConvertComboBox(cmbRewindCore, Core.InToMM(1));
				//ConvertComboBox(cmbUnwindCore, Core.InToMM(1));
				Double currVal = Double.parseDouble(((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getText());
				Double currVal2 = Double.parseDouble(((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getText());
				currVal *=  Core.InToMM(1);
				currVal2 *=  Core.InToMM(1);
				cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_MM));
				cmbUnwindCore.setModel(new DefaultComboBoxModel(Consts.UNWIND_CORE_MM));
				((JTextField) cmbRewindCore.getEditor().getEditorComponent()).setText(Double.toString(roundTwoDecimals(currVal)));
				((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).setText(Double.toString(roundTwoDecimals(currVal2)));
				
				lblTrim.setText("0 mm");

			}
		
		}
		
		jobFormReady = backup;
		
		ConvertLabels(labs, metric);
		
		metric = !metric;
		
		if(jobModel.getSize() < 1)
			ResetJobForm();
		
		environment.metric = metric;
		
		ChangeRoiUnits();
	
		UpdateJobForm();
	
		UpdateTrim();
		
		UpdateNumericalAnalysis();
		
		UpdateROI();
		
		if(!initialising){
			ShowMessageSuccess("Units changed to: "+(metric ? "metric" : "imperial"));
		}
	}
	
	private void ChangeRoiUnits(){
		double cost = 0., value = 0., wasteflag = 0., wasteguide = 0.;
		try{
			cost = Double.parseDouble(txtsellingprice.getText());
		}catch(Exception e){
			cost = 0;
		}
		try{
			//value = Double.parseDouble(lblvalueadded.getText());
		}catch(Exception e){
			value = 0;
		}
		try{
			wasteflag = Double.parseDouble(txtwastesavedflags.getText());
		}catch(Exception e){
			wasteflag = 0;
		}
		try{
			wasteguide = Double.parseDouble(txtwastesavedguide.getText());
		}catch(Exception e){
			wasteguide = 0;
		}
		
		if(metric){
			// from per ton to per tonne
			cost = Core.TonneToTon(cost);
			// from feet to metres
			wasteflag = Core.FtToM(wasteflag);
			wasteguide = Core.FtToM(wasteguide);
		}else{
			// from per tonne to per ton
			cost = Core.TonToTonne(cost);
			// from metres to feet
			wasteflag = Core.MToFt(wasteflag);
			wasteguide = Core.MToFt(wasteguide);
		}
		
		try{
			txtsellingprice.setText(Double.toString(roundTwoDecimals(cost)));
			txtwastesavedflags.setText(Double.toString(roundTwoDecimals(wasteflag)));
			txtwastesavedguide.setText(Double.toString(roundTwoDecimals(wasteguide)));
		}catch(Exception e){ }
		
		// Update value added label
		double contrib = 0.;
		try{
			contrib = Double.parseDouble(txtcontribution.getText());
			contrib = contrib / 100.;
			value = contrib * cost;
			lblvalueadded.setText("£"+formatDecimal(value) + (metric ? " / tonne" : " / ton"));
		}catch(Exception e){}
		
	}
	
	private void UpdateTrim(){
		// Update trim display on form
		Double trim = 0.;
		boolean valid = true;
		try{
			trim = Double.parseDouble(txtWebWidth.getText()) - Double.parseDouble(txtSlits.getText()) * Double.parseDouble(txtSlitWidth.getText());
			if(trim < 0 || trim > Double.parseDouble(txtWebWidth.getText()))
				valid = false;
			trim = Math.max(trim, 0.);
			if(metric)
				lblTrim.setText(Double.toString(roundTwoDecimals(trim)) + " mm");
			else
				lblTrim.setText(Double.toString(roundTwoDecimals(trim)) + " in");
		}catch(Exception e2)
		{
			valid = false;
			if(metric)
				lblTrim.setText("0 mm");
			else
				lblTrim.setText("0 in");
		}
		if(!valid){
			ShowMessage("Invalid configuration of slits - please check settings.");
		}
	}
	
	private void UpdateUnwindWarning(){
		double diam = 0.;
		try{
			diam = ((Job)listJobs.getSelectedValue()).getUnwindDiam();
			UpdateUnwindWarning(diam);
		}catch(Exception e){}
	}
	private void UpdateUnwindWarning(double diam){
		// this is a big approximation, and just a caution to the user
		if(diam > Consts.UNWIND_DIAM_DRIVE_LIMIT)
			ShowMessage("Caution: this size of unwind may require a double unwind drive on machine");
	}
	
	private void UpdateWebWidthWarning(double width){
		if(listMachines == null || listModel.getSize() < 1)
			return;
		int[] selection = listMachines.getSelectedIndices();
		for(int i=0; i<selection.length; ++i){
			double maxwidth = 0.;
			Machine m = (Machine)listModel.get(i);
			switch(m.model){
				case ER610: maxwidth = Consts.ER610_MAXWEB; break;
				case SR9DS: maxwidth = Consts.SR9DS_MAXWEB; break;
				case SR9DT: maxwidth = Consts.SR9DT_MAXWEB; break;
				case SR800: maxwidth = Consts.SR800_MAXWEB; break;
				default: maxwidth = Double.MAX_VALUE; break;
			}
		
			if(width > maxwidth)
				ShowMessage("Caution: not all configured machines support this web width");
		}
	}
	
	private double roundDown(double in){
		double up = Math.ceil(in);
		double down = Math.floor(in);
		if(in - up < Consts.DOUBLE_ERROR_MARGIN)
			return up;
		else
			return down;
	}
	
	private void UpdateReelCounts(){
		// Update trim display on form
		int reels = 0, sets = 0, mothers = 0;
		try{
			// the totLength is interpreted as linear length (ignoring slit#)
			Job j = (Job) listJobs.getSelectedValue();
			double rewLength = j.getRewindLengthSI();
			double unwLength = j.getUnwindLengthSI();
			double totLength = j.getTotLengthSI();
			double slits = (double) j.getSlits();
			reels = (int) (slits * roundDown(totLength / rewLength));
			if(rewLength == 0)
				reels = 0;
			sets = (int) roundDown(totLength / rewLength);
			if(slits == 0)
				sets = 0;
			mothers = (int) Math.ceil(totLength / unwLength);
			if(unwLength == 0)
				mothers = 0;
			reels = Math.max(0, reels);
			sets = Math.max(0, sets);
			mothers = Math.max(0, mothers);
			lblCounts.setText(reels+" reel(s), "+sets+" set(s), "+mothers+" mother(s)");
		}catch(Exception e2)
		{
			lblCounts.setText("0 reel(s), 0 set(s), 0 mother(s)");
		}
	}
	
	
	public void UpdateMachine(){

		if(formReady){
		
	    if (txtMachName.getText().equals("")) {
	    //User didn't type in a name...
	    	ShowMessage("Please enter a machine name.");
	    	return;
	    }
	    
	    Machine m = (Machine) listMachines.getSelectedValue();
	
	    //int index = listMachines.getSelectedIndex();
	    //int size = listModel.getSize();
	    
	    
	    if(machNames.contains(txtMachName.getText().toLowerCase()) && (!(m.name.equals(txtMachName.getText())))){
	    	ShowMessage("Duplicate machine names not allowed. Please choose a different name.");
	    	return;
	    }
	    machNames.remove(m.name.toLowerCase());
	    machNames.add(txtMachName.getText().toLowerCase());
		
	    // Option is only selected if it is applicable (ie. enabled) and selected (checked)
	    boolean[] opts = { chckbxSpliceTable.isSelected() && chckbxSpliceTable.isEnabled(), 
	    				   chckbxAlignmentGuide.isSelected() && chckbxAlignmentGuide.isEnabled(), 
	    				   chckbxRollConditioning.isSelected() && chckbxRollConditioning.isEnabled(), 
	    				   chckbxTurretSupport.isSelected() && chckbxTurretSupport.isEnabled(), 
	    				   chckbxAutostripping.isSelected() && chckbxAutostripping.isEnabled(), 
	    				   chckbxFlag.isSelected() && chckbxFlag.isEnabled(), 
	    				   chckbxAutoCutoff.isSelected() && chckbxAutoCutoff.isEnabled(), 
	    				   chckbxAutoTapeCore.isSelected() && chckbxAutoTapeCore.isEnabled(), 
	    				   chckbxAutoTapeTail.isSelected() && chckbxAutoTapeTail.isEnabled(), 
	    				   chckbxExtraRewind.isSelected() && chckbxExtraRewind.isEnabled() };
	    
	    //If no selection or if item in last position is selected,
	    //add the new one to end of list, and select new one.
	    m.update(txtMachName.getText(), 
	        		model, 
	        		/*cmbSpeed.isEnabled() ? */(String) cmbSpeed.getSelectedItem()/* : ""*/, 
    				/*cmbKnives.isEnabled() ? */(String) cmbKnives.getSelectedItem()/* : ""*/, 
					/*cmbCorepos.isEnabled() ? */(String) cmbCorepos.getSelectedItem()/* : ""*/, 
					/*cmbUnloader.isEnabled() ? */(String) cmbUnloader.getSelectedItem()/* : ""*/, 
					/*cmbUnwindDrive.isEnabled() ? */(String) cmbUnwindDrive.getSelectedItem()/* : ""*/, 
					/*cmbRewindCtrl.isEnabled() ? */(String) cmbRewindCtrl.getSelectedItem()/* : ""*/, 
    				opts);
	    
	    if(machine.isCustom()){
	    	m.setCustom(true);
	    	m.setCustomMachine(machine.getCustomMachine());
	    }
	    
	    ResetStatusLabel();
	    //btnMachReset.doClick();
	    
		}
	}
	
	class UpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ResetStatusLabel();
			int moveMe = listMachines.getSelectedIndex();
			 //UP ARROW BUTTON
            if (moveMe != 0) {     
            //not already at top
                swap(moveMe, moveMe-1);
                listMachines.setSelectedIndex(moveMe-1);
                listMachines.ensureIndexIsVisible(moveMe-1);
                machine = (Machine) listMachines.getSelectedValue();
                RoiDataOrderSwap(moveMe, moveMe-1);
            }
		}
	}
	
	//Listen for clicks on the up and down arrow buttons.
    class DownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only when
            //there's a valid selection,
            //so go ahead and move the list item.
        	ResetStatusLabel();
            int moveMe = listMachines.getSelectedIndex();
           
            //DOWN ARROW BUTTON
            if (moveMe != listModel.getSize()-1) {
            //not already at bottom
                swap(moveMe, moveMe+1);
                listMachines.setSelectedIndex(moveMe+1);
                listMachines.ensureIndexIsVisible(moveMe+1);
                machine = (Machine) listMachines.getSelectedValue();
                RoiDataOrderSwap(moveMe, moveMe+1);
            }
            
        }
    }
    
    class JobUpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ResetStatusLabel();
			int moveMe = listJobs.getSelectedIndex();
			 //UP ARROW BUTTON
            if (moveMe != 0) {     
            //not already at top
                swapJob(moveMe, moveMe-1);
                listJobs.setSelectedIndex(moveMe-1);
                listJobs.ensureIndexIsVisible(moveMe-1);
                job = (Job) listJobs.getSelectedValue();
            }
		}
	}
	
	//Listen for clicks on the up and down arrow buttons.
    class JobDownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only when
            //there's a valid selection,
            //so go ahead and move the list item.
        	ResetStatusLabel();
            int moveMe = listJobs.getSelectedIndex();
           
            //DOWN ARROW BUTTON
            if (moveMe != jobModel.getSize()-1) {
            //not already at bottom
                swapJob(moveMe, moveMe+1);
                listJobs.setSelectedIndex(moveMe+1);
                listJobs.ensureIndexIsVisible(moveMe+1);
                job = (Job) listJobs.getSelectedValue();
            }
            
        }
    }
    
    class ScheduleUpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ResetStatusLabel();
			int moveMe = listSchedule.getSelectedIndex();
			 //UP ARROW BUTTON
            if (moveMe != 0) {     
            //not already at top
                swapSchedule(moveMe, moveMe-1);
                listSchedule.setSelectedIndex(moveMe-1);
                listSchedule.ensureIndexIsVisible(moveMe-1);
                environment.getSchedule().swap(moveMe, moveMe-1);
            }
		}
	}
	
	//Listen for clicks on the up and down arrow buttons.
    class ScheduleDownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only when
            //there's a valid selection,
            //so go ahead and move the list item.
        	ResetStatusLabel();
            int moveMe = listSchedule.getSelectedIndex();
           
            //DOWN ARROW BUTTON
            if (moveMe != scheduleModel.getSize()-1) {
            //not already at bottom
                swapSchedule(moveMe, moveMe+1);
                listSchedule.setSelectedIndex(moveMe+1);
                listSchedule.ensureIndexIsVisible(moveMe+1);
                environment.getSchedule().swap(moveMe, moveMe+1);
            }
            
        }
    }
    
    private void UpdateShifts(){
    	if(!initialising){
	    	double result = 0.;
	    	try{ 
				result = Double.parseDouble(txtShiftLength.getText()) * Double.parseDouble(txtShiftCount.getText()); 
				lblDayLength2.setText(formatDecimal(result) + " hours");
			}
			catch(Exception e2)
			{
				lblDayLength2.setText("0 hours"); 
			}
			
			try{
				result *= Double.parseDouble(txtDaysYear.getText());
				lblHoursYear2.setText(formatDecimal(result) + " hours"); 
			}
			catch(Exception e2){
				lblHoursYear2.setText("0 hours");
			}
			environment.update(this);
    	}
    }
    
    private void UpdateUnwindAmount(){
	    if(jobFormReady && (oldUnwindIndex != cmbUnwindType.getSelectedIndex())){
	 		try{
	 			
	 			Double val = Double.parseDouble(txtUnwindAmount.getText());
	 			Double newval = val;
	 			Double thickness = 0.000001 * Double.parseDouble(txtThickness.getText());
	 			Double density = 0.;
	 			if(lblGsm.getText().equals("(gsm)"))
					density = 1000 * Double.parseDouble(txtDensity.getText()); // approx: kg m^-3
				else
					density = Double.parseDouble(txtDensity.getText()) / (thickness * 1000000);
				
			
				Double width = 0.001 * Double.parseDouble(txtWebWidth.getText());
				Double core = 0.001 * (Consts.UNWIND_CORE_THICKNESS + Double.parseDouble(((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getText()));
				
				if(! metric){
					//density = density;
					thickness = thickness * 25.4;
					width = width * 25.4;
					core = core * 25.4;
				}
	 			
	 			if(cmbUnwindType.getSelectedIndex() == 0){
	 				// length
	 				if(oldUnwindIndex==1)
	 					newval = Core.WeightToLength((metric ? val : Core.TonToKg(val)), thickness, width, density);
	 				if(oldUnwindIndex==2)
	 					newval = Core.DiamToLength(0.001 * (metric ? val : Core.InToMM(val)), thickness, core);
	 				oldUnwindIndex = 0;
	 				double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getUnwindLengthSI();
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
	 				if(!metric)
	 					newval = Core.MToFt(newval);
	 			}else if(cmbUnwindType.getSelectedIndex() == 1){
	 				// weight
	 				if(oldUnwindIndex==0)
	 					newval = Core.LengthToWeight((metric ? val : Core.FtToM(val)), thickness, width, density);
		 			if(oldUnwindIndex==2)
		 				newval = Core.LengthToWeight(Core.DiamToLength(0.001 * (metric ? val : Core.InToMM(val)), thickness, core), thickness, width, density);
		 			oldUnwindIndex = 1;
		 			double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getUnwindWeightSI();
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
		 			if(!metric)
	 					newval = Core.KgToTon(newval);
	 			}else if(cmbUnwindType.getSelectedIndex() == 2){
	 				// diam
	 				if(oldUnwindIndex==0)
	 					newval = 1000 * Core.LengthToDiam((metric ? val : Core.FtToM(val)), thickness, core);
		 			if(oldUnwindIndex==1)
		 				newval = 1000 * Core.LengthToDiam(Core.WeightToLength((metric ? val : Core.TonToKg(val)), thickness, width, density), thickness, core);
		 			oldUnwindIndex = 2;
		 			double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getUnwindDiamSI() * 1000;
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
		 			if(!metric)
	 					newval = Core.MMToIn(newval);
	 			}
	 			jobFormReady = false;
	 			txtUnwindAmount.setText(Double.toString(roundTwoDecimals(newval)));
	 			jobFormReady = true;
	 			
	 			((Job) listJobs.getSelectedValue()).updateQuantityTypes(
	 					cmbUnwindType.getSelectedIndex(),
	 					cmbRewindType.getSelectedIndex(),
	 					cmbTargetTotal.getSelectedIndex());
	 			
	 		}catch(NumberFormatException e2){
	 			txtUnwindAmount.setText(("0"));
	 		}
		}
    }
 
    private void UpdateRewindAmount(){
	    if(jobFormReady && (oldRewindIndex != cmbRewindType.getSelectedIndex()) && !(cmbJobDomain.isEnabled() && cmbJobDomain.getSelectedIndex() == 1)){
	 		try{
	 			
	 			int slits = Integer.parseInt(txtSlits.getText());
	 			
	 			Double val = Double.parseDouble(txtRewindAmount.getText());
	 			Double newval = val;
	 			Double density = 0.;
	 			Double thickness = 0.000001 * Double.parseDouble(txtThickness.getText());
	 			if(lblGsm.getText().equals("(gsm)"))
					density = 1000 * Double.parseDouble(txtDensity.getText()); // approx: kg m^-3
				else
					density = Double.parseDouble(txtDensity.getText()) / (thickness * 1000000);
			
				Double width = 0.001 * Double.parseDouble(txtWebWidth.getText());
				Double core = 0.001 * (Consts.REWIND_CORE_THICKNESS + Double.parseDouble(((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getText()));
				
				if(! metric){
					//density = density;
					thickness = thickness * 25.4;
					width = width * 25.4;
					core = core * 25.4;
				}
	 			
	 			if(cmbRewindType.getSelectedIndex() == 0){
	 				// length
	 				if(oldRewindIndex==1)
	 					newval = Core.WeightToLength((metric ? val : Core.LbsToKg(val)), thickness, width, density);
	 				if(oldRewindIndex==2)
	 					newval = Core.DiamToLength(0.001 * (metric ? val : Core.InToMM(val)), thickness, core);
	 				// easier to just read val back from job:
	 				double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getRewindLengthSI();
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
	 				oldRewindIndex = 0;
	 				cmbJobDomain.setEnabled(false);
	 				if(!metric)
	 					newval = Core.MToFt(newval);
	 			}else if(cmbRewindType.getSelectedIndex() == 1){
	 				// weight
	 				if(oldRewindIndex==0)
	 					newval = Core.LengthToWeight((metric ? val : Core.FtToM(val)), thickness, width, density) / slits;
		 			if(oldRewindIndex==2)
		 				newval = Core.LengthToWeight(Core.DiamToLength(0.001 * (metric ? val : Core.InToMM(val)), thickness, core), thickness, width, density) / slits;
		 			double weight; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					weight = job.getRewindWeightSI();
	 					newval = weight;
	 					if(!(cmbJobDomain.isEnabled() && cmbJobDomain.getSelectedIndex() == 1))
	 						newval = weight / slits;
	 				}catch(Exception e){ newval = 0.; }
	 				oldRewindIndex = 1;
		 			cmbJobDomain.setEnabled(true);
		 			if(!metric)
	 					newval = Core.KgToLbs(newval);
	 			}else if(cmbRewindType.getSelectedIndex() == 2){
	 				// diam
	 				if(oldRewindIndex==0)
	 					newval = 1000 * Core.LengthToDiam((metric ? val : Core.FtToM(val)), thickness, core);
		 			if(oldRewindIndex==1)
		 				newval = 1000 * Core.LengthToDiam(Core.WeightToLength((metric ? val : Core.LbsToKg(val)), thickness, width, density), thickness, core);
		 			double diam; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					diam = job.getRewindDiam();
	 					newval = diam;
	 				}catch(Exception e){ newval = 0.; }
	 				oldRewindIndex = 2;
		 			cmbJobDomain.setEnabled(false);
		 			if(!metric)
	 					newval = Core.MMToIn(newval);
	 			}
	 			jobFormReady = false;
	 			txtRewindAmount.setText(Double.toString(roundTwoDecimals(newval)));
	 			jobFormReady = true;
	 			
	 			((Job) listJobs.getSelectedValue()).updateQuantityTypes(
	 					cmbUnwindType.getSelectedIndex(),
	 					cmbRewindType.getSelectedIndex(),
	 					cmbTargetTotal.getSelectedIndex());
	 			
	 		}catch(NumberFormatException e2){
	 			txtRewindAmount.setText(("0"));
	 		}
		}
    }
    
    private void UpdateTotalsAmount(){
	    if(jobFormReady && (oldTotalsIndex != cmbTargetTotal.getSelectedIndex())){
	 		try{
	 			
	 			Double val = Double.parseDouble(txtTargetTotal.getText());
	 			Double newval = val;
	 			Double density = 0.;
	 			Double thickness = 0.000001 * Double.parseDouble(txtThickness.getText());
	 			if(lblGsm.getText().equals("(gsm)"))
					density = 1000 * Double.parseDouble(txtDensity.getText()); // approx: kg m^-3
				else
					density = Double.parseDouble(txtDensity.getText()) / (thickness * 1000000);
				
				Double width = 0.001 * Double.parseDouble(txtWebWidth.getText());
				
				if(! metric){
					//density = density;
					thickness = thickness * 25.4;
					width = width * 25.4;
				}
				
	 			if(cmbTargetTotal.getSelectedIndex() == 0){
	 				// length
	 				if(oldTotalsIndex==1)
	 					newval = Core.WeightToLength((metric ? val : Core.TonToKg(val)), thickness, width, density);
	 				if(oldTotalsIndex==2)
	 					newval = Core.WeightToLength(1000 * (metric ? val : Core.TonToKg(val)), thickness, width, density);
	 				oldTotalsIndex = 0;
	 				double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getTotLengthSI();
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
	 				if(!metric)
	 					newval = Core.MToFt(newval);
	 			}else if(cmbTargetTotal.getSelectedIndex() == 1){
	 				// weight
	 				if(oldTotalsIndex==0)
	 					newval = Core.LengthToWeight((metric ? val : Core.FtToM(val)), thickness, width, density);
		 			if(oldTotalsIndex==2)
		 				newval = (metric ? val : Core.TonToKg(val)) * 1000;
		 			oldTotalsIndex = 1;
		 			double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getTotWeightSI();
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
		 			if(!metric)
	 					newval = Core.KgToTon(newval);
	 			}else if(cmbTargetTotal.getSelectedIndex() == 2){
	 				// weight tonnes
	 				if(oldTotalsIndex==0)
	 					newval = Core.LengthToWeight((metric ? val : Core.FtToM(val)), thickness, width, density)/1000;
		 			if(oldTotalsIndex==1)
		 				newval = (metric ? val : Core.TonToKg(val))/1000;
		 			oldTotalsIndex = 2;
		 			double length; Job job;
	 				try{
	 					job = (Job) listJobs.getSelectedValue();
	 					length = job.getTotWeightSI() / 1000;
	 					newval = length;
	 				}catch(Exception e){ newval = 0.; }
		 			if(!metric)
	 					newval = Core.TonneToTon(newval);
	 			}
	 			jobFormReady = false;
	 			txtTargetTotal.setText(Double.toString(roundTwoDecimals(newval)));
	 			jobFormReady = true;
	 			
	 			((Job) listJobs.getSelectedValue()).updateQuantityTypes(
	 					cmbUnwindType.getSelectedIndex(),
	 					cmbRewindType.getSelectedIndex(),
	 					cmbTargetTotal.getSelectedIndex());
	 			
	 		}catch(NumberFormatException e2){
	 			txtTargetTotal.setText(("0"));
	 		}
		}
    }
    
    private void UpdateSetReel(){
	    if(jobFormReady && (SetReelType != cmbJobDomain.getSelectedIndex())){
			Double newval = 0., val = 0.;
			int slits = 0;
			try{
				val = (metric ? ((Job)listJobs.getSelectedValue()).getRewindWeight() : Core.KgToLbs(((Job)listJobs.getSelectedValue()).getRewindWeight()));//Double.parseDouble(txtRewindAmount.getText());
				slits = Integer.parseInt(txtSlits.getText());
			} catch(NumberFormatException ee){
				val = 0.;
				slits = 0;
			}
			
			int index = cmbRewindType.getSelectedIndex();
			jobFormReady = false;
			
			if(cmbJobDomain.getSelectedIndex() == 0){
				newval = (slits == 0 ? 0 : val / slits);
				SetReelType = 0;
				
				if(metric)
					cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
				else
					cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (lbs)", "Diameter (in)"}));
				
				cmbRewindType.setSelectedIndex(1);
			}
			else if(cmbJobDomain.getSelectedIndex() == 1){
				newval = val;// * slits;
				SetReelType = 1;
				
				if(index == 2)
					index = 0;
				if(metric)
					cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {/*"Length (m)", */"Weight (kg)"}));
				else
					cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {/*"Length (ft)",*/ "Weight (lbs)"}));
				
				cmbRewindType.setSelectedIndex(0);
			}
			
			
			txtRewindAmount.setText(Double.toString(roundTwoDecimals(newval)));
			jobFormReady = true;
			
		}
    }
    
    //Swap two elements in the list.
    private void swap(int a, int b) {
        Object aObject = listModel.getElementAt(a);
        Object bObject = listModel.getElementAt(b);
        listModel.set(a, bObject);
        listModel.set(b, aObject);
    }
    
    private void swapJob(int a, int b) {
        Object aObject = jobModel.getElementAt(a);
        Object bObject = jobModel.getElementAt(b);
        jobModel.set(a, bObject);
        jobModel.set(b, aObject);
    }
    
    private void swapSchedule(int a, int b) {
        Object aObject = scheduleModel.getElementAt(a);
        Object bObject = scheduleModel.getElementAt(b);
        scheduleModel.set(a, bObject);
        scheduleModel.set(b, aObject);
    }
    
    class ROIListSelectionListener implements ListSelectionListener {
    	@Override
    	public void valueChanged(ListSelectionEvent e){
    		int[] indices = listCompareRoi.getSelectedIndices();
    		if(indices.length > 0){
    			if(indices.length == listModel.getSize())
    				btnROIselectall.setEnabled(false);
    			else
    				btnROIselectall.setEnabled(true);
    			btnROIselectnone.setEnabled(true);
    		}else{
    			btnROIselectnone.setEnabled(false);
    			if(listModel.getSize() > 0)
    				btnROIselectall.setEnabled(true);
    		}
    		
    		if(!e.getValueIsAdjusting() && (tabbedPane.getSelectedIndex()==3||tabbedPane.getSelectedIndex()==4)){
    			UpdateROI();
    		}
    	}
    }
    
    private void UpdateROIValue(){
    	double price = 0.;
    	double contrib = 0.;
    	double profit = 0.;
    	try{
    		price = Math.max(0, Double.parseDouble(txtsellingprice.getText()));
    		if(!metric)
    			price = Core.TonneToTon(price);
    		contrib = Math.max(0, Double.parseDouble(txtcontribution.getText())/100);
    		if(contrib > 1){
    			contrib = 1;
    			txtcontribution.setText("100");
    		}
    		profit = price * contrib;
    		lblvalueadded.setText("£"+ formatDecimal((metric ? profit : Core.TonToTonne(profit)))+(metric ? " / tonne" : " / ton"));
    		RoiData.value = profit;
    		RoiData.sellingprice = price;
    		RoiData.contribution = contrib;
    	}catch(Exception e){
    		RoiData.value = 0;
    		RoiData.sellingprice = 0;
    		RoiData.contribution = 0;
    		if(listCompareRoi.getSelectedIndices().length > 0)
    			ShowMessageStandard("Enter relevant data above to view graph.");
    		lblMarginalImprovement.setText("£0.00 per annum");
    		lblvalueadded.setText((metric ? "£0.00 / tonne" : "£0.00 / ton"));
    		// Display results graph
    		try{
    			pnlGraphProd.remove(pnlGraphProdInner);
    		}catch(Exception e2){}
			pnlGraphProd.add(lblNoGraph);
			lblNoGraph.repaint();
    	}
    	
    	UpdateROIProd();
    }
    
    private void UpdateROIProd(){
    	int[] selection = listCompareRoi.getSelectedIndices();
		Machine[] ms = new Machine[selection.length];
		for(int i=0; i<selection.length;i++)
			ms[i] = (Machine) listModel.get(selection[i]);
    	UpdateROIProd(ms);
    }
    private void UpdateROIProd(Machine[] machs){ //TODO don't need machs actually... (part of results)
    	
    	if(results != null && RoiData.value >= 0){
            
            if(listCompareRoi.getSelectedIndices().length > 1){
            	DefaultCategoryDataset roidata = new DefaultCategoryDataset();
        		String series1 = "Value Added";
        		double offset = 0.;
        		for(int i=0; i<results.getSize(); ++i){
        			String name =  results.get(i).getModel() +": "+ results.get(i).getName();
        			if(i==0){
        				// offset all relative to first value
        				offset = results.get(i).getResult(ResultType.WEIGHT, ResultTime.YEAR) * RoiData.value / 1000;
        				roidata.addValue(0, series1, name);
        				RoiResults.get(i).value = 0;
        			}else{
        				roidata.addValue(results.get(i).getResult(ResultType.WEIGHT, ResultTime.YEAR) * RoiData.value / 1000 - offset, series1, name);
        				RoiResults.get(i).value = results.get(i).getResult(ResultType.WEIGHT, ResultTime.YEAR) * RoiData.value / 1000 - offset;
        			}
        		}

        		// draw graph
        		JFreeChart chart = ChartFactory.createBarChart("", "", "Relative Value Added (£/year)", roidata, PlotOrientation.VERTICAL, false, true, false);
        		CategoryPlot cp = chart.getCategoryPlot();
                cp.setBackgroundPaint(new Color(240,240,240));
                cp.setRangeGridlinePaint(Color.gray);
                CategoryItemRenderer renderer = new CustomRenderer(new Paint[] {new Color(255,85,85), new Color(85,85,255), new Color(85,255,85), new Color(251,251,0), new Color(86,228,200) });
                cp.setRenderer(renderer);
                BarRenderer b = (BarRenderer) cp.getRenderer();
                b.setBarPainter(new StandardBarPainter());
                renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{1}, £{2}", NumberFormat.getInstance()));
                ValueAxis va = cp.getRangeAxis();
                va.setLabelFont(new Font("Tahoma", Font.BOLD, 13));
                
            	ResetStatusLabel();
	    		RoiResults.setProdGraph(chart);
	    		// Display results graph
	    		try{
	    			pnlGraphProd.remove(lblNoGraph);
	    		}catch(Exception e){}
	    		try{
	    			pnlGraphProd.remove(pnlGraphProdInner);
	    		}catch(Exception e){}
	    		pnlGraphProdInner = new ChartPanel(RoiResults.getProdGraph());
				pnlGraphProdInner.setBounds(2, 2, 435, 226);
				pnlGraphProdInner.addMouseListener(new MouseAdapter() {
		        	@Override
		        	public void mouseReleased(MouseEvent e) {
		        		if(e.getButton() == MouseEvent.BUTTON1 && pnlGraphProdInner.contains(e.getPoint()) && RoiResults != null && RoiResults.getProdGraph() != null){
		        			JFreeChart chart = RoiResults.getProdGraph();
		        			JFrame pop = new JFrame();
		        			pop.getContentPane().add(new ChartPanel(chart));
		        			try{
		    					pop.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		    				}catch(Exception e11){

		    				}
		    				pop.setTitle("ROI Graph");
		        			pop.pack();
		        			pop.setLocationRelativeTo(frmTitanRoiCalculator);
		        			pop.setVisible(true);
		        		}
		        	}
		        });
				pnlGraphProd.add(pnlGraphProdInner);
				pnlGraphProdInner.repaint();
				
				// marginal improvement label
				int ind1 = cmbMarg1.getSelectedIndex();
				int ind2 = cmbMarg2.getSelectedIndex();
				double diff = roidata.getValue(0, ind2).doubleValue() - roidata.getValue(0, ind1).doubleValue();
				lblMarginalImprovement.setText("£" + formatDecimal(diff)+" per annum");
            }else{
            	ResetStatusLabel();
            	ShowMessageStandard("Select at least 2 machines to view marginal benefits.");
        		lblMarginalImprovement.setText("£0.00 per annum");
        		// Display results graph
        		try{
        			pnlGraphProd.remove(pnlGraphProdInner);
        		}catch(Exception e){}
    			pnlGraphProd.add(lblNoGraph);
    			lblNoGraph.repaint();
            }
    		
    	}else{
    		ShowMessageStandard("Enter relevant data above to view graph.");
    		lblMarginalImprovement.setText("£0.00 per annum");
    		// Display results graph
    		try{
    			pnlGraphProd.remove(pnlGraphProdInner);
    		}catch(Exception e2){}
			pnlGraphProd.add(lblNoGraph);
			lblNoGraph.repaint();
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
    
    private void RoiDataOrderSwap(int a, int b){
    	EnergyData e = RoiData.energies.get(a);
    	RoiData.energies.set(a, RoiData.energies.get(b));
    	RoiData.energies.set(b, e);
    	MaintData e2 = RoiData.maintenance.get(a);
    	RoiData.maintenance.set(a, RoiData.maintenance.get(b));
    	RoiData.maintenance.set(b, e2);
    }
    private void UpdateEnergyView(){
    	int index = cmbMachineEnergy.getSelectedIndex();
    	ROIData.EnergyData dat = null;
    	try{
    		dat = RoiData.energies.get(index);
    	}catch(Exception e){
    		dat = null;
    	}
    	
    	if(dat != null){
    		double kwhyear = dat.kwhrsperyear;
        	int type = dat.viewtype;
        	initialising = true;
        	switch(type){
	        	case 1: rdbtnAveragePower.doClick(); txtaveragepower.setText((kwhyear==0 ? "" : Double.toString(roundTwoDecimals((environment.HrsPerYear == 0 ? 0 : (kwhyear / environment.HrsPerYear)))))); 
	        	txthourlyusage.setText("");
	        	txtannualusage.setText("");
	        	break;
	        	case 2: rdbtnHourlyUsage.doClick(); txthourlyusage.setText((kwhyear==0 ? "" : Double.toString(roundTwoDecimals((environment.HrsPerYear == 0 ? 0 : (kwhyear / environment.HrsPerYear)))))); 
	        	txtaveragepower.setText("");
	        	txtannualusage.setText("");
	        	break;
	        	case 3: rdbtnAnnualUsage.doClick(); txtannualusage.setText((kwhyear==0 ? "" : Double.toString(roundTwoDecimals(kwhyear / 1000)))); 
	        	txtaveragepower.setText("");
	        	txthourlyusage.setText("");
	        	break;
        	}
        	initialising = false;
    	}else{
    		//rdbtnAveragePower.doClick();
    		rdbtnAveragePower.setSelected(true);
    		
    		/*txtaveragepower.setText("");
    		txthourlyusage.setText("");
        	txtannualusage.setText("");*/
    	}
    }
    private void UpdateEnergyData(){
    	if(!initialising){
	    	int index = cmbMachineEnergy.getSelectedIndex();
	    	double kwhannual = 0.;
	    	if(rdbtnAveragePower.isSelected()){
	    		RoiData.energies.get(index).viewtype = 1;
	    		try{
	        		kwhannual = Double.parseDouble(txtaveragepower.getText()) * environment.HrsPerYear;
	        	}catch(NumberFormatException e){
	        		kwhannual = 0.;
	        	}
	    	}else if(rdbtnHourlyUsage.isSelected()){
	    		RoiData.energies.get(index).viewtype = 2;
	    		try{
	        		kwhannual = Double.parseDouble(txthourlyusage.getText()) * environment.HrsPerYear;
	        	}catch(NumberFormatException e){
	        		kwhannual = 0.;
	        	}
	    	}else{
	    		RoiData.energies.get(index).viewtype = 3;
	    		try{
	        		kwhannual = Double.parseDouble(txtannualusage.getText()) * 1000;
	        	}catch(NumberFormatException e){
	        		kwhannual = 0.;
	        	}
	    	}
	    	
	    	RoiData.energies.get(index).kwhrsperyear = kwhannual;
	    	
	    	UpdateROIEnergy();
    	}
    }
    
    private void UpdateROIEnergy(){
    	int[] selection = listCompareRoi.getSelectedIndices();
		Machine[] ms = new Machine[selection.length];
		for(int i=0; i<selection.length;i++)
			ms[i] = (Machine) listModel.get(selection[i]);
		if(ms.length > 0)
			UpdateROIEnergy(ms);
    }
    private void UpdateROIEnergy(Machine[] mach){
    	if(RoiData !=null && results != null && RoiData.energies.size() >= results.getSize()){ //  TODO first case redundant?
	    	double powercost = 0.;
	    	try{
	    		powercost = Double.parseDouble(txtenergycost.getText());
	    	}catch(Exception e){
	    		powercost = 0.;
	    	}
	    	
	    	RoiData.energycost = powercost;
	    	
	    	DefaultCategoryDataset energydata = new DefaultCategoryDataset();
			String series1 = "Energy Costs";
			for(int i=0; i<results.getSize(); ++i){
				String name =  results.get(i).getModel() +": "+ results.get(i).getName();
				energydata.addValue(RoiData.energies.get(listCompareRoi.getSelectedIndices()[i]).kwhrsperyear * powercost, series1, name);
				RoiResults.get(i).energycost = RoiData.energies.get(listCompareRoi.getSelectedIndices()[i]).kwhrsperyear * powercost;
			}
			
			// draw graph
			JFreeChart chart = ChartFactory.createBarChart("", "", "Energy Cost (£ / year)", energydata, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot cp = chart.getCategoryPlot();
	        cp.setBackgroundPaint(new Color(240,240,240));
	        cp.setRangeGridlinePaint(Color.gray);
	        CategoryItemRenderer renderer = new CustomRenderer(new Paint[] {new Color(255,85,85), new Color(85,85,255), new Color(85,255,85), new Color(251,251,0), new Color(86,228,200) });
	        cp.setRenderer(renderer);
	        BarRenderer b = (BarRenderer) cp.getRenderer();
	        b.setBarPainter(new StandardBarPainter());
	        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{1}, £{2}", NumberFormat.getInstance()));
	        
			RoiResults.setEnergyGraph(chart);
			
			// Display results graph
			try{
				pnlGraphEnergy.remove(lblNoGraph2);
			}catch(Exception e){}
			try{
				pnlGraphEnergy.remove(pnlGraphEnergyInner);
			}catch(Exception e){}
			pnlGraphEnergyInner = new ChartPanel(RoiResults.getEnergyGraph());
			pnlGraphEnergyInner.setBounds(2, 2, 435, 222);
			pnlGraphEnergyInner.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseReleased(MouseEvent e) {
	        		if(e.getButton() == MouseEvent.BUTTON1 && pnlGraphEnergyInner.contains(e.getPoint()) && RoiResults != null && RoiResults.getEnergyGraph() != null){
	        			JFreeChart chart = RoiResults.getEnergyGraph();
	        			JFrame pop = new JFrame();
	        			pop.getContentPane().add(new ChartPanel(chart));
	        			try{
	    					pop.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
	    				}catch(Exception e11){
	    				}
	    				pop.setTitle("ROI Graph");
	        			pop.pack();
	        			pop.setLocationRelativeTo(frmTitanRoiCalculator);
	        			pop.setVisible(true);
	        		}
	        	}
	        });
			pnlGraphEnergy.add(pnlGraphEnergyInner);
			pnlGraphEnergyInner.repaint();
			
			// marginal improvement label
			int ind1 = cmbMargEnergy1.getSelectedIndex();
			int ind2 = cmbMargEnergy2.getSelectedIndex();
			double diff = energydata.getValue(0, ind1).doubleValue() - energydata.getValue(0, ind2).doubleValue();
			lblMarginalEnergy.setText("£"+ formatDecimal(diff)+" per annum");
		
    	}

    }
    
    private void UpdateMaintCosts(){
    	if(!initialising){
	    	int index = cmbMachinesmaintenance.getSelectedIndex();
	    	double labour = 0.;
	    	double parts = 0.;
	    	double hrs = 0.;
	    	double tot = 0.;
	    	try{
	    		labour = Double.parseDouble(txtmaintenanceperhour.getText());
	    	}catch(NumberFormatException e){
	    		labour = 0.;
	    	}
	    	try{
	    		parts = Double.parseDouble(txtmaintenanceparts.getText());
	    	}catch(NumberFormatException e){
	    		parts = 0.;
	    	}
	    	try{
	    		hrs = Double.parseDouble(txtmaintenancehours.getText());
	    	}catch(NumberFormatException e){
	    		hrs = 0.;
	    	}
	    	tot = hrs * (labour) + parts;
	    	
	    	lbltotalmaintcost.setText("£"+ formatDecimal(tot)+" / year");
	    	
	    	RoiData.maintenance.get(index).labourhourly = labour;
	    	RoiData.maintenance.get(index).parts = parts;
	    	RoiData.maintenance.get(index).tothours = hrs;
	    	RoiData.maintenance.get(index).totcost = tot;
	    	
	    	UpdateROIMaint();
    	}
    }
    private void ViewMaintCosts(){
    	int index = cmbMachinesmaintenance.getSelectedIndex();
    	ROIData.MaintData dat = null;
    	try{
    		dat = RoiData.maintenance.get(index);
    	}catch(Exception e){
    		dat = null;
    	}
    	
    	if(dat != null){
    		double labour = dat.labourhourly;
        	double parts = dat.parts;
        	double hrs = dat.tothours;
        	double tot = dat.totcost;
        	initialising = true;
        	txtmaintenanceperhour.setText(Double.toString(roundTwoDecimals(labour)));
        	txtmaintenanceparts.setText(Double.toString(roundTwoDecimals(parts)));
        	txtmaintenancehours.setText(Double.toString(roundTwoDecimals(hrs)));
    		lbltotalmaintcost.setText("£"+ formatDecimal(tot) +" / year");
    		initialising = false;
    	}else
    		lbltotalmaintcost.setText("£0.00 / year");
    	
    	// Update production loss stat
    	try{
			int sel = cmbMachinesmaintenance.getSelectedIndex();
			if(arrayContains(listCompareRoi.getSelectedIndices(), sel))
				lblProdLoss.setText("£"+ formatDecimal(RoiData.value * RoiData.maintenance.get(sel).tothours * results.get(arrayIndex(listCompareRoi.getSelectedIndices(), sel)).getResult(ResultType.WEIGHT, ResultTime.HOUR) / 1000) + " / year");
			else
				lblProdLoss.setText("Machine not selected");
		}catch(Exception e){
			lblProdLoss.setText("£0.00 / year");
		}
    }
    
    private boolean arrayContains(int[] arr, int val){
    	boolean result = false;
    	for(int i=0; i<arr.length; ++i)
    		if(arr[i] == val)
    			result = true;
    	return result;
    }
    private int arrayIndex(int[] arr, int val){
    	int result = 0;
    	for(int i=0; i<arr.length; ++i)
    		if(arr[i] == val)
    			result = i;
    	return result;
    }
    
    private void UpdateROIMaint(){
    	int[] selection = listCompareRoi.getSelectedIndices();
		Machine[] ms = new Machine[selection.length];
		for(int i=0; i<selection.length;i++)
			ms[i] = (Machine) listModel.get(selection[i]);
		if(ms.length > 0)
			UpdateROIMaint(ms);
    }
    private void UpdateROIMaint(Machine[] mach){
    	//ResetStatusLabel();
    	
    	if(results != null && RoiData.maintenance.size() >= results.getSize()){
	    	DefaultCategoryDataset maintdata = new DefaultCategoryDataset();
			String series1 = "Basic Costs";
			String series2 = "Production Loss";
			for(int i=0; i<results.getSize(); ++i){
				String name =  results.get(i).getModel() +": "+ results.get(i).getName();
				maintdata.addValue(RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).totcost, series1, name);
				maintdata.addValue(RoiData.value * RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).tothours * results.get(i).getResult(ResultType.WEIGHT, ResultTime.HOUR) / 1000, series2, name);
				RoiResults.get(i).prodloss = RoiData.value * RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).tothours * results.get(i).getResult(ResultType.WEIGHT, ResultTime.HOUR) / 1000;
				RoiResults.get(i).partcosts = RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).totcost;
				RoiResults.get(i).maintcost = RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).totcost + RoiData.value * RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).tothours * results.get(i).getResult(ResultType.WEIGHT, ResultTime.HOUR) / 1000;
			}
			
			// draw graph
			//JFreeChart chart = ChartFactory.createBarChart("", "", "Maintenance Cost (£ / year)", maintdata, PlotOrientation.VERTICAL, false, true, false);
			JFreeChart chart = ChartFactory.createStackedBarChart("", "", "Maintenance Cost (£ / year)", maintdata, PlotOrientation.VERTICAL, true, true, false);
			CategoryPlot cp = chart.getCategoryPlot();
	        cp.setBackgroundPaint(new Color(240,240,240));
	        cp.setRangeGridlinePaint(Color.gray);
	        CategoryItemRenderer renderer = new CustomRenderer(new Paint[] {new Color(255,85,85), new Color(85,85,255), new Color(85,255,85), new Color(251,251,0), new Color(86,228,200) });
	        //cp.setRenderer(renderer);
	        cp.setShadowGenerator(new DefaultShadowGenerator(7, Color.DARK_GRAY, 1f, 5, -Math.PI / 4));
	        BarRenderer b = (BarRenderer) cp.getRenderer();
	        b.setBarPainter(new StandardBarPainter());
	        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{1}, £{2}", NumberFormat.getInstance()));
	        ValueAxis va = cp.getRangeAxis();
            va.setLabelFont(new Font("Tahoma", Font.BOLD, 13));
            
			RoiResults.setMaintGraph(chart);
			
			// Display results graph
			try{
				pnlGraphMaint.remove(lblNoGraph3);
			}catch(Exception e){}
			try{
				pnlGraphMaint.remove(pnlGraphMaintInner);
			}catch(Exception e){}
			pnlGraphMaintInner = new ChartPanel(RoiResults.getMaintGraph());
			pnlGraphMaintInner.setBounds(2, 2, 435, 238);
			pnlGraphMaintInner.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseReleased(MouseEvent e) {
	        		if(e.getButton() == MouseEvent.BUTTON1 && pnlGraphMaintInner.contains(e.getPoint()) && RoiResults != null && RoiResults.getMaintGraph() != null){
	        			JFreeChart chart = RoiResults.getMaintGraph();
	        			JFrame pop = new JFrame();
	        			pop.getContentPane().add(new ChartPanel(chart));
	        			try{
	    					pop.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
	    				}catch(Exception e11){

	    				}
	    				pop.setTitle("ROI Graph");
	        			pop.pack();
	        			pop.setLocationRelativeTo(frmTitanRoiCalculator);
	        			pop.setVisible(true);
	        		}
	        	}
	        });
			pnlGraphMaint.add(pnlGraphMaintInner);
			pnlGraphMaintInner.repaint();
			
			// Update production loss stat
			try{
				int sel = cmbMachinesmaintenance.getSelectedIndex();
				if(arrayContains(listCompareRoi.getSelectedIndices(), sel))
					lblProdLoss.setText("£"+ formatDecimal(RoiData.value * RoiData.maintenance.get(sel).tothours * results.get(arrayIndex(listCompareRoi.getSelectedIndices(), sel)).getResult(ResultType.WEIGHT, ResultTime.HOUR) / 1000) + " / year");
				else
					lblProdLoss.setText("Machine not selected");
			}catch(Exception e){
				lblProdLoss.setText("£0.00 / year");
			}
			
			// marginal improvement label
			int ind1 = cmbMargMaint1.getSelectedIndex();
			int ind2 = cmbMargMaint2.getSelectedIndex();
			double diff = maintdata.getValue(0, ind1).doubleValue()+maintdata.getValue(1, ind1).doubleValue() - maintdata.getValue(0, ind2).doubleValue()-maintdata.getValue(1, ind2).doubleValue();
			lblMarginalMaint.setText("£"+ formatDecimal(diff)+" per annum");
    	}
    }
    
    private void UpdateROIWaste(){
    	int[] selection = listCompareRoi.getSelectedIndices();
		Machine[] ms = new Machine[selection.length];
		for(int i=0; i<selection.length;i++)
			ms[i] = (Machine) listModel.get(selection[i]);
		if(ms.length > 0)
			UpdateROIWaste(ms);
    }
    private void UpdateROIWaste(Machine[] mach){
    	
    	if(results != null){
	    	//ResetStatusLabel();
			double wasteflags = 0.;
			double wasteguide = 0.;
			double wastetable = 0.;
			try{
				wasteflags = Math.max(0, Double.parseDouble(txtwastesavedflags.getText()));
				if(!metric)
					wasteflags = Core.FtToM(wasteflags);
			}catch(NumberFormatException e){
				wasteflags = 0.;
			}
			try{
				wasteguide = Math.max(0, Double.parseDouble(txtwastesavedguide.getText()));
				if(!metric)
					wasteguide = Core.FtToM(wasteguide);
			}catch(NumberFormatException e){
				wasteguide = 0.;
			}
			
			RoiData.wastesavedflag = wasteflags;
			RoiData.wastesavedguide = wasteguide;
			RoiData.wastesavedtable = wastetable;
				
			DefaultCategoryDataset wastedata = new DefaultCategoryDataset();
			String series1 = "Waste Saved";
			for(int i=0; i<results.getSize(); ++i){
				double totsave = 0.;
				double SchedulesPerYear = results.get(i).ScheduleTime == 0 ? 0 : environment.HrsPerYear / results.get(i).ScheduleTime;
				String name =  results.get(i).getModel() +": "+ results.get(i).getName();
				if(mach[i].flags)
					totsave += wasteflags * results.get(i).SplicesPerSchedule * SchedulesPerYear;
				if(mach[i].alignment)
					totsave += wasteguide * results.get(i).MothersPerSchedule * SchedulesPerYear;
				if(mach[i].splice_table)
					totsave += wastetable * results.get(i).SplicesPerSchedule * SchedulesPerYear;
				wastedata.addValue(totsave, series1, name);
				RoiResults.get(i).wastesave = totsave;
				double conversion = results.get(i).getResult(ResultType.LENGTH, ResultTime.HOUR) == 0 ? 0 : results.get(i).getResult(ResultType.WEIGHT, ResultTime.HOUR) / results.get(i).getResult(ResultType.LENGTH, ResultTime.HOUR) / 1000;
				RoiResults.get(i).wasteval = totsave * conversion * RoiData.sellingprice;
			}
	
			// draw graph
			JFreeChart chart = ChartFactory.createBarChart("", "", "Waste Reduced (m / year)", wastedata, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot cp = chart.getCategoryPlot();
	        cp.setBackgroundPaint(new Color(240,240,240));
	        cp.setRangeGridlinePaint(Color.gray);
	        CategoryItemRenderer renderer = new CustomRenderer(new Paint[] {new Color(255,85,85), new Color(85,85,255), new Color(85,255,85), new Color(251,251,0), new Color(86,228,200) });
	        cp.setRenderer(renderer);
	        BarRenderer b = (BarRenderer) cp.getRenderer();
	        b.setBarPainter(new StandardBarPainter());
	        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{1}, {2}m", NumberFormat.getInstance()));
	        
			RoiResults.setWasteGraph(chart);
			
			// Display results graph
			try{
				pnlGraphWaste.remove(lblNoGraph4);
			}catch(Exception e){}
			try{
				pnlGraphWaste.remove(pnlGraphWasteInner);
			}catch(Exception e){}
			pnlGraphWasteInner = new ChartPanel(RoiResults.getWasteGraph());
			pnlGraphWasteInner.setBounds(2, 2, 435, 258);
			pnlGraphWasteInner.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseReleased(MouseEvent e) {
	        		if(e.getButton() == MouseEvent.BUTTON1 && pnlGraphWasteInner.contains(e.getPoint()) && RoiResults != null && RoiResults.getWasteGraph() != null){
	        			JFreeChart chart = RoiResults.getWasteGraph();
	        			JFrame pop = new JFrame();
	        			pop.getContentPane().add(new ChartPanel(chart));
	        			try{
	    					pop.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
	    				}catch(Exception e11){
	
	    				}
	    				pop.setTitle("ROI Graph");
	        			pop.pack();
	        			pop.setLocationRelativeTo(frmTitanRoiCalculator);
	        			pop.setVisible(true);
	        		}
	        	}
	        });
			pnlGraphWaste.add(pnlGraphWasteInner);
			pnlGraphWasteInner.repaint();
			
			// marginal improvement label
			int ind1 = cmbMargWaste1.getSelectedIndex();
			int ind2 = cmbMargWaste2.getSelectedIndex();
			double diff = wastedata.getValue(0, ind2).doubleValue() - wastedata.getValue(0, ind1).doubleValue();
			lblMarginalWaste.setText(formatDecimal((metric ? diff : Core.MToFt(diff)))+(metric ? "m per annum" : "ft per annum"));
			if(RoiData.value <= 0)
				lblMarginalWasteValue.setText("(£0.00 per annum)");
			else{
				double conversion1 = results.get(ind1).getResult(ResultType.LENGTH, ResultTime.HOUR) == 0 ? 0 : results.get(ind1).getResult(ResultType.WEIGHT, ResultTime.HOUR) / results.get(ind1).getResult(ResultType.LENGTH, ResultTime.HOUR);
				double conversion2 = results.get(ind2).getResult(ResultType.LENGTH, ResultTime.HOUR) == 0 ? 0 : results.get(ind2).getResult(ResultType.WEIGHT, ResultTime.HOUR) / results.get(ind2).getResult(ResultType.LENGTH, ResultTime.HOUR);
				double weight1 = conversion1 * wastedata.getValue(0, ind1).doubleValue();
				double weight2 = conversion2 * wastedata.getValue(0, ind2).doubleValue();
				double diff_weight = weight2 - weight1;
				// convert to tonnes
				diff_weight = diff_weight / 1000;
				lblMarginalWasteValue.setText("(£"+formatDecimal(diff_weight * RoiData.sellingprice)+" per annum)");
			}
				
    	}
		
    }
    
    private void UpdateROI(){
    	//if(tabbedPane.getSelectedIndex() != 3 && tabbedPane.getSelectedIndex() != 4){
    	//	return;
    	//}else{
    	
    	//UpdateRoiCmbs();
    	
    	if(listCompareRoi.getSelectedIndices() != null && listCompareRoi.getSelectedIndices().length > 0 && listCompareRoi.getSelectedIndices().length <= listCompareRoi.getModel().getSize() && results!=null && results.getSize()>0){
    		EnableROIfull();
    		
    		int[] selection = listCompareRoi.getSelectedIndices();
    		Machine[] ms = new Machine[selection.length];
    		for(int i=0; i<selection.length;i++)
    			ms[i] = (Machine) listModel.get(selection[i]);
    		
    		/*// preserve roi data which has been input when change of machine selection
    		if(selection.length != RoiData.selection.length){
	    		// update lists in roidata
	    		//if(RoiData.selection.length != 0){
	    			int[] prevselection = RoiData.selection;
	    			int[] newselection = selection;
	    			// selection only ever changes by one
	    			int changeindex = 0;
	    			for(int i=0; i<Math.max(prevselection.length, newselection.length);++i){
	    				if(i == prevselection.length || i == newselection.length || (prevselection[i] != newselection[i])){
	    					changeindex = i;
	    					break;
	    				}
	    			}
	    			if(newselection.length > prevselection.length){
	    				// inserted new
	    				//RoiData.energies.add(changeindex, RoiData.new EnergyData());
	    				//RoiData.maintenance.add(changeindex, RoiData.new MaintData());
	    			}else{
	    				// deleted one
	    				//RoiData.energies.remove(changeindex);
	    				//RoiData.maintenance.remove(changeindex);
	    			}
	    		//}else{
	    		//	RoiData.setSize(selection.length);
	    		//}
	    		RoiData.selection = selection;
	    	}*/
    		
    		UpdateEnergyView();
    		ViewMaintCosts();
    		
    		UpdateROIProd(ms);
    		UpdateROIEnergy(ms);
    		UpdateROIMaint(ms);
    		UpdateROIWaste(ms);
    	}else{
    		DisableROIfull();
    		UpdateEnergyView();
    		ViewMaintCosts();
    	}
    	//}
    }
    
    private void EnableROI(){
    	
		lblSellingPrice.setEnabled(true);
		lblPerTonne.setEnabled(true);
		lblpound1.setEnabled(true);
		lblvalueadded.setEnabled(true);
		txtcontribution.setEnabled(true);
		txtsellingprice.setEnabled(true);
		lblValueAddedlbl.setEnabled(true);
		lblpercent.setEnabled(true);
		lblContribution.setEnabled(true);

		lblMachine_1.setEnabled(true);
		cmbMachineEnergy.setEnabled(true);
		lblPleaseSetPower.setEnabled(true);
		lblEnergyCostl.setEnabled(true);
		lblpound2.setEnabled(true);
		lblPerKwh.setEnabled(true);
		txtenergycost.setEnabled(true);
		
		rdbtnAveragePower.setEnabled(true);
		rdbtnHourlyUsage.setEnabled(true);
		rdbtnAnnualUsage.setEnabled(true);
		
		txtmaintenancehours.setEnabled(true);
		txtmaintenanceparts.setEnabled(true);
		txtmaintenanceperhour.setEnabled(true);
		cmbMachinesmaintenance.setEnabled(true);
		lblpound10.setEnabled(true);
		lblpound11.setEnabled(true);
		lbltotalmaintcost.setEnabled(true);
		lblProdLoss.setEnabled(true);
		lblMaintenanceHoursPer.setEnabled(true);
		lblRepairCostsPer.setEnabled(true);
		lblPartsCostsPer.setEnabled(true);

		lblWasteSavedPer_1.setEnabled(true);
		lblWasteSavedPer_2.setEnabled(true);
		lblM_1.setEnabled(true);
		lblM_2.setEnabled(true);
		txtwastesavedflags.setEnabled(true);
		txtwastesavedguide.setEnabled(true);
		
		// update machine selection combobox
		int[] indices = listCompareRoi.getSelectedIndices();
		String[] machineNames = new String[indices.length];
		for(int i=0; i<indices.length; ++i){
			machineNames[i] = ((Machine) listCompareRoi.getModel().getElementAt(indices[i])).model.toString() + ": " + ((Machine) listCompareRoi.getModel().getElementAt(indices[i])).name;
		}

		//cmbMachineEnergy.setModel(new DefaultComboBoxModel(machineNames));
    	// update machine selection combobox
		String[] allMachineNames = new String[listModel.getSize()];
		for(int i=0; i<listModel.getSize(); ++i){
			allMachineNames[i] = ((Machine) listModel.getElementAt(i)).model.toString() + ": " + ((Machine) listModel.getElementAt(i)).name;
		}
    	cmbMachineEnergy.setModel(new DefaultComboBoxModel(allMachineNames));
    	cmbMachinesmaintenance.setModel(new DefaultComboBoxModel(allMachineNames));
    	//cmbMachinesmaintenance.setSelectedIndex(0);
    	
    	/*if(rdbtnHourlyUsage.isSelected()){
			txthourlyusage.setEnabled(true);
			lblKwh.setEnabled(true);
			rdbtnHourlyUsage.doClick();
		}else if(rdbtnAnnualUsage.isSelected()){
			txtannualusage.setEnabled(true);
			lblKwh_1.setEnabled(true);
			rdbtnAnnualUsage.doClick();
		}else{//if(rdbtnAveragePower.isSelected()){*/
			txtaveragepower.setEnabled(true);
			lblKw.setEnabled(true);
			/*rdbtnAveragePower.doClick();
		}*/
    	//UpdateEnergyView();
    }
    private void EnableROIfull(){
    	RoiResults = new ResultSetROI();
		RoiResults.set(results);
		
    	cmbMarg1.setEnabled(true);
		cmbMarg2.setEnabled(true);
		cmbMargEnergy1.setEnabled(true);
		cmbMargEnergy2.setEnabled(true);
		cmbMargMaint1.setEnabled(true);
		cmbMargMaint2.setEnabled(true);
		cmbMargWaste1.setEnabled(true);
		cmbMargWaste2.setEnabled(true);
		lblMarginalImprovement.setEnabled(true);
		lblMarginalEnergy.setEnabled(true);
		lblMarginalMaint.setEnabled(true);
		lblMarginalWaste.setEnabled(true);
		
    	// update machine selection combobox
		int[] indices = listCompareRoi.getSelectedIndices();
		String[] machineNames = new String[indices.length];
		for(int i=0; i<indices.length; ++i){
			machineNames[i] = ((Machine) listCompareRoi.getModel().getElementAt(indices[i])).model.toString() + ": " + ((Machine) listCompareRoi.getModel().getElementAt(indices[i])).name;
		}
		cmbMarg1.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMarg2.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargEnergy1.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargEnergy2.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargMaint1.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargMaint2.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargWaste1.setModel(new DefaultComboBoxModel(machineNames));
    	cmbMargWaste2.setModel(new DefaultComboBoxModel(machineNames));
    	if(machineNames.length > 1){
    		cmbMarg2.setSelectedIndex(1);
    		cmbMargEnergy2.setSelectedIndex(1);
    		cmbMargMaint2.setSelectedIndex(1);
    		cmbMargWaste2.setSelectedIndex(1);
    	}
    	
    	lblPerTonne.setText((metric ? "per tonne" : "per ton"));
    	lblM_1.setText((metric ? "m" : "ft"));
    	lblM_2.setText((metric ? "m" : "ft"));
    }
    
    private void DisableROI(){
    	
		lblSellingPrice.setEnabled(false);
		lblPerTonne.setEnabled(false);
		lblvalueadded.setEnabled(false);
		lblpound1.setEnabled(false);
		txtcontribution.setEnabled(false);
		txtsellingprice.setEnabled(false);
		lblValueAddedlbl.setEnabled(false);
		lblpercent.setEnabled(false);
		lblContribution.setEnabled(false);

		lblMachine_1.setEnabled(false);
		cmbMachineEnergy.setEnabled(false);
		lblPleaseSetPower.setEnabled(false);
		lblEnergyCostl.setEnabled(false);
		lblpound2.setEnabled(false);
		lblPerKwh.setEnabled(false);
		txtenergycost.setEnabled(false);
		txtaveragepower.setEnabled(false);
		txthourlyusage.setEnabled(false);
		txtannualusage.setEnabled(false);
		rdbtnAveragePower.setEnabled(false);
		rdbtnHourlyUsage.setEnabled(false);
		rdbtnAnnualUsage.setEnabled(false);
		lblKw.setEnabled(false);
		lblKwh.setEnabled(false);
		lblKwh_1.setEnabled(false);

		txtmaintenancehours.setEnabled(false);
		txtmaintenanceparts.setEnabled(false);
		txtmaintenanceperhour.setEnabled(false);
		cmbMachinesmaintenance.setEnabled(false);
		lblpound10.setEnabled(false);
		lblpound11.setEnabled(false);
		lbltotalmaintcost.setEnabled(false);
		lblProdLoss.setEnabled(false);
		lblMaintenanceHoursPer.setEnabled(false);
		lblRepairCostsPer.setEnabled(false);
		lblPartsCostsPer.setEnabled(false);

		lblWasteSavedPer_1.setEnabled(false);
		lblWasteSavedPer_2.setEnabled(false);
		lblM_1.setEnabled(false);
		lblM_2.setEnabled(false);
		txtwastesavedflags.setEnabled(false);
		txtwastesavedguide.setEnabled(false);
		
    }
    private void DisableROIfull(){
    	RoiResults = null;
    	//RoiData.reset();
    	
    	try{
	    	//rdbtnAveragePower.setSelected(true);
	    	cmbMarg1.setModel(new DefaultComboBoxModel());
	    	cmbMarg2.setModel(new DefaultComboBoxModel());
	    	cmbMargEnergy1.setModel(new DefaultComboBoxModel());
	    	cmbMargEnergy2.setModel(new DefaultComboBoxModel());
	    	cmbMargWaste1.setModel(new DefaultComboBoxModel());
	    	cmbMargWaste2.setModel(new DefaultComboBoxModel());
	    	cmbMargMaint1.setModel(new DefaultComboBoxModel());
	    	cmbMargMaint2.setModel(new DefaultComboBoxModel());
	    	//cmbMachineEnergy.setModel(new DefaultComboBoxModel());
	    	//cmbMachinesmaintenance.setModel(new DefaultComboBoxModel());
    	}catch(IllegalArgumentException e){
    		
    	}
    	
    	cmbMarg1.setEnabled(false);
		cmbMarg2.setEnabled(false);
		cmbMargEnergy1.setEnabled(false);
		cmbMargEnergy2.setEnabled(false);
		cmbMargMaint1.setEnabled(false);
		cmbMargMaint2.setEnabled(false);
		cmbMargWaste1.setEnabled(false);
		cmbMargWaste2.setEnabled(false);
		lblMarginalImprovement.setEnabled(false);
		lblMarginalEnergy.setEnabled(false);
		lblMarginalMaint.setEnabled(false);
		lblMarginalWaste.setEnabled(false);
		
    	lblMarginalImprovement.setText("£0.00 per annum");
		lblMarginalEnergy.setText("£0.00 per annum");
		lblMarginalMaint.setText("£0.00 per annum");
		lblMarginalWaste.setText((metric ? "0m per annum" : "0ft per annum"));
		lblMarginalWasteValue.setText("(£0.00 per annum)");
		//clear graphs
		try{
			pnlGraphProd.remove(pnlGraphProdInner);
		}catch(Exception e){}
		pnlGraphProd.add(lblNoGraph);
		lblNoGraph.repaint();
		try{
			pnlGraphEnergy.remove(pnlGraphEnergyInner);
		}catch(Exception e){}
		pnlGraphEnergy.add(lblNoGraph2);
		lblNoGraph2.repaint();
		try{
			pnlGraphMaint.remove(pnlGraphMaintInner);
		}catch(Exception e){}
		pnlGraphMaint.add(lblNoGraph3);
		lblNoGraph3.repaint();
		try{
			pnlGraphWaste.remove(pnlGraphWasteInner);
		}catch(Exception e){}
		pnlGraphWaste.add(lblNoGraph4);
		lblNoGraph4.repaint();
    }
    
    // Refresh analyses
    class MultiSelectionListener implements ListSelectionListener {
    	@Override
    	public void valueChanged(ListSelectionEvent e){
    		if(!e.getValueIsAdjusting() && ((tabbedPane.getSelectedIndex() == 3 || tabbedPane.getSelectedIndex() == 4))){
    			oldMachineIndex = 0;
    			UpdateAnalysis();
    		}
    	}
    }

    private class BtnShowTimingsActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		JFrame popGraph = new JFrame();
    		
    		ChartPanel cpanel = new ChartPanel(results.getTimingGraph());
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
    		popGraph.setLocationRelativeTo(frmTitanRoiCalculator);
    	}
    }
    /*private class RdbtnmntmPrototypeActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		MODEL_VERSION = 1;
    		UpdateAnalysis();
    	}
    }
    private class RdbtnmntmStandardActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		MODEL_VERSION = 2;
    		UpdateAnalysis();
    	}
    }*/
    private void SaveAndClose(){
    	chart = null;
		breakdown = null;
	
		StatusDialog sd = null;
		if(environment.SaveOnClose)
			sd = new StatusDialog("Saving settings...");
		else
			sd = new StatusDialog("Closing...");
		sd.setLocationRelativeTo(frmTitanRoiCalculator);
		
		SaveFileWorker worker = new SaveFileWorker(new File(/*environment.SaveFileName*/Consts.SETTINGS_FILENAME), sd, true);
        
        worker.execute();
        sd.setVisible(true);
        
		System.exit(0);
    }
    
    private class FrmTitanRoiCalculatorWindowListener extends WindowAdapter {
    	@Override
    	public void windowClosing(WindowEvent arg0) {
			SaveAndClose();
    	}
    }
    private void SaveSettings(String filename, Production prod){
    	if(!prod.SaveFileName.equals(filename))
    		SaveSettings(prod.SaveFileName, prod);
    	
    	Production p = null;
		try {
			p = (Production) prod.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//if(! p.SaveSchedule)
    	p.setSchedule(null);
    	
        String extension = ".ser";
        File file;
        
        if(!filename.endsWith(extension))
          file = new File(filename + extension);
        else
        	file = new File(filename);

        if(!file.getParentFile().exists())
        	file.getParentFile().mkdirs();
        
        try {
        	FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(p);
            oos.close();
		} catch (IOException e1) {
			e1.printStackTrace();   // if we fail here, it's too late anyway...
		}
    }
    
    /*private void DeleteFile(String s){
    	DeleteFile(new File(s));
    }*/
    private void DeleteFile(File f){
    	try{
	        // Check file exists
	        if (f.exists()){
	
	        	// Check file not write protected
		        if (f.canWrite()){
		
		        	// Check not a directory
			        if (! f.isDirectory()) {
			        	
			        	// Attempt to delete it
				        f.delete();
			        }
		        }
	        }
    	}catch(Exception e){}
    }
    
    private class MntmSaveActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		ResetStatusLabel();
    		
    		if(tabbedPane.getSelectedIndex() == 0){
    			// Machine save
    		
	    		if(listMachines.getSelectedIndex() == -1){
	    			ShowMessage("Please select a machine to save.");
	    		}else{
	    			
	    			Machine m = (Machine) listMachines.getSelectedValue();
	    			fc = new OpenSaveFileChooser();
					fc.setFileFilter(new OBJfilter(1));
	    			fc.type = 1;
	    			
	    			int returnVal = fc.showSaveDialog(frmTitanRoiCalculator);
	    			if (returnVal == JFileChooser.APPROVE_OPTION) {
	    				
	    	            File file = fc.getSelectedFile();
	    	            
	    	            String path = file.getAbsolutePath();
	
	    	            String extension = ".ser";
	
	    	            if(!path.endsWith(extension))
	    	            {
	    	              file = new File(path + extension);
	    	            }
	
	    	            try {
	    	            	/*System.setProperty("java.security.policy", "C:\\Users\\king0j\\.java.policy");
	    	            	Policy.getPolicy().refresh();
	    	            	System.setSecurityManager(new SecurityManager());*/
	    	            	FileOutputStream fout = new FileOutputStream(file);
	    	                ObjectOutputStream oos = new ObjectOutputStream(fout);
	    	                oos.writeObject(m);
	    	                oos.close();
	    	                ShowMessageSuccess("File saved.");
	    				} catch (Exception e1) {
	    					JOptionPane.showMessageDialog(frmTitanRoiCalculator, "Error writing file.", "Error", JOptionPane.ERROR_MESSAGE);
	    				}
	    			}
	    		}
    		}
    		
    		else if(tabbedPane.getSelectedIndex() == 1){
    			// Job save
    			
    			if(listJobs.getSelectedIndex() == -1){
	    			ShowMessage("Please select a job to save.");
	    		}else{
	    			
	    			Job j = (Job) listJobs.getSelectedValue();
	    			fc = new OpenSaveFileChooser();
					fc.setFileFilter(new OBJfilter(2));
	    			fc.type = 1;
	    			
	    			int returnVal = fc.showSaveDialog(frmTitanRoiCalculator);
	    			if (returnVal == JFileChooser.APPROVE_OPTION) {
	    				
	    	            File file = fc.getSelectedFile();
	    	            
	    	            String path = file.getAbsolutePath();
	
	    	            String extension = ".ser";
	
	    	            if(!path.endsWith(extension))
	    	            {
	    	              file = new File(path + extension);
	    	            }
	    	            
	    	            try {
	    	            	FileOutputStream fout = new FileOutputStream(file);
	    	                ObjectOutputStream oos = new ObjectOutputStream(fout);
	    	                oos.writeObject(j);
	    	                oos.close();
	    	                ShowMessageSuccess("File saved.");
	    				} catch (Exception e1) {
	    					JOptionPane.showMessageDialog(frmTitanRoiCalculator, "Error writing file.", "Error", JOptionPane.ERROR_MESSAGE);
	    				}
	    			}
	    		}
    		}
    		else if(tabbedPane.getSelectedIndex() == 3){
    			btnSaveToFile.doClick();
    		}
    	}
    }
    private class MntmOpenActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		ResetStatusLabel();
    		
    		if(tabbedPane.getSelectedIndex() == 0){
    			// Machine open
    			formReady = false;
    		
	    		Machine m = null;
	    		int index = listMachines.getSelectedIndex();
	    		fc = new OpenSaveFileChooser();
				fc.setFileFilter(new OBJfilter(1));
    			fc.type = 1;
				
				int returnVal = fc.showOpenDialog(frmTitanRoiCalculator);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
		            File file = fc.getSelectedFile();
		            
		            /*String path = file.getAbsolutePath();
	
		            String extension = ".ser";
	
		            if(!path.endsWith(extension))
		            {
		              file = new File(path + extension);
		            }*/
		            
		            int size = listModel.getSize();
	    
		            try {
		            	FileInputStream fin = new FileInputStream(file);
		                ObjectInputStream ois = new ObjectInputStream(fin);
		                m = (Machine) ois.readObject();
		                ois.close();
		                
		                if(size >= Consts.MACH_LIST_LIMIT){    // Max list size
		        	    	ShowMessage("Maximum number of machines allocated. Please delete before attempting to add more."); formReady = true;
		        	    	return;
		        	    }
		        	    
		        	    if(machNames.contains(m.name.toLowerCase())){
		        	    	ShowMessage("Duplicate machine names not allowed. Please choose a different name."); formReady = true;
		        	    	return;
		        	    }
		                
		                if (index == -1 || (index+1 == size)){
							listModel.addElement(m);
						}else{
							int[] sel = listCompare.getSelectedIndices();
					    	int[] selection = new int[sel.length + 1];
					    	System.arraycopy(sel, 0, selection, 0, sel.length);
					    	listCompare.setSelectedIndices(new int[]{});
					    	listModel.insertElementAt(m, index + 1);
					        
					    	boolean max = false;
					        for(int i=0; i<selection.length; i++){
					        	if(selection[i] >= index + 1 && !max){
					        		if(i < selection.length - 1)
					        			System.arraycopy(selection, i, selection, i+1, selection.length - i - 1);
					        		selection[i] = index + 1;
					        		max = true;
					        	}
					        	else if(selection[i] >= index + 1)
					        		selection[i] = selection[i] + 1;
					        }
					        RoiData.energies.add(index + 1, RoiData.new EnergyData());
					        RoiData.maintenance.add(index + 1, RoiData.new MaintData());
					        listCompare.setSelectedIndices(selection);
						}
		                
		                listMachines.setSelectedIndex((index == -1 || (index+1 == listModel.getSize())) ? listModel.getSize()-1 : index+1);
		                
		                UpdateForm();
		            
		                machNames.add(m.name.toLowerCase());
		                
		                ShowMessageSuccess("File loaded.");
					
		            } catch (ClassCastException e1){
		            	ShowMessage("Machine file required, job file found.");
					} catch (ClassNotFoundException e1) {
						ShowMessage("File error.");
					} catch (FileNotFoundException e1){
						ShowMessage("File not found.");
					} catch (Exception e1) {
						ShowMessage("File error.");
					} finally {
						if(!(listModel.getSize() == 0))
								formReady = true;
					}
		            
				}
				if(!(listModel.getSize() == 0))
					formReady = true;
				
	    	}
			
			else if(tabbedPane.getSelectedIndex() == 1){
				// Job open
				//jobFormReady = false;
	    		jobFormReady = true;
	    		
	    		Job j = null;
	    		int index = listJobs.getSelectedIndex();
	    		fc = new OpenSaveFileChooser();
				fc.setFileFilter(new OBJfilter(2));
    			fc.type = 1;
				
				int returnVal = fc.showOpenDialog(frmTitanRoiCalculator);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
		            File file = fc.getSelectedFile();
		            
		            int size = jobModel.getSize();
	    
		            try {
		            	FileInputStream fin = new FileInputStream(file);
		                ObjectInputStream ois = new ObjectInputStream(fin);
		                j = (Job) ois.readObject();
		                ois.close();
		                
		                if(size >= Consts.MACH_LIST_LIMIT){    // Max list size
		        	    	ShowMessage("Maximum number of jobs allocated. Please delete before attempting to add more."); //jobFormReady = true;
		        	    	return;
		        	    }
		        	    
		        	    if(jobNames.contains(j.getName().toLowerCase())){
		        	    	ShowMessage("Duplicate job names not allowed. Please choose a different name."); //jobFormReady = true;
		        	    	return;
		        	    }
		                
		                if (index == -1 || (index+1 == size)){
							jobModel.addElement(j);
						}else{
							jobModel.insertElementAt( j , index + 1);
						}
		                
		                listJobs.setSelectedIndex((index == -1 || (index+1 == jobModel.getSize())) ? jobModel.getSize()-1 : index+1);
		                
		               // jobFormReady = true;
		                UpdateJobForm(); //jobFormReady = false;
		            
		                jobNames.add(j.getName().toLowerCase());
		                
		                ShowMessageSuccess("File loaded.");
					
		            } catch (ClassCastException e1){
		            	ShowMessage("Job file required, machine file found.");
					} catch (ClassNotFoundException e1) {
						ShowMessage("File error.");
					} catch (FileNotFoundException e1){
						ShowMessage("File not found.");
					} catch (Exception e1) {
						ShowMessage("File error.");
					} finally {
						//if(!(jobModel.getSize() == 0))
							//jobFormReady = true;
					}
		            
				}
				//if(!(jobModel.getSize() == 0))
					//jobFormReady = true;
				
			}
    	}
    }

    private void ResetForm(){
    	ResetStatusLabel();
		
		chckbxAlignmentGuide.setSelected(false);
		chckbxAutoCutoff.setSelected(false);
		chckbxAutostripping.setSelected(false);
		chckbxAutoTapeCore.setSelected(false);
		chckbxAutoTapeTail.setSelected(false);
		chckbxExtraRewind.setSelected(false);
		chckbxFlag.setSelected(false);
		chckbxRollConditioning.setSelected(false);
		chckbxSpliceTable.setSelected(false);
		chckbxTurretSupport.setSelected(false);
		chckbxSelectAll.setSelected(false);
		cmbCorepos.setSelectedIndex(0);
		if(cmbKnives.isEnabled())
			cmbKnives.setSelectedIndex(0);
		//cmbRewindCore.setSelectedIndex(0);
		cmbRewindCtrl.setSelectedIndex(0);
		cmbSpeed.setSelectedIndex(0);
		cmbUnloader.setSelectedIndex(0);
		cmbUnwindDrive.setSelectedIndex(0);
		
		//txtMachName.setText("");
    }
    
    boolean FileSaveError = false;
    boolean FileSaveWarning = false;
    public class SaveFileWorker extends SwingWorker<Void, Void> {
		
		File file;
		StatusDialog a;
		boolean closing;
		
		public SaveFileWorker(File f, StatusDialog ab, boolean closing){
			file = f;
			a = ab;
			this.closing = closing;
		}
		
		@Override
        public Void doInBackground() {
			if(closing)
				SaveSettings(file.getPath(), environment);
			else{
				try{
					UpdateAnalysis();
	            	UpdateROI();
					boolean result = WriteSpreadsheet(file);
					FileSaveError = false;
					FileSaveWarning = false;
					if(!result){
						a.lblMessage.setText("Saved, but possible errors");
						FileSaveWarning = true;
					}
				}catch(Exception e){
					a.lblMessage.setText("Failed");
					if(e.getMessage() != null && !e.getMessage().equals(""))
						JOptionPane.showMessageDialog(frmTitanRoiCalculator, e.getMessage(), "Save error", JOptionPane.ERROR_MESSAGE);
					FileSaveError = true;
				}
					
			}
			
        	return null;
        }
        
        @Override
        public void done() {
          a.dispose();
		return;
        }
	}
 
    public class MachineListSelectionListener implements ListSelectionListener {
	    //Listener method for list selection changes.
	    public void valueChanged(ListSelectionEvent e) {
	    	//if(formReady){
	    	boolean resetForm = false;
	        if (e.getValueIsAdjusting() == false) {
	        	if(! formReady)
	        		resetForm = true;
	        	formReady = false;
	        	
	            if (listMachines.getSelectedIndex() == -1) {
	            //No selection: disable delete, up, and down buttons.
	            	btnMachDelete.setEnabled(false);
	                btnMachUp.setEnabled(false);
	                btnMachDown.setEnabled(false);
	                //txtMachName.setText("");
	                DisableForm();
	                DisableROI();
	                machine = null;
	 
	            } else if (listMachines.getSelectedIndices().length > 1) {
	            //Multiple selection: disable up and down buttons.
	            	btnMachDelete.setEnabled(true);
	                btnMachUp.setEnabled(false);
	                btnMachDown.setEnabled(false);
	 
	            } else {
	            //Single selection: permit all operations.
	                btnMachDelete.setEnabled(true);
	                
	                if(listMachines.getSelectedIndex() == 0)
	                	btnMachUp.setEnabled(false);
	                else
	                	btnMachUp.setEnabled(true);
	                
	                if(listMachines.getSelectedIndex() == listModel.getSize()-1)
	                	btnMachDown.setEnabled(false);
	                else
	                	btnMachDown.setEnabled(true);
	                
	                machine = (Machine) listMachines.getSelectedValue();
	                
	                EnableROI();
	                
	                //txtMachName.setText(listMachines.getSelectedValue().toString());
	                txtMachName.setEnabled(true);
	                lblMachName.setEnabled(true);
	                btnMachReset.setEnabled(true);
	                rdbtnCustom.setEnabled(true);
	                rdbtnER610.setEnabled(true);
	                rdbtnSR800.setEnabled(true);
	                rdbtnSR9DS.setEnabled(true);
	                rdbtnSR9DT.setEnabled(true);
	                chckbxSelectAll.setEnabled(true);
	                
	                switch(machine.model){
		                case ER610: rdbtnClick("ER610"); break;
						case SR9DS: rdbtnClick("SR9DS"); break;
						case SR9DT: rdbtnClick("SR9DT"); break;
						case SR800: rdbtnClick("SR800"); break;
						case CUSTOM: rdbtnClick("Custom"); break;
	                }
	            }
	            
	            UpdateRoiCmbs();
	            
	            UpdateForm();
	            
	            UpdateSelectAllChckbx();
	            
	            formReady = true;
	            if(resetForm)
	            	formReady = false;
	        }
	    }
	    //}
    }
    
    private void ClearPowerTxts(){
    	txtaveragepower.setText("");
    	txthourlyusage.setText("");
    	txtannualusage.setText("");
    }
    
    private void UpdateRoiCmbs(){
    	// update machine selection combobox
    	try{
			String[] allMachineNames = new String[listModel.getSize()];
			for(int i=0; i<listModel.getSize(); ++i){
				allMachineNames[i] = ((Machine) listModel.getElementAt(i)).model.toString() + ": " + ((Machine) listModel.getElementAt(i)).name;
			}
	    	cmbMachineEnergy.setModel(new DefaultComboBoxModel(allMachineNames));
	    	cmbMachinesmaintenance.setModel(new DefaultComboBoxModel(allMachineNames));
    	}catch(Exception e){
    		cmbMachineEnergy.setModel(new DefaultComboBoxModel());
	    	cmbMachinesmaintenance.setModel(new DefaultComboBoxModel());
    	}
    }
    
    private void UpdateSelectAllChckbx(){
    	boolean selectall = true;
    	
    	if(chckbxAlignmentGuide.isEnabled() && !chckbxAlignmentGuide.isSelected())
    		selectall = false;
    	if(chckbxAutoCutoff.isEnabled() && !chckbxAutoCutoff.isSelected())
    		selectall = false;
    	if(chckbxAutostripping.isEnabled() && !chckbxAutostripping.isSelected())
    		selectall = false;
    	if(chckbxAutoTapeCore.isEnabled() && !chckbxAutoTapeCore.isSelected())
    		selectall = false;
    	if(chckbxAutoTapeTail.isEnabled() && !chckbxAutoTapeTail.isSelected())
    		selectall = false;
    	if(chckbxExtraRewind.isEnabled() && !chckbxExtraRewind.isSelected())
    		selectall = false;
    	if(chckbxFlag.isEnabled() && !chckbxFlag.isSelected())
    		selectall = false;
    	if(chckbxRollConditioning.isEnabled() && !chckbxRollConditioning.isSelected())
    		selectall = false;
    	if(chckbxSpliceTable.isEnabled() && !chckbxSpliceTable.isSelected())
    		selectall = false;
    	if(chckbxTurretSupport.isEnabled() && !chckbxTurretSupport.isSelected())
    		selectall = false;
		
		chckbxSelectAll.setSelected(selectall);
    }
    
    public class JobListSelectionListener implements ListSelectionListener {
	    //Listener method for list selection changes.
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {
	        	if(jobFormReady){
	        		jobFormReady = false;
	        	
	            if (listJobs.getSelectedIndex() == -1) {
	            //No selection: disable delete, up, and down buttons.
	            	btnJobDelete.setEnabled(false);
	                btnJobUp.setEnabled(false);
	                btnJobDown.setEnabled(false);
	                listJobs.clearSelection();
	                btnAddAll.setEnabled(false);
	                jobFormReady = false;
	                job = null;
	                //txtMachName.setText("");
	                ResetJobForm();
	                DisableJobForm();
	                job = null;
	                btnAddAll.setEnabled(false);
	 
	            } else if (listJobs.getSelectedIndices().length > 1) {
	            //Multiple selection: disable up and down buttons.
	            	btnJobDelete.setEnabled(true);
	            	btnJobUp.setEnabled(false);
	            	btnJobDown.setEnabled(false);
	 
	            } else {
	            //Single selection: permit all operations.
	            	btnJobDelete.setEnabled(true);
	            	
	            	btnAddAll.setEnabled(true);
	                
	                if(listJobs.getSelectedIndex() == 0)
	                	btnJobUp.setEnabled(false);
	                else
	                	btnJobUp.setEnabled(true);
	                
	                if(listJobs.getSelectedIndex() == jobModel.getSize()-1)
	                	btnJobDown.setEnabled(false);
	                else
	                	btnJobDown.setEnabled(true);
	                
	                job = (Job) listJobs.getSelectedValue();
	                
	                EnableJobForm();
	                jobFormReady = true;
	                UpdateJobForm();
	            }
	            jobIndex = listJobs.getSelectedIndex();
	            jobFormReady = true;
	        	}
	        }
	    }
    }
    
    public class JobAvailSelectionListener implements ListSelectionListener {
	    //Listener method for list selection changes.
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {
	        	
	            if (listJobs.getSelectedIndex() == -1) {
		            btnAddJob.setEnabled(false);
	 
	            } else if (listJobs.getSelectedIndices().length > 1) {
	          
	 
	            } else {
	            	btnAddJob.setEnabled(true);
	            }
	            
	        }
	    }
    }
    
    public class ScheduleSelectionListener implements ListSelectionListener {
	    //Listener method for list selection changes.
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {
	        	//formReady = false;
	        	
	            if (listSchedule.getSelectedIndex() == -1) {
	            //No selection: disable delete, up, and down buttons.
	            	btnClearSchedule.setEnabled(false);
	                btnUpSchedule.setEnabled(false);
	                btnDownSchedule.setEnabled(false);
	                btnRemoveJob.setEnabled(false);
	                btnViewSchedule.setEnabled(false);
	 
	            } else if (listSchedule.getSelectedIndices().length > 1) {
	            //Multiple selection: disable up and down buttons.
	 
	            } else {
	            	if(listSchedule.getSelectedIndex() == 0)
	            		btnUpSchedule.setEnabled(false);
	                else
	                	btnUpSchedule.setEnabled(true);
	                
	                if(listSchedule.getSelectedIndex() == scheduleModel.getSize()-1)
	                	btnDownSchedule.setEnabled(false);
	                else
	                	btnDownSchedule.setEnabled(true);
	            //Single selection: permit all operations.
	            	btnClearSchedule.setEnabled(true);
	                btnRemoveJob.setEnabled(true);
	                btnViewSchedule.setEnabled(true);
	            }
	            
	        }
	    }
    }
    
    private void DisableForm(){
    	lblMachName.setEnabled(false);
    	txtMachName.setEnabled(false);
        btnMachReset.setEnabled(false);
        rdbtnCustom.setEnabled(false);
        rdbtnER610.setEnabled(false);
        rdbtnSR800.setEnabled(false);
        rdbtnSR9DS.setEnabled(false);
        rdbtnSR9DT.setEnabled(false);
        
        chckbxSelectAll.setEnabled(false);
        
        chckbxAlignmentGuide.setEnabled(false);
		chckbxAutoCutoff.setEnabled(false);
		chckbxAutostripping.setEnabled(false);
		chckbxAutoTapeCore.setEnabled(false);
		chckbxAutoTapeTail.setEnabled(false);
		chckbxExtraRewind.setEnabled(false);
		chckbxFlag.setEnabled(false);
		chckbxRollConditioning.setEnabled(false);
		chckbxSpliceTable.setEnabled(false);
		chckbxTurretSupport.setEnabled(false);
		
		lblKnifeControl.setEnabled(false);
		cmbKnives.setEnabled(false);
		lblCorePositioning.setEnabled(false);
		cmbCorepos.setEnabled(false);
		lblUnloader.setEnabled(false);
		cmbUnloader.setEnabled(false);
		lblUnwindDrive.setEnabled(false);
		cmbUnwindDrive.setEnabled(false);
		lblRewindControlLoop.setEnabled(false);
		cmbRewindCtrl.setEnabled(false);
		
		lblSpeed.setEnabled(false);
		cmbSpeed.setEnabled(false);
		
		/*chckbxAlignmentGuide.setVisible(true);
		chckbxAutoCutoff.setVisible(true);
		chckbxAutostripping.setVisible(true);
		chckbxAutoTapeCore.setVisible(true);
		chckbxAutoTapeTail.setVisible(true);
		chckbxExtraRewind.setVisible(true);
		chckbxFlag.setVisible(true);
		chckbxRollConditioning.setVisible(true);
		chckbxSpliceTable.setVisible(true);
		chckbxTurretSupport.setVisible(true);*/
		
		btnCustomMachine.setEnabled(false);
		btnOverrideDefaultAcceleration.setEnabled(false);
		
		rdbtnER610.setSelected(true);
		
		btnNewMachine.requestFocusInWindow();
    }
    
   /* private void EnableForm(){
    	lblMachName.setEnabled(true);
    	txtMachName.setEnabled(true);
        btnMachReset.setEnabled(true);
        rdbtnCustom.setEnabled(true);
        rdbtnER610.setEnabled(true);
        rdbtnSR800.setEnabled(true);
        rdbtnSR9DS.setEnabled(true);
        rdbtnSR9DT.setEnabled(true);
        
        chckbxAlignmentGuide.setEnabled(false);
		chckbxAutoCutoff.setEnabled(false);
		chckbxAutostripping.setEnabled(false);
		chckbxAutoTapeCore.setEnabled(false);
		chckbxAutoTapeTail.setEnabled(false);
		chckbxExtraRewind.setEnabled(false);
		chckbxFlag.setEnabled(false);
		chckbxRollConditioning.setEnabled(false);
		chckbxSpliceTable.setEnabled(false);
		chckbxTurretSupport.setEnabled(false);
		
		lblKnifeControl.setEnabled(false);
		cmbKnives.setEnabled(false);
		lblCorePositioning.setEnabled(true);
		cmbCorepos.setEnabled(true);
		lblUnloader.setEnabled(false);
		cmbUnloader.setEnabled(false);
		lblUnwindDrive.setEnabled(false);
		cmbUnwindDrive.setEnabled(false);
		lblRewindControlLoop.setEnabled(false);
		cmbRewindCtrl.setEnabled(false);
		
		lblSpeed.setEnabled(true);
		cmbSpeed.setEnabled(true);
		
		chckbxAlignmentGuide.setVisible(true);
		chckbxAutoCutoff.setVisible(true);
		chckbxAutostripping.setVisible(true);
		chckbxAutoTapeCore.setVisible(true);
		chckbxAutoTapeTail.setVisible(true);
		chckbxExtraRewind.setVisible(true);
		chckbxFlag.setVisible(true);
		chckbxRollConditioning.setVisible(true);
		chckbxSpliceTable.setVisible(true);
		chckbxTurretSupport.setVisible(true);
		
		btnCustomMachine.setVisible(false);
		
		rdbtnER610.setSelected(true);
    }*/
    
    public void UpdateAnalysis(){
    	//if(tabbedPane.getSelectedIndex() != 3 && tabbedPane.getSelectedIndex() != 4)
    	//	return;
    	//else{
    	try{
    		ResetStatusLabel();
	    	int[] indices = listCompare.getSelectedIndices();
			if(indices.length > 0 && indices.length <= listCompare.getModel().getSize()){
				btnNone.setEnabled(true);
				
				if(indices.length < listModel.getSize())
					btnAll.setEnabled(true);
				else
					btnAll.setEnabled(false);
				
				Machine[] machines = new Machine[indices.length];
				
				for(int i=0; i<indices.length; ++i){
					machines[i] = (Machine) listModel.elementAt(indices[i]);
				}
				
				environment.update(window);
				
				if(environment.isValid()){
				
					// Get analysis
					comp = new Comparator(machines, environment, MODEL_VERSION);
					results = comp.getResults();
					// TODO: make sure machines in order on chart/results
				/*	chart = results.getTimeGraph();
					chart.setTitle("");*/
					
					/*// Display results graph
					pnlPreview.remove(pnlGraph);
					pnlGraph = new ChartPanel(chart);
					pnlGraph.setBounds(2, 2, 312, 199);
					pnlPreview.add(pnlGraph);*/
					
					breakdown = results.getBreakdownCharts();
					UpdateGraph();
					
					// TODO base on cmbGraphType
					
					// update form components
					EnableProdForm();
					
					// update machine selection combobox
					String[] machineNames = new String[indices.length];
					for(int i=0; i<indices.length; ++i){
						machineNames[i] = ((Machine) listModel.elementAt(indices[i])).model.toString() + ": " + ((Machine) listModel.elementAt(indices[i])).name;
					}
					DefaultComboBoxModel selectedMachines = new DefaultComboBoxModel(machineNames);
					cmbMachines.setModel(selectedMachines);
					if(oldMachineIndex < indices.length)
						cmbMachines.setSelectedIndex(oldMachineIndex); // triggers update of numerical results display
					else
						cmbMachines.setSelectedIndex(0);
					
				}else{
					ShowMessage("Job schedule not valid. Please check valid jobs have been selected.");
					DisableProdForm();
				}
			}
			else{
				ShowMessage("No machines selected.");
				DisableProdForm();
				btnNone.setEnabled(false);
				if(listModel.size() > 0)
					btnAll.setEnabled(true);
			}
    	}catch(NullPointerException e){
    		// Form isn't initialized
    		// TODO: find a nicer way around this...!
    		//e.printStackTrace();
    	}
    	//}
    }
    
    private void UpdateGraph(){
    	// TODO
    	
    	if(listCompare.getSelectedIndices().length > 0 && environment != null && environment.isValid()){
    		
    		JFreeChart preview = null;
    		
	    	if(cmbGraphType.getSelectedIndex() == 0){
	    		// Schedule time
	    		chart = results.getTimeGraph();
	    		preview = results.getTimeGraphPrev();
	    	}else if(cmbGraphType.getSelectedIndex() == 1){
	    		// Length/hour
	    		chart = results.getRateGraph();
	    		preview = results.getRateGraphPrev();
	    	}else if(cmbGraphType.getSelectedIndex() == 2){
	    		// Weight/hour
	    		chart = results.getRateWGraph();
	    		preview = results.getRateWGraphPrev();
	    	}else if(cmbGraphType.getSelectedIndex() == 3){
	    		// Length/year
	    		chart = results.getProdGraph();
	    		preview = results.getProdGraphPrev();
	    	}else if(cmbGraphType.getSelectedIndex() == 4){
	    		// Weight/year
	    		chart = results.getProdWGraph();
	    		preview = results.getProdWGraphPrev();
	    	}else if(cmbGraphType.getSelectedIndex() == 5){
	    		// Efficiency
	    		chart = results.getEffGraph();
	    		preview = results.getEffGraphPrev();
	    	}
	    	
    		// Display results graph
			try{
				pnlPreview.remove(pnlGraph);
			}catch(Exception e){
				// TODO !!
			}
			pnlGraph = new ChartPanel(preview);
			pnlGraph.setBounds(2, 2, 312, 199);
			pnlPreview.add(pnlGraph);
			
			pnlGraph.repaint();
	    	
    	}

    }
    
    private void DisableProdForm(){
    	results = null;
    	
	    pnlGraph.setVisible(false);
		picLabel.setVisible(true);
		btnSaveToFile.setEnabled(false);
		btnShowGraph.setEnabled(false);
		btnShowTimings.setEnabled(false);
		lblPreview.setEnabled(false);
		lblType.setEnabled(false);
		cmbGraphType.setEnabled(false);
		lblMachine.setEnabled(false);
		lblPer.setEnabled(false);
		lblOutputWeight.setEnabled(false);
		lblOutputLength.setEnabled(false);
		lblAverageMmin.setEnabled(false);
		lblEfficiency.setEnabled(false);
		lblscheduletime.setEnabled(false);
		lblscheduletimelbl.setEnabled(false);
		cmbMachines.setEnabled(false);
		cmbTimeRef.setEnabled(false);
		lblNumericsEff.setEnabled(false);
		lblNumericsLength.setEnabled(false);
		lblNumericsWeight.setEnabled(false);
		lblNumericsRate.setEnabled(false);
		btnDowntime.setEnabled(false);
		lblbreakdown1.setEnabled(false);
		lblbreakdown2.setEnabled(false);
		cmbMachines.setModel(new DefaultComboBoxModel());
		lblNumericsEff.setText("0.0 %");
		lblscheduletime.setText("0.0 hr");
		lblNumericsLength.setText((metric ? "0.0 m" : "0.0 ft"));
		lblNumericsWeight.setText((metric ? "0.0 kg" : "0.0 tons"));
		lblNumericsRate.setText((metric ? "0.0 m/hr" : "0.0 ft/hr"));
    }
    
    private void EnableProdForm(){
    	picLabel.setVisible(false);
		pnlGraph.setVisible(true);
		pnlGraph.repaint();
		
		btnSaveToFile.setEnabled(true);
		btnShowGraph.setEnabled(true);
		btnShowTimings.setEnabled(true);
		lblPreview.setEnabled(true);
		lblType.setEnabled(true);
		cmbGraphType.setEnabled(true);
		lblMachine.setEnabled(true);
		lblPer.setEnabled(true);
		lblOutputWeight.setEnabled(true);
		lblOutputLength.setEnabled(true);
		lblAverageMmin.setEnabled(true);
		lblEfficiency.setEnabled(true);
		lblscheduletime.setEnabled(true);
		lblscheduletimelbl.setEnabled(true);
		cmbMachines.setEnabled(true);
		cmbTimeRef.setEnabled(true);
		btnDowntime.setEnabled(true);
		lblbreakdown1.setEnabled(true);
		lblbreakdown2.setEnabled(true);
		lblNumericsEff.setEnabled(true);
		lblNumericsLength.setEnabled(true);
		lblNumericsWeight.setEnabled(true);
		lblNumericsRate.setEnabled(true);
    }
    
    private void UpdateNumericalAnalysis(){
    	
    	double numericsLength = 0.;
		double numericsWeight = 0.;
		double numericsRate = 0.;
		double numericsEff = 0.;
		double numericsTime = 0.;
		
    	if(! cmbMachines.isEnabled()){
			lblNumericsLength.setText(formatDecimal(numericsLength) + (metric ? " m" : " ft"));
			lblNumericsWeight.setText(formatDecimal(numericsWeight) + (metric ? " kg" : " tons"));
			lblNumericsRate.setText(formatDecimal(numericsRate) + (metric ? " m/hr" : " ft/hr"));
			lblNumericsEff.setText(formatDecimal(numericsEff) + " %");
			lblscheduletime.setText(formatDecimal(numericsTime) + " hr");
			oldMachineIndex = 0;
		}
		
    	else if((results != null) && (! results.isError)){
    		// Get machine selection
    		int machineInd = cmbMachines.getSelectedIndex();
    		oldMachineIndex = machineInd;
    		
    		// Get results for that selection
    		// TODO: check result do come in order!!
    		Result res = results.get(machineInd);
    		
    		// Switch over the selected time domain to update labels
    		switch(cmbTimeRef.getSelectedIndex()){
	    		case 0: // per schedule
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.SCHEDULE);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.SCHEDULE);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.SCHEDULE);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.SCHEDULE);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;
	    		case 1: // per year
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.YEAR);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.YEAR);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.YEAR);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.YEAR);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;
	    		case 2: // per hour
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.HOUR);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.HOUR);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.HOUR);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.HOUR);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;
	    		case 3: // per shift
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.SHIFT);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.SHIFT);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.SHIFT);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.SHIFT);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;
	    		case 4: // per day
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.DAY);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.DAY);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.DAY);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.DAY);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;
	    		/*case 5: // per week
	    			numericsLength = res.getResult(ResultType.LENGTH, ResultTime.WEEK);
    				numericsWeight = res.getResult(ResultType.WEIGHT, ResultTime.WEEK);
    				numericsRate = res.getResult(ResultType.RATE, ResultTime.WEEK);
    				numericsEff = res.getResult(ResultType.EFF, ResultTime.WEEK);
    				numericsTime = res.getResult(ResultType.TIME, ResultTime.SCHEDULE);
    				break;*/
	    		default: // otherwise
	    			numericsLength = 0;
    				numericsWeight = 0;
    				numericsRate = 0;
    				numericsEff = 0;
    				numericsTime = 0;
    				break;
    		}
    		
    		if(!metric){
    			numericsLength = Core.MToFt(numericsLength);
    			numericsWeight = Core.KgToTon(numericsWeight);
    			numericsRate = Core.MToFt(numericsRate);
    		}
    		
    		lblNumericsLength.setText(formatDecimal( (numericsLength >= 10000 ? numericsLength/(metric ? 1000 : Core.MiToFt(1)) : numericsLength )) + (numericsLength >= 10000 ? (metric ? " km" : " mi") : (metric ? " m" : " ft")));
			lblNumericsWeight.setText(formatDecimal( (numericsWeight >= 10000 ? numericsWeight/(metric ? 1000 : 1) : numericsWeight )) + (numericsWeight >= 10000 ? (metric ? " tonnes" : " tons") : (metric ? " kg" : " tons")));
			lblNumericsRate.setText(formatDecimal(numericsRate) + (metric ? " m/hr" : " ft/hr"));
			lblNumericsEff.setText(formatDecimal(numericsEff) + " %");
			lblscheduletime.setText(formatDecimal(numericsTime) + " hr");
		}
    }
 
    	
    	private void rdbtnClick(String name){
    		
    		if(name.equals("ER610") /*&& (listModel.getSize()==1 || !(model == Machine.Model.ER610))*/){
				ResetStatusLabel();
				
				chckbxSelectAll.setEnabled(true);
				chckbxAlignmentGuide.setEnabled(Consts.ER_ALIGN_OPTION);
				chckbxAutoCutoff.setEnabled(Consts.ER_CUTOFF_OPTION);
				chckbxAutostripping.setEnabled(Consts.ER_AUTOSTRIP_OPTION);
				chckbxAutoTapeCore.setEnabled(Consts.ER_TAPECORE_OPTION);
				chckbxAutoTapeTail.setEnabled(Consts.ER_TAPETAIL_OPTION);
				chckbxExtraRewind.setEnabled(Consts.ER_850MM_OPTION);
				chckbxFlag.setEnabled(Consts.ER_FLAG_OPTION);
				chckbxRollConditioning.setEnabled(Consts.ER_ROLL_OPTION);
				chckbxSpliceTable.setEnabled(Consts.ER_SPLICE_OPTION);
				chckbxTurretSupport.setEnabled(Consts.ER_TURRET_OPTION);
				
				lblKnifeControl.setEnabled(false);
				cmbKnives.setEnabled(false);
				//lblCorePositioning.setEnabled(false);
				//cmbCorepos.setEnabled(false);
				lblUnloader.setEnabled(false);
				cmbUnloader.setEnabled(false);
				lblUnwindDrive.setEnabled(false);
				cmbUnwindDrive.setEnabled(false);
				lblRewindControlLoop.setEnabled(false);
				cmbRewindCtrl.setEnabled(false);
				
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblSpeed.setEnabled(true);
				cmbSpeed.setEnabled(true);
				
				/*chckbxAlignmentGuide.setVisible(true);
				chckbxAutoCutoff.setVisible(true);
				chckbxAutostripping.setVisible(true);
				chckbxAutoTapeCore.setVisible(true);
				chckbxAutoTapeTail.setVisible(true);
				chckbxExtraRewind.setVisible(true);
				chckbxFlag.setVisible(true);
				chckbxRollConditioning.setVisible(true);
				chckbxSpliceTable.setVisible(true);
				chckbxTurretSupport.setVisible(true);*/
				
				//cmbCorepos.setModel(new DefaultComboBoxModel(FrmMain.CoreposER.values()));
				cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser"}));
				cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"450", "550"}));
				cmbKnives.setSelectedIndex(0);
				
				btnCustomMachine.setEnabled(false);
				btnOverrideDefaultAcceleration.setEnabled(true);
				
				model = Machine.Model.ER610;
				
				rdbtnER610.setSelected(true);
			}
    		
    		if(name.equals("SR9DS") /*&& !(model == Machine.Model.SR9DS)*/){
				ResetStatusLabel();
				
				chckbxSelectAll.setEnabled(true);
				chckbxAlignmentGuide.setEnabled(Consts.SRDS_ALIGN_OPTION);
				chckbxAutoCutoff.setEnabled(Consts.SRDS_CUTOFF_OPTION);
				chckbxAutostripping.setEnabled(Consts.SRDS_AUTOSTRIP_OPTION);
				chckbxAutoTapeCore.setEnabled(Consts.SRDS_TAPECORE_OPTION);
				chckbxAutoTapeTail.setEnabled(Consts.SRDS_TAPETAIL_OPTION);
				chckbxExtraRewind.setEnabled(Consts.SRDS_850MM_OPTION);
				chckbxFlag.setEnabled(Consts.SRDS_FLAG_OPTION);
				chckbxRollConditioning.setEnabled(Consts.SRDS_ROLL_OPTION);
				chckbxSpliceTable.setEnabled(Consts.SRDS_SPLICE_OPTION);
				chckbxTurretSupport.setEnabled(Consts.SRDS_TURRET_OPTION);
				
				lblKnifeControl.setEnabled(true);
				cmbKnives.setEnabled(true);
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblUnloader.setEnabled(true);
				cmbUnloader.setEnabled(true);
				lblUnwindDrive.setEnabled(true);
				cmbUnwindDrive.setEnabled(true);
				lblRewindControlLoop.setEnabled(true);
				cmbRewindCtrl.setEnabled(true);
				
				/*chckbxAlignmentGuide.setVisible(true);
				chckbxAutoCutoff.setVisible(true);
				chckbxAutostripping.setVisible(true);
				chckbxAutoTapeCore.setVisible(true);
				chckbxAutoTapeTail.setVisible(true);
				chckbxExtraRewind.setVisible(true);
				chckbxFlag.setVisible(true);
				chckbxRollConditioning.setVisible(true);
				chckbxSpliceTable.setVisible(true);
				chckbxTurretSupport.setVisible(true);*/
				
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblSpeed.setEnabled(true);
				cmbSpeed.setEnabled(true);
				
				cmbKnives.setSelectedIndex(0);
				
				//cmbCorepos.setModel(new DefaultComboBoxModel(FrmMain.CoreposSR.values()));
				cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser", "Servo"}));
				cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"1000"}));
				
				btnCustomMachine.setEnabled(false);
				btnOverrideDefaultAcceleration.setEnabled(true);
				
				model = Machine.Model.SR9DS;
				
				rdbtnSR9DS.setSelected(true);
			}
    		
    		if(name.equals("SR9DT")/* && !(model == Machine.Model.SR9DT)*/){
				ResetStatusLabel();
				
				chckbxSelectAll.setEnabled(true);
				chckbxAlignmentGuide.setEnabled(Consts.SRDT_ALIGN_OPTION);
				chckbxAutoCutoff.setEnabled(Consts.SRDT_CUTOFF_OPTION);
				chckbxAutostripping.setEnabled(Consts.SRDT_AUTOSTRIP_OPTION);
				chckbxAutoTapeCore.setEnabled(Consts.SRDT_TAPECORE_OPTION);
				chckbxAutoTapeTail.setEnabled(Consts.SRDT_TAPETAIL_OPTION);
				chckbxExtraRewind.setEnabled(Consts.SRDT_850MM_OPTION);
				chckbxFlag.setEnabled(Consts.SRDT_FLAG_OPTION);
				chckbxRollConditioning.setEnabled(Consts.SRDT_ROLL_OPTION);
				chckbxSpliceTable.setEnabled(Consts.SRDT_SPLICE_OPTION);
				chckbxTurretSupport.setEnabled(Consts.SRDT_TURRET_OPTION);
				chckbxTurretSupport.setSelected(true);
				
				lblKnifeControl.setEnabled(true);
				cmbKnives.setEnabled(true);
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblUnloader.setEnabled(true);
				cmbUnloader.setEnabled(true);
				lblUnwindDrive.setEnabled(true);
				cmbUnwindDrive.setEnabled(true);
				lblRewindControlLoop.setEnabled(true);
				cmbRewindCtrl.setEnabled(true);
				
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblSpeed.setEnabled(true);
				cmbSpeed.setEnabled(true);
				
				
				/*chckbxAlignmentGuide.setVisible(true);
				chckbxAutoCutoff.setVisible(true);
				chckbxAutostripping.setVisible(true);
				chckbxAutoTapeCore.setVisible(true);
				chckbxAutoTapeTail.setVisible(true);
				chckbxExtraRewind.setVisible(true);
				chckbxFlag.setVisible(true);
				chckbxRollConditioning.setVisible(true);
				chckbxSpliceTable.setVisible(true);
				chckbxTurretSupport.setVisible(true);*/
				
				//cmbCorepos.setModel(new DefaultComboBoxModel(FrmMain.CoreposSR.values()));
				cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser", "Servo"}));
				cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"1000"}));
				cmbKnives.setSelectedIndex(0);
				
				btnCustomMachine.setEnabled(false);
				btnOverrideDefaultAcceleration.setEnabled(true);
			
				model = Machine.Model.SR9DT;
				
				rdbtnSR9DT.setSelected(true);
			}
    		
    		if(name.equals("SR800")/* && !(model == Machine.Model.SR800)*/){
				ResetStatusLabel();
				
				chckbxSelectAll.setEnabled(true);
				chckbxAlignmentGuide.setEnabled(Consts.SR800_ALIGN_OPTION);
				chckbxAutoCutoff.setEnabled(Consts.SR800_CUTOFF_OPTION);
				chckbxAutostripping.setEnabled(Consts.SR800_AUTOSTRIP_OPTION);
				chckbxAutoTapeCore.setEnabled(Consts.SR800_TAPECORE_OPTION);
				chckbxAutoTapeTail.setEnabled(Consts.SR800_TAPETAIL_OPTION);
				chckbxExtraRewind.setEnabled(Consts.SR800_850MM_OPTION);
				chckbxFlag.setEnabled(Consts.SR800_FLAG_OPTION);
				chckbxRollConditioning.setEnabled(Consts.SR800_ROLL_OPTION);
				chckbxSpliceTable.setEnabled(Consts.SR800_SPLICE_OPTION);
				chckbxTurretSupport.setEnabled(Consts.SR800_TURRET_OPTION);
				
				lblKnifeControl.setEnabled(true);
				cmbKnives.setEnabled(true);
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblUnloader.setEnabled(true);
				cmbUnloader.setEnabled(true);
				lblUnwindDrive.setEnabled(true);
				cmbUnwindDrive.setEnabled(true);
				lblRewindControlLoop.setEnabled(true);
				cmbRewindCtrl.setEnabled(true);
				
				lblCorePositioning.setEnabled(true);
				cmbCorepos.setEnabled(true);
				lblSpeed.setEnabled(true);
				cmbSpeed.setEnabled(true);
				
				cmbKnives.setSelectedIndex(0);
				
				cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser"}));
				cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"700"}));
				
				btnCustomMachine.setEnabled(false);
				btnOverrideDefaultAcceleration.setEnabled(true);
				
				model = Machine.Model.SR800;
				
				rdbtnSR800.setSelected(true);
			}
    		
    		if(name.equals("Custom") /*&& !(model == Machine.Model.OTHER)*/){
				
    			chckbxSelectAll.setSelected(false);
    			chckbxSelectAll.setEnabled(false);
    			chckbxAlignmentGuide.setEnabled(false);
				chckbxAutoCutoff.setEnabled(false);
				chckbxAutostripping.setEnabled(false);
				chckbxAutoTapeCore.setEnabled(false);
				chckbxAutoTapeTail.setEnabled(false);
				chckbxExtraRewind.setEnabled(false);
				chckbxFlag.setEnabled(false);
				chckbxRollConditioning.setEnabled(false);
				chckbxSpliceTable.setEnabled(false);
				chckbxTurretSupport.setEnabled(false);
				
				lblKnifeControl.setEnabled(false);
				cmbKnives.setEnabled(false);
				//lblCorePositioning.setEnabled(false);
				//cmbCorepos.setEnabled(false);
				lblUnloader.setEnabled(false);
				cmbUnloader.setEnabled(false);
				lblUnwindDrive.setEnabled(false);
				cmbUnwindDrive.setEnabled(false);
				lblRewindControlLoop.setEnabled(false);
				cmbRewindCtrl.setEnabled(false);
				lblCorePositioning.setEnabled(false);
				cmbCorepos.setEnabled(false);
				lblSpeed.setEnabled(false);
				cmbSpeed.setEnabled(false);
				cmbKnives.setSelectedIndex(0);
				
				//cmbCorepos.setModel(new DefaultComboBoxModel(FrmMain.CoreposER.values()));
				//cmbCorepos.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Laser"}));
				//cmbSpeed.setModel(new DefaultComboBoxModel(new String[] {"450", "550"}));
				
				/*MachineBuilder newmach = new MachineBuilder(window listMachines, lblStatus, machNames);
				newmach.setVisible(true);*/
				btnCustomMachine.setEnabled(true);
				btnOverrideDefaultAcceleration.setEnabled(false);
				
				model = Machine.Model.CUSTOM;
				
				rdbtnCustom.setSelected(true);
			}
    		if(name.equals("Custom")){
    			machine.setCustom(true);
    			if(machine.getCustomMachine() == null)
    				machine.setCustomMachine(machine.new CustomMachine());
    		}else
    			machine.setCustom(false);
    		
    		UpdateSelectAllChckbx();
    		
    		if(name.equals("Custom"))
    			chckbxSelectAll.setSelected(false);
    }
    
    ActionListener SaveActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO base on cmbGraphType selection, or not needed with 'chart'?
			fc = new OpenSaveFileChooser();
			fc.setFileFilter(new PNGfilter());
			fc.type = 2;
			
			int returnVal = fc.showSaveDialog(pnlCompare);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
	            File file = fc.getSelectedFile();
	            
	            String path = file.getAbsolutePath();

	            String extension = ".png";

	            if(!path.endsWith(extension))
	            {
	              file = new File(path + extension);
	            }

	            
	            try {
	            	JFreeChart chartSave = (JFreeChart) chart.clone();
					ChartUtilities.saveChartAsPNG(file, chartSave, 800, 600);
					ShowMessageSuccess("File saved.");
				} catch (Exception e1) {
					ShowMessage("File error.");
				}
			}
		}
	};
	
	public File addExt(File f, String ext){
		String path = f.getAbsolutePath();
	
	    if(!path.endsWith(ext))
	    {
	      f = new File(path + ext);
	    }
	    
	    return f;
	}
	
	public File[] searchLCS(){
		File[] licences = new File[0];
		if(new File("licence.lcs").exists())
			licences = concat(licences, new File[]{new File("licence.lcs")});
		/*if(new File("license.lcs").exists())
			licences = concat(licences, new File[]{new File("license.lcs")});
		*/
		File currentDir = new File("");
		String currentPath = currentDir.getAbsolutePath();
		
		// search local dir
		File[] here = finder(currentPath);
		if(licences.length > 0){
			// delete duplicate of "licence.lcs" in this search
			for(int i=0;i<here.length; i++)
				if(here[i].getAbsolutePath().equals(licences[0].getAbsolutePath()))
					here[i] = null;
		}
		
		licences = concat(licences, here);
		
		String progfiles = System.getenv("ProgramFiles") + "\\Production Calculator\\";
		String progfiles2 = System.getenv("ProgramFiles(x86)") + "\\Production Calculator\\";
		String homedir = System.getenv("user.home");
		if(homedir == null)
			homedir = System.getenv("USERPROFILE");
		String desktop = homedir + "\\Desktop\\";
		String docs = homedir + "\\Documents\\";
		String docs2 = homedir + "\\Documents and Settings\\";
		String primary = homedir + "\\Production Calculator\\";
		// TODO: check if / or \ already there (eg. user.dir)

		licences = concat(licences, finder(primary));
		licences = concat(licences, finder(progfiles));
		licences = concat(licences, finder(progfiles2));
		licences = concat(licences, finder(homedir));
		licences = concat(licences, finder(desktop));
		licences = concat(licences, finder(docs));
		licences = concat(licences, finder(docs2));
		
		return licences;
	}
	
	private File[] concat(File[] a, File[] b){
		if(a == null){
			if(b == null)
				return new File[0];
			else
				return b;
		}
		if(b == null)
			return a;
		File[] result = new File[a.length+b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	
	 public File[] finder(String dirName){
		if(dirName == null || dirName.replaceAll("\\s+","").equals(""))
			return new File[0];
		File dir = new File(dirName);
	
		return dir.listFiles(new FilenameFilter() { 
		         public boolean accept(File dir, String filename)
		              { return filename.endsWith(".lcs"); }
		} );
    }
	
	public void DoLicenceCheck(){
		
		File[] licences = searchLCS();
		if(licences == null || licences.length < 1){
			JOptionPane.showMessageDialog(frmTitanRoiCalculator, "Could not find a licence file. \nPlease put a valid \".lcs\" file in the same folder as this tool and try again.", "No licence", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		for(int i=0; i<licences.length; ++i){
			Security checker = new Security(1, licences[i]);
			boolean result = checker.CheckLicence();
			if(result){
				long currtime = System.currentTimeMillis();
				long timestart = checker.getStartTimestamp();
				long timeend = checker.getEndTimestamp();
				if(currtime < timestart || currtime > timeend){
					JOptionPane.showMessageDialog(frmTitanRoiCalculator, "The licence \""+licences[i].getAbsolutePath()/*getName()*/+"\" has expired. Please contact the software provider for more information.", "Licence expired", JOptionPane.WARNING_MESSAGE);
				}
				else{
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
					Date time1 = new Date(timeend);
					String time1f = sdf.format(time1);
					time1f = "\".  Valid until " + time1f + ".";
					boolean unlim = false;
					if(timeend == Long.MAX_VALUE)
						unlim = true;
					ShowMessageStandard("Loaded licence file \""+licences[i].getAbsolutePath()+(unlim ? "\".  Unlimited licence." : time1f));
					return;
				}
			}
		}
		// no valid licence
		JOptionPane.showMessageDialog(frmTitanRoiCalculator, "No valid licence file found on this machine. Please contact the software provider for more information.", "No licence", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		
	}
    
	class PNGfilter extends FileFilter {
		@Override
		public String getDescription(){
			return "PNG images";
		}
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
		        return true;
		    }

		    String extension = getExtension(f);
		    if (extension != null) {
		        if (extension.equals("png")) {
		                return true;
		        } else {
		            return false;
		        }
		    }

		    return false;
		}

	}
	
	class XLSfilter extends FileFilter {
		@Override
		public String getDescription(){
			return "Excel XLS Spreadsheets";
		}
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
		        return true;
		    }

		    String extension = getExtension(f);
		    if (extension != null) {
		        if (extension.equals("xls")) {
		                return true;
		        } else {
		            return false;
		        }
		    }

		    return false;
		}

	}
	
	class OBJfilter extends FileFilter {
		int type;  // 1==machine, 2==job, 3==all
		
		public OBJfilter(int type){
			super();
			this.type = type;
		}
		
		public void setType(int type){
			this.type = type;
		}
		
		@Override
		public String getDescription(){
			String des = "";
			switch(type){
				case 1: des = "Machine setting files"; break;
				case 2:  des = "Job setting files"; break;
				case 3:  des = "Program setup files"; break;
				default:  des = "Java object files"; break;
			}
			return des;//"Java serialized object files";
		}
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
		        return true;
		    }

		    String extension = getExtension(f);
		    if (extension != null) {
		        if (extension.equals("ser")) {
		                return true;
		        } else {
		            return false;
		        }
		    }

		    return false;
		}

	}
	
	/*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    ChangeListener TabChangeListener = new ChangeListener() {
    	@Override
		public void stateChanged(ChangeEvent arg0) {
    		try{
    			ResetStatusLabel();
    		}catch(NullPointerException e){
    			// Form is still initialising
    		}
    		if(tabbedPane.getSelectedIndex() == 0){
    			mntmOpen.setEnabled(true);
				mntmSave.setEnabled(true);
				try{
					btnNewMachine.requestFocusInWindow();
				}catch(NullPointerException e){}
    		}
    		else if(tabbedPane.getSelectedIndex() == 1){
    			mntmOpen.setEnabled(true);
				mntmSave.setEnabled(true);
				UpdateUnwindWarning();
				UpdateTrim();
				try{
					btnNewJob.requestFocusInWindow();
				}catch(NullPointerException e){}
    		}
    		else if(tabbedPane.getSelectedIndex() == 2){
    			mntmOpen.setEnabled(false);
				mntmSave.setEnabled(false);
				listJobsAvail.requestFocusInWindow();
    		}
    		else if(tabbedPane.getSelectedIndex() == 3){
				if(listModel.getSize() > 0){
					lblSelect.setEnabled(true);
	    			btnAll.setEnabled(true);
				}else{
	    			lblSelect.setEnabled(false);
	    			btnAll.setEnabled(false);
	    			btnNone.setEnabled(false);
				}
				
				mntmOpen.setEnabled(false);
				mntmSave.setEnabled(true);
    			
				// refresh analyses
				UpdateAnalysis();
				UpdateROI();
				
				try{
					btnAll.requestFocusInWindow();
				}catch(NullPointerException e){}
			}
    		else if(tabbedPane.getSelectedIndex() == 4){
    			if(listModel.getSize() > 0){
					lblROIselect.setEnabled(true);
					if(listCompareRoi.getSelectedIndices().length != listModel.getSize())
						btnROIselectall.setEnabled(true);
					else
						btnROIselectall.setEnabled(false);
				}else{
					lblROIselect.setEnabled(false);
					btnROIselectall.setEnabled(false);
					btnROIselectnone.setEnabled(false);
				}
    			
    			mntmOpen.setEnabled(false);
				mntmSave.setEnabled(false);

				// refresh analyses
				UpdateAnalysis();
				UpdateRoiCmbs();
				UpdateROI();
				
				try{
					btnROIselectall.requestFocusInWindow();
				}catch(NullPointerException e){}
    		}
    		else{
    			mntmOpen.setEnabled(false);
				mntmSave.setEnabled(false);
    		}
		}
	};
	
	private boolean DrawBorder(WritableSheet sheet, SheetRangeImpl range, BorderLineStyle style){
		//int width = range.getBottomRight().getColumn() - range.getTopLeft().getColumn() + 1;
		//int height = range.getBottomRight().getRow() - range.getTopLeft().getRow() + 1;
		int startrow = range.getTopLeft().getRow();
		int startcol = range.getTopLeft().getColumn();
		int endrow = range.getBottomRight().getRow();
		int endcol = range.getBottomRight().getColumn();
		
		try{
			int i, j;
			j = startrow;
			for(i=startcol; i <= endcol; ++i){
				WritableCellFormat cell = new WritableCellFormat();
				cell.setBorder(Border.TOP, style);
				sheet.addCell(new Label(i, j, "", cell));
			}
			j = endrow;
			for(i=startcol; i <= endcol; ++i){
				WritableCellFormat cell = new WritableCellFormat();
				cell.setBorder(Border.BOTTOM, style);
				sheet.addCell(new Label(i, j, "", cell));
			}
			i = startcol;
			for(j=startrow; j <= endrow; ++j){
				// For the left hand column, we need to keep any text already in the cell (could do this for all borders if needed)
				WritableCell before = sheet.getWritableCell(i, j);
				// 'before' is never null
				if(before.getCellFormat() == null){
					WritableCellFormat cellformat = new WritableCellFormat();
					cellformat.setBorder(Border.LEFT, style);
					before = new Label(i,j,"",cellformat);
					sheet.addCell(before);
				}else{
					WritableCellFormat after = new WritableCellFormat(before.getCellFormat());
					after.setBorder(Border.LEFT, style);
					before.setCellFormat(after);
				}
			}
			i = endcol;
			for(j=startrow; j <= endrow; ++j){
				// For the right hand column, we need to keep any text already in the cell (could do this for all borders if needed)
				WritableCell before = sheet.getWritableCell(i, j);
				// 'before' is never null
				if(before.getCellFormat() == null){
					WritableCellFormat cellformat = new WritableCellFormat();
					cellformat.setBorder(Border.RIGHT, style);
					before = new Label(i,j,"",cellformat);
					sheet.addCell(before);
				}else{
					WritableCellFormat after = new WritableCellFormat(before.getCellFormat());
					after.setBorder(Border.RIGHT, style);
					before.setCellFormat(after);
				}
			}
			// Finally, border corners
			WritableCellFormat topleft = new WritableCellFormat();
			topleft.setBorder(Border.LEFT, style);
			topleft.setBorder(Border.TOP, style);
			sheet.addCell(new Label(startcol, startrow, "", topleft));
			WritableCellFormat topright = new WritableCellFormat();
			topright.setBorder(Border.RIGHT, style);
			topright.setBorder(Border.TOP, style);
			sheet.addCell(new Label(endcol, startrow, "", topright));
			WritableCellFormat bottomleft = new WritableCellFormat();
			bottomleft.setBorder(Border.LEFT, style);
			bottomleft.setBorder(Border.BOTTOM, style);
			sheet.addCell(new Label(startcol, endrow, "", bottomleft));
			WritableCellFormat bottomright = new WritableCellFormat();
			bottomright.setBorder(Border.RIGHT, style);
			bottomright.setBorder(Border.BOTTOM, style);
			sheet.addCell(new Label(endcol, endrow, "", bottomright));
		}catch(WriteException e){
			return false;
		}
		return true;
	}
	
	private boolean WriteSpreadsheet(File file) throws Exception {
		//boolean success = true;
		// TODO: check first if image files present, and what data available. Display message on sheet if no data, or error message in form.
		//try {
			// New workbook
			WritableWorkbook workbook = null;
			
			boolean errors = false;
		
			workbook = Workbook.createWorkbook(file);
		
			// New sheets
			WritableSheet sheetMach = workbook.createSheet("Machines", 0); 
			WritableSheet sheetJobs = workbook.createSheet("Jobs", 1); 
			WritableSheet sheetRes = workbook.createSheet("Productivity Results", 2); 
			WritableSheet sheetRoi = workbook.createSheet("ROI Results", 3);
			
			// All static images
			File imgLogo = new File("sheet_imgs/logo.png");
			File imgER610 = new File("sheet_imgs/er610.png");
			File imgSR9DS = new File("sheet_imgs/sr9ds.png");
			File imgSR9DT = new File("sheet_imgs/sr9dt.png");
			File imgSR800 = new File("sheet_imgs/sr800.png");
			File imgCustom = new File("sheet_imgs/custom.png");
			
			// TODO
			// All temp image holders
			File fs = null;
			File fresa = new File(Consts.SETTINGS_DIR+"/tempresa.png");
			File fresb = new File(Consts.SETTINGS_DIR+"/tempresb.png");
			File fresc = new File(Consts.SETTINGS_DIR+"/tempresc.png");
			File fresd = new File(Consts.SETTINGS_DIR+"/tempresd.png");
			File froia = new File(Consts.SETTINGS_DIR+"/temproia.png");
			File froib = new File(Consts.SETTINGS_DIR+"/temproib.png");
			File froic = new File(Consts.SETTINGS_DIR+"/temproic.png");
			File froid = new File(Consts.SETTINGS_DIR+"/temproid.png");
			String tmpSched = Consts.SETTINGS_DIR+"/imgSched.png";
			File[] brkdns = new File[(results != null ? results.getSize() : 0)];
			for (int i=0; i<brkdns.length; ++i)
				brkdns[i] = new File(Consts.SETTINGS_DIR+"/tmpBrkdn"+i+".png");
			
			// All fonts
			WritableFont fontStandard = new WritableFont(WritableFont.ARIAL, 10); 
			WritableCellFormat fontStandardFormat = new WritableCellFormat (fontStandard); 
			WritableFont fontError = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED); 
			WritableCellFormat fontErrorFormat = new WritableCellFormat (fontError); 
			WritableFont fontTitle = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD); 
			WritableCellFormat fontTitleFormat = new WritableCellFormat (fontTitle); 
			WritableFont fontPageTitle = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREEN); 
			WritableCellFormat fontPageTitleFormat = new WritableCellFormat (fontPageTitle); 
			WritableFont fontCategory = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
			WritableCellFormat fontCategoryFormat = new WritableCellFormat (fontCategory); 
			WritableFont fontData = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE); 
			WritableCellFormat fontDataFormat = new WritableCellFormat (fontData);
			fontDataFormat.setAlignment(Alignment.LEFT); 
			WritableFont fontDataBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE); 
			WritableCellFormat fontDataBoldFormat = new WritableCellFormat (fontDataBold); 
			WritableFont fontNumber = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE); 
			WritableCellFormat fontNumberFormat = new WritableCellFormat (fontNumber); 
			fontNumberFormat.setAlignment(Alignment.LEFT);
			//TODO: dark blue 2?
			WritableFont fontDataCategory = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GRAY_50); 
			WritableCellFormat fontDataCategoryFormat = new WritableCellFormat (fontDataCategory); 
			fontDataCategoryFormat.setAlignment(Alignment.RIGHT);
			WritableFont fontFootnote = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GRAY_50); 
			WritableCellFormat fontFootnoteFormat = new WritableCellFormat (fontFootnote); 
			WritableFont fontJobHead = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE); 
			WritableCellFormat fontJobHeadFormat = new WritableCellFormat (fontJobHead); 
			
			// Precompute dimensions
			Machine[] machines = new Machine[listModel.getSize()];
			for(int i=0; i<listModel.getSize(); ++i)
				machines[i] = (Machine) listModel.get(i);
			Job[] jobs = new Job[jobModel.getSize()];
			for(int i=0; i<jobModel.getSize(); ++i)
				jobs[i] = (Job) jobModel.get(i);
			
			int PAGE1_WIDTH = 3 + 3*machines.length;
			int PAGE1_HEIGHT = 38;
			int PAGE2_WIDTH1 = 3 + 3*jobs.length;
			int PAGE2_HEIGHT1 = 24;
			int PAGE2_WIDTH2 = 6;
			int PAGE2_HEIGHT2 = 10;
			int PAGE2_WIDTH3 = 12;
			int PAGE2_HEIGHT3 = 22 + Math.max(0, environment.getSchedule().getSize() - 17);
			if(environment.getSchedule() == null || environment.getSchedule().getSize() < 1){
				PAGE2_WIDTH3 = 4;
				PAGE2_HEIGHT3 = 5;
			}
			int PAGE3_WIDTH = Math.max(19, 3 + 4*((results==null ? 0 : results.getSize())));
			int PAGE3_HEIGHT = 45;
			int PAGE4_WIDTH = Math.max(19, 3 + 3*((RoiResults==null ? 0 : RoiResults.getSize())));
			int PAGE4_HEIGHT = 68;
			
			// Add headers
			if(imgLogo.exists()){
				sheetMach.addImage(new WritableImage(0, 0, 3, 5, imgLogo));
				sheetJobs.addImage(new WritableImage(0, 0, 3, 5, imgLogo));
				sheetRes.addImage(new WritableImage(0, 0, 3, 5, imgLogo));
				sheetRoi.addImage(new WritableImage(0, 0, 3, 5, imgLogo));
			}
			
			// NUMBERING IS FROM 0!!
			sheetMach.addCell(new Label(4, 1, "Titan Production Calculator", fontTitleFormat));
			sheetJobs.addCell(new Label(4, 1, "Titan Production Calculator", fontTitleFormat));
			sheetRes.addCell(new Label(4, 1, "Titan Production Calculator", fontTitleFormat));
			sheetRoi.addCell(new Label(4, 1, "Titan Production Calculator", fontTitleFormat));
			
			sheetMach.addCell(new Label(4, 3, "Spreadsheet generated on " + now(), fontStandardFormat));
			sheetJobs.addCell(new Label(4, 3, "Spreadsheet generated on " + now(), fontStandardFormat));
			sheetRes.addCell(new Label(4, 3, "Spreadsheet generated on " + now(), fontStandardFormat));
			sheetRoi.addCell(new Label(4, 3, "Spreadsheet generated on " + now(), fontStandardFormat));
			
			// Set borders
			SheetRangeImpl r1 = new SheetRangeImpl(sheetMach, 0, 6, PAGE1_WIDTH - 1, PAGE1_HEIGHT + 6 - 1);
			SheetRangeImpl r2a = new SheetRangeImpl(sheetJobs, 0, 6, PAGE2_WIDTH1 - 1, PAGE2_HEIGHT1 + 6 - 1);
			SheetRangeImpl r2b = new SheetRangeImpl(sheetJobs, 0, 32+PAGE2_HEIGHT3, PAGE2_WIDTH2 - 1, PAGE2_HEIGHT2 + PAGE2_HEIGHT3 + 31 - 1);
			SheetRangeImpl r2c = new SheetRangeImpl(sheetJobs, 0, 31, PAGE2_WIDTH3 - 1, PAGE2_HEIGHT3 + 31 - 1);
			SheetRangeImpl r3 = new SheetRangeImpl(sheetRes, 0, 6, PAGE3_WIDTH - 1, PAGE3_HEIGHT + 6 - 1);
			SheetRangeImpl r4 = new SheetRangeImpl(sheetRes, 0, 6, PAGE4_WIDTH - 1, PAGE4_HEIGHT + 6 - 1);
			// TODO: this probably means border cells can't have text in...

			// No data warnings, else build sheet
			if(machines.length < 1){
				sheetMach.addCell(new Label(1, 7, "No machines were configured", fontErrorFormat));
			}else{

				sheetMach.addCell(new Label(1, 7, "Machines", fontPageTitleFormat));
				sheetMach.addCell(new Label(1, 9, "Model", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 13, "Description", fontCategoryFormat));

				sheetMach.addCell(new Label(1, 15, "Speed", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 16, "Knife Control", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 17, "Core Positioning", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 18, "Unloader", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 19, "Unwind Drive", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 20, "Rewind Ctrl Loop", fontCategoryFormat));
				
				sheetMach.addCell(new Label(1, 22, "Splice Table", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 23, "Alignment Guide", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 24, "Roll Conditioning", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 25, "Turret Support", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 26, "Autostripping", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 27, "Flag Detection", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 28, "Auto Cut-off", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 29, "Auto Tape Core", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 30, "Auto Tape Tail", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 31, "850mm Rewind", fontCategoryFormat));
				
				sheetMach.addCell(new Label(1, 33, "Duplex Rewind", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 34, "Turret Rewind", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 35, "Braked Unwind", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 36, "Splice Table", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 37, "Auto Cross Cut", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 38, "Auto Tape", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 39, "Core Positioning", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 40, "Reel Stripping", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 41, "Unload Boom", fontCategoryFormat));
				sheetMach.addCell(new Label(1, 42, "Rewind Type", fontCategoryFormat));
				
				sheetMach.addCell(new Label(0, 15, "Variants:", fontDataCategoryFormat));
				sheetMach.addCell(new Label(0, 22, "Titan:", fontDataCategoryFormat));
				sheetMach.addCell(new Label(0, 33, "Custom:", fontDataCategoryFormat));
				
				for(int i=0; i < machines.length; ++i){
					Machine m = machines[i];
					
					int x = 3 + 3*i;
					
					// add model logo
					switch(m.model){
						case ER610: sheetMach.addImage(new WritableImage(x, 9, 2, 3, imgER610));
						break;
						case SR9DS: sheetMach.addImage(new WritableImage(x, 9, 2, 3, imgSR9DS));
						break;
						case SR9DT: sheetMach.addImage(new WritableImage(x, 9, 2, 3, imgSR9DT));
						break;
						case SR800: sheetMach.addImage(new WritableImage(x, 9, 2, 3, imgSR800));
						break;
						case CUSTOM: sheetMach.addImage(new WritableImage(x, 9, 2, 3, imgCustom));
						break;
					}
					
					// description
					sheetMach.addCell(new Label(x, 13, m.name, fontDataFormat));
					
					if(!m.isCustom()){
					
						// variants
						sheetMach.addCell(new Number(x, 15, (int) m.speed.orig_speed, fontNumberFormat));
						sheetMach.addCell(new Label(x, 16, m.knives.toString().toUpperCase(), fontDataFormat));
						sheetMach.addCell(new Label(x, 17, m.corepos.toString(), fontDataFormat));
						sheetMach.addCell(new Label(x, 18, m.unloader.toString(), fontDataFormat));
						sheetMach.addCell(new Label(x, 19, m.unwind.toString(), fontDataFormat));
						sheetMach.addCell(new Label(x, 20, m.rewind.toString(), fontDataFormat));
						
						// options
						if(m.splice_table)
							sheetMach.addCell(new Label(x, 22, "X", fontDataBoldFormat));
						if(m.alignment)
							sheetMach.addCell(new Label(x, 23, "X", fontDataBoldFormat));
						if(m.roll_cond)
							sheetMach.addCell(new Label(x, 24, "X", fontDataBoldFormat));
						if(m.turret)
							sheetMach.addCell(new Label(x, 25, "X", fontDataBoldFormat));
						if(m.autostrip)
							sheetMach.addCell(new Label(x, 26, "X", fontDataBoldFormat));
						if(m.flags)
							sheetMach.addCell(new Label(x, 27, "X", fontDataBoldFormat));
						if(m.cutoff)
							sheetMach.addCell(new Label(x, 28, "X", fontDataBoldFormat));
						if(m.tapecore)
							sheetMach.addCell(new Label(x, 29, "X", fontDataBoldFormat));
						if(m.tapetail)
							sheetMach.addCell(new Label(x, 30, "X", fontDataBoldFormat));
						if(m.extrarewind)
							sheetMach.addCell(new Label(x, 31, "X", fontDataBoldFormat));
						
					}else{
						// Custom machine
						//  duplexrewind turret  brakedunwind  splicetab autocut  autotape  corepos  autostrip unloadboom
						sheetMach.addCell(new Number(x, 15, (int) (60*m.getSpeed(0.)), fontNumberFormat));
						sheetMach.addCell(new Label(x, 16, "n/a", fontDataFormat));
						sheetMach.addCell(new Label(x, 17, "See below", fontDataFormat));
						sheetMach.addCell(new Label(x, 18, "n/a", fontDataFormat));
						sheetMach.addCell(new Label(x, 19, "n/a", fontDataFormat));
						sheetMach.addCell(new Label(x, 20, "n/a", fontDataFormat));
						
						if(m.getCustomMachine().options[0])
							sheetMach.addCell(new Label(x, 33, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[1])
							sheetMach.addCell(new Label(x, 34, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[2])
							sheetMach.addCell(new Label(x, 35, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[3])
							sheetMach.addCell(new Label(x, 36, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[4])
							sheetMach.addCell(new Label(x, 37, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[5])
							sheetMach.addCell(new Label(x, 38, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[6])
							sheetMach.addCell(new Label(x, 39, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[7])
							sheetMach.addCell(new Label(x, 40, "X", fontDataBoldFormat));
						if(m.getCustomMachine().options[8])
							sheetMach.addCell(new Label(x, 41, "X", fontDataBoldFormat));
						
						sheetMach.addCell(new Label(x, 42, m.getCustomMachine().rewind_type.toString(), fontDataFormat));
						
					}
					
					sheetMach.addCell(new Label(0, PAGE1_HEIGHT + 6 + 1, "X = option present", fontFootnoteFormat));
					sheetMach.addCell(new Label(PAGE1_WIDTH - 4, PAGE1_HEIGHT + 6 + 1, "Calculations are approximations only", fontFootnoteFormat));
					
				}
				
				DrawBorder(sheetMach, r1, BorderLineStyle.THICK); 
				
			}
			if(jobs.length < 1){
				sheetJobs.addCell(new Label(1, 7, "No jobs were configured", fontErrorFormat));
			}else{
				//DrawBorder(sheetJobs, r2a, BorderLineStyle.THIN);
				//DrawBorder(sheetJobs, r2, BorderLineStyle.THICK);
				//PopulateSheet(2);
				
				sheetJobs.addCell(new Label(1, 7, "Jobs", fontPageTitleFormat));
				
				sheetJobs.addCell(new Label(1, 9, "Description", fontCategoryFormat));

				sheetJobs.addCell(new Label(1, 11, "Name", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 12, "Thickness", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 13, "Density", fontCategoryFormat));
				
				sheetJobs.addCell(new Label(1, 15, "Web Width", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 16, "Unwind Core", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 17, "Avg. Flags", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 18, "Unwind Size", fontCategoryFormat));
				
				sheetJobs.addCell(new Label(1, 20, "Reel Count", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 21, "Slit Width", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 22, "Knife Type", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 23, "Trim", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 24, "Rewind Core", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 25, "Speed Limit", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 26, "Rewind Output", fontCategoryFormat));
				
				sheetJobs.addCell(new Label(1, 28, "Job Size", fontCategoryFormat));
				
				sheetJobs.addCell(new Label(0, 11, "Material:", fontDataCategoryFormat));
				sheetJobs.addCell(new Label(0, 15, "Unwind:", fontDataCategoryFormat));
				sheetJobs.addCell(new Label(0, 20, "Rewind:", fontDataCategoryFormat));
				sheetJobs.addCell(new Label(0, 28, "Totals:", fontDataCategoryFormat));
				
				for(int i=0; i<jobs.length; ++i){
					Job j = jobs[i];
					int x = 3 * i + 3;
					sheetJobs.addCell(new Label(x, 9, j.getName(), fontJobHeadFormat));
					
					sheetJobs.addCell(new Label(x, 11, j.getMaterial().name, fontDataFormat));
					sheetJobs.addCell(new Number(x, 12, (metric ? j.getMaterial().thickness : Core.MicroToMil(j.getMaterial().thickness)), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 12, "microns", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 12, "mils", fontDataFormat));
					sheetJobs.addCell(new Number(x, 13, j.getMaterial().density, fontDataFormat));
					sheetJobs.addCell(new Label(x+1, 13, "cc", fontDataFormat));
					
					sheetJobs.addCell(new Number(x, 15, (metric ? j.getWebWidth() : Core.MMToIn(j.getWebWidth())), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 15, "mm", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 15, "in", fontDataFormat));
					sheetJobs.addCell(new Number(x, 16, (metric ? j.getUnwindCore()-Consts.UNWIND_CORE_THICKNESS : Core.MMToIn(j.getUnwindCore()-Consts.UNWIND_CORE_THICKNESS)), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 16, "mm", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 16, "in", fontDataFormat));
					sheetJobs.addCell(new Number(x, 17, j.getFlagRate(), fontDataFormat));
					
					if(j.getUnwindType() == 0)
						sheetJobs.addCell(new Number(x, 18, (metric ? j.getUnwindLengthSI() : Core.MToFt(j.getUnwindLengthSI())), fontDataFormat));
					else if(j.getUnwindType() == 1)
						sheetJobs.addCell(new Number(x, 18, (metric ? j.getUnwindWeightSI() : Core.KgToTon(j.getUnwindWeightSI())), fontDataFormat));
					else if(j.getUnwindType() == 2)
						sheetJobs.addCell(new Number(x, 18, (metric ? j.getUnwindDiam() : Core.MMToIn(j.getUnwindDiam())), fontDataFormat));
					if(metric){
						if(j.getUnwindType() == 0)
							sheetJobs.addCell(new Label(x+1, 18, "m", fontDataFormat));
						else if(j.getUnwindType() == 1)
							sheetJobs.addCell(new Label(x+1, 18, "kg", fontDataFormat));
						else if(j.getUnwindType() == 2)
							sheetJobs.addCell(new Label(x+1, 18, "mm diam", fontDataFormat));
					}else{
						if(j.getUnwindType() == 0)
							sheetJobs.addCell(new Label(x+1, 18, "ft", fontDataFormat));
						else if(j.getUnwindType() == 1)
							sheetJobs.addCell(new Label(x+1, 18, "tons", fontDataFormat));
						else if(j.getUnwindType() == 2)
							sheetJobs.addCell(new Label(x+1, 18, "in diam", fontDataFormat));
					}
					sheetJobs.addCell(new Number(x, 20, j.getSlits(), fontDataFormat));
					sheetJobs.addCell(new Number(x, 21, (metric ? j.getSlitWidth() : Core.MMToIn(j.getSlitWidth())), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 21, "mm", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 21, "in", fontDataFormat));
					sheetJobs.addCell(new Label(x, 22, j.getKnifeType().toString(), fontDataFormat));
					sheetJobs.addCell(new Number(x, 23, (metric ? Math.max(0, j.getWebWidth()-j.getSlits()*j.getSlitWidth()) : Core.MMToIn(Math.max(0, j.getWebWidth()-j.getSlits()*j.getSlitWidth()))), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 23, "mm", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 23, "in", fontDataFormat));
					sheetJobs.addCell(new Number(x, 24, (metric ? j.getRewindCore()-Consts.REWIND_CORE_THICKNESS : Core.MMToIn(j.getRewindCore()-Consts.REWIND_CORE_THICKNESS)), fontDataFormat));
					if(metric)
						sheetJobs.addCell(new Label(x+1, 24, "mm", fontDataFormat));
					else
						sheetJobs.addCell(new Label(x+1, 24, "in", fontDataFormat));
					
					if(j.isSpeedLimited()){
						sheetJobs.addCell(new Number(x, 25, (metric ? j.getSpeedLimitSI()*60 : Core.MToFt(j.getSpeedLimitSI()*60)), fontDataFormat));
						sheetJobs.addCell(new Label(x+1, 25, "m/min", fontDataFormat));
					}else
						sheetJobs.addCell(new Label(x, 25, "NO", fontDataFormat));
					
					if(j.getRewindType() == 0)
						sheetJobs.addCell(new Number(x, 26, (metric ? j.getRewindLengthSI() : Core.MToFt(j.getRewindLengthSI())), fontDataFormat));
					else if(j.getRewindType() == 1)
						sheetJobs.addCell(new Number(x, 26, (metric ? j.getRewindWeightSI() : Core.KgToLbs(j.getRewindWeightSI())), fontDataFormat));
					else if(j.getRewindType() == 2)
						sheetJobs.addCell(new Number(x, 26, (metric ? j.getRewindDiam() : Core.MMToIn(j.getRewindDiam())), fontDataFormat));
					
					if(metric){
						if(j.getRewindType() == 0)
							sheetJobs.addCell(new Label(x+1, 26, "m", fontDataFormat));
						else if(j.getRewindType() == 1)
							sheetJobs.addCell(new Label(x+1, 26, "kg/set", fontDataFormat));
						else if(j.getRewindType() == 2)
							sheetJobs.addCell(new Label(x+1, 26, "mm diam", fontDataFormat));
					}else{
						if(j.getRewindType() == 0)
							sheetJobs.addCell(new Label(x+1, 26, "ft", fontDataFormat));
						else if(j.getRewindType() == 1)
							sheetJobs.addCell(new Label(x+1, 26, "lbs/set", fontDataFormat));
						else if(j.getRewindType() == 2)
							sheetJobs.addCell(new Label(x+1, 26, "in diam", fontDataFormat));
					}
					
					if(j.getTotalType() == 0)
						sheetJobs.addCell(new Number(x, 28, (metric ? j.getTotLength() : Core.MToFt(j.getTotLengthSI())), fontDataFormat));
					else if(j.getTotalType() == 1)
						sheetJobs.addCell(new Number(x, 28, (metric ? j.getTotWeightSI() : Core.KgToTon(j.getTotWeightSI())), fontDataFormat));
					else if(j.getTotalType() == 2)
						sheetJobs.addCell(new Number(x, 28, (metric ? j.getTotWeightSI()/1000 : Core.TonneToTon(j.getTotWeightSI()/1000)), fontDataFormat));
					if(metric){
						if(j.getTotalType() == 0)
							sheetJobs.addCell(new Label(x+1, 28, "km", fontDataFormat));
						else if(j.getTotalType() == 1)
							sheetJobs.addCell(new Label(x+1, 28, "kg", fontDataFormat));
						else if(j.getTotalType() == 2)
							sheetJobs.addCell(new Label(x+1, 28, "tonnes", fontDataFormat));
					}else{
						if(j.getTotalType() == 0)
							sheetJobs.addCell(new Label(x+1, 28, "ft", fontDataFormat));
						else if(j.getTotalType() == 1)
							sheetJobs.addCell(new Label(x+1, 28, "tons", fontDataFormat));
						else if(j.getTotalType() == 2)
							sheetJobs.addCell(new Label(x+1, 28, "tons", fontDataFormat));
					}
				}

				// environment data
				sheetJobs.addCell(new Label(1, 33+PAGE2_HEIGHT3, "Environment", fontPageTitleFormat));
				
				sheetJobs.addCell(new Label(1, 35+PAGE2_HEIGHT3, "Shift Length", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 36+PAGE2_HEIGHT3, "Shifts per Day", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 37+PAGE2_HEIGHT3, "Day Length", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 39+PAGE2_HEIGHT3, "Days per Year", fontCategoryFormat));
				sheetJobs.addCell(new Label(1, 40+PAGE2_HEIGHT3, "Year Length", fontCategoryFormat));
				sheetJobs.addCell(new Label(4, 35+PAGE2_HEIGHT3, "hrs", fontDataFormat));
				sheetJobs.addCell(new Label(4, 37+PAGE2_HEIGHT3, "hrs", fontDataFormat));
				sheetJobs.addCell(new Label(4, 40+PAGE2_HEIGHT3, "hrs", fontDataFormat));
				
				sheetJobs.addCell(new Number(3, 35+PAGE2_HEIGHT3, Math.max(0, environment.HrsPerShift), fontDataFormat));
				sheetJobs.addCell(new Number(3, 36+PAGE2_HEIGHT3, Math.max(0, roundTwoDecimals(environment.HrsPerDay / environment.HrsPerShift)), fontDataFormat));
				sheetJobs.addCell(new Number(3, 37+PAGE2_HEIGHT3, Math.max(0, environment.HrsPerDay), fontDataFormat));
				sheetJobs.addCell(new Number(3, 39+PAGE2_HEIGHT3, Math.max(0, roundTwoDecimals(environment.HrsPerYear / environment.HrsPerDay)), fontDataFormat));
				sheetJobs.addCell(new Number(3, 40+PAGE2_HEIGHT3, Math.max(0, environment.HrsPerYear), fontDataFormat));
				
				// schedule data
				if(environment.getSchedule() == null || environment.getSchedule().getSize() < 1){
					sheetJobs.addCell(new Label(1, 32, "Schedule", fontPageTitleFormat));
					sheetJobs.addCell(new Label(1, 34, "No schedule found", fontErrorFormat));
				}else{
					sheetJobs.addCell(new Label(1, 32, "Schedule", fontPageTitleFormat));
					sheetJobs.addCell(new Label(10, 34, "Order: ", fontFootnoteFormat));
					for(int i=0; i<environment.getSchedule().getSize(); ++i){
						sheetJobs.addCell(new Label(10, 35+i, environment.getSchedule().getJob(i).getName(), fontDataFormat));
					}
					
					ScheduleChart ch = new ScheduleChart(environment.getSchedule());
					JFreeChart sched = ch.getChart();
					
					fs = new File(tmpSched);
					if(sched !=null){
						try {
							ChartUtilities.saveChartAsPNG(fs, sched, 600, 360);
							sheetJobs.addImage(new WritableImage(1, 34, 8, 18, new File(tmpSched)));
						} catch (Exception e1) {
							sheetJobs.addCell(new Label(1, 34, "No schedule chart found"));
						}
						
					}
				}
				
				sheetJobs.addCell(new Label(0, PAGE2_HEIGHT1 + PAGE2_HEIGHT2 + PAGE2_HEIGHT3 + 8, "Calculations are approximations only", fontFootnoteFormat));
				
				DrawBorder(sheetJobs, r2a, BorderLineStyle.THICK);
				DrawBorder(sheetJobs, r2b, BorderLineStyle.THICK);
				DrawBorder(sheetJobs, r2c, BorderLineStyle.THICK);
			}
			if(results == null || results.getSize() < 1){
				sheetRes.addCell(new Label(1, 7, "No results were available", fontErrorFormat));
			}else{
				DrawBorder(sheetRes, r3, BorderLineStyle.THICK);
				
				sheetRes.addCell(new Label(1, 7, "Productivity Results", fontPageTitleFormat));
				
				sheetRes.addCell(new Label(1, 9, "Machine", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 14, "Average rate", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 15, "Schedule run time", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 16, "Time running %", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 17, "Length / year", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 18, "Weight / year", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 20, "Productivity", fontCategoryFormat));
				sheetRes.addCell(new Label(1, 21, "breakdown", fontCategoryFormat));
				
				for(int i=0; i < results.getSize(); ++i){
					Result r = results.get(i);
					
					int x = 3 + 4*i;
					
					// add model logo
					switch(r.getModelType()){
						case ER610: sheetRes.addImage(new WritableImage(x, 9, 2, 3, imgER610));
						break;
						case SR9DS: sheetRes.addImage(new WritableImage(x, 9, 2, 3, imgSR9DS));
						break;
						case SR9DT: sheetRes.addImage(new WritableImage(x, 9, 2, 3, imgSR9DT));
						break;
						case SR800: sheetRes.addImage(new WritableImage(x, 9, 2, 3, imgSR800));
						break;
						case CUSTOM: sheetRes.addImage(new WritableImage(x, 9, 2, 3, imgCustom));
						break;
					}
					
					// description
					sheetRes.addCell(new Label(x, 12, r.getName(), fontDataFormat));
					
					if(metric){
						sheetRes.addCell(new Number(x, 14, (int) r.MtPerHour, fontNumberFormat));
						sheetRes.addCell(new Label(x+1, 14, "m/hr", fontDataFormat));
					}else{
						sheetRes.addCell(new Number(x, 14, (int) Core.MToFt(r.MtPerHour), fontNumberFormat));
						sheetRes.addCell(new Label(x+1, 14, "ft/hr", fontDataFormat));
					}
					sheetRes.addCell(new Number(x, 15, roundTwoDecimals(r.ScheduleTime), fontNumberFormat));
					sheetRes.addCell(new Label(x+1, 15, "hours", fontDataFormat));
					sheetRes.addCell(new Number(x, 16, (int) r.getResult(ResultType.EFF, ResultTime.HOUR), fontNumberFormat));
					sheetRes.addCell(new Label(x+1, 16, "%", fontDataFormat));
					if(metric){
						sheetRes.addCell(new Number(x, 17, (int) r.getResult(ResultType.LENGTH, ResultTime.YEAR) / 1000, fontNumberFormat));
						sheetRes.addCell(new Number(x, 18, (int) r.getResult(ResultType.WEIGHT, ResultTime.YEAR) / 1000, fontNumberFormat));
						sheetRes.addCell(new Label(x+1, 17, "km", fontDataFormat));
						sheetRes.addCell(new Label(x+1, 18, "tonnes", fontDataFormat));
					}else{
						sheetRes.addCell(new Number(x, 17, (int) Core.KmToMi(r.getResult(ResultType.LENGTH, ResultTime.YEAR) / 1000), fontNumberFormat));
						sheetRes.addCell(new Number(x, 18, (int) Core.TonneToTon(r.getResult(ResultType.WEIGHT, ResultTime.YEAR) / 1000), fontNumberFormat));
						sheetRes.addCell(new Label(x+1, 17, "mi", fontDataFormat));
						sheetRes.addCell(new Label(x+1, 18, "tons", fontDataFormat));
					}
					
					
					
					JFreeChart tmp = null;
					try {
						if(results.getBreakdownCharts()[i]!=null)
							tmp = (JFreeChart) results.getBreakdownCharts()[i].clone();
					}catch(CloneNotSupportedException e){
						errors = true;
					}
					try {
						ChartUtilities.saveChartAsPNG(brkdns[i], tmp, 300, 180);
						sheetRes.addImage(new WritableImage(x,  20, 3, 7, brkdns[i]));
					}catch(Exception e){
						sheetRes.addCell(new Label(x,  20, "No breakdown chart found"));
					}
					
				}

				//results
				JFreeChart a = null, b=null;
				try {
					if(results.getRateGraph()!=null)
						a = (JFreeChart) results.getRateGraph().clone();
					if(results.getProdWGraph()!=null)
						b = (JFreeChart) results.getProdWGraph().clone();
					/*if(results.getRateGraph()!=null)
						c = (JFreeChart) results.getRateGraph().clone();
					if(results.getRateGraph()!=null)
						d = (JFreeChart) results.getRateGraph().clone();*/
				} catch (CloneNotSupportedException e1) {
					errors = true;
				}
				
				sheetRes.addCell(new Label(1, 30, "Graphical Results:", fontJobHeadFormat));
				
				try {
					ChartUtilities.saveChartAsPNG(fresa, a, 600, 360);
					sheetRes.addImage(new WritableImage(1, 32, 8, 18, fresa));
				} catch (Exception e1) {
					sheetRes.addCell(new Label(1,  32, "No rate chart found"));
				}
				try {
					ChartUtilities.saveChartAsPNG(fresb, b, 600, 360);
					sheetRes.addImage(new WritableImage(10, 32, 8, 18, fresb));
				} catch (Exception e1) {
					sheetRes.addCell(new Label(10,  32, "No production chart found"));
				}
				
				sheetRes.addCell(new Label(PAGE3_WIDTH - 4, PAGE3_HEIGHT + 6 + 1, "Calculations are approximations only", fontFootnoteFormat));
				
			}			
			
			if(RoiResults == null || RoiResults.getSize() < 1){
				sheetRoi.addCell(new Label(1, 7, "No results were available", fontErrorFormat));
			}else{
				DrawBorder(sheetRoi, r4, BorderLineStyle.THICK);
				
				sheetRoi.addCell(new Label(1, 7, "Return on Investment Analysis", fontPageTitleFormat));
				
				sheetRoi.addCell(new Label(1, 9, "Input Data", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 14, "Machine", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 19, "Average Power", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 20, "Energy / Year", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 21, "Annual Downtime", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 22, "Labour Cost", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 23, "Parts Costs", fontCategoryFormat));
				
				sheetRoi.addCell(new Label(1, 25, "Value Added", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 26, "Energy Cost", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 27, "Production Loss", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 28, "Parts+Labour Costs", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 29, "Total Maintenance", fontCategoryFormat));
				sheetRoi.addCell(new Label(1, 30, "Waste Reduced", fontCategoryFormat));
				sheetRoi.addCell(new Label(2, 31, "=", fontCategoryFormat));
				
				sheetRoi.addCell(new Label(3, 9, "Selling Price:", fontCategoryFormat));
				sheetRoi.addCell(new Label(3, 10, "Energy Cost:", fontCategoryFormat));
				sheetRoi.addCell(new Label(3, 11, "Waste per splice saved with flag camera:", fontCategoryFormat));
				sheetRoi.addCell(new Label(3, 12, "Waste per mother saved with alignment guide:", fontCategoryFormat));
				sheetRoi.addCell(new Label(7, 9, "Contribution:", fontCategoryFormat));
				sheetRoi.addCell(new Label(10, 9, "Value Added:", fontCategoryFormat));
				sheetRoi.addCell(new Label(5, 9, "£"+formatDecimal(RoiData.sellingprice)+" / tonne", fontDataFormat));
				sheetRoi.addCell(new Label(5, 10, "£"+formatDecimal(RoiData.energycost)+" / kWh", fontDataFormat));
				sheetRoi.addCell(new Label(8, 11, "£"+formatDecimal(RoiData.wastesavedflag), fontDataFormat));
				sheetRoi.addCell(new Label(8, 12, "£"+formatDecimal(RoiData.wastesavedguide), fontDataFormat));
				sheetRoi.addCell(new Label(9, 9, formatDecimal(RoiData.contribution * 100)+"%", fontDataFormat));
				sheetRoi.addCell(new Label(12, 9, "£"+formatDecimal(RoiData.value)+" / tonne", fontDataFormat));
				
				for(int i=0; i < results.getSize(); ++i){
					Result r = results.get(i);
					ResultROI roi = RoiResults.get(i);
					
					int x = 3 + 3*i;
					
					// add model logo
					switch(r.getModelType()){
						case ER610: sheetRoi.addImage(new WritableImage(x, 14, 2, 3, imgER610));
						break;
						case SR9DS: sheetRoi.addImage(new WritableImage(x, 14, 2, 3, imgSR9DS));
						break;
						case SR9DT: sheetRoi.addImage(new WritableImage(x, 14, 2, 3, imgSR9DT));
						break;
						case SR800: sheetRoi.addImage(new WritableImage(x, 14, 2, 3, imgSR800));
						break;
						case CUSTOM: sheetRoi.addImage(new WritableImage(x, 14, 2, 3, imgCustom));
						break;
					}
					
					// description
					sheetRoi.addCell(new Label(x, 17, r.getName(), fontDataFormat));
					
					//todo: units type, write units, values
					sheetRoi.addCell(new Number(x, 19, roundTwoDecimals(RoiData.energies.get(listCompareRoi.getSelectedIndices()[i]).kwhrsperyear / environment.HrsPerYear), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 19, "kW", fontDataFormat));
					sheetRoi.addCell(new Number(x, 20, roundTwoDecimals(RoiData.energies.get(listCompareRoi.getSelectedIndices()[i]).kwhrsperyear / 1000), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 20, "MWh", fontDataFormat));
					sheetRoi.addCell(new Number(x, 21, roundTwoDecimals(RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).tothours), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 21, "hrs", fontDataFormat));
					sheetRoi.addCell(new Label(x, 22, "£"+formatDecimal(RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).labourhourly), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 22, "/ hour", fontDataFormat));
					sheetRoi.addCell(new Label(x, 23, "£"+formatDecimal(RoiData.maintenance.get(listCompareRoi.getSelectedIndices()[i]).parts), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 23, "/ year", fontDataFormat));
					
    				sheetRoi.addCell(new Number(x, 25, roundTwoDecimals(roi.value), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 25, "£ / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 26, roundTwoDecimals(roi.energycost), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 26, "£ / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 27, roundTwoDecimals(roi.prodloss), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 27, "£ / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 28, roundTwoDecimals(roi.partcosts), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 28, "£ / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 29, roundTwoDecimals(roi.maintcost), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 29, "£ / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 30, roundTwoDecimals(roi.wastesave), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 30, "m / yr", fontDataFormat));
					sheetRoi.addCell(new Number(x, 31, roundTwoDecimals(roi.wasteval), fontNumberFormat));
					sheetRoi.addCell(new Label(x+1, 31, "£ / yr", fontDataFormat));
					
				}

				//results
				JFreeChart a = null, b=null, c = null, d=null;
				try {
					if(RoiResults.getProdGraph()!=null)
						a = (JFreeChart) RoiResults.getProdGraph().clone();
					if(RoiResults.getEnergyGraph()!=null)
						b = (JFreeChart) RoiResults.getEnergyGraph().clone();
					if(RoiResults.getMaintGraph()!=null)
						c = (JFreeChart) RoiResults.getMaintGraph().clone();
					if(RoiResults.getWasteGraph()!=null)
						d = (JFreeChart) RoiResults.getWasteGraph().clone();
				} catch (CloneNotSupportedException e1) {
					errors = true;
				}
				
				sheetRoi.addCell(new Label(1, 34, "Graphical Results:", fontJobHeadFormat));
				
				try {
					ChartUtilities.saveChartAsPNG(froia, a, 600, 360);
					sheetRoi.addImage(new WritableImage(1, 36, 8, 18, froia));
				} catch (Exception e1) {
					sheetRoi.addCell(new Label(1,  36, "No productivity chart found"));
				}
				try {
					ChartUtilities.saveChartAsPNG(froib, b, 600, 360);
					sheetRoi.addImage(new WritableImage(10, 36, 8, 18, froib));
				} catch (Exception e1) {
					sheetRoi.addCell(new Label(10,  36, "No energy chart found"));
				}
				try {
					ChartUtilities.saveChartAsPNG(froic, c, 600, 360);
					sheetRoi.addImage(new WritableImage(1, 55, 8, 18, froic));
				} catch (Exception e1) {
					sheetRoi.addCell(new Label(1,  55, "No maintenance chart found"));
				}
				try {
					ChartUtilities.saveChartAsPNG(froid, d, 600, 360);
					sheetRoi.addImage(new WritableImage(10, 55, 8, 18, froid));
				} catch (Exception e1) {
					sheetRoi.addCell(new Label(10,  55, "No waste chart found"));
				}
					
				sheetRoi.addCell(new Label(PAGE4_WIDTH - 4, PAGE4_HEIGHT + 6 + 1, "Calculations are approximations only", fontFootnoteFormat));
			}
			
			// All sheets and cells added. Now write out the workbook 
			workbook.write();
			workbook.close();
			 
			// Delete temp files (graphs)
			DeleteFile(fs);
			DeleteFile(fresa);
			DeleteFile(fresb);
			DeleteFile(fresc);
			DeleteFile(fresd);
			DeleteFile(froia);
			DeleteFile(froib);
			DeleteFile(froic);
			DeleteFile(froid);
			for(int i=0;i<brkdns.length;++i)
				DeleteFile(brkdns[i]);
			// TODO more...
			
		//} catch (RowsExceededException e){
		///TODO show error...
		//	success = false;
		//} catch (WriteException e){
		//	success = false;
		//} catch (IOException e) {
		//	success = false;
		//} catch (Exception e){
		//	success = false;
		//}
		// TODO errors=true indicates success but with errors
		//return success;
		
			return !errors;
	}
	
	private void SaveAll(){
		
		ResetStatusLabel();
		
		fc = new OpenSaveFileChooser();
		fc.setFileFilter(new OBJfilter(3));
		fc.type = 1;
		
		int returnVal = fc.showSaveDialog(frmTitanRoiCalculator);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
            File file = fc.getSelectedFile();
            
            String path = file.getAbsolutePath();

            String extension = ".ser";

            if(!path.endsWith(extension))
            {
              file = new File(path + extension);
            }

            try {
            	UpdateAnalysis();
            	UpdateROI();
            	SaveFile sf = new SaveFile();
        		sf.add(environment, listMachines.getModel(), listJobs.getModel(), machNames, jobNames, listCompare.getSelectedIndices(), RoiData);
        		
            	FileOutputStream fout = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(sf);
                oos.close();
                ShowMessageSuccess("File saved.");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frmTitanRoiCalculator, "Error writing file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class AddScheduleBtn extends AbstractAction {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			btnAddJob.doClick();
		}
	}
	
	private void OpenAll(){
		ResetStatusLabel();

		formReady = false;
		jobFormReady = false;
	
		fc = new OpenSaveFileChooser();
		fc.setFileFilter(new OBJfilter(3));
		fc.type = 1;
		
		JOptionPane pane = new JOptionPane("Opening a previous configuration will erase all current settings.\nAre you sure you want to proceed?");
	    Object[] options = new String[] { "Yes", "No" };
	    pane.setOptions(options);
	    JDialog dialog = pane.createDialog(new JFrame(), "Confirm");
	    dialog.setVisible(true);
	    Object obj = pane.getValue(); 
	    if(obj != null && obj.equals(options[0])){
		
			int returnVal = fc.showOpenDialog(frmTitanRoiCalculator);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				SaveFile save;
				
	            File file = fc.getSelectedFile();
	
	            try {
	            	FileInputStream fin = new FileInputStream(file);
	                ObjectInputStream ois = new ObjectInputStream(fin);
	                save = (SaveFile) ois.readObject();
	                ois.close();
	                
	                // Don't want to change the save settings in any way
	                boolean temp1 = environment.SaveOnClose;
	                String temp2 = environment.SaveFileName;
	                environment = save.getEnvironment();
	                environment.SaveOnClose = temp1;
	                environment.SaveFileName = temp2;
	                
	                listModel = (DefaultListModel) save.getMachineList();
	                RoiData.setSize(listModel.getSize());
	                listMachines.setModel(listModel);
	                jobModel = (DefaultListModel) save.getJobList();
	                listJobs.setModel(jobModel);
	                listJobsAvail.setModel(jobModel);
	                listCompare.setModel(listModel);
	                listCompareRoi.setModel(listModel);
	                listCompare.setSelectedIndices(save.getSelection());
	                machNames = save.getMachNames();
	                jobNames = save.getJobNames();
	                RoiData = (ROIData) save.getRoiData().clone();
	                if(metric != environment.getUnits()){
	                	ChangeUnits();
	                	rdbtnmntmImperial.setSelected(true);
	                }
	                
	                chckbxSimulateScheduleStart.setSelected(!environment.StartStopTimes);
	                
	                scheduleModel.clear();
	                environment.Unlocked = false;
    	            if(environment.getSchedule() == null)
    	            	environment.setSchedule(new JobSchedule());
    	            else{
    	            	int size = environment.getSchedule().getSize();
    	            	for(int i=0; i<size; ++i){
    	            		scheduleModel.addElement(environment.getSchedule().getJob(i));
    	            	}
    	            	btnRemoveJob.setEnabled(true);
    	            	btnUpSchedule.setEnabled(true);
    	            	btnViewSchedule.setEnabled(true);
    	            	btnClearSchedule.setEnabled(true);
    	            	if(size > 0)
    	            		listSchedule.setSelectedIndex(0);
    	            }
	                
    	            formReady = false;
    	            UpdateForm();
    	            
	                if(listModel.size() > 0){
	                	listMachines.setSelectedIndex(0);
	                	formReady = true;
	                }
	                if(jobModel.size() > 0){
	                	listJobs.setSelectedIndex(0);
	                	jobFormReady = true;
	                }

	                UpdateJobForm();
	                
	                // Update environment
	                initialising = true;
	                chckbxSimulateScheduleStart.setSelected(!environment.StartStopTimes);
	                txtShiftLength.setText(Double.toString(roundTwoDecimals(environment.HrsPerShift)));
	                txtShiftCount.setText(Double.toString(roundTwoDecimals(environment.HrsPerDay / environment.HrsPerShift)));
	                txtDaysYear.setText(Double.toString(roundTwoDecimals(environment.HrsPerYear / environment.HrsPerDay)));
	                initialising = false;
	                UpdateShifts();
	                
	                // Update ROI form
	                txtsellingprice.setText(Double.toString(roundTwoDecimals((metric ? save.RoiData.sellingprice : Core.TonToTonne(save.RoiData.sellingprice)))));
	                txtcontribution.setText(Double.toString(roundTwoDecimals(save.RoiData.contribution * 100)));
	                lblvalueadded.setText("£"+formatDecimal((metric ? save.RoiData.value : Core.TonToTonne(save.RoiData.value)))+(metric ? " / tonne" : " / ton"));
	                txtenergycost.setText(Double.toString(roundTwoDecimals(save.RoiData.energycost)));
	                txtwastesavedflags.setText(Double.toString(roundTwoDecimals((metric ? save.RoiData.wastesavedflag : Core.MToFt(save.RoiData.wastesavedflag)))));
	                txtwastesavedguide.setText(Double.toString(roundTwoDecimals((metric ? save.RoiData.wastesavedguide : Core.MToFt(save.RoiData.wastesavedguide)))));
	                
	                UpdateAnalysis();
	                UpdateROI();
	                
	                ShowMessageSuccess("File loaded successfully.");
	                
	            } catch (ClassCastException e1){
	            	ShowMessage("Save-all file required, not found.");
				} catch (ClassNotFoundException e1) {
					ShowMessage("File load error.");
				} catch (FileNotFoundException e1){
					ShowMessage("File not found.");
				} catch (Exception e1){
					ShowMessage("File load error.");
				} finally {
					if(!(listModel.getSize() == 0))
						formReady = true;
				    if(!(jobModel.getSize() == 0))
						jobFormReady = true;
				}
	            
			}
		
	    }
	    
	}
	
	private void ShowTimingBreakdown(){
		if(breakdown.length > 0){
			int[] is = listCompare.getSelectedIndices();
			Machine[] ms = new Machine[is.length];
			for(int i=0; i<ms.length; i++){
				ms[i] = (Machine) listModel.get(is[i]);
			}
			String[] mnames = new String[ms.length];
			for(int i=0; i<mnames.length; i++){
				mnames[i] = ms[i].model + ": " + ms[i].name;
			}
			TimingBreakdown pop = new TimingBreakdown(breakdown, mnames);
			pop.setLocationRelativeTo(frmTitanRoiCalculator);
		}
	}
	
	public void ShowMessage(String msg){
		lblStatus.setText(" " + msg);
    	lblStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
    	lblStatus.setForeground(Color.RED);
	}
	public void ShowMessageSuccess(String msg){
		lblStatus.setText(" " + msg);
    	lblStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
    	lblStatus.setForeground(new Color(0,128,0));
	}
	public void ShowMessageStandard(String msg){
		lblStatus.setText(" " + msg);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setForeground(Color.BLACK);
	}
	public void RefreshStatusLabel(){
		//lblStatus.repaint();
		//lblStatus.revalidate();
	}
	
	public static final String DATE_FORMAT_NOW = "dd/MM/yyyy";

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	public class JobInputChangeListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent arg0) {
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			UpdateJob();
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			UpdateJob();
		}
	}
	
	private void ResetJobForm(){
		try{
			ResetStatusLabel();
			
			int selected=0;
			int size=0;
			try{
				selected = listJobs.getSelectedIndex();
				size = jobModel.getSize();
			}catch (NullPointerException e)
			{ jobFormReady = false;
			}
			if((selected < 0) || (size == 0))
				jobFormReady = false;
			
			//try{
			if(metric){
				cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_MM));
				cmbUnwindCore.setModel(new DefaultComboBoxModel(Consts.UNWIND_CORE_MM));
				
				cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
				cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Diameter (mm)"}));
				cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (m)", "Weight (kg)", "Weight (tonnes)"}));
			}else{
				cmbRewindCore.setModel(new DefaultComboBoxModel(Consts.REWIND_CORE_IN));
				cmbUnwindCore.setModel(new DefaultComboBoxModel(Consts.UNWIND_CORE_IN));
				
				cmbUnwindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)", "Diameter (in)"}));
				cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (lbs)", "Diameter (in)"}));
				cmbTargetTotal.setModel(new DefaultComboBoxModel(new String[] {"Length (ft)", "Weight (ton)"}));
			}
			//}catch(IllegalStateException e){
				
			//}
			
			oldUnwindIndex = 0;
			oldRewindIndex = 0;
			oldTotalsIndex = 0;
			SetReelType = 0;
			
			cmbMaterials.setSelectedIndex(0);
			cmbUnwindCore.setSelectedIndex(0);
			cmbRewindCore.setSelectedIndex(1);
			cmbJobDomain.setSelectedIndex(0);
			cmbUnwindType.setSelectedIndex(0);
			cmbRewindType.setSelectedIndex(0);
			cmbTargetTotal.setSelectedIndex(0);
			cmbKnifeType.setSelectedIndex(0);
			txtThickness.setText("20");
			if(lblGsm.getText().equals("(gsm)"))
				txtDensity.setText("0.92");
			else
				txtDensity.setText("18.4");
			chckbxLimitRunSpeed.setSelected(false);
			txtUnwindAmount.setText("10000");
			txtRewindAmount.setText("1000");
			lblTrim.setText("0 mm");
			txtLimitRunSpeed.setText("800");
			txtSlits.setText("1");
			txtSlitWidth.setText("1350");
			txtTargetTotal.setText("10000");
			txtWebWidth.setText("1350");
			txtFlagCount.setText("1");
			txtLimitRunSpeed.setEnabled(false);
			
			if(!metric){
				txtThickness.setText("0.79");
				txtUnwindAmount.setText("32810");
				txtRewindAmount.setText("3281.0");
				txtLimitRunSpeed.setText("2624.8");
				txtTargetTotal.setText("32810");
				txtWebWidth.setText("53.15");
				txtSlitWidth.setText("53.15");
				lblTrim.setText("0 in");
			}
		}
			catch(IllegalStateException e1){
				e1.printStackTrace();
		}
			catch(NullPointerException e){
		}
	}
	
	private void EnableJobForm(){
		ResetStatusLabel();
		
		txtJobName.setEnabled(true);
		txtWebWidth.setEnabled(true);
		cmbMaterials.setEnabled(true);
		cmbUnwindCore.setEnabled(true);
		cmbRewindCore.setEnabled(true);
		cmbJobDomain.setEnabled(false);
		cmbUnwindType.setEnabled(true);
		cmbRewindType.setEnabled(true);
		txtThickness.setEnabled(true);
		txtDensity.setEnabled(true);
		chckbxLimitRunSpeed.setEnabled(true);
		txtUnwindAmount.setEnabled(true);
		txtRewindAmount.setEnabled(true);
		lblTrim.setEnabled(true);
		if(chckbxLimitRunSpeed.isSelected())
			txtLimitRunSpeed.setEnabled(true);

		txtSlits.setEnabled(true);
		txtSlitWidth.setEnabled(true);
		cmbKnifeType.setEnabled(true);
		lblKnifeType.setEnabled(true);
		
		btnResetJobs.setEnabled(true);
		//btnJobDelete.setEnabled(true);
		//btnJobUp.setEnabled(true);
		//btnJobDown.setEnabled(true);
		
		lblDensity_1.setEnabled(true);
		lblThickness_1.setEnabled(true);
		lblmicro0.setEnabled(true);
		lblLimitRunSpeed.setEnabled(true);
		lblPer.setEnabled(true);
		lblRewindCoremm.setEnabled(true);
		lblUnwindCoremm.setEnabled(true);
		lblWebWidthmm.setEnabled(true);
		lblTrimtotal.setEnabled(true);
		lblSlitCount.setEnabled(true);
		lblmm0.setEnabled(true);
		lblmm1.setEnabled(true);
		lblmm2.setEnabled(true);
		lblmm3.setEnabled(true);
		lblmmin.setEnabled(true);
		lblPresets.setEnabled(true);
		lblJobName.setEnabled(true);
		lblgm3.setEnabled(true);
		lblGsm.setEnabled(true);
		lblOr.setEnabled(true);
		lblTargetRewindLength.setEnabled(true);
		lblUnwindLength.setEnabled(true);
		lblSlitWidth.setEnabled(true);
		lblPer_1.setEnabled(true);
		label_3.setEnabled(true);
		lblTargetOutputFor.setEnabled(true);
		txtTargetTotal.setEnabled(true);
		cmbTargetTotal.setEnabled(true);
		lblPerRoll.setEnabled(true);
		lblAverageFlags.setEnabled(true);
		txtFlagCount.setEnabled(true);
	}
	private void DisableJobForm(){
		ResetStatusLabel();
		txtJobName.setEnabled(false);
		txtWebWidth.setEnabled(false);
		cmbMaterials.setEnabled(false);
		cmbUnwindCore.setEnabled(false);
		cmbRewindCore.setEnabled(false);
		cmbJobDomain.setEnabled(false);
		cmbUnwindType.setEnabled(false);
		cmbRewindType.setEnabled(false);
		txtThickness.setEnabled(false);
		txtDensity.setEnabled(false);
		chckbxLimitRunSpeed.setEnabled(false);
		txtUnwindAmount.setEnabled(false);
		txtRewindAmount.setEnabled(false);
		lblTrim.setEnabled(false);
		txtLimitRunSpeed.setEnabled(false);
		txtSlits.setEnabled(false);
		txtSlitWidth.setEnabled(false);
		cmbKnifeType.setEnabled(false);
		lblKnifeType.setEnabled(false);
		
		lblGsm.setText("(gsm)");
		label_3.setVisible(true);
		lblgm3.setText("(g/cm  )");
		txtDensity.setText("0.92");
		
		btnResetJobs.setEnabled(false);
		btnJobDelete.setEnabled(false);
		btnJobUp.setEnabled(false);
		btnJobDown.setEnabled(false);
		
		lblDensity_1.setEnabled(false);
		lblThickness_1.setEnabled(false);
		lblmicro0.setEnabled(false);
		lblLimitRunSpeed.setEnabled(false);
		lblPer.setEnabled(false);
		lblRewindCoremm.setEnabled(false);
		lblUnwindCoremm.setEnabled(false);
		lblWebWidthmm.setEnabled(false);
		lblTrimtotal.setEnabled(false);
		lblSlitCount.setEnabled(false);
		lblmm0.setEnabled(false);
		lblmm1.setEnabled(false);
		lblmm2.setEnabled(false);
		lblmm3.setEnabled(false);
		lblmmin.setEnabled(false);
		lblPresets.setEnabled(false);
		lblJobName.setEnabled(false);
		lblgm3.setEnabled(false);
		lblGsm.setEnabled(false);
		lblOr.setEnabled(false);
		lblTargetRewindLength.setEnabled(false);
		lblUnwindLength.setEnabled(false);
		lblSlitWidth.setEnabled(false);
		lblPer_1.setEnabled(false);
		label_3.setEnabled(false);
		lblTargetOutputFor.setEnabled(false);
		txtTargetTotal.setEnabled(false);
		cmbTargetTotal.setEnabled(false);
		lblPerRoll.setEnabled(false);
		lblAverageFlags.setEnabled(false);
		txtFlagCount.setEnabled(false);
		
		btnNewJob.requestFocusInWindow();
	}
	
	private void UpdateJobForm(){
		
    	if (listJobs == null || (listJobs.getSelectedIndex() < 0) || (!jobFormReady) || jobModel.getSize() < 1) {
    		
    	}else{
    	
    		jobFormReady = false;
    	
    		EnableJobForm();
	    	ResetJobForm();
	    	ResetStatusLabel();
			
			int selected = -1;
			int size = 0;
			try{
				selected = listJobs.getSelectedIndex();
				size = jobModel.getSize();
			}catch (NullPointerException e)
			{ jobFormReady = true;
				return; 
			}
			if((selected < 0) || (size == 0)){
				jobFormReady = true;ResetJobForm();
				return;
			}
			
			Job j = (Job) listJobs.getSelectedValue();
			
			txtJobName.setText(j.getName());
			
			if(j.gsm){
				lblGsm.setText("(g/cc)");
	 			label_3.setVisible(false);
	 			lblgm3.setText("(gsm)");
			}else{
				lblGsm.setText("(gsm)");
	 			label_3.setVisible(true);
	 			lblgm3.setText("(g/cm  )");
			}
			
			cmbMaterials.setSelectedItem(j.getMaterial());
			txtThickness.setText(Double.toString(roundTwoDecimals((metric ? j.getMaterial().thickness : Core.MicroToMil(j.getMaterial().thickness)))));
			if(lblGsm.getText().equals("(gsm)"))
				txtDensity.setText(Double.toString(roundTwoDecimals(j.getMaterial().density)));
			else
				txtDensity.setText(Double.toString(roundTwoDecimals(j.getMaterial().density * j.getMaterial().thickness)));
			cmbUnwindType.setSelectedIndex(j.getUnwindType());
			cmbRewindType.setSelectedIndex(j.getRewindType());
			cmbTargetTotal.setSelectedIndex( ((!metric && j.getTotalType()==2) ? 1 : j.getTotalType()));
			oldRewindIndex = cmbRewindType.getSelectedIndex();
			oldUnwindIndex = cmbUnwindType.getSelectedIndex();
			oldTotalsIndex = cmbTargetTotal.getSelectedIndex();
			
			if(j.getUnwindType() == 0)
				txtUnwindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getUnwindLength() : Core.MToFt(j.getUnwindLength())))));
			else if(j.getUnwindType() == 1)
				txtUnwindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getUnwindWeight() : Core.KgToTon(j.getUnwindWeight())))));
			else if(j.getUnwindType() == 2)
				txtUnwindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getUnwindDiam() : Core.MMToIn(j.getUnwindDiam())))));
			
			if(j.getRewindType() == 0)
				txtRewindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getRewindLength() : Core.MToFt(j.getRewindLength())))));
			else if(j.getRewindType() == 1)
				txtRewindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getRewindWeight() : Core.KgToLbs(j.getRewindWeight())) / j.getSlits())));
			else if(j.getRewindType() == 2)
				txtRewindAmount.setText(Double.toString(roundTwoDecimals((metric ? j.getRewindDiam() : Core.MMToIn(j.getRewindDiam())))));
			
			if(j.getTotalType() == 0)
				txtTargetTotal.setText(Double.toString(roundTwoDecimals((metric ? j.getTotLength() : Core.MToFt(j.getTotLength())))));
			else if(j.getTotalType() == 1)
				txtTargetTotal.setText(Double.toString(roundTwoDecimals((metric ? j.getTotWeight() : Core.KgToTon(j.getTotWeight())))));
			else if(j.getTotalType() == 2)
				txtTargetTotal.setText(Double.toString(roundTwoDecimals((metric ? Core.KgToTonne(j.getTotWeight()) : Core.KgToTon(j.getTotWeight())))));
			
			txtFlagCount.setText(Double.toString(roundTwoDecimals(j.getFlagRate())));
			
			txtSlits.setText(Integer.toString(j.getSlits()));
			txtSlitWidth.setText(Double.toString(roundTwoDecimals((metric ? j.getSlitWidth() : Core.MMToIn(j.getSlitWidth())))));
			txtWebWidth.setText(Double.toString(roundTwoDecimals((metric ? j.getWebWidth() : Core.MMToIn(j.getWebWidth())))));
			
			chckbxLimitRunSpeed.setSelected(j.isSpeedLimited());
			txtLimitRunSpeed.setText(Double.toString(roundTwoDecimals((metric ? j.getSpeedLimit() : Core.MToFt(j.getSpeedLimit())))));
			if(j.isSpeedLimited()){
				txtLimitRunSpeed.setEnabled(true);
			}else{
				txtLimitRunSpeed.setEnabled(false);
			}
			
			cmbRewindCore.setSelectedItem(Double.toString(roundTwoDecimals((metric ? j.getRewindCore() - Consts.REWIND_CORE_THICKNESS : Core.MMToIn(j.getRewindCore() - Consts.REWIND_CORE_THICKNESS)))));
			cmbUnwindCore.setSelectedItem(Double.toString(roundTwoDecimals((metric ? j.getUnwindCore() - Consts.UNWIND_CORE_THICKNESS : Core.MMToIn(j.getUnwindCore() - Consts.UNWIND_CORE_THICKNESS)))));
			
			if(j.getKnifeType() == KnifeType.RAZOR)
				cmbKnifeType.setSelectedIndex(0);
			else
				cmbKnifeType.setSelectedIndex(1);
			
			UpdateUnwindWarning(j.getUnwindDiam());
			
			UpdateTrim();
			
			UpdateReelCounts();
			
			jobFormReady = true;
		
		}
		
	}
	
	private void UpdateJobName(){
		if(jobFormReady){
			jobFormReady = false;
		
			ResetStatusLabel();
			int selected = -1;
			int size = 0;
			try{
				selected = listJobs.getSelectedIndex();
				size = jobModel.getSize();
			}catch (NullPointerException e)
			{ jobFormReady = true;
				return; 
			}
			if((selected < 0) || (size == 0)){
				jobFormReady = true;ResetJobForm();
				return;
			}
			
			String input = txtJobName.getText();
	    	ResetStatusLabel();
			if(input.equals("")){
				ShowMessage("Please enter a job name.");
				jobFormReady = true;
		    	return;
			}
			
			if(jobNames.contains(input.toLowerCase()) && (!(((Job)listJobs.getSelectedValue()).getName().equals(input)))){
		    	ShowMessage("Duplicate job names not allowed. Please choose a different name.");
		    	jobFormReady = true;
		    	return;
		    }
			jobNames.remove(((Job)listJobs.getSelectedValue()).getName().toLowerCase());
			jobNames.add(input.toLowerCase());
		    
			((Job)listJobs.getSelectedValue()).setName(input);
			listJobs.repaint();
			jobFormReady = true;
		}
	}
	
	private void UpdateJob(){
		
		if(jobFormReady){
			
			ResetStatusLabel();

			jobFormReady = false;
			
			int selected = -1;
			int size = 0;
			try{
				selected = listJobs.getSelectedIndex();
				size = jobModel.getSize();
			}catch (NullPointerException e)
			{ jobFormReady = true;
				return; 
			}
			if((selected < 0) || (size == 0)){
				jobFormReady = false;
				ResetJobForm();
				return;
			}
			
			Double WebWidth;
			Double UnwindCore;
			Double UnwindAmount;
			Double FlagRate;
			Double TotalAmount;
			Double RewindCore;
			Double RewindAmount;
			Double SlitWidth;
			Double speed_limit;
			int Slits;
			
			// Get selected
			Job j = (Job) listJobs.getSelectedValue();
			//Job j = (Job) jobModel.getElementAt(job_index);
			
			// Update name
			UpdateJobName();
			
			// Update material
			Material material = new Material((Material) cmbMaterials.getSelectedItem());
			try{
				material.thickness = (metric ? Double.parseDouble(txtThickness.getText()) : Core.MilToMicro(Double.parseDouble(txtThickness.getText())));
				if(lblGsm.getText().equals("(gsm)"))
					material.density = Double.parseDouble(txtDensity.getText());
				else
					material.density = Double.parseDouble(txtDensity.getText()) / material.thickness;
			}catch (NumberFormatException e){
				material.density = 0;
				material.thickness = 0;
			}
			
			// Update unwind
			try{
				WebWidth = (metric ? Double.parseDouble(txtWebWidth.getText()) : Core.InToMM(Double.parseDouble(txtWebWidth.getText())));
				UnwindCore = (metric ? Double.parseDouble((((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getText())) : Core.InToMM(Double.parseDouble((((JTextField) cmbUnwindCore.getEditor().getEditorComponent()).getText()))));
				UnwindCore += Consts.UNWIND_CORE_THICKNESS;
				//WebWidth = Double.parseDouble(cmbWebWidth.getSelectedItem().toString());
				//UnwindCore = Double.parseDouble(cmbUnwindCore.getSelectedItem().toString());
				UnwindAmount = Double.parseDouble(txtUnwindAmount.getText());
				FlagRate = Double.parseDouble(txtFlagCount.getText());
			}catch (NumberFormatException e){
				WebWidth = 0.;
				UnwindCore = 0.;
				UnwindAmount = 0.;
				FlagRate = 0.;
			}
			
			Double UnwindLength = 0., UnwindWeight = 0., UnwindDiam = 0.;
			
			if(cmbUnwindType.getSelectedIndex() == 0){
				// Length
				UnwindLength = (metric ? UnwindAmount : Core.FtToM(UnwindAmount));
				UnwindWeight = Core.LengthToWeight(UnwindLength, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
				UnwindDiam = 1000*Core.LengthToDiam(UnwindLength, 0.000001*material.thickness, 0.001*UnwindCore);
			}
			else if(cmbUnwindType.getSelectedIndex() == 1){
				// Weight
				UnwindWeight = (metric ? UnwindAmount : Core.TonToKg(UnwindAmount));
				UnwindLength = Core.WeightToLength(UnwindWeight,0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
				UnwindDiam = 1000*Core.LengthToDiam(UnwindLength, 0.000001*material.thickness, 0.001*UnwindCore);
			}
			else if (cmbUnwindType.getSelectedIndex() == 2){
				// Diameter
				UnwindDiam = (metric ? UnwindAmount : Core.InToMM(UnwindAmount));;
				UnwindLength = Core.DiamToLength(0.001*UnwindDiam, 0.000001*material.thickness, 0.001*UnwindCore);
				UnwindWeight = Core.LengthToWeight(UnwindLength,  0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
			}
			
			// Update totals
			try{
				TotalAmount = Double.parseDouble(txtTargetTotal.getText());
			}catch(NumberFormatException e){
				TotalAmount = 0.;
			}
			Double TotalLength = 0., TotalWeight = 0.;
			if(cmbTargetTotal.getSelectedIndex() == 0){
				// Length
				TotalLength = (metric ? TotalAmount : Core.FtToM(TotalAmount));
				TotalWeight = Core.LengthToWeight(TotalLength, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
			}
			else if(cmbTargetTotal.getSelectedIndex() == 1){
				// Weight kg
				TotalWeight = (metric ? TotalAmount : Core.TonToKg(TotalAmount));
				TotalLength = Core.WeightToLength(TotalWeight, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
			}
			else if (cmbTargetTotal.getSelectedIndex() == 2){
				// Weight tonnes
				TotalWeight = (metric ? Core.TonneToKg(TotalAmount) : Core.TonToKg(TotalAmount));
				TotalLength = Core.WeightToLength(TotalWeight, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
			}
			
			// Update slits
			try{
				Slits = Integer.parseInt(txtSlits.getText());
				SlitWidth = (metric ? Double.parseDouble(txtSlitWidth.getText()) : Core.InToMM(Double.parseDouble(txtSlitWidth.getText())));
			}catch(NumberFormatException e){
				Slits = 0;
				SlitWidth = 0.;
			}
			
			// Update rewind
			try{
				RewindCore = (metric ? Double.parseDouble((((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getText())) : Core.InToMM(Double.parseDouble((((JTextField) cmbRewindCore.getEditor().getEditorComponent()).getText()))));
				RewindCore += Consts.REWIND_CORE_THICKNESS;
				//RewindCore = Double.parseDouble(cmbRewindCore.getSelectedItem().toString());
				RewindAmount = Double.parseDouble(txtRewindAmount.getText());
			}catch(NumberFormatException e){
				RewindCore = 0.;
				RewindAmount = 0.;
			}
			Double RewindLength = 0., RewindWeight = 0., RewindDiam = 0.;
			if(cmbJobDomain.getSelectedItem().toString().equals("Set")){
				RewindWeight = (metric ? RewindAmount : Core.LbsToKg(RewindAmount));
				RewindLength = Core.WeightToLength(RewindWeight, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
				RewindDiam = 1000*Core.LengthToDiam(RewindLength, 0.000001*material.thickness, 0.001*RewindCore);
			}else{
				if(cmbRewindType.getSelectedIndex() == 0){
					// Length
					RewindLength = (metric ? RewindAmount : Core.FtToM(RewindAmount));
					RewindWeight = Core.LengthToWeight(RewindLength, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
					RewindDiam = 1000*Core.LengthToDiam(RewindLength, 0.000001*material.thickness, 0.001*RewindCore);
				}
				else if(cmbRewindType.getSelectedIndex() == 1){
					// Weight
					RewindWeight = (metric ? RewindAmount : Core.LbsToKg(RewindAmount)) * Slits;
					RewindLength = Core.WeightToLength(RewindWeight, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
					RewindDiam = 1000*Core.LengthToDiam(RewindLength, 0.000001*material.thickness, 0.001*RewindCore);
				}
				else if(cmbRewindType.getSelectedIndex() == 2){
					// Diameter
					RewindDiam = (metric ? RewindAmount : Core.InToMM(RewindAmount));
					RewindLength = Core.DiamToLength(0.001*RewindDiam, 0.000001*material.thickness, 0.001*RewindCore);
					RewindWeight = Core.LengthToWeight(RewindLength, 0.000001*material.thickness, 0.001*WebWidth, 1000*material.density);
				}
			}
			
			// Update speed limiting
			boolean limit_speed = chckbxLimitRunSpeed.isSelected();
			try{
				speed_limit = (metric ? Double.parseDouble(txtLimitRunSpeed.getText()) : Core.FtToM(Double.parseDouble(txtLimitRunSpeed.getText())));
			}catch(NumberFormatException e){
				speed_limit = 0.;
				limit_speed = false;
			}
			
			KnifeType knives = null;
			if(cmbKnifeType.getSelectedIndex() == 0)
				knives = KnifeType.RAZOR;
			else
				knives = KnifeType.ROTARY;
			
			j.update(
					material, 
					knives,
					WebWidth, 
					UnwindCore, 
					UnwindLength, 
					UnwindWeight, 
					UnwindDiam, 
					FlagRate,
					Slits, 
					SlitWidth, 
					RewindCore, 
					RewindLength, 
					RewindWeight, 
					RewindDiam, 
					TotalLength, 
					TotalWeight, 
					limit_speed, 
					speed_limit,
					cmbUnwindType.getSelectedIndex(),
					cmbRewindType.getSelectedIndex(),
					cmbTargetTotal.getSelectedIndex()
					);
			
			UpdateUnwindWarning(UnwindDiam);
			
			UpdateWebWidthWarning(WebWidth);
			
			UpdateTrim();
			
			UpdateReelCounts();
			
			jobFormReady = true;
		}
	}
	
    MouseListener mouseListener = new MouseAdapter() 
    {
    	@Override
        public void mouseClicked(MouseEvent e) 
        {
        	if(e.getClickCount() == 1){
                int position = listCompare.locationToIndex(e.getPoint());
                if(listCompare.isSelectedIndex(position))
                	listCompare.removeSelectionInterval(position, position);
                else
                	listCompare.addSelectionInterval(position, position);
        	}
        }
    	
    	@Override
    	public void mousePressed(MouseEvent e){ }
    	@Override
    	public void mouseReleased(MouseEvent e){ }
    };
    
    public String getUniqueName(String s){
    	String str = s;
    	int i = 1;
    	while(machNames.contains(str.toLowerCase())){
    		str = s + " (" + Integer.toString(i++) + ")";
    	}
    	return str;
    }
    
    public String getUniqueJobName(String s){
    	String str = s;
    	int i = 1;
    	while(jobNames.contains(str.toLowerCase())){
    		str = s + " (" + Integer.toString(i++) + ")";
    	}
    	return str;
    }
 
    private void UpdateMachineName(){
	    if(formReady){
	    	String input = txtMachName.getText();
	    	ResetStatusLabel();
			if(input.equals("")){
				ShowMessage("Please enter a machine name.");
		    	return;
			}
			
			if(machNames.contains(input.toLowerCase()) && (!(machine.name.equals(input)))){
		    	ShowMessage("Duplicate machine names not allowed. Please choose a different name.");
		    	return;
		    }
		    machNames.remove(machine.name.toLowerCase());
		    machNames.add(input.toLowerCase());
		    
		    machine.name = input;
			listMachines.repaint();
		}
	}
    
    private void UpdateForm(){
    	//ResetForm();
    	if (listMachines.getSelectedIndex() == -1) {
    		
    		
    	}else{
			
			Machine selected = (Machine) listMachines.getSelectedValue();
			txtMachName.setText(selected.name);
			switch(selected.model){
				case ER610: rdbtnClick("ER800"); break;
				case SR9DS: rdbtnClick("SR9DS"); break;
				case SR9DT: rdbtnClick("SR9DT"); break;
				case SR800: rdbtnClick("SR800"); break;
				case CUSTOM: rdbtnClick("Custom"); break;
			}
			if(!(model == Machine.Model.CUSTOM)){
				//if(cmbSpeed.isEnabled())
					cmbSpeed.setSelectedItem(Integer.toString((int)Math.round(selected.speed.orig_speed)));
				//if(cmbCorepos.isEnabled())
					cmbCorepos.setSelectedItem(WordUtils.capitalizeFully(selected.corepos.toString()));
				//if(cmbKnives.isEnabled())
					cmbKnives.setSelectedItem(selected.knives.toString());
				//if(cmbUnloader.isEnabled())
					cmbUnloader.setSelectedItem(WordUtils.capitalizeFully(selected.unloader.toString()));
				//if(cmbUnwindDrive.isEnabled())
					cmbUnwindDrive.setSelectedItem(WordUtils.capitalizeFully(selected.unwind.toString()));
				//if(cmbRewindCtrl.isEnabled())
					cmbRewindCtrl.setSelectedItem(WordUtils.capitalizeFully(selected.rewind.toString()));
				chckbxAlignmentGuide.setSelected(selected.alignment);
				chckbxAutoCutoff.setSelected(selected.cutoff);
				chckbxAutostripping.setSelected(selected.autostrip);
				chckbxAutoTapeCore.setSelected(selected.tapecore);
				chckbxAutoTapeTail.setSelected(selected.tapetail);
				chckbxExtraRewind.setSelected(selected.extrarewind);
				chckbxFlag.setSelected(selected.flags);
				chckbxRollConditioning.setSelected(selected.roll_cond);
				chckbxSpliceTable.setSelected(selected.splice_table);
				chckbxTurretSupport.setSelected(selected.turret);
			}
    	}
    }
    
    private JButton btnSaveToFile;
    private JPanel pnlPreview;
    private JLabel lblPreview;
    private JLabel lblAverageMmin;
    private JPanel pnlROI;
    private JPanel pnlCompare;
    private JPanel pnlEnviron;
    private JPanel pnlJob;
    private JPanel pnlMachine;
    private JButton btnAll;
    private JButton btnNone;
    private JLabel lblSelect;
    private JLabel lblNumericsWeight;
    private JLabel lblNumericsLength;
    private JLabel lblNumericsRate;
    private JLabel lblType;
    public JComboBox cmbGraphType;
    private JTabbedPane tabbedPane;
    
    JLabel[] labs;
    private JButton btnShowGraph;
    public JButton btnShowTimings;
    public JList listJobsAvail;
    public JList listSchedule;
    public JButton btnAddAll;
    public JTextField txtLimitRunSpeed;
    public JButton btnViewSchedule;
    public JButton btnAddJob;
    public JButton btnRemoveJob;
    public JList listJobs;
    public JComboBox cmbRewindType;
    public JCheckBox chckbxLimitRunSpeed;
    public JLabel lblLimitRunSpeed;
    public JComboBox cmbUnwindType;
    public JButton btnJobUp;
    public JButton btnJobDown;
    public JButton btnNewJob;
    public JButton btnNewMachine;
    public JRadioButton rdbtnSR800;
    public JRadioButton rdbtnCustom;
    public JLabel lblMachineConfiguration;
    public JLabel lblAddNewMachines;
    public JLabel lblJobConfiguration;
    public JButton btnJobDelete;
    public JLabel lblAddNewJobs;
    public JButton btnResetJobs;
    public JTextField txtJobName;
    public JLabel lblJobName;
    public JLabel lblJobSchedule_1;
    public JLabel lblScheduleJobsTo;
    public JLabel lblJobs;
    public JLabel lblAvailableJobs_1;
    public JLabel lblJobSchedule_2;
    public JPanel panel;
    private JLabel lblSlitWidth;
    private JLabel lblUnwindLength;
    private JLabel lblTargetRewindLength;
    public JButton btnUpSchedule;
    public JButton btnDownSchedule;
    public JButton btnClearSchedule;
    public JPanel panel_1;
    public JLabel lblTargetOutputFor;
    public JTextField txtTargetTotal;
    public JComboBox cmbTargetTotal;
    public JPanel panel_2;
    public JLabel lblProductivityComparison;
    public JLabel lblSelectMultipleMachines;
    public JLabel label_2;
    public JScrollPane scrlSchedule;
    public JPanel panel_3;
    public JLabel lblReturnOnInvestment;
    public JLabel lblSelectMultipleMachines_1;
    public JLabel lblCompareroiList;
    public JList listCompareRoi;
    public JPanel panel_4;
    public JButton btnROIselectnone;
    public JButton btnROIselectall;
    public JLabel lblROIselect;
    public JTextField txtWebWidth;
    public JMenuItem mntmUnitConverter;
    public JLabel lblEfficiency;
    public JComboBox cmbMachines;
    public JLabel lblMachine;
    public JLabel lblAverageFlags;
    public JTextField txtFlagCount;
    public JLabel lblPerRoll;
    public JLabel lblNumericsEff;
    private JMenuItem mntmOpen;
    private JMenuItem mntmSave;
    public JTabbedPane tabsROI;
    public JPanel pnlProdROI;
    public JPanel pnlEnergy;
    public JPanel pnlWaste;
    public JLabel lblThisToolIlllustrates;
    public JLabel lblInTermsOf;
    public JLabel lblThisToolHighlights;
    public JLabel lblImpactOnFinancial;
    public JLabel lblThisToolDemonstrates;
    public JPanel pnlMaint;
    public JLabel lblThisToolDemonstrates_1;
    public JLabel lblCounts;
    public JLabel lblKnifeType;
    public JComboBox cmbKnifeType;
    public JLabel lblscheduletimelbl;
    public JLabel lblscheduletime;
    public JMenuItem mntmSaveAll;
    public JButton btnDowntime;
    public JLabel lblbreakdown1;
    public JLabel lblbreakdown2;
    public JMenu mnExport;
    public JMenuItem mntmSpreadsheet;
    public JMenuItem mntmOpenAll;
    public JPanel pnlGraphProd;
    public JLabel lblSellingPrice;
    public JTextField txtsellingprice;
    public JLabel lblPerTonne;
    public JLabel lblContribution;
    public JTextField txtcontribution;
    public JLabel lblpercent;
    public JLabel lblValueAddedlbl;
    public JLabel lblvalueadded;
    public JComboBox cmbMarg1;
    public JComboBox cmbMarg2;
    public JLabel lblMarginalImprovement;
    public JLabel lblSelectMachines;
    public JLabel lblpound1;
    public JRadioButton rdbtnAveragePower;
    public JRadioButton rdbtnHourlyUsage;
    public JRadioButton rdbtnAnnualUsage;
    public JLabel lblEnergyCostl;
    public JLabel lblpound2;
    public JTextField txtenergycost;
    public JLabel lblPerKwh;
    public JTextField txtaveragepower;
    public JTextField txthourlyusage;
    public JTextField txtannualusage;
    public JComboBox cmbMachineEnergy;
    public JLabel lblMachine_1;
    public JLabel lblKw;
    public JLabel lblKwh;
    public JLabel lblKwh_1;
    public JPanel pnlGraphEnergy;
    public JLabel lblMaintenanceHoursPer;
    public JLabel lblRepairCostsPer;
    public JLabel lblPartsCostsPer;
    public JComboBox cmbMachinesmaintenance;
    public JTextField txtmaintenancehours;
    public JTextField txtmaintenanceperhour;
    public JTextField txtmaintenanceparts;
    public JLabel lbltotalmaintcost;
    public JLabel lblpound10;
    public JLabel lblpound11;
    public JPanel pnlGraphMaint;
    public JLabel lblWasteSavedPer_1;
    public JTextField txtwastesavedflags;
    public JLabel lblM_1;
    public JLabel lblWasteSavedPer_2;
    public JTextField txtwastesavedguide;
    public JLabel lblM_2;
    public JPanel pnlGraphWaste;
    public JLabel lblPleaseSetPower;
    public JPanel pnlGraphProdInner;
    public JPanel pnlGraphEnergyInner;
    public JPanel pnlGraphMaintInner;
    public JPanel pnlGraphWasteInner;
    public JLabel lblTo;
    public JLabel label;
    public JPanel panel_5;
    public JLabel lblToViewAnalysis;
    public JLabel lblToTheSchedule;
    public JLabel lblToModelMaintenance;
    public JLabel lblShiftOptionsAbove;
    public JLabel lblButNotThe;
    public JLabel lbladvancedTabIn;
    public JLabel lblMarginalEnergy;
    public JLabel label_4;
    public JComboBox cmbMargEnergy2;
    public JLabel label_5;
    public JComboBox cmbMargEnergy1;
    public JComboBox cmbMargWaste1;
    public JLabel label_1;
    public JComboBox cmbMargWaste2;
    public JLabel label_6;
    public JLabel lblMarginalWaste;
    public JComboBox cmbMargMaint1;
    public JLabel label_8;
    public JComboBox cmbMargMaint2;
    public JLabel label_9;
    public JLabel lblMarginalMaint;
    public JPanel panel_6;
    public JScrollPane scrollPane;
    public JPanel panel_7;
    public JScrollPane scrollPane_1;
    public JPanel panel_8;
    public JPanel panel_9;
    public JScrollPane scrollPane_2;
    public JScrollPane scrollPane_3;
    public JPanel panel_10;
    public JPanel panel_11;
    public JScrollPane scrollPane_4;
    public JLabel lblMarginalWasteValue;
    public JLabel lblProdLoss;
    public JLabel lblHrs_1;
    public JLabel label_10;
    public JLabel label_11;
    public JLabel label_12;
    public JLabel lblAnnualTotal;
    public JButton btnOverrideDefaultAcceleration;
    public JLabel lblProductionLoss;
    private JMenu mnSettings;
    private JMenu mnUnits;
    private JRadioButtonMenuItem rdbtnmntmImperial;
    private JRadioButtonMenuItem rdbtnmntmMetric;
    public JLabel lblGsm;
    public JLabel lblOr;
    public JCheckBox chckbxSelectAll;
    public JCheckBox chckbxSimulateScheduleStart;
    public JMenu mnTimings;
    public JMenuItem mntmSaveToFile;
    public JMenuItem mntmLoadFromFile;
    public JLabel lblhoverForInfo;


    
    public void ResetStatusLabel(){
    	lblStatus.setText(" Ready.");
    	lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblStatus.setForeground(Color.BLACK);
    }
    
    public enum Model{
    	ER610("ER610"), SR9DS("SR9-DS"), SR9DT("SR9-DT"), CUSTOM("Custom");
    	String m;
    	Model(String m){
    		this.m = m;
    	}
    }
    public enum Speed{
    	A(450), B(550), C(1000);
    	int s;
    	Speed(int j){
    		s = j;
    	}
    }
    public enum Knives{
    	Auto("Auto"),  Manual("Manual");
    	String k;
    	Knives(String l){
    		k = l;
    	}
    }
    public enum CoreposER{
    	Manual, Laser
    }
    public enum CoreposSR{
    	Manual, Laser, Servo
    }
    public enum Unloader{
    	Manual, Pneumatic, Electric
    }
    public enum Unwind{
    	Single, Double
    }
    public enum Rewind{
    	Open, Closed
    }
}