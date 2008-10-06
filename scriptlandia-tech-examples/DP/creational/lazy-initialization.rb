# lazy-initialization.rb

# Lazy initialization is the tactic of delaying the creation of an object, 
# the calculation of a value, or some other expensive process until 
# the first time it is needed.

class Item
  def self.item
    if (@instance == nil)
      puts "Creating new instance"
    else
      puts "Using existing instance"
    end

    instance
  end

  def self.instance
    @instance || (@instance = Item.new)
  end

end

# test

item1 = Item.item

item2 = Item.item

item3 = Item.item
