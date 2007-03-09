
class MyMojo < Mojo
  goal :mymojo
  description "This is a mojo description"
  phase :validate
  requiresDependencyResolution :compile

  string :prop, :expression=>"${message}", :default=>"nothing", :alias=>"prop3"
  parameter "org.apache.maven.project.MavenProject", :project, :expression=>"${project}", :required=>true

  def execute
    info "The following String was passed to prop: '#{$prop}'"
    info "My project artifact is: #{$project.artifactId}"
    return Hash.new
  end
end

run_mojo MyMojo