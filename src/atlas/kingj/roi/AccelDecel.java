package atlas.kingj.roi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AccelDecel extends JDialog {

	private static final long serialVersionUID = 1L;
	public JButton btnOk;
	public JButton btnCancel;
	public JLabel lblAcceleration;
	public JLabel lblDeceleration;
	public JTextField textField;
	public JTextField textField_1;
	public JPanel panel;
	public JLabel lblMs;
	public JLabel lblMmins;
	
	Machine machine;
	double new_accel;
	double new_decel;
	public JLabel lblReset;
	public JPanel pnlUnderline;

	/**
	 * Create the dialog.
	 */
	public AccelDecel(Machine mach) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		machine = mach;
		new_accel = mach.accel;
		new_decel = mach.decel;
		getContentPane().setBackground(Color.WHITE);
		setTitle("Set Rates");
		setBounds(100, 100, 270, 167);
		try{
			setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
			getContentPane().setLayout(null);
			
			btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PressOK();
				}
			});
			btnOk.setBounds(56, 97, 89, 23);
			getContentPane().add(btnOk);
			
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBounds(155, 97, 89, 23);
			getContentPane().add(btnCancel);
			
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBackground(Color.WHITE);
			panel.setBounds(10, 11, 234, 81);
			getContentPane().add(panel);
			panel.setLayout(null);
			
			textField_1 = new JTextField(Double.toString(roundTwoDecimals(machine.decel * 60)));
			textField_1.setToolTipText("Enter new machine deceleration");
			textField_1.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					UpdateVals();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					UpdateVals();
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
				}
			});
			textField_1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					textField_1.selectAll();
				}
			});
			textField_1.setBounds(104, 46, 55, 20);
			panel.add(textField_1);
			textField_1.setColumns(10);
			
			textField = new JTextField(Double.toString(roundTwoDecimals(machine.accel * 60)));
			textField.setToolTipText("Enter new machine acceleration");
			textField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					UpdateVals();
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					UpdateVals();
				}
				@Override
				public void changedUpdate(DocumentEvent arg0) {
				}
			});
			textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					textField.selectAll();
				}
			});
			textField.setBounds(104, 21, 55, 20);
			this.getRootPane().setDefaultButton(btnOk);
			panel.add(textField);
			textField.setColumns(10);
			
			lblAcceleration = new JLabel("Acceleration:");
			lblAcceleration.setForeground(Color.DARK_GRAY);
			lblAcceleration.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblAcceleration.setBounds(20, 24, 86, 14);
			panel.add(lblAcceleration);
			
			lblDeceleration = new JLabel("Deceleration:");
			lblDeceleration.setForeground(Color.DARK_GRAY);
			lblDeceleration.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblDeceleration.setBounds(20, 49, 86, 14);
			panel.add(lblDeceleration);
			
			lblMs = new JLabel("m/min/s");
			lblMs.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblMs.setBounds(163, 24, 46, 14);
			panel.add(lblMs);
			
			lblMmins = new JLabel("m/min/s");
			lblMmins.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblMmins.setBounds(163, 49, 46, 14);
			panel.add(lblMmins);
			
			lblReset = new JLabel("Reset");
			lblReset.setToolTipText("Reset both values");
			lblReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
			lblReset.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					pnlUnderline.setVisible(false);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					pnlUnderline.setVisible(true);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					if(lblReset.contains(e.getPoint()) || pnlUnderline.contains(e.getPoint())){
						ResetForm();
					}
				}
			});
			lblReset.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblReset.setForeground(new Color(0, 0, 128));
			lblReset.setBounds(10, 100, 36, 14);
			getContentPane().add(lblReset);
			
			pnlUnderline = new JPanel();
			pnlUnderline.setCursor(new Cursor(Cursor.HAND_CURSOR));
			pnlUnderline.setForeground(new Color(0, 0, 128));
			pnlUnderline.setBackground(new Color(0, 0, 128));
			pnlUnderline.setBounds(10, 114, 36, 2);
			getContentPane().add(pnlUnderline);
			
			textField.requestFocusInWindow();
			
		}catch(NullPointerException e11){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	private void PressOK(){
		if(new_accel <= 0 || new_accel > machine.max_accel || new_decel > machine.max_decel || new_decel < -machine.max_decel){
			JOptionPane.showMessageDialog(this, "Invalid settings: please use positive values, and do not exceed the machine defaults.", "Settings not saved", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(new_decel < 0)
			new_decel = -new_decel;
		machine.accel = new_accel;
		machine.decel = new_decel;
		dispose();
	}
	
	private void ResetForm(){
		textField.setText(Double.toString(roundTwoDecimals(machine.max_accel * 60)));
		textField_1.setText(Double.toString(roundTwoDecimals(machine.max_decel * 60)));
	}
	
	private void UpdateVals(){
		double a = 0.;
		double d = 0.;
		try{
			a = Double.parseDouble(textField.getText()) / 60;
		}catch(NumberFormatException e){
			a = 0.;
		}
		try{
			d = Double.parseDouble(textField_1.getText()) / 60;
		}catch(NumberFormatException e){
			d = 0.;
		}
		new_accel = a;
		new_decel = d;
	}
	
	double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	
}
