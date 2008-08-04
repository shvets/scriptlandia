            
class PetController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ petList: Pet.list( params ) ]
    }

    def show = {
        def pet = Pet.get( params.id )

        if(!pet) {
            flash.message = "Pet not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ pet : pet ] }
    }

    def delete = {
        def pet = Pet.get( params.id )
        if(pet) {
            pet.delete()
            flash.message = "Pet ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Pet not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def pet = Pet.get( params.id )

        if(!pet) {
            flash.message = "Pet not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ pet : pet ]
        }
    }

    def update = {
        def pet = Pet.get( params.id )
        if(pet) {
            pet.properties = params
            if(!pet.hasErrors() && pet.save()) {
                flash.message = "Pet ${params.id} updated"
                redirect(action:show,id:pet.id)
            }
            else {
                render(view:'edit',model:[pet:pet])
            }
        }
        else {
            flash.message = "Pet not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def pet = new Pet()
        pet.properties = params
        return ['pet':pet]
    }

    def save = {
        def pet = new Pet(params)
        if(!pet.hasErrors() && pet.save()) {
            flash.message = "Pet ${pet.id} created"
            redirect(action:show,id:pet.id)
        }
        else {
            render(view:'create',model:[pet:pet])
        }
    }
}