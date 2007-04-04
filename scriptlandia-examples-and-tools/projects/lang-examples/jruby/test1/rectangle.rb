require "formulas.rb"

class Rectangle
	include Formulas
	
	attr_accessor :height, :width
	
	def initialize (hgt, wdth)
		@height = hgt
		@width = wdth
	end

	def area ()
		@height*@width
	end
	
	def circumference ()
		@height * 2 + @width * 2
	end
	
	def + (anotherRectangle)
		totalArea = area() + anotherRectangle.area()
		Rectangle.new(@height,totalArea/@height)
	end
	
	private	#start making private methods
	
	def grow (heightMultiple, widthMultiple)
		@height = @height * heightMultiple
		@width = @width * widthMultiple
		return "New area: " + area().to_s
	end
	
	public #back to public methods again
	
	def doubleSize ()
		grow(2,2)
	end
	
end
