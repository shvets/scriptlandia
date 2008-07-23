class AddPetOwnerIdToPets < ActiveRecord::Migration
  def self.up
    add_column :pets, :pet_owner_id, :int
  end

  def self.down
    remove_column :pets, :pet_owner_id
  end
end
