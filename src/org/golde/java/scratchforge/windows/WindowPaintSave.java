package org.golde.java.scratchforge.windows;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WindowPaintSave extends JFrame{
	
	private static final long serialVersionUID = 3709465786796447952L;

	public WindowPaintSave() {
		super("Reminder!");
		this.setAlwaysOnTop(true);
		this.setLocationByPlatform(true);
		
		JLabel label = new JLabel("When you are finished, Remember to click 'File -> Save' or 'CTRL + S'");
		
		label.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		this.add(label);
		this.setResizable(false);
		this.setUndecorated(true);
		
		this.pack();
		this.setVisible(false);
		

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight();
        this.setLocation(x, y);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	setVisible(false);
		    }
		});
        
	}
	
	/*@Override
    public Dimension getPreferredSize() {
        return new Dimension(320, 240);
    }
*/
	
}
