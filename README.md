# Quarkus with microstream persistence

This project is a demo project that investigates how quarkus an microstream may
be used together. [Microstream](https://microstream.one) is a java native persistence framework that is available as community edition (open source) since version 5.
Microstream is a conviniend way to store and load the Java object graph.
This makes it especially interesting for microservices since there is no overhead in the form of an ORM framework like hibernate.

In order to demonstrate that MicroStream may be extended by other frameworks and persistence solutions this 
project features an anlaytical data model using neo4j. While it is also possible to build an analytical data model with MicroStream, Neo4j's cypher query language is well suited for dynamic analysis.
By separating the business data model and the analytical data model, one can ensure separation of concerns.
Analytical queries do not affect the business data and the business data model does not need to be changed to capture analytical data.


### Architecture
````
+-----------------+        +---------------+       +------------------+
|                 |        |               |       |                  |
| PersonRessource +------->| PersonService +------>| PersonRepository |
|                 |        |               |       |                  |
+-----------------+        +-------+-------+       +-----------+------+
                                   |                           |
                                   v                           |
                          +------------------+                 v
                          |                   |           +-----------+
                          | AnalyticsRecorder |           |   SPM     |
                          |                   |           +-----------+
                          +-------------------+           |MicroStream|
                          |      Neo4J        |           +-----------+
                          +-------------------+

SPM = SimplePersistenceManager
````

## Test
Using [Jimfs](https://github.com/google/jimfs) one can use Microstream inside integration test in a convenient way. The file system type to be used can be configured inside the application.properties.


## Dev mode

Quarkus dev mode (`./mvnw compile quarkus:dev`) allows auto reload and hot code replacement. 
This feature does not work in conjunction with microstream atm. 
See the corresponding [issue](https://github.com/microstream-one/microstream/issues/189).


## Notes on GraalVM: Native Image

see [README-graalvm.md](README-graalvm.md)
