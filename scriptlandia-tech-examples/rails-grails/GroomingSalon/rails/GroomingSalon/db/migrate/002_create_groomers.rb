class CreateGroomers < ActiveRecord::Migration
  def self.up
    create_table :groomers do |t|
      t.string :first_name
      t.string :last_name
      t.text :notes

      t.integer :company_id 

      t.timestamps
    end
  end

  def self.down
    drop_table :groomers
  end
end
