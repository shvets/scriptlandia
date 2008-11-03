#

Gem::Specification.new do |spec|
  spec.name        = 'scriptlandia'
  spec.autorequire = 'scriptlandia-r'
  spec.version     = '0.1.0'
  spec.author      = "Alexander Shvets"
  spec.date        = %q{2008-11-02}
  spec.description = 'Scriptlandia launcher.'
  spec.email       = 'alexander.shvets@gmail.com'

  candidates = Dir.glob("{docs,lib,tests}/**/*")
  spec.files = candidates.delete_if do |item|
    item.include?("svn") || item.include?("rdoc")
  end

  #spec.has_rdoc = true
  #spec.extra_rdoc_files = ["README"]
  #spec.homepage = %q{http://www.rubyonrailspec.org}

  spec.require_paths = ["lib"]
  spec.requirements = ["none"]
  #spec.rubyforge_project = %q{scriptlandia-r}
  spec.rubygems_version = '1.3.0'
  #spec.homepage = "http://www.joshost.com/MomLog"
  spec.platform = Gem::Platform::RUBY
  #spec.test_file = "tests/ts_momlog.rb"
  #spec.add_dependency("BlueCloth", ">= 0.0.4")

  spec.summary = %q{.}
end
