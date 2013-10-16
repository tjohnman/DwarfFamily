package de.ninjasoft.dwarffamily;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) throws SAXException, IOException {

		try {
			File fXmlFile = new File("/workspace/dwarffamily/region2-legends.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeLst = doc.getElementsByTagName("historical_figure");
			System.out.println("Information of all historical_figures");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("race");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					String race = (String) fstNm.item(0).getNodeValue();

					if (race.contentEquals("DWARF")) {
						fstElmnt = (Element) fstNode;
						fstNmElmntLst = fstElmnt.getElementsByTagName("name");
						fstNmElmnt = (Element) fstNmElmntLst.item(0);
						fstNm = fstNmElmnt.getChildNodes();
						String name = fstNm.item(0).getNodeValue();

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
						
						System.out.println(id);
						System.out.println(name);
						System.out.println(gender);
						System.out.println(birthdate);
						System.out.println(deathdate);
						System.out.println();
					}
				}

			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
