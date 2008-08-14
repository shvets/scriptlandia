#

class Pet < ActiveRecord::Base
  include PetBreeds

  SEX_TYPES = [
    ["male", "m"], ["female", "f"]
  ]

  belongs_to :pet_owner
  has_many :appointments

  validates_presence_of :name, :breed, :sex, :size, :color, :birth_date

  validates_inclusion_of :sex, :in => SEX_TYPES.map { |disp, *| disp}
  
  def self.get_breeds subtype
    Breed.find(:all, :conditions => (subtype == nil) ? [] :  [ "subtype=?", subtype])
  end

  def self.find_by_current_user current_user, params
    if current_user.admin
      pet_owner_ids = []

      if params[:pet_owner_id] != nil
        pet_owner_ids << params[:pet_owner_id]
      end
    else
      if current_user.company.id
        pet_owner_ids = PetOwner.find(:all, :conditions => ["company_id=?", current_user.company.id]).collect() { |x| x.id }
      else
        pet_owner_ids = []

        if params[:pet_owner_id] != nil
          pet_owner_ids << params[:pet_owner_id]
        end
      end
    end

    if pet_owner_ids.size() == 0
      conditions = {}
    else
      conditions = { :pet_owner_id => pet_owner_ids }
    end

    find(:all, :conditions => conditions )
  end
  
  def to_s
    "Pet { name: #{name}; subtype: #{subtype}; sex: #{sex}; breed: #{breed}; color: #{color}; size: #{size}; owner: #{pet_owner.name if pet_owner != nil} }"
  end
end
