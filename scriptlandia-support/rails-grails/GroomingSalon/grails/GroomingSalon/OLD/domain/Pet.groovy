class Pet {
  String veterinar

  String referredBy

  String medicalProblems

  String breed

  String size

  String name

  String sex

  String color

  Date birthDate

  String clip1
  String clip2
  String clip3

  String specialInstructions

  String behavior

  PetOwner owner

  static constraints = {
    name(blank:false)
    sex(inList: ["male", "female"])
    breed()
    color()
    owner()
    birthDate()
    size()
    behavior()

    veterinar()
    referredBy()
    medicalProblems()

    clip1()
    clip2()
    clip3()

    specialInstructions()
  }

  String toString(){
    "${name} ( breed: ${breed}; sex: ${sex}; color: ${color})"
  }

}
