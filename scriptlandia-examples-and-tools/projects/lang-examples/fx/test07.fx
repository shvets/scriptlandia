import java.lang.System;

import javafx.ui.*;

class ButtonClickModel {
  attribute numClicks: Number;
}

var model = new ButtonClickModel();

Frame {
  onClose: operation() {
      System.exit(0);
  }

  width: 200
  height: 500

  content: GridPanel {
      border: EmptyBorder {
         top: 30
         left: 30
         bottom: 30
         right: 30
      }
      rows: 2
      columns: 1
      vgap: 10
      cells:
      [Label {
           text: bind
                   "<html>
                      <a href='{#(operation() {model.numClicks++;})}'>
                          I'm a hyperlink!
                      </a>
                   </html>"
      },
      Label {
          text: bind "Number of clicks: {model.numClicks}"
      }]

  }
  visible: true
};