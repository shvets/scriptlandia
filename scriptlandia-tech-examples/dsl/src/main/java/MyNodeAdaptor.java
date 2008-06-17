import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.Token;

class MyNodeAdaptor extends CommonTreeAdaptor {
  
  public Object create(Token token) {
    return new MfTree(token);
  }

}
