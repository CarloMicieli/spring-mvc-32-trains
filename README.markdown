Trenako
======

[![Build Status](https://secure.travis-ci.org/CarloMicieli/trenako.png)](http://travis-ci.org/CarloMicieli/trenako)

A java web application for model railways collectors.

Functionality
-----------

* Create an entry for model railway rolling stocks;
* Manage rolling stocks lists and wish lists;
* Write reviews and comments;
* Create groups;
* Manage a calendar of railways events.

Powered by
----------

* Spring 3.1
* Jersey 1.8
* Mongodb 2.0

Usage
-----

The project build is managed using a `Gradle` script. 

To build the project:

    ./gradlew build

To import the project in _Eclipse_:

    ./gradlew eclipse
    
The only requirement is to have the `Java development kit 6`, the `Gradle wrapper` will download 
all the required packages to build the application.

to generate the project Javadoc an appropriate Gradle task exists:

    gradle projectDoc

`Mongo 2.0` must be already installed before the integration tests can run successfully.
To run the application test suite:

    gradle check

Before you can run the application the `app.properties` file is required.

    cp trenako-web/src/main/resources/META-INF/app.properties.example trenako-web/src/main/resources/META-INF/app.properties

License
------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

