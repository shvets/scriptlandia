public class DefaultMessageModel implements MessageModel {

  private String message;

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}