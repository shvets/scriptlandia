class Book { 
   String isbn
   String title
   String author
   Float price
   String format
 
   static constraints = {
       isbn(maxLength:20, unique:true)
       title(maxLength:50)
       author(maxLength:50)
       price(min:0F, max:999F, scale:2)
       format(inList:["Hardcover", "Paperback", "e-Book"])
   }
}
