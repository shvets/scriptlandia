import groovyx.net.ws.WSServer

def server = new WSServer()

server.setNode("MathService", "http://localhost:6980/MathService")

