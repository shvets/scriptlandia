class ContactController < ApplicationController
  scaffold :contact
  
  def new
    @contact = Contact.new
    @contact_types = ContactType.find_all
  end
  
  def create
    @contact = Contact.new(@params['contact'])
    if @contact.save
      redirect_to :action => 'list'
    else
      render_action 'new'
    end
  end
  
  def edit
    @contact = Contact.find(@params['id'])
    @contact_types = ContactType.find_all
  end
  
  def list
    @contacts = Contact.find_all
  end
  
  def show
    @contact = Contact.find(@params['id'])
  end 
  
end
