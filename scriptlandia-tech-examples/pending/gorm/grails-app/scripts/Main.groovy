Book b = new Book();
b.title = "The Bible"
b.author = "God"
println "Adding book:"+b

b = new Book();
b.title = "The Dark Tower: The Gunslinger"
b.author = "Stephen King"
println "Adding book:"+b
b.save();

b = new Book();
b.title = "Accounting For Dummies, 3rd Edition"
b.author = "John A. Tracy"
println "Adding book:"+b
b.save();

b = new Book();
b.title = "It"
b.author = "Stephen King"
println "Adding book:"+b
b.save();

Car c = new Car();
c.make = "Hyundai"
c.model = "Tiburon"
c.year = "2006"
c.vin = "SOMELONGVINNUMBER"
println "Adding car:"+c
c.save();

c = new Car();
c.make = "Dodge"
c.model = "Neon"
c.year = "2001"
c.vin = "SOMEOTHERLONGVINNUMBER"
println "Adding car:"+c
c.save();

Person p = new Person();
p.firstName = "Jeremie"
p.lastName = "Weldin"
p.dateOfBirth = new Date()
println "Adding person:"+p
p.save()

p = new Person();
p.firstName = "John"
p.lastName = "Doe"
p.dateOfBirth = new Date() - 450
println "Adding person:"+p
p.save()

println "\nAll of the books:"
def bookList = Book.list()
bookList.each{println "\t"+it}

println "\nAll of the cars:"
def carList = Car.list()
carList.each{println "\t"+it}

println "\nAll of the people:"
def personList = Person.list()
personList.each{println "\t"+it}

println "\nAll of the books by Stephen King:"
bookList = Book.findAllByAuthor("Stephen King")
bookList.each{println "\t"+it}

println "\nAll of the books ordered by name:"
bookList = Book.listOrderByTitle()
bookList.each{println "\t"+it}