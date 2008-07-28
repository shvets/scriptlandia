class Groomer < ActiveRecord::Base
  belongs_to :company

  has_many :appointments

  validates_presence_of :firstName, :lastName

  def name
    lastName + " " + firstName
  end

end
