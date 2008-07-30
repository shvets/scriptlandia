# groomers_controller.rb

class GroomersController < ProtectedController
  # GET /groomers
  # GET /groomers.xml
  def index
    if User.create.current_user(session).admin
      conditions = []
    else
      conditions = [ "company_id=?", User.create.current_user(session).company_id ]
    end

    @groomers = Groomer.find(:all, :conditions => conditions)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @groomers }
    end
  end

  # GET /groomers/1
  # GET /groomers/1.xml
  def show
    @groomer = Groomer.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @groomer }
    end
  end

  # GET /groomers/new
  # GET /groomers/new.xml
  def new
    @groomer = Groomer.new(:company_id => User.create.current_user(session).company_id)

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @groomer }
    end
  end

  # GET /groomers/1/edit
  def edit
    @groomer = Groomer.find(params[:id])
  end

  # POST /groomers
  # POST /groomers.xml
  def create
    @groomer = Groomer.new(params[:groomer])
    puts "? " + params[:company_id].to_s

    @groomer.company_id = User.create.current_user(session).company_id if User.create.current_user(session).company_id

    respond_to do |format|
      if @groomer.save
        flash[:notice] = 'Groomer was successfully created.'
        format.html { redirect_to(groomers_url) }
        format.xml  { render :xml => @groomer, :status => :created, :location => @groomer }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @groomer.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /groomers/1
  # PUT /groomers/1.xml
  def update
    @groomer = Groomer.find(params[:id])

    respond_to do |format|
      if @groomer.update_attributes(params[:groomer])
        flash[:notice] = 'Groomer was successfully updated.'
        format.html { redirect_to(groomers_url) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @groomer.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /groomers/1
  # DELETE /groomers/1.xml
  def destroy
    @groomer = Groomer.find(params[:id])
    @groomer.destroy

    respond_to do |format|
      format.html { redirect_to(groomers_url) }
      format.xml  { head :ok }
    end
  end
end