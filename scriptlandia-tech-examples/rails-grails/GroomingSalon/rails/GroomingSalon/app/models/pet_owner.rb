# == Schema Information
# Schema version: 12
#
# Table name: pet_owners
#
#  id         :integer(11)     not null, primary key
#  first_name :string(255)     
#  last_name  :string(255)     
#  home_phone :string(255)     
#  work_phone :string(255)     
#  cell_phone :string(255)     
#  salutation :string(255)     
#  company_id :integer(11)     
#  created_at :datetime        
#  updated_at :datetime        
#

#

class PetOwner < ActiveRecord::Base
  belongs_to :company

  has_many :pets
  has_many :appointments, :through => :pets

  validates_presence_of :first_name, :last_name, :home_phone

  def name
    last_name + " " + first_name
  end

  def self.find_by_current_user current_user
    if current_user.admin
      conditions = []
    else
      conditions = [ "company_id=?", current_user.company_id ]
    end

    find(:all, :conditions => conditions )
  end

  def to_s
    "PetOwner { name: #{name}; company: #{company.name if company != nil} }"
  end
end
