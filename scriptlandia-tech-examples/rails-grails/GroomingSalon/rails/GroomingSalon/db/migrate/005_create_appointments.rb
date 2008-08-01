class CreateAppointments < ActiveRecord::Migration
  def self.up
    create_table :appointments do |t|
      t.datetime :appointmentDate
      t.float :price
    
      t.integer :pet_owner_id
      t.integer :groomer_id
      t.integer :pet_id

      t.timestamps
    end
  end

  def self.down
    drop_table :appointments
  end
end
