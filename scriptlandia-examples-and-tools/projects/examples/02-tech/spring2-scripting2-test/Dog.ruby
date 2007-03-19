# Dog.rb

require "java"

include_class("Animal")

class Dog < Animal
  def makeSound
    puts "Bark!!!"
  end
end

Dog.new
