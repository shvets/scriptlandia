module Formulas
	ACCELERATION = 9.8
	LIGHTSPEED = 299792458
	PI=3.141592654

	def energy (mass)
		mass*(LIGHTSPEED**2)
	end

	def force (mass)
		mass*ACCELERATION
	end
	
	def > (otherShape)
		self.area() > otherShape.area()
	end	
end
