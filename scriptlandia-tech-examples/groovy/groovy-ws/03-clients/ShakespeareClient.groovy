import groovyx.net.ws.WSClient
import java.awt.BorderLayout

//class ShakespeareClient {
    
    def groovy.swing.SwingBuilder swing = new groovy.swing.SwingBuilder()
    
    def proxy = new WSClient("http://www.xmlme.com/WSShakespeare.asmx?WSDL", ShakespeareClient.class.classLoader)

    def refresh = swing.action(
          name:'Refresh',
          closure:this.&refreshText,
          mnemonic:'R'
    )

       
 // void main() {

        def frame = swing.frame(title:'Shakespeare',size:[300,300]) {
          panel(layout: new BorderLayout()) {
            textField(id:'quote',constraints: BorderLayout.CENTER, "fair is foul")
            textArea (id:'area',constraints: BorderLayout.NORTH, proxy.GetSpeech(swing.quote.text).replaceAll("><",">\n   <"))
            button(constraints: BorderLayout.SOUTH,"Search",action:refresh)
          }
        }
        
        frame.pack()
        frame.show()
        
  //  }
        
//}

    def refreshText(event) {
          def newQuote = proxy.GetSpeech(swing.quote.text)
          swing.area.text = newQuote.replaceAll("><",">\n   <")
    }
