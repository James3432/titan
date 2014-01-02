package atlas.kingj.roi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class PasswordDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JPasswordField txtPassword;
	public JLabel lblEnterPasswordTo;
	private Production prod;
	JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public PasswordDialog(Production p) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setBackground(Color.WHITE);
		prod = p;
		setTitle("Password Entry");
		setResizable(false);
		setModal(true);
		try {	
			setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		} catch (NullPointerException e111) {
			System.out.println("Image load error");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 190, 141);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("Enter admin password");
		txtPassword.setBounds(32, 41, 124, 20);
		contentPanel.add(txtPassword);
		
		lblEnterPasswordTo = new JLabel("Enter password to unlock:");
		lblEnterPasswordTo.setBounds(32, 18, 136, 14);
		contentPanel.add(lblEnterPasswordTo);
		JButton okButton = new JButton("OK");
		okButton.setToolTipText("Submit password");
		okButton.setBounds(32, 72, 47, 23);
		contentPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((new String(txtPassword.getPassword())).equals(Consts.PASSWORD)){
					prod.Unlocked = true;  // NB. once unlocked, cannot re-lock without closing
					dispose();
				}else if(cancelButton.isVisible() == false){
					dispose();
				}else{
					lblEnterPasswordTo.setText("Incorrect password.");
					txtPassword.setVisible(false);
					cancelButton.setVisible(false);
				}
			}
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setToolTipText("Cancel");
			cancelButton.setBounds(91, 72, 65, 23);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
	}
}
