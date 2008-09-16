class PetOwner {
  static hasMany = [pet:Pet]

  static constraints = {
    firstName(blank:false, maxSize:25)
    lastName(blank:false, maxSize:25)
    salutation()

    homePhone(maxSize:15)
    workPhone(maxSize:15)
    cellPhone(maxSize:15)
  }

  String firstName
  String lastName

  String homePhone
  String workPhone
  String cellPhone

  String salutation

  String toString(){
    "${salutation} ${firstName} ${lastName}"
  }

}
