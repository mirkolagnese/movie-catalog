# Movie Catalog

### Description

This is a movie catalog CRUD application, build with Spring Boot, using basic on file persistence (H2).

### Notes and Assumptions:

Due to a quick search on the internet, it seems that less than 1% of movies have more than 1 director. For the sake of
simplicity, this exercise will consider a Movie to have one and only one Director. Of course a Director can be linked to
many movies, thus the relationship between Movie and Director will be 0..N.

The requirements say that the API should allow creation/update/deletion of Movies, Directors and Ratings. It is
understood that a Movie can have multiple Ratings. Thus, the Rating object will have its own representation in
persistence, with explicit
reference to a particular movie. Relationship Movie to Rating is 1..N.

The application will make use of JPA. Persistence is handled by on-file H2 database, so that the application can be
portable and deployed without any further dependency. Given the simplicity of the problem at hand and the lack of
complex data formats, H2 is the preferred choice, although with its limitations.

For the same reason, no beans will be drafted as interface/implementation (i.e. Rest controllers, service classes). This
is just to avoid excessive scaffolding whilst retaining readability.

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