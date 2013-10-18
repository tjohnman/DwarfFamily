package de.ninjasoft.dwarffamily;

import javax.swing.JFrame;

public class Main {
	
	static public DebugWindow dbgWindow;
	
	public static void main(String[] args) {
		dbgWindow = new DebugWindow();
		dbgWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dbgWindow.setVisible(true);
	}
	
	
}
