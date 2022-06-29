# Turmas

Distributed Systems Project 2021/2022

## Authors

**Group A08**

### Code Identification

In all source files (namely in the *groupId*s of the POMs), replace __GXX__ with your group identifier. The group
identifier consists of a G and the group number - always two digits. This change is important for code dependency
management, to ensure your code runs using the correct components and not someone else's.

### Team Members

| Number | Name              | User                             | Email                               |
|--------|-------------------|----------------------------------|-------------------------------------|
| 95550  | David Belchior | <https://github.com/FunkyCracky>   | <mailto:davidbelchior@tecnico.ulisboa.pt>   |
| 95635  | Mariana Charneca       | <https://github.com/Mariana-droid>     | <mailto:mariana.charneca@tecnico.ulisboa.pt>     |
| 96904  | Pedro Severino     | <https://github.com/gh0stSevs> | <mailto:pedro.severino@tecnico.ulisboa.pt> |

## Getting Started

The overall system is made up of several modules. The main server is the _ClassServer_. The clients are the _Student_,
the _Professor_ and the _Admin_. The definition of messages and services is in the _Contract_. The future naming server
is the _NamingServer_.

See the [Project Statement](https://github.com/tecnico-distsys/Turmas) or a complete domain and system description.

### Prerequisites

The Project is configured with Java 17 (which is only compatible with Maven >= 3.8), but if you want to use Java 11 you
can too, just downgrade the version in the POMs.

To confirm that you have them installed and which versions they are, run in the terminal:

```s
javac -version
mvn -version
```

### Installation

To compile and install all modules:

```s
mvn clean install
```

### Running 

#### ClassServer

```s
cd ClassServer
mvn exec:java -Dexec.args="<<QUALIFIER>> <<HOST>> <<PORT>>" # To run with debug mode turned off
mvn exec:java -Dexec.args="<<QUALIFIER>> <<HOST>> <<PORT>> -debug" # To run with debug mode turned on
# <<QUALIFIER>> must be P or S and <<PORT>> must be an integer (between 1 and 65535, excluding the well-known ports).
```

#### Admin

```s
cd Admin
mvn exec:java -Dexec.args="<<HOST>> <<PORT>>" # To run with debug mode turned off
mvn exec:java -Dexec.args="<<HOST>> <<PORT>> -debug" # To run with debug mode turned on
```

#### Professor

```s
cd Professor
mvn exec:java -Dexec.args="<<HOST>> <<PORT>>" # To run with debug mode turned off
mvn exec:java -Dexec.args="<<HOST>> <<PORT>> -debug" # To run with debug mode turned on
```

#### Student

```s
cd Student
mvn exec:java -Dexec.args="<<HOST>> <<PORT>> alunoXXXX <student_name>" # To run with debug mode turned off
mvn exec:java -Dexec.args="<<HOST>> <<PORT>> alunoXXXX <student_name> -debug" # To run with debug mode turned on
# XXXX must be an integer, student_name must contain from 3 to 30 characters
```

#### Naming Server

```s
cd NamingServer
mvn exec:java # To run with debug mode turned off
mvn exec:java -Dexec.args="-debug" # To run with debug mode turned on
```

## Built With

* [Maven](https://maven.apache.org/) - Build and dependency management tool;
* [gRPC](https://grpc.io/) - RPC framework.
