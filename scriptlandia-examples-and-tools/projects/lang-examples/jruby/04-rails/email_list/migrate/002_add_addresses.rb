class AddAddresses < ActiveRecord::Migration
  def self.up
    create_table "addresses" do |table|
      table.column "address", :string, :limit => 255
      table.column "city", :string, :limit => 255 
      table.column "state", :string, :limit => 2
      table.column "zip", :string, :limit => 10
      table.column "person_id", :integer
    end
    Person.find_all.each do |person|
      person.address = Address.new
      person.address.address = "Not yet initialized"
      person.save
    end
  end

  def self.down
    drop_table "addresses"
  end
end
