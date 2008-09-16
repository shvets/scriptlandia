            
class PetOwnerController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ petOwnerList: PetOwner.list( params ) ]
    }

    def show = {
        def petOwner = PetOwner.get( params.id )

        if(!petOwner) {
            flash.message = "PetOwner not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ petOwner : petOwner ] }
    }

    def delete = {
        def petOwner = PetOwner.get( params.id )
        if(petOwner) {
            petOwner.delete()
            flash.message = "PetOwner ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "PetOwner not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def petOwner = PetOwner.get( params.id )

        if(!petOwner) {
            flash.message = "PetOwner not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ petOwner : petOwner ]
        }
    }

    def update = {
        def petOwner = PetOwner.get( params.id )
        if(petOwner) {
            petOwner.properties = params
            if(!petOwner.hasErrors() && petOwner.save()) {
                flash.message = "PetOwner ${params.id} updated"
                redirect(action:show,id:petOwner.id)
            }
            else {
                render(view:'edit',model:[petOwner:petOwner])
            }
        }
        else {
            flash.message = "PetOwner not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def petOwner = new PetOwner()
        petOwner.properties = params
        return ['petOwner':petOwner]
    }

    def save = {
        def petOwner = new PetOwner(params)
        if(!petOwner.hasErrors() && petOwner.save()) {
            flash.message = "PetOwner ${petOwner.id} created"
            redirect(action:show,id:petOwner.id)
        }
        else {
            render(view:'create',model:[petOwner:petOwner])
        }
    }
}