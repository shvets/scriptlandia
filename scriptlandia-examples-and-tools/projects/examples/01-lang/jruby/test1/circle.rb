require "formulas.rb"

class Circle
	include Formulas

	attr_accessor :radius

	def initialize (r)
		@radius=r
	end
	
	def area()
		@radius **2
	end

	def circumference ()
		PI * @radius * 2
	end	
	
	def + (anotherCircle)
		totalArea = area() + anotherRectangle.area()
		Cirlce.new(Math.sqrt(totalArea/PI))
	end
	
	private	#start making private methods
	
	def grow (radiusMultiple)
		@radius = @radius * radiusMultiple
		return "New area: " + area().to_s
	end
	
	public #back to public methods again
	
	def doubleSize ()
		grow(2)
	end
end
