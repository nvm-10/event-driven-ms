# Saga Orchestration Pattern Implementation

This implementation demonstrates the Saga pattern for managing distributed transactions in a microservices architecture.

## Architecture Overview

- **Pattern**: Orchestration-based Saga
- **Implementation Status**: In Progress
- **Last Updated**: 2025-11-20

## Services

| Service | Status | Description |
|---------|--------|-------------|
| Order Service | ⏳ Pending | Manages order creation and lifecycle |
| Payment Service | ⏳ Pending | Handles payment processing |
| Inventory Service | ⏳ Pending | Manages product inventory |
| Saga Orchestrator | ⏳ Pending | Coordinates the saga execution |

## Saga Flow

1. **Create Order** → Process Payment → Update Inventory → Complete Order
2. **Compensation Flow**: If any step fails, compensating transactions are triggered in reverse order

## Getting Started

1. **Prerequisites**
   - Java 17+
   - Docker (for infrastructure services)
   - Maven/Gradle

2. **Running the Services**
   ```bash
   # Start all services
   ./mvnw spring-boot:run -pl <service-name>
   ```

## Development Notes

- [ ] Implement Order Service
- [ ] Implement Payment Service
- [ ] Implement Inventory Service
- [ ] Implement Saga Orchestrator
- [ ] Add error handling and compensation logic
- [ ] Add monitoring and observability

## API Documentation

Will be updated as services are implemented.

- `server.port`: Specifies the port on which the Axon Server will be accessible. In this case, it's set to `8024`.
- `axoniq.axonserver.name`: Assigns a name to the Axon Server instance. Here it’s set as "EazyBank Axon Server".
- `axoniq.axonserver.hostname`: Defines the hostname, which is set to `localhost` for local development.
- `axoniq.axonserver.devmode.enabled`: When enabled (`true`), this mode optimizes the server for local development purposes.

### 3. Run the Axon Server Docker Container

Now that the configuration is ready, you can start the Axon Server in Docker. Run the following Docker command in your terminal:

```bash
docker run -d --name axonserver \
    -p 8024:8024 -p 8124:8124 \
    -v "/Users/eazybytes/Desktop/axonserver/data":/axonserver/data \
    -v "/Users/eazybytes/Desktop/axonserver/events":/axonserver/events \
    -v "/Users/eazybytes/Desktop/axonserver/config":/axonserver/config \
    axoniq/axonserver
```

**Explanation of the Docker Command**

- `-d`: Runs the container in detached mode (in the background).
- `--name axonserver`: Gives the container a name (`axonserver`), making it easier to manage.
- `-p 8024:8024 -p 8124:8124`: Maps Axon Server's internal ports (`8024` and `8124`) to the corresponding ports on your local machine.
    - Port `8024` is used for the HTTP interface (Axon Dashboard).
    - Port `8124` is used for the gRPC interface that Axon Framework applications use to communicate with the server.
- `-v "/Users/eazybytes/Desktop/axonserver/data":/axonserver/data`: Mounts the local `data` folder to the container's `/axonserver/data` directory to persist Axon Server's internal data.
- `-v "/Users/eazybytes/Desktop/axonserver/events":/axonserver/events`: Mounts the local `events` folder to store Axon events.
- `-v "/Users/eazybytes/Desktop/axonserver/config":/axonserver/config`: Mounts the local `config` folder, where you placed the `axonserver.properties` file, to the container's `/axonserver/config` directory. This ensures that the server uses the custom configuration defined in this file.

### 4. Verify the Setup

Once the container is running, you can verify the Axon Server by visiting [http://localhost:8024](http://localhost:8024) in your browser. This should open the Axon Server dashboard.

---

This setup allows you to run Axon Server locally while ensuring that all important data (like events and configurations) persist even if the container is stopped or removed. Each volume maps specific local directories to the container, ensuring that Axon Server has access to the necessary configuration, data, and events on your system.

# Axon libraries setup using Maven

All Axon Framework-related JAR files are published on Maven Central. To use Axon Framework in a Spring Boot application built with Maven, add the following to your **eazy-bom/pom.xml**

**Mention the axon version under properties:**

```
<axon.version>4.10.1</axon.version>
```

**Mention the following dependency under dependencyManagement->dependencies:**

```
<dependency>
  <groupId>org.axonframework</groupId>
	<artifactId>axon-bom</artifactId>
	<version>${axon.version}</version>
	<type>pom</type>
	<scope>import</scope>
</dependency>
```

**Mention the following dependency under common/pom.xml:**

```
<dependency>
    <groupId>org.axonframework</groupId>
    <artifactId>axon-spring-boot-starter</artifactId>
</dependency>
```