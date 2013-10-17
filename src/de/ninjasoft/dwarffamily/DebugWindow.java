package de.ninjasoft.dwarffamily;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xml.sax.SAXException;

public class DebugWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5355006794591105789L;

	private JPanel contentPanel = new JPanel(new FlowLayout());
	private ArrayList<Dwarf> dwarfList;
	private JButton xmlImportButton = new JButton("Import \"debug-legends.xml\"");
	private JButton gedExportButton = new JButton("GEDCOM file export test");
	
	public DebugWindow() throws HeadlessException {
		xmlImportButton.setActionCommand("import debug legends");
		xmlImportButton.addActionListener(this);
		contentPanel.add(xmlImportButton);
		
		gedExportButton.setActionCommand("export test");
		gedExportButton.addActionListener(this);
		gedExportButton.setEnabled(false);
		contentPanel.add(gedExportButton);
		
		this.setContentPane(contentPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("export test".equals(e.getActionCommand()))
		{
			try {
				Control.GedExportTest(dwarfList);
			} catch (SAXException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if("import debug legends".equals(e.getActionCommand()))
		{
			dwarfList = Control.ImportXML("debug-legends.xml");
			gedExportButton.setEnabled(true);
		}
	}

}
