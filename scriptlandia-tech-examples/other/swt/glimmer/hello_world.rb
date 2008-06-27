# Copyright (C) 2007-2008 Annas Al Maleh
# Licensed under the LGPL. See /COPYING.LGPL for more details.

require "java"

jrubyHome = java.lang.System.getProperty("jruby.home")

require jrubyHome + "/lib/ruby/gems/1.8/gems/glimmer-0.1.0.0/src/swt"

class HelloWorld
	include_package 'org.eclipse.swt'
	include_package 'org.eclipse.swt.layout'
	
	include Glimmer

	def launch
		@shell = shell {
			text "SWT"
			composite {
				label { 
					text "Hello World!" 
				}
			}
		}

    @shell.open
	end
end

HelloWorld.new.launch
