class PetOwner < ActiveRecord::Base
  has_many :pets
  has_many :appointments

  validates_presence_of :firstName, :lastName, :homePhone

  def name
    lastName + " " + firstName
  end
end
