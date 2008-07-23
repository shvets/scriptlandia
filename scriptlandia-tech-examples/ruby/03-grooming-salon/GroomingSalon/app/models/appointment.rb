class Appointment < ActiveRecord::Base
  belongs_to :pet_owner
  belongs_to :groomer
  belongs_to :pet

  validates_presence_of :appointmentDate, :price
end
