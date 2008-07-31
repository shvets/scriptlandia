# pets_controller.rb

class PetsController < ProtectedController
  # GET /pets
  # GET /pets.xml
  def index
    current_user = User.create.current_user(session)

    if current_user.admin
      pet_owner_ids = []

      if params[:pet_owner_id] != nil
        pet_owner_ids << params[:pet_owner_id]
      end
    else
      if current_user.company.id
        pet_owner_ids = PetOwner.find(:all, :conditions => ["company_id=?", current_user.company.id]).collect() { |x| x.id }
      else
        pet_owner_ids = []

        if params[:pet_owner_id] != nil
          pet_owner_ids << params[:pet_owner_id]
        end
      end
    end

    if pet_owner_ids.size() == 0
      conditions = {}
    else
      conditions = { :pet_owner_id => pet_owner_ids }
    end

    @pets = Pet.find(:all, :conditions => conditions )

    if @pets.empty?
      flash[:notice] = 'We don\'t have any pet.'
    end

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @pets }
    end
  end

  # GET /pets/1
  # GET /pets/1.xml
  def show
    @pet = Pet.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @pet }
    end
  end

  # GET /pets/new
  # GET /pets/new.xml
  def new
    @pet = Pet.new(:pet_owner_id => params[:pet_owner_id])

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @pet }
    end
  end

  # GET /pets/1/edit
  def edit
    @pet = Pet.find(params[:id])
  end

  # POST /pets
  # POST /pets.xml
  def create
    @pet = Pet.new(params[:pet])

    respond_to do |format|
      if @pet.save
        flash[:notice] = 'Pet was successfully created.'
        format.html { redirect_to(pets_url) }
        format.xml  { render :xml => @pet, :status => :created, :location => @pet }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @pet.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /pets/1
  # PUT /pets/1.xml
  def update
    @pet = Pet.find(params[:id])

    respond_to do |format|
      if @pet.update_attributes(params[:pet])
        flash[:notice] = 'Pet was successfully updated.'
        format.html { redirect_to(pets_url) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @pet.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /pets/1
  # DELETE /pets/1.xml
  def destroy
    @pet = Pet.find(params[:id])
    @pet.destroy

    respond_to do |format|
      format.html { redirect_to(pets_url) }
      format.xml  { head :ok }
    end
  end

end
