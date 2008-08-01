class Company < ActiveRecord::Base
  has_many :groomers
  has_many :pet_owners

  has_one :user

  def to_s
    "Company { name: #{name}; address: #{address} }"
  end
end
