/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

public class Tutorial {
    public attribute lessons: Lesson*;
}

@tutorial.introduction.title:String = "Introduction";

@tutorial.introduction.content:String = 
"<html>
<body>

<h1>Introduction</h1>

<p>
This tutorial provides an interactive canvas with a source code editor that displays the code for each example. You can edit the code yourself and immediately see the effect. The source code editor provides auto-completion, syntax highlighting, and error checking. To return to the original example code just click the Reset button which will appear when you start typing.
</p>
<p>
<b>Canvas</b>
</p>
<p>
The JavaFX Canvas supports a 2D vector graphics scene-graph model with a declarative API similar to SVG. Using this API you can declaratively create visual effects that combine Swing components together with 2D graphics (shapes, lines, curves, images, etc). Any object in the canvas (including Swing components) can be transformed (translated, rotated, scaled, skewed) and/or filtered (blurred, reflected, shadowed, etc).
</p>
<p>
In addition, objects can be combined into groups, and an entire group can be transformed or filtered as a whole. Groups also introduce local coordinate spaces. The objects contained in a group are positioned relative to the origin of the group.
</p>
<p>
You can also create user-defined classes that compose other scene graph objects into reusable components. Instances of such classes can be used anywhere a primitive object can be used and can likewise be transformed or filtered.
</p>
<p>
The Canvas itself is a Swing component and can be used anywhere other Swing components can be used (including inside another Canvas).
</p>
<br>
</body>
</html>";

@tutorial.introduction.exampleCode:String = 
"import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Canvas \{
    content: Text \{
        x: 20
        y: 20
        content: \"Welcome to \nJavaFX\"
        font: Font \{face: VERDANA, style: [ITALIC, BOLD], size: 70}
        fill: LinearGradient \{
            x1: 0, y1: 0, x2: 0, y2: 1
            stops: 
            [Stop \{
                offset: 0
                color: blue
            },
            Stop \{
                offset: 0.5
                color: dodgerblue
            },
            Stop \{
                offset: 1
                color: blue
            }]
        }
        filter: 
        [Glow \{
            amount: 0.1
        },
        Noise \{
            monochrome: true
            distribution: 0
        }]
    }
}";

attribute Tutorial.lessons = 
    [Lesson {
        title: @tutorial.introduction.title
        content: @tutorial.introduction.content
        exampleCode: @tutorial.introduction.exampleCode
    },
    ShapesLesson,
    PaintingLesson,
    TransformationLesson,
    GroupsLesson,
    SwingLesson,
    ImageLesson,
    TransparencyLesson,
    FilterLesson,
    InputLesson,
    AreasLesson,
    ClippingLesson,
    UserDefinedLesson,
    AnimationLesson]
    ;
