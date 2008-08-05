import java.util.*;

public class State {
  private String name;

  private List<Command> commands = new ArrayList<Command>();
  private Map<String, Transition> transitions = new HashMap<String, Transition>();

  public State(String name) {
    this.name = name;
  }

  public void addCommand(Command command) {
    commands.add(command);
  }
  
  public void addTransition(Event event, State targetState) {
    transitions.put(event.getCode(), new Transition(this, event, targetState));
  }

  public String getName() {
    return name;
  }

  public Map<String, Transition> getTransitions() {
    return transitions;
  }

  public List<Command> getCommands() {
    return commands;
  }

  public Collection<State> getAllTargets() {
    List<State> result = new ArrayList<State>();

    for (Transition t : transitions.values()) {
      result.add(t.getTarget());
    }

    return result;
  }

  public boolean hasTransition(String eventCode) {
    return transitions.containsKey(eventCode);
  }
  public State getTargetState(String eventCode) {
    return transitions.get(eventCode).getTarget();
  }
  public void executeActions(CommandChannel commandsChannel) {
    for (Command c : commands) {
      commandsChannel.send(c.getCode());
    }
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("State {\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("}");

    return sb.toString();
  }

}