module GroomersHelper
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

end
