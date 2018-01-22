package com.itechtopus.sshgenerator.static_tests;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.itechtopus.sshgenerator.generator.AllInfoGenerator;
import com.itechtopus.sshgenerator.sheduler.GeneratorScheduler;
import com.itechtopus.sshgenerator.sheduler.OutputScheduler;

public class Main {

  private static boolean isInterrupted = false;

  public static void main(String[] args) throws InterruptedException {

    Long startTime = System.currentTimeMillis();

    Thread counter = new Thread(() -> {
      while (!isInterrupted) {
        Long elapsed = System.currentTimeMillis() - startTime;
        System.err.print("\r" + getStrRepresentationOfTime(elapsed) +
          " Количество клиентов: " + AllInfoGenerator.get().clientPIS.size() + "" +
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

  private static String getStrRepresentationOfTime(long elapsed) {
    List<String> line = new ArrayList<>();
    LinkedHashMap<Integer, String> timeMap = new LinkedHashMap<>();
    timeMap.put(1000 * 60 * 60, " ч");
    timeMap.put(1000 * 60, " м");
    timeMap.put(1000, " c");
//    timeMap.put(1, " мc");
    for (Map.Entry<Integer, String> pair : timeMap.entrySet()) {
      if (elapsed > pair.getKey()) {
        int power = (int)(elapsed / pair.getKey());
        line.add(power + pair.getValue());
        elapsed %= pair.getKey();
      }
    }
    String elapsedStr = line.stream().collect(Collectors.joining(" "));
    return elapsedStr;
  }

}