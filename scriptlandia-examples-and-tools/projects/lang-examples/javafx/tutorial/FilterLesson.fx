/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class FilterLesson extends Lesson {
    attribute filterValue: String?;
}

attribute FilterLesson.exampleCode = bind
"import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Canvas \{
    content:
    Group \{
        filter: [{filterValue}]
        content: 
        [Star \{
            cx: 100
            cy: 100
            points: 5
            startAngle: 18
            rin: 30
            rout: 70
            fill: blue
    
        },
        Text \{
            x: 200
            y: 20
            content: \"5-point Star\"
            font: new Font(\"Verdana\", \"BOLD\", 60)
            fill: red
        }]
    }
}";

attribute FilterLesson.title = "Filter Effects";
attribute FilterLesson.content = bind
"<html>
<body>
<h1>Filter Effects</h1>
<p>
Each graphical object has an optional filter attribute which consists of a list of Filters. If present, whenever the content of the object changes a new image will be created consisting of the result of applying the list of filters (in order) to the new content. The generated image will then be used to paint the object.
</p>
<p>
Click the links below to see the effect of several different filters.
<br>
<table>
   <tr>
     <td>Filter:</td>
     <td><a href='{#(operation() {filterValue = null;})}'>None</a></td>
     <td><a href='{#(operation() {filterValue = "ShadowFilter";})}'>Shadow</a></td>
     <td><a href='{#(operation() {filterValue = "GaussianBlur \{radius: 6}";})}'>Blur</a></td>
     <td><a href='{#(operation() {filterValue = "Noise \{distribution: 0}";})}'>Noise</a></td>
     <td><a href='{#(operation() {filterValue = "ShapeBurst";})}'>ShapeBurst</a></td>
   </tr>
</table>
</html>";
