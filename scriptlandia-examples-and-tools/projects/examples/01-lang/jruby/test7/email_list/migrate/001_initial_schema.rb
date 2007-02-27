class InitialSchema < ActiveRecord::Migration
  def self.up
    create_table "people" do |table|
      table.column "first_name", :string, :limit => 255 
      table.column "last_name", :string, :limit => 255 
      table.column "email", :string, :limit => 255 
    end
  end
  def self.down
    drop_table "people"
  end
end
