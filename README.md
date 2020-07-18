# SlangApp Code Challenge

:wave: Hello SlangApp Evaluator :)

This is a small project por the code challenge that you sent to me


## Prerequisites
+ Postgres database
+ AWS Account with API credentials created (I can share mine if you want :blush:) for Amazon Polly  and S3
+ A Dictionary file that is on src/main/resources/static/ (This is used to get a word phoneme)

## Installation
For running this app you would have to clone the repository and make the following steps

+ Clone or download the project
+ Install Dependencies with Maven (mvn clean install)
+ Import project to IDE
Note : Inside the project in the application.properties file, uncomment the following properties to generate database and initial words

```bash
spring.jpa.generate-ddl=true
spring.jpa.hibernate.populate=true
spring.jpa.hibernate.ddl-auto=create 
```

For AWS config complete the following information

```bash
aws.api.accessKey=
aws.api.secretKey=
aws.s3.url=
aws.s3.bucketName=
```