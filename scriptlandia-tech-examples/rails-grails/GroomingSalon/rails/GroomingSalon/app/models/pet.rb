class Pet < ActiveRecord::Base  
  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birth_date

  def to_s
    "Pet { name: #{name}; subtype: #{subtype}; sex: #{sex}; breed: #{breed}; color: #{color}; size: #{size}; owner: #{pet_owner.name if pet_owner != nil} }"
  end
end
