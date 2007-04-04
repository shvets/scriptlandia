package com.springinaction.chapter01.knight;
import com.springinaction.chapter01.knight.GrailNotFoundException;




public class HolyGrailQuest implements Quest {
  public HolyGrailQuest() {}
  
  public Object embark() throws GrailNotFoundException {
    System.out.println("Embarking on quest");
    return new HolyGrail();
  }
}
