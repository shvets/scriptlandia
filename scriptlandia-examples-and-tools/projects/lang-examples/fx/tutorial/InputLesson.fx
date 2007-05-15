/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;

class InputLesson extends Lesson {
}

attribute InputLesson.exampleCode =
"import javafx.ui.canvas.*;
import javafx.ui.*;

Canvas \{
    var label = Label \{}
    function displayMouseEvent(title:String, e:CanvasMouseEvent) \{
        return 
        \"<html>
           <table>
              <tr><td><b>\{title}</b></td></tr>
              <tr>
                 <td>button:</td>
                 <td>\{e.button}</td>
              </tr>
              <tr>
                 <td>clickCount:</td>
                 <td>\{e.clickCount}</td>
              </tr>
              <tr>
                 <td>x:</td>
                 <td>\{e.x}</td>
              </tr>
              <tr>
                 <td>y:</td>
                 <td>\{e.y}</td>
              </tr>
              <tr>
                 <td>localX:</td>
                 <td>\{e.localX}</td>
              </tr>
              <tr>
                 <td>localY:</td>
                 <td>\{e.localY}</td>
              </tr>
              \{
                if (e.dragTranslation <> null) 
                then
                \"<tr>
                   <td>dragX:</td>
                   <td>\{e.dragTranslation.x}</td>
                </tr>
                <tr>
                   <td>dragY:</td>
                   <td>\{e.dragTranslation.y}</td>
                </tr>
                <tr>
                   <td>localDragX:</td>
                   <td>\{e.localDragTranslation.x}</td>
                </tr>
                <tr>
                   <td>localDragY:</td>
                   <td>\{e.localDragTranslation.y}</td>
                </tr>\"
                else
                \"\"
              }
          </table>
        </html>\"
    }
    content: 
    [View \{
        transform: translate(200, 10)
        content: label
    },
    Group \{
        transform: [translate(30, 30), rotate(45, 50, 50)]
        content: Circle \{
            cx: 50
            cy: 50
            radius: 50
            fill: yellow
            stroke: green
            strokeWidth: 2
            onMouseClicked: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Clicked\", e);
            }
            onMousePressed: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Pressed\", e);
            }
            onMouseEntered: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Entered\", e);
            }
            onMouseExited: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Exited\", e);
            }
            onMouseMoved: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Moved\", e);
            }
            onMouseReleased: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Released\", e);
            }
            onMouseDragged: operation(e:CanvasMouseEvent) \{
                label.text = displayMouseEvent(\"Mouse Dragged\", e);
            }
        }
    }]
}
";

attribute InputLesson.title = "Input Events";
attribute InputLesson.content = bind
"<html>
<style>
.semiIndented \{
  margin-left: 15;
}
</style>
<div>
    <h1>Input Events</h1>
    <p>
    <b>Mouse Events</b>
    <p>
    Each graphical object has the following function-valued attributes to handle mouse 
    events:
    <br>
    <ul>
      <li>onMouseEntered</li>
      <li>onMouseExited</li>
      <li>onMouseMoved</li>
      <li>onMouseDragged</li>
      <li>onMousePressed</li>
      <li>onMouseReleased</li>
      <li>onMouseClicked</li>
    </ul>
    <br>        
    If you assign a function or operation to any of these attributes then
    your function or operation will be called whenever the corresponding 
    mouse event occurs. Your function will be passed one argument of type
    CanvasMouseEvent. CanvasMouseEvent has the following attributes:
    <br>
    <table>
        <tr>
        <td>x</td>
        <td>The x position of the mouse cursor in Canvas coordinates</td>
        </tr>
        <tr>
        <td>y</td>
        <td>The y position of the mouse cursor in Canvas coordinates</td>
        </tr>
        <tr>
        <td>localX</td>
        <td>The x position of the mouse cursor in the object's local coordinates.</td>
        </tr>
        <tr>
        <td>localY</td>
        <td>The y position of the mouse cursor in the object's local coordinates.</td>
        </tr>
        <tr>
        <td valign='top'>button</td>
        <td valign='top'>The ordinal number of the mouse button<br>
                <ol>
                     <li>Left</li>
                     <li>Center</li>
                     <li>Right</li>
                </ol>
        </td>
        </tr>
        <tr>
        <td>clickCount</td>
        <td>The number times the mouse button was clicked</td>
        </tr>
        <tr>
        <td>dragTranslation</td>
        <td>The (x, y) translation of the current drag in Canvas coordinates.</td>
        </tr>
        <tr>
        <td>localDragTranslation</td>
        <td>The (x, y) translation of the current drag in the object's local coordinates.</td>
        </tr>
    </table>
    <p>
    In the example below the details of the mouse events received by the yellow 
    circle are dynamically displayed.
    <p>
    The local coordinates reflect the rotation and translation of the circle's
    coordinate space.
    <br>
    &nbsp;
</html>";
