# jrubyVersion.rb

require "java"

module JavaLang
  include_package "java.lang"
end


puts "Jruby version: " + JavaLang::System.getProperty("jruby.version")
