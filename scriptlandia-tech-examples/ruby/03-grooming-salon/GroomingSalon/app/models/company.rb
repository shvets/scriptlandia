class Company < ActiveRecord::Base
  has_many :groomers

  has_one :user

end
