module AppointmentsHelper
  include ActionView::Helpers::DateHelper, ActionView::Helpers::FormOptionsHelper
  
  def display_filter_value_field2 filter_id = nil
    text = ''

    filter_id = params[:filter_id] if filter_id == nil and params != nil

    if filter_id != nil
      if filter_id == 'PETOWNER'
        pet_owners = PetOwner.find_by_current_user User.current_user(session)

        text = collection_select(:filter, :value, pet_owners, :id, :name)  
      elsif filter_id == "DATE"
        text = date_select(:filter, :value)
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
