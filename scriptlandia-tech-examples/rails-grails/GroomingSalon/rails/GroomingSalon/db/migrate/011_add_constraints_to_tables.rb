#

class AddConstraintsToTables < ActiveRecord::Migration
  def self.up
    execute "alter table users add constraint fk_user_companies foreign key (company_id) references companies(id)"

    execute "alter table groomers add constraint fk_groomer_companies foreign key (company_id) references companies(id)"

    execute "alter table pet_owners add constraint fk_pet_owner_companies foreign key (company_id) references companies(id)"

    execute "alter table pets add constraint fk_pet_pet_owners foreign key (pet_owner_id) references pet_owners(id)"

    #execute "alter table appointments add constraint fk_appointment_pet_owners foreign key (pet_owner_id) references pet_owners(id)"
    execute "alter table appointments add constraint fk_appointment_pets foreign key (pet_id) references pets(id)"
    execute "alter table appointments add constraint fk_appointment_groomers foreign key (groomer_id) references groomers(id)"
  end

  def self.down
    execute "alter table users drop constraint fk_user_companies"

    execute "alter table groomers drop constraint fk_groomer_companies"

    execute "alter table pet_owners drop constraint fk_pet_owner_companies"

    execute "alter table pets drop constraint fk_pet_pet_owners"

    #execute "alter table appointments drop constraint fk_appointment_pet_owners"
    execute "alter table appointments drop constraint fk_appointment_pets"
    execute "alter table appointments drop constraint fk_appointment_groomers"
  end
end
