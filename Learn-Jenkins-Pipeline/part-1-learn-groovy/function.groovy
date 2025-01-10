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