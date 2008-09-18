require "rectangle.rb"
require "square.rb"
require "circle.rb"

r1 = Rectangle.new(3,4)
print("Rectangle 1's area is: " + r1.area().to_s + "\n");
print("Rectangle 1's circumference is: " + r1.circumference().to_s + "\n\n");
r2 = Rectangle.new(5,7)
print("Rectangle 2's area is: " + r2.area().to_s + "\n");
print("Rectangle 2's circumference is: " + r2.circumference().to_s + "\n\n");
print("Creating Rectangle 3 from rectangles 1 and 2\n");
r3 = r1 + r2;
print("Rectangle 3's area is: " + r3.area().to_s + "\n");
print("Rectangle 3's circumference is: " + r3.circumference().to_s + "\n\n");
print("Doubling the size of rectangle 1\n");
r1.doubleSize();
print("Rectangle 1's area is now: " + r1.area().to_s + "\n");
print("Rectangle 1's circumference is now: " + r1.circumference().to_s + "\n\n");
s1 = Square.new(6);
print("Square 1's area is: " + s1.area().to_s + "\n");
print("Square 1's circumference is: " + s1.circumference().to_s + "\n\n");
print("Doubling the size of square 1\n");
s1.doubleSize();
print("Square 1's area is now: " + s1.area().to_s + "\n");
print("Square 1's circumference is now: " + s1.circumference().to_s + "\n\n");
print("Creating Rectangle 4 from rectangle 1 and square 1\n");
r4 = r1 + s1;
print("Rectangle 4's area is: " + r4.area().to_s + "\n");
print("Rectangle 4's circumference is: " + r4.circumference().to_s + "\n\n");
c1 = Circle.new(7);
print("Circle 1's area is: " + c1.area().to_s + "\n");
print("Cirlce 1's circumference is: " + c1.circumference().to_s + "\n\n");
print("Circle 1 bigger than Rectangle 3?\n");
print(c1 > r3);
print("\nCircle 1 bigger than Square 1 + Rectangle 3?\n");
print(c1 > (s1 + r3));








