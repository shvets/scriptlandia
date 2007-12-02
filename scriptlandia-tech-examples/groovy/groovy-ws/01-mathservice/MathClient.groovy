import groovyx.net.ws.WSClient

def proxy = new WSClient("http://localhost:6980/MathService?wsdl", this.class.classLoader)

def result = proxy.add(1.0 as double, 2.0 as double)
assert (result == 3.0)

result = proxy.square(3.0 as double)

assert (result == 9.0)
