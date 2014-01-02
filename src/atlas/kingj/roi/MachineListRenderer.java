package atlas.kingj.roi;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class MachineListRenderer extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private	ImageIcon	image[];
	Font fontPlain;
	Font fontBold;
	
	public MachineListRenderer()
	{
		ImageIcon[]	image = new ImageIcon[20];
 		try {	
 			image[0] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_list.png")));
 			image[1] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_list.png")));
 			image[2] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_list.png")));
 			image[3] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_list.png")));
 			image[4] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_list.png")));
 			image[10] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/er610_list_down.png")));
 			image[11] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9ds_list_down.png")));
 			image[12] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr9dt_list_down.png")));
 			image[13] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/custom_list_down.png")));
 			image[14] = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/sr800_list_down.png")));
 		} catch (NullPointerException e111) {
 			System.out.println("Image load error");
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}

 		setOpaque(true);
 		
 		fontPlain = new Font( "Arial", Font.PLAIN, 14 ); 
 		fontBold = new Font( "Arial", Font.BOLD, 16 ); 

		this.image = image;
	}
     
	public Component getListCellRendererComponent(
			JList list, Object value, int index, 
			boolean isSelected, boolean cellHasFocus ) 
	{
		Machine m = (Machine) value;
		// Display the text for this item
		setText(m.name/*m.toString()*/);
		
		// Set the correct image
		switch(m.model){
			case ER610: setIcon(image[0]); break;
			case SR9DS: setIcon(image[1]); break;
			case SR9DT: setIcon(image[2]); break;
			case SR800: setIcon(image[4]); break;
			case CUSTOM: setIcon(image[3]); break;
			default: setIcon(image[3]); break;
		}
		
		// Draw the correct colors and font
		if( isSelected )
		{
			// Set the color and font for a selected item
			setBackground( new Color(0,75,141) );
			setForeground( Color.white );
			setFont(fontBold);
			// Set the correct image
			switch(m.model){
				case ER610: setIcon(image[10]); break;
				case SR9DS: setIcon(image[11]); break;
				case SR9DT: setIcon(image[12]); break;
				case SR800: setIcon(image[14]); break;
				case CUSTOM: setIcon(image[13]); break;
				default: setIcon(image[13]); break;
			}
		}
		else
		{
			// Set the color and font for an unselected item
			setBackground( Color.white );
			setForeground( Color.black );
			setFont(fontPlain);
		}

		return this;
	}

}
