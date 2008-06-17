import statemachine.StateMachineLexer;
import statemachine.StateMachineParser;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Controller {
  private MfTree ast;

  private Map<String, Event> events = new HashMap<String, Event>();
  private Map<String, Command> commands = new HashMap<String, Command>();
  private Map<String, State> states = new HashMap<String, State>();

  private StateMachine stateMachine;

  public Controller(MfTree ast, CommandChannel commandChannel) {
    this.ast = ast;

    stateMachine = new StateMachine(commandChannel);

    loadEvents();
    loadCommands();
    loadStates();
    loadResetEvents();

    String startStateName = stateName((MfTree) ast.getFirstChildWithType(StateMachineParser.STATE));

    stateMachine.setStartState(states.get(startStateName));

    stateMachine.reset();
  }

  public StateMachine getStateMachine() {
    return stateMachine;
  }

  public Map<String, Event> getEvents() {
    return events;
  }

  public Map<String, Command> getCommands() {
    return commands;
  }

  public Map<String, State> getStates() {
    return states;
  }

  protected void loadEvents() {
    MfTree tree = ast.getSoleChild(StateMachineLexer.EVENT_LIST);

    for (MfTree node : tree.getChildren()) {
      Event event = new Event(node.getText(0), node.getText(1));

      events.put(event.getName(), event);
    }
  }

  protected void loadCommands() {
    MfTree tree = ast.getSoleChild(StateMachineLexer.COMMAND_LIST);

    for (MfTree node : tree.getChildren()) {
      Command command = new Command(node.getText(0), node.getText(1));

      commands.put(command.getName(), command);
    }
  }

  protected void loadStates() {
    List<MfTree> tree = ast.getChildren(StateMachineLexer.STATE);

    for (MfTree subTree : tree) {
      State state = new State(stateName(subTree));

      states.put(state.getName(), state);
    }

    for (MfTree subTree : tree) {
      State state = states.get(stateName(subTree));

      loadActions(subTree, state);
      loadTransitions(subTree, state);
    }
  }

  private void loadActions(MfTree tree, State parentState) {
    for (MfTree node : tree.getSoleChild(StateMachineParser.ACTION_LIST).getChildren()) {
      Command command = commands.get(node.getText());

      parentState.addCommand(command);
    }
  }

  protected void loadTransitions(MfTree tree, State parentState) {
    for (MfTree node : tree.getSoleChild(StateMachineParser.TRANSITION_LIST).getChildren()) {
      Event event = events.get(node.getText(0));
      State state = states.get(node.getText(1));

      parentState.addTransition(event, state);
    }
  }

  protected void loadResetEvents() {
    if (ast.hasChild(StateMachineParser.RESET_EVENT_LIST)) {
      MfTree tree = ast.getSoleChild(StateMachineParser.RESET_EVENT_LIST);

      for (MfTree e : tree.getChildren()) {
        stateMachine.getResetEvents().add(events.get(e.getText()));
      }
    }
  }

  private String stateName(MfTree tree) {
    MfTree stateNode = tree.getChildren().get(0);

    return stateNode.getText();
  }

}
