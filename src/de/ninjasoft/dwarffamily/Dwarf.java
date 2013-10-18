package de.ninjasoft.dwarffamily;

import java.util.ArrayList;

public class Dwarf {
	private Integer id;
	private String name;
	private String deathday;
	private String birthday;
	private String deathyear;
	private String birthyear;
	private String gender;
	private Dwarf mother;
	private Dwarf farther;
	private ArrayList<Dwarf> children;
	private Dwarf spouse;

	
	public Dwarf() {

	}
	

	public void print() {
		System.out.println("ID: " + id);
		System.out.println("Name: " + name);
		System.out.println("Gender " + gender);
		System.out.println("Birthdate " + birthday + " " + birthyear);
		System.out.println("Deathdate: " + deathday + " " + deathyear);
		if (mother != null) {
			System.out.println("Mother: " + mother.getName());
		}
		if (farther != null) {
			System.out.println("Father: " + farther.getName());
		}
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				System.out.println("Child: " + children.get(i).getName());
			}
		}
		if (spouse != null) {
			System.out.println("Spouse: " + spouse.getName());
		}
		System.out.println();
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Dwarf getMother() {
		return mother;
	}

	public void setMother(Dwarf mother) {
		this.mother = mother;
	}

	public Dwarf getFather() {
		return farther;
	}

	public void setFather(Dwarf farther) {
		this.farther = farther;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ArrayList<Dwarf> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Dwarf> children) {
		this.children = children;
	}

	public Dwarf getSpouse() {
		return spouse;
	}

	public void setSpouse(Dwarf spouse) {
		this.spouse = spouse;
	}
	public String getDeathday() {
		return deathday;
	}
	public void setDeathday(String deathday) {
		this.deathday = deathday;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getDeathyear() {
		return deathyear;
	}
	public void setDeathyear(String deathyear) {
		this.deathyear = deathyear;
	}
	public String getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(String birthyear) {
		this.birthyear = birthyear;
	}
}
