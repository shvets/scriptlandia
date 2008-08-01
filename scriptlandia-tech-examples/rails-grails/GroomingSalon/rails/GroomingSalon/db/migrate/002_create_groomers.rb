class CreateGroomers < ActiveRecord::Migration
  def self.up
    create_table :groomers do |t|
      t.string :firstName
      t.string :lastName
      t.text :notes

      t.integer :company_id 

      t.timestamps
    end
  end

  def self.down
    drop_table :groomers
  end
end
