/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class AreasLesson extends Lesson {
    attribute area: String?;
    attribute noOpCode: String;
    attribute areaOpCode: String;
}

attribute AreasLesson.exampleCode = bind if area == null then noOpCode else areaOpCode;

attribute AreasLesson.noOpCode =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Group \{
        content:
        [Rect \{
            x: 20
            y: 20
            width: 300
            height: 100
            fill: wheat
            stroke: brown
        },
        Circle \{
            cx: 100
            cy: 100
            radius: 80
            fill: wheat
            stroke: brown
        }]
    }
}
";

attribute AreasLesson.areaOpCode = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: {area} \{
        shape1: Rect \{
            x: 20
            y: 20
            width: 300
            height: 100
        }
        shape2: Circle \{
            cx: 100
            cy: 100
            radius: 80
        }
        fill: wheat
        stroke: brown
    }
}
";

attribute AreasLesson.title = "Areas";
attribute AreasLesson.content = bind
"<html>
<style>
.semiIndented \{
  margin-left: 15;
}
</style>
<div>
    <h1>Areas</h1>
    <p>
    <b>Area Operations</b>
    <p>
        Area objects are shapes created by performing constructive geometry 
        operations on other shapes.
    <p>
    Click the links below to see the effect of each operation:
    <br>
    <table>
        <tr>
           <td><a href='{#(operation() {area = null;})}'>None</a></td>
           <td></td>
        </tr>
        <tr>
           <td><a href='{#(operation() {area = "Add";})}'>Add</a></td>
           <td>Adds the shape of shape2 to shape1.</td>
        </tr>
        <tr>
           <td><a href='{#(operation() {area = "Subtract";})}'>Subtract</a></td>
           <td>Subtracts the shape of shape2 from shape1.</td>
        </tr>
        <tr>
           <td><a href='{#(operation() {area = "Intersect";})}'>Intersect</a></td>
           <td>Creates the intersection of shape1 and shape2.</td>
        </tr>
        <tr>
           <td><a href='{#(operation() {area = "XOR";})}'>XOR</a></td>
           <td>Returns the combined area of shape1 and shape2 minus their intersection.</td>
        </tr>
    </table>
    <br>
    &nbsp;
</html>";
