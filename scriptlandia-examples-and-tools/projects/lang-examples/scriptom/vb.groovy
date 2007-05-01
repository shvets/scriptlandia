//

import org.codehaus.groovy.scriptom.ActiveXProxy

// invoke some VBScript from Groovy and get the results!
def sc = new ActiveXProxy("ScriptControl")
sc.Language = "VBScript"
println sc.Eval("1 + 1").value
