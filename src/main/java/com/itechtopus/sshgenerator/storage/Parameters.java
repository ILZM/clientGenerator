package com.itechtopus.sshgenerator.storage;

public class Parameters {

  public static long OUTPUT_SCHEDULER_PERIOD = 300_000;

  public static int CLIENT_PI_BUFFER_SIZE = 50;

  public static int ACCOUNT_AND_TRANSACTION_BUFFER_SIZE = 50_000;

  public static long SCHEDULER_PERIOD = 1000; // in milliseconds

  public static int TRANSACTIONS_PER_ITERATION = 200; // number of transactions need to be generated every iteration

  public static int ACCOUNT_GENERATION_PERIOD = 3; // 5 means one account per 5 seconds (if sheduler period is 1000 ms)

  public static int CLIENT_GENERATION_PERIOD = 1;

  public static boolean GENERATE_DUPLICATES = true;

  public static int DUPLICATES_GENERATION_PERIOD = 5; // one of 5000 is stored as duplicate

  public static String OUTPUT_FOLDER = "/home/jgolibzhan/123/";

  public static int NEW_CHARM_PERIOD = 10;


}
