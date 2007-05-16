import java.lang.System;

import javafx.ui.*;
import javafx.ui.canvas.*;

Frame {
  onClose: operation() {
      System.exit(0);
  }

    title: "Hello World Java FX"

    width: 200
    height: 200

    content: Canvas {

    content: Rect {

        x: 20

        y: 20

        height: 80

        width: 300

        arcHeight: 20

        arcWidth: 20

        fill: cyan

        stroke: purple

        strokeWidth: 2

    }

}


    visible: true
}