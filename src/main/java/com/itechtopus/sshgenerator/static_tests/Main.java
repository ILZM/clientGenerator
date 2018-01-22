package com.itechtopus.sshgenerator.static_tests;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.itechtopus.sshgenerator.generator.AllInfoGenerator;
import com.itechtopus.sshgenerator.sheduler.GeneratorScheduler;
import com.itechtopus.sshgenerator.sheduler.OutputScheduler;

/**
 *
 *     !!! Important !!!
 *
 * для того, чтобы запустить генератор через этот main,
 * нужно указать в параметрах Vm options значение
 *   -Xmx10000m (не менбше)
 * так как вся коллекция хранится в оперативной памяти
 * для быстроты генерации
 */

public class Main {

  private static boolean isInterrupted = false;

  public static void main(String[] args) throws InterruptedException {

    Long startTime = System.currentTimeMillis();

    Thread counter = new Thread(() -> {
      int finished = 0;
      while (!isInterrupted) {
        Long elapsed = System.currentTimeMillis() - startTime;
        int currentlyGenerated = AllInfoGenerator.get().clientPIS.size();
        System.err.print("\r" + getStrRepresentationOfTime(elapsed) +
          " Количество клиентов: " + currentlyGenerated +
          " Количество счетов: " + AllInfoGenerator.get().accountMap.size() +
          " Скорость: " + ((currentlyGenerated - finished) / 0.5) + " кл/сек."
        );
        finished = currentlyGenerated;
        try {
          Thread.sleep(500);
        } catch (InterruptedException ignore) {}
      }
    });

    OutputScheduler outputScheduler = new OutputScheduler();
    try {
      counter.start();
      GeneratorScheduler generatorScheduler = new GeneratorScheduler();
      generatorScheduler.generateNClients(1_000_000);

      outputScheduler.flushClients();
      generatorScheduler.generateNAccount(1_000_000);

      for (int i = 0; i < 10; i++) {
        generatorScheduler.generateNTransactions(1_000_000);
        outputScheduler.flush();
      }

    } finally {


      outputScheduler.flush();


      isInterrupted = true;
    }

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