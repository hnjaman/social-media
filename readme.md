## The technologies are used here
1. Spring Boot
2. Spring Security
3. Hibernate/JPA
4. Logback
5. JWT
6. Thymeleaf

## System configuration prerequisites to run the application
### 1. Clone the project
Open terminal and run
````
git clone https://github.com/hnjaman/status-application.git
````
In your current directory ``status-application`` directory will be created.

### 2. Install Java and Maven
Install java 8 or higher version and Apache Maven 

### 3. Integrate a MySQL database with the application
Just two table are used here  
![ER Diagram](readme-images/ER-diagram.JPG)

Note: No need to create any table. It will create by itself when run the application. Just create ```post``` database
and put its credential in *application.properties* file 
```
spring.datasource.url=jdbc:mysql://locathost/post?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false
spring.datasource.username=username
spring.datasource.password=password
```

## Run the application
````
cd service-registry/
mvn clean install
mvn spring-boot:run
````

go to browser and enter The application will run in ``http://localhost:8080/home`` and browse other stuff.

##### Home page with all public posts:
![Home page with all public posts](readme-images/home.JPG)

##### Sign up page:
![Sign up page](readme-images/signup.JPG)

##### Login page:
![Login page](readme-images/login.JPG)

##### All user posts:
![All user posts](readme-images/allposts.JPG)

##### Logged user posts:
![Logged user posts](readme-images/userposts.JPG)

##### Add new post:
![Add new post](readme-images/addpost.JPG)

##### Edit post:
![Edit post](readme-images/editpost.JPG)

##### Logout:
![Logout](readme-images/logout.JPG)
