class Appointment < ActiveRecord::Base
  validates_presence_of :appointmentDate, :price
end
