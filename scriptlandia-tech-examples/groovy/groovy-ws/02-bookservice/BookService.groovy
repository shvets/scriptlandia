class BookService {

  private List allBooks = new ArrayList()

  Book findBook(String isbn) {
    for (book in allBooks) {
      if (book.isbn == isbn) return book
    }
    return null
  }

  void addBook(Book book) {
    allBooks.add(book)
  }

  Book[] getBooks() {
    return (Book[])allBooks.toArray(new Book[allBooks.size()])
  }

}
