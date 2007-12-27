package org.sf.scriptlandia.install;

import java.util.*;
import javax.swing.*;

/**
 * This class represents swing list model for language object.
 *
 * @author Alexander Shvets
 * @version 1.0 12/22/2007
 */
public class LanguageModel extends AbstractListModel
    implements ComboBoxModel {
  protected Object selectedObject;

  protected List list;

  public LanguageModel(List list) {
    this.list = list;
  }

  public int getSize() {
    return list.size();
  }

  public Object getElementAt(int index) {
    if (index >= 0 && index < list.size()) {
      //return ((Map) list.get(index)).get("name");
      return list.get(index);
    } else {
      return null;
    }
  }

  // implements ComboBoxModel

  public Object getSelectedItem() {
    return selectedObject;
  }

  public void setSelectedItem(Object anObject) {
    if ((selectedObject != null && !selectedObject.equals(anObject)) ||
        selectedObject == null && anObject != null) {
      selectedObject = anObject;
      fireContentsChanged(this, -1, -1);
    }
  }

}
