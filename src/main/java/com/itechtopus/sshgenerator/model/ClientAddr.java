package com.itechtopus.sshgenerator.model;

import com.itechtopus.sshgenerator.model.enums.AddressType;

public class ClientAddr extends ModelParent {

  public Client client;
  public AddressType type;
  public String street;
  public String house;
  public String flat;

  public ClientAddr(Client client, AddressType type, String street, String house, String flat) {
    this.client = client;
    this.type = type;
    this.street = street;
    this.house = house;
    this.flat = flat;
  }

  public ClientAddr() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ClientAddr that = (ClientAddr) o;

    if (type != that.type) return false;
    if (street != null ? !street.equals(that.street) : that.street != null) return false;
    if (house != null ? !house.equals(that.house) : that.house != null) return false;
    return flat != null ? flat.equals(that.flat) : that.flat == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (house != null ? house.hashCode() : 0);
    result = 31 * result + (flat != null ? flat.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Address for client: " + client.getAbbr() +
        ", " + type +
        ", '" + street + '\'' +
        " " + house +
        "-" + flat;
  }
}
