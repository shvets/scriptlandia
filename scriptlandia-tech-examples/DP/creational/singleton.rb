# singleton.rb

# Ensure a class only has one instance and provide a global point of access to it.

# Note: try to avoid singleton (see singleton-corrected.rb). 
# Major question: how to test/mock the singleton?

class MySingleton
  def self.instance
    @instance || (@instance = MySingleton.new)

    @name = Time.now.to_s
  end
end

puts "singleton: " + MySingleton.instance.to_s

puts "singleton: " + MySingleton.instance.to_s