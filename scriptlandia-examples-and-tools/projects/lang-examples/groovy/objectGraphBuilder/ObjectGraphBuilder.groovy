class ObjectGraphBuilder extends BuilderSupport
{
	def createNode(def name)
	{
		return createNode(name, null, null)
	}
	
	def createNode(def name, def value)
	{
		return createNode(name, null, value)
	}

	def createNode(def name, Map attributes)
	{
		createNode(name, attributes, null)
	}

	def createNode(def name, Map attributes, def value)
	{
		if(attributes)
		{
			return Class.forName(capitalize(name)).newInstance(attributes)
		}
		else
		{
			return Class.forName(capitalize(name)).newInstance()
		}
	}

	void setParent(def parent, def child)
	{
		if(parent)
		{
			// set the parent on the child, if applicable
			setObject(child, parent)

			// and set the parent on the child, if applicable
			setObject(parent, child)		
		}
	}
	
	void nodeCompleted(def parent, def node)
	{
	}
	
	def setObject(def object, def relatedObject)
	{
		def relationName = uncapitalize(relatedObject.class.name)
		
		if(object.properties.containsKey(relationName))
		{
			object."${relationName}" = relatedObject
		}
		else
		{
			def pluralRelationName = pluralize(relationName)
			if(object.properties.containsKey(pluralRelationName))
			{
				// doesn't handle maps yet
				object."${pluralRelationName}" << relatedObject
			}
		}
	}
	
	def capitalize(def string)
	{
		return capitalization(string, { it.toUpperCase() } )
	}
	
	def uncapitalize(def string)
	{
		return capitalization(string, { it.toLowerCase() } )
	}

	def capitalization(def string, Closure capitalizationType)
	{
		def s = ''
		if(string.length() > 0)
		{
			s += capitalizationType(string[0])
			
			if(string.length() > 1)
			{
				s += string.substring(1)
			}
		}
		
		return s
	}
	
	def pluralize(def string)
	{
		return string + 's'
	}
}
