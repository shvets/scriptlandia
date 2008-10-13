# interpreter.rb

# Given a language, defines a representation for it's grammar along with an interpreter
# that uses the representation to interpret sentences in the language

# 1. context

class NamesInterpreterContext
  def initialize
    @names = []

    @names << "monitor"
    @names << "keyboard"
    @names << "mouse"
    @names << "system-block"
  end

  def names
    @names
  end
end

class NamesInterpreter
  def initialize(context)
    @context = context;
  end
   
  # expression syntax:
  # show names
  def interpret(expression)
    result = ''

    words = expression.chomp.scan(/\w[-\w']+/) # extract each word

    i = 0
    while i <= words.size do
      word = words[i]

      if(word != nil)
        puts "word: " + word

        if (word == 'show')
          word = words[i+1]
          i = i + 1

          if (word == 'names')
            result = result + @context.names.join(', ')
          else
            result = result + "error!"
          end
        else
          result = result + "error!"
        end
      end

      i = i + 1
    end

    result
  end
end

# test

interpreter = NamesInterpreter.new(NamesInterpreterContext.new);

puts "interpreting show names: " + interpreter.interpret("show names")
