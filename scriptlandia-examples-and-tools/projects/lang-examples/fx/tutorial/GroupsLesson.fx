/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class GroupsLesson extends Lesson {
    attribute transformValue: String?;
}

attribute GroupsLesson.exampleCode = bind
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
        Polygon \{
             points: [5, 5, 25, 5, 15, 25]
             fill: gray
             stroke: black
             strokeWidth: 3
        }]
    }
}
";
attribute GroupsLesson.title = "Groups";
attribute GroupsLesson.content = bind
"<html>
<body>
<h1>Groups</h1>
<p>
Groups are non-visual container elements that introduce new coordinate systems. Graphic objects assigned to a Group's content attribute are positioned relative to the group's origin. In addition, the z-order of such objects is defined by their position within the group.
<p>
The example below shows a triangle, rectangle, and ellipse all contained in the
same group. Transformation operations applied to a group apply to the group
as a whole. Click the links below to see the effect.<br>
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
