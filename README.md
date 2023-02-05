# Movie Catalog

### Description

This is a movie catalog CRUD application, build with Spring Boot, using basic on file persistence (H2).

### Notes and Assumptions:

The task requires that the API should allow creation/update/deletion of _Movies_, _Directors_ and _Ratings_. It is
understood that a _Movie_ can have multiple _Ratings_, and each _Rating_ pertains to one and only one _Movie_. Thus, the _Rating_ object will have its own representation in
persistence, with explicit
reference to a particular _Movie_. The relationship _``Movie_ <--> _Rating``_ is 1..N.

It is assumed that a movie must have at least 1 _Director_. Thus, deletion of a _Director_ will cascade into the deletion of
all the movies which have only that director (which will eventually cascade into the deletion of the related ratings).

The owning entity of the ``Movie <--> Director`` relationship will be the Movie entity. This, from a blackbox perspective,
would mean that movies will contain a set of directors in their details, whilst directors will only contain details
about their names. However, a retrieval of all the movies directed by a director is still possible through a specific
API
endpoint.

For the sake of this exercise, and to keep the codebase compact, different endpoints will make use of the same DTOs,
however,
for presentation purposes, responses will have a minimum set of fields populated in the ``getAll`` endpoints to keep the
consumption on the API human-readable. All the details will be shown in the specific ``getById`` endpoints.

The application will make use of JPA. Persistence is handled by on-file H2 database, so that the application can be
portable and deployed without any further dependency. Given the simplicity of the problem at hand and the lack of
complex data formats, H2 is the preferred choice, although with its limitations.

In relation to this exercise, the consistency and referential integrity of the data stored will be integrally handled by
JPA. Once the relationships are defined properly and the cascading mechanism are set correctly, there is no need to
handle such scenarios at business logic / domain level (i.e. movie deletion deletes all ratings).

Given the nature of the problem, and in order to provide a usable API not coupled with the implementation of the
persistence layer, a rough, unstructured decoupling mechanism between the presentation and persistence layer is created,
at domain level.

For the same reason, no beans will be drafted as interface/implementation (i.e. Rest controllers, service classes). This
is just to avoid excessive scaffolding whilst retaining readability.

Unit tests will only cover the service and controller classes, as, provided that the persistence layer is defined
correctly and retains referencial integrity on its own, testing framework proxies does not add any value.

**N.B. unit test coverage is still WIP.**

### Requirements:

Create a program that maintains a Movie catalog allowing creation/update/deletion of:

    • Movies

    • Movie Directors

    • Movie Ratings

All communication with the program must be via REST, and the program must allow searching on the following criteria:

    • Search movies by Director

    • Search movies where the rating is above a provided rating.

The key technical requirements are as follows:

    • Must use Spring Framework to create a wholly self-contained artifact, built by Maven.

    • Must compile in Java 8. A later runtime is perfectly acceptable, but compilation must be in Java 8.

    • Must be within a git repository (remote not necessary, use own judgement on commit strategy).

    • Must persist data across application restarts (may persist outside the runnable artifact) using an RDBMS with appropriate model relationships.

    • Must allow data manipulation via REST.

    • Must contain an appropriate level of Unit Test coverage (use own judgment in defining "appropriate").

    • Must provide documentation to describe application functionality (JavaDoc, UML diagrams and/or text based documentation is acceptable, again, use own judgement).