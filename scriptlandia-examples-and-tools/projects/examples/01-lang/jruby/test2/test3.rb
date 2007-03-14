require "java"
module JavaLang
   include_package "java.lang"
end

s = JavaLang::String.new("Hello World from JRuby!")
puts s
