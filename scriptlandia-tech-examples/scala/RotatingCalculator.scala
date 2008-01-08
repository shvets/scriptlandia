//package tfd.scala.squib.scenegraph.demo;

import java.awt.{Color, Point, Font, Dimension, Rectangle, BasicStroke}
import javax.swing.{JFrame}

import com.sun.scenario.scenegraph._
import com.sun.scenario.animation._

import tfd.scala.squib._
import tfd.scala.squib.scenegraph._

object RotatingCalculator extends Application {
    import java.awt.Color
    import java.awt.event.ActionEvent
    import javax.swing.{JFrame,JLabel,SwingConstants}

    // The attributes_default of a component type specify attributes that will automatically be 
    // set for any instance of that component afterward.
    //
    // The attributes_by_pattern of a component type maps regular expression to attributes 
    // that will automatically set for instances whose id mathes the regular expression 
    // that are created afterward. It's kind of a "pseudo CSS".
    // 
    // Ensure all frames exit on close
    frame.attributesDefault = attributes('defaultcloseoperation->JFrame.EXIT_ON_CLOSE)
    
    // By default make button text blue ...
    button.attributesDefault = attributes('foreground->Color.blue)
    
    // ...unless it's id is digit then red
    button.attributesByPattern += ("\\d" -> attributes('foreground->Color.red))

    // create a panel for the buttons assign to val
    // This is to show that the entire GUI doesn't have to be one
    // gigantic nesting.

    val calcField = textfield(
        'text->"0",
        'horizontalalignment->SwingConstants.RIGHT,
        'editable->false,
        'background->Color.white
    )
    
    var stored = 0.0
    var operation = ' '
    var newInput = true
    
    def doNumber(num:Int)(ae: ActionEvent) = { 
        val prepend = if (newInput) "" else calcField.getText
        calcField.setText(prepend + num)
        newInput = false
    }
    
    def doOperation(oper:Char)(ae: ActionEvent) = {
        operation = oper
        stored = calcField.getText().toDouble
        newInput = true
    }
    
    val gridPanel = panel(
        gridlayout('rows->5, 'columns->4, 'vgap->4, 'hgap->2),
        contents(
                button(
                        attributes(
                                'text->"C",
                                'foreground->Color.black),
                        { ae:ActionEvent => 
                                calcField.setText("0")
                                newInput = true
                        }
                ),
                button(
                        attributes(
                        'text->"CE",
                        'foreground->Color.black),
                        { ae:ActionEvent => 
                                calcField.setText("0")
                                stored = 0.0
                                newInput = true
                        }),
                new JLabel(""),
                button("/",     doOperation('/')_),
                button("7","7", doNumber(7)_),    
                button("8","8", doNumber(8)_),
                button("9","9", doNumber(9)_),
                button("*",     doOperation('/')_),
                button("4","4", doNumber(4)_),
                button("5","5", doNumber(5)_),
                button("6","6", doNumber(6)_),
                button("-",     doOperation('-')_),
                button("1","1", doNumber(1)_),
                button("2","2", doNumber(2)_),
                button("3","3", doNumber(3)_),
                button("+",     doOperation('+')_),
                button("0","0", doNumber(0)_),
                button("+/-", { ae:ActionEvent => 
                                   calcField.setText("" + (-calcField.getText().toDouble))
                }),
                button(".", { ae:ActionEvent => 
                                   if (newInput) {
                                        calcField.setText("0.")
                                        newInput = false;
                                   } else {
                                        val text = calcField.getText()
                                        if (!text.contains('.')) {
                                            calcField.setText(text + ".")
                                        }
                                   }
                }),
                button("=", { ae:ActionEvent => 
                                   val current = calcField.getText().toDouble
                                   calcField.setText("" + (operation match {
                                       case '+' =>  (stored + current)
                                       case '*' =>  (stored * current)
                                       case '-' =>  (stored - current)
                                       case '/' =>  (stored / current)
                                       case _ => current
                                   }))
                                   newInput = true
                })            
        )                        
    )

    var calculatorPanel = panel(
        borderlayout(),
        contents(
            panel("calc_panel",
                borderlayout(),
                contents(
                    calcField -> "North", 
                    gridPanel -> "Center" 
                )
            ) -> "Center"
        )
    )
    
     text.attributesDefault = attributes(
             'font -> new Font("Courier", Font.BOLD, 24),
             'mode -> SGAbstractShape.Mode.FILL,
             'fillPaint -> Color.BLUE
     )
    
     val frm = frame(
            attributes(
                    'title->"SQUIB Scenegraph Demo",
                    'visible -> true,
                    'defaultCloseOperation -> JFrame.EXIT_ON_CLOSE,
                    'size -> new Dimension(300,300)
             ),
             borderlayout(),
                 contents(
                         scenepanel("scene",
                              'scene -> group("root",
                                                  translate(140,140,
                                              rotate("calculator", 0.0,
                                                  translate(-85,-90,
                                                       component(
                                                      'component -> calculatorPanel
                                                       )
                                                                                          )))
                               ),
                              'background -> Color.GREEN
                         ) -> "Center"
                    )
    )
    
    Clip.create(
            10000, // duration
            600.0,
            rotate.id("calculator"), // animating obj
            "rotation", // animating prop
            Array(-3.14159f, 3.14159f).map(_.asInstanceOf[Object]) // "to" value
    ).start();
}


RotatingCalculator.main(args)