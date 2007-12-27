package org.sf.scriptlandia.install;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

/**
 * This class renders separate cell for the list
 */
class LanguageRenderer extends JLabel implements ListCellRenderer  {
  private final Border emptyBorder = new EmptyBorder(0,0,0,0);

  /**
   * Creates new list renderer
   */
  public LanguageRenderer() {
    this.setOpaque(true);
  }

  /**
   * Return a component that has been configured to display the
   * specified value. Contains main logic for the renderer,
   */
  public Component getListCellRendererComponent(JList listbox, Object value,
                      int index, boolean isSelected, boolean cellHasFocus) {
    Map entry = (Map)value;

    if(entry != null) {
      this.setText((String)entry.get("name"));
      this.setIcon((Icon)entry.get("imageIcon"));

      this.setBorder(emptyBorder);

      if(isSelected) {
        this.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
        this.setForeground(UIManager.getColor("ComboBox.selectionForeground"));
      }
      else {
        this.setBackground(UIManager.getColor("ComboBox.background"));
        this.setForeground(UIManager.getColor("ComboBox.foreground"));
      }
    }
    else {
      this.setText("");
      this.setIcon(null);
    }

    return this;
  }
}
