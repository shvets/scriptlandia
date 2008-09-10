#

if RAILS_ENV == "test"
  class ActiveRecord::ConnectionAdapters::AbstractAdapter
    @@queries = []
    cattr_accessor :queries
  
    def log_info_with_trace(sql, name, runtime)
      return unless @logger and @logger.debug?
      self.queries << sql
      #log_info_without_trace(sql, name, runtime)
      puts "sql: " + sql
    end
  
    alias_method_chain :log_info, :trace
  end
end
