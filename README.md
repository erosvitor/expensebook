# About
Project created in the course API REST with Ktor.

## Requirements
* JDK 17
* Maven 3.8.x
* MySQL 8
* Postman

## Steps to Setup
1. Create the database
```
CREATE DATABASE expensebook CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;

USE expensebook;

CREATE TABLE expenses (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  value DECIMAL(11,2) NOT NULL,
  paid_at DATE NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE utf8mb3_general_ci;

```

2. Clone the application
```
git clone https://github.com/erosvitor/expensebook.git
```

3. Build the project
```
mvn package
```

4. Run the project
```
java -jar target/expensebook-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Swagger
```
http://localhost:8080/swagger-ui
```

## Using the project via Postman
1. Create expense
```
POST http://localhost:8080/expenses

{
    "description": "Luz",
    "value": 147.80,
    "paid_at": "2023-11-05"
}
```

2. Read all expenses
```
GET http://localhost:8080/expenses
```

3. Update expense data
```
PUT http://localhost:8080/expenses

{
    "id": 1,
    "description": "Luz",
    "value": 125.00,
    "paid_at": "2023-11-05"
}
```

4. Delete expense
```
DELETE http://localhost:8080/expenses/1
```

## Using the project via curl
1. Create expense
```
curl --location 'http://localhost:8080/expenses' \
     --header 'Content-Type: application/json' \
     --data ' {
         "description": "Luz",
         "value": 147.80,
         "paid_at": "2023-11-05"
       }'
```

2. Read all expenses
```
curl --location 'http://localhost:8080/expenses'
```

3. Update expense data
```
curl --location --request PUT 'http://localhost:8080/expenses' \
     --header 'Content-Type: application/json' \
     --data ' {
         "id": 1,
         "description": "Luz",
         "value": 125.00,
         "paid_at": "2023-11-05"
      }'
```

4. Delete expense
```
curl --location --request DELETE 'http://localhost:8080/expenses/1'
```

## License
This project is under license from MIT. For more details, see the LICENSE file.
