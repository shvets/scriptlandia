import java.lang.System;

import javafx.ui.*;

class HelloWorldModel {
    attribute saying: String;
}

var model = HelloWorldModel {
    saying: "Hello World"
};

var win = Frame {
    onClose: operation() {
        System.exit(0);
    }

    title: "Hello World Java FX"

    width: 200
    height: 100

    content: Label {

        text: bind model.saying

    }

    visible: true
};

model.saying = "Goodbye Cruel World!";