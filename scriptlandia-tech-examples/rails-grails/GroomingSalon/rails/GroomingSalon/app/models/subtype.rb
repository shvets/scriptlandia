class Subtype
  attr_accessor :id, :name
 
  def initialize(id, name) 
    @id = id
    @name = name
  end  

  def self.load
    @@SUBTYPES = [Subtype.new('1', 'cat'), Subtype.new('2','dog')]
  end
  
  def self.list
    @@SUBTYPES
  end
  
  def self.find_name id
    @@SUBTYPES.find {|subtype| subtype.id == id}
    #return (id == '1') ? 'cat' : 'dog'
  end
end