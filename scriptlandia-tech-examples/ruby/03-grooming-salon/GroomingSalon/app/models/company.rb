class Company < ActiveRecord::Base
  has_many :groomers
  has_many :pet_owners

  has_one :user

end
