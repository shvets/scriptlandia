// TestRecipe.groovy
// Link: http://nealford.com/downloads/conferences/canonical/Neal_Ford-Building_DSLs_in_Static_and_Dynamic_Languages-handouts.pdf

class Ingredient {
  String name
  int quantity

  Ingredient(String name) {
    this.name = name
  }

  String toString() {
    "Ingredient { " + "name: " + name + ", " + "quantity: " + quantity + " }"
  }
}

class Recipe {
  List ingredients = new ArrayList()
 
  String name
 
  Recipe(String name) {
    this.name = name
  }

  def add(ingredient) {
    ingredients.add(ingredient)
  }

  String toString() {
    "Recipe { " + "name: " + name + "," + "ingredients: " + ingredients + " }"
  }
}

// redefinging system classes behavior

Integer.metaClass.getGram = { ->
  delegate.intValue()
}

Integer.metaClass.getGrams = { -> delegate.gram }

Integer.metaClass.gram = { ->
  delegate.intValue()
}

Integer.metaClass.getPound = { ->
  delegate * 453.59237
}

Integer.metaClass.getPounds = { -> delegate.pound }
Integer.metaClass.getLb = { -> delegate.pound }
Integer.metaClass.getLbs = { -> delegate.pound }

Integer.metaClass.of = { name ->
  def ingredient = new Ingredient(name)

  ingredient.quantity = delegate.intValue()

  ingredient
}

BigDecimal.metaClass.of = { name ->
  def ingredient = new Ingredient(name)

  ingredient.quantity = delegate.intValue()

  ingredient
}

// test

def recipe = new Recipe("Smoky Flour")

recipe.add 1.pound.of("Flour")
recipe.add 2.grams.of("Nutmeg")

print "recipe: " + recipe
