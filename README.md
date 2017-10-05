# java-persistence-reference

This is a reference for the following topics:
1. JDBC
2. Hibernate
3. JPA

The idea is to have a nice and simple reference about what are those concepts and how should we use them

# Table of Contents
1. [JDBC](#jdbc) <br/>
1.1 [Development Process](#dev_process) <br/>
1.2 [MySQL Server installation](#mysql_installation) <br/>
1.3 [MySQL JDBC Driver](#driver) <br/>
2. [Object persistence Concepts](#concepts) <br/>
3. [Hibernate](#hibernate)<br/>
3.1 [Transactions](#transaction) <br/>
3.2 [Entity vs Component ](#entity_vs_com) <br/>
3.3 [Associations](#associations) <br/>
3.4 [Cascades](#cascades) <br/>
3.5 [Enum Persistence](#enums) <br/>
3.6 [Persisting collection](#collection) <br/>
3.7 [Composite Primary key](#composite) <br/>
4. [JPA](#jpa) <br/>
4.1 [Persistence Context](#persistence_context) <br/>
4.2 [SQL Joins](#sql_joins) <br/>
4.3 [Lazy Fetching](#lazy_fetching) <br/>
4.4 [Query Language](#query_language) <br/>
4.5 [Mapping Inheritance](#inheritance) <br/>
4.6 [N+1 Select problem](#n_1_select_problem) <br/>
4.7 [Batch Fetching](#batch_fetching) <br/>
4.8 [Merging Detached Objects](#merging) <br/>
4.9 [Optimistic Locking and Versioning](#versioning) <br/>
4.10 [Isolation Levels](#isolation_levels) <br/>
4.11 [Catching and Object Identity](#catching_and) <br/>
4.12 [Second level Cache](#2l_cache)<br/>
5. [Best Practices](#best_practices)

<a name="jdbc"></a>
### 1. **JDBC**  

**J**ava **D**ata **B**ase **C**onnectivity is an API that enables the communication between Java and Relational DataBases. It is a standard API. There is no need to write different code to connect to different databases.<br /><br />
The JDBC Architecture has a key component and is the **JDBC Driver** that converts the standar JDBC calls to low level calls of the specific database in use. It is provided by the database vendor.<br /><br />
The JDBC API is defined in the ***java.sql*** and ***javax.sql*** packages.

<a name="dev_process"></a>
### 1.1 Development Process 
1. Get a connection to database<br/>
1.1 Need a connection string  (jdbc url) -> ***jdbc***:***driver_protocol***:***driver_connection_details***
2. Create a Statement object
3. Execute SQL Query
4. Process the Result Set

<a name="mysql_installation"></a>
### 1.2 ***Installation of MySQL Server*** 
Go to http://dev.mysql.com/downloads 

<a name="driver"></a>
### 1.3 ***JDBC Driver*** 
https://dev.mysql.com/downloads/connector/j/

<a name="concepts"></a>
### 2. Object persistence concepts  
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

<a name="hibernate"></a>
## 3. **HIBERNATE**
Hibernate is an ORM framework use to map java objects to relational dabases. Java Programers are use to create POJOs and hibernate create the sql code. 

You can configure logging with hibernate

<a name="transaction"></a>
### 3.1 *Transactions*
A transaction is a group of operations that are run as a single unit of work
All the instructions are executed only when the transaction is commited. 

<a name="entity_vs_com"></a>
### 3.2 Entity vs Component 
Answer the question: Do we need a individual identity of the thing that we are going to persist ? If yes, It is an @Entity, if not, it is a component (value-type) and its denoted by @Embedded - @Embeddable 

<a name="associations"></a>
### 3.3 Associations
We can stablish de relation between entities @ManyToOne , for example, and defining a @JoinColumn

<a name="cascades"></a>
### 3.4 Cascades
When we have associations between entities, we can do a "transitive persistence" in order to persist automatically the chain of objects that are associated. We dont have to persist one by one. Only the root object. 
There are many Cascade types:
* We can use it in order to persist *CascadeType.PERSIST* 
* We can use it in order too delete objects *CascadeType.REMOVE*  (deletes the whole object graph)

In OneToOne and ManyToMany relations  we declare a side as not responsible for the relationship with 
*@ManyToMany(mappedBy="..")*

<a name="enums"></a>
### 3.5 Enum persistence
@Enumerated(EnumType.ORDINAL/EnumType.STRING)

<a name="collection"></a>
### 3.6 Persisting collection
@ElementCollection
@CollectionTable

<a name="composite"></a>
### 3.7 Composite Primary key
@EmbeddedId -> creating a new class to hold the involved columns

<a name="jpa"></a>
## 4. **JPA** ##
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

<a name="persistence_context"></a>
### 4.1 Persistence Context
When you create a Hibernate Session Onject or a EntityManager, they are created with a *persistence context*. This is a First-level Cache.

The Cache is a copy of data (pulled from the database but living outside of the database). This copy of data is located in memory. That is the reason why it is faster to read. Cache can improve the performance of our application.  The persistence context of an EntityManager is a ***first-level caching***.

Second-level-caching -> EntityManagerFactory in order to access data across EntityManager persistence contexts. 

<a name="sql_joins"></a>
### 4.2 SQL Joins
* Inner Join or Join = Returns the rows that match in both tables <br/>
*select * from A INNER JOIN B on A.name = B.name*
* Left Outer Join = Returns all the rows from the left table with the matching rows in the right table. If there is no match, the right side will contain null <br/>
*select * from A LEFT OUTER JOIN B on A.name = B.name*

<a name="lazy_fetching"></a>
### 4.3 Lazy Fetching
A collections is fetched or loaded when the application invokes an operations upon that collection. <br/>

* By default,  *collection associations* (@OneToMany and @ManyToMany) are lazily fetched. It is thanks to a proxy that only loads the collection data when it is needed. **Improved performance**
* By default, *single point associations* (@OneToOne and @ManyToOne) are eagerly fetched

Example = *@OneToMany(fetch=Fetchtype.LAZY)*

<a name="query_language"></a>
### 4.4 Query Language 
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

<a name="inheritance"></a>
### 4.5 Maping Inheritance

There are 3 strategies in order to map inheritance from object model to the relational model:
@Entity
@Inheritance(strategy=InheritanceType.___)

1. SINGLE_TABLE <br/>
The class hierarchy is represented in one table and a discriminator column identifies the type of the subclass.
 * Good for polymorphic queries; no joins required, all the data is in a single table
 * All the properties in subclasses must not have not-null constraint

2. JOINED <br/>
The superclass has a table and each subclass has a table that contains only un-inherited properties. The primary key of the subclasses-tables are foreign keys to the superclass table. 
 * Poor performance for polymorphic queries due to the number of joins
 * All the properties in subclasses may have not-null constraint

3. TABLE_PER_CLASS
Each table contains all the properties of the concrete class and also the properties that are inherited from its superclasses.
 * Complex performance for polymorphic queries 
 * Good performance for derived class queries (there is a table for each derived class)
 * Not all JPA providers might have support of it 

<a name="n_1_select_problem"></a>
### 4.6 N+1 Selects problem 
For single point associations (OneToOne ManyToOne) the default fetch strategy is eagerly. So in runtime, it is executed 1 select for the root object, and N selects if we have N child objects. We can solve this changing the fetching strategy from EAGERLY to LAZY, **or** writting a specific query.

<a name="batch_fetching"></a>
### 4.7 Batch Fetching
@BatchSize(size=?) --> HIBERNATE PACKAGE
Using Batch Fetching, Hibernate can load several uninitilized proxies, even if just one proxy is accessed. 

<a name="merging"></a>
### 4.8 Merging Detached Objects
CascadeType.MERGE -> when updating a root object, we update also all of his dependencies if they have changed. 
1. *Detached object* Merge needed in order to  update objects. 2 EntityManagers invovled.
2. *Extended Persistence Context* No merge needed. Only one entityManager is used.

<a name="versioning"></a>
### 4.9 Optimistic Locking and Versioning
When multiple user are accessing our database, we can protect the system against lost updates through the following ways:
1. Versioning <br/>
Add a new column called version in the table. <br/>
Add a new field called version in the entity. <br/>
Anotate it with @Version <br/>
Hibernate is going to automatically update the version number of a changed row. <br/>
Hibernate is going to check for the version number at each update and an excetion will be thrown, to prevent the lost update. <br/>
***OPTIMISTIC LOCKING*** =  Official name of the Versioning strategy

<a name="isolation_levels"></a>
### 4.10 Isolation Levels
Isolation levels defines the extend to which a transaction is visible to other transactions.
How and when the changes made by one transaction are made visible to other transactions.

| Levels  (from highest to lowest isolation-level)           | 
| --------------------|
|  SERIALIZABLE       | 
|  REPEATABLE_READ    | 
|  READ_COMMITED      | 
|  READ_UNCOMMITED    | 

True Isolation = slow performance
from 1 to 4 -> better performance & lesser data ingetrity

<a name="catching_and"></a>
### 4.11 Catching and Object Identity
Hibernate to be able to look for an object in a cache, it needs to know the ID of that Object.  *em.find(Class, ID)*
If we execute a JPQL query, de query will be executed against the database, but the stored object in the cache, will continue being the same.

<a name="2l_cache"></a>
### 4.12 Second level Cache
Shared Data Cache /  Shared Cache accross all the entityManagers.
Hibernate as a JPA provider does not come with an implementation of L2 Cache.
L2 Cache is an optional optimization feature in JPA.
@Cacheable

<a name="best_practices"></a>
## 5. BEST PRACTICES

1. Declare identifier properties on persistent classes (generate them with no business meaning)
2. Do not treat exceptions. Rollback the transaction. Close the entityManager session.
3. Prefer lazy fetching for associations
4. Prefer bidirectional associations
5. Use bind variables - named parameters

