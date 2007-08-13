# Dog.rb

require "java"

include_class("Animal")

class Dog < Animal
  def setName(name)
    @@name = name
  end

  def getName
    @@name
  end

  def makeSound
    puts getName() + ": Bark!!!"
  end
end

Dog.new
