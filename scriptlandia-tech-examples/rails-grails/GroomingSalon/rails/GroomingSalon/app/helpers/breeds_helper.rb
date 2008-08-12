module BreedsHelper
  include ActionView::Helpers::FormOptionsHelper

  def display_filter_value_field filter_id = nil
    text = ''

    filter_id = params[:filter_id] if filter_id == nil and params != nil

    if filter_id != nil
      if filter_id == 'COMPANY'
        companies = Company.find(:all)

        text = collection_select(:filter, :value, companies, :id, :name)  
      else
        text = ''
      end
    end

    render :text => text

    text
  end

end
