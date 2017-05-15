package com.itechtopus.sshgenerator.sheduler;

import com.itechtopus.sshgenerator.generator.AllInfoGenerator;
import com.itechtopus.sshgenerator.generator.Constants;
import com.itechtopus.sshgenerator.storage.MainStorage;
import com.itechtopus.sshgenerator.storage.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorShceduler implements Runnable{

  private final Logger log = LoggerFactory.getLogger(getClass());

  private long counter = 0;

  private AllInfoGenerator generator;

  public GeneratorShceduler() {
    generator = AllInfoGenerator.get();
  }

  @Override
  public void run() {
    log.info("Scheduler started");
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(Parameters.SCHEDULER_PERIOD);
        doIteration();
      } catch (InterruptedException e) {
        log.info("Interrupted from outside. Counter parameter:{}", counter);
        return;
      }
    }
  }

  private void doIteration() {
    doCount();
    generateTransactions();
    if (counter %  Parameters.ACCOUNT_GENERATION_PERIOD == 0)
      generateAccounts();
    if (counter % Parameters.CLIENT_GENERATION_PERIOD == 0)
      generateClient();
  }

  private void doCount() {
    if ((counter++)+100 > Long.MAX_VALUE)
      counter = 0;
  }

  private void generateClient() {
    generator.generateNewClientPI();
  }

  private void generateAccounts() {
    generator.generateNewAccount();
  }

  private void generateTransactions() {
    generator.generateNewTransaction();
  }



}
