# java-persistence-reference

This is a reference for the following topics:
1. JDBC
2. Hibernate
3. JPA

The idea is to have a nice and simple reference about what are those concepts and how should we use them

### **JDBC** 

**J**ava **D**ata **B**ase **C**onnectivity is an API that enables the communication between Java and Relational DataBases. It is a standard API. There is no need to write different code to connect to different databases.<br /><br />
The JDBC Architecture has a key component and is the **JDBC Driver** that converts the standar JDBC calls to low level calls of the specific database in use. It is provided by the database vendor.<br /><br />
The JDBC API is defined in the ***java.sql*** and ***javax.sql*** packages.

#### Development Process
1. Get a connection to database<br/>
1.1 Need a connection string  (jdbc url) -> ***jdbc***:***driver_protocol***:***driver_connection_details***
2. Create a Statement object
3. Execute SQL Query
4. Process the Result Set

### ***Installation of MySQL Server*** 
Go to http://dev.mysql.com/downloads 

### ***JDBC Driver***
https://dev.mysql.com/downloads/connector/j/



