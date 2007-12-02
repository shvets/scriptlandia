package data;

import java.util.List;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: shvetsa
 * Date: Jun 9, 2006
 * Time: 5:46:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ItemManager extends Serializable {

  List getItems();
  
}
