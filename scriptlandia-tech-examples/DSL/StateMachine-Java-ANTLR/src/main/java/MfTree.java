import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import java.util.List;
import java.util.ArrayList;

public class MfTree extends CommonTree {

  public MfTree(Token token) {
    super.token = token;
  }

  List<MfTree> getChildren() {
    List<MfTree> result = new ArrayList<MfTree>();

    for (int i = 0; i < getChildCount(); i++) {
      result.add((MfTree) getChild(i));
    }

    return result;
  }

  MfTree getSoleChild(int nodeType) {
    List<MfTree> matchingChildren = getChildren(nodeType);

    assert 1 == matchingChildren.size();
    return matchingChildren.get(0);
  }

  List<MfTree> getChildren(int nodeType) {
    List<MfTree> result = new ArrayList<MfTree>();

    for (int i = 0; i < getChildCount(); i++) {
      if (getChild(i).getType() == nodeType) {
        result.add((MfTree) getChild(i));
      }
    }

    return result;
  }

  String getText(int i) {
    return getChild(i).getText();
  }

  boolean hasChild(int nodeType) {
    List<MfTree> matchingChildren = getChildren(nodeType);
    return matchingChildren.size() != 0;
  }

}
