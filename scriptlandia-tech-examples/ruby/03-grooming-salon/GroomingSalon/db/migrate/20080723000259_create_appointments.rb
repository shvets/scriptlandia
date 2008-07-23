class CreateAppointments < ActiveRecord::Migration
  def self.up
    create_table :appointments do |t|
      t.datetime :appointmentDate

      t.timestamps
    end
  end

  def self.down
    drop_table :appointments
  end
end
