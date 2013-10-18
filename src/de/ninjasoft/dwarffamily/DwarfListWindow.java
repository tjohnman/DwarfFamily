package de.ninjasoft.dwarffamily;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DwarfListWindow extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1854336400936388884L;

	private ArrayList<Dwarf> dwarfList;
	
	AbstractListModel<String> dataModel = new AbstractListModel<String>() {
		private static final long serialVersionUID = 7104965065235986310L;
		public int getSize() { return dwarfList.size(); }
		public String getElementAt(int index) { return dwarfList.get(index).getName(); }
	 };

	private JList<String> dataList = new JList<String>(dataModel);
	private JTextArea textArea = new JTextArea();
	
	private JScrollPane listScrollPane = new JScrollPane(dataList);
	private JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	
	public DwarfListWindow(ArrayList<Dwarf> dwarves) throws HeadlessException {
		dwarfList = dwarves;
		
		dataList.addMouseListener(this);
		
		textArea.setText("Select a dwarf on the list.");
		textArea.setEditable(false);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		getContentPane().add(listScrollPane);
		getContentPane().add(textAreaScrollPane);
		pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int index = dataList.locationToIndex(e.getPoint());
		Dwarf dwarf = dwarfList.get(index);
		
		String info = "ID: "    + 	dwarf.getId() + "\n";
		info += 	  "Name: "  + 	dwarf.getName() + "\n";
		info += 	  "Gender: "+ 	dwarf.getGender() + "\n";
		
		info +=		  "Mother: ";
		if(dwarf.getMother() != null) info += dwarf.getMother().getName() + "\n";
		else info += "unknown\n";
		
		info +=		  "Father: ";
		if(dwarf.getFather() != null) info += dwarf.getFather().getName() + "\n";
		else info += "unknown\n";
		
		info += 	  "Birth: " + 	dwarf.getBirthday() + " " + dwarf.getBirthyear() + "\n";
		info += 	  "Death: " + 	dwarf.getDeathday() + " " + dwarf.getDeathyear() + "\n";
		
		if(dwarf.getChildren().size() > 0)
		{
			info +=		  "Children:\n";
			for(int i=0; i<dwarf.getChildren().size(); i++)
			{
				info += "\t" + dwarf.getChildren().get(i).getName() + "\n";
			}
		}
		else
		{
			info += "No offspring\n";
		}
		
		textArea.setText(info);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
