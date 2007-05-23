/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class TransparencyLesson extends Lesson {
    attribute transformValue: String?;
}

attribute TransparencyLesson.exampleCode = bind
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
            opacity: 0.3
            cx: 150
            cy: 80
            radiusX: 100
            radiusY: 50
            fill: orange
            stroke: blue
            strokeWidth: 2
        },
        View \{
            opacity: 0.5
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
            opacity: 0.5
            transform: translate(100, 40)
            content: TextField \{
                columns: 15
                value: \"This is a text field\"
            }
        }]
    }
}";

attribute TransparencyLesson.title = "Transparency";
attribute TransparencyLesson.content = bind
"<html>
<body>
<h1>Transparency</h1>
<p>
Each graphical object has an opacity attribute which takes a fractional value between 0 and 1, 0 being transparent and 1 opaque. By default all objects are opaque.
</p>
<p>
Below is an example containing several objects with various levels of transparency.
</html>";
