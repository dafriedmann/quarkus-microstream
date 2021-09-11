# Quarkus with microstream persistence

This project is a simple demo project that investigates how quarkus an microstream may
be used together. [Microstream](https://microstream.one) is a java native persistence framework that is available as community edition (open source) since version 5.
Microstream is a conviniend way to store and load the Java object graph.
This makes it especially interesting for microservices since there is no overhead in the form of an ORM framework like hibernate.

## Dev mode - hot class replacement

Quarkus dev mode (`./mvnw compile quarkus:dev`) allows auto reload and hot code replacement. 
This feature does not work in conjunction with microstream atm. 
See the corresponding [issue](https://github.com/microstream-one/microstream/issues/189).

## Test
Using [Jimfs](https://github.com/google/jimfs) one can use Microstream inside integration test in a convenient way. The file system type to be used can be configured inside the application.properties.

## Notes on GraalVM: Native Image

Using Quarkus one can generate a native image instead of a traditional jar running via JVM.
In order to be able to use native image generation together with microstream classes that are accessed using reflections or are dynamically loaded have to be pre-registered. 
This is mandatory due to the ahead-of-time compilation. 

Microstream uses java.lang.reflections internally. Some classes are registered automatically, but not all of them. Pre registering all classes manually results in a never ending process. Fortunately there is way to trace the usage of reflections inside a jar. I found the hint in [belu/microquark](https://github.com/belu/microquark), and I' d like to thank the author for his detailed documentation.

The process that worked for me is as follows:

**Required:** GraalVM with native-image, Visual Studio C++ (Windows)

1. mvn package quarkus runnable jar
2. Run the jar via GraalVM using this command: 
    ```sh
    java -agentlib:native-image-agent=config-output-dir=C:/tmp -jar quarkus-run.jar
    ```
Notice the -agentlib parameter has to be placed before the -jar.
While running the jar one must call every endpoint of the microservice.
After shutting down the execution one can find meta files inside C:/tmp.
The main file of interest is the `refelct-config.json`.
Copying this file into src/main/ressources and setting these properties into the pom.xml native profile does the job:

```properties
<properties>
    <quarkus.package.type>native</quarkus.package.type>
    <quarkus.native.additional-build-args>
        -H:ReflectionConfigurationFiles=reflect-config.json
    </quarkus.native.additional-build-args>
    <quarkus.native.additional-build-args>
        --allow-incomplete-classpath,
        --verbose
    </quarkus.native.additional-build-args>
</properties>
```

Note: The build is not stable and has several warnings. In order to use it in production one must fix some more issues that are reported during a run (See: --allow-incomplete-classpath argument).

