# Getting Started

## Setup

### Pre-requisities

To run the application you would require:

- [Java](https://www.azul.com/downloads/#zulu)
- [Temporal](https://docs.temporal.io/cli#install)
- [Docker](https://docs.docker.com/get-docker/)
- [Stripe API Keys](https://stripe.com/docs/keys)

### On macOS:

First, you need to install Java 21 or later. You can download it from [Azul](https://www.azul.com/downloads/#zulu) or
use [SDKMAN](https://sdkman.io/).

```sh
brew install --cask zulu21
```

You can install Temporal using Homebrew

```sh
brew install temporal
```

or visit [Temporal Installation](https://docs.temporal.io/cli#install) for more information.

You can install Docker using Homebrew

```sh
brew install docker
```

or visit [Docker Installation](https://docs.docker.com/get-docker/) for more information.

### Other platforms

Please check the official documentation for the installation of Java, Temporal, and Docker for your platform.

### Stripe API Keys

Sign up for a Stripe account and get your API keys from the [Stripe Dashboard](https://dashboard.stripe.com/apikeys).
Then in `application.properties` file replace "<STRIPE-API-KEY>" the following line with your secret key.

```properties
stripe.api-key=<STRIPE-API-KEY>
```

## Run

You are required to first start the temporal server using the following command

```sh
temporal server start-dev
```

and then run the application using the following command or using your IDE.

```sh
./gradlew bootRun
```

### Other commands

#### Lint
To run lint checks, use the following command

```sh
./gradlew sonarlintMain
```

#### Code Formatting
To format the code, use the following command

```sh
./gradlew spotlessApply
```

## Implementation
Three main classes have been defined for this project to work:
1. AccountActivityImpl.java
2. CreateAccountWorkflowImpl.java
3. TemporalWorker.java
4. TemporalConfig.java

### AccountActivityImpl.java
This is the temporal Activity implementation that overrides the saveAccount and createPaymentAccount methods. The Stripe API request is executed in the createPaymentAccount method of this class. This activity class will be executed in the temporal workflow implementation class.

### CreateAccountWorkflowImpl.java
This is the temporal Workflow implementation that overrides the createAccount method to execute the implemented temporal activity methods. The workflow ensures that the customer account is created and that the customer info is persisted in the database.

### TemporalWorker.java
This class defines the temporal worker instance that polls for tasks based on the queue name and registered workflow/activity implementations. The workflow will not progress despite being defined with activities if the workers are not defined.

### TemporalConfig.java
This is a configuration class that is used to define beans that call the worker methods and register the workflow and activity implementations.

## Tests
Not implemented
