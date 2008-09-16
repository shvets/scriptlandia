class Company {
  String name
  String address

  String toString(){
    return name
  }

  /*
  static hasMany = [trip:Trip]

  static constraints = {
    name(blank:false, maxSize:100)
    url(url:true)
    frequentFlyer(blank:true)
    notes(maxSize:1500)  
  }
  
  */

  /*
def constraints = {
hardiness(inList:["Hardy", "Half Hardy", "Tender"])
annual(inList:["Annual", "Perennial", "Biennial"])
}

String toString() { "${this.class.name} : $id" }
boolean equals(other) {
if(other?.is(this))return true
if(!(other instanceof Plant)) return false
if(!id || !other?.id || id!=other?.id) return false
return true
}
int hashCode() {
int hashCode = 0
hashCode = 29 * (hashCode + ( !id ? 0 : id ^ (id >>> 32) ) )
}
   */
}
