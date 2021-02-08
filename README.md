# itunes-assigment
This is a small web app simulating an iTunes like application using the Chinook sqlite database.
Deployed on [Heroku](https://itunes-assignment.herokuapp.com/)

## Features
  - Rest API
  - Thymeleaf webpage

## API Docs
Postman collection for API testing is available in the root of the project 

### Get all customers
```
GET
api/customers
```

### Get a customer from id
```
GET
api/customers/{id}
```

### Create customer
```
POST
api/customers
```

### Update customer
```
PUT
api/customers
```

### Customers per country
```
GET
api/customers/country/amount
```

### Customers ordered by spending
```
GET
api/customers/spending
```

### Get customers favourite genre(s)
```
GET
api/customers/{id}/favourite/genre
```
