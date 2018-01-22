package com.itechtopus.sshgenerator.generator;

import com.itechtopus.sshgenerator.model.Charm;

import java.util.List;
import java.util.Map;

public class  CharmGenerator {

  private static CharmGenerator instance;

  int id = 0;

  private final Map<Integer, Charm> charmMap = Util.newMap();

  public static CharmGenerator getInstance() {
    if (instance == null)
      instance = new CharmGenerator();
    return instance;
  }

  private CharmGenerator() {
    if (charmMap.isEmpty())
      processData();
  }

  private void processData() {
    for (Map.Entry<String, String> pair : StaticBase.charmMap.entrySet()){
      charmMap.put(id++, new Charm(Constants.ASSIGN_ID_TO_GENERATED_DATA ? 1000 + id: null, pair.getKey(), pair.getValue(), generateRandomEnergy()));
    }
  }

  public List<Charm> getCharmList() {
    return Util.getListOfCollection(charmMap.values());
  }

  private float generateRandomEnergy() {
    return Math.abs(Util.rnd.nextFloat());
  }

  public void addNewRandomCharm() {
    charmMap.put(id++,
        new Charm(Constants.ASSIGN_ID_TO_GENERATED_DATA ? 1000 + id: null,
            Util.getRandom(StaticBase.extraCharacters),
            "No_description",
            generateRandomEnergy()));
  }

  public Charm getRandomCharm() {
    return Util.getRandom(charmMap.values());
  }
}
