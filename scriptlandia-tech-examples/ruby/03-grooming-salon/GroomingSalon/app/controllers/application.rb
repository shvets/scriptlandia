# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  #model :user

  helper :all # include all helpers, all the time

  layout "grooming-salon-layout"

  before_filter :store_user_in_session

  # See ActionController::RequestForgeryProtection for details
  # Uncomment the :secret if you're not using the cookie session store
  protect_from_forgery # :secret => 'f599262b5622ea5038a3a07071187bd0'
  
  # See ActionController::Base for details 
  # Uncomment this to filter the contents of submitted sensitive data parameters
  # from your application log (in this case, all fields with names like "password"). 
  # filter_parameter_logging :password

  def current_user
    User.find(session[:user])
  end

  protected 

  def store_user_in_session
    session[:user] = authenticate().id
  end

  def authenticate()
    authenticate_or_request_with_http_basic do |user_name, password|
      User.find(:first, :conditions => ["name =?", user_name])
    end
   end
end
