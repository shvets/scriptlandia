# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ProtectedController < ApplicationController
  # Store the current URL so we can redirect back to it if necessary
  before_filter :store_locations, :check_auth

  private

  # Store where we are
  def store_locations
    puts "1. request.request_uri " + request.request_uri.to_s
    puts "1.1.  " + session['prevpage'].to_s
    puts "1.2. " + session['thispage'].to_s

    session['prevpage'] = session['thispage']
    session['thispage'] = request.request_uri

    #if session['prevpage'] != nil && session['thispage'] != nil && 
    #   session['prevpage'] && session['thispage'] != request.request_uri
     # puts "1.3"
      #session['prevpage'] = session['thispage'] || ''
      #session['thispage'] = request.request_uri
    #end
  end

  def check_auth
    puts "2."
    unless session[:uid]
      flash[:error] = 'You need to be logged in to access this panel'
      redirect_to :controller => 'home', :action => 'login'
    end
  end

end
