package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Charm;
import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.enums.Gender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
  public static final int CLIENTS_TO_GENERATE = 200;
  public static final int MAX_CLIENT_AGE_DIFF = 10;  // in years
  public static final int ACCOUNTS_PER_CLIENT = 5;
  public static final int ACCOUNTS_MIN = 2;
  public static final int TRANSACTIONS_PER_ACCOUNT = 50;
  public static final int TRANSACTIONS_MIN = 5;
  public static final long TRANSACTION_TIME_DIFF_MIN = 1000;
  public static final long TRANSACTION_TIME_DIFF_MAX = 1000 * 60 * 60 * 24;
  public static final long JAN_1_1970 = 43200000L;
  public static final long JAN_1_2010 = 1262347200000L;
  public static final boolean ASSIGN_ID_TO_GENERATED_DATA = true  ;
  public static final int PHONE_NUMBER_LENGTH = 16;
  public static final int ACCOUNT_NUMBER_LENGTH = 25;
  public static final float MINIMAL_BALANCE = 15_000f;
  public static final float MAXIMUM_BALANCE = 1_000_000f;
  public static final float MAXIMUM_MONEY_AMOUNT_PER_TRANSACTION = 1_000;
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
  public static final SimpleDateFormat DATE_FORMAT_XML = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); //"2010-01-23T11:56:11.987"



  public static final Charm CHARM = new Charm(1, "Тихий", "Этот человек очень спокоен", 0.001f);
  public static final Client CLIENT = new Client(1, "Иванов", "Иван", "Иванович", Gender.MALE, new Date(JAN_1_1970), CHARM);
  public static final ClientAccount ACOUNT = new ClientAccount(1, CLIENT, "KZT1254785398547841254800", 1_000_000f, new Date(JAN_1_2010));
}
