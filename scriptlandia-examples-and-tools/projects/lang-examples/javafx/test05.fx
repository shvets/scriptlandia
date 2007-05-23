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
  menubar: MenuBar {
       menus: Menu {
           text: "File"
           mnemonic: F
           items: MenuItem {
               text: "Exit"
               mnemonic: X
               accelerator: {
                   modifier: ALT
                   keyStroke: F4
               }
               action: operation() {
                   System.exit(0);
               }
           }
       }
  }
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
      [Button {
           text: "I'm a button!"
           mnemonic: I
           action: operation() {
               model.numClicks++;
          }
      },
      Label {
          text: bind "Number of button clicks: {model.numClicks}"
      }]
  }
  visible: true
}