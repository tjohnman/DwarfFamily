package de.ninjasoft.dwarffamily;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xml.sax.SAXException;
import javax.swing.JProgressBar;

public class DebugWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5355006794591105789L;

	private JPanel contentPanel = new JPanel(new FlowLayout());
	private ArrayList<Dwarf> dwarfList;
	private JButton xmlImportButton = new JButton("Import a legends xml file");
	private JButton gedExportButton = new JButton("GEDCOM file export test");
	private final JProgressBar progressBar = new JProgressBar();

	public DebugWindow() throws HeadlessException {
		xmlImportButton.setActionCommand("import debug legends");
		xmlImportButton.addActionListener(this);
		contentPanel.add(xmlImportButton);

		gedExportButton.setActionCommand("export test");
		gedExportButton.addActionListener(this);
		gedExportButton.setEnabled(false);
		contentPanel.add(gedExportButton);

		this.setContentPane(contentPanel);

		contentPanel.add(progressBar);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("export test".equals(e.getActionCommand())) {
			try {
				Control.GedExportTest(dwarfList);
			} catch (SAXException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if ("import debug legends".equals(e.getActionCommand())) {
			xmlImportButton.setEnabled(false);
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			dwarfList = Control.ImportXML(chooser.getSelectedFile().getPath(), progressBar, gedExportButton, xmlImportButton);
		}
	}

}
