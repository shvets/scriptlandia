# scriptlandia_setup.rb

groovy.ui.GroovyMain

options.proxy.http = 'http://myproxy:8080'
options.proxy.exclude << '*.mycompany.com'
options.proxy.exclude << 'localhost'

repositories.local = 'c:/Work/maven-repository'

repositories.remote << ['http://www.ibiblio.org/maven2/'

artifact('org.apache.openjpa:openjpa-all:jar:0.9.7').invoke
artifacts(OPENJPA).each(&:invoke)

DOJO = '0.2.2'

url = "http://download.dojotoolkit.org/release-#{DOJO}/dojo-#{DOJO}-widget.zip"
download(artifact("dojo:dojo:zip:widget:#{DOJO}")=>url)


