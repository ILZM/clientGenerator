package com.itechtopus.sshgenerator.model;

import com.itechtopus.sshgenerator.model.enums.Gender;

import java.util.Date;

public class Client extends ModelParent{

  public Integer id;
  public String cia_id;
  public String surname;
  public String name;
  public String patronymic;
  public Gender gender;
  public Date birthDate;
  public Charm charm;

  public Client() {
  }

  public Client(Integer id, String surname, String name, String patronymic, Gender gender, Date birthDate, Charm charm) {
    this.id = id;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.gender = gender;
    this.birthDate = birthDate;
    this.charm = charm;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Client client = (Client) o;

    if (surname != null ? !surname.equals(client.surname) : client.surname != null) return false;
    if (name != null ? !name.equals(client.name) : client.name != null) return false;
    if (patronymic != null ? !patronymic.equals(client.patronymic) : client.patronymic != null) return false;
    if (gender != client.gender) return false;
    return birthDate != null ? birthDate.equals(client.birthDate) : client.birthDate == null;
  }

  @Override
  public int hashCode() {
    int result = surname != null ? surname.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
    result = 31 * result + (gender != null ? gender.hashCode() : 0);
    result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Client #" + id +
        " " + surname + '\'' +
        " " + name + '\'' +
        " " + patronymic + '\'' +
        " " + (gender == Gender.MALE ? "муж" : "жен") +
        " " + birthDate +
        "г.р. <" + charm.name +
        ">";
  }

  public String getAbbr() {
    return surname + " " +
        name.toUpperCase().charAt(0) + ". " +
        patronymic.toUpperCase().charAt(0) + '.';
  }
}
