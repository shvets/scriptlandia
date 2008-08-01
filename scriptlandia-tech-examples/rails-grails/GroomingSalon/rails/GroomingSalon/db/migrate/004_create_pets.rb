class CreatePets < ActiveRecord::Migration
  def self.up
    create_table :pets do |t|
      t.string :subtype
      t.string :name
      t.string :sex
      t.string :breed
      t.string :color
      t.string :size
      t.datetime :birthDate

      t.string :veterinar
      t.string :referredBy

      t.text :medicalProblems
      t.text :specialInstructions
      t.text :behavior

      t.string :clip1
      t.string :clip2
      t.string :clip3

      t.integer :pet_owner_id
      t.string :alive

      t.timestamps
    end
  end

  def self.down
    drop_table :pets
  end
end
