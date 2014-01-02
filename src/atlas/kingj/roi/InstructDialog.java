package atlas.kingj.roi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class InstructDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnOk;
	private JLabel lblInstructions;
	private JLabel lblUseTheTabs;
	private JLabel lblSetupOptionsThe;
	private JLabel lblOfMachineTypes;
	private JLabel lblTypeOfJob;
	private JLabel lblEnvironmentSettingsAnd;
	private JLabel lblForAnalysingAnd;
	private JLabel lblDifferentScenarios;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructDialog dialog = new InstructDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the dialog.
	 */
	public InstructDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		try{
		setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
	}catch(NullPointerException e){
		System.out.println("Image load error");
	} catch (IOException e) {
		e.printStackTrace();
	}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setTitle("Instructions");
		setBounds(100, 100, 343, 252);
		getContentPane().setLayout(null);
		
		btnOk = new JButton("OK");
		btnOk.setToolTipText("Close window");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(121, 181, 89, 23);
		getContentPane().add(btnOk);
		
		lblInstructions = new JLabel("Instructions");
		lblInstructions.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInstructions.setBounds(27, 23, 96, 14);
		getContentPane().add(lblInstructions);
		
		lblUseTheTabs = new JLabel("Use the tabs along the top to navigate between different");
		lblUseTheTabs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUseTheTabs.setBounds(27, 49, 295, 14);
		getContentPane().add(lblUseTheTabs);
		
		lblSetupOptionsThe = new JLabel("setup options. The first tab allows selection and customisation");
		lblSetupOptionsThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetupOptionsThe.setBounds(27, 66, 305, 14);
		getContentPane().add(lblSetupOptionsThe);
		
		lblOfMachineTypes = new JLabel("of machine types. The second provides settings for the");
		lblOfMachineTypes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOfMachineTypes.setBounds(27, 83, 295, 14);
		getContentPane().add(lblOfMachineTypes);
		
		lblTypeOfJob = new JLabel("type of job being run. The third allows jobs to be scheduled,");
		lblTypeOfJob.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTypeOfJob.setBounds(27, 100, 295, 14);
		getContentPane().add(lblTypeOfJob);
		
		lblEnvironmentSettingsAnd = new JLabel("the fourth provides tools for analysing and graphing");
		lblEnvironmentSettingsAnd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnvironmentSettingsAnd.setBounds(27, 117, 283, 14);
		getContentPane().add(lblEnvironmentSettingsAnd);
		
		lblForAnalysingAnd = new JLabel("productivities, and the final tab provides an ROI model");
		lblForAnalysingAnd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblForAnalysingAnd.setBounds(27, 134, 295, 14);
		getContentPane().add(lblForAnalysingAnd);
		
		lblDifferentScenarios = new JLabel("comparing machines in the different scenarios.");
		lblDifferentScenarios.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDifferentScenarios.setBounds(27, 151, 283, 14);
		getContentPane().add(lblDifferentScenarios);

	}
}
