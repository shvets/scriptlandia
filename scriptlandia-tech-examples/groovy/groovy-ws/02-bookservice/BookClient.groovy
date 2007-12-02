import groovyx.net.ws.WSClient

def proxy = new WSClient("http://localhost:6981/BookService?wsdl", this.class.classLoader)

def books =  proxy.getBooks()

for (book in books) println book

def book = proxy.create("defaultnamespace.Book")

book.title = "Groovy in Action"
book.author = "Dierk"
book.isbn = "123"

proxy.addBook(book)

def bks =  proxy.getBooks()

println bks.books[0].isbn

//def aob = proxy.getBooks()
//for (book in aob.books) println book.name
