#
class ApplicationController < ActionController::Base

        # Store the current URL so we can redirect back to it if necessary
        before_filter :store_locations


        # Store where we are
        def store_locations
                if @session['prevpage'] && @session['thispage'] != @request.request_uri
                        @session['prevpage'] = @session['thispage'] || ''
                        @session['thispage'] = @request.request_uri
                end
        end

        # Make sure the user is authorized. If not, make them log in.
        def authorize
                unless session[:user_id]
                        redirect_to :signin_url
                        return false
                end
                @user = User.find(session[:user_id])
        end

        # Make sure the user is authorized as an administrator. If not, make them log in.
        def authorize_as_admin
                unless session[:user_id] && session[:admin] == 1
                        redirect_to :signin_url
                        return false
                end
                @user = User.find(session[:user_id])
        end

        # Authorize the user if possible, but don't force it.
        def authorize_if_possible
                if session[:user_id]
                        @user = User.find(session[:user_id])
                else
                        @user = User.new
                        @notloggedin = true
                end
        end

        # redirect_back
        # If a previous page is stored in the session, go back to it.. otherwise go back to a default page

        def redirect_back(default)
                if @session['prevpage'].nil?
                        if default
                                redirect_to default
                        else
                                redirect_to :controller => "", :action => ""
                        end
                else
                        if @session['prevpage'].length > 4
                                redirect_to_url @session['prevpage']
                        else
                                redirect_to default
                        end
                end
        end

end