# appointments_controller.rb

class AppointmentsController < ProtectedController
  #include AppointmentsHelper
  include ActionView::Helpers::DateHelper, ActionView::Helpers::FormOptionsHelper 

  finder_filter :appointment, :only => [:show, :update, :destroy]
 

  # GET /appointments
  # GET /appointments.xml
  def index
    @appointments = Appointment.find_by_current_user User.current_user(session), nil, params

    if @appointments.empty?
      flash[:notice] = 'We don\'t have any appointment.'
    end

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @appointments }
    end
  end

  # GET /appointments/1
  # GET /appointments/1.xml
  def show
    #@appointment = Appointment.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @appointment }
    end
  end

  # GET /appointments/new
  # GET /appointments/new.xml
  def new
    reset_flash_messages

    @appointment = Appointment.new(:pet_owner_id => params[:pet_owner_id], :pet_id => params[:pet_id], 
      :appointment_date => Date.today, :appointment_time => Time.now )
                                                                                                                     
    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @appointment }
    end
  end

  # GET /appointments/1/edit
  def edit
    @appointment = super(params[:id], "index") { Appointment.find(params[:id]) }
  end

  # POST /appointments
  # POST /appointments.xml
  def create
    @appointment = Appointment.new(params[:appointment])

    respond_to do |format|
      if @appointment.save
        flash[:notice] = 'Appointment was successfully created.'
        format.html { redirect_to(appointments_url) }
        format.xml  { render :xml => @appointment, :status => :created, :location => @appointment }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @appointment.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /appointments/1
  # PUT /appointments/1.xml
  def update
    #@appointment = Appointment.find(params[:id])

    respond_to do |format|
      if @appointment.update_attributes(params[:appointment])
        flash[:notice] = 'Appointment was successfully updated.'
        format.html { redirect_to(appointments_url) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @appointment.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /appointments/1
  # DELETE /appointments/1.xml
  def destroy
    #@appointment = Appointment.find(params[:id])
    @appointment.destroy

    respond_to do |format|
      format.html { redirect_to(appointments_url) }
      format.xml  { head :ok }
    end
  end

  def display_filter_value_field filter_id = nil
    text = ''

    filter_id = params[:filter_id] if filter_id == nil and params != nil

    if filter_id != nil
      if filter_id == 'PETOWNER'
        pet_owners = PetOwner.find_by_current_user User.current_user(session)
        
        text = display_collection_select pet_owners, pet_owners[0].id
      elsif filter_id == "DATE"
        text = display_date
      else
        text = ''
      end
    end

    render :text => text

    #text
  end

end
