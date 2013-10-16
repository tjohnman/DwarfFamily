package de.ninjasoft.dwarffamily;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

			ArrayList<Dwarf> dwarfs = new ArrayList<Dwarf>();
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

						dwarfs.add(new Dwarf(id, name, gender, null, null, null, birthdate, deathdate));

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
						if(type.contentEquals("mother")){
							fstNode = fstNmElmntLst.item(j);
							fstElmnt = (Element) fstNode;
							scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
							fstNmElmnt = (Element) scnNmElmntLst.item(0);
							fstNm = fstNmElmnt.getChildNodes();
							Integer motherid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
							for(int h=0;h < dwarfs.size(); h++){
								if(dwarfs.get(h).getId() == motherid){
									dwarfs.get(i).setMother(dwarfs.get(h));
									break;
								}
							}
						}
						else if(type.contentEquals("father")){
							fstNode = fstNmElmntLst.item(j);
							fstElmnt = (Element) fstNode;
							scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
							fstNmElmnt = (Element) scnNmElmntLst.item(0);
							fstNm = fstNmElmnt.getChildNodes();
							Integer fatherid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
							for(int h=0;h < dwarfs.size(); h++){
								if(dwarfs.get(h).getId() == fatherid){
									dwarfs.get(i).setFarther(dwarfs.get(h));
									break;
								}
							}
						}
						else if(type.contentEquals("child")){
							fstNode = fstNmElmntLst.item(j);
							fstElmnt = (Element) fstNode;
							scnNmElmntLst = fstElmnt.getElementsByTagName("hfid");
							fstNmElmnt = (Element) scnNmElmntLst.item(0);
							fstNm = fstNmElmnt.getChildNodes();
							Integer childid = Integer.valueOf((String) fstNm.item(0).getNodeValue());
							for(int h=0;h < dwarfs.size(); h++){
								if(dwarfs.get(h).getId() == childid){
									children.add(dwarfs.get(h));
									break;
								}
							}
						}
					}
				}
				dwarfs.get(i).setChildren(children);
				dwarfs.get(i).print();

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
