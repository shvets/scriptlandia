# iterator.bsh

# Provides a way to access the elements of an aggregate object sequentially without exposing 
# its underlying representation.

class Iterator
  def initialize(array)
    @array = array
    @index = 0
  end

  def has_next?
    @index < @array.length
  end

  def next
    value = @array[@index]

    @index += 1

    value
  end

  def current_element
    @array[@index]
  end
end

# test

array = ['e1', 'e2', 'e3', 'e4']

it = Iterator.new(array)

while it.has_next?
  puts it.next.to_s
end

