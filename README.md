# Personal Identity Number Validation Service

## The issue
We have an endpoint in this service that returns if the supplied person identity number is a valid one.   
But we have received reports from the business that this is not working properly. This service has  
not been given the love it deserves by various reasons. So we need your help to fix the issues and  
give it the love it deserves. Please be our hero and save the day. 

## Assignment
1. The endpoint should validate if the parameter _personal identity number_ is valid or not.  
Use the following algorithm to validate if the input is a personal identity number or not.  
https://en.wikipedia.org/wiki/Personal_identity_number_(Sweden)#Checksum  
2. Every validation attempt should be persisted in the h2 database.  
These can be used to improve performance on the endpoint. 
3. When done send us your fork on email.  

** Feel free to add/change whatever you think is necessary in order to make this service as 
good as possible. Nothing in today's solution is a must have. 

## Run the app

Prerequisites: Java11, Maven

1. Start by building the app using command ```mvn clean install```
2. Run the app by using command ```java -jar target/personal-identity-number-service-0.0.1-SNAPSHOT.jar```


