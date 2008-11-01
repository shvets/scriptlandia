# java_launcher.rb

require 'yaml'
require 'rubygems'
require 'rjb'

s_config = YAML::load File.open(File.dirname(__FILE__) + '/config/scriptlandia_config.yaml')

script_name = ARGV[0]

extension = script_name[script_name.rindex('.')+1, script_name.length]

config = YAML::load File.open(File.dirname(__FILE__) + '/config/' + extension + '_launcher_config.yaml')
#config = YAML::load File.open(File.dirname(__FILE__) + '/config/groovy_launcher_config.yaml')

ENV['JAVA_HOME'] = s_config['java_home']
local_repository = s_config['repositories']['local']

jvm_args = config['jvmargs']
jvm_args = [] if jvm_args == nil

classpath = config['classpath']
classpath = [] if classpath == nil

config['artifacts'].each do |name, artifact|
  group, id, type,version = artifact.split(':')
  
  file_name = local_repository + '/' + group.gsub('.', '/') + '/' + id.gsub('.', '/') + '/' + version + '/' + id.gsub('.', '/') + '-' + version + '.' + type

  unless File.exist? file_name
    puts 'File ' + file_name + ' does not exists.'
  else
    classpath << file_name
  end
end

Rjb::load(classpath.join(File::PATH_SEPARATOR), jvmargs = jvm_args)       

Rjb::import(config['start_class']).main(ARGV)
