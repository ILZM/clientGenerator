package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;
import com.itechtopus.sshgenerator.model.TransactionType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itechtopus.sshgenerator.generator.Constants.*;
import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class TransactionGenerator {

  private static TransactionGenerator instance;

  private final Set<TransactionType> types = Util.newSet();
  private final Map<ClientAccount, Date> lastTransactionMap = Util.newMap();

  private final Map<ClientAccount, Float> accountBalances = Util.newMap();

  public static TransactionGenerator getInstance() {
    if (instance == null)
      instance = new TransactionGenerator();
    return instance;
  }

  public TransactionGenerator() {
    if (types.isEmpty())
      processTypes();
  }

  private void processTypes() {
    int typeId = 0;
    for (Map.Entry<String, String> pair : StaticBase.transactionMap.entrySet()){
      types.add(new TransactionType(typeId++, pair.getValue(), pair.getKey()));
    }
  }


  /**
   * Generates random transaction with null id for account in parameter
   * @param account - account to create transaction for
   * @param fromDate - if needed date to begin from
   * @return a transaction instance with null id
   */
  public ClientAccountTransaction generateRandomTransaction(ClientAccount account, Date fromDate) {
    if (fromDate == null)
      fromDate = getLastTransactionTimeForAccount(account);
    else
      lastTransactionMap.put(account, fromDate);

    ClientAccountTransaction transaction = new ClientAccountTransaction();
    transaction.account = account;
    float balance = accountBalances.get(account) != null ? accountBalances.get(account) : account.money;
    if (balance > 0) {
      transaction.type = getRandomType();
      transaction.money = getRandomMoneyAmount(transaction.type, balance);
    } else {
      transaction.type = getRandomDepositTransaction();
      transaction.money = getRandomMoneyAmount(transaction.type);
    }
    accountBalances.put(account, balance + transaction.money);
    transaction.finishedAt = new Timestamp(fromDate.getTime() + TRANSACTION_TIME_DIFF_MIN + (long)(rnd.nextFloat() * (TRANSACTION_TIME_DIFF_MAX - TRANSACTION_TIME_DIFF_MIN)));
    return transaction;
  }

  /**
   * Geberates bunch of transactions for account from date "fromDate"
   * @param number - amount of transactions to generate -1 if it need to be random
   * @param account -  account to generate transactions for
   * @param fromDate - null if nt nessesary - tranmsactions will be generated from either
   *                 last generated transaction for this account, or
   *                 date when account itself was registered
   *                 if not null - generates bunch transactions chronologically from
   *                 passed through parameters date
   * @return - list of transactions with no id's (null)
   */
  public List<ClientAccountTransaction> generateRandomTransactions(int number, ClientAccount account, Date fromDate) {
    if (number < 0)
      number = TRANSACTIONS_MIN + rnd.nextInt(TRANSACTIONS_PER_ACCOUNT - TRANSACTIONS_MIN);
    List<ClientAccountTransaction> transactions = Util.newList();
    for (int i = 0; i < number; i++)
      transactions.add(generateRandomTransaction(account, i == 0 ? fromDate : null));
    return transactions;
  }

  private Date getLastTransactionTimeForAccount(ClientAccount account) {
    Date date = lastTransactionMap.get(account);
    if (date == null)
      date = account.registeredAt;
    return date;
  }

  private TransactionType getRandomType() {
    return Util.getRandom(types);
  }


  private TransactionType getRandomDepositTransaction() {
    return Util.getRandom(types.stream().filter(tr -> !isWithdrawing(tr)).collect(Collectors.toList()));
  }

  private float getRandomMoneyAmount(TransactionType type) {
    return getRandomMoneyAmount(type, MAXIMUM_MONEY_AMOUNT_PER_TRANSACTION);
  }

  private float getRandomMoneyAmount(TransactionType type, float maximum) {
    float money = Math.abs(rnd.nextFloat() * (maximum <= MAXIMUM_MONEY_AMOUNT_PER_TRANSACTION ? maximum : MAXIMUM_MONEY_AMOUNT_PER_TRANSACTION));
    int mult = getMultiplierFor(type);
    money += mult;
    money *= mult;
    if (isWithdrawing(type))
      money *= -1;
    return money;
  }

  private int getMultiplierFor(TransactionType type) {
    switch (type.code) {
      case "LKUYNHLKJH.W" : return 100000;
      case "SKUNFWWO23.D" : return 500;
      case "AAAFDDVKJG.D" : return 100;
    }
    return 1;
  }

  private boolean isWithdrawing(TransactionType  type) {
    return type.code.endsWith("W");
  }

}
