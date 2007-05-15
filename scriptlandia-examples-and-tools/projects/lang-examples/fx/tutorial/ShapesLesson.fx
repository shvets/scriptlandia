/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class ShapesLesson extends Lesson {
    attribute rectExample:String;
    attribute circleExample:String;
    attribute ellipseExample:String;
    attribute lineExample:String;
    attribute polylineExample:String;
    attribute polygonExample:String;
    attribute arcExample:String;
    attribute cubicCurveExample:String;
    attribute quadCurveExample:String;
    attribute starExample:String;
    attribute textExample:String;
    attribute pathExample:String;
}

attribute ShapesLesson.circleExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Circle \{
        cx: 80
        cy: 80
        radius: 50
        fill: yellow
        stroke: green
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.rectExample = 
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Rect \{
        x: 20
        y: 20
        height: 80
        width: 300
        arcHeight: 20
        arcWidth: 20
        fill: cyan
        stroke: purple
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.ellipseExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Ellipse \{
        cx: 120
        cy: 80
        radiusX: 100
        radiusY: 50
        fill: orange
        stroke: blue
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.lineExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Line \{
        x1: 20
        y1: 20
        x2: 300
        y2: 50
        stroke: orangered       
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.polylineExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Polyline \{
        points: [10, 100, 50, 100, 50, 10, 30, 10, 30, 30, 40, 30]
        stroke: blue
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.polygonExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Polygon \{
        points: [75, 38, 129, 69, 129, 131, 75, 162, 21, 131, 21, 69]
        fill: lime
        stroke: blue
        strokeWidth: 2
    }
}
";

attribute ShapesLesson.arcExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: Arc \{
        x: 10
        y: 10
        height: 100
        width: 100
        startAngle: 0
        length: 90
        closure: PIE
        stroke: blue
        fill: pink
        strokeWidth: 2
    }
}";

attribute ShapesLesson.cubicCurveExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: CubicCurve \{
        x1: 10
        y1: 10
        ctrlx1: 10
        ctrly1: 100
        ctrlx2: 300
        ctrly2: 10
        x2: 300
        y2: 100
        stroke: red
        strokeWidth: 2
    }
}";

attribute ShapesLesson.quadCurveExample =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    content: QuadCurve \{
        x1: 20
        y1: 20
        ctrlx: 300
        ctrly: 60
        x2: 20
        y2: 100
        stroke: cornflowerblue
        strokeWidth: 2
    }
}";

attribute ShapesLesson.starExample =
"import javafx.ui.*;
import javafx.ui.canvas.*;

Canvas \{
    content: Star \{
        cx: 100
        cy: 100
        rin: 30
        rout: 70
        points: 5
        startAngle: 18
        stroke: green
        fill: lightgreen
        strokeWidth: 2
    }
}";

attribute ShapesLesson.textExample =
"import javafx.ui.*;
import javafx.ui.canvas.*;

Canvas \{
    content: Text \{
        x: 100
        y: 50
        content: \"This is a text object\"
        font: new Font(\"Tahoma\", \"BOLD\", 50)
        stroke: green
        fill: yellow
        strokeWidth: 2
    }
}";


attribute ShapesLesson.pathExample =
"import javafx.ui.*;
import javafx.ui.canvas.*;
import java.awt.Font;
Canvas \{
    content: Path \{
        d:
        [MoveTo \{x: 50, y: 50},
        QuadTo \{x1: -30, y1: 100, x2: 50, y2: 150},
        QuadTo \{x1: 100, y1: 230, x2: 150, y2: 150},
        QuadTo \{x1: 230, y1: 100, x2: 150, y2: 50},
        QuadTo \{x1: 100, y1: -30, x2: 50, y2: 50}]
        stroke: darkblue
        fill: blue
        strokeWidth: 4
    }
}";



attribute ShapesLesson.title = "Shapes";
attribute ShapesLesson.content = bind
"<html>
<style>
.semiIndented \{
  margin-left: 15;
}
</style>
<div>
    <h1>Shapes</h1>

    <p>JavaFX provides a number of built-in shapes as follows (click the links to see examples):</p>
    <br>
    <table>
      <tr>

        <td valign='top'><a href='{#(operation() {exampleCode = rectExample;})}'>Rect</a></td>

        <td valign='top' class='semiIndented'>Describes a rectangle
        with possibly rounded corners defined by a location
        (x,&nbsp;y), dimension (width&nbsp;,&nbsp;height), and
        optionally the width and height of an arc (arcWidth,
        arcHeight) with which to round the corners.</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = circleExample;})}'>Circle</a></td>

        <td valign='top' class='semiIndented'>Defines a circle by
        means of a center point (cx, cy) and a radius (radius).</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = ellipseExample;})}'>Ellipse</a></td>

        <td valign='top' class='semiIndented'>Defines an ellipse by
        means of a center point (cx, cy), a horizontal radius
        (radiusX), and vertical radius (radiusY).</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = lineExample;})}'>Line</a></td>

        <td valign='top' class='semiIndented'>Defines a straight
        line by means of two points (x1, y1), (x2, y2).</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = polylineExample;})}'>Polyline</a></td>

        <td valign='top' class='semiIndented'>Defines a line by
        means of a sequence of points (x1, y1, x2, y2,...xN, yN)</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = polygonExample;})}'>Polygon</a></td>

        <td valign='top' class='semiIndented'>Defines a polygon by
        means of a sequence of points (x1, y1, x2, y2,...xN, yN)</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = arcExample;})}'>Arc</a></td>

        <td valign='top' class='semiIndented'>Describes an elliptical
        arc by means of a bounding rectangle (x, y, width, height), a
        start angle (startAngle) in degrees, an angular extent (length) in degrees, and a 
        closure type (closure) OPEN, CHORD, or PIE. 
        <p>
        The bounding rectangle defines the outer boundary of a full ellipse 
        of which this arc is a partial section.   
        <p>
        The angles are specified relative to the non-square extents of the
        bounding rectangle such that 45 degrees always falls on the line
        from the center of the ellipse to the right corner of the bounding
        rectangle.
        <p>
        As a reslt if the bounding rectangle is noticeably longer along one
        axis than the other, the angles to the start and end of the arc segment
        will be skewed farther along the longer axis of the bounds.
        </td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = cubicCurveExample;})}'>CubicCurve</a></td>

        <td valign='top' class='semiIndented'>Defines a cubic parametric curve 
        segment by means of a start point (x1, y1), two control points, 
        (ctrlx1, ctrly1), (ctrlx2, ctrly2), and an end point (x2, y2).</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = quadCurveExample;})}'>QuadCurve</a></td>

        <td valign='top' class='semiIndented'>Defines a quadratic parametric curve
        segment by means of a start point (x1, y1), a control point (ctrlx, ctrly), 
        and an end point (x2, y2).</td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = starExample;})}'>Star</a></td>
        <td valign='top' class='semiIndented'>Describes a star by means of a center point (cx, cy), an inner radius (rin), an outer radius (rout), a number of points (points) and an optional angle (in degrees) at which to draw the first point (startAngle).
        </td>
      </tr>

      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = textExample;})}'>Text</a></td>

        <td valign='top' class='semiIndented'>Text objects are also shapes.</td>
      </tr>
      <tr>
        <td valign='top'><a href='{#(operation() {exampleCode = pathExample;})}'>Path</a></td>

        <td valign='top' class='semiIndented'>
    <p>Paths are the most general kind of shape, however it is
    quite hard to visualize anything but the most simple paths.
    In most cases complex paths are created with vector drawing
    tools and not by hand coding.</p>

    <p>The <code>Path</code> class has an attribute <code>d</code>
    (meaning \"data\") which contains a list of objects that define
    the points that make up the path. The objects assigned to
    <code>d</code> may be commands that add points (MoveTo, LineTo,
    CurveTo, QuadTo, HLine, VLine) or embedded shapes, in which
    case the points that make up the shape are added to the path.
    The list of commands must always start with an instance of
    MoveTo.</p>
    <br>
    <table>
      <tr>
        <td valign=\"top\">MoveTo</td>

        <td>Adds a point to the path by moving to the specified
        coordinates (x, y).</td>
      </tr>

      <tr>

        <td valign=\"top\">LineTo</td>

        <td>Adds a point to the path by drawing a straight line
        from the current coordinates to the new specified
        coordinates (x, y).</td>
      </tr>

      <tr>
        <td valign=\"top\">HLine</td>

        <td>Draws a horizontal line from the current point to
        x.</td>

      </tr>

      <tr>
        <td valign=\"top\">VLine</td>

        <td>Draws a vertical line from the current point to y.</td>
      </tr>

      <tr>

        <td valign=\"top\">CurveTo</td>

        <td>Adds a curved segment, defined by three new points, to
        the path by drawing a B&eacute;zier curve that intersects
        both the current coordinates and the coordinates
        (x3,&nbsp;y3), using the specified points (x1,&nbsp;y1) and
        (x2,&nbsp;y2) as B&eacute;zier control points.</td>
      </tr>

      <tr>

        <td valign=\"top\">QuadTo</td>

        <td>Adds a curved segment, defined by two new points, to
        the path by drawing a Quadratic curve that intersects both
        the current coordinates and the coordinates (x2,&nbsp;y2),
        using the specified point (x1,&nbsp;y1) as a quadratic
        parametric control point.</td>
      </tr>

      <tr>
        <td valign=\"top\">ClosePath</td>

        <td>Closes the current subpath by drawing a straight line
        back to the coordinates of the last <code>MoveTo</code> .
        If the path is already closed then it has no effect.</td>
      </tr>
    </table>
        </td>
      </tr>
    </table>

    <div>
</html>";
