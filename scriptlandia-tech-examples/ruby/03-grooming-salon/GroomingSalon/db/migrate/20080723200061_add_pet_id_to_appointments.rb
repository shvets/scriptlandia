class AddPetIdToAppointments < ActiveRecord::Migration
  def self.up
    add_column :appointments, :pet_id, :int
  end

  def self.down
    remove_column :appointments, :pet_id
  end
end
