package com.itechtopus.sshgenerator.model;

import com.itechtopus.sshgenerator.model.enums.PhoneType;

public class ClientPhone extends ModelParent {

  public Client client;
  public String pNumber;
  public PhoneType type;

  public ClientPhone(Client client, String pNumber, PhoneType type) {
    this.client = client;
    this.pNumber = pNumber;
    this.type = type;
  }

  public ClientPhone() {

  }

  @Override
  public String toString() {
    return "Phone for client: " + client.getAbbr() +
        ", " + pNumber +
        ", " + type;
  }
}
