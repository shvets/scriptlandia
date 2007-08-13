public class Coconut {
  private Lime lime;

  public Coconut() {
    System.out.println("You put the lime in the coconut...");
  }

  public void setLime(Lime lime) {
    this.lime = lime;
  }
  
  public void drinkThemBothUp() {
    lime.drink();
  }
  
}