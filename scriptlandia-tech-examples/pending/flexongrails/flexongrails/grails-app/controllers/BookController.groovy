class BookController {

  def scaffold = Book
/*
  def index = { redirect(action:list, params:params) }
 
  // the delete, save and update actions only accept POST requests
  // def allowedMethods = [delete:'POST', save:'POST', update:'POST']
 
    def list = {
        response.setHeader("Cache-Control", "no-store")
        def bookList = Book.list(params)
        render(contentType:"text/xml") {
            data {
                for(i in bookList) {
                    book {
//                        id(i.id)
                        isbn(i.isbn)
                        title(i.title)
                        author(i.author)
                        price(i.price)
                        format(i.format)
                    }
                }
            }
        }

    }
 
    def save = {
        def book
        if(params.id) {
            book = Book.get(params.id)
        }
        else {
         book = new Book()
        }
        book.properties = params
        book.save()
        render ""
    }
 
    def delete = {
        def book = Book.get(params.id)
        if(book) {
            book.delete()
        }
        render ""
    }
*/
}

