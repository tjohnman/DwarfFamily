package de.ninjasoft.dwarffamily;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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

	private ArrayList<Dwarf> dwarfList;
	private JButton xmlImportButton = new JButton("Import a legends XML file");
	private JButton gedExportButton = new JButton("GEDCOM file export test");
	private JButton viewDwarvesButton = new JButton("View dwarf list");
	private final JProgressBar progressBar = new JProgressBar();

	public DebugWindow() throws HeadlessException {
		JPanel buttonLayout = new JPanel(new FlowLayout());
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		xmlImportButton.setActionCommand("import debug legends");
		xmlImportButton.addActionListener(this);
		buttonLayout.add(xmlImportButton);

		gedExportButton.setActionCommand("export test");
		gedExportButton.addActionListener(this);
		gedExportButton.setEnabled(false);
		buttonLayout.add(gedExportButton);

		viewDwarvesButton.setActionCommand("open dwarf list");
		viewDwarvesButton.addActionListener(this);
		viewDwarvesButton.setEnabled(false);
		buttonLayout.add(viewDwarvesButton);

		getContentPane().add(buttonLayout);
		getContentPane().add(progressBar);

		this.setTitle("DwarfFamily Debugging Tools");
		this.setResizable(false);

		this.pack();
	}

	public void enableAllButtons() {
		gedExportButton.setEnabled(true);
		xmlImportButton.setEnabled(true);
		viewDwarvesButton.setEnabled(true);
		
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
			final JFileChooser chooser = new JFileChooser();
			if(chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
                        {
                            xmlImportButton.setEnabled(true);
                            return;
                        }
			progressBar.setIndeterminate(true);
			Runnable r = new Runnable() {
				public void run() {
					if ((dwarfList = Control.ImportXML(chooser.getSelectedFile().getPath(), DebugWindow.this)) != null) {
						enableAllButtons();
					} else {
						xmlImportButton.setEnabled(true);
						
					}
					progressBar.setIndeterminate(false);
				}
			};
			Thread t = new Thread(r);
			t.start();
		}
		if ("open dwarf list".equals(e.getActionCommand())) {
			DwarfListWindow listWindow = new DwarfListWindow(dwarfList);
			listWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			listWindow.setVisible(true);
		}
	}

}
