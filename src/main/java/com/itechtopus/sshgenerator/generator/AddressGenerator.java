package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAddr;
import com.itechtopus.sshgenerator.model.enums.AddressType;

import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class AddressGenerator {

  private static AddressGenerator instance;

  public static AddressGenerator getInstance() {
    if (instance == null)
      instance = new AddressGenerator();
    return instance;
  }

  public ClientAddr generateRandomAddress(Client client, AddressType type) {
    ClientAddr addr = new ClientAddr();
    addr.client = client;
    addr.street = generateStreet();
    addr.house = generateHouse();
    addr.flat = generateFlat();
    return addr;
  }

  private String generateStreet() {
    return Util.getRandom(StaticBase.streets);
  }

  private String generateHouse() {
    return "" + (rnd.nextInt(10) == 1 ? rnd.nextInt(500) : rnd.nextInt(50)) + (rnd.nextInt(10) == 1 ? (rnd.nextBoolean() ? "a" : "/" + rnd.nextInt(5)) : "");
  }

  private String generateFlat() {
    return "" + rnd.nextInt(100);
  }





}
