class AddFieldsToAppointments < ActiveRecord::Migration
  def self.up
    add_column :appointments, :price, :float
    add_column :appointments, :pet_owner_id, :int
  end

  def self.down
    remove_column :appointments, :price
    remove_column :appointments, :pet_owner_id
  end
end
