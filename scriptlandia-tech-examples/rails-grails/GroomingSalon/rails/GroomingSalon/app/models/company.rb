class Company < ActiveRecord::Base
  has_many :groomers
  has_many :pet_owners

  has_one :user

  validates_presence_of :name

  def to_s
    "Company { name: #{name}; address: #{address} }"
  end
end
