import java.lang.System;

import javafx.ui.*;

class ButtonDemoModel {
  attribute buttonEnabled: Boolean;
}

var model = ButtonDemoModel {
  buttonEnabled: true
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

  title: "ButtonDemo"
  content: FlowPanel {
      content:
      [Button {
          text: "Disable middle button"
          verticalTextPosition: CENTER
          horizontalTextPosition: LEADING
          icon: Image {
              url: "http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/images/right.gif"
          }
          mnemonic: D
          toolTipText: "Click this button to disable the middle button"
          enabled: bind model.buttonEnabled
          action: operation() {
               model.buttonEnabled = false;
          }
      },
      Button {
          text: "Middle button"
          icon: Image {
              url: "http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/images/middle.gif"
          }
          verticalTextPosition: BOTTOM
          horizontalTextPosition: CENTER
          mnemonic: M
          toolTipText: "This middle button does nothing when you click it."
          enabled: bind model.buttonEnabled
      },
      Button {
          text: "Enable middle button"
          icon: Image {
              url: "http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/images/left.gif"
          }
          mnemonic: E
          toolTipText: "Click this button to enable the middle button"
          action: operation() {
               model.buttonEnabled = true;
          }
          enabled: bind not model.buttonEnabled
      }]
  }
  visible: true
}