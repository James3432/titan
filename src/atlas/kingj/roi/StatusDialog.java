package atlas.kingj.roi;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class StatusDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel lblMessage;

	/**
	 * Create the dialog.
	 */
	public StatusDialog(String message) {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		initialize(message);
	}
	
	private void initialize(String message) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	
		setVisible(false);
		
		getContentPane().setBackground(Color.WHITE);
		try{
			setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("");
		getContentPane().setLayout(null);
		
		lblMessage = new JLabel(message);
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(new Color(0, 128, 0));
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMessage.setBounds(10, 26, 401, 27);
		getContentPane().add(lblMessage);
	
		setBounds(100, 100, 427, 109);

	}

}
