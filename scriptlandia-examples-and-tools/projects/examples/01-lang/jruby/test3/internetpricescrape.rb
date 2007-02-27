require 'net/http'
require 'uri'

class InternetPriceScrape
	# HTML line used to label the price quote display.
	# Looking for this line allows the search engine to locate the stock price
	@@TAGLINEBEGIN="<td class=\"yfnc_tabledata1\"><big><b>"
	# HTML line used at the end of the price quote display.
	# Looking for this line also allows the search engine to locate the stock price
	@@TAGLINEEND="</b></big></td>"
	@@TAGEXPBEGIN=Regexp.new(@@TAGLINEBEGIN)
	@@TAGEXPEND=Regexp.new(@@TAGLINEEND)
	
	#Financial web site URL and parameter to pass in the stock symbol to the financial web site.
	@@FINANCEURL=URI.parse('http://finance.yahoo.com')
	@@SITEPARAM='/q?s='
	
	attr_accessor :symbol
	
	def getPrice(searchSym)
		#
		# get the stock price from the financial web site with the use of private methods
		#
		@symbol=searchSym
		page=getPricePage
		return scrapePrice(page).to_f
	end

	private 
	
	def getPricePage()
		#
		# get the HTML page (in the form of a string) for the symbol from the financial web site
		#
		page = @@SITEPARAM + @symbol
    res = Net::HTTP.start(@@FINANCEURL.host, @@FINANCEURL.port) {|http|
      http.get(@@SITEPARAM+@symbol)
    }
    return res.body.to_s
  end
  
	def scrapePrice(page)
		#
		# scrape the price from the HTML page or return -1 indicating it was not found
		#
		startingLoc=@@TAGEXPBEGIN=~page
		endLoc=@@TAGEXPEND=~page
		if (!startingLoc.nil?) & (!endLoc.nil?) && (endLoc > startingLoc)
			return page[(startingLoc+@@TAGLINEBEGIN.length),(endLoc-(startingLoc+@@TAGLINEBEGIN.length))]
		else
			return -1
		end
	end
 
end