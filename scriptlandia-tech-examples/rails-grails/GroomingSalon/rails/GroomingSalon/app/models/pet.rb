#

class Pet < ActiveRecord::Base
  include PetBreeds

  @SEX_TYPES = ["male", "female"]

  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birth_date

  validates_inclusion_of :sex, :in => @SEX_TYPES 

  def self.get_breeds subtype
    if subtype == nil
      %w()
    elsif subtype == "dog"
      @@DOG_BREEDS
    elsif subtype == "cat"
      #@@CAT_BREEDS
      breeds = []
      File.open("app/models/dog_breeds.txt").each_line { |line| breeds << line unless line.empty? or line.chomp.empty? }
      breeds
    end
  end

  def to_s
    "Pet { name: #{name}; subtype: #{subtype}; sex: #{sex}; breed: #{breed}; color: #{color}; size: #{size}; owner: #{pet_owner.name if pet_owner != nil} }"
  end
end
