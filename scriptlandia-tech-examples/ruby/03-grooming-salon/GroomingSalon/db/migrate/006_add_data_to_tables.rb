class AddDataToTables < ActiveRecord::Migration
  def self.up
    Company.create :name => "Goochie Pooch", :address => "Rt. 18" # 1
    Company.create :name => "Paws", :address => "Rt. 33"          # 2

    Groomer.create :firstName => "Inna", :lastName => "Shvets"    # 1
    Groomer.create :firstName => "Carrol", :lastName => "Pollak"  # 2

    PetOwner.create :firstName => "Inna", :lastName => "Shvets"   # 1
    PetOwner.create :firstName => "Lisa", :lastName => "Ribansky" # 2

    Pet.create :type => "cat", :name => "Cheeta", :sex => "female", :breed => "siameze", :color => "white", :pet_owner_id => 1
    Pet.create :type => "cat", :name => "Sheila", :sex => "female", :breed => "persian", :color => "gray", :pet_owner_id => 1
  end

  def self.down
    Company.delete :id => 1
    Company.delete :id => 2

    Groomer.delete :id => 1
    Groomer.delete :id => 2

    PetOwner.delete :id => 1
    PetOwner.delete :id => 2

    Pet.delete :id => 1
    Pet.delete :id => 2
  end
end
