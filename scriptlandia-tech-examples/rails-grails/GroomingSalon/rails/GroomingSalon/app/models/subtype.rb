class Subtype
  attr_accessor :id, :name
  
  def initialize(id, name) 
    @id = id
    @name = name
  end  
    
  def self.list
    [Subtype.new('1', 'cat'), Subtype.new('2','dog')]
  end
  
end