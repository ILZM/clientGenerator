package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;
import com.itechtopus.sshgenerator.model.ClientAddr;
import com.itechtopus.sshgenerator.model.ClientPhone;
import com.itechtopus.sshgenerator.model.enums.AddressType;
import com.itechtopus.sshgenerator.model.enums.PhoneType;
import com.itechtopus.sshgenerator.to.AccountTo;
import com.itechtopus.sshgenerator.to.ClientPI;
import com.itechtopus.sshgenerator.to.ClientTo;
import com.itechtopus.sshgenerator.worker.Saver;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itechtopus.sshgenerator.generator.Constants.*;
import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class AllInfoGenerator {

  private static AllInfoGenerator instance;

  private final Map<Integer, ClientTo> clientToMap = Util.newMap();

  private final ClientGenerator clientGenerator = ClientGenerator.getInstance();
  private final AddressGenerator addressGenerator = AddressGenerator.getInstance();
  private final PhoneGenerator phoneGenerator = PhoneGenerator.getInstance();
  private final AccountGenerator accountGenerator = AccountGenerator.getInstance();
  private final TransactionGenerator transactionGenerator = TransactionGenerator.getInstance();

  public static AllInfoGenerator get() {
    return instance;
  }

  private final Saver saveClientPI;
  private final Saver saveAccount;
  private final Saver saveTransaction;

  public AllInfoGenerator(Saver saveClientPI, Saver saveAccount, Saver saveTransaction) {
    instance = this;
    this.saveClientPI = saveClientPI;
    this.saveAccount = saveAccount;
    this.saveTransaction = saveTransaction;
  }

  public List<ClientTo> getAllTos() {
    return Util.getListOfCollection(clientToMap.values());
  }

 /* private void generateData(int amount) {
    for (Client client : clientGenerator.getAllClients()) {
      clientToMap.put(client.id, generateNewClientTo(client));
    }
  }*/

  /**
   * Generates and triggers to store new ClientParticular info (client, addresses, phones)
   * @return new clientPI instance
   */
  public ClientPI generateNewClientPI() {
    ClientPI clientPI = new ClientPI();
    clientPI.client = clientGenerator.generateNewClient();

    // Generating all addresses (two)
    for (AddressType addressType : AddressType.values())
      clientPI.addresses.put(addressType, addressGenerator.generateRandomAddress(clientPI.client, addressType));


    // Generating all phonetype phones (if not home - there can be plenty of them, up to 3)
    for (PhoneType phoneType : PhoneType.values()) {
      int count = 1;
      if (phoneType != PhoneType.HOME)
        count = rnd.nextInt(4);
      for (int i = 0; i < count; i++)
        clientPI.phones.add(phoneGenerator.generatePhone(clientPI.client, phoneType));
    }

    saveClientPI.save(clientPI);
    return clientPI;
  }

  private ClientTo generateNewClientTo(Client client) {
    // Generating client itself
    ClientTo clientTo = new ClientTo();
    clientTo.client = client;

    // Generating all addresses (two)
    for (AddressType addressType : AddressType.values())
      clientTo.addresses.put(addressType, addressGenerator.generateRandomAddress(client, addressType));

    // Generating all phonetype phones (if not home - there can be plenty of them, up to 3)
    for (PhoneType phoneType : PhoneType.values()) {
      int count = 1;
      if (phoneType != PhoneType.HOME)
        count = rnd.nextInt(4);
      for (int i = 0; i < count; i++)
        clientTo.phones.add(phoneGenerator.generatePhone(client, phoneType));
    }

    // Generating accounts
    generateAccountTos(clientTo);

    return clientTo;
  }

  private void generateAccountTos(ClientTo clientTo) {
    int count = ACCOUNTS_MIN + rnd.nextInt(ACCOUNTS_PER_CLIENT - ACCOUNTS_MIN);
    for (int i = 0; i < count; i++) {
      AccountTo accountTo = new AccountTo();
      accountTo.account = accountGenerator.generateAccount(clientTo.client);
      saveAccount.save(accountTo.account);
      accountTo.transactions = transactionGenerator.generateRandomTransactions(-1, accountTo.account, null);
      for (ClientAccountTransaction transaction : accountTo.transactions)
        saveTransaction.save(transaction);
    }
  }

  public String convertToXML(ClientTo clientTo) {
    StringBuilder sb = new StringBuilder();
      sb.append("\t<client id=\"" + clientTo.client.id + "\"> \n");
        sb.append("\t\t<surname value=\"" + clientTo.client.surname + "\" /> \n");
        sb.append("\t\t<name value=\"" + clientTo.client.name + "\" /> \n");
        sb.append("\t\t<patronymic value=\"" + clientTo.client.patronymic + "\" /> \n");
        sb.append("\t\t<gender value=\"" + clientTo.client.gender.toString() + "\" /> \n");
        sb.append("\t\t<charm value=\"" + clientTo.client.charm.name + "\" /> \n");
        sb.append("\t\t<birth value=\"" + format(clientTo.client.birthDate) + "\" /> \n");
        sb.append("\t\t<address> \n");
          ClientAddr homAddr = clientTo.addresses.get(AddressType.FACT);
          sb.append("\t\t\t<fact street=\"" + homAddr.street + "\" house=\"" + homAddr.house + "\" flat=\"" + homAddr.flat + "\"/> \n");
          ClientAddr regAddr = clientTo.addresses.get(AddressType.REG);
          sb.append("\t\t\t<register street=\"" + regAddr.street + "\" house=\"" + regAddr.house + "\" flat=\"" + regAddr.flat + "\"/> \n");
        sb.append("\t\t</address> \n");
        for (ClientPhone phone : clientTo.phones)
          sb.append("\t\t<" + tagFor(phone.type) + ">" + phone.pNumber + "</" + tagFor(phone.type) + "> \n");
        sb.append("\t</client> \n \n");
    return sb.toString();
  }

  public String convertToXML(Collection<ClientTo> clientTos) {
    StringBuilder sb = new StringBuilder("<cia> \n");
    for (ClientTo clientTo : clientTos)
      sb.append(convertToXML(clientTo));
    sb.append("</cia>");
    return sb.toString();
  }

  public String getUnreadableXML(Collection<ClientTo> clientTos) {
    return convertToXML(clientTos).replaceAll(" \n", "").replaceAll("\t", "");
  }

  private String tagFor(PhoneType type) {
    switch (type) {
      case HOME   : return "homePhone";
      case WORK   : return "workPhone";
      case MOBILE : return "mobilePhone";
    }
    return "";
  }

  private String format(Date birthDate) {
    return DATE_FORMAT.format(birthDate);
  }

  private static String format2(Date date) {
    return DATE_FORMAT2.format(date);
  }

  public static void main(String[] args){
    AllInfoGenerator aig = AllInfoGenerator.get();
    System.out.println(aig.convertToXML(aig.getAllTos()));
    System.out.println("\n\n\n");
    System.out.println(aig.getUnreadableXML(aig.getAllTos().subList(0, 10)));
    String fileName = "/home/yerlan/xmls/" + format2(new Date()) + "-cia.xml";
    String fileName2 = "/home/yerlan/xmls/" + format2(new Date()) + "-cia-right.xml";

    File file = new File(fileName);
    File file2 = new File(fileName2);

    try (
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2))
    ) {
      bw.write(aig.convertToXML(aig.getAllTos()));
      bw2.write(aig.getUnreadableXML(aig.getAllTos()));
      bw.flush();
      bw2.flush();
    } catch (Exception e) {
      System.out.println("Unsuccessful. Message:" + e.getLocalizedMessage());
    }
    System.out.println("Records successfully added to the file ");
  }



}
