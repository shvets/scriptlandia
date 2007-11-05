import groovyx.net.ws.WSClient

class TryIt {
    
    groovy.swing.SwingBuilder swing = new groovy.swing.SwingBuilder()
    
    def proxy = new WSClient("http://saintbook.org/MightyMaxims/MightyMaxims.asmx?WSDL", TryIt.class.classLoader)
        
    void main() {

        def frame = swing.frame(title:'Thought for the Day') {
          panel {
            label(proxy.ForToday())
          }
        }
        
        frame.pack()
        frame.show()
        
    }
        
}