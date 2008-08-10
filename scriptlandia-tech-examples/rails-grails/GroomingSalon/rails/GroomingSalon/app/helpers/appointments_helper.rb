module AppointmentsHelper
  def display_filter_value_field2 filter_id = nil
    text = ''

    puts "1filter_id: " + filter_id.to_s

    filter_id = params[:filter_id] if filter_id == nil and params != nil

    puts "2filter_id: " + filter_id.to_s

    if filter_id != nil
      if filter_id == 'PETOWNER'
        pet_owners = PetOwner.find_by_current_user User.current_user(session)

        text = '<select id="filter_value" name="filter_value">'
        
        for pet_owner in pet_owners
          text = text + '  <option value="' + pet_owner.id.to_s + '">' + pet_owner.name + '</option>'
        end

        text = text + '</select>'
      elsif filter_id == "DATE"
        text = '<input id="filter_value" name="filter_value" value="" type="text"/>'
      else
        text = ''
      end
    end

    render :text => text

    text
  end

  def display_pets_select
    pets = Pet.find(:all, :conditions => [ "pet_owner_id=?", params[:pet_owner_id] ])

    text = '<select id="appointment_pet_id" name="appointment[pet_id]">'
    
    for pet in pets
      text = text + '  <option value="' + pet.id.to_s + '">' + pet.name + '</option>'
    end

    text = text + '</select>'

    render :text => text
  end

  def effects
    @cart = "bbbbb"
    redirect_to appointments_url unless request.xhr?
  end

end
