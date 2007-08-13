// GuessingGame.groovy

// http://groovy.codehaus.org/WingSBuilder

import org.sf.scriptlandia.ScriptlandiaHelper

ScriptlandiaHelper.resolveDependencies("org.codehaus.groovy-contrib", "wingsbuilder", "1.0")
//      ScriptlandiaHelper.resolveDependencies("wings", "wings-css", "3.0")

      def randomNr = (new Random().nextInt(10) + 1) as String
      def builder = new WingSBuilder()

      def border = SBorderFactory.createSTitledBorder(
                    SBorderFactory.createSLineBorder([0,0,255] as Color, 2),
                    "Guessing Game")
      def font = new SFont( null, SFont.BOLD, 14 )
/*
      def frame = builder.frame {
         panel( border: border){
            gridLayout( columns: 1, rows: 5, vgap: 10)
            label("Hello World - this is wingS (+WingSBuilder&Groovy)!",
                  font: font)
            label("We want fun, so let's play a game!" +
                  "Try to guess a number between 1 and 10.")
            textField( id: "answer" )
            button( text: "Guess!", actionPerformed: { event ->
               def value = builder.answer.text
               if( value == randomNr ){
                  builder.message.text = "Congratulations! You guessed my number!"
               }else{
                  builder.message.text = "No - '${value}' is not the right number. Try again!"
               }
            })
            label( id: "message" )
         }

      }
*/
      frame.visible = true
