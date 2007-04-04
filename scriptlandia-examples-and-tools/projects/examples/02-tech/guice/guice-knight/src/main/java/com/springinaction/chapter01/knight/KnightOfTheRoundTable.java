package com.springinaction.chapter01.knight;

import com.google.inject.Inject;

public class KnightOfTheRoundTable implements Knight {
  private String name;
  private Quest quest;
  
  @Inject
  public KnightOfTheRoundTable(@Name String name) {
    this.name = name;
  }
  
  @MinstrelIntercepted
  public Object embarkOnQuest() throws QuestFailedException {
    return quest.embark();
  }
  
  @Inject
  public void setQuest(Quest quest) {
    this.quest = quest;
  }
  
  public String getName() {
    return name;
  }
}
