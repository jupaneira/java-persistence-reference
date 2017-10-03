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

### Object persistence concepts
Save to a data store the state of an object and re/created it at a later point in time

RDBMS Rules
- Entity Integrity - every table has a primary key (NULL values are not valid for a primary key)
- Referential Integrity - a foreign key points at a value that is the primary key pf another table (NULL values are valid)

Paradigm Mismatch (object model vs relational model)
1. Granularity 
2. Subtypes (inheritance)
3. Identity (object identity & object equality)
4. Data Navigation

When we use JDBC API:
* sql knowledge 
* Writting too many sql statements
* Too many copy codes
* SQL code that is written is database dependent

Those are problems generated because de mismatch between object and relational models. The solution?

**ORM** = technique of mapping the representation of data from Java Objects to Relational database (vise versa)
* Hides the complexity of SQL and JDBC
* XML or Annotations

## **HIBERNATE**
Hibernate is an ORM framework use to map java objects to relational dabases. Java Programers are use to create POJOs and hibernate create the sql code. 

You can configure logging with hibernate

### *Transactions*
A transaction is a group of operations that are run as a single unit of work
All the instructions are executed only when the transaction is commited. 

### Entity vs Component 
Answer the question: Do we need a individual identity of the thing that we are going to persist ? If yes, It is an @Entity, if not, it is a component (value-type) and its denoted by @Embedded - @Embeddable 

### Associations
We can stablish de relation between entities @ManyToOne , for example, and defining a @JoinColumn

### Cascades
When we have associations between entities, we can do a "transitive persistence" in order to persist automatically the chain of objects that are associated. We dont have to persist one by one. Only the root object. 
There are many Cascade types:
* We can use it in order to persist *CascadeType.PERSIST* 
* We can use it in order too delete objects *CascadeType.REMOVE*  (deletes the whole object graph)

In OneToOne and ManyToMany relations  we declare a side as not responsible for the relationship with 
*@ManyToMany(mappedBy="..")*

### Enum persistence
@Enumerated(EnumType.ORDINAL/EnumType.STRING)

### Persisting collection
@ElementCollection
@CollectionTable

### Composite Primary key
@EmbeddedId -> creating a new class to hold the involved columns

## **JPA** ##
JPA is a Java specification for accesing, persisting and managing data between Java objects and a relational database.
Provide guidelines that a framework can implement to be considered JPA compatible.

Hibernate is a JPA provider. It has a implementation of a native API but also a implementation of JPA. 

All the previous annotations come from JPA (*javax.persistence.*) , not from Hibernate.
But the SessionObject, SessionFactory,Transaction object are Hibernate Objects.

| JPA                 | Hibernate     | 
| --------------------|:-------------:|
| EntityManagerFactory| SessionFactory| 
| EntityManager       | Session       | 

### *persistence.xml* 
File inside the META-INF folder where the persistence units are defined. Each persistence unit defines the conectivity to a datasource.
* Database connection settings

### Persistence Context
When you create a Hibernate Session Onject or a EntityManager, they are created with a *persistence context*. This is a First-level Cache.

The Cache is a copy of data (pulled from the database but living outside of the database). This copy of data is located in memory. That is the reason why it is faster to read. Cache can improve the performance of our application.  The persistence context of an EntityManager is a ***first-level caching***.

Second-level-caching -> EntityManagerFactory in order to access data across EntityManager persistence contexts. 

### SQL Joins
* Inner Join or Join = Returns the rows that match in both tables <br/>
*select * from A INNER JOIN B on A.name = B.name*
* Left Outer Join = Returns all the rows from the left table with the matching rows in the right table. If there is no match, the right side will contain null <br/>
*select * from A LEFT OUTER JOIN B on A.name = B.name*

### Lazy Fetching
A collections is fetched or loaded when the application invokes an operations upon that collection. <br/>

* By default,  *collection associations* (@OneToMany and @ManyToMany) are lazily fetched. It is thanks to a proxy that only loads the collection data when it is needed. **Improved performance**
* By default, *single point associations* (@OneToOne and @ManyToOne) are eaferly fetched

### Query Language 
When we are using JPA we are using JPQL-> deals with the entitites and data attributes and translates it to SQL at runtime. <br/>
Hibernate has own language - HQL. It is also translated into SQL at runtime.

**JPQL**<br/>  
*select st from Student as st* <br/>  
**Translated into SQL**<br/>  
*select <br/>
&nbsp;&nbsp;  st.id as id,<br/>
&nbsp;&nbsp;  st.name as name, <br/>
&nbsp;&nbsp;  st.lastName as lastName, <br/>
&nbsp;&nbsp;  from <br/>
&nbsp;&nbsp;  Student st* <br/>

JPQL supports named parameters in the queries. 
You can also use native SQL queries -> *entityManager.createNativeQuery(query...)*





