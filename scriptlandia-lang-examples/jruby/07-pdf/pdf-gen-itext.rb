//

require 'java'

require "c:/Work/maven-repository/com/lowagie/itext/2.0.4/itext-2.0.4.jar"

include_class "java.io.FileOutputStream"
include_class "java.io.IOException"
include_class "com.lowagie.text.pdf.PdfWriter"
include_class "com.lowagie.text.Document"
include_class "com.lowagie.text.Paragraph"

document=Document.new()
PdfWriter.getInstance(document,FileOutputStream.new("HelloWorld.pdf"))
document.open()

document.add(Paragraph.new("Hello World"))
document.close

