// Интерполяция строк
jstring = "Java String"
gString = "Groovy String + ${jstring}"
println(gString)

// Конкатенация строк
println("Hello World" + "!")

// Виды строк
def lang = 'a single-quoted string' // java.lang.String
def gString = "a double-quoted string" //groovy.lang.GString

println(gString)

def aMultilineString = '''line one

line two

line three'''

println(aMultilineString)

// Индексация
def step = 'Study Stepik'
println step[4]     // y 
println step[-1]    // k 

// Срезы строк
def step = 'Study Stepik'

println step[2..4]  // срез udy
println step[1..<3] // срез tu
println step[4..2]  // обратный срез
println step[4, 1, 6] // избирательная нарезка ytS