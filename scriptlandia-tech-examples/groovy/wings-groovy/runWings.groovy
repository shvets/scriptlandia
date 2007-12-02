// GuessingGame.groovy
// http://groovy.codehaus.org/WingSBuilder

import org.sf.scriptlandia.ScriptlandiaHelper

ScriptlandiaHelper.resolveDependencies("org.wings", "wings", "3.0")
ScriptlandiaHelper.resolveDependencies("org.wings", "wings-css", "3.0")
ScriptlandiaHelper.resolveDependencies("org.wings", "wingx", "3.0")
ScriptlandiaHelper.resolveDependencies("org.wings", "wingx-cxx", "3.0")
ScriptlandiaHelper.resolveDependencies("org.codehaus.groovy-contrib", "wingsbuilder", "1.0")

ScriptlandiaHelper.resolveDependencies("log4j", "log4j", "1.2.13")

evaluate(new File("TestWings.groovy"))
