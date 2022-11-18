# REST API test framework

* [Required Software](#required-software)
* [How to execute the tests](#how-to-execute-the-tests)
    * [Running the test suites](#running-the-test-suites)
    * [Generating the test report](#generating-the-test-report)
* [About the Project Structure](#about-the-project-structure)
* [Libraries](#libraries)
* [Patterns applied](#patterns-applied)

This project was created to start the initial steps with test automation for a REST API using Rest-Assured.
It tests the API: [petstore](https://petstore.swagger.io/)

## Required software

* Java JDK 8
* Maven installed and in your classpath

## How to execute the tests

You can open each test class on `src\test\java` and execute all of them, but I recommend you run it by the
command line. It enables us to run in different test execution strategies and, also in a pipeline, that is the repo
purpose.

### Running the test suites

The test suites can be run directly by your IDE or by command line.
If you run `mvn test` all the tests will execute because it's the regular Maven lifecycle to run all the tests.

To run different suites based on the groups defined for each test you must inform the property `-Dgroups` and the group
names.
The example below shows how to run the test for each pipeline stage:

| pipeline stage | command                     |
|----------------|-----------------------------|
| users tests    | `mvn test -Dgroups="users"` |
| pet tests      | `mvn test -Dgroups="pets"`  |
| all tests      | `mvn test`                  |  

### Generating the test report

This project uses Allure Report to automatically generate the test report.
There are some configuration to make it happen:

* aspectj configuration on `pom.xml` file
* `allure.properties` file on `src/test/resources`

You can use the command line to generate it in two ways:

* `mvn allure:serve -Dallure.results.directory=../allure-results`: will open the HTML report into the browser
* `mvn allure:report -Dallure.results.directory=../allure-results`: will generate the HTML port
  at `target/site/allure-maven-plugin` folder

## About the Project Structure

### src/main/java

#### config

APIConfig is singleton class for test configurations

#### constant

ResponseCode to make response code more readable

#### utils

ConfigReader is to read environment files like qa,stage,prod

#### DBAccess

Wrapper class to find data from mysql database

#### ExcelDataProvider

Wrapper class to read data from excelsheet

#### PropertyProvider

Wrapper to read/update environment properties as key/values used in APIConfig

#### resources

config files for log4j and allure

### src/test/java

#### data

Contains builder classes for `pet` and `user` model

### requests

Wrapper class for rest calls for `pet` and `user`

#### tests

**BaseTest** that sets the initial aspects to make the requests using RestAssured.

**PetTests** contains unit test for `/pet`

**UserTest** contains unit test for `/user`

#### resources

It contains test properties file

#### Utils

Wrapper class for rest-assured class which asserts the response with each call.

## Libraries

* [RestAssured](http://rest-assured.io/) library to test REST APIs
* [Testng](https://testng.org/doc/) to support the test creation
* [jxl](https://mvnrepository.com/artifact/net.sourceforge.jexcelapi/jxl) to read excel sheets for data
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging strategy
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy

## Patterns applied

* Singleton
* Data Provider
* Builder
* Request and Response Specification
* BaseTest