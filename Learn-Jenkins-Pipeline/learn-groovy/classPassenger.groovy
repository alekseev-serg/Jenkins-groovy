class Passenger {

    String firstName
    String lastName
    
    String hello(String name) {
        "Hello ${firstName} ${lastName}"
    }
}

Passenger p = new Passenger()
p.firstName = "Valera"
p.lastName = "Wood"

p.hello()
