package com.springinaction.chapter01.knight;
import com.google.inject.ImplementedBy;

@ImplementedBy(HolyGrailQuest.class)
public interface Quest {
  public Object embark() throws QuestFailedException;
}
