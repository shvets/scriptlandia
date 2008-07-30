# home_controller.rb

class HomeController < ApplicationController
  def index
  end

  def login
    if request.post?
      controller = session['thispage']
      if controller = "login"
        controller = "home"
      end

      @user = User.find_by_username(params[:login])

      if @user and @user.password_is? params[:password]
        session[:user] = @user.id
        session[:uid] = @user.id
        redirect_to :controller => controller, :action => 'index'
      else 
         @auth_error = 'Wrong username or password'
      end
    end
  end

  def logout
    puts "logging out..."
    puts session[:user].to_s
    reset_session
    session[:user] = nil
    puts session[:user].to_s

    session[:uid] = nil
    flash[:notice] = 'You\'re logged out'

    respond_to do |format|
      format.html { redirect_to :controller => 'home', :action => 'index' }
      format.xml  { head :ok }
    end
  end

end
       