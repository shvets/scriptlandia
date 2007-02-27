require 'java'

include_class 'java.lang.System'

require 'obtainstockquotes'

quoteService=ObtainStockQuotes.new
quoteService.readStockQuotes
quoteService.obtainPrices
quoteService.reportStockPrices