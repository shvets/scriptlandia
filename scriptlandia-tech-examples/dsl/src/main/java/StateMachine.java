import java.util.List;
import java.util.ArrayList;

public class StateMachine {
  private List<Event> resetEvents = new ArrayList<Event>();
  private State currentState;

  private State startState;
  private CommandChannel commandsChannel;

  public StateMachine(CommandChannel commandChannel) {
    this.commandsChannel = commandChannel;
  }

  public State getStartState() {
    return startState;
  }

  public void setStartState(State startState) {
    this.startState = startState;
  }

  public State getCurrentState() {
    return currentState;
  }

  public void setCurrentState(State currentState) {
    this.currentState = currentState;
  }

  public List<Event> getResetEvents() {
    return resetEvents;
  }

  public void reset() {
    currentState = startState;
  }

  public void handle(String eventCode) {
    if (currentState.hasTransition(eventCode)) {
      transitionTo(currentState.getTargetState(eventCode));
    }
    else if (isResetEvent(eventCode)) {
      transitionTo(startState);
    }

    // ignore unknown events
  }

  private void transitionTo(State targetState) {
    currentState.executeActions(commandsChannel);

    currentState = targetState;
  }

 public boolean isResetEvent(String eventCode) {
    return getResetEventCodes().contains(eventCode);
  }

  private List<String> getResetEventCodes() {
    List<String> result = new ArrayList<String>();

    for (Event e : resetEvents) {
      result.add(e.getCode());
    }

    return result;
  }

/*
  public Collection<State> computeStates() {
    List<State> result = new ArrayList<State>();

    gatherForwards(result, getStartState());

    return result;
  }

  private void gatherForwards(Collection<State> result, State state) {
    if (!result.contains(state)) {
      result.add(state);

      for (State next : state.getAllTargets()) {
        gatherForwards(result, next);
      }
    }
  }
*/
}
