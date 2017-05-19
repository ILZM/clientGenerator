package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;
import com.itechtopus.sshgenerator.model.ClientAddr;
import com.itechtopus.sshgenerator.model.ClientPhone;
import com.itechtopus.sshgenerator.model.enums.AddressType;
import com.itechtopus.sshgenerator.model.enums.PhoneType;
import com.itechtopus.sshgenerator.to.ClientPI;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.itechtopus.sshgenerator.generator.Constants.DATE_FORMAT;
import static com.itechtopus.sshgenerator.generator.Constants.DATE_FORMAT_XML;

public class Util {

  public static final Random rnd = new Random(System.currentTimeMillis());

  public static <T> List<T> getListOfCollection(Collection<T> values) {

    return new ArrayList<T>(values);
  }

  public static <T> T getRandom(Collection<T> collection) {
    if (collection == null || collection.isEmpty())
      return null;
    return (T)getRandom(collection.toArray());
  }

  public static <T> T getRandom(T... collection) {
    if (collection == null || collection.length == 0)
      return null;
    return collection[rnd.nextInt(collection.length)];
  }

  public static <K, V> Map<K,V> newMap() {
    return new ConcurrentHashMap<K, V>();
  }

  public static <T> List<T> newList() {
    return new CopyOnWriteArrayList<T>();
  }

  public static <T> Set<T> newSet() {
    return new CopyOnWriteArraySet<T>();
  }

  public static <T, K> int getMapValuesTotalSize(Map<T, List<K>> map) {
    return map.values().stream().mapToInt(list -> list.size()).sum();
  }


  public static String convertToXML(ClientPI clientPI) {
    StringBuilder sb = new StringBuilder();
    sb.append("\t<client id=\"" + clientPI.client.id + "\"> \n");
    sb.append("\t\t<surname value=\"" + clientPI.client.surname + "\" /> \n");
    sb.append("\t\t<name value=\"" + clientPI.client.name + "\" /> \n");
    sb.append("\t\t<patronymic value=\"" + clientPI.client.patronymic + "\" /> \n");
    sb.append("\t\t<gender value=\"" + clientPI.client.gender.toString() + "\" /> \n");
    sb.append("\t\t<charm value=\"" + clientPI.client.charm.name + "\" /> \n");
    sb.append("\t\t<birth value=\"" + format(clientPI.client.birthDate) + "\" /> \n");
    sb.append("\t\t<address> \n");
    ClientAddr homAddr = clientPI.addresses.get(AddressType.FACT);
    sb.append("\t\t\t<fact street=\"" + homAddr.street + "\" house=\"" + homAddr.house + "\" flat=\"" + homAddr.flat + "\"/> \n");
    ClientAddr regAddr = clientPI.addresses.get(AddressType.REG);
    sb.append("\t\t\t<register street=\"" + regAddr.street + "\" house=\"" + regAddr.house + "\" flat=\"" + regAddr.flat + "\"/> \n");
    sb.append("\t\t</address> \n");
    for (ClientPhone phone : clientPI.phones)
      sb.append("\t\t<" + tagFor(phone.type) + ">" + phone.pNumber + "</" + tagFor(phone.type) + "> \n");
    sb.append("\t</client> \n \n");
    return sb.toString();
  }

  public static String convertToXML(Collection<ClientPI> clientPIS) {
    StringBuilder sb = new StringBuilder("<cia> \n");
    for (ClientPI clientPI : clientPIS)
      sb.append(convertToXML(clientPI));
    sb.append("</cia>");
    return sb.toString();
  }

  public static String getUnreadableXML(Collection<ClientPI> clientPIS) {
    return simplify(convertToXML(clientPIS));
  }

  public static String simplify(String value) {
    return value.replaceAll(" \n", "").replaceAll("\t", "").replaceAll("\n", "");
  }

  private static String tagFor(PhoneType type) {
    switch (type) {
      case HOME   : return "homePhone";
      case WORK   : return "workPhone";
      case MOBILE : return "mobilePhone";
    }
    return "";
  }

  public static String convertToXML(ClientAccountTransaction transaction) {
    StringBuilder sb = new StringBuilder("{\n");
    sb.append(convertPair("type", "transaction"));
    sb.append(convertPair("money", format(transaction.money)));
    sb.append(convertPair("finished_at", formatXML(transaction.finishedAt)));
    sb.append(convertPair("transaction_type", transaction.type.name));
    sb.append(convertPair("account_number", transaction.account.a_number));
    sb.append("}\n");
    return sb.toString();
  }

  private static String convertPair(String key, String value) {
    return "\t\"" + key + "\": \"" + value + "\"\n";
  }

  public static String convertToXML(ClientAccount newAccount) {
    StringBuilder sb = new StringBuilder("{\n");
    sb.append(convertPair("type", "new_account"));
    sb.append(convertPair("client_id", format(newAccount.client.id)));
    sb.append(convertPair("account_number", format(newAccount.a_number)));
    sb.append(convertPair("registered_at", formatXML(newAccount.registeredAt)));
    sb.append("}\n");
    return sb.toString();
  }

  private static String format(String account_number) {
    return account_number.substring(0, 10) + "-" +
        account_number.substring(10, 15) + "-" +
        account_number.substring(15, 21) + "-" +
        account_number.substring(21);
  }

  private static String format(Date birthDate) {
    return DATE_FORMAT.format(birthDate);
  }

  private static DecimalFormat moneyXMLFormat = new DecimalFormat("###,###,###,###,###.00");
  private static DecimalFormat clientIdFormat = new DecimalFormat("##############,###");

  private static String format(float money) {
    return ((money < 0 ? "-" : "+") + moneyXMLFormat.format(Math.abs(money))).replaceAll(",", "_");
  }

  private static String format(int id) {
    return clientIdFormat.format(id).replaceAll(",", "-");
  }

  private static String formatXML(Date date) {
    return DATE_FORMAT_XML.format(date);
  }

  public static void main(String[] args) throws InterruptedException {
    /*List<String> list1 = Arrays.asList("11", "22", "33", "44", "55");
    List<String> list2 = Arrays.asList("66", "77", "88", "99", "10");
    Map<String, List<String>> map = new HashMap<>();
    map.put("line1", list1);
    map.put("line2", list2);
    System.out.println(getMapValuesTotalSize(map));*/

  }


}
