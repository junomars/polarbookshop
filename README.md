# polarbookshop
Exercises from Cloud Native Spring In Action

# Notes From Chapters

These notes will pull the summary and contain my comments as sub-bullets.

## Chapter 3

- Each cloud native application should be tracked in its own codebase, and all the dependencies should be declared in a manifest using tools like Gradle or Maven.
    - I've used Gradle and Maven extensively so practicing its use in this project wasn't new. Still, it is a nice refresher. The project provides a more in-depth look at Spring's auto-magical dependencies.
- Cloud native applications don’t depend on web servers being injected into the environment, but instead, they use an embedded server and are self-contained. Tomcat is the default embedded server for Spring Boot applications and can be configured through properties to customize the ports it’s listening on, connections, timeouts, and threads.
    - I figured applications would want to use a dedicated web server with more power however it makes sense to remove the need for external depedencies. Its more effecting shifting from vertical to horizontal elasticity via configurable containers.
- The request/response interaction provided by a Servlet container like Tomcat is both synchronous and blocking. Each thread handles an HTTP request until a response is returned.
    - This was new. There were notes on being able to configure an asynchronous solution which I'm curious about. Having a large thread pool seems like a suitable alternative but its important to make sure the configuration is profiled.
- Your application will likely be consumed by other services. The "API first" principle recommends designing the API before implementing the business logic to establish a contract. In this way, other teams can develop their services to consume your application based on the contract itself without waiting for the application to be finished.
    - I hadn't thought about it but this has been standard. Alleviate blockers as soon as possible; especially if you're blocking another team as they wait for an API, ICD, etc.
- In Spring MVC, REST APIs are implemented in @RestController classes. Each REST controller method is responsible for handling an incoming request with a specific method (GET, POST, PUT, DELETE) and endpoint (e.g., /books). Methods can declare both parameters through the annotations @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, and @RequestMapping.
    - Most of my experience has not involved creating an API. This was simpler to setup than I had expected.
- Methods of a @RestController class can validate the HTTP request body before the processing happens by applying the @Valid annotation. The validation constraints for the given object are defined using the annotations from the Java Validation API on the fields. For example, @NotBlank, @Pattern, @PastOrPresent.
    - This was cool! Spring makes validation and error handling super simple.
- Java Exceptions thrown during the processing of an HTTP request can be mapped to an HTTP status code and body in a centralized @RestControllerAdvice class, decoupling the exception handling for the REST API from the code throwing the exception.
    - Spring to the rescue again. Being able to create an exception and provide a way to generate responses per error code was sweet. HATEOAS was mentioned here but not covered. I'm left wondering what that's about.
- Unit tests are not aware of the Spring configuration but can be written as standard Java tests using familiar tools like JUnit, Mockito, and AssertJ.
    - Straight forward. Not too familiar with mockito or assertj but the syntax appeared familiar.
- Integration tests need a Spring application context to run. A full application context, including an optional embedded web server, can be initialized for testing by using the @SpringBootTest annotation.
    - Yay! Fast, easy to run, *meaningful* tests = happy tests. 
- When tests are focused only on a "slice" of the application and just need a part of the configuration, Spring Boot provides several annotations for more targeted integrations tests. When using those annotations, a Spring application context is initialized, but only the components and configuration parts used by the specific functionality slice are loaded. @WebMvcTest is for testing Spring MVC components. @JsonTest is for testing JSON serialization and deserialization.
    - I wouldn't have considered testing the serialization / deserialization but that makes sense as a sanity check. Also, its cheap in time and effort. Being able to test the endpoints without standing everything up was fun to experience. The runtime of the tests drastically improved when going from integration / functional tests to stubbed (sliced?) / unit tests.
- GitHub Actions is a tool built into GitHub to declare pipelines (or workflows) to automate tasks. It can be used to build a CI pipeline to automatically run unit and integration tests whenever a developer pushes some changes to the GitHub repository.
    - I've played with Jenkins and Bamboo. Time to learn yet another mark-up language (double groan, mostly for the lame pun).
