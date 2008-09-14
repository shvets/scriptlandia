#

class ValidatingFormBuilder < ActionView::Helpers::FormBuilder
  
  helpers = field_helpers +
           %w(text_field_with_auto_complete, date_select datetime_select time_select, :select, collection_select) -
           %w(hidden_field label fields_for)
           

  def self.create_tagged_field name
    define_method(name) do |field, *args|
      options = args.last.is_a?(Hash) ? args.pop : {}

      if %w(text_field password_field).include?(name) && required_field?(field)
        options[:onblur] = "checkPresence('#{field_name(field)}')"
      end

      label_options = (required_field?(field)) ? {:style => 'color:red'} : {}
     
      @template.content_tag(:p,
                            label(field, label_text(field), label_options) + ": " +
                            super(field, options))    
    end
  end
  
  helpers.each do |name|
    create_tagged_field name
  end
  
  #def select (object, method, choices, options = {}, html_options = {})
  #  ValidatingFormBuilder.create_tagged_field "subtype"
  #end

private
  def field_name(field)
   "#{@object_name.to_s.underscore}_#{field.to_s.underscore}"
  end

  def label_text(field)
    "#{field.to_s.humanize}"
  end

#  def required_mark(field)
#    required_field?(field) ? '*' : ''
#  end

  def required_field?(field)
    @object_name.to_s.camelize.constantize.
                 reflect_on_validations_for(field).
                 map(&:macro).include?(:validates_presence_of)
  end
end

