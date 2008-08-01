class Appointment < ActiveRecord::Base
  belongs_to :pet_owner
  belongs_to :groomer
  belongs_to :pet

  validates_presence_of :appointmentDate, :price
 
  validates_numericality_of :price, :message=>"should be a number"

  def to_s
    "Appointment { owner: #{pet_owner.name if pet_owner != nil}; pet: ${pet.name if pet != nil}; appointmentDate: #{appointmentDate}; price: #{price} }"
  end
end
