/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;
import javafxpad.JavaFXPad;
import javafx.ui.*;
import java.lang.System;

public class Main extends CompositeWidget {
    attribute tutorial: Tutorial;
    attribute selectedLesson: Number?;
    attribute currentLessonCode: String?;
    attribute currentExampleCode: String?;
}

attribute Main.tutorial = new Tutorial;
attribute Main.currentExampleCode = 
    bind tutorial.lessons[selectedLesson].exampleCode;

trigger on Main.tutorial = value {
    selectedLesson  = 0;
    currentLessonCode = tutorial.lessons[0].exampleCode;
 }

trigger on Main.currentExampleCode = value {
    currentLessonCode = value;
}

function Main.composeWidget() =
GroupPanel {
   var row = Row {alignment:LEADING resizable: true}
   var column = Column {resizable: true}
   rows: row
   columns: column
   content: 
   SplitPane { 
        row: row
        column: column
        orientation: HORIZONTAL
        content:
        [SplitView {
            weight: .2
            content: ListBox {
                //scrollPaneBorder: ShadowedBorder
                selection: bind selectedLesson
                cells: bind foreach (lesson in tutorial.lessons) 
                {
                    text: lesson.title
                    value: lesson.title
                }
            
            }            
        },
        SplitView {
            weight: .8
            content:
            SplitPane {
                orientation: VERTICAL
                content:
                [SplitView {
                    weight: .35
                    content:
                    CardPanel {
                        background: white                                
                        cursor: DEFAULT
                        //border: ShadowedBorder
                        cards: bind foreach(l in tutorial.lessons) 
                        ScrollPane {
                            view: Label {
                                cursor: DEFAULT
                                border: EmptyBorder {left: 4, right: 4}
                                focusable: true
                                font: new Font("Dialog", "PLAIN", 14)
                                background: white
                                text: bind l.content
                            }
                       }
                      selection: bind selectedLesson
        
                   }
                },
                SplitView {
                    weight: .65
                    content: BorderPanel {
                        cursor: DEFAULT
                        preferredSize: {height: 600, width: 800}
                        var javafxPad = JavaFXPad {
                            userCode: bind currentLessonCode
                        }
                        top: GroupPanel {
                            cursor: DEFAULT
                            //border: ShadowedBorder
                            var row = Row {}
                            var labelCol = new Column
                            var butCol = new Column
                            var zoomCol = new Column
                            rows: row
                            columns: [labelCol, zoomCol, butCol]
                            content: 
                            [SimpleLabel {
                                background: new Color(0, 0, 0, 0.001)
                                row: row
                                column: labelCol
                                text: "Zoom:"
                            },
                            ComboBox {
                                row: row
                                column: zoomCol
                                selection: bind javafxPad.zoomSelection
                                cells: bind foreach (i in javafxPad.zoomOptions)
                                   ComboBoxCell { text: "{i}%" }
                            },
                            VerticalStrut {
                                row: row
                                column: butCol
                                height: 30
                            },
                            Button {
                                row: row
                                column: butCol
                                visible: bind currentLessonCode <> tutorial.lessons[selectedLesson].exampleCode
                                text: "Reset"
                                action: operation() {
                                    currentLessonCode = tutorial.lessons[selectedLesson].exampleCode;
                                }
                            }]
                        }
                        center: javafxPad
                    }
                }]
            }
        }]
    }
};
    
