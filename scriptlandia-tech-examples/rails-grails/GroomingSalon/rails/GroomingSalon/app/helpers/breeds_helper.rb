module BreedsHelper

  def display_filter_value_field1 filter_id = nil
    puts "1234"

    text = ''

    filter_id = params[:filter_id] if filter_id == nil and params != nil

    if filter_id != nil
      if filter_id == 'TYPE'
        subtypes = %w(cat dog)

        text = '<select id="filter_value" name="filter_value">'
                   
        for subtype in subtypes
          text = text + '  <option value="' + subtype + '">' + subtype + '</option>'
        end

        text = text + '</select>'
      else
        text = ''
      end
    end

    render :text => text

    text
  end

end
