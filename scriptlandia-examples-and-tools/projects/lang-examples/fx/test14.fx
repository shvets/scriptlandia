import java.lang.System;

import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

 
Frame {
  onClose: operation() {
      System.exit(0);
  }

    title: "Hello World Java FX"

    width: 200
    height: 200

    content: 
Canvas {

    content: Text {

        x: 20

        y: 20

        content: "Welcome to Java FX"

        font: Font { face: VERDANA, style: [ITALIC, BOLD], size: 80 }

        fill: LinearGradient {

            x1: 0, y1: 0, x2: 0, y2: 1

            stops: 

            [Stop {

                offset: 0

                color: blue

            },

            Stop {

                offset: 0.5

                color: dodgerblue

            },

            Stop {

                offset: 1

                color: blue

            }]

        }

        filter: [Glow {amount: 0.1}, Noise {monochrome: true, distribution: 0}]

    }


}


    visible: true
}

