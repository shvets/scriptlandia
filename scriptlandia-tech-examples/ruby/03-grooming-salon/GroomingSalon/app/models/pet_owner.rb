class PetOwner < ActiveRecord::Base
  has_many :pets
end
