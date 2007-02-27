class BankAccount
	@@interestRate = 6.5

		def BankAccount.getInterestRate()
			@@interestRate
		end

	attr_accessor :balance	
	def initialize (bal)
		@balance = bal
	end
end
