/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class PaintingLesson extends Lesson {
    attribute colorExampleStroke: String;
    attribute colorExampleFill: String;
    attribute colorExample: String;
    attribute linearGradientExample:String;
    attribute spreadMethod: String;
    attribute radialGradientExample:String;
    attribute patternExample:String;

    attribute examples: String*;
    attribute selectedExample: Number?;
}

attribute PaintingLesson.title = "Painting";
attribute PaintingLesson.exampleCode = bind examples[selectedExample];

attribute PaintingLesson.colorExample = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Circle \{
        cx: 80
        cy: 80
        radius: 50
        fill: {colorExampleFill}
        stroke: {colorExampleStroke}
        strokeWidth: 2
    }
}
";
attribute PaintingLesson.examples = bind [colorExample, 
                                          linearGradientExample,
                                          radialGradientExample,
                                          patternExample];
attribute PaintingLesson.selectedExample = -1;
attribute PaintingLesson.colorExampleFill = "red";
attribute PaintingLesson.colorExampleStroke = "red";

attribute PaintingLesson.spreadMethod = "PAD";

attribute PaintingLesson.linearGradientExample = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Rect \{
        x: 10
        y: 10
        width: 500
        height: 80
        stroke: black
        strokeWidth: 1.5
        fill: LinearGradient \{
            x1: 0.25, y1: 0, x2: 0.75, y2: 0
            stops:
            [Stop \{
                offset: 0.0
                color: red
            },
            Stop \{
                offset: 1.0
                color: blue
            }]
            spreadMethod: {spreadMethod}
        }
    }
}
";

attribute PaintingLesson.radialGradientExample = bind
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Rect \{
        transform: translate(10, 10)
        width: 300
        height: 80
        stroke: black
        strokeWidth: 1.5
        fill: RadialGradient \{
            cx: 150, cy: 40, focusX: 150, focusY: 40
            radius: 100
            stops:
            [Stop \{
                offset: 0.0
                color: red
            },
            Stop \{
                offset: 0.5
                color: orange
            },
            Stop \{
                offset: 1.0
                color: blue
            }]
            spreadMethod: {spreadMethod}
        }
    }
}
";

attribute PaintingLesson.patternExample = bind
"import javafx.ui.*;
import javafx.ui.canvas.*;
Canvas \{
    content: Ellipse \{    
        fill: Pattern \{
             content: Polygon \{
                 points: [5, 5, 25, 5, 15, 25]
                 fill: orange
                 stroke: red
                 strokeWidth: 3
             }
       } 
       cx: 100
       cy: 50
       radiusX: 90
       radiusY: 40
       stroke: black
    }
}";

attribute PaintingLesson.content = bind
"<html>
<body>
<div>
      <h1>Painting</h1>
      <p>All visual elements have two attributes named 'stroke' and
      'fill' which are used to paint them. The 'stroke' or border
      attribute determines the paint of the outside edge of the
      object while the 'fill' value determines the inner paint.
      Fill and stroke values are defined by instances of the JavaFX Paint class.
      <p>
      <b><a href='{#(operation() {selectedExample = 0;})}'>Colors</a></b> (click this link to show the example)</p>
      <p>
      The JavaFX Color class extends Paint and therefore is suitable to supply fill and stroke values.</p>
      JavaFX defines enumerations for the standard web colors. In addition, you can create
      custom colors using the Color class constructor which takes four arguments representing the red, green, blue, and opacity attributes of the color respectively. The arguments
      may be real numbers between 0 and 1 or integer values between 2 and 255. 
      </p>
      <p>
      Click the links below to change the stroke and fill of the circle in the example or try editing the code yourself.
      <p>
      <b>Stroke</b>
      <table>
        {foreach (s in ["red", "green", "blue", "purple", "yellow", "none"])
            "<tr><td><a href='{#(operation() {colorExampleStroke = if s == "none" then "null" else s;})}'>{s}</td></tr>"
        }
      </table>
      <p>
      <b>Fill</b>
      <table>
        {foreach (s in ["red", "green", "blue", "purple", "yellow", "none"])
            "<tr><td><a href='{#(operation() {colorExampleFill = if s == "none" then "null" else s;})}'>{s}</td></tr>"
        }
      </table>

      <p><b>Gradients</b></p>

      <p>Gradients are smooth transitions from one color to
      another. The end points of these transitions are called stops.
      Stops are placed at fractional offsets between 0 and 1 and are later scaled to fit into the area chosen for the gradient. These stops have color
      and offset attributes. 
      <p>
      If the gradient doesn't fill the entire region then its spreadMethod
      attribute determines how the target region is filled. Possible values are: PAD, 
      which says to use the terminal colors of the gradient to fill the remainder 
      of the target region, REFLECT, which says to reflect the gradient 
      pattern start-to-end, end-to-start, start-to-end, etc. continuously until 
      the target rectangle is filled, and REPEAT, which says to repeat the gradient 
      pattern start-to-end, start-to-end, start-to-end, etc. continuously until the 
      target region is filled.
      <p>
      <b><a href='{#(operation() {selectedExample = 1;})}'>Linear Gradient<a></b>&nbsp;(click this link to show the example)
      <p>
      A linear gradient describes a color transition along a line given by two
      end points (x1, y1), (x2, y2). The coordinates of the end points are 
      given as fractional values between 0 and 1. The line is later scaled relative
      to the area to be painted by the gradient. The default values (0, 0), (1, 0) 
      describe a horizontal transition filling the entire area. A vertical linear 
      gradient filling the entire are can be defined with (0, 0), (0, 1). By changing the direction of the line you also change the direction of the gradient.
      <br>
      <table>
        <tr>
          <td><a href='{#(operation() {spreadMethod = "PAD";})}'>PAD</a></td>
          <td><a href='{#(operation() {spreadMethod = "REFLECT";})}'>REFLECT</a></td>
          <td><a href='{#(operation() {spreadMethod = "REPEAT";})}'>REPEAT</a></td>
           <td>(click these links to change the spreadMethod)
        </tr>
        </table>
      </p>
      <p>
      <b><a href='{#(operation() {selectedExample = 2;})}'>Radial Gradient<a></b>&nbsp;(click this link to show the example)
      <p>
      A radial gradient describes a color transition along the radius of a 
      circle defined by a center point (cx, cy), a focal point (focusX, focusY),  and a radius (radius). The
      last stop in the gradient is mapped to the perimeter of the circle.
      The first stop in the gradient is mapped to the focal point.
      If (cx, cy) isn't provided it defaults to (radius, radius). If (focusX, focusY) isn't provided it defaults to (cx, cy).
      <table>
        <tr>
          <td><a href='{#(operation() {spreadMethod = "PAD";})}'>PAD</a></td>
          <td><a href='{#(operation() {spreadMethod = "REFLECT";})}'>REFLECT</a></td>
          <td><a href='{#(operation() {spreadMethod = "REPEAT";})}'>REPEAT</a></td>
           <td>(click these links to change the spreadMethod)
        </tr>
        </table>
      <p>
      <b>Patterns</b>
      <p>
      <b><a href='{#(operation() {selectedExample = 3;})}'>Pattern<a></b>&nbsp;(click this link to show the example)
      <p>
        A Pattern is used to fill or stroke an object using a pre-defined graphic object which can be replicated (\"tiled\") at fixed intervals in x and y to 
         cover the areas to be painted. 
          <p>
         Attributes (x, y, width, height) define a reference rectangle somewhere on 
         the infinite canvas. The reference rectangle has its top/left at (x,y) and 
         its bottom/right at (x+width,y+height). The tiling theoretically extends a 
         series of such rectangles to infinity in X and Y (positive and negative), 
         with rectangles starting at (x + m*width, y + n*height) for each possible 
         integer value for m and n. If the rectangle (x, y, width, height) isn't specified it defaults to the bounds of the Pattern's content.
         <p>
         Any object that can appear in a Canvas can be used as the content of
         a Pattern.
      <p>
      
</div>
</body>
</html>";
