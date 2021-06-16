# eventsys

## General Information
In order to run this application, you will have to download and slightly configure both MongoDB and Node.
The installation order is described below, and the steps should be relatively straight forward.

The application is for the moment configured to run in development mode, seeing as there is no need to prepare it for production since it is only going to be run locally.

## Prerequisites - MongoDB
Before you clone this repository you need to download and install **MongoDB 4.2.1 Community Edition**.

It is presumed that the application is installed with the "complete" setting in the MSI-file installation.

Download link:
```
https://www.mongodb.com/download-center/community
```

#### If not installed as complete by the MSI, then:

The server has to be running on:
```
localhost:27017 (Windows Default)
```

You will then have to create a database with the name:
```
eventsys-dev-db
```

And the service has to be allowed access from Node. 

## Prerequisites - Node 10
To run this application you need to have installed Node v10.
The application is built using Node v10.16.3. It has been tested with v10.17.0, which is the latest 10.17 release.

Node v10.17.0
```
https://nodejs.org/dist/latest-v10.x/
```

## Technologies
Eventsys is built using:
* MongoDB 4.2.1 Community Edition
* Node v10.16.3
* Vue 3.11
* Git 2.17.0

## Installation

### 1. Clone project
```
git clone https://github.com/xZyph/eventsys.git
```

### 2. Project setup
```
npm install
```

### 3. Creating Mock Data
This command will create an event, owned by the sensor-eventmanager account.
The sensor-athlete account will also automatically be enrolled to this event.
```
npm run mock:data
```

The accounts can be accessed with the following credentials:
```
Username: sensor-athlete
Password: sensor

Username: sensor-eventmanager
Password: sensor
```

### 4. Run application
```
npm run serve
```

### 5. Access application
```
Open http://localhost:8080 in a browser.
```

### (Optional) Run unit tests
```
npm run test:unit
```

### (Optional) Run integration tests
```
npm run test:e2e
```