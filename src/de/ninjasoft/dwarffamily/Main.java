package de.ninjasoft.dwarffamily;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	
	static public DebugWindow dbgWindow;
	
	public static void main(String[] args) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            dbgWindow = new DebugWindow();
            dbgWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dbgWindow.setVisible(true);
	}
	
	
}
