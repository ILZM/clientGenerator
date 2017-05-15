package com.itechtopus.sshgenerator.model;

import java.util.Date;

/**
 * Created by yerlan on 14/05/17.
 */
public class ClientAccount extends ModelParent {

  public Integer id;
  public Client client;
  public String a_number;
  public Float money;
  public Date registeredAt;

  public ClientAccount(Integer id, Client client, String a_number, Float money, Date registeredAt) {
    this.id = id;
    this.client = client;
    this.a_number = a_number;
    this.money = money;
    this.registeredAt = registeredAt;
  }

  public ClientAccount() {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ClientAccount that = (ClientAccount) o;

    if (client != null ? !client.equals(that.client) : that.client != null) return false;
    if (a_number != null ? !a_number.equals(that.a_number) : that.a_number != null) return false;
    if (money != null ? !money.equals(that.money) : that.money != null) return false;
    return registeredAt != null ? registeredAt.equals(that.registeredAt) : that.registeredAt == null;
  }

  @Override
  public int hashCode() {
    int result = client != null ? client.hashCode() : 0;
    result = 31 * result + (a_number != null ? a_number.hashCode() : 0);
    result = 31 * result + (money != null ? money.hashCode() : 0);
    result = 31 * result + (registeredAt != null ? registeredAt.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ClientAccount #" + id +
        ", client=" + client.getAbbr() +
        ", " + a_number + '\'' +
        ", balance: " + money +
        ", registeredAt:" + registeredAt;
  }
}
