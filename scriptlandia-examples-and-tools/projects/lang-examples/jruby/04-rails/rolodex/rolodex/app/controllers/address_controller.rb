class AddressController < ApplicationController
  scaffold :address
  
  def list
    @addresses = Address.find_all
  end
  
  def new
    @address = Address.new
    @contact = Contact.find(@params['id'])
  end
  
  def create
    @address = Address.new(@params['address'])
    if @address.save
      @contact = Contact.find(@params['contact_id'])
      @contact.address_id = @address.id
      @contact.save
    end
    redirect_to :controller => 'contact', :action => 'list'
  end
  
  def show
    @contact = Contact.find(@params['contactid'])  
    @address = Address.find(@params['id'])  
  end
  
  def edit
    @contact = Contact.find(@params['contactid'])  
    @address = Address.find(@params['id'])
  end
  
  def destroy
    @address = Address.find(@params['id'])
    @address.destroy
    redirect_to :controller => 'contact', :action => 'list'
  end
end
