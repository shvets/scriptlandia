/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class ClippingLesson extends Lesson {
    attribute clip: Number?;
    attribute shapes: String*;
    operation clipCode(clip:Number): String;
    function noClipCode(): String;
}
attribute ClippingLesson.clip = -1;
attribute ClippingLesson.shapes = 
["Rect \{ x: 40, y: 40, width: 220, height: 80}",
 "Circle \{ cx: 150, cy: 80, radius: 60 }",
 "Text \{ x: 30, y: 30, font: new Font(\"Verdana\", \"BOLD\", 100), content: \"Text\" }"];

attribute ClippingLesson.exampleCode = bind clipCode(this.clip);

        
function ClippingLesson.noClipCode() =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Group \{
        content:
        [Rect \{
            x: 20
            y: 20
            height: 80
            width: 300
            arcHeight: 20
            arcWidth: 20
            fill: cyan
            stroke: purple
            strokeWidth: 2
        },
        Ellipse \{
            cx: 150
            cy: 80
            radiusX: 100
            radiusY: 50
            fill: orange
            stroke: blue
            strokeWidth: 2
        },
        View \{
            transform: translate(150, 70)
            content: Button \{
               cursor: DEFAULT
               text: \"Click Me!\"
            }
        },
        Polygon \{
             points: [5, 5, 25, 5, 15, 25]
             fill: gray
             stroke: black
             strokeWidth: 3
        },
        View \{
            transform: translate(100, 40)
            content: TextField \{
                columns: 15
                value: \"This is a text field\"
            }
        }]
    }
}
";

operation ClippingLesson.clipCode(clipN:Number) {
println("clipCode: {clipN}");
if (clipN < 0) {
     return noClipCode();
}
return 
"import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Canvas \{
    content: Clip \{
        shape: {shapes[clipN]}
        content: 
        [Rect \{
            x: 20
            y: 20
            height: 80
            width: 300
            arcHeight: 20
            arcWidth: 20
            fill: cyan
            stroke: purple
            strokeWidth: 2
        },
        Ellipse \{
            cx: 150
            cy: 80
            radiusX: 100
            radiusY: 50
            fill: orange
            stroke: blue
            strokeWidth: 2
        },
        View \{
            transform: translate(150, 70)
            content: Button \{
               cursor: DEFAULT
               text: \"Click Me!\"
            }
        },
        Polygon \{
             points: [5, 5, 25, 5, 15, 25]
             fill: gray
             stroke: black
             strokeWidth: 3
        },
        View \{
            transform: translate(100, 40)
            content: TextField \{
                columns: 15
                value: \"This is a text field\"
            }
        }]
    }
}";
}


attribute ClippingLesson.title = "Clipping";
attribute ClippingLesson.content = bind
"<html>
<body>
<h1>Clipping</h1>
<p>
The Clip object is a group which has an additional shape attribute which \"clips\" its visual content. Only the subset of its content that intersects the shape will be displayed. 
</p>
<p>
Click the links below to see the effect of several different shapes.
<br>
<table>
   <tr>
     <td>Clip shape:</td>
     <td><a href='{#(operation() {clip = -1;})}'>None</a></td>
     <td><a href='{#(operation() {clip = 0;})}'>Rectangle</a></td>
     <td><a href='{#(operation() {clip = 1;})}'>Circle</a></td>
     <td><a href='{#(operation() {clip = 2;})}'>Text</a></td>
   </tr>
</table>
</html>";
