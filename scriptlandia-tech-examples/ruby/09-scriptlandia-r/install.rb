#
require 'rubygems'
require 'rbconfig'
require 'find'
require 'ftools'
require 'rake/gempackagetask'

include Config

def prepare
  $bindir = CONFIG["bindir"]
  $libdir = CONFIG["libdir"]
  $ruby = CONFIG['ruby_install_name']

  spec = Gem::Specification.load('scriptlandia-r.gemspec')

  $my_libdir = File.join(CONFIG["libdir"], 'ruby', 'gems', 
                         CONFIG["MAJOR"]+"."+CONFIG["MINOR"], 
                         'gems', spec.name+'-'+spec.version.to_s, 'lib') 
end

def tmp_file
  tmp_dir = nil
  for t in [".", "/tmp", "c:/temp", $bindir]
    stat = File.stat(t) rescue next
    if stat.directory? and stat.writable?
      tmp_dir = t
      break
    end
  end

  fail "Cannot find a temporary directory" unless tmp_dir

  File.join(tmp_dir, "_tmp")
end

def install_file from_file, to_file
  File::install(from_file, File.join(to_file), 0755, true)
end

def install_file_with_header(from_file, to_file)
  tmp = tmp_file()

  File.open(from_file) do |ip|
    File.open(tmp, "w") do |op|
      ruby = File.join($bindir, $ruby)
      op.puts "#!#{ruby} -w"
      op.write ip.read
    end
  end

  File::install(tmp, to_file, 0755, true)
  File::unlink(tmp)
end

def install_settings from_file, to_file
  require 'yaml'

  orig_settings_file_name = $my_libdir + "/settings.yaml"
  
  if(File.exist? orig_settings_file_name)
    settings = YAML::load File.open(orig_settings_file_name)
  else
    settings = YAML::load File.open(File.dirname(__FILE__) + '/lib/settings.yaml')
  end

  orig_java_home = settings['java_home'].chomp
  orig_repository_home = settings['repositories']['local'].chomp

  puts 'Enter Java Home (' + orig_java_home + "):"

  java_home = gets

  if(java_home.chomp.empty?)
    java_home = orig_java_home
  end

  puts 'Enter Repository Home (' + orig_repository_home + "):"

  repository_home = gets

  if(repository_home.chomp.empty?)
    repository_home = orig_repository_home
  end

  settings = YAML::load File.open(File.dirname(__FILE__) + '/lib/settings.yaml')
  settings['java_home'] = java_home
  settings['repositories']['local'] = repository_home

  File.open( to_file, 'w' ) do |out|
    YAML.dump(settings, out)
  end
end

prepare

install_file("bin/sl.bat", $bindir + "/sl.bat")

install_file_with_header("bin/sl", $bindir + "/sl")

install_settings("lib/settings.yaml", $my_libdir + "/settings.yaml")
