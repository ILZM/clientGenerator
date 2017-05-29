package com.itechtopus.sshgenerator.storage;

import com.itechtopus.sshgenerator.generator.Util;
import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;
import com.itechtopus.sshgenerator.model.ModelParent;
import com.itechtopus.sshgenerator.to.ClientPI;
import com.itechtopus.sshgenerator.worker.Saver;

import java.util.List;
import java.util.Stack;

public class MainStorage {

  public static final List<ClientPI> clientPIs = Util.newList();

  public static final List<ClientAccount> accounts = Util.newList();

  public static final List<ClientAccountTransaction> transactions = Util.newList();

  public static StringBuffer operations = new StringBuffer();

  private static int duplicateCounter = 0;

  public static Stack<String> duplicateIds = new Stack<>();

  private static boolean needDuplicate(){
    if (!Parameters.GENERATE_DUPLICATES)
      return false;
    if (duplicateCounter++ == Parameters.DUPLICATES_GENERATION_PERIOD) {
      duplicateCounter = 0;
      return true;
    }
    return false;
  }

  public static Saver clientPISaver = new Saver() {
    @Override
    public void save(ModelParent entity) {
      if (!(entity instanceof ClientPI))
        throw new IllegalArgumentException("Client saver can only save clientPIs");
      ClientPI pi = (ClientPI) entity;
      if (needDuplicate()) {
        if (duplicateIds.size() > 0)
          pi.client.cia_id = duplicateIds.pop();
        else
          insertIntoList(clientPIs, pi);
      }
      clientPIs.add(pi);
    }
  };

  public  static Saver AccountSaver = new Saver() {
    @Override
    public void save(ModelParent entity) {
      if (!(entity instanceof ClientAccount))
        throw new IllegalArgumentException("Account saver can only save ClientAccounts");
      ClientAccount account = (ClientAccount) entity;
      accounts.add(account);
      operations.append(Util.simplify(Util.convertToXML(account))).append('\n');
      if (needDuplicate())
        insertIntoList(accounts, (ClientAccount) entity);
    }
  };

  public static Saver transactionSaver = new Saver() {
    @Override
    public void save(ModelParent entity) {
      if (!(entity instanceof ClientAccountTransaction))
        throw new IllegalArgumentException("Transaction saver can only save ClientAccountTransactions");
      ClientAccountTransaction transaction = (ClientAccountTransaction) entity;
      transactions.add(transaction);
      operations.append(Util.simplify(Util.convertToXML(transaction))).append('\n');
      if (needDuplicate())
        insertIntoList(transactions, (ClientAccountTransaction) entity);
    }
  };

  private static <T> void insertIntoList(List<T> list, T entity) {
    T first = list.get(0);
    list.set(0, entity);
    list.add(first);
  }


}
