// ----- 1 ------
println 500 + 111 // comment
println 10010 + 202000
/* Более
    того
    comment
 */
println 2 * 4
println 'Hello World'

// ----- 2 Variable ------

String str1
def y

y = 1

str1 = 'stroka'
println y
println str1

def (q,w,r) = [0.0011, 9, 'new text']
println "${q} - ${w} - ${r}"
println q.getClass()
println w.getClass()
println r.getClass()

def f = 0.1f
def b = true
def c = 'a'

println c.getClass()
println f.getClass()
println b.getClass()


// ----- 3 - IF ELSE example ------
def t1 = false
def t2 = true

if ( !t1 ) {
    println "t1 is $t1"
}

if ( t2 ) {
    println "t2 is ${t2}"
} else {
    println '...'
}

def b1 = false
def b2 = true

if ( b1 ) {
    println "b1: ${b1}"
} else if ( b2 ) {
    println "b2: ${b2} and b1: $b1"
} else if (b1 && b2) {
    println 'ALL is TRUE'
} else {
    println 'else....'
}

// ----- 4 - Switch example ------
def varSwitch = 2

switch (varSwitch) {
    case 1:
        println "This is One: ${varSwitch}"
        break
    case 2:
        println "This is Two: ${varSwitch}"
        break
    case 3:
        println "This is Three: ${varSwitch}"
        break
    default:
        println 'unknown...'
        break
}

// условие ? значение1 : значение2


// ----- 5 - FOR / WHILE ------

for (int i = 0; i < 5; i++) {
    print "This is i = ${i}"
}

for (i in 0..5) {
    print i
}

def numbers = [1, 2, 3, 4, 5]

for (number in numbers) {
   println "current number: $number"
}

def text = "QWERTY"
def list = []
for (ch in text) {
    list.add(ch)
}

for (Integer i : 0..5) {
    print i
}

def x1=0
while(x1++ < 5){
    print "x1: " + x1 + "\n"
}

def count = 5
def fact = 1
do {
    fact *= count--
} while(count > 1)
println fact // fact: 120

// ----- 6 - LIST example ------
println '----------------------------------------------------------'
def exampleList = [0, 1, 2, 3, 4]
def emptyList = []

println 'list[0]: ' + exampleList[0]

// Добавление новых переменных в список
exampleList[5] = 5
exampleList << 6 << 7
exampleList += [8, 9 , 1000000]

// Перебор списка
for ( iter in exampleList) { println iter }
println '----------------------------------------------------------'


// ----- 7 - Function Groovy ------

String strMethod() {
    'This is string from method'
    return 'This is ZERO 0'
}

def paramMethod(param1) {
    "Parameter: $param1"
}

static String strParamMethod(String arg1) {
    println "This is string: $arg1"
}

def paramDefault(String a, Integer b = 100, def c = 500) {
                    println 'Text: ' + a
                    println "Default: $b and ${c}"
                }


println 'strMethod(): ' + strMethod()
println 'paramMethod(): ' + paramMethod(2340)
paramDefault('Hello')



// ----- 8 - MAP example ------

def newMap = [name: 'Valera', age: '44', id: 8]
def emptyMap = [:]

newMap['name']='Vasy'
newMap.name='Egor'

emptyMap.put("newKey", 1000)
println emptyMap.get('newKey')

newMap.each { key, value -> println "$key and $value" }