class CreatePets < ActiveRecord::Migration
  def self.up
    create_table :pets do |t|
      t.string :veterinar
      t.string :referredBy
      t.text :medicalProblems
      t.string :breed
      t.string :size
      t.string :name
      t.string :sex
      t.string :color
      t.datetime :birthDate
      t.string :clip1
      t.string :clip2
      t.string :clip3
      t.text :specialInstructions
      t.text :behavior
      t.string :petOwner

      t.timestamps
    end
  end

  def self.down
    drop_table :pets
  end
end
