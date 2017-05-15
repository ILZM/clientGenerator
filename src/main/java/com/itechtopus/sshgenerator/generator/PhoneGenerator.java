package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientPhone;
import com.itechtopus.sshgenerator.model.enums.PhoneType;

import static com.itechtopus.sshgenerator.generator.Util.rnd;

public class PhoneGenerator {

  private static PhoneGenerator instance;
  private final String[] mobileCodes =
      ("705\n" +
          "707\n" +
          "701\n" +
          "700\n" +
          "777\n" +
          "775\n" +
          "778\n" +
          "774\n" +
          "747").split("\n");

  private final String[] homePhoneCodes =
      ("718\n" +
          "727\n" +
          "717\n" +
          "744").split("\n");

  public static PhoneGenerator getInstance() {
    if (instance == null)
      instance = new PhoneGenerator();
    return instance;
  }

  public ClientPhone generatePhone(Client client, PhoneType type) {
    return new ClientPhone(client, getRandomNumber(type), type);
  }

  private String getRandomNumber(PhoneType type) {
    StringBuilder num = new StringBuilder("+7 ");
    if (type == PhoneType.MOBILE)
      num.append(Util.getRandom(mobileCodes));
    else
      num.append(Util.getRandom(homePhoneCodes));
    num.append(" ");
    String timeStamp = generateThroughTimeStamp();
    num.append(timeStamp.substring(0, 3)).append(" ");
    num.append(timeStamp.substring(3, 5)).append(" ");
    num.append(timeStamp.substring(6));

    return num.toString();
  }

  private String generateThroughTimeStamp() {
    String timeStamp = ("" + System.currentTimeMillis());
    timeStamp = timeStamp.substring(timeStamp.length() - 8);
    return timeStamp;
  }

  private String generateThroughRandom() {
    StringBuilder t = new StringBuilder();
    for (int i = 0; i < 8; i++)
      t.append("" + rnd.nextInt(10));
    return t.toString();
  }

  //
//  public static void main(String[] args) {
//    System.out.println(getInstance().getRandomNumber(PhoneType.MOBILE));
//  }


}
