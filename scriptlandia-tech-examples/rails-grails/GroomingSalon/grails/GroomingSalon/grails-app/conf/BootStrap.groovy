class BootStrap {

  def init = {servletContext ->
    // Create some test data
    new Company(name: "Goochie Pooch", address: "Rt. 18").save()
    new Company(name: "Paws", address: "Rt. 33").save()

    new User(email: "eg@eg.com", password: "password").save()
  }

  def destroy = {
  }

} 