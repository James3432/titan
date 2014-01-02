package atlas.kingj.roi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import atlas.kingj.roi.Machine.Corepos;
import atlas.kingj.roi.Machine.Model;

public class OptionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton btnOk;
	private Production p;
	public JButton btnCancel;
	public JPanel pnlOptions;
	public JPanel pnlBasic;
	public JLabel lblOther_1;
	public JTextField txtFileName;
	public JTabbedPane tabs;
	public JPanel pnlViews;
	public JPanel pnlCharts;
	public JPanel pnlTimings;
	public JComboBox cmbMachineType;
	public JLabel lblMachineType;
	public JTextField txtSet1;
	public JTextField txtLoad1;
	public JTextField txtDiam1;
	public JTextField txtAlign1;
	public JTextField txtPos1;
	public JTextField txtSplice1;
	public JTextField txtCut1;
	public JTextField txtTapet1;
	public JTextField txtTapec1;
	public JTextField txtLoadr1;
	public JTextField txtUnloadr1;
	public JTextField txtAlignr1;
	public JLabel lblStandard;
	public JLabel lblWithOptions;
	public JTextField txtSet2;
	public JTextField txtLoad2;
	public JTextField txtDiam2;
	public JTextField txtAlign2;
	public JTextField txtPos2;
	public JTextField txtSplice2;
	public JTextField txtCut2;
	public JTextField txtTapet2;
	public JTextField txtTapec2;
	public JTextField txtLoadr2;
	public JTextField txtUnloadr2;
	public JTextField txtAlignLaser;
	public JLabel lblConfigureMachine;
	public JLabel lblLoadUnwind;
	public JLabel lblAlignUnwind;
	public JLabel lblPositionKnives;
	public JLabel lblSpliceRoll;
	public JLabel lblCutRewind;
	public JLabel lblTapeRewindTail;
	public JLabel lblTapeRewindCore;
	public JLabel lblLoadRewind;
	public JLabel lblUnloadRewind;
	public JLabel lblAlignRewind;
	public JLabel lbls;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_10;
	public JLabel label_11;
	public JLabel label_12;
	public JLabel label_13;
	public JLabel label_14;
	public JLabel label_15;
	public JLabel label_16;
	public JLabel label_17;
	public JLabel label_18;
	public JLabel label_19;
	public JLabel label_20;
	public JLabel label_21;
	public JLabel label_22;
	public JLabel label_23;
	public JLabel label_24;
	public JLabel label_25;
	public JLabel label_26;
	public JLabel label_27;
	public JLabel label_28;
	public JLabel label_29;
	public JLabel label_30;
	
	private JTextField[] TimingsBoxes1;
	private JTextField[] TimingsBoxes2;
	public JLabel lblChangeRewindDiam;
	public JLabel lblOptionsNotYet;
	public JPanel pnlLock;
	public JLabel lblLock;
	public JCheckBox chckbxSaveSettings;
	public JButton btnUnlock;
	public JSlider slide1;
	public JSlider slide2;
	public JSlider slide3;
	public JLabel lblPc3;
	public JLabel lblPc2;
	public JLabel lblPc1;
	public JLabel lblGlobalAdjustment;
	public JLabel lblCustomMachines;
	public JLabel lblTitanMachines;
	public JLabel lblAdvancedOptionsPlease;
	public JLabel lblAdv2;
	public JLabel lblAdv3;
	public JLabel lblResetOptions;
	public JButton btnResetAdvancedOptions;
	public JButton btnResetManualTimings;
	public JComboBox cmbResetManual;
	public JButton btnResetAutoTimings;
	public JComboBox cmbResetAuto;
	public JButton btnResetEfficiencySliders;
	public JLabel lblNoteResetsTake;
	public JLabel lblBeReverted;
	public JButton btnResetAllSettings;
	
	private Machine m;
	public JLabel lblTurretChange;
	public JLabel lblFlagCameraOffset;
	public JLabel lblRotaryKnifeMultiplier;
	public JTextField txtAlignServo;
	public JLabel label_5;
	public JTextField txtTurret1;
	public JLabel label_6;
	public JLabel lblLaser;
	public JLabel lblServo;
	public JTextField txtFlagcam;
	public JTextField txtRotKnife;
	public JTextField txtTurret2;
	public JLabel label_9;
	public JLabel lblCoreDiameter;
	public JButton btnBrowse;
	public JPanel panel;
	public JButton btnUnlock_1;
	public JLabel lblIfYouHave;
	public JLabel lblButtonToUnlock;
	public JPanel panel_1;
	public JLabel lblUseThesave;
	public JLabel lblChangesMadeIn;
	public JLabel lblNoteThatThis;
	public JLabel lblOtherDataIn;
	public JLabel lblFeatureForThis;
	public JLabel lblOperatorTimingsCan;
	public JLabel lblTabOfThis;
	
	OpenSaveFileChooser fc = new OpenSaveFileChooser();
	public JLabel lblSclamp;
	public JTextField txtSclampR;
	public JLabel lblRpm;
	public JTextField txtSclampU;
	public JLabel lblRpm_1;
	
	public class OpenSaveFileChooser extends JFileChooser {
		private static final long serialVersionUID = 1L;

		@Override
	    public void approveSelection(){
	        File f = getSelectedFile();
	        if((f.exists() || (addExt(f,".lcs").exists())) && getDialogType() == SAVE_DIALOG){
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

	/**
	 * Create the dialog.
	 */
	public OptionDialog(Production p, Machine m) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		this.p = p;
		this.m = m;
		setTitle("Options");
		try {	
			setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		} catch (NullPointerException e111) {
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 316, 552);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		btnOk = new JButton("OK");
		btnOk.setToolTipText("Save and close");
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnOK();
			}
		});
		btnOk.setBounds(140, 494, 75, 25);
		getContentPane().add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(225, 494, 75, 25);
		getContentPane().add(btnCancel);
		
		tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setBounds(10, 11, 290, 480);
		getContentPane().add(tabs);
		
		pnlOptions = new JPanel();
		tabs.addTab("System", null, pnlOptions, null);
		pnlOptions.setBackground(Color.WHITE);
		pnlOptions.setLayout(null);
		
		pnlBasic = new JPanel();
		pnlBasic.setLayout(null);
		pnlBasic.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Basic Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlBasic.setBackground(Color.WHITE);
		pnlBasic.setBounds(10, 11, 266, 114);
		pnlOptions.add(pnlBasic);
		
		lblOther_1 = new JLabel("Save location:");
		lblOther_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOther_1.setBounds(10, 54, 128, 14);
		pnlBasic.add(lblOther_1);
		
		txtFileName = new JTextField();
		txtFileName.setToolTipText("Enter location for the save file");
		txtFileName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UpdateFilename();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateFilename();
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		txtFileName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*if(!txtFileName.isEnabled()){
					txtFileName.setEnabled(false/*true);
					chckbxSaveSettings.setSelected(true);
				}*/
			}
		});
		txtFileName.setText(p.SaveFileName);
		txtFileName.setColumns(10);
		txtFileName.setBounds(10, 70, 155, 23);
		pnlBasic.add(txtFileName);
		
		chckbxSaveSettings = new JCheckBox("Save settings on close");
		chckbxSaveSettings.setToolTipText("Save settings on close");
		chckbxSaveSettings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxSaveSettings.isSelected()){
					txtFileName.setEnabled(true);
				}else{
					txtFileName.setEnabled(false);
				}
			}
		});
		chckbxSaveSettings.setSelected(p.SaveOnClose);
		chckbxSaveSettings.setBackground(Color.WHITE);
		chckbxSaveSettings.setBounds(10, 24, 155, 23);
		pnlBasic.add(chckbxSaveSettings);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowseFiles();
			}
		});
		btnBrowse.setToolTipText("Browse hard disk for location");
		btnBrowse.setBounds(167, 70, 89, 23);
		pnlBasic.add(btnBrowse);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Unlock Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 136, 265, 101);
		pnlOptions.add(panel);
		panel.setLayout(null);
		
		btnUnlock_1 = new JButton("Unlock");
		btnUnlock_1.setToolTipText("Unlock all administrator privileges");
		btnUnlock_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenPasswordDialog();
			}
		});
		btnUnlock_1.setBounds(90, 61, 89, 23);
		panel.add(btnUnlock_1);
		
		lblIfYouHave = new JLabel("If you have the administrator password, use this");
		lblIfYouHave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIfYouHave.setBounds(10, 24, 245, 14);
		panel.add(lblIfYouHave);
		
		lblButtonToUnlock = new JLabel("button to unlock the advanced options and timings:");
		lblButtonToUnlock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblButtonToUnlock.setBounds(10, 38, 255, 14);
		panel.add(lblButtonToUnlock);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Help", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 248, 265, 143);
		pnlOptions.add(panel_1);
		panel_1.setLayout(null);
		
		lblUseThesave = new JLabel("Use the \"save settings\" option above to store all");
		lblUseThesave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUseThesave.setBounds(12, 23, 245, 14);
		panel_1.add(lblUseThesave);
		
		lblChangesMadeIn = new JLabel("changes made in this window to the hard disk.");
		lblChangesMadeIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChangesMadeIn.setBounds(12, 38, 245, 14);
		panel_1.add(lblChangesMadeIn);
		
		lblNoteThatThis = new JLabel("Note that this will not save any machines, jobs, or");
		lblNoteThatThis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNoteThatThis.setBounds(12, 53, 245, 14);
		panel_1.add(lblNoteThatThis);
		
		lblOtherDataIn = new JLabel("other data in the main program (use the \"Save All\"");
		lblOtherDataIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOtherDataIn.setBounds(12, 68, 245, 14);
		panel_1.add(lblOtherDataIn);
		
		lblFeatureForThis = new JLabel("feature for this).");
		lblFeatureForThis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFeatureForThis.setHorizontalAlignment(SwingConstants.LEFT);
		lblFeatureForThis.setBounds(12, 83, 219, 14);
		panel_1.add(lblFeatureForThis);
		
		lblOperatorTimingsCan = new JLabel("Operator timings can be configured in the second");
		lblOperatorTimingsCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOperatorTimingsCan.setBounds(12, 102, 245, 14);
		panel_1.add(lblOperatorTimingsCan);
		
		lblTabOfThis = new JLabel("tab of this window.");
		lblTabOfThis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTabOfThis.setBounds(12, 117, 245, 14);
		panel_1.add(lblTabOfThis);
		
		pnlTimings = new JPanel();
		pnlTimings.setBackground(Color.WHITE);
		tabs.addTab("Timings", null, pnlTimings, null);
		pnlTimings.setLayout(null);
		
		cmbMachineType = new JComboBox();
		cmbMachineType.setFont(new Font("Tahoma", Font.BOLD, 11));
		cmbMachineType.setToolTipText("Select the machine type to edit");
		cmbMachineType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				SaveTimings();
			}
		});
		cmbMachineType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTimeOptions();
			}
		});
		cmbMachineType.setModel(new DefaultComboBoxModel(new String[] {"ER610", "SR9-DS", "SR9-DT", "SR800"/*, "Custom"*/}));
		cmbMachineType.setBounds(112, 11, 146, 20);
		pnlTimings.add(cmbMachineType);
		
		lblMachineType = new JLabel("Machine Type:");
		lblMachineType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMachineType.setToolTipText("Select the machine type to edit");
		lblMachineType.setBounds(10, 14, 92, 14);
		pnlTimings.add(lblMachineType);
		
		txtSet1 = new JTextField();
		txtSet1.setEnabled(false);
		txtSet1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtSet1.setToolTipText("TOTAL");
		txtSet1.setBounds(112, 62, 61, 20);
		pnlTimings.add(txtSet1);
		txtSet1.setColumns(10);
		
		txtLoad1 = new JTextField();
		txtLoad1.setEnabled(false);
		txtLoad1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtLoad1.setToolTipText("TOTAL");
		txtLoad1.setColumns(10);
		txtLoad1.setBounds(112, 84, 61, 20);
		pnlTimings.add(txtLoad1);
		
		txtDiam1 = new JTextField();
		txtDiam1.setEnabled(false);
		txtDiam1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtDiam1.setToolTipText("TOTAL");
		txtDiam1.setColumns(10);
		txtDiam1.setBounds(112, 282, 61, 20);
		pnlTimings.add(txtDiam1);
		
		txtAlign1 = new JTextField();
		txtAlign1.setEnabled(false);
		txtAlign1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtAlign1.setToolTipText("TOTAL");
		txtAlign1.setColumns(10);
		txtAlign1.setBounds(112, 106, 61, 20);
		pnlTimings.add(txtAlign1);
		
		txtPos1 = new JTextField();
		txtPos1.setEnabled(false);
		txtPos1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtPos1.setToolTipText("PER KNIFE");
		txtPos1.setColumns(10);
		txtPos1.setBounds(112, 128, 61, 20);
		pnlTimings.add(txtPos1);
		
		txtSplice1 = new JTextField();
		txtSplice1.setEnabled(false);
		txtSplice1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtSplice1.setToolTipText("TOTAL");
		txtSplice1.setColumns(10);
		txtSplice1.setBounds(112, 150, 61, 20);
		pnlTimings.add(txtSplice1);
		
		txtCut1 = new JTextField();
		txtCut1.setEnabled(false);
		txtCut1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtCut1.setToolTipText("PER REEL");
		txtCut1.setColumns(10);
		txtCut1.setBounds(112, 172, 61, 20);
		pnlTimings.add(txtCut1);
		
		txtTapet1 = new JTextField();
		txtTapet1.setEnabled(false);
		txtTapet1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtTapet1.setToolTipText("PER REEL");
		txtTapet1.setColumns(10);
		txtTapet1.setBounds(112, 194, 61, 20);
		pnlTimings.add(txtTapet1);
		
		txtTapec1 = new JTextField();
		txtTapec1.setEnabled(false);
		txtTapec1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtTapec1.setToolTipText("PER CORE");
		txtTapec1.setColumns(10);
		txtTapec1.setBounds(112, 216, 61, 20);
		pnlTimings.add(txtTapec1);
		
		txtLoadr1 = new JTextField();
		txtLoadr1.setEnabled(false);
		txtLoadr1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtLoadr1.setToolTipText("PER CORE");
		txtLoadr1.setColumns(10);
		txtLoadr1.setBounds(112, 238, 61, 20);
		pnlTimings.add(txtLoadr1);
		
		txtUnloadr1 = new JTextField();
		txtUnloadr1.setEnabled(false);
		txtUnloadr1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtUnloadr1.setToolTipText("PER REEL");
		txtUnloadr1.setColumns(10);
		txtUnloadr1.setBounds(112, 260, 61, 20);
		pnlTimings.add(txtUnloadr1);
		
		txtAlignr1 = new JTextField();
		txtAlignr1.setEnabled(false);
		txtAlignr1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtAlignr1.setToolTipText("PER CORE");
		txtAlignr1.setColumns(10);
		txtAlignr1.setBounds(112, 326, 61, 20);
		pnlTimings.add(txtAlignr1);
		
		lblStandard = new JLabel("Manual");
		lblStandard.setEnabled(false);
		lblStandard.setToolTipText("Manual timings");
		lblStandard.setBounds(112, 42, 46, 14);
		pnlTimings.add(lblStandard);
		
		lblWithOptions = new JLabel("Auto/upgraded");
		lblWithOptions.setEnabled(false);
		lblWithOptions.setToolTipText("Timings with automation (locked by default)");
		lblWithOptions.setBounds(197, 42, 88, 14);
		pnlTimings.add(lblWithOptions);
		
		txtSet2 = new JTextField();
		txtSet2.setToolTipText("TOTAL");
		txtSet2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtSet2.setEnabled(false);
		txtSet2.setColumns(10);
		txtSet2.setBounds(197, 62, 61, 20);
		pnlTimings.add(txtSet2);
		
		txtLoad2 = new JTextField();
		txtLoad2.setToolTipText("TOTAL");
		txtLoad2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtLoad2.setEnabled(false);
		txtLoad2.setColumns(10);
		txtLoad2.setBounds(197, 84, 61, 20);
		pnlTimings.add(txtLoad2);
		
		txtDiam2 = new JTextField();
		txtDiam2.setToolTipText("TOTAL");
		txtDiam2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtDiam2.setEnabled(false);
		txtDiam2.setColumns(10);
		txtDiam2.setBounds(197, 282, 61, 20);
		pnlTimings.add(txtDiam2);
		
		txtAlign2 = new JTextField();
		txtAlign2.setToolTipText("TOTAL");
		txtAlign2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtAlign2.setEnabled(false);
		txtAlign2.setColumns(10);
		txtAlign2.setBounds(197, 106, 61, 20);
		pnlTimings.add(txtAlign2);
		
		txtPos2 = new JTextField();
		txtPos2.setToolTipText("PER KNIFE");
		txtPos2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtPos2.setEnabled(false);
		txtPos2.setColumns(10);
		txtPos2.setBounds(197, 128, 61, 20);
		pnlTimings.add(txtPos2);
		
		txtSplice2 = new JTextField();
		txtSplice2.setToolTipText("TOTAL");
		txtSplice2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		
		txtSplice2.setEnabled(false);
		txtSplice2.setColumns(10);
		txtSplice2.setBounds(197, 150, 61, 20);
		pnlTimings.add(txtSplice2);
		
		txtCut2 = new JTextField();
		txtCut2.setToolTipText("TOTAL");
		txtCut2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtCut2.setEnabled(false);
		txtCut2.setColumns(10);
		txtCut2.setBounds(197, 172, 61, 20);
		pnlTimings.add(txtCut2);
		
		txtTapet2 = new JTextField();
		txtTapet2.setToolTipText("TOTAL");
		txtTapet2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtTapet2.setEnabled(false);
		txtTapet2.setColumns(10);
		txtTapet2.setBounds(197, 194, 61, 20);
		pnlTimings.add(txtTapet2);
		
		txtTapec2 = new JTextField();
		txtTapec2.setToolTipText("TOTAL");
		txtTapec2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtTapec2.setEnabled(false);
		txtTapec2.setColumns(10);
		txtTapec2.setBounds(197, 216, 61, 20);
		pnlTimings.add(txtTapec2);
		
		txtLoadr2 = new JTextField();
		txtLoadr2.setToolTipText("PER CORE");
		txtLoadr2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtLoadr2.setEnabled(false);
		txtLoadr2.setColumns(10);
		txtLoadr2.setBounds(197, 238, 61, 20);
		pnlTimings.add(txtLoadr2);
		
		txtUnloadr2 = new JTextField();
		txtUnloadr2.setToolTipText("TOTAL");
		txtUnloadr2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtUnloadr2.setEnabled(false);
		txtUnloadr2.setColumns(10);
		txtUnloadr2.setBounds(197, 260, 61, 20);
		pnlTimings.add(txtUnloadr2);
		
		txtAlignLaser = new JTextField();
		txtAlignLaser.setHorizontalAlignment(SwingConstants.CENTER);
		txtAlignLaser.setToolTipText("PER CORE");
		txtAlignLaser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtAlignLaser.setEnabled(false);
		txtAlignLaser.setColumns(10);
		txtAlignLaser.setBounds(226, 326, 32, 20);
		pnlTimings.add(txtAlignLaser);
		
		lblConfigureMachine = new JLabel("Configure machine:");
		lblConfigureMachine.setEnabled(false);
		lblConfigureMachine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfigureMachine.setToolTipText("Time taken to enter job settings into the machine");
		lblConfigureMachine.setBounds(10, 65, 95, 14);
		pnlTimings.add(lblConfigureMachine);
		
		lblLoadUnwind = new JLabel("Mother change:");
		lblLoadUnwind.setEnabled(false);
		lblLoadUnwind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadUnwind.setToolTipText("Total time taken to change a mother roll");
		lblLoadUnwind.setBounds(10, 87, 92, 14);
		pnlTimings.add(lblLoadUnwind);
		
		lblAlignUnwind = new JLabel("Adjust/align mother:");
		lblAlignUnwind.setEnabled(false);
		lblAlignUnwind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAlignUnwind.setToolTipText("Time to adjust and align a new mother roll after it is loaded");
		lblAlignUnwind.setBounds(10, 109, 105, 14);
		pnlTimings.add(lblAlignUnwind);
		
		lblPositionKnives = new JLabel("Align razor knives:");
		lblPositionKnives.setEnabled(false);
		lblPositionKnives.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPositionKnives.setToolTipText("The time taken to position for a new job");
		lblPositionKnives.setBounds(10, 131, 92, 14);
		pnlTimings.add(lblPositionKnives);
		
		lblSpliceRoll = new JLabel("Complete splice:");
		lblSpliceRoll.setEnabled(false);
		lblSpliceRoll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpliceRoll.setToolTipText("Time taken to detect a flag and splice the web, excluding ramp up/down");
		lblSpliceRoll.setBounds(10, 153, 92, 14);
		pnlTimings.add(lblSpliceRoll);
		
		lblCutRewind = new JLabel("Cut reel:");
		lblCutRewind.setEnabled(false);
		lblCutRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCutRewind.setToolTipText("Time to cut the web after a run");
		lblCutRewind.setBounds(10, 175, 92, 14);
		pnlTimings.add(lblCutRewind);
		
		lblTapeRewindTail = new JLabel("Tape reel tail:");
		lblTapeRewindTail.setEnabled(false);
		lblTapeRewindTail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTapeRewindTail.setToolTipText("Time to tape down tails");
		lblTapeRewindTail.setBounds(10, 197, 92, 14);
		pnlTimings.add(lblTapeRewindTail);
		
		lblTapeRewindCore = new JLabel("Tape reel core:");
		lblTapeRewindCore.setEnabled(false);
		lblTapeRewindCore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTapeRewindCore.setToolTipText("Time to apply tape to new cores");
		lblTapeRewindCore.setBounds(10, 219, 92, 14);
		pnlTimings.add(lblTapeRewindCore);
		
		lblLoadRewind = new JLabel("Load core:");
		lblLoadRewind.setEnabled(false);
		lblLoadRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadRewind.setToolTipText("Time to load a new reel before a run");
		lblLoadRewind.setBounds(10, 241, 92, 14);
		pnlTimings.add(lblLoadRewind);
		
		lblUnloadRewind = new JLabel("Unload reel:");
		lblUnloadRewind.setEnabled(false);
		lblUnloadRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnloadRewind.setToolTipText("Time to unload completed reel from shaft");
		lblUnloadRewind.setBounds(10, 263, 92, 14);
		pnlTimings.add(lblUnloadRewind);
		
		lblAlignRewind = new JLabel("Align core:");
		lblAlignRewind.setEnabled(false);
		lblAlignRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAlignRewind.setToolTipText("Time to align a new core");
		lblAlignRewind.setBounds(10, 329, 92, 14);
		pnlTimings.add(lblAlignRewind);
		
		lbls = new JLabel("(s)");
		lbls.setEnabled(false);
		lbls.setBounds(259, 65, 16, 14);
		pnlTimings.add(lbls);
		
		label_7 = new JLabel("(s)");
		label_7.setEnabled(false);
		label_7.setBounds(259, 87, 16, 14);
		pnlTimings.add(label_7);
		
		label_8 = new JLabel("(s)");
		label_8.setEnabled(false);
		label_8.setBounds(259, 285, 16, 14);
		pnlTimings.add(label_8);
		
		label_10 = new JLabel("(s)");
		label_10.setEnabled(false);
		label_10.setBounds(259, 109, 16, 14);
		pnlTimings.add(label_10);
		
		label_11 = new JLabel("(s)");
		label_11.setEnabled(false);
		label_11.setBounds(259, 131, 16, 14);
		pnlTimings.add(label_11);
		
		label_12 = new JLabel("(s)");
		label_12.setEnabled(false);
		label_12.setBounds(259, 153, 16, 14);
		pnlTimings.add(label_12);
		
		label_13 = new JLabel("(s)");
		label_13.setEnabled(false);
		label_13.setBounds(259, 175, 16, 14);
		pnlTimings.add(label_13);
		
		label_14 = new JLabel("(s)");
		label_14.setEnabled(false);
		label_14.setBounds(259, 197, 16, 14);
		pnlTimings.add(label_14);
		
		label_15 = new JLabel("(s)");
		label_15.setEnabled(false);
		label_15.setBounds(259, 219, 16, 14);
		pnlTimings.add(label_15);
		
		label_16 = new JLabel("(s)");
		label_16.setEnabled(false);
		label_16.setBounds(259, 241, 16, 14);
		pnlTimings.add(label_16);
		
		label_17 = new JLabel("(s)");
		label_17.setEnabled(false);
		label_17.setBounds(259, 263, 16, 14);
		pnlTimings.add(label_17);
		
		label_18 = new JLabel("(s)");
		label_18.setEnabled(false);
		label_18.setBounds(259, 329, 16, 14);
		pnlTimings.add(label_18);
		
		label_19 = new JLabel("(s)");
		label_19.setEnabled(false);
		label_19.setBounds(174, 65, 16, 14);
		pnlTimings.add(label_19);
		
		label_20 = new JLabel("(s)");
		label_20.setEnabled(false);
		label_20.setBounds(174, 87, 16, 14);
		pnlTimings.add(label_20);
		
		label_21 = new JLabel("(s)");
		label_21.setEnabled(false);
		label_21.setBounds(174, 285, 16, 14);
		pnlTimings.add(label_21);
		
		label_22 = new JLabel("(s)");
		label_22.setEnabled(false);
		label_22.setBounds(174, 109, 16, 14);
		pnlTimings.add(label_22);
		
		label_23 = new JLabel("(s)");
		label_23.setEnabled(false);
		label_23.setBounds(259, 351, 16, 14);
		pnlTimings.add(label_23);
		
		label_24 = new JLabel("(s)");
		label_24.setEnabled(false);
		label_24.setBounds(174, 153, 16, 14);
		pnlTimings.add(label_24);
		
		label_25 = new JLabel("(s)");
		label_25.setEnabled(false);
		label_25.setBounds(174, 241, 16, 14);
		pnlTimings.add(label_25);
		
		label_26 = new JLabel("(s)");
		label_26.setEnabled(false);
		label_26.setBounds(174, 131, 16, 14);
		pnlTimings.add(label_26);
		
		label_27 = new JLabel("(s)");
		label_27.setEnabled(false);
		label_27.setBounds(174, 175, 16, 14);
		pnlTimings.add(label_27);
		
		label_28 = new JLabel("(s)");
		label_28.setEnabled(false);
		label_28.setBounds(174, 197, 16, 14);
		pnlTimings.add(label_28);
		
		label_29 = new JLabel("(s)");
		label_29.setEnabled(false);
		label_29.setBounds(174, 219, 16, 14);
		pnlTimings.add(label_29);
		
		label_30 = new JLabel("(s)");
		label_30.setEnabled(false);
		label_30.setBounds(174, 263, 16, 14);
		pnlTimings.add(label_30);
		
		lblChangeRewindDiam = new JLabel("Change rewind");
		lblChangeRewindDiam.setEnabled(false);
		lblChangeRewindDiam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChangeRewindDiam.setToolTipText("Total time to change the rewind core diameter on all shafts");
		lblChangeRewindDiam.setBounds(10, 278, 105, 14);
		pnlTimings.add(lblChangeRewindDiam);
		
		pnlViews = new JPanel();
		pnlViews.setBackground(Color.WHITE);
		tabs.addTab("Advanced", null, pnlViews, null);
		pnlViews.setLayout(null);
		
		lblOptionsNotYet = new JLabel("These options are locked");
		lblOptionsNotYet.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOptionsNotYet.setForeground(Color.RED);
		lblOptionsNotYet.setBounds(75, 25, 120, 14);
		pnlViews.add(lblOptionsNotYet);
		
		btnUnlock = new JButton("Unlock");
		btnUnlock.setToolTipText("Unlock advanced options");
		btnUnlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenPasswordDialog();
			}
		});
		btnUnlock.setBounds(90, 50, 90, 23);
		pnlViews.add(btnUnlock);
		
		slide1 = new JSlider();
		slide1.setToolTipText("Slide to adjust");
		slide1.setMaximum(1500);
		slide1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(slide1.isVisible()){
					try{
						lblPc1.setText(Double.toString(roundTwoDecimals(slide1.getValue() / 10.)) + "%");
					}catch(NullPointerException e2){}
				}
			}
		});
		slide1.setVisible(false);
		slide1.setValue((int)(p.TitanEfficiency*1000));
		slide1.setMinorTickSpacing(100);
		slide1.setPaintTicks(true);
		slide1.setBackground(Color.WHITE);
		slide1.setBounds(29, 120, 225, 36);
		pnlViews.add(slide1);
		
		slide2 = new JSlider();
		slide2.setToolTipText("Slide to adjust");
		slide2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(slide2.isVisible()){
					try{
						lblPc2.setText(Double.toString(roundTwoDecimals(slide2.getValue() / 10.)) + "%");
					}catch(NullPointerException e2){}
				}
			}
		});
		slide2.setMaximum(1500);
		slide2.setVisible(false);
		slide2.setValue((int)(p.CustomEfficiency*1000));
		slide2.setMinorTickSpacing(100);
		slide2.setPaintTicks(true);
		slide2.setBackground(Color.WHITE);
		slide2.setBounds(29, 190, 225, 36);
		pnlViews.add(slide2);
		
		slide3 = new JSlider();
		slide3.setToolTipText("Slide to adjust");
		slide3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(slide3.isVisible()){
					try{
						lblPc3.setText(Double.toString(roundTwoDecimals(slide3.getValue() / 10.)) + "%");
					}catch(NullPointerException e2){}
				}
			}
		});
		slide3.setMaximum(1500);
		slide3.setVisible(false);
		slide3.setMinorTickSpacing(100);
		slide3.setPaintTicks(true);
		slide3.setValue((int)(p.GlobalEfficiency*1000));
		slide3.setBackground(Color.WHITE);
		slide3.setBounds(29, 260, 225, 36);
		pnlViews.add(slide3);
		
		lblPc3 = new JLabel(Double.toString(roundTwoDecimals(p.GlobalEfficiency*100)) + "%");
		lblPc3.setVisible(false);
		lblPc3.setBounds(210, 240, 46, 14);
		pnlViews.add(lblPc3);
		
		lblPc2 = new JLabel(Double.toString(roundTwoDecimals(p.CustomEfficiency*100)) + "%");
		lblPc2.setVisible(false);
		lblPc2.setBounds(210, 170, 46, 14);
		pnlViews.add(lblPc2);
		
		lblPc1 = new JLabel(Double.toString(roundTwoDecimals(p.TitanEfficiency*100)) + "%");
		lblPc1.setVisible(false);
		lblPc1.setBounds(210, 100, 46, 14);
		pnlViews.add(lblPc1);
		
		lblGlobalAdjustment = new JLabel("Global adjustment:");
		lblGlobalAdjustment.setVisible(false);
		lblGlobalAdjustment.setBounds(29, 240, 126, 14);
		pnlViews.add(lblGlobalAdjustment);
		
		lblCustomMachines = new JLabel("Custom machines:");
		lblCustomMachines.setVisible(false);
		lblCustomMachines.setBounds(29, 170, 126, 14);
		pnlViews.add(lblCustomMachines);
		
		lblTitanMachines = new JLabel("Titan machines:");
		lblTitanMachines.setVisible(false);
		lblTitanMachines.setBounds(29, 100, 97, 14);
		pnlViews.add(lblTitanMachines);
		
		lblAdvancedOptionsPlease = new JLabel("Efficiency factor adjustment");
		lblAdvancedOptionsPlease.setVisible(false);
		lblAdvancedOptionsPlease.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdvancedOptionsPlease.setBounds(29, 23, 221, 18);
		pnlViews.add(lblAdvancedOptionsPlease);
		
		lblAdv2 = new JLabel("Adjust these sliders to change the efficiency");
		lblAdv2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAdv2.setVisible(false);
		lblAdv2.setForeground(Color.GRAY);
		lblAdv2.setBounds(29, 50, 216, 14);
		pnlViews.add(lblAdv2);
		
		lblAdv3 = new JLabel("scaling factors applied to all calculations.");
		lblAdv3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAdv3.setVisible(false);
		lblAdv3.setForeground(Color.GRAY);
		lblAdv3.setBounds(29, 65, 216, 14);
		pnlViews.add(lblAdv3);
		
		pnlCharts = new JPanel();
		pnlCharts.setBackground(Color.WHITE);
		tabs.addTab("Reset", null, pnlCharts, null);
		pnlCharts.setLayout(null);
		
		lblResetOptions = new JLabel("Reset options");
		lblResetOptions.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResetOptions.setBounds(29, 23, 189, 18);
		pnlCharts.add(lblResetOptions);
		
		btnResetAdvancedOptions = new JButton("Reset save settings");
		btnResetAdvancedOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetAdvanced();
			}
		});
		btnResetAdvancedOptions.setToolTipText("Reset save settings");
		btnResetAdvancedOptions.setBounds(29, 64, 225, 23);
		pnlCharts.add(btnResetAdvancedOptions);
		
		btnResetManualTimings = new JButton("Reset manual timings");
		btnResetManualTimings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!btnResetManualTimings.isEnabled())
					OpenPasswordDialog();
			}
		});
		btnResetManualTimings.setEnabled(false);
		btnResetManualTimings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnResetManualTimings.setToolTipText("Reset manual timings for the specified machine");
		btnResetManualTimings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetManual();
			}
		});
		btnResetManualTimings.setBounds(29, 107, 133, 23);
		pnlCharts.add(btnResetManualTimings);
		
		cmbResetManual = new JComboBox();
		cmbResetManual.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cmbResetManual.isEnabled())
					OpenPasswordDialog();
			}
		});
		cmbResetManual.setEnabled(false);
		cmbResetManual.setToolTipText("Select a machine to reset timings");
		cmbResetManual.setModel(new DefaultComboBoxModel(new String[] {"All", "ER610", "SR9-DS", "SR9-DT", "SR800"}));
		cmbResetManual.setBounds(172, 107, 82, 23);
		pnlCharts.add(cmbResetManual);
		
		btnResetAutoTimings = new JButton("Reset auto timings");
		btnResetAutoTimings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!btnResetAutoTimings.isEnabled())
					OpenPasswordDialog();
			}
		});
		btnResetAutoTimings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnResetAutoTimings.setToolTipText("Reset automatic timings for the specified machine");
		btnResetAutoTimings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetAuto();
			}
		});
		btnResetAutoTimings.setEnabled(false);
		btnResetAutoTimings.setBounds(29, 141, 133, 23);
		pnlCharts.add(btnResetAutoTimings);
		
		cmbResetAuto = new JComboBox();
		cmbResetAuto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cmbResetAuto.isEnabled())
					OpenPasswordDialog();
			}
		});
		cmbResetAuto.setToolTipText("Select a machine to reset timings");
		cmbResetAuto.setEnabled(false);
		cmbResetAuto.setModel(new DefaultComboBoxModel(new String[] {"All", "ER610", "SR9-DS", "SR9-DT", "SR800"}));
		cmbResetAuto.setBounds(172, 141, 82, 23);
		pnlCharts.add(cmbResetAuto);
		
		btnResetEfficiencySliders = new JButton("Reset efficiency sliders");
		btnResetEfficiencySliders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!btnResetEfficiencySliders.isEnabled())
					OpenPasswordDialog();
			}
		});
		btnResetEfficiencySliders.setToolTipText("Reset all efficiency values");
		btnResetEfficiencySliders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResetSliders();
			}
		});
		btnResetEfficiencySliders.setEnabled(false);
		btnResetEfficiencySliders.setBounds(29, 184, 225, 23);
		pnlCharts.add(btnResetEfficiencySliders);
		
		lblNoteResetsTake = new JLabel("Note: resets take place instantly and cannot");
		lblNoteResetsTake.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNoteResetsTake.setForeground(Color.GRAY);
		lblNoteResetsTake.setBounds(29, 279, 225, 14);
		pnlCharts.add(lblNoteResetsTake);
		
		lblBeReverted = new JLabel("be reverted.");
		lblBeReverted.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBeReverted.setForeground(Color.GRAY);
		lblBeReverted.setBounds(29, 295, 82, 14);
		pnlCharts.add(lblBeReverted);
		
		btnResetAllSettings = new JButton("Reset all settings");
		btnResetAllSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResetHelp();
			}
		});
		btnResetAllSettings.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnResetAllSettings.setBounds(29, 231, 225, 23);
		pnlCharts.add(btnResetAllSettings);
		
		lblTurretChange = new JLabel("Turret change:");
		lblTurretChange.setEnabled(false);
		lblTurretChange.setToolTipText("Time for turret to rotate (turretted machines only)");
		lblTurretChange.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTurretChange.setBounds(10, 307, 95, 14);
		pnlTimings.add(lblTurretChange);
		
		lblFlagCameraOffset = new JLabel("Flag camera speedup:");
		lblFlagCameraOffset.setEnabled(false);
		lblFlagCameraOffset.setToolTipText("Time saved in seconds by the flag camera, per flag");
		lblFlagCameraOffset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFlagCameraOffset.setBounds(10, 379, 106, 14);
		pnlTimings.add(lblFlagCameraOffset);
		
		lblRotaryKnifeMultiplier = new JLabel("Rotary knife multiplier:");
		lblRotaryKnifeMultiplier.setEnabled(false);
		lblRotaryKnifeMultiplier.setToolTipText("Factor of extra time taken to manually change rotary, rather than razor, knives");
		lblRotaryKnifeMultiplier.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRotaryKnifeMultiplier.setBounds(146, 379, 108, 14);
		pnlTimings.add(lblRotaryKnifeMultiplier);
		
		txtAlignServo = new JTextField();
		txtAlignServo.setHorizontalAlignment(SwingConstants.CENTER);
		txtAlignServo.setToolTipText("PER CORE");
		txtAlignServo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtAlignServo.setEnabled(false);
		txtAlignServo.setColumns(10);
		txtAlignServo.setBounds(226, 348, 32, 20);
		pnlTimings.add(txtAlignServo);
		
		label_5 = new JLabel("(s)");
		label_5.setEnabled(false);
		label_5.setBounds(174, 329, 16, 14);
		pnlTimings.add(label_5);
		
		txtTurret1 = new JTextField();
		txtTurret1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtTurret1.setEnabled(false);
		txtTurret1.setToolTipText("TOTAL");
		txtTurret1.setColumns(10);
		txtTurret1.setBounds(112, 304, 61, 20);
		pnlTimings.add(txtTurret1);
		
		label_6 = new JLabel("(s)");
		label_6.setEnabled(false);
		label_6.setBounds(174, 307, 16, 14);
		pnlTimings.add(label_6);
		
		lblLaser = new JLabel("laser");
		lblLaser.setEnabled(false);
		lblLaser.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLaser.setBounds(197, 329, 23, 14);
		pnlTimings.add(lblLaser);
		
		lblServo = new JLabel("servo");
		lblServo.setEnabled(false);
		lblServo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblServo.setBounds(197, 350, 27, 14);
		pnlTimings.add(lblServo);
		
		txtFlagcam = new JTextField();
		txtFlagcam.setHorizontalAlignment(SwingConstants.CENTER);
		txtFlagcam.setToolTipText("PER FLAG");
		txtFlagcam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtFlagcam.setEnabled(false);
		txtFlagcam.setColumns(10);
		txtFlagcam.setBounds(117, 376, 24, 20);
		pnlTimings.add(txtFlagcam);
		
		txtRotKnife = new JTextField();
		txtRotKnife.setHorizontalAlignment(SwingConstants.CENTER);
		txtRotKnife.setToolTipText("TOTAL");
		txtRotKnife.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtRotKnife.setEnabled(false);
		txtRotKnife.setColumns(10);
		txtRotKnife.setBounds(255, 376, 24, 20);
		pnlTimings.add(txtRotKnife);
		
		txtTurret2 = new JTextField();
		txtTurret2.setToolTipText("TOTAL");
		txtTurret2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpenPasswordDialog();
			}
		});
		txtTurret2.setEnabled(false);
		txtTurret2.setColumns(10);
		txtTurret2.setBounds(197, 304, 61, 20);
		pnlTimings.add(txtTurret2);
		
		txtSclampR = new JTextField();
		txtSclampR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtSclampR.setToolTipText("Rewind speed clamp");
		txtSclampR.setEnabled(false);
		txtSclampR.setColumns(10);
		txtSclampR.setBounds(112, 407, 61, 20);
		pnlTimings.add(txtSclampR);
		
		lblRpm = new JLabel("rpm (at rewind)");
		lblRpm.setEnabled(false);
		lblRpm.setBounds(178, 409, 97, 14);
		pnlTimings.add(lblRpm);
		
		txtSclampU = new JTextField();
		txtSclampU.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenPasswordDialog();
			}
		});
		txtSclampU.setToolTipText("Unwind speed clamp");
		txtSclampU.setEnabled(false);
		txtSclampU.setColumns(10);
		txtSclampU.setBounds(112, 428, 61, 20);
		pnlTimings.add(txtSclampU);
		
		lblRpm_1 = new JLabel("rpm (at unwind)");
		lblRpm_1.setEnabled(false);
		lblRpm_1.setBounds(178, 431, 97, 14);
		pnlTimings.add(lblRpm_1);
		
		label_9 = new JLabel("(s)");
		label_9.setEnabled(false);
		label_9.setBounds(259, 307, 16, 14);
		pnlTimings.add(label_9);
		
		TimingsBoxes1 = new JTextField[OperatorTimings.length];
		TimingsBoxes2 = new JTextField[OperatorTimings.optnslength];
		
		TimingsBoxes1[0] = txtSet1;
		TimingsBoxes1[1] = txtLoad1;
		TimingsBoxes1[2] = txtAlign1;
		TimingsBoxes1[3] = txtPos1;
		TimingsBoxes1[4] = txtSplice1;
		TimingsBoxes1[5] = txtCut1;
		TimingsBoxes1[6] = txtTapet1;
		TimingsBoxes1[7] = txtTapec1;
		TimingsBoxes1[8] = txtLoadr1;
		TimingsBoxes1[9] = txtUnloadr1; 
		TimingsBoxes1[10] = txtDiam1; 
		TimingsBoxes1[11] = txtTurret1;
		TimingsBoxes1[12] = txtAlignr1;
		
		TimingsBoxes2[0] = txtSet2;
		TimingsBoxes2[1] = txtLoad2;
		TimingsBoxes2[2] = txtAlign2;
		TimingsBoxes2[3] = txtPos2;
		TimingsBoxes2[4] = txtSplice2;
		TimingsBoxes2[5] = txtCut2;
		TimingsBoxes2[6] = txtTapet2;
		TimingsBoxes2[7] = txtTapec2;
		TimingsBoxes2[8] = txtLoadr2;
		TimingsBoxes2[9] = txtUnloadr2; 
		TimingsBoxes2[10] = txtDiam2; 
		TimingsBoxes2[11] = txtTurret2;
		TimingsBoxes2[12] = txtAlignLaser;
		TimingsBoxes2[13] = txtAlignServo;
		TimingsBoxes2[14] = txtFlagcam; 
		TimingsBoxes2[15] = txtRotKnife;
		TimingsBoxes2[16] = txtSclampR;
		TimingsBoxes2[17] = txtSclampU;
		
		lblCoreDiameter = new JLabel("core diameter:");
		lblCoreDiameter.setEnabled(false);
		lblCoreDiameter.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCoreDiameter.setBounds(20, 290, 92, 14);
		pnlTimings.add(lblCoreDiameter);
		
		lblSclamp = new JLabel("Speed clamps:");
		lblSclamp.setEnabled(false);
		lblSclamp.setToolTipText("Speed clamp in rpm");
		lblSclamp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSclamp.setBounds(10, 410, 69, 14);
		pnlTimings.add(lblSclamp);
		
		for(int i=0; i<TimingsBoxes1.length; ++i){
			TimingsBoxes1[i].addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					((JTextField)arg0.getSource()).selectAll();
				}
			});
		}
		for(int i=0; i<TimingsBoxes2.length; ++i){
			TimingsBoxes2[i].addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					((JTextField)arg0.getSource()).selectAll();
				}
			});
		}
		
		if(m != null){
			switch(m.model){
				case ER610: cmbMachineType.setSelectedIndex(0); break;
				case SR9DS: cmbMachineType.setSelectedIndex(1); break;
				case SR9DT: cmbMachineType.setSelectedIndex(2); break;
				case SR800: cmbMachineType.setSelectedIndex(3); break;
				case CUSTOM: cmbMachineType.setSelectedIndex(0); break;
				default: cmbMachineType.setSelectedIndex(0); break;
			}
		}

		UpdateTimeOptions();
		
		pnlLock = new JPanel();
		pnlLock.setToolTipText("Administrator controls lock status");
		pnlLock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pnlLock.contains(e.getPoint()) || lblLock.contains(e.getPoint()))
					OpenPasswordDialog();
			}
		});
		pnlLock.setBackground(Color.RED);
		pnlLock.setBounds(10, 499, 15, 15);
		getContentPane().add(pnlLock);
		
		lblLock = new JLabel("Locked");
		lblLock.setToolTipText("Administrator controls lock status");
		lblLock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pnlLock.contains(e.getPoint()) || lblLock.contains(e.getPoint()))
					OpenPasswordDialog();
			}
		});
		lblLock.setBounds(32, 499, 75, 14);
		getContentPane().add(lblLock);
		
		UpdateLock();
	}
	
	private void UpdateLock(){
		if(p.Unlocked){
			pnlLock.setBackground(Color.GREEN);
			lblLock.setText("Unlocked");
			
			txtAlign1.setEnabled(true);
			txtAlign2.setEnabled(true);
			txtAlignr1.setEnabled(true);
			txtAlignLaser.setEnabled(true);
			txtAlignServo.setEnabled(true);
			txtTurret1.setEnabled(true);
			txtTurret2.setEnabled(true);
			txtFlagcam.setEnabled(true);
			txtRotKnife.setEnabled(true);
			txtCut1.setEnabled(true);
			txtCut2.setEnabled(true);
			txtDiam1.setEnabled(true);
			txtDiam2.setEnabled(true);
			txtLoad1.setEnabled(true);
			txtLoad2.setEnabled(true);
			txtLoadr1.setEnabled(true);
			txtLoadr2.setEnabled(true);
			txtPos1.setEnabled(true);
			txtPos2.setEnabled(true);
			txtSet1.setEnabled(true);
			txtSet2.setEnabled(true);
			txtSplice1.setEnabled(true);
			txtSplice2.setEnabled(true);
			txtTapec1.setEnabled(true);
			txtTapec2.setEnabled(true);
			txtTapet1.setEnabled(true);
			txtTapet2.setEnabled(true);
			txtUnloadr1.setEnabled(true);
			txtUnloadr2.setEnabled(true);
			txtSclampR.setEnabled(true);
			txtSclampU.setEnabled(true);
			
			lblLaser.setEnabled(true);
			lblServo.setEnabled(true);
			lblFlagCameraOffset.setEnabled(true);
			lblRotaryKnifeMultiplier.setEnabled(true);
			
			lblAdvancedOptionsPlease.setVisible(true);
			lblAdv2.setVisible(true);
			lblAdv3.setVisible(true);
			lblCustomMachines.setVisible(true);
			lblTitanMachines.setVisible(true);
			lblGlobalAdjustment.setVisible(true);
			lblPc1.setVisible(true);
			lblPc2.setVisible(true);
			lblPc3.setVisible(true);
			slide1.setVisible(true);
			slide2.setVisible(true);
			slide3.setVisible(true);
			
			lblAlignRewind.setEnabled(true);
			lblAlignUnwind.setEnabled(true);
			lblTurretChange.setEnabled(true);
			lblCoreDiameter.setEnabled(true);
			lblChangeRewindDiam.setEnabled(true);
			lblTapeRewindCore.setEnabled(true);
			lblTapeRewindTail.setEnabled(true);
			lblLoadRewind.setEnabled(true);
			lblUnloadRewind.setEnabled(true);
			lblLoadUnwind.setEnabled(true);
			lblCutRewind.setEnabled(true);
			lblSpliceRoll.setEnabled(true);
			lblPositionKnives.setEnabled(true);
			lblConfigureMachine.setEnabled(true);
			
			lblStandard.setEnabled(true);
			lblWithOptions.setEnabled(true);
			lblRpm.setEnabled(true);
			lblRpm_1.setEnabled(true);
			lblSclamp.setEnabled(true);
			
			lbls.setEnabled(true);
			label_5.setEnabled(true);
			label_6.setEnabled(true);
			label_7.setEnabled(true);
			label_8.setEnabled(true);
			label_9.setEnabled(true);
			label_10.setEnabled(true);
			label_11.setEnabled(true);
			label_12.setEnabled(true);
			label_13.setEnabled(true);
			label_14.setEnabled(true);
			label_15.setEnabled(true);
			label_16.setEnabled(true);
			label_17.setEnabled(true);
			label_18.setEnabled(true);
			label_19.setEnabled(true);
			label_20.setEnabled(true);
			label_21.setEnabled(true);
			label_22.setEnabled(true);
			label_23.setEnabled(true);
			label_24.setEnabled(true);
			label_25.setEnabled(true);
			label_26.setEnabled(true);
			label_27.setEnabled(true);
			label_28.setEnabled(true);
			label_29.setEnabled(true);
			label_30.setEnabled(true);
			
			btnUnlock.setVisible(false);
			lblOptionsNotYet.setVisible(false);
			
			lblIfYouHave.setEnabled(false);
			lblButtonToUnlock.setEnabled(false);
			btnUnlock_1.setEnabled(false);
			
			btnResetAutoTimings.setEnabled(true);
			btnResetManualTimings.setEnabled(true);
			cmbResetManual.setEnabled(true);
			cmbResetAuto.setEnabled(true);
			btnResetEfficiencySliders.setEnabled(true);
		}else{
			pnlLock.setBackground(Color.RED);
			lblLock.setText("Locked");
			
			txtAlign2.setEnabled(false);
			txtAlignLaser.setEnabled(false);
			txtAlignServo.setEnabled(false);
			txtTurret1.setEnabled(false);
			txtTurret2.setEnabled(false);
			txtFlagcam.setEnabled(false);
			txtRotKnife.setEnabled(false);
			txtCut2.setEnabled(false);
			txtDiam2.setEnabled(false);
			txtLoad2.setEnabled(false);
			txtLoadr2.setEnabled(false);
			txtPos2.setEnabled(false);
			txtSet2.setEnabled(false);
			txtSplice2.setEnabled(false);
			txtTapec2.setEnabled(false);
			txtTapet2.setEnabled(false);
			txtUnloadr2.setEnabled(false);
			txtSclampR.setEnabled(false);
			txtSclampU.setEnabled(false);
			
			lblLaser.setEnabled(false);
			lblServo.setEnabled(false);
			lblFlagCameraOffset.setEnabled(false);
			lblRotaryKnifeMultiplier.setEnabled(false);
			
			lblAdvancedOptionsPlease.setVisible(false);
			lblAdv2.setVisible(false);
			lblAdv3.setVisible(false);
			lblCustomMachines.setVisible(false);
			lblTitanMachines.setVisible(false);
			lblGlobalAdjustment.setVisible(false);
			lblPc1.setVisible(false);
			lblPc2.setVisible(false);
			lblPc3.setVisible(false);
			slide1.setVisible(false);
			slide2.setVisible(false);
			slide3.setVisible(false);

			btnUnlock.setVisible(true);
			lblOptionsNotYet.setVisible(true);
			
			btnResetAutoTimings.setEnabled(false);
			cmbResetAuto.setEnabled(false);
			btnResetEfficiencySliders.setEnabled(false);
		}
	}
	
	class SERfilter extends FileFilter {
		@Override
		public String getDescription(){
			return "Settings files";
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
	
	public File addExt(File f, String ext){
		String path = f.getAbsolutePath();
	
	    if(!path.endsWith(ext))
	    {
	      f = new File(path + ext);
	    }
	    
	    return f;
	}
	
	private void ResetManual(){
		Machine.Model model = null;
		switch(cmbResetManual.getSelectedIndex()){
			case 0: model = null; break;
			case 1: model = Model.ER610; break;
			case 2: model = Model.SR9DS; break;
			case 3: model = Model.SR9DT; break;
			case 4: model = Model.SR800; break;
			case 5: model = Model.CUSTOM; break;
		}
		p.timings.ResetTimes(model, true, (cmbResetManual.getSelectedIndex()==0 ? true : false));
		UpdateTimeOptions();
	}
	private void ResetAuto(){
		Machine.Model model = null;
		switch(cmbResetAuto.getSelectedIndex()){
			case 0: model = null; break;
			case 1: model = Model.ER610; break;
			case 2: model = Model.SR9DS; break;
			case 3: model = Model.SR9DT; break;
			case 4: model = Model.SR800; break;
			case 5: model = Model.CUSTOM; break;
		}
		p.timings.ResetTimes(model, false, (cmbResetAuto.getSelectedIndex()==0 ? true : false));
		UpdateTimeOptions();
	}
	
	private void BrowseFiles(){
		fc.setCurrentDirectory((new File(p.SaveFileName)).getParentFile());
		fc.setFileFilter(new SERfilter());
		
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
            File file = fc.getSelectedFile();
            
            String path = file.getAbsolutePath();

            String extension = ".ser";

        	if(!path.endsWith(extension))
            {
              file = new File(path + extension);
            }
        	
        	txtFileName.setText(file.getAbsolutePath());
		}
	}
	
	private void ShowResetHelp(){
		JOptionPane.showMessageDialog(this, "To clear all settings, de-select the \"Save settings on close\" option on the first options screen, then close and re-start the application.");
	}
	
	private void ResetAdvanced(){
		p.SaveOnClose = true;
		p.SaveFileName = Consts.SETTINGS_FILENAME;
		/*p.accel = Consts.DEFAULT_ACCEL;
		p.decel = Consts.DEFAULT_DECEL;
		p.accel_delta = Consts.DEFAULT_ADELTA;*/
		
		// TODO reset save settings!
	}
	
	private void ResetSliders(){
		slide1.setValue((int)(Consts.TITAN_EFF*1000));
		slide2.setValue((int)(Consts.CUSTOM_EFF*1000));
		slide3.setValue((int)(Consts.GLOBAL_EFF*1000));
		p.TitanEfficiency = Consts.TITAN_EFF;
		p.CustomEfficiency = Consts.CUSTOM_EFF;
		p.GlobalEfficiency = Consts.GLOBAL_EFF;
		lblPc1.setText(Double.toString(roundTwoDecimals(Consts.TITAN_EFF*100))+"%");
		lblPc2.setText(Double.toString(roundTwoDecimals(Consts.CUSTOM_EFF*100))+"%");
		lblPc3.setText(Double.toString(roundTwoDecimals(Consts.GLOBAL_EFF*100))+"%");
	}
	
	private void btnOK(){
		/*p.accel = Double.parseDouble(txtAccel.getText()) * Consts.MIN_TO_S;
		p.decel = Double.parseDouble(txtDecel.getText()) * Consts.MIN_TO_S;
		p.accel_delta = Double.parseDouble(txtAccelDelta.getText());*/
		
		SaveTimings();
		
		if(chckbxSaveSettings.isSelected()){
			p.SaveOnClose = true;
			//p.SaveSchedule = chckbxSaveSchedule.isSelected();

		}else{
			p.SaveOnClose = false;
			//p.SaveSchedule = false;
		}
		p.SaveFileName = txtFileName.getText();
		
		p.TitanEfficiency = slide1.getValue() / 1000.;
		p.CustomEfficiency = slide2.getValue() / 1000.;
		p.GlobalEfficiency = slide3.getValue() / 1000.;
		
		dispose();
	}
	
	private void UpdateFilename(){
		p.SaveFileName = txtFileName.getText();
	}
	
	private void UpdateTimeOptions(){
		if(cmbMachineType.getSelectedItem().toString().equals("ER610")){
			PopulateTimings(p.timings.ER610_BASIC, p.timings.ER610_OPTNS);
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR9-DS")){
			PopulateTimings(p.timings.SR9DS_BASIC, p.timings.SR9DS_OPTNS);
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR9-DT")){
			PopulateTimings(p.timings.SR9DT_BASIC, p.timings.SR9DT_OPTNS);
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR800")){
			PopulateTimings(p.timings.SR800_BASIC, p.timings.SR800_OPTNS);
		}else if(cmbMachineType.getSelectedItem().toString().equals("Custom")){
			PopulateTimings(p.timings.CUSTOM_BASIC, p.timings.CUSTOM_OPTNS);
		}
	}
	
	private void SaveTimings(){
		double[][] TimeGridBasic = {p.timings.ER610_BASIC, p.timings.SR9DS_BASIC, p.timings.SR9DT_BASIC, p.timings.SR800_BASIC, p.timings.CUSTOM_BASIC};
		double[][] TimeGridOpts  = {p.timings.ER610_OPTNS, p.timings.SR9DS_OPTNS, p.timings.SR9DT_OPTNS, p.timings.SR800_OPTNS, p.timings.CUSTOM_OPTNS};
		int index = 0;
		
		if(cmbMachineType.getSelectedItem().toString().equals("ER610")){
			index = 0;
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR9-DS")){
			index = 1;
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR9-DT")){
			index = 2;
		}else if(cmbMachineType.getSelectedItem().toString().equals("SR800")){
			index = 3;
		}else if(cmbMachineType.getSelectedItem().toString().equals("Custom")){
			index = 4;
		}

		for(int i=0; i < TimingsBoxes1.length; ++i){
			TimeGridBasic[index][i] = Double.parseDouble(TimingsBoxes1[i].getText());
		}
		for(int i=0; i < TimingsBoxes2.length; ++i){
			TimeGridOpts[index][i] = Double.parseDouble(TimingsBoxes2[i].getText());
			if(i==16 || i==17)
				TimeGridOpts[index][i] = Double.parseDouble(TimingsBoxes2[i].getText()) / 60.;
		}
		
	}
	
	private void OpenPasswordDialog(){
		if(! p.Unlocked){
			PasswordDialog pw = new PasswordDialog(p);
			pw.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			        UpdateLock();
			    }
			});
			pw.setLocationRelativeTo(this);
			pw.setVisible(true);
		}
	}	
	
	private void PopulateTimings(double[] basic, double[] opts){
		
		for(int i=0; i < TimingsBoxes1.length; ++i){
			TimingsBoxes1[i].setText(Double.toString(roundTwoDecimals(basic[i])));
			if(m != null && cmbMachineType.getSelectedItem().toString().toLowerCase().equals(m.model.toString().toLowerCase()) && !UpgradeSelected(m, i)){
				if(i != 12 || m.corepos != Corepos.SERVO)
					TimingsBoxes1[i].setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, new Color(0, 102, 0), new Color(0, 102, 0), Color.GREEN));
			}else{
				TimingsBoxes1[i].setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}
		for(int i=0; i < TimingsBoxes2.length; ++i){
			TimingsBoxes2[i].setText(Double.toString(roundTwoDecimals(opts[i])));
			if(i ==14)
				TimingsBoxes2[i].setText(Integer.toString((int)(Math.round(opts[i]))));
			if(i == 16 || i==17)
				TimingsBoxes2[i].setText(Double.toString(roundTwoDecimals(60*opts[i])));
			if(m != null && cmbMachineType.getSelectedItem().toString().toLowerCase().equals(m.model.toString().toLowerCase()) && UpgradeSelected(m, i)){
				TimingsBoxes2[i].setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, new Color(0, 102, 0), new Color(0, 102, 0), Color.GREEN));
			}else{
				TimingsBoxes2[i].setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}
	}
	
	private boolean UpgradeSelected(Machine m, int textbox){
		boolean result = false;
		switch(textbox){
			case 0: // machine config
				//if(m.model != Machine.Model.OTHER)
				//	result = true;
				break;
			case 1: // mother change
				
				break;
			case 2: // align mother
				if(m.alignment)
					result = true;
				break;
			case 3: // knife pos
				if(m.knives == Machine.Knives.AUTO)
					result = true;
				break;
			case 4: // splice
				if(m.splice_table)
					result = true;
				break;
			case 5: // cut
				if(m.cutoff)
					result = true;
				break;
			case 6: // tape tail
				if(m.tapetail)
					result = true;
				break;
			case 7: // tape core
				if(m.tapecore)
					result = true;
				break;
			case 8: // load rewind
				
				break;
			case 9: // unload rewind
				if(m.autostrip)
					result = true;
				break;
			case 10: // change diam
				
				break;
			case 11: // turret
				if(m.turret)
					result = true;
				break;
			case 12: // align rewind
				if(m.corepos == Machine.Corepos.LASER)
					result = true;
				break;
			case 13: // align rewind
				if(m.corepos == Machine.Corepos.SERVO)
					result = true;
				break;
			case 14: // flagcam
				if(m.flags)
					result = true;
				break;
			case 15: // knives rotary
				break;
			
			default: break;
		}
		return result;
	}
	
	private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
}
