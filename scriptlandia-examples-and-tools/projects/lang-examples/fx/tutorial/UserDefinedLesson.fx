/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class UserDefinedLesson extends Lesson {
}

attribute UserDefinedLesson.exampleCode = bind
"import javafx.ui.*;
import javafx.ui.canvas.*;


class ExampleNode extends CompositeNode \{
    attribute x1: Number;
    attribute y1: Number;
    attribute x2: Number;
    attribute y2: Number;
    attribute stroke: Paint;
}

function ExampleNode.composeNode() = 

    Group \{
        content:
        [Line \{
            x1: bind x1
            y1: bind y1
            x2: bind x2
            y2: bind y2
            stroke: bind stroke
            strokeWidth: 2
        },
        Circle \{
            cursor: HAND
            cx: bind x1
            cy: bind y1
            radius: 5
            stroke: bind stroke
            strokeWidth: 2
            fill: new Color(0, 0, 0, 0)
            onMouseDragged: operation(e:CanvasMouseEvent) \{
                x1 += e.localDragTranslation.x;
                y1 += e.localDragTranslation.y;
            }
        },
        Circle \{
            cursor: HAND
            cx: bind x2
            cy: bind y2
            radius: 5
            stroke: bind stroke
            strokeWidth: 2
            fill: new Color(0, 0, 0, 0)
            onMouseDragged: operation(e:CanvasMouseEvent) \{
                x2 += e.localDragTranslation.x;
                y2 += e.localDragTranslation.y;
            }
        }]
    }
;


Canvas \{
    content: ExampleNode \{
        x1: 20
        y1: 20
        x2: 120
        y2: 80
        stroke: new Color(0.0, 0.0, 1.0, 0.5)
    }
}
";
attribute UserDefinedLesson.title = "User-Defined Graphic Objects";
attribute UserDefinedLesson.content = bind
"<html>
<body>
<h1>User-Defined Graphic Objects</h1>
<p>
<b>CompositeNode</b>
<p>
You can create your own user-defined graphic objects by extending the
JavaFX CompositeNode class and implementing its abstract composeNode() method.
<p>
You can return any type of graphic object from composeNode(). Whatever you
return will become the content of your user-defined object. 
<p>
In the example 
below I've created a user-defined object that consists of two circles and
a line. I've used JavaFX's data-binding operator to bind the start point of 
the line and the center point of the first circle to the (x1, y1) attributes
of my object and I've also bound the end point of the line and the center
point of the second circle to the the (x2, y2) attributes of my object. 
<p>
I've added mouse handlers to the circles that modify (x1, y1) and
(x2, y2) respectively when you drag the mouse. In addition, I've bound the 
stroke attribute of all three to the stroke attribute of my object. 
<p>
As a result, if you modify (x1, y1) or (x2, y2) then the line and both circles will 
move accordingly, if you drag the circles the line also moves, and if you
modify the stroke of my object the stroke of the circles and the line will
automatically change to that value.
</body>
</html>";
