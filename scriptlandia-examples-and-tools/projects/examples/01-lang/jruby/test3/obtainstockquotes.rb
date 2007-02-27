require 'stock'
require 'internetpricescrape'

class ObtainStockQuotes
	@@HOLDINGS_FILE="holdings.txt"
	attr_accessor :stocks
	
	def initialize
		@stocks=[]
	end
	
	def readStockQuotes
		#This method
		#	1. calls to opens the stock holdings file and retrieve the stock symbols (and shares owned)
		# which is returned in an array.
		# 2. calls to create a new Stock object for each of the stock symbols
		# 3. pushes the Stock object on a stocks array.
		#
		holdingSymbols = retrieveSymbols
		holdingSymbols.each {|holding| @stocks.push(createStock(holding))}
	end
	
	def obtainPrices
		# This method does the following...
		#	for each Stock object in the stocks array
		#		fetch the stock price from the Web
		#		store the price in the stock object
		#
		@stocks.each {|stock| stock.price=lookupPrice(stock.symbol)}
	end
	
	def reportStockPrices
		# This method does the following...
		#	for each Stock object in the stocks array
		#		print the stock price, shares and total value to the screen
		#
		total = 0.0
		@stocks.each {|stock| 
			puts stock
			if (stock.price > 0)
				total+= (stock.price * stock.shares)
			end
		}
		puts "Your portfolio is today worth:  $#{total}.\n" 
	end
	
	private
		
	def retrieveSymbols
		#
		#	the holdings text file contains a set of symbol/shares owned pairs.
		#	this method reads the pairs from the file and converts each pair
		#	into an array which it returns in another array called holdingSymbols.
		#	
		holdingSymbols=[]
		begin
			holdingsFile = File.new(@@HOLDINGS_FILE, "r")
			holdingInfo=holdingsFile.readlines
			holdingInfo.each{|line| holdingSymbols.push(line.split(pattern="\s"))}
		rescue
			puts "Error - problem trying to open the holdings.txt file containing the stock symbols of interest.\n"
		ensure
			holdingsFile.close
			return holdingSymbols
		end
	end
	
	def createStock(symInfo)
		#
		# This method uses the Stock class to create a new Stock object
		# It is passed an array containing two strings - one the symbol and the other
		# the number of shares of the stock owned
		# 
		return Stock.new(symInfo[0],symInfo[1].to_i)
	end
	
	def lookupPrice(sym)
		scraper = InternetPriceScrape.new
		return scraper.getPrice(sym)
	end
end