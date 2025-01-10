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
