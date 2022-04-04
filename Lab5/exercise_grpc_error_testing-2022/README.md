# gRPC testing

This is the base project for the gRPC testing application, composed of three modules:

- [contract](contract/) - protocol buffers definition
- [server](server/) - implementation of service
- [client](client/) - invocation of service

To compile and install all modules:

```
mvn clean install -DskipTests
```

The integration tests are skipped because they require the servers to be running.

----

[SD Faculty](mailto:leti-sod@disciplinas.tecnico.ulisboa.pt)
