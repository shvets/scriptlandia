public class Command {
  private String name;
  private String code;

  public Command(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Command {\n");
    sb.append("  name: " + name + "\n");
    sb.append("  code: " + code + "\n");
    sb.append("}");

    return sb.toString();
  }

}