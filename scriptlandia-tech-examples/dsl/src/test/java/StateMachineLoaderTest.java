
import com.devx.sparql.SparqlPrettifier;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.*;

public class StateMachineLoaderTest {
  @Test
  public void loadStateMachine() throws Exception {
    StateMachineLoader loader = new StateMachineLoader("test1.dsl");

    loader.run();

    //assertEquals(expected, actual);
  }

}
