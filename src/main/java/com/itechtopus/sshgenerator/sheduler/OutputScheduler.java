package com.itechtopus.sshgenerator.sheduler;

import com.itechtopus.sshgenerator.generator.Util;
import com.itechtopus.sshgenerator.storage.MainStorage;
import com.itechtopus.sshgenerator.storage.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import static com.itechtopus.sshgenerator.generator.Constants.DATE_FORMAT2;

public class OutputScheduler implements Runnable {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private long counter = 0;

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(Parameters.OUTPUT_SCHEDULER_PERIOD);
        doIteration();
      } catch (InterruptedException e) {
        log.info("Interrupted from outside. Counter :" + counter);
        flush();
        return;
      }
    }
  }

  private void flush() {
    flushClientPIS();
  }

  private void doIteration() {
    doCount();
    flushClientPIS();

  }

  private void flushClientPIS() {
    if (MainStorage.clientPIs.isEmpty())
      return;
    String newFileName = Parameters.OUTPUT_FOLDER + format(new Date()) + "-cia-right.xml";
    File file = new File(newFileName);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      bw.write(Util.getUnreadableXML(MainStorage.clientPIs));
      bw.flush();
      log.info("" + Util.convertToXML(MainStorage.clientPIs));
      MainStorage.clientPIs.clear();
    } catch (IOException e) {
      log.info("Error during output writing ClientPI's to file:" + newFileName, e);
    }
  }

  private static String format(Date date) {
    return DATE_FORMAT2.format(date);
  }

  private void doCount() {
    if ((counter++)+100 > Long.MAX_VALUE)
      counter = 0;
  }
}