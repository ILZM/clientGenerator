package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;
import com.itechtopus.sshgenerator.model.enums.AddressType;
import com.itechtopus.sshgenerator.model.enums.PhoneType;
import com.itechtopus.sshgenerator.storage.MainStorage;
import com.itechtopus.sshgenerator.to.AccountTo;
import com.itechtopus.sshgenerator.to.ClientPI;
import com.itechtopus.sshgenerator.to.ClientTo;
import com.itechtopus.sshgenerator.worker.Saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.itechtopus.sshgenerator.generator.Constants.*;
import static com.itechtopus.sshgenerator.generator.Util.convertToXML;
import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class AllInfoGenerator {

  private static AllInfoGenerator instance;

//  public final Map<Integer, ClientTo> clientToMap = Util.newMap();

  private final ClientGenerator clientGenerator = ClientGenerator.getInstance();
  private final AddressGenerator addressGenerator = AddressGenerator.getInstance();
  private final PhoneGenerator phoneGenerator = PhoneGenerator.getInstance();
  private final AccountGenerator accountGenerator = AccountGenerator.getInstance();
  private final TransactionGenerator transactionGenerator = TransactionGenerator.getInstance();

  public static AllInfoGenerator get() {
    if (instance == null)
      instance = new AllInfoGenerator(MainStorage.clientPISaver, MainStorage.accountSaver, MainStorage.transactionSaver);
    return instance;
  }

  private final Saver saveClientPI;
  private final Saver saveAccount;
  private final Saver saveTransaction;

  public final Set<ClientPI> clientPIS = Util.newSet();
  public final Map<ClientPI, List<ClientAccount>> accountMap = Util.newMap();

  private final AtomicInteger clientIdCounter = new AtomicInteger(10000);
  private final AtomicInteger accountIdCounter = new AtomicInteger(10000);
  private final AtomicInteger transactionIdCounter = new AtomicInteger(10000);


  public AllInfoGenerator(Saver saveClientPI, Saver saveAccount, Saver saveTransaction) {
    instance = this;
    this.saveClientPI = saveClientPI;
    this.saveAccount = saveAccount;
    this.saveTransaction = saveTransaction;
  }

  public Long getAccountsSize() {
    long sum = 0;
    for (ClientPI client : accountMap.keySet())
      sum += accountMap.get(client).size();
    return sum;
  }

/*  public List<ClientTo> getAllTos() {
    return Util.getListOfCollection(clientToMap.values());
  }*/

 /* private void generateData(int amount) {
    for (Client client : clientGenerator.getAllClients()) {
      clientToMap.put(client.id, generateNewClientTo(client));
    }
  }*/

  /**
   * Generates and triggers to store new ClientParticular info (client, addresses, phones)
   * Also stores in clientPIS List
   * @return new clientPI instance
   */
  public ClientPI generateNewClientPI() {
    ClientPI clientPI = new ClientPI();
    do {
      clientPI.client = clientGenerator.generateNewClient();
    } while (clientPIS.contains(clientPI));

    clientPI.client.id = clientIdCounter.incrementAndGet();

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

    clientPIS.add(clientPI);
    saveClientPI.save(clientPI);
    return clientPI;
  }

  public ClientPI getRandomClientPI() {
    ClientPI clientPI = Util.getRandom(clientPIS);
    return  clientPI != null ? clientPI : generateNewClientPI();
  }

  public ClientAccount generateNewAccount(ClientPI clientPi){
    if (clientPi == null)
      clientPi = getRandomClientPI();
    ClientAccount account = accountGenerator.generateAccount(clientPi.client);
    account.id = accountIdCounter.incrementAndGet();
    List<ClientAccount> accounts = accountMap.get(clientPi);
    if (accounts == null) {
      accounts = Util.newList();
      accountMap.put(clientPi, accounts);
    }
    accounts.add(account);
    saveAccount.save(account);
    return account;
  }

  public ClientAccount generateNewAccount() {
    return generateNewAccount(getRandomClientPI());
  }

  public ClientAccount getRandomAccount() {
    List<ClientAccount> accounts = accountMap.get(getRandomClientPI());
    return accounts != null && !accounts.isEmpty() ? Util.getRandom(accounts) : generateNewAccount();
  }

  public ClientAccountTransaction generateNewTransaction(ClientAccount account) {
    if (account == null)
      account = generateNewAccount();
    ClientAccountTransaction transaction = transactionGenerator.generateRandomTransaction(account, null);
    transaction.id = transactionIdCounter.incrementAndGet();
    saveTransaction.save(transaction);
    return transaction;
  }

  public ClientAccountTransaction generateNewTransaction() {
    return generateNewTransaction(getRandomAccount());
  }

  private ClientTo generateNewClientTo(Client client) {
    // Generating client itself
    ClientTo clientTo = new ClientTo();
    clientTo.client = client;
    clientTo.client.id = clientIdCounter.incrementAndGet();

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


  private static String format2(Date date) {
    return DATE_FORMAT2.format(date);
  }


  public static void main(String[] args){
    AllInfoGenerator aig = AllInfoGenerator.get();


    System.out.println(convertToXML(aig.clientPIS));
    System.out.println("\n\n\n");

    String fileName = "/home/jgolibzhan/xmls/asdf-cia.xml";

    File file = new File(fileName);

    try (
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    ) {

      for (int i = 0; i < 200; i++) aig.generateNewClientPI();

      bw.write(convertToXML(aig.clientPIS));
      bw.flush();
    } catch (Exception e) {
      System.out.println("Unsuccessful. Message:" + e.getLocalizedMessage());
    }
    System.out.println("Records successfully added to the file ");
  }


}
