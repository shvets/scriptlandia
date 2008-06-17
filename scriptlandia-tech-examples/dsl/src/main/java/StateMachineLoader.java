import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ANTLRInputStream;

import java.io.*;

import statemachine.StateMachineLexer;
import statemachine.StateMachineParser;

public class StateMachineLoader {

  private String fileName;

  public StateMachineLoader(String fileName) {
    this.fileName = fileName;
  }

  public void run() throws Exception {
    InputStream is = new FileInputStream(fileName);

    ANTLRInputStream source = new ANTLRInputStream(is);

    StateMachineLexer lexer = new StateMachineLexer(source);

    CommonTokenStream tokens = new CommonTokenStream(lexer);

    StateMachineParser parser = new StateMachineParser(tokens);
    parser.setTreeAdaptor(new MyNodeAdaptor());

    MfTree ast = (MfTree) parser.machine().getTree();

    CommandChannel commandChannel = new CommandChannel() {
      public void send(String code) {
        System.out.println(code);
      }
    };

    Controller controller = new Controller(ast, commandChannel);

    System.out.println("events: " + controller.getEvents());
    System.out.println("commands: " + controller.getCommands());
    //System.out.println("reset events: " + controller.getResetEvents());
    System.out.println("states: " + controller.getStates());

    StateMachine stateMachine = controller.getStateMachine();

    stateMachine.reset();

    stateMachine.handle("D1CL");
    stateMachine.handle("D2OP");
    stateMachine.handle("L1ON");
    stateMachine.handle("PNCL");

    stateMachine.reset();

    stateMachine.handle("D1CL");
    stateMachine.handle("L1ON");
    stateMachine.handle("D2OP");
    stateMachine.handle("PNCL");
  }

}
