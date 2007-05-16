import java.lang.System;

import javafx.ui.*;

Frame {
    onClose: operation() {
        System.exit(0);
    }

    title: "Hello World Java FX"

    width: 200
    height: 200

    content: Label {

        text: "Hello World"

    }

    visible: true
}