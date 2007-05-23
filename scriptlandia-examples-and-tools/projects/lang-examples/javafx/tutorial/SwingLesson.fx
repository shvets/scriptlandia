/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class SwingLesson extends Lesson {
    attribute transformValue: String?;
}
attribute SwingLesson.title = "Swing Components";
attribute SwingLesson.exampleCode = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Group \{
        transform: [{transformValue}]
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

attribute SwingLesson.content = bind
"<html>
<body>
<h1>Swing Components</h1>
<p>
<b>View</b>
<p>
Using the JavaFX View element it's possible to place Swing Components into a Canvas along with other graphic elements. The content of a View element can be any Swing component.
<p>
In the below example I've added a text field and a button to the group from the previous chapter containing the triangle, rectangle, and ellipse. 
<p>
Transformation operations applied to a View affect the Swing component as well.
<p>
<table>
   <tr>
     <td>Transformation:</td>
     <td><a href='{#(operation() {transformValue = null;})}'>None</a></td>
     <td><a href='{#(operation() {transformValue= "translate(100, 20)";})}'>Translate</a></td>
     <td><a href='{#(operation() {transformValue= "rotate(20, 0, 0)";})}'>Rotate</a></td>
     <td><a href='{#(operation() {transformValue= "scale(2.0, 2.0)";})}'>Scale</a></td>
     <td><a href='{#(operation() {transformValue= "skew(10, 10)";})}'>Skew</a></td>
   </tr>
</table>
</body>
</html>";
