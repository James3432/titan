package atlas.kingj.roi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import atlas.kingj.roi.Machine.RewindType;

public class MachineBuilder extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnApply;
	private JButton btnCancel;
	private JLabel lblUseThisForm;
	private JTextField txtaccel;
	private JTextField txtspeed;
	private JTextField txtmother;
	private JLabel lblA;
	private JLabel lblC;
	private JLabel lblD;
	
	Machine machine;
	private JLabel lblNewLabel;
	private JLabel lblMs;
	private JPanel grpOptions;
	public JTextField txtdiam;
	public JLabel label_1;
	public JCheckBox chckbxDuplexRewind;
	public JLabel lblMotherChange;
	public JLabel lblTotalJobChange;
	public JLabel lblSpliceTime;
	public JCheckBox chckbxSplice;
	public JCheckBox chckbxTurret;
	public JCheckBox chckbxAutoCut;
	public JCheckBox chckbxAutostrip;
	public JLabel lblRewindAlignTime;
	public JLabel lblChangeRewindDiam;
	public JTextField txtjob;
	public JLabel label;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField txtsplice;
	public JTextField txtalign;
	public JPanel panel;
	public JPanel panel_1;
	public JCheckBox chckbxCorepos;
	public JButton btnReset;
	public JComboBox cmbRewindType;
	public JLabel lblRewindType;
	public JLabel lblDiameterChange;
	public JCheckBox chckbxBrakedUnwind;
	public JCheckBox chckbxAutoTape;
	public JCheckBox chckbxUnloadBoom;
	public JPanel panel_2;
	public JLabel lblRewind;
	public JTextField txtrewindramp;
	public JLabel lblRpm;
	public JLabel lblUnwind;
	public JTextField txtunwindramp;
	public JLabel lblRpm_1;

	/**
	 * Create the dialog.
	 */
	public MachineBuilder(Machine machine) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		this.machine = machine;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Custom Machine Builder");
		try{
		setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setBounds(100, 100, 427, 510);
		setModal(true);
		getContentPane().setLayout(null);
		
		lblUseThisForm = new JLabel("Use this form to add details about an existing machine not modelled by this tool.");
		lblUseThisForm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUseThisForm.setForeground(Color.DARK_GRAY);
		lblUseThisForm.setBounds(13, 11, 401, 14);
		getContentPane().add(lblUseThisForm);
		
		grpOptions = new JPanel();
		grpOptions.setBounds(13, 97, 398, 127);
		getContentPane().add(grpOptions);
		grpOptions.setBackground(Color.WHITE);
		grpOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Machine Timings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		grpOptions.setLayout(null);
		
		txtaccel = new JTextField();
		txtaccel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtaccel.selectAll();
			}
		});
		txtaccel.setToolTipText("The time taken for the machine to accelerate to its given top speed");
		txtaccel.setText("50");
		txtaccel.setBounds(123, 28, 46, 20);
		grpOptions.add(txtaccel);
		txtaccel.setColumns(10);
		
		txtmother = new JTextField();
		txtmother.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtmother.selectAll();
			}
		});
		txtmother.setToolTipText("The total time taken to change a mother reel");
		txtmother.setText("300");
		txtmother.setBounds(318, 59, 46, 20);
		grpOptions.add(txtmother);
		txtmother.setColumns(10);
		
		lblC = new JLabel("Reach top speed:");
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblC.setToolTipText("The time taken for the machine to accelerate to its given top speed");
		lblC.setBounds(15, 31, 98, 14);
		grpOptions.add(lblC);
		
		lblD = new JLabel("s");
		lblD.setBounds(368, 61, 11, 14);
		grpOptions.add(lblD);
		
		lblNewLabel = new JLabel("s");
		lblNewLabel.setBounds(174, 30, 11, 14);
		grpOptions.add(lblNewLabel);
		
		txtdiam = new JTextField();
		txtdiam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtdiam.selectAll();
			}
		});
		txtdiam.setToolTipText("The time taken to adjust core diameters on the rewind shafts");
		txtdiam.setText("720");
		txtdiam.setColumns(10);
		txtdiam.setBounds(123, 89, 46, 20);
		grpOptions.add(txtdiam);
		
		label_1 = new JLabel("s");
		label_1.setBounds(174, 92, 11, 14);
		grpOptions.add(label_1);
		
		lblMotherChange = new JLabel("Mother roll change:");
		lblMotherChange.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotherChange.setToolTipText("The total time taken to change a mother reel");
		lblMotherChange.setBounds(208, 62, 98, 14);
		grpOptions.add(lblMotherChange);
		
		lblTotalJobChange = new JLabel("Job change:");
		lblTotalJobChange.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTotalJobChange.setToolTipText("The total time taken to change between jobs (including unwind & rewind changes)");
		lblTotalJobChange.setBounds(208, 31, 69, 14);
		grpOptions.add(lblTotalJobChange);
		
		lblSpliceTime = new JLabel("Splice time:");
		lblSpliceTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpliceTime.setToolTipText("Time taken to detect flag and perform a splice, excluding ramp up and down times");
		lblSpliceTime.setBounds(208, 93, 98, 14);
		grpOptions.add(lblSpliceTime);
		
		lblRewindAlignTime = new JLabel("Set change:");
		lblRewindAlignTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRewindAlignTime.setToolTipText("Time taken to align reels on the rewind shaft before starting a run");
		lblRewindAlignTime.setBounds(15, 62, 98, 14);
		grpOptions.add(lblRewindAlignTime);
		
		lblChangeRewindDiam = new JLabel("Rewind core");
		lblChangeRewindDiam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChangeRewindDiam.setToolTipText("The time taken to adjust core diameters on the rewind shafts");
		lblChangeRewindDiam.setBounds(15, 85, 76, 14);
		grpOptions.add(lblChangeRewindDiam);
		
		txtjob = new JTextField();
		txtjob.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtjob.selectAll();
			}
		});
		txtjob.setToolTipText("The total time taken to change between jobs (including unwind & rewind changes)");
		txtjob.setText("600");
		txtjob.setColumns(10);
		txtjob.setBounds(318, 28, 46, 20);
		grpOptions.add(txtjob);
		
		label = new JLabel("s");
		label.setBounds(368, 31, 11, 14);
		grpOptions.add(label);
		
		label_2 = new JLabel("s");
		label_2.setBounds(368, 93, 11, 14);
		grpOptions.add(label_2);
		
		label_3 = new JLabel("s");
		label_3.setBounds(174, 61, 11, 14);
		grpOptions.add(label_3);
		
		txtsplice = new JTextField();
		txtsplice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtsplice.selectAll();
			}
		});
		txtsplice.setToolTipText("Time taken to detect flag and perform a splice, excluding ramp up and down times");
		txtsplice.setText("300");
		txtsplice.setColumns(10);
		txtsplice.setBounds(318, 90, 46, 20);
		grpOptions.add(txtsplice);
		
		txtalign = new JTextField();
		txtalign.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtalign.selectAll();
			}
		});
		txtalign.setToolTipText("Time taken to align reels on the rewind shaft before starting a run");
		txtalign.setText("50");
		txtalign.setColumns(10);
		txtalign.setBounds(123, 59, 46, 20);
		grpOptions.add(txtalign);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Cancel and close");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancel.setBounds(322, 450, 89, 23);
		getContentPane().add(btnCancel);
		
		btnApply = new JButton("OK");
		btnApply.setToolTipText("Save and close");
		btnApply.setBounds(223, 450, 89, 23);
		getContentPane().add(btnApply);
		btnApply.addActionListener(new AddButtonListener());
		
		
		
		btnApply.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApply.setForeground(Color.BLACK);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Machine Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(13, 296, 398, 147);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		chckbxDuplexRewind = new JCheckBox("Duplex Rewind");
		chckbxDuplexRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxDuplexRewind.setToolTipText("Duplex Rewind");
		chckbxDuplexRewind.setBounds(16, 27, 119, 23);
		panel.add(chckbxDuplexRewind);
		chckbxDuplexRewind.setBackground(Color.WHITE);
		
		chckbxSplice = new JCheckBox("Splice Table");
		chckbxSplice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxSplice.setToolTipText("Splice Table");
		chckbxSplice.setBounds(16, 53, 125, 23);
		panel.add(chckbxSplice);
		chckbxSplice.setBackground(Color.WHITE);
		
		chckbxTurret = new JCheckBox("Turret Rewind");
		chckbxTurret.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxTurret.setToolTipText("Turret Rewind");
		chckbxTurret.setBounds(140, 27, 97, 23);
		panel.add(chckbxTurret);
		chckbxTurret.setBackground(Color.WHITE);
		
		chckbxAutoCut = new JCheckBox("Auto Cross Cut");
		chckbxAutoCut.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutoCut.setToolTipText("Auto Cross Cut");
		chckbxAutoCut.setBounds(140, 53, 119, 23);
		panel.add(chckbxAutoCut);
		chckbxAutoCut.setBackground(Color.WHITE);
		
		chckbxAutostrip = new JCheckBox("Reel Stripping");
		chckbxAutostrip.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxAutostrip.setToolTipText("Reel Stripping");
		chckbxAutostrip.setBounds(140, 79, 119, 23);
		panel.add(chckbxAutostrip);
		chckbxAutostrip.setBackground(Color.WHITE);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Machine Speed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(13, 36, 398, 50);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblA = new JLabel("Top speed:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblA.setToolTipText("Top speed of machine");
		lblA.setBounds(91, 19, 59, 14);
		panel_1.add(lblA);
		
		txtspeed = new JTextField();
		txtspeed.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtspeed.selectAll();
			}
		});
		txtspeed.setToolTipText("Top speed of machine");
		txtspeed.setBounds(155, 16, 86, 20);
		panel_1.add(txtspeed);
		txtspeed.setText("500");
		txtspeed.setColumns(10);
		
		lblMs = new JLabel("m/min");
		lblMs.setBounds(245, 19, 46, 14);
		panel_1.add(lblMs);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		chckbxCorepos = new JCheckBox("Core Positioning");
		chckbxCorepos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxCorepos.setToolTipText("Core Positioning");
		chckbxCorepos.setBackground(Color.WHITE);
		chckbxCorepos.setBounds(16, 79, 114, 23);
		panel.add(chckbxCorepos);
		
		cmbRewindType = new JComboBox();
		cmbRewindType.setToolTipText("Select rewind type");
		cmbRewindType.setModel(new DefaultComboBoxModel(new String[] {"Quick lock", "Lock core", "Differential"}));
		cmbRewindType.setBounds(91, 111, 138, 20);
		panel.add(cmbRewindType);
		
		lblRewindType = new JLabel("Rewind type:");
		lblRewindType.setBounds(21, 114, 64, 14);
		panel.add(lblRewindType);
		
		chckbxBrakedUnwind = new JCheckBox("Braked Unwind");
		chckbxBrakedUnwind.setToolTipText("Braked Unwind");
		chckbxBrakedUnwind.setBackground(Color.WHITE);
		chckbxBrakedUnwind.setBounds(260, 27, 112, 23);
		panel.add(chckbxBrakedUnwind);
		
		chckbxAutoTape = new JCheckBox("Auto Tape Core & Tail");
		chckbxAutoTape.setToolTipText("Auto Tape Core & Tail");
		chckbxAutoTape.setBackground(Color.WHITE);
		chckbxAutoTape.setBounds(260, 53, 134, 23);
		panel.add(chckbxAutoTape);
		
		chckbxUnloadBoom = new JCheckBox("Unload Boom");
		chckbxUnloadBoom.setToolTipText("Unload Boom");
		chckbxUnloadBoom.setBackground(Color.WHITE);
		chckbxUnloadBoom.setBounds(260, 79, 97, 23);
		panel.add(chckbxUnloadBoom);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResetForm();
			}
		});
		btnReset.setToolTipText("Reset the form");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnReset.setBounds(13, 450, 89, 23);
		getContentPane().add(btnReset);
		
		try{
			txtspeed.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().speed * 60)));
			txtaccel.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().speed / machine.getCustomMachine().accel)));
	        txtalign.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().change_set)));
	        txtdiam.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().change_diam)));
	        txtjob.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().change_job)));
	        txtmother.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().change_mother)));
	        txtsplice.setText(Double.toString(roundTwoDecimals(machine.getCustomMachine().splice)));
	        
	        lblDiameterChange = new JLabel("diameter change:");
	        lblDiameterChange.setFont(new Font("Tahoma", Font.PLAIN, 11));
	        lblDiameterChange.setBounds(15, 98, 88, 14);
	        grpOptions.add(lblDiameterChange);
	        chckbxDuplexRewind.setSelected(machine.getCustomMachine().options[0]);
	        chckbxTurret.setSelected(machine.getCustomMachine().options[1]);
	        chckbxBrakedUnwind.setSelected(machine.getCustomMachine().options[2]);
	        chckbxSplice.setSelected(machine.getCustomMachine().options[3]);
	        chckbxAutoCut.setSelected(machine.getCustomMachine().options[4]);
	        chckbxAutoTape.setSelected(machine.getCustomMachine().options[5]);
	        chckbxCorepos.setSelected(machine.getCustomMachine().options[6]);
	        chckbxAutostrip.setSelected(machine.getCustomMachine().options[7]);
	        chckbxUnloadBoom.setSelected(machine.getCustomMachine().options[8]);
			
			cmbRewindType.setSelectedIndex( (machine.getCustomMachine().rewind_type == RewindType.QUICKLOCK ? 0 : (machine.getCustomMachine().rewind_type == RewindType.LOCKCORE ? 1 : 2)) );
			
			panel_2 = new JPanel();
			panel_2.setBounds(13, 235, 398, 50);
			getContentPane().add(panel_2);
			panel_2.setLayout(null);
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Speed Ramps", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_2.setBackground(Color.WHITE);
			
			lblRewind = new JLabel("Rewind:");
			lblRewind.setToolTipText("Top speed of machine");
			lblRewind.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblRewind.setBounds(32, 22, 46, 14);
			panel_2.add(lblRewind);
			
			txtrewindramp = new JTextField();
			txtrewindramp.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtrewindramp.selectAll();
				}
			});
			txtrewindramp.setToolTipText("Top speed of machine");
			txtrewindramp.setText("1000");
			txtrewindramp.setColumns(10);
			txtrewindramp.setBounds(75, 19, 64, 20);
			panel_2.add(txtrewindramp);
			
			lblRpm = new JLabel("rpm");
			lblRpm.setBounds(146, 22, 46, 14);
			panel_2.add(lblRpm);
			
			lblUnwind = new JLabel("Unwind:");
			lblUnwind.setToolTipText("Top speed of machine");
			lblUnwind.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblUnwind.setBounds(202, 22, 46, 14);
			panel_2.add(lblUnwind);
			
			txtunwindramp = new JTextField();
			txtunwindramp.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					txtunwindramp.selectAll();
				}
			});
			txtunwindramp.setToolTipText("Top speed of machine");
			txtunwindramp.setText("1000");
			txtunwindramp.setColumns(10);
			txtunwindramp.setBounds(245, 19, 64, 20);
			panel_2.add(txtunwindramp);
			
			lblRpm_1 = new JLabel("rpm");
			lblRpm_1.setBounds(316, 22, 46, 14);
			panel_2.add(lblRpm_1);
			txtrewindramp.setText(Double.toString(roundTwoDecimals(60*machine.getCustomMachine().rewindLimit)));
			txtunwindramp.setText(Double.toString(roundTwoDecimals(60*machine.getCustomMachine().unwindLimit)));
			
			
		}catch(NumberFormatException e){
			ResetForm();
		}

	}
	
	private void ResetForm(){
		txtspeed.setText(Double.toString(roundTwoDecimals(Consts.CUST_MACHINE_SPEED * 60)));
		txtaccel.setText(Double.toString(roundTwoDecimals(Consts.CUST_MACHINE_SPEED / Consts.CUST_MACHINE_ACCEL)));
        txtalign.setText(Double.toString(Consts.CUST_MACHINE_CHANGESET));
        txtdiam.setText(Double.toString(Consts.CUST_MACHINE_CHANGEDIAM));
        txtjob.setText(Double.toString(Consts.CUST_MACHINE_CHANGEJOB));
        txtmother.setText(Double.toString(Consts.CUST_MACHINE_CHANGEMO));
        txtsplice.setText(Double.toString(Consts.CUST_MACHINE_SPLICE));
        txtrewindramp.setText(Double.toString(0.00));
        txtunwindramp.setText(Double.toString(0.00));
        chckbxDuplexRewind.setSelected(false);
        chckbxTurret.setSelected(false);
        chckbxBrakedUnwind.setSelected(false);
        chckbxSplice.setSelected(false);
        chckbxAutoCut.setSelected(false);
        chckbxAutoTape.setSelected(false);
        chckbxCorepos.setSelected(false);
        chckbxAutostrip.setSelected(false);
        chckbxUnloadBoom.setSelected(false);
        cmbRewindType.setSelectedIndex(0);
        
	}
	
	class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			machine.getCustomMachine().speed = Double.parseDouble(txtspeed.getText()) / 60.;
			machine.getCustomMachine().accel = machine.getCustomMachine().speed / Double.parseDouble(txtaccel.getText());
            machine.getCustomMachine().change_set = Double.parseDouble(txtalign.getText());
            machine.getCustomMachine().change_diam = Double.parseDouble(txtdiam.getText());
            machine.getCustomMachine().change_job = Double.parseDouble(txtjob.getText());
            machine.getCustomMachine().change_mother = Double.parseDouble(txtmother.getText());
            machine.getCustomMachine().splice = Double.parseDouble(txtsplice.getText());
            machine.getCustomMachine().rewindLimit = Double.parseDouble(txtrewindramp.getText()) / 60.;
            machine.getCustomMachine().unwindLimit = Double.parseDouble(txtunwindramp.getText()) / 60.;
            
            //  duplexrewind turret  brakedunwind  splicetab autocut  autotape  corepos  autostrip unloadboom
            machine.getCustomMachine().options = new boolean[]{ chckbxDuplexRewind.isSelected(),        chckbxTurret.isSelected(), chckbxBrakedUnwind.isSelected(), 
            													chckbxSplice.isSelected(),   chckbxAutoCut.isSelected(), chckbxAutoTape.isSelected(), 
            													chckbxCorepos.isSelected(),  chckbxAutostrip.isSelected(), chckbxUnloadBoom.isSelected()
            												  };
            switch(cmbRewindType.getSelectedIndex()){
            case 0: machine.getCustomMachine().rewind_type = RewindType.QUICKLOCK; break;
            case 1: machine.getCustomMachine().rewind_type = RewindType.LOCKCORE; break;
            case 2: machine.getCustomMachine().rewind_type = RewindType.DIFFERENTIAL; break;
            default: machine.getCustomMachine().rewind_type = RewindType.QUICKLOCK; break;
            }
			
			dispose();
		}
	}
	
	double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
}
