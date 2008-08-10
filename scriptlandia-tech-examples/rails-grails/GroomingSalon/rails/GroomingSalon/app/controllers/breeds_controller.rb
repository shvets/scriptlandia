#

class BreedsController < ProtectedController
  include BreedsHelper

  # GET /breeds
  # GET /breeds.xml
  def index
    filter_value = params[:filter_value]

    if filter_value != nil
      params[:subtype] = filter_value
      conditions = [ "subtype = ?", filter_value]
    else
      conditions = []
    end

    @breeds = Breed.find(:all, :conditions => conditions)

    if @breeds.empty?
      flash[:notice] = 'We don\'t have any breed.'
    end

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @breeds }
    end
  end

  # GET /breeds/1
  # GET /breeds/1.xml
  def show
    @breed = Breed.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @breed }
    end
  end

  # GET /breeds/new
  # GET /breeds/new.xml
  def new
    @breed = Breed.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @breed }
    end
  end

  # GET /breeds/1/edit
  def edit
    @breed = Breed.find(params[:id])
  end

  # POST /breeds
  # POST /breeds.xml
  def create
    @breed = Breed.new(params[:breed])

    respond_to do |format|
      if @breed.save
        flash[:notice] = 'Breed was successfully created.'
        format.html { redirect_to(@breed) }
        format.xml  { render :xml => @breed, :status => :created, :location => @breed }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @breed.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /breeds/1
  # PUT /breeds/1.xml
  def update
    @breed = Breed.find(params[:id])

    respond_to do |format|
      if @breed.update_attributes(params[:breed])
        flash[:notice] = 'Breed was successfully updated.'
        format.html { redirect_to(@breed) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @breed.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /breeds/1
  # DELETE /breeds/1.xml
  def destroy
    @breed = Breed.find(params[:id])
    @breed.destroy

    respond_to do |format|
      format.html { redirect_to(breeds_url) }
      format.xml  { head :ok }
    end
  end

end