public class DefaultMessagePresentation implements MessagePresentation {

  private MessageModel messageModel;

  public void setMessageModel(MessageModel messageModel) {
    this.messageModel = messageModel;
  }

  public MessageModel getMessageModel() {
    return messageModel;
  }

  public void view() {
    System.out.println(messageModel.getMessage());
  }

}