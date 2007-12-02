/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javafxpad;
import javafx.ui.*;
import javafx.ui.canvas.*;
import java.lang.System;
import java.io.*;

Frame {
    var: frame
    var javafxPad = JavaFXPad {
        frame: frame
        url: if ARGUMENTS:String <> null then ARGUMENTS:String else null
    }        
    onClose: operation() {System.exit(0);}
    visible: true
    title: "JavaFXPad"
    height: 800
    width: 1000
    var fileChooser = FileChooser {
           fileFilters: {
                filter: operation(f:File) {
                    return f.isDirectory() or f.name.endsWith(".fx");
                }
                description: "JavaFX Files (*.fx)"
           }
    }
    menubar: MenuBar {
        menus: 
        [Menu {
            text: "File"
            mnemonic: F
            items:
            [MenuItem {
                text: "Open"
                mnemonic: O
                action: operation() {
                    fileChooser.action = operation(f:File) {
                         javafxPad.url = f.toURL().toString();
                         javafxPad.go();
                    };
                    fileChooser.showOpenDialog(null);                        
                }
            },
            MenuItem {
                text: "Save"
                mnemonic: S
                accelerator: {modifier: CTRL, keyStroke: S}
                enabled: bind javafxPad.url.startsWith("file:")
                action: operation() {
                     var url = javafxPad.url;
                     var fname = url.substring("file:".length());
                     var fw = new FileWriter(fname);
                     fw.write(javafxPad.userCode);
                     fw.close();
                }
            },
            MenuItem {
                text: "Save As"
                mnemonic: A
                action: operation() {
                    fileChooser.action = operation(f:File) {
                        var fw = new FileWriter(f.name);
                        fw.write(javafxPad.userCode);
                        fw.close();
                        javafxPad.url = f.toURL().toString();
                    };
                    fileChooser.showSaveDialog(null);                        
                }
            },
            MenuSeparator,
            MenuItem {
                text: "Exit"
                mnemonic: X
                action: operation() {
                    System.exit(0);
                }
            }]
        },
        Menu {
            mnemonic: R
            text: "Run"
            items:
            [RadioButtonMenuItem {
                mnemonic: A
                text: "Run Automatically"
                selected: bind javafxPad.runAutomatically
            },
            MenuItem {
                mnemonic: R
                text: "Run"
                enabled: bind not javafxPad.runAutomatically and javafxPad.isValid()
                action: operation() {
                    javafxPad.runNow();
                }
            }]
           
        }]
    }
    content: GroupPanel {

        var row1 = Row {}
        var row2 = Row {resizable: true}
        var col = Column {resizable: true}
        rows: [row1, row2]
        columns: col
        content: 
        [GroupLayout {
            row: row1
            column: col
            var row = Row {}
            var labelCol = new Column
            var fieldCol = new Column
            var butCol = new Column
            var zoomCol = new Column
            rows: row
            columns: [labelCol, fieldCol, butCol, zoomCol]
            content: 
            [SimpleLabel {
                row: row
                column: labelCol
                text: "Location:"
            },
            TextField {
                row: row
                column: fieldCol
                columns: 60
                value: bind javafxPad.url
                action: operation() {
                    javafxPad.go();
                }
            },
            Button {
                row: row
                column: butCol
                text: "Go"
                action: operation() {
                    javafxPad.go();
                }
            },
            ComboBox {
                row: row
                column: zoomCol
                selection: bind javafxPad.zoomSelection
                cells: bind foreach (i in javafxPad.zoomOptions)
                   ComboBoxCell { text: "{i}%" }
            }]
        },
        BorderPanel {
            row: row2
            column: col
            center: javafxPad
        }]
    }
}

attribute JavaFXPad.userCode = 
"import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Group \{
    content:
    [Rect \{
        x: 10
        y: 10
        width: 460
        height: 140
        fill: LinearGradient \{
            x1: 0
            y1: 0
            x2: 1
            y2: 0
            stops:
            [Stop \{offset: 0, color: green},
            Stop \{offset: .5, color: new Color(.5, 1, 0, 1)},
            Stop \{offset: 1, color: green}]
        }
        stroke: green
        strokeWidth: 3
    },
    Text \{
        x: 120
        y: 50
        content: \"JavaFX\"
        font: Font \{face: VERDANA, style: [ITALIC, BOLD], size: 60}
        fill: LinearGradient \{
            x1: 0, y1: 0, x2: 0, y2: 1
            stops: 
            [Stop \{
                offset: 0.2
                color: red
            },
            Stop \{
                offset: 0.5
                color: orange
            },
            Stop \{
                offset: .8
                color: red
            }]
        }
        filter: 
        [Glow \{
            amount: 0.1
        },
        Noise \{
            monochrome: true
            distribution: 0
        }]
    },
    View \{
        transform: translate(160, 200)
        content: Button \{
            icon: Image \{ url: \"javafxpad/images/duke.gif\" }
            text: \"Click Me!\"
        }
    }]
}";
