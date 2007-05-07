class Person < ActiveRecord::Base
  has_one :address
  validates_presence_of :email
end