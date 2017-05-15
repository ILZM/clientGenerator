package com.itechtopus.sshgenerator.model;

public class Charm extends ModelParent{

  public Integer id;
  public String name;
  public String description;
  public float energy;

  public Charm(Integer id, String name, String description, float energy) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.energy = energy;
  }


  @Override
  public String toString() {
    return "Charm #" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", energy=" + energy;
  }
}
