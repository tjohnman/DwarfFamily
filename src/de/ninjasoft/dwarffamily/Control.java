package de.ninjasoft.dwarffamily;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.SAXException;

public class Control{
	public static ArrayList<Dwarf> ImportXML(String filename, DebugWindow debug) {
		
		ArrayList<Dwarf> dwarfs = new ArrayList<Dwarf>();
		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(filename);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			Dwarf dwarf = null;
			ArrayList<Integer> children = null;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement() && event.asStartElement().getName().getLocalPart() == "historical_figure") {
					dwarf = new Dwarf();
					children = new ArrayList<Integer>();
					Boolean histfig = true;
					while (histfig) {
						event = eventReader.nextEvent();
						if (event.isStartElement()) {
							if (event.asStartElement().getName().getLocalPart().equals("race")) {
								event = eventReader.nextEvent();
								if (event.asCharacters().getData().contentEquals("DWARF")) {
									System.out.println(event.asCharacters().getData());
									continue;
								} else {
									break;
								}
							}
							if (event.asStartElement().getName().getLocalPart().equals("name")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setName(event.asCharacters().getData());
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("caste")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setGender(event.asCharacters().getData());
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("birth_seconds72")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setBirthday(secondsToDate(Integer.valueOf(event.asCharacters().getData())));
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("death_seconds72")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setDeathday(secondsToDate(Integer.valueOf(event.asCharacters().getData())));
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("id")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setId(Integer.valueOf(event.asCharacters().getData()));
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("death_year")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setDeathyear(event.asCharacters().getData());
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("birth_year")) {
								event = eventReader.nextEvent();
								System.out.println(event.asCharacters().getData());
								dwarf.setBirthyear(event.asCharacters().getData());
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals("link_type")) {
								event = eventReader.nextEvent();
								if (event.asCharacters().getData().contentEquals("mother")) {
									do {
										event = eventReader.nextEvent();
									} while (!event.isStartElement());
									event = eventReader.nextEvent();
									dwarf.setMotherid(Integer.valueOf(event.asCharacters().getData()));
									continue;
								}
								if (event.asCharacters().getData().contentEquals("father")) {
									do {
										event = eventReader.nextEvent();
									} while (!event.isStartElement());
									event = eventReader.nextEvent();
									dwarf.setFatherid(Integer.valueOf(event.asCharacters().getData()));
									continue;
								}
								if (event.asCharacters().getData().contentEquals("spouse")) {
									do {
										event = eventReader.nextEvent();
									} while (!event.isStartElement());
									event = eventReader.nextEvent();
									dwarf.setSpouseid(Integer.valueOf(event.asCharacters().getData()));
									continue;
								}
								if (event.asCharacters().getData().contentEquals("child")) {
									do {
										event = eventReader.nextEvent();
									} while (!event.isStartElement());
									event = eventReader.nextEvent();
									children.add(Integer.valueOf(event.asCharacters().getData()));
									continue;
								}

							}
						} else if (event.isEndElement()) {
							if (event.asEndElement().getName().getLocalPart() == "historical_figure") {
								histfig = false;
								dwarf.setChildrenids(children);
								dwarfs.add(dwarf);
							}
						}
					}

				}
				
			}
			eventReader.close();
			for (int i = 0; i < dwarfs.size(); i++) {
				if (dwarfs.get(i).getMotherid() != null) {
					for (int j = 0; j < dwarfs.size(); j++) {
						if (dwarfs.get(j).getId() == dwarfs.get(i).getMotherid()) {
							dwarfs.get(i).setMother(dwarfs.get(j));
							break;
						}
					}
				}
				if (dwarfs.get(i).getFatherid() != null) {
					for (int j = 0; j < dwarfs.size(); j++) {
						if (dwarfs.get(j).getId() == dwarfs.get(i).getFatherid()) {
							dwarfs.get(i).setFather(dwarfs.get(j));
							break;
						}
					}
				}
				if (dwarfs.get(i).getSpouseid() != null) {
					for (int j = 0; j < dwarfs.size(); j++) {
						if (dwarfs.get(j).getId() == dwarfs.get(i).getSpouseid()) {
							dwarfs.get(i).setSpouse(dwarfs.get(j));
							break;
						}
					}
				}
				if (dwarfs.get(i).getChildrenids() != null) {
					ArrayList<Dwarf> dwarfchildren = new ArrayList<Dwarf>();
					for (int j = 0; j < dwarfs.size(); j++) {
						for (int h = 0; h < dwarfs.get(i).getChildrenids().size(); h++) {
							if (dwarfs.get(j).getId() == dwarfs.get(i).getChildrenids().get(h)) {
								dwarfchildren.add(dwarfs.get(j));
								break;
							}
						}
					}
					dwarfs.get(i).setChildren(dwarfchildren);
				}
			}
		} catch (Exception E) {
			E.printStackTrace();
			JOptionPane.showMessageDialog(null, E.getMessage().toString());
			return null;
		}
		return dwarfs;

	}

	public static void GedExportTest(ArrayList<Dwarf> dwarfs) throws SAXException, IOException {
		PrintWriter writer = new PrintWriter("dwarf.ged", "UTF-8");
		for (int i = 0; i < dwarfs.size(); i++) {
			if (dwarfs.get(i).getGender().contentEquals("MALE")) {
				if (dwarfs.get(i).getSpouse() != null) {
					writer.println("0 @F" + dwarfs.get(i).getId() + "@ FAM");
					writer.println("1 HUSB @I" + dwarfs.get(i).getId() + "@");
					writer.println("1 WIFE @I" + dwarfs.get(i).getSpouse().getId() + "@");
					for (int j = 0; j < dwarfs.get(i).getChildren().size(); j++) {
						writer.println("1 CHIL @I" + dwarfs.get(i).getChildren().get(j).getId() + "@");
					}
					writer.println("");
				}
			}
		}
		for (int i = 0; i < dwarfs.size(); i++) {
			writer.println("0 @I" + dwarfs.get(i).getId() + "@ INDI");
			writer.println("1 NAME " + dwarfs.get(i).getName());
			writer.println("1 SEX " + dwarfs.get(i).getGender().substring(0, 1));
			writer.println("1 BIRT");
			writer.println("2 DATE " + dwarfs.get(i).getBirthday() + " " + dwarfs.get(i).getBirthyear());
			writer.println("1 DEAT");
			writer.println("2 DATE " + dwarfs.get(i).getDeathday() + " " + dwarfs.get(i).getDeathyear());
			writer.println("");
		}

		writer.close();

	}

	public static String secondsToDate(int count) {
		String dateString = Integer.toString((count % 33600) / 1200 + 1) + " ";
		int month = count / 33600 + 1;

		switch (month) {
		case 1: {
			dateString += "Granite";
			break;
		}
		case 2: {
			dateString += "Slate";
			break;
		}
		case 3: {
			dateString += "Felsite";
			break;
		}
		case 4: {
			dateString += "Hematite";
			break;
		}
		case 5: {
			dateString += "Malachite";
			break;
		}
		case 6: {
			dateString += "Galena";
			break;
		}
		case 7: {
			dateString += "Limestone";
			break;
		}
		case 8: {
			dateString += "Sandstone";
			break;
		}
		case 9: {
			dateString += "Timber";
			break;
		}
		case 10: {
			dateString += "Moonstone";
			break;
		}
		case 11: {
			dateString += "Opal";
			break;
		}
		case 12: {
			dateString += "Obsidian";
			break;
		}
		}

		return dateString;
	}

}
