class HomesController < ApplicationController
  def index
    #@current_user = current_user()
  end

  def logout
    puts session[:user].to_s
    reset_session
    #session[:user] = nil
    puts session[:user].to_s

    respond_to do |format|
      format.html { redirect_to :controller => 'homes', :action => 'index' }
      format.xml  { head :ok }
    end
  end
end
