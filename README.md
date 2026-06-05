e-commerce app 

use spring boot mvc,spring security,JPA and postgres db

first create a admin with postman like this

http://localhost:8080/login - use this url with post request

in body use format like this
{
    "username": "admin",
    "email": "admin@gmail.com",
    "password":"admin123",
    "phone": "9876543210",
    "address": "Mumbai, India",
    "role":"ROLE_ADMIN"  
}
