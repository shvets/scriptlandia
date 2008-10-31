# java_launcher.rb

require 'yaml'
require 'rubygems'
require 'rjb'

config = YAML::load File.open(File.dirname(__FILE__) + '/java_launcher.config')

ENV['JAVA_HOME'] = config['java_home']
local_repository = config['local_repository']

jvm_args = config['jvmargs']
jvm_args = [] if jvm_args == nil

classpath = config['classpath']
classpath = [] if classpath == nil

config['dependencies'].each do |dependency|
  group, id, type,version = dependency.split(':')
  
  file_name = local_repository + '/' + group + '/' + id + '/' + version + '/' + id + '-' + version + '.' + type

  unless File.exist? file_name
    puts 'File ' + file_name + ' does not exists.'
  else
    classpath << file_name
  end
end

Rjb::load(classpath.join(File::PATH_SEPARATOR), jvmargs = jvm_args)       

Rjb::import(config['start_class']).main(ARGV)
