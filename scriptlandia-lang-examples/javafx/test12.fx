import java.lang.System;
import java.net.*;
import javafx.ui.*;


class ExampleModel {
  attribute imageFiles: String*;
  attribute selectedImageIndex: Number;
  attribute selectedImageUrl: String;
}


var model = ExampleModel {
  var: self
  imageFiles: ["Bird.gif", "Cat.gif", "Dog.gif",
               "Rabbit.gif", "Pig.gif", "dukeWaveRed.gif",
               "kathyCosmo.gif", "lainesTongue.gif",
               "left.gif", "middle.gif", "right.gif",
               "stickerface.gif"]

  selectedImageUrl: bind "http://java.sun.com/docs/books/tutorial/uiswing/components/example-1dot4/images/{self.imageFiles[self.selectedImageIndex]}"
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

  title: "SplitPane Example"
  height: 400
  width: 500
  content: SplitPane {
      orientation: HORIZONTAL
      content:
      [SplitView {
          weight: 0.30
          content: ListBox {
               selection: bind model.selectedImageIndex
               cells: bind foreach (file in model.imageFiles)
                  ListCell {
                     text: bind file
                 }
          }
      },
      SplitView {
          weight: 0.70
          content: ScrollPane {
              view: CenterPanel {
                  background: white
                  content: ImagePanel {
                      url: bind model.selectedImageUrl
                  }
              }
          }
      }]
  }

  visible: true
}