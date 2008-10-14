# visitor.bsh

# Represents an operation to be performed on the elements of an object structure.
# Lets you define a new operation without changing the classes of the elements on which
# it operates.

# 1. type and visitor interfaces

class Visitable
  def accept(visitor)
  end
end

class Visitor
  def visit(visitable)
  end
end

# 2. type implementation woth visitable behavior

# basic parts

class MyVisitable1 < Visitable
  def accept(visitor)
    visitor.visit(self)
  end
end

class MyVisitable2 < Visitable
  def accept(visitor)
    visitor.visit(self)
  end
end

class MyVisitable3 < Visitable
  def accept(visitor)
    visitor.visit(self)
  end
end

# compound

class MyCompoundVisitable < Visitable
  def initialize
    @visitable1 = MyVisitable1.new
    @visitable2 = MyVisitable2.new

    @visitables3 = [
      MyVisitable3.new, MyVisitable3.new, MyVisitable3.new
    ]
  end;

  def accept(visitor)
    visitor.visit(self)

    # takes care of components
    @visitable1.accept(visitor)
    @visitable2.accept(visitor);

    @visitables3.each { |visitable| visitable.accept(visitor) }
  end     
end

# 3. visitor implementations

class MyVisitor1 < Visitor
  def visit(visitable)
    if(visitable.kind_of? MyVisitable1)
      puts "visitor1: visiting my visitable 1"
    elsif(visitable.kind_of? MyVisitable2)
      puts "visitor1: visiting my visible 2"
    elsif(visitable.kind_of? MyVisitable3)
      puts "visitor1: visiting my visitable 3"
    elsif(visitable.kind_of? MyCompoundVisitable)
      puts "visitor1: visiting my compound visitable"
    end
  end
end

class MyVisitor2 < Visitor
  def visit(visitable)
    if(visitable.kind_of? MyVisitable1)
      puts "visitor2: visiting my visitable 1"
    elsif(visitable.kind_of? MyVisitable2)
      puts "visitor2: visiting my visible 2"
    elsif(visitable.kind_of? MyVisitable3)
      puts "visitor2: visiting my visitable 3"
    elsif(visitable.kind_of? MyCompoundVisitable)
      puts "visitor2: visiting my compound visitable"
    end
  end
end

# 4. test

visitable = MyCompoundVisitable.new

visitor1 = MyVisitor1.new
visitor2 = MyVisitor2.new

visitable.accept(visitor1)
visitable.accept(visitor2)
