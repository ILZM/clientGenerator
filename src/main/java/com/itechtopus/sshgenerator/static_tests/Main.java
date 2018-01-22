package com.itechtopus.sshgenerator.static_tests;

import com.itechtopus.sshgenerator.generator.AllInfoGenerator;
import com.itechtopus.sshgenerator.sheduler.GeneratorScheduler;
import com.itechtopus.sshgenerator.sheduler.OutputScheduler;

public class Main {

  private static boolean isInterrupted = false;

  public static void main(String[] args) throws InterruptedException {

    Thread counter = new Thread(() -> {
      while (!isInterrupted) {
        System.err.print("\r Количество клиентов: " + AllInfoGenerator.get().clientPIS.size() + "" +
          " Количество счетов: " + AllInfoGenerator.get().accountMap.size());
        try {
          Thread.sleep(500);
        } catch (InterruptedException ignore) {}
      }
    });

    counter.start();

    GeneratorScheduler generatorScheduler = new GeneratorScheduler();
    generatorScheduler.generateNClients(1_000_000);
    generatorScheduler.generateNAccount(1_000_000);
    generatorScheduler.generateNTransactions(10_000_000);

    OutputScheduler outputScheduler = new OutputScheduler();

    outputScheduler.flush();

    isInterrupted = true;

    counter.join();

  }

}