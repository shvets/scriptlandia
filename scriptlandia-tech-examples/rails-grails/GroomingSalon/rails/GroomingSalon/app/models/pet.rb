#

class Pet < ActiveRecord::Base
  include PetBreeds

  SEX_TYPES = [
    ["male", "m"], ["female", "f"]
  ]

  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birth_date

  validates_inclusion_of :sex, :in => SEX_TYPES.map { |disp, value| disp}
  
  def self.get_breeds subtype
    Breed.find(:all, :conditions => (subtype == nil) ? [] :  [ "subtype=?", subtype])
  end

  def to_s
    "Pet { name: #{name}; subtype: #{subtype}; sex: #{sex}; breed: #{breed}; color: #{color}; size: #{size}; owner: #{pet_owner.name if pet_owner != nil} }"
  end
end
