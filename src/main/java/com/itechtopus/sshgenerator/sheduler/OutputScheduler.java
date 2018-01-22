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
import java.util.stream.Collectors;

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

  public void flush() {
    File outputDir = new File(Parameters.OUTPUT_FOLDER);
    if (outputDir.exists())
      outputDir.mkdirs();
    flushClientPIS();
    flushOperations();
  }

  private void doIteration() {
    doCount();
    flushClientPIS();
    flushOperations();
  }

  private void flushOperations() {
    if (MainStorage.operations.length() == 0)
      return;
    String newFileName = getOperationsNewFileName();
    File file = new File(getOperationsNewFileName());
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      bw.write(MainStorage.operations.toString());
      bw.flush();
      log.info(MainStorage.operations.toString());
      MainStorage.operations = new StringBuffer();
    } catch (IOException e) {
      log.info("Error during output writing Operations to file:" + newFileName, e);
    }
  }

  private void flushClientPIS() {
    if (MainStorage.clientPIs.isEmpty())
      return;
    String newFileName = getCIANewFileName();
    File file = new File(newFileName);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      bw.write("<cia>\n");
      bw.write(MainStorage.clientPIs
          .stream()
          .map(Util::convertToXML)
          .map(Util::simplify)
          .collect(Collectors.joining("\n")));
      bw.write("\n</cia>");
      bw.flush();
      log.info("" + Util.convertToXML(MainStorage.clientPIs));
      MainStorage.clientPIs.clear();
    } catch (IOException e) {
      log.info("Error during output writing ClientPI's to file:" + newFileName, e);
    }
  }

  private String getCIANewFileName() {
    return Parameters.OUTPUT_FOLDER + "from_cia_" + format(new Date()) + ".xml";
  }

  private String getOperationsNewFileName() {
    return Parameters.OUTPUT_FOLDER + "from_frs_" + format(new Date()) + ".json_row";
  }

  private static String format(Date date) {
    return DATE_FORMAT2.format(date);
  }

  private void doCount() {
    if ((counter++)+100 > Long.MAX_VALUE)
      counter = 0;
  }
}
