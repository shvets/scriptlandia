class Pet < ActiveRecord::Base  
  belongs_to :pet_owner


  validates_presence_of :breed
  #validate_format_of :sex, 
  #  :with => "",
  #  :message : "Should be from: M, F"
end
