package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAccount;

import java.util.Date;
import java.util.List;

import static com.itechtopus.sshgenerator.generator.Constants.MAXIMUM_BALANCE;
import static com.itechtopus.sshgenerator.generator.Constants.MINIMAL_BALANCE;
import static com.itechtopus.sshgenerator.generator.Util.getRandom;
import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class AccountGenerator {

  private static AccountGenerator instance;

  public static AccountGenerator getInstance() {
    if (instance == null)
      instance = new AccountGenerator();
    return instance;
  }

  /**
   * Returns randomly generated instance of Client Account
   * @param client - client to generate account for
   * @return new randomly generated ClientAccount instance with null id
   */
  public ClientAccount generateAccount(Client client) {
    ClientAccount account = new ClientAccount();
    account.client = client;
    account.a_number = generateAccountNumber();
    account.money = getRandomBalance();
    account.registeredAt = getRandomDate();
    return account;
  }

  /**
   *
   * @param number - number of accounts need to be generated
   * @param client - client to create accoutns for
   * @return a list of "number" accounts with null id for client "client"
   */
  public List<ClientAccount> generateAccounts(int number, Client client) {
    List<ClientAccount> accounts = Util.newList();
    for (int i = 0; i < number; i++)
      accounts.add(generateAccount(client));
    return accounts;
  }

  private Date getRandomDate() {
    return new Date(Constants.JAN_1_2010 + (long)(rnd.nextFloat() * 1000 * 60 * 60 * 24 * 365 * 4));
  }

  private String generateAccountNumber() {
    StringBuilder s = new StringBuilder();
    while (s.length() < Constants.ACCOUNT_NUMBER_LENGTH) {
      s.append("" + rnd.nextInt(10));
      if (s.length() == 5)
        s.append("KZ");
    }
    return s.toString();
  }

  private Float getRandomBalance() {
    return MINIMAL_BALANCE + rnd.nextFloat() * (MAXIMUM_BALANCE - MINIMAL_BALANCE);
  }

  /*public static void main(String[] args) throws InterruptedException {
    while (true) {
      System.out.println(getInstance().generateAccounts(CLIENT));
      Thread.sleep(250);
    }
  }
*/

}
