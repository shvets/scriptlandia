//package tfd.scala.squib.scenegraph.demo;

import java.awt.{Color, Point, Font, Dimension, Rectangle, BasicStroke}
import javax.swing.{JFrame}

import com.sun.scenario.scenegraph._
import com.sun.scenario.animation._

import tfd.scala.squib._
import tfd.scala.squib.scenegraph._

object SceneGraphSimple extends Application {
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
                    'size -> new Dimension(100,200)
             ),
             borderlayout(),
                 contents(
                     scenepanel("scene",
                         'scene -> group("root",
                                       group("sub",
                                           translate(50, 100, 
                                               rotate("square", 0.0,
                                                   shape(
                                                         'shape -> new Rectangle(-20,-20,40,40),
                                                         'mode -> SGAbstractShape.Mode.STROKE_FILL,
                                                         'fillPaint -> Color.RED,
                                                         'drawPaint -> Color.ORANGE,
                                                         'drawStroke -> new BasicStroke(5.0f)
                                                   )
                                               )
                                           ),
                                           scale("blah", 1.0, 1.0, 
                                               text("blah",
                                                    'text -> "Blah",
                                                    'location -> new Point(10,20)
                                               )
                                           ),
                                           translate(10, 110,
                                               composite(0.75, 
                                                   component(
                                                             'component -> button("FooBar")
                                                   )
                                               )
                                                                                        )
                                       )
                         ),
                         'background -> Color.GREEN
                     ) -> "Center"
                 )
    )
    
    Clip.create(
            500,
            600.0,
            rotate.id("square"), 
            "rotation", 
            Array(-3.14159f, 3.14159f).map(_.asInstanceOf[Object]) 
    ).start();
    
    Clip.create(
            500, 
            900.0,
            scale.id("blah"), 
            "scaleY",
            Array(0.5, 5.0).map(_.asInstanceOf[Object]) 
    ).start();
}

SceneGraphSimple.main(args)