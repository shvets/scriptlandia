package dao;

import org.apache.struts.util.LabelValueBean;

import java.util.List;
import java.util.ArrayList;

public class ComboboxCollectionManager {

  public List<LabelValueBean> getBooleanList() {
    List<LabelValueBean> list = new ArrayList<LabelValueBean>();

    list.add(new LabelValueBean("No", "false"));
    list.add(new LabelValueBean("Yes", "true"));

    return list;
  }

}
