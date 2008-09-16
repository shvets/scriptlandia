# http://github.com/pelargir/finder_filter

module FinderFilter
  def self.included(base)
    base.extend(FinderFilter)
  end
  
  def finder_filter(name, options={})
    by = options.delete(:by)
    param = options.delete(:param) || :id
    before_filter "find_#{name}", options
    
    define_method "find_#{name}" do
      klass = name.to_s.classify.constantize
      item = by ? klass.send("find_by_#{by}", params[param]) : klass.find(params[param])
      instance_variable_set("@#{name}", item)
    end
  end
end
