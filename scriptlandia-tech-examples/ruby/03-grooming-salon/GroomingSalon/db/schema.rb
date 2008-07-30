# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 9) do

  create_table "appointments", :force => true do |t|
    t.datetime "appointmentDate"
    t.float    "price"
    t.integer  "pet_owner_id",    :limit => 11
    t.integer  "groomer_id",      :limit => 11
    t.integer  "pet_id",          :limit => 11
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "companies", :force => true do |t|
    t.string   "name"
    t.string   "address"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "groomers", :force => true do |t|
    t.string   "firstName"
    t.string   "lastName"
    t.text     "notes"
    t.integer  "company_id", :limit => 11
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "pet_owners", :force => true do |t|
    t.string   "firstName"
    t.string   "lastName"
    t.string   "homePhone"
    t.string   "workPhone"
    t.string   "cellPhone"
    t.string   "salutation"
    t.integer  "company_id", :limit => 11
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "pets", :force => true do |t|
    t.string   "subtype"
    t.string   "name"
    t.string   "sex"
    t.string   "breed"
    t.string   "color"
    t.string   "size"
    t.datetime "birthDate"
    t.string   "veterinar"
    t.string   "referredBy"
    t.text     "medicalProblems"
    t.text     "specialInstructions"
    t.text     "behavior"
    t.string   "clip1"
    t.string   "clip2"
    t.string   "clip3"
    t.integer  "pet_owner_id",        :limit => 11
    t.string   "alive"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "reports", :force => true do |t|
    t.string   "name"
    t.string   "description"
    t.string   "table"
    t.string   "condition"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", :force => true do |t|
    t.string   "username"
    t.boolean  "admin"
    t.string   "password_salt"
    t.string   "password_hash"
    t.string   "email"
    t.integer  "company_id",    :limit => 11
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "cookie_hash"
  end

end
