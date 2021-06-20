# store-product-service
This project is a RESTful Web service API with spring boot and spring MVC. The project uses java 11 with the spring framework many of the annotations that comes with it.

Spring initalizr was used partly to create Maven build system using the VS code plugin.

The dependencies set are,
Spring Web,
Spring Data JPA,
MySQL Driver,
Validation.

These can also afterwards be found in the Maven pom.xml file.

MySQL Server for the database. My MySQL version is the latest as of press time, 8.0.21.

CREATE DATABASE storee;
USE storee;
CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    quantity BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_product_id PRIMARY KEY(id)
);

mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
mysql> grant all on storee.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database

If you use Maven, you can run the application by using 

$ ./mvnw spring-boot:run

Alternatively, you can build the JAR file with ./mvnw clean package and then run the JAR file, as follows:

To build on linux,

$ ./mvnw clean package

Run the service, 

$ java -jar ./target/store-product-service-0.0.1-SNAPSHOT.jar

Test API with curl requests,

$ curl -X POST -H "Content-Type: application/json" http://localhost:7000/storee/api/v1/products -d '{ "name": "Samsung Galaxy S20", "description": "Samsung Galaxy S20" }' | jq '.'

working like a charm and showing as expected

{
    "status": 400,
    "message": "Bad Request",
    "createdAt": "2020-09-12T20:02:56.4905563+08:00",
    "path": "http://localhost:7000/storee/api/v1/products",
    "errors": [
        {
            "message": "Product price is required."
        },
        {
            "message": "Product quantity is required."
        }
    ]
}

Create a product now also with price and quantity,

$ curl -X POST -H "Content-Type: application/json" http://localhost:7000/storee/api/v1/products -d '{ "name": "Samsung Galaxy S20", "description": "Samsung Galaxy S20", "price": 50000, "quantity": 25 }' | jq '.'

{
  "id": 1,
  "name": "Samsung Galaxy S20",
  "description": "Samsung Galaxy S20",
  "price": 50000,
  "quantity": 25,
  "createdAt": "2021-06-19T17:24:43.750544+02:00"
}


$ curl http://localhost:7000/storee/api/v1/products | jq '.'

showing the product created above.

[
  {
    "id": 1,
    "name": "Samsung Galaxy S20",
    "description": "Samsung Galaxy S20",
    "price": 50000,
    "quantity": 25,
    "createdAt": "2021-06-19T17:24:44+02:00"
  }
]


Let us update an existing item corresponding to products/{id} where in below curl request id=2 and we have change the "name",

$ curl -X PUT -H "Content-Type: application/json" http://localhost:7000/storee/api/v1/products/2 -d '{ "name": "Hello and fake Samsung Galaxy S20", "description": "Samsung Galaxy S20", "price": 50000, "quantity": 25 }' | jq '.'

We still need to pass all input parameter (name, price, quantity) inside the input json otherwise an error msg occurs as specified in message.properties which is used in ProductDto.java when field is null. It can also be tested that both update and create work perfectly fine using json input with not set description i.e. field is null.

Lets delete an entry with id=3

$ curl -X DELETE http://localhost:7000/storee/api/v1/products/3 | jq '.'

Trying to delete same product returns with status code 404 and error msg,
"message": "Product with ID 3 was not found."

as expected.
