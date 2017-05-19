package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.enums.Gender;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.itechtopus.sshgenerator.generator.Constants.*;
import static com.itechtopus.sshgenerator.generator.Util.getListOfCollection;
import static com.itechtopus.sshgenerator.generator.Util.getRandom;
import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class ClientGenerator {

  private class ClientFullName {
    String surname;
    String name;
    String patronymic;
    Gender gender;
  }

  private static ClientGenerator instance;
  private static final AtomicInteger idCounter = new AtomicInteger(1000);

  public static ClientGenerator getInstance() {
    if (instance == null)
      instance = new ClientGenerator();
    return instance;
  }

  private ClientGenerator() {
//    if (clientSet.isEmpty())
//      processData();
  }

  private void processData() {
    for (int i = 0; i < Constants.CLIENTS_TO_GENERATE; i++) {
      Client client;
      client = generateRandomClient(null);
      client.id = ASSIGN_ID_TO_GENERATED_DATA ? idCounter.incrementAndGet() : null;
    }
  }

  /**
   * Returns a randomly created (new) client with no id
   * which is not correlates with alreay created clients
   * (!Important) Doesn't stores it in storage
   * @param gender - null if gender must be selected randomly
   * @return a new client instance
   */
  private Client generateRandomClient(Gender gender) {
    Client client = new Client();
    ClientFullName cfn = generateFullName(null);
    client.name = cfn.name;
    client.surname = cfn.surname;
    client.patronymic = cfn.patronymic;
    client.gender = cfn.gender;
    client.birthDate = generateRandomBirthDate();
    client.charm = CharmGenerator.getInstance().getRandomCharm();
    return client;
  }

  /**
   * Method generates new client and stores it in storage
   * @return - new Client instance
   */
  public Client generateNewClient() {
    Client client = generateRandomClient(null);
    return client;
  }

  private Date generateRandomBirthDate() {
    return new Date(JAN_1_1970 + (long)(rnd.nextFloat() * ((long)MAX_CLIENT_AGE_DIFF * 31536000000L)));
  }

  private ClientFullName generateFullName(Gender gender) {
    // if gender is null - generate random gender client
    boolean male = gender != null ? gender == Gender.MALE : rnd.nextBoolean();
    ClientFullName cfn = new ClientFullName();
    cfn.gender = male ? Gender.MALE : Gender.FEMALE;
    String[] names = male ? StaticBase.maleNames :StaticBase.femaleNames;
    String[] surnames = male ? StaticBase.maleSurnames : StaticBase.femaleSurnames;
    String[] patronymics = male ? StaticBase.malePatronymics : StaticBase.femalePatronymics;
    cfn.name = getRandom(names);
    cfn.surname = getRandom(surnames);
    cfn.patronymic = getRandom(patronymics);
    return cfn;
  }

  public static void main(String[] args) throws InterruptedException {
    ClientGenerator cg = ClientGenerator.getInstance();
    while (true) {
      System.out.println(cg.generateRandomBirthDate());
      Thread.sleep(250);
    }
  }

}
