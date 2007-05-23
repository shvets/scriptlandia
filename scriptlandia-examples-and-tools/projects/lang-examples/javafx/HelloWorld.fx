import java.lang.System;

import javafx.ui.*;

Frame {
  title: "Hello World JavaFX"
  width: 200
  height: 50
  content: Label {
    text: "Hello World"
  }
  visible: true

  onClose: operation() {
    System.exit(0);
  }

}
