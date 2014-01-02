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
import javax.swing.JPanel;

/*
 *  Class to display an 'About' dialog
 */
public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel panel;


	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try{
		setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("About");
		setBounds(100, 100, 323, 178);
		getContentPane().setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.setToolTipText("Close window");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(109, 105, 104, 35);
		getContentPane().add(btnOk);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 297, 83);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblLabel = new JLabel("\u00A9 2013 Atlas Converting Equipment Ltd.\r\n");
		lblLabel.setBounds(21, 11, 251, 54);
		panel.add(lblLabel);
		lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

	}
}
