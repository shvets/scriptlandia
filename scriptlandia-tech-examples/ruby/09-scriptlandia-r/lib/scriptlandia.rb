# scriptlandia.rb

require 'yaml'
require 'rubygems'
require 'rjb'

module Scriptlandia
  class Launcher
    def initialize
      @s_config = YAML::load File.open(File.dirname(__FILE__) + '/config/scriptlandia_config.yaml')
      @ext_config = YAML::load File.open(File.dirname(__FILE__) + '/config/extension_mapping.yaml')
    end

    def self.language_folder ext_config, ext
      ext_config.each do |folder, extensions|
        if extensions.include?(ext)
          return folder
        end
      end
    end

    def launch
      script_name = ARGV[0]

      extension = script_name[script_name.rindex('.')+1, script_name.length]
                                                                                                                       
      config = YAML::load File.open(File.dirname(__FILE__) + '/config/' + Launcher.language_folder(@ext_config, extension) + '/config.yaml')
      #config = YAML::load File.open(File.dirname(__FILE__) + '/config/groovy_launcher_config.yaml')

      ENV['JAVA_HOME'] = @s_config['java_home']
      local_repository = @s_config['repositories']['local']

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

      ARGV[0, 0] = config['command_line'] if config['command_line']

      Rjb::import(config['start_class']).main(ARGV)
    end
  end
end
