class CreateCompanies < ActiveRecord::Migration
  def self.up
    create_table :companies do |t|
      t.string :name
      t.string :address

      t.timestamps
    end

    Company.create :name => "Goochie Pooch", :address => "Rt. 18"

  end

  def self.down
    drop_table :companies
  end
end
