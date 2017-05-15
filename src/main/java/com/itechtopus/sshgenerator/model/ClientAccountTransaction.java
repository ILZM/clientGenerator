package com.itechtopus.sshgenerator.model;

import java.sql.Timestamp;

/**
 * Created by yerlan on 14/05/17.
 */
public class ClientAccountTransaction extends ModelParent {

  public Integer id;
  public ClientAccount account;
  public float money;
  public Timestamp finishedAt;
  public TransactionType type;

  public ClientAccountTransaction(Integer id, ClientAccount account, float money, Timestamp finishedAt, TransactionType type) {
    this.id = id;
    this.account = account;
    this.money = money;
    this.finishedAt = finishedAt;
    this.type = type;
  }

  public ClientAccountTransaction() {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ClientAccountTransaction that = (ClientAccountTransaction) o;

    if (Float.compare(that.money, money) != 0) return false;
    if (account != null ? !account.equals(that.account) : that.account != null) return false;
    if (finishedAt != null ? !finishedAt.equals(that.finishedAt) : that.finishedAt != null) return false;
    return type != null ? type.equals(that.type) : that.type == null;
  }

  @Override
  public int hashCode() {
    int result = account != null ? account.hashCode() : 0;
    result = 31 * result + (money != +0.0f ? Float.floatToIntBits(money) : 0);
    result = 31 * result + (finishedAt != null ? finishedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CATransaction #" + id +
        ", account #" + account.a_number +
        ", money=" + money +
        ", finishedAt=" + finishedAt +
        ", type=" + type.code;
  }
}
