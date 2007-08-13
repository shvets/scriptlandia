// AnimalFarm.java

import java.util.List;

public class AnimalFarm {
  private List animals;

  public AnimalFarm() {
    System.out.println("New Animal farm has been created.");
  }

  public void setAnimals(List animals) {
    this.animals = animals;
  }
  
  public void wakeUp() {
    for(int i=0; i < animals.size(); i++) {
      Animal animal = (Animal)animals.get(i);

      animal.makeSound();
    }
  }
  
}
