/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class AnimationLesson extends Lesson {
}

attribute AnimationLesson.exampleCode = bind
"import javafx.ui.*;
import javafx.ui.canvas.*;


class AnimationExample extends CompositeNode \{
    attribute width: Number;
    attribute height: Number;
    attribute opacityValue: Number;
    attribute rotationValue: Number;
}
// Initial values
attribute AnimationExample.width = 200;
attribute AnimationExample.height = 100;
attribute AnimationExample.opacityValue = 1.0;
attribute AnimationExample.rotationValue = 0;

function AnimationExample.composeNode() = 

    Group \{
        content:
        [Rect \{
            width: bind width
            height: bind height
            arcHeight: 20
            arcWidth: 20
            opacity: bind opacityValue
            transform: bind [translate(80, 50), rotate(rotationValue, width/2, height/2)]
            fill: dodgerblue
            stroke: darkblue
            strokeWidth: 2
        },
        View \{
            content: GroupPanel \{
                cursor: DEFAULT
                var row = Row \{alignment: BASELINE}
                var column1 = Column \{ }
                var column2 = Column \{ }
                var column3 = Column \{ }
                var column4 = Column \{ }
				var column5 = Column \{ }
                rows: [row]
                columns: [column1, column2, column3, column4, column5]
                content:
                [SimpleLabel \{
                    row: row
                    column: column1
                    text: \"Animate:\"
                },
                Button \{
                    row: row
                    column: column2
                    opaque: false
                    mnemonic: W
                    text: \"Width\"
                    action: operation() \{
                        width = [0..200] dur 1000;
                    }
                },
                Button \{
                    row: row
                    column: column3
                    opaque: false
                    mnemonic: H
                    text: \"Height\"
                    action: operation() \{
                        height = [0..80] dur 1000;
                    }
                },
                Button \{
                    row: row
                    column: column4
                    opaque: false
                    mnemonic: O
                    text: \"Opacity\"
                    action: operation() \{
                        opacityValue = [0.00,0.01 .. 1.00] dur 1000;
                    }
                },
                Button \{
                    row: row
                    column: column5
                    opaque: false
                    mnemonic: R
                    text: \"Rotation\"
                    action: operation() \{
                        rotationValue = [0..360] dur 1000;
                    }
                }]
            }
        }]
    }
;


Canvas \{
    content: AnimationExample \{
    }
}
";

attribute AnimationLesson.title = "Animation";
attribute AnimationLesson.content = bind
"<html>
<body>
<h1>Animation</h1>
<p>
<b>The <span color='#7F0055'>dur</span> operator</b>
<p>
In JavaFX, you create animations using the dur (\"duration\") operator. This operator applies an array to a time interval (specified in milliseconds). It divides the time interval by the number of elements in the array and then at each such sub-interval it returns the corresponding element from the array. This is done asynchronously. For example:
<br>
<br>
<div style='margin-left:15;'>
   <code><b color='#7F0055'>var</b> i = [1..100] <b color='#7F0055'>dur</b> 1000 <b color='#7F0055'>linear</b></code>;
</div>
<p>
In this example every 10 milliseconds the variable i will be assigned a new value from the list of integers between one and a hundred. The qualifier \"linear\" indicates that linear interpolation will be performed. By default JavaFX performs an \"ease-in, ease-out\" interpolation, meaning that the selection of elements from the array starts out slowly, accelerates to a constant velocity, and then when it nears the end of the array deccelarates again. The possible values for the interpolation qualifier are as follows:
<ul>
  <li>easeboth</li>
  <li>easein</li>
  <li>easeout</li>
  <li>linear</li>
</ul>
<p>
The dur operator can also be applied to a for loop. In this case it assigns new elements to the loop header variables and then executes the loop body at each sub-interval.
<p>
Thus the dur operator allows you to write \"time-iterated\" arrays and loops.
</p>
<p>
To perform animations you simply use assignment or bind expressions together with the dur operator to repeatedly assign values to a graphic object's properties over some time interval. Any property can be animated. For example you can do \"fade\" animations by animating the opacity attribute, or \"slide\" animations by animating the translation.
</p>
<p>
The below example demonstrates animations of four different properties of a rectangle: width, height, opacity, and rotation.
</p>
<br>
&nbsp;
</body>
</html>";
