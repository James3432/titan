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

public class JobListRenderer extends JLabel implements ListCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	Font fontPlain;
	Font fontBold;
	ImageIcon icon;
	
	public JobListRenderer()
	{
		try {
			icon = new ImageIcon(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/job_icon.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpaque(true);
		fontPlain = new Font( "Arial", Font.PLAIN, 14 ); 
 		fontBold = new Font( "Arial", Font.BOLD, 16 ); 
	}
     
	public Component getListCellRendererComponent(
			JList list, Object value, int index, 
			boolean isSelected, boolean cellHasFocus ) 
	{
		Job j = (Job) value;
		// Display the text for this item
		setText(j.getName()/*m.toString()*/);
		
		setIcon(icon);//TODO selectedIcon
		
		// Draw the correct colors and font
		if( isSelected )
		{
			// Set the color and font for a selected item
			setBackground( new Color(0,75,141) );
			setForeground( Color.white );
			setFont(fontBold);
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
