//

import org.codehaus.groovy.scriptom.ActiveXProxy
import java.io.File

def word = new ActiveXProxy("Word.Application")

word.Documents.Open(new File(args[0]).canonicalPath)
word.ActiveDocument.SaveAs(new File(args[0] - ".doc" + ".html").canonicalPath, 8)
word.Quit()