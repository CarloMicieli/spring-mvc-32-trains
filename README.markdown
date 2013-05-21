Trenako
======

A Java web application for model railways collections

Functionality
-------------

* Manage a model rolling stocks database;
* Manage rolling stock collections and wish lists;
* Comments rolling stocks;
* Write reviews.

Powered by
----------

* Spring MVC 3.2
* Mongodb 2.4

Live demo
---------

A live demo for this application is currently running on OpenShift at

    http://trenako-carlomicieli.rhcloud.com/web

Usage
-----

The project includes a `Gradle` building script. 

To build the project:

    ./gradlew build

To import the project in _Eclipse_:

    ./gradlew eclipse

To import the project in _Intellij IDEA_:

    ./gradlew idea
    
The only requirement is to have the `Java development kit 6`, the `Gradle wrapper` will download 
all the required packages to build the application.

To generate the project Javadoc an appropriate Gradle task exists:

    ./gradlew projectDoc

`Mongo 2.4` must be already installed before it will be possible to run the integration tests successfully.
To run the tests suite:

    ./gradlew check

The `app.properties` file is required in order to run the application successfully. This file includes the
database username and password, and for this reason is not committed to the Git repository.
A suitable template file is available under:

    trenako-web/src/main/resources/META-INF/app.properties.example

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

