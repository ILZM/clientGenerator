package com.itechtopus.sshgenerator.listener;

import com.itechtopus.sshgenerator.sheduler.GeneratorScheduler;
import com.itechtopus.sshgenerator.sheduler.OutputScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationManager implements ServletContextListener {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private Thread generatingThread = new Thread(new GeneratorScheduler());
  private Thread outputtingThread = new Thread(new OutputScheduler());

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    // run the schedulers
    log.info("Initializing application");
    generatingThread.start();
    outputtingThread.start();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    // stopping all shedulers
    log.info("Destroying application");
    generatingThread.interrupt();
    outputtingThread.interrupt();
  }
}
