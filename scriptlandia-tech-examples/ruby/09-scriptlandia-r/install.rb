#

require 'rbconfig'
require 'find'
require 'ftools'

include Config

def prepare
  $bindir = CONFIG["bindir"]
  $ruby = CONFIG['ruby_install_name']

  $realbindir = $bindir

  $sitedir = CONFIG["sitelibdir"]
  unless $sitedir
    version = CONFIG["MAJOR"]+"."+CONFIG["MINOR"]
    $libdir = File.join(CONFIG["libdir"], "ruby", version)
    $sitedir = $:.find {|x| x =~ /site_ruby/}
    if !$sitedir
      $sitedir = File.join($libdir, "site_ruby")
    elsif $sitedir !~ Regexp.quote(version)
      $sitedir = File.join($sitedir, version)
    end
  end

end

##
# Install a binary file. We patch in on the way through to
# insert a #! line. If this is a Unix install, we name
# the command (for example) 'rake' and let the shebang line
# handle running it. Under windows, we add a '.rb' extension
# and let file associations to their stuff
#

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

def installBin(from, to, insert_line)
  tmp = tmp_file()

  if(insert_line)
    File.open(from) do |ip|
      File.open(tmp, "w") do |op|
        ruby = File.join($realbindir, $ruby)
        op.puts "#!#{ruby} -w"
        op.write ip.read
      end
    end

    #opfile += ".rb" if CONFIG["target_os"] =~ /mswin/i
    File::install(tmp, File.join($bindir, to), 0755, true)
    File::unlink(tmp)
  else
    File::install(from, File.join($bindir, to), 0755, true)
  end
end

prepare

installBin("bin/sl.bat", "sl.bat", false)
installBin("bin/sl", "sl", true)
