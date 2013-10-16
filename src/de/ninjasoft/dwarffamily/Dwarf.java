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

	public Dwarf(Integer id, String name, String gender, Dwarf mother, Dwarf farther, ArrayList<Dwarf> children, String birth, String death) {
		this.setId(id);
		this.setName(name);
		this.setGender(gender);
		this.setMother(mother);
		this.setFarther(farther);
		this.setChildren(children);
		this.setBirth(birth);
		this.setDeath(death);
	}

	public void print() {
		System.out.println(id);
		System.out.println(name);
		System.out.println(gender);
		System.out.println(birth);
		System.out.println(death);
		if (mother != null) {
			System.out.println(mother.getName());
		}
		if (farther != null) {
			System.out.println(farther.getName());
		}
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				System.out.println(children.get(i).getName());
			}
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

	public Dwarf getFarther() {
		return farther;
	}

	public void setFarther(Dwarf farther) {
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
}
