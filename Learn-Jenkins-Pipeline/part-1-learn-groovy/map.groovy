// ----- 8 - MAP example ------

def newMap = [name: 'Valera', age: '44', id: 8]
def emptyMap = [:]

newMap['name']='Vasy'
newMap.name='Egor'

emptyMap.put("newKey", 1000)
println emptyMap.get('newKey')

newMap.each { key, value -> println "$key and $value" }