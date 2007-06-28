//

require 'java'

require "c:/maven-repository/com/lowagie/itext/1.3.1/itext-1.3.1.jar"

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


