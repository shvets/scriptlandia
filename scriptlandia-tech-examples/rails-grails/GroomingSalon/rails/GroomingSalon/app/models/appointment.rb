#

class Appointment < ActiveRecord::Base
#  belongs_to :pet_owner
  belongs_to :groomer
  belongs_to :pet

  validates_presence_of :appointment_date, :price
 
  validates_numericality_of :price, :message=>"should be a number"

  def self.find_by_current_user current_user, current_date, params
    pet_owner_ids = []
  
    if current_user != nil
      if current_user.admin
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
    end

    if pet_owner_ids.size() == 0
      conditions = {}
    else
      pet_ids = Pet.find(:all, :conditions => [ "pet_owner_id in(?)", pet_owner_ids])

      conditions = { :pet_id => pet_ids }
    end

    if current_date != nil
      conditions[:appointment_date] = current_date
    end

    find(:all, :conditions => conditions)
  end

  def to_s
    "Appointment { owner: #{pet_owner.name if pet_owner != nil}; pet: ${pet.name if pet != nil}; appointment_date: #{appointment_date}; price: #{price} }"
  end
end
