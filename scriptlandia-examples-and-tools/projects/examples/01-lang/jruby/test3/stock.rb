class Stock
	attr_accessor :symbol, :shares, :price
	
	def initialize (sym, sh)
		@symbol = sym
		@shares = sh.to_i
		if @shares < 0
			@shares = 0
		end
	end
	
	def to_s
		if price > 0
		  "Today's price for #{@symbol} is $#{@price} and you own #{@shares} share(s).\n" 
		else
			"The price for #{@symbol} could not be obtained.  Please check the symbol in your holdings file.\n"
		end
	end 


end