class AddDataToTables < ActiveRecord::Migration
  def self.up
    User.create :username => "admin", :admin => true, :password => 'admin'
    User.create :username => "gp", :admin => false, :password => 'gp'
    User.create :username => "paws", :admin => false, :password => 'paws'

    Company.create :name => "Goochie Pooch", :address => "Rt. 18" # 1
    Company.create :name => "Paws", :address => "Rt. 33"          # 2

    Groomer.create :firstName => "Inna", :lastName => "Shvets"    # 1
    Groomer.create :firstName => "Carrol", :lastName => "Pollak"  # 2

    PetOwner.create :firstName => "Inna", :lastName => "Shvets", :homePhone => "0"   # 1
    PetOwner.create :firstName => "Lisa", :lastName => "Ribansky", :homePhone => "0" # 2

    Pet.create :subtype => "cat", :name => "Cheeta", :sex => "female", :breed => "siameze",
      :color => "white", :size => 10, :birthDate => '12-12-2008',
      :veterinar => "v1", :referredBy => 'rb1', :alive => 'yes'

    Pet.create :subtype => "cat", :name => "Sheila", :sex => "female", :breed => "persian",
      :color => "gray", :size => 5, :birthDate => '12-12-2008',
      :veterinar => "v1", :referredBy => 'rb1', :alive => 'yes'
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
