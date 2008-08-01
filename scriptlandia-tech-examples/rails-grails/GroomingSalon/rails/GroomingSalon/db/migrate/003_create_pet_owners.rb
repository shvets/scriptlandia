class CreatePetOwners < ActiveRecord::Migration
  def self.up
    create_table :pet_owners do |t|
      t.string :firstName
      t.string :lastName
      t.string :homePhone
      t.string :workPhone
      t.string :cellPhone
      t.string :salutation

      t.integer :company_id

      t.timestamps
    end
  end

  def self.down
    drop_table :pet_owners
  end
end
