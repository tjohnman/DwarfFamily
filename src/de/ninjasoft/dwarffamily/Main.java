package de.ninjasoft.dwarffamily;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) {
		DebugWindow dbgWindow = new DebugWindow();
		dbgWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dbgWindow.setVisible(true);
	}
	
	public static ArrayList<Dwarf> ImportXML(String filename)
	{
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();

		NodeList nodeLst = doc.getElementsByTagName("historical_figure");
		System.out.println("Information of all historical_figures");
		ArrayList<Dwarf> dwarfs = new ArrayList<Dwarf>();
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("race");
				Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				NodeList fstNm;
				String race;
				if (fstNmElmnt != null) {
					fstNm = fstNmElmnt.getChildNodes();
					race = fstNm.item(0).getNodeValue();
				} else {
					race = "unknown";
				}
				

				if (race.contentEquals("DWARF")) {
					String name;
					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("name");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					if (fstNmElmnt != null) {
						fstNm = fstNmElmnt.getChildNodes();
						name = fstNm.item(0).getNodeValue();
					} else {
						name = "unknown";
					}
					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("caste");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					fstNm = fstNmElmnt.getChildNodes();
					String gender = fstNm.item(0).getNodeValue();

					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("id");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					fstNm = fstNmElmnt.getChildNodes();
					Integer id = Integer.valueOf(fstNm.item(0).getNodeValue());
					System.out.println(id);
					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("birth_seconds72");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					fstNm = fstNmElmnt.getChildNodes();
					String birthdate = secondsToDate(Integer.valueOf(fstNm.item(0).getNodeValue()));

					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("birth_year");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					fstNm = fstNmElmnt.getChildNodes();
					birthdate += " " + fstNm.item(0).getNodeValue();

					fstElmnt = (Element) fstNode;
					fstNmElmntLst = fstElmnt.getElementsByTagName("death_seconds72");
					fstNmElmnt = (Element) fstNmElmntLst.item(0);
					fstNm = fstNmElmnt.getChildNodes();
					Integer death = Integer.valueOf(fstNm.item(0).getNodeValue());
					String deathdate = "alive";
					if (death >= 1) {
						deathdate = secondsToDate(death);
						fstElmnt = (Element) fstNode;
						fstNmElmntLst = fstElmnt.getElementsByTagName("death_year");
						fstNmElmnt = (Element) fstNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						deathdate += " " + fstNm.item(0).getNodeValue();
					}

					dwarfs.add(new Dwarf(id, name, gender, null, null, null, birthdate, deathdate, null));

				}
			}

		}

		for (int i = 0; i < dwarfs.size(); i++) {
			ArrayList<Dwarf> children = new ArrayList<Dwarf>();
			Node fstNode = nodeLst.item(dwarfs.get(i).getId());
			Element fstElmnt = (Element) fstNode;
			NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("hf_link");
			if (fstNmElmntLst.getLength() != 0) {
				for (int j = 0; j < fstNmElmntLst.getLength(); j++) {

					fstNode = fstNmElmntLst.item(j);
					fstElmnt = (Element) fstNode;
					NodeList scnNmElmntLst = fstElmnt.getElementsByTagName("link_type");
					Element fstNmElmnt = (Element) scnNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					String type = (String) fstNm.item(0).getNodeValue();
					if (type.contentEquals("mother")) {
						fstNode = fstNmElmntLst.item(j);
						fstElmnt = (Element) fstNode;
						scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
						fstNmElmnt = (Element) scnNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						Integer motherid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
						for (int h = 0; h < dwarfs.size(); h++) {
							if (dwarfs.get(h).getId() == motherid) {
								dwarfs.get(i).setMother(dwarfs.get(h));
								break;
							}
						}
					} else if (type.contentEquals("father")) {
						fstNode = fstNmElmntLst.item(j);
						fstElmnt = (Element) fstNode;
						scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
						fstNmElmnt = (Element) scnNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						Integer fatherid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
						for (int h = 0; h < dwarfs.size(); h++) {
							if (dwarfs.get(h).getId() == fatherid) {
								dwarfs.get(i).setFarther(dwarfs.get(h));
								break;
							}
						}
					} else if (type.contentEquals("child")) {
						fstNode = fstNmElmntLst.item(j);
						fstElmnt = (Element) fstNode;
						scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
						fstNmElmnt = (Element) scnNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						Integer childid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
						for (int h = 0; h < dwarfs.size(); h++) {
							if (dwarfs.get(h).getId() == childid) {
								children.add(dwarfs.get(h));
								break;
							}
						}
					} else if (type.contentEquals("spouse")) {
						fstNode = fstNmElmntLst.item(j);
						fstElmnt = (Element) fstNode;
						scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
						fstNmElmnt = (Element) scnNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						Integer spouseid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
						for (int h = 0; h < dwarfs.size(); h++) {
							if (dwarfs.get(h).getId() == spouseid) {
								dwarfs.get(i).setSpouse(dwarfs.get(h));
								break;
							}
						}
					}

				}
			}
			dwarfs.get(i).setChildren(children);
			dwarfs.get(i).print();
		}
		
		return dwarfs;
	}
	
	public static void GedExportTest(ArrayList<Dwarf> dwarfs) throws SAXException, IOException
	{
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
			writer.println("2 DATE " + dwarfs.get(i).getBirth());
			writer.println("1 DEAT");
			writer.println("2 DATE " + dwarfs.get(i).getDeath());
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
