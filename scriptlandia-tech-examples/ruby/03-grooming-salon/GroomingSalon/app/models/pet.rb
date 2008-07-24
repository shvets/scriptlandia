class Pet < ActiveRecord::Base  
  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birthDate

  #validate_format_of :sex, 
  #  :with => "",
  #  :message : "Should be from: M, F"

end
