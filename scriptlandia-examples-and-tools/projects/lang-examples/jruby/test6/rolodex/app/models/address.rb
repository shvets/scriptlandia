class Address < ActiveRecord::Base
  has_many :contacts
end
