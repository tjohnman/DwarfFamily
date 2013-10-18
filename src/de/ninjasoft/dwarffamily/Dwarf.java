package de.ninjasoft.dwarffamily;

import java.util.ArrayList;

public class Dwarf {
	private Integer id;
	private String name;
	private String death;
	private String birth;
	private String gender;
	private Dwarf mother;
	private Dwarf farther;
	private ArrayList<Dwarf> children;
	private Dwarf spouse;

	public Dwarf(Integer id, String name, String gender, Dwarf mother, Dwarf father, ArrayList<Dwarf> children, String birth, String death, Dwarf spouse) {
		this.setId(id);
		this.setName(name);
		this.setGender(gender);
		this.setMother(mother);
		this.setFather(father);
		this.setChildren(children);
		this.setBirth(birth);
		this.setDeath(death);
		this.setSpouse(spouse);
	}

	public void print() {
		System.out.println("ID: " + id);
		System.out.println("Name: " + name);
		System.out.println("Gender " + gender);
		System.out.println("Birthdate " + birth);
		System.out.println("Deathdate: " + death);
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

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
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
}
