/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class ImageLesson extends Lesson {
    attribute transformValue: String?;
}
attribute ImageLesson.title = "Images";
attribute ImageLesson.exampleCode = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Group \{   
        transform: translate(20, 20)
        content: ImageView \{
            transform: [{transformValue}]
            image: Image \{ url: \"javafxpad/images/duke.gif\" }
        }
    }
}
";

attribute ImageLesson.content = bind
"<html>
<body>
<h1>Images</h1>
<p>
<b>ImageView</b>
<p>
You can place images into a canvas using the JavaFX ImageView element. The ImageView element has an image attribute that identifies the image that will be displayed. 
<p>
Transformation operations applied to an ImageView also affect the image.
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
