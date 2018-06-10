## Table of Contents

- [Introduction](#introduction)
- [Deployment](#deployment)
- [API Endpoints](#api-endpoints)


### Introduction ###

This is a restful web application that exposes a few endpoints. The project needs the following softwares to be installed.

* Java 8
* Apache Maven 3.x installed.

### Deployment ###

There are two ways to deploy the web application.

As this is a Maven project, it can be imported in an IDE such as Eclipse or IntelliJ as a Maven Project.

* Create a Run configuration -> Java Application. Once done, select App as a main-class. 
 The application will be deployed to an embedded TOMCAT container 

* Build and compile the project from command line. Navigate to the project root using the command line, and execute the following command ```mvn spring-boot:run```. You will need maven plugin for that.

The RESTful services can be invoked after either steps is performed. 

**IMPORTANT**: these two instructions are mutually exclusive.

### API Endpoints ##
Following are the list of endpoints supported the restful application

#### GET /avenuecode/rest/helloword ####

```
 curl -XGET 'http://localhost:8080/avenuecode/products/greaterthanprice/{price}'
```
Returns an array of products having price greater than provided price.

**Request**

| Name | Type | Description| Required |
| :---         |     :---     |          :--- | :---   |
| price  | decimal (path variable)   | price | Yes |

**Response**

A success response will include:

● a status code of 200 Created

```
[
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             {
                 "upc": "<some_upc_value2>",
                 "sku": "<some_sku_value2>",
                 "description": "<some_description2>",
                 "price": 30.00
             },
             {
                 "upc": "<some_upc_value3>",
                 "sku": "<some_sku_value3>",
                 "description": "<some_description3>",
                 "price": 40
             },
]
```

#### GET /avenuecode/products/{productId} ####

Returns product by product id.

```
curl -XGET  'http://localhost:8080/avenuecode/products/{productId}/'
```

**Request**

| Name | Type | Description| Required |
| :---         |     :---     |          :--- | :---   |
| productId  | string (path variable)   | product id  | Yes |


**Response**

A successful response will be a json array.

● a status code of 200 OK

● Response body properties

```
{
    "upc": "<some_upc_value>",
    "sku": "<some_sku_value>",
    "description": "<some_description>",
    "price": 19.99
}
```

#### GET /avenuecode/products ####

```
curl -XGET 'http://localhost:8080/avenuecode/products/
```
Returns an array of all products.

**Response**

● a status code of 200 OK

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
| upc   | string | order number|
| sku   | string | discount applied; can be `null`.|
| description   | string | description of the product|
| price    | decimal |total price of the products|

Sample Response

```
[
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             {
                 "upc": "<some_upc_value2>",
                 "sku": "<some_sku_value2>",
                 "description": "<some_description2>",
                 "price": 30.00
             },
             {
                 "upc": "<some_upc_value3>",
                 "sku": "<some_sku_value3>",
                 "description": "<some_description3>",
                 "price": 40
             },
]
```

#### GET /avenuecode/morethantwoproducts ####

```
curl -XGET 'http://localhost:8080/avenuecode/morethantwoproducts'
```

Returns all the orders that have more than two products associated with an order.

**Response**

The response payload returns orders associated with more than two products.

● a status code of 200 Created

● Response Body Properties

```
[
    {
         "orderNumber": "<order_number>",
         "taxPercent": 10,
         "total": 110,
         "totalTax": 10,
         "grandTotal": 100,
         "status": "<status>",
         "products": [
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             {
                 "upc": "<some_upc_value2>",
                 "sku": "<some_sku_value2>",
                 "description": "<some_description2>",
                 "price": 30.00
             },
             {
                 "upc": "<some_upc_value3>",
                 "sku": "<some_sku_value3>",
                 "description": "<some_description3>",
                 "price": 40
             },
         ]
    }
]
```

### GET /avenuecode/discounted/

```
curl -XGET  'http://localhost:8080/avenuecode/discounted/'
```

Returns all the discounted orders from the database.

**Response**

The response payload returns a unique person identifier associated with the person.

● a status code of 200 OK

● Response Body Properties

```
{
         "orderNumber": "<order_number>",
         "taxPercent": 10,
         "total": 110,
         "totalTax": 10,
         "grandTotal": 100,
         "status": "<status>",
         "products": [
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             . . . . . More products . . . . . 
         ]
}
```

### GET /avenuecode/orders/status/{status}

```
curl -XGET 'http://localhost:8080/avenuecode/status/{status}'
```

Returns an entity of type `Order` from the database by `orderId`.

The sample request body is given below

| Name | Type | Description | Required|
| :---         |     :---      | :--- |:--- |
| status | string (path variable)  | status of the order  |Yes|

**Response**

The response payload returns the success of the operation.

● a status code of 200 OK

● Response Body Properties

```
[
      {
         "orderNumber": "<order_number>",
         "taxPercent": 10,
         "total": 110,
         "totalTax": 10,
         "grandTotal": 100,
         "status": "<status>",
         "products": [
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             . . . . . More products . . . . . 
         ]
      }
     . . . . Some other orders . . . .
]
```

### GET /avenuecode/orders/{orderId}

```
curl -XDELETE 'http://localhost:8080/avenuecode/orders/{orderId}'
```

Returns an entity of type `Order` from the database by `orderId`.

The sample request body is given below

| Name | Type | Description | Required|
| :---         |     :---      | :--- |:--- |
| orderId | string (path variable)  | unique person identifier  |Yes|

**Response**

The response payload returns the success of the operation.

● a status code of 200 OK

● Response Body Properties

```
{
         "orderNumber": "<order_number>",
         "taxPercent": 10,
         "total": 110,
         "totalTax": 10,
         "grandTotal": 100,
         "status": "<status>",
         "products": [
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             . . . . . More products . . . . . 
         ]
}
```

#### GET /avenuecode/orders ####

```
curl -XGET 'http://localhost:8080/avenuecode/orders'
```

The function checks if the deadlock has occurred after specified period of time.


**Response**

● a status code of 200 OK

● Response Body Properties


| Name | Type | Description |
| :---         |     :---      | :--- |
| orderNumber   | string | order number|
| discount   | decimal | discount applied; can be `null`.|
| taxPercent   | decimal | tax percentage applied|
| total    | decimal |total worth of orders|
| totalTax   | decimal |total tax applied to the order|
| grandTotal  | decimal |total worth of the order|


The sample response is given below

```
{
         "orderNumber": "<order_number>",
         "taxPercent": 10,
         "total": 110,
         "totalTax": 10,
         "grandTotal": 100,
         "status": "<status>",
         "products": [
             {
                 "upc": "<some_upc_value>",
                 "sku": "<some_sku_value>",
                 "description": "<some_description>",
                 "price": 19.99
             },
             . . . . . More products . . . . . 
         ]
}
```