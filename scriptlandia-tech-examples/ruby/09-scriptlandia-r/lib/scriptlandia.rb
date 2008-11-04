# scriptlandia.rb

require 'yaml'
require 'rubygems'
require 'rjb'

module Scriptlandia
  class Launcher
    def initialize
      @settings = YAML::load File.open(File.dirname(__FILE__) + '/settings.yaml')
      @ext_mapping = YAML::load File.open(File.dirname(__FILE__) + '/languages/extension_mapping.yaml')
    end

    def self.language_folder ext_mapping, ext
      language_folder = nil

      ext_mapping.each do |folder, extensions|
        if extensions.include?(ext)
          language_folder = folder
          break
        end
      end

      language_folder
    end

    def launch
      script_name = ARGV[0]

      extension = script_name[script_name.rindex('.')+1, script_name.length]
                          
      language = Launcher.language_folder(@ext_mapping, extension)
      
      if(language == nil) 
        puts "Unsupported language/extension: " + extension
      else
        lang_config = YAML::load File.open(File.dirname(__FILE__) + '/languages/' + 
                             language + '/config.yaml')

        ENV['JAVA_HOME'] = @settings['java_home']
        local_repository = @settings['repositories']['local']

        jvm_args = lang_config['jvmargs']
        jvm_args = [] if jvm_args == nil

        classpath = lang_config['classpath']
        classpath = [] if classpath == nil

        lang_config['artifacts'].each do |name, artifact|
          group, id, type,version = artifact.split(':')
          
          file_name = local_repository + '/' + 
                      group.gsub('.', '/') + '/' + 
                      id.gsub('.', '/') + '/' + 
                      version + '/' + 
                      id.gsub('.', '/') + '-' + version + '.' + type

          unless File.exist? file_name
            puts 'File ' + file_name + ' does not exists.'
          else
            classpath << file_name
          end
        end

        Rjb::load(classpath.join(File::PATH_SEPARATOR), jvmargs = jvm_args)       

        ARGV[0, 0] = lang_config['command_line'] if lang_config['command_line']

        Rjb::import(lang_config['start_class']).main(ARGV)
      end
    end
  end
end
