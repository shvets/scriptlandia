class Pet < ActiveRecord::Base  
  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birthDate

  #validate_format_of :sex, 
  #  :with => "",
  #  :message : "Should be from: M, F"

  # validates_length_of :title, :within => 1..20
  # validates_uniqueness_of :title, :message => "already exists"

end
