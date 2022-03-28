import java.sql.DriverManager
import java.sql.SQLException

// the model class
data class User(val id: Int, val name: String)

fun main() {

    val jdbcUrl = "jdbc:h2:tcp://localhost:9092/default"

    // get the connection
    val connection = DriverManager
        .getConnection(jdbcUrl, "postgres", "postgres")

    // prints true if the connection is valid
    println(connection.isValid(0))


    try {
        //Get and instance of statement from the connection and use
        //the execute() method to execute the sql
        connection.createStatement().use { st ->
            //SQL statement to create a table
            st.execute("CREATE TABLE USER (id  number(3) NOT NULL AUTO_INCREMENT,name varchar(120) NOT NULL,")
            st.execute("insert into USER" + "(id = 1, name = manuel)")
        }
        //Commit the change to the database
        connection.commit()
    } catch (e: SQLException) {
        printSQLException(e)
    }

    // the query is only prepared not executed
    val query = connection.prepareStatement("SELECT * FROM USER ")

    // the query is executed and results are fetched
    val result = query.executeQuery()

    // an empty list for holding the results
    val users = mutableListOf<User>()

    while (result.next()) {

        // getting the value of the id column
        val id = result.getInt("id")

        // getting the value of the name column
        val name = result.getString("name")

        /*
        constructing a User object and
        putting data into the list
         */
        users.add(User(id, name))
    }
    /*
    [User(id=1, name=Kohli), User(id=2, name=Rohit),
    User(id=3, name=Bumrah), User(id=4, name=Dhawan)]
     */
    println(users)
}

fun printSQLException(e: SQLException) {

}
