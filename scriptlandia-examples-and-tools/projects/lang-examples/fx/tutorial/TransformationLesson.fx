/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class TransformationLesson extends Lesson {
    attribute transformValue: String?;
}



attribute TransformationLesson.exampleCode = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Rect \{
        transform: [{transformValue}]
        x: 10
        y: 10
        width: 300
        height: 50
        arcHeight: 20
        arcWidth: 20
        fill: cyan
        stroke: blue
        strokeWidth: 2
    }
}
";
attribute TransformationLesson.title = "Transformations";

attribute TransformationLesson.content = bind
"<html>
<body>
<h1>Transformations</h1>
<p>
Each graphic object has an optional transform attribute that contains a list of tranformation operations to apply to the object. 
<p>
Transformation operations allow you to control the location, rotation, scale, and skew of an object, as follows (click the links to see the effect):
<table width='100%'>
   <tr>
     <td><a href='{#(operation() {transformValue = null;})}'>None</a></td>
   </tr>
   <tr>
     <td><a href='{#(operation() {transformValue= "translate(100, 20)";})}'>translate(x, y)</a></td>
     <td>Moves an object so that its origin is at the point (x, y).</td>
   </tr>
   <tr>
     <td><a href='{#(operation() {transformValue= "rotate(20, 0, 0)";})}'>rotate(angle, cx, cy)</a></td>
     <td>Rotates an object by (angle) degrees around the point (cx, cy).</td>
   </tr>
   <tr>
     <td><a href='{#(operation() {transformValue= "scale(2.0, 2.0)";})}'>scale(x, y)</a></td>
     <td>Scales an object by a factor of (x) along the x-axis and (y) along y-axis.</td>
   </tr>
   <tr>
     <td><a href='{#(operation() {transformValue= "skew(10, 10)";})}'>skew(x, y)</a></td>
       <td>Skews an object by (x) degrees along the x-axis and (y) degrees along the y-axis.</td>
   </tr>
</table>
</body>
</html>";
