class Report < ActiveRecord::Base

  def to_s
    "Report { name: #{name}; table: ${table}; controller: #{controller} }"
  end
end
