class Groomer < ActiveRecord::Base
  has_many :appointments

  validates_presence_of :firstName, :lastName

  def name
    lastName + " " + firstName
  end

end
