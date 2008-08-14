namespace :db do
  desc "desc"

  task :schema_version => :environment do
    puts ActiveRecord::Base.connection.select_value('select * from users')
  end
end                                                                       