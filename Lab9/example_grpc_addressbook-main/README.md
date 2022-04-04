# gRPC example

This is a gRPC example application, composed of three modules:
- [contract](contract/) - protocol buffers definition
- [server](server/) - implementation of service
- [client](client/) - invocation of service

## Instructions
Create a python virtual environment and install de grpc packages
```
 # command to install virtualenv
 python -m pip install virtualenv 

 # create a virtual environment
 # windows
 > python -m venv .venv
 > venv\Scripts\activate

 # linux
 $ virtualenv .venv
 $ source .venv/bin/activate

 # install the packages
 python -m pip install grpcio
 python -m pip install grpcio-tools

```
See the README for each module.  
Start at contract, then go to server, and finally go to the client.

----

[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)
