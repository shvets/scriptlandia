# pet_owners_controller.rb

class PetOwnersController < ProtectedController
  # GET /pet_owners
  # GET /pet_owners.xml
  def index
    current_user = User.create.current_user(session)

    if current_user.admin
      conditions = []
    else
      conditions = [ "company_id=?", current_user.company_id ]
    end

    @pet_owners = PetOwner.find(:all, :conditions => conditions)

    if @pet_owners.empty?
      flash[:notice] = 'We don\'t have any pet owner.'
    end

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @pet_owners }
    end
  end

  # GET /pet_owners/1
  # GET /pet_owners/1.xml
  def show
    @pet_owner = PetOwner.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @pet_owner }
    end
  end

  # GET /pet_owners/new
  # GET /pet_owners/new.xml
  def new
    @pet_owner = PetOwner.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @pet_owner }
    end
  end

  # GET /pet_owners/1/edit
  def edit
    @pet_owner = PetOwner.find(params[:id])
  end

  # POST /pet_owners
  # POST /pet_owners.xml
  def create
    @pet_owner = PetOwner.new(params[:pet_owner])
     @pet_owner.company_id = User.create.current_user(session).company_id

    respond_to do |format|
      if @pet_owner.save
        flash[:notice] = 'Pet owner was successfully created.'
        format.html { redirect_to(pet_owners_url) }
        format.xml  { render :xml => @pet_owner, :status => :created, :location => @pet_owner }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @pet_owner.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /pet_owners/1
  # PUT /pet_owners/1.xml
  def update
    @pet_owner = PetOwner.find(params[:id])

    respond_to do |format|
      if @pet_owner.update_attributes(params[:pet_owner])
        flash[:notice] = 'Pet owner was successfully updated.'
        format.html { redirect_to(pet_owners_url) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @pet_owner.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /pet_owners/1
  # DELETE /pet_owners/1.xml
  def destroy
    @pet_owner = PetOwner.find(params[:id])
    @pet_owner.destroy

    respond_to do |format|
      format.html { redirect_to(pet_owners_url) }
      format.xml  { head :ok }
    end
  end
end
