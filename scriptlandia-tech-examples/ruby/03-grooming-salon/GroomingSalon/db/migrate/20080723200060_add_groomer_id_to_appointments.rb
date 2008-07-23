class AddGroomerIdToAppointments < ActiveRecord::Migration
  def self.up
    add_column :appointments, :groomer_id, :int
  end

  def self.down
    remove_column :appointments, :groomer_id
  end
end
