# command.rb

# Encapsulate a request as an object, thereby letting you parametrize
# clients with different requests, queue or log requests, and support
# undoable operations.

class Command
  def execute()
  end
end

class MyCommand1 < Command
  def execute
    puts "command1"
  end
end

class MyCommand2 < Command
  def execute
     puts "command2"
  end
end

class MyCommand3 < Command
  def execute
     puts "command3"
  end
end

# test

commands = [ MyCommand1.new, MyCommand2.new, MyCommand3.new, MyCommand2.new, MyCommand1.new ]

commands.each {|command| command.execute}

puts "-------"

class Test
  def operation(command=nil)
    if block_given?
      yield
    else
      command.execute if command != nil
    end
  end
end

test = Test.new

test.operation(MyCommand1.new)

test.operation {
  puts "hello!"
}
