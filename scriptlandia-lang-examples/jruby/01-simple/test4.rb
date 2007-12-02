#

require "java"

module JavaLang
   include_package "java.lang"
end

include_class("java.util.HashMap") { |pkg, name| "J" + name }

s = JavaLang::StringBuffer.new("Hello Java World")

puts s.append(", I love JRuby")

m = JHashMap.new()

m.put("java", "Java")
m.put("ruby", "Ruby")
m.put("jruby", "JRuby")

puts m

m.keySet().each { |key| puts m.get(key) }
