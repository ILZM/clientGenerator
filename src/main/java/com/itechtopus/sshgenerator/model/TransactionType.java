package com.itechtopus.sshgenerator.model;

public class TransactionType extends ModelParent {

  public Integer id;
  public String name;
  public String code;

  public TransactionType(Integer id, String name, String code) {
    this.id = id;
    this.name = name;
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TransactionType that = (TransactionType) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return code != null ? code.equals(that.code) : that.code == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TransactionType #" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'';
  }
}
