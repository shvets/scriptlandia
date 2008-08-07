class Groomer < ActiveRecord::Base
  belongs_to :company

  has_many :appointments

  validates_presence_of :first_name, :last_name

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
    "Groomer { name: #{name}; company: #{company.name if company != nil} }"
  end
end
