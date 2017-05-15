package com.itechtopus.sshgenerator.generator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Util {

  public static final Random rnd = new Random(System.currentTimeMillis());

  public static <T> List<T> getListOfCollection(Collection<T> values) {
    return new ArrayList<T>(values);
  }

  public static <T> T getRandom(Collection<T> collection) {
    return (T)getRandom(collection.toArray());
  }

  public static <T> T getRandom(T... collection) {
    return collection[rnd.nextInt(collection.length)];
  }

  public static <K, V> Map<K,V> newMap() {
    return new ConcurrentHashMap<K, V>();
  }

  public static <T> List<T> newList() {
    return new CopyOnWriteArrayList<T>();
  }

  public static <T> Set<T> newSet() {
    return new CopyOnWriteArraySet<T>();
  }
}
