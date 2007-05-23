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

    title: bind "{model.saying} Java FX"

    width: 200
    height: 100

    content: TextField {

        value: bind model.saying

    }

    visible: true
};
