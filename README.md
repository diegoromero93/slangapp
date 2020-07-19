# SlangApp Code Challenge

:wave: Hello Slang Evaluator :)

This is a small project por the code challenge that you sent to me

## Before we start
For generating disctractors I'm using phonemes, In my research I found some common mistakes that people make and are related to minimal pairs 
([See here](https://www.englishclub.com/pronunciation/minimal-pairs.htm),  [and here](https://allesl.com/minimal-pairs-list-examples/), [and here](https://glossary.sil.org/term/phonetically-similar-segment#:~:text=Phonetically%20similar%20segments%20are%20two,one%20or%20two%20articulatory%20features.)). So 
I need the dictionary file to get the actual word phoneme and then I've created a table where I've mapped the common mistakes. 
<br/>
For example:


| This phoneme | gets confused with | Example|
| :---: | :---: | :---: |
| /ɪ/ | /i:/ | <b>sit</b> and <b>seat</b>   |
|  /f/ | /v/| <b>fan</b> and <b>van</b>    |
| /b/  | /p/ | <b>buy</b> and <b>pie</b>    |  |

An so on...<br/>

## Prerequisites
+ Postgres database
+ AWS Account with API credentials, I'm using Amazon Polly TTS for generating Audio file and I'm saving them on a S3 (If you want to test it locally I can share mine)
+ A Dictionary file that is on src/main/resources/static/ (This is used to get the word phoneme) this can be later changed for a API than returns phoneme


## Installation

For AWS config complete the following information on the <b>application.properties</b> file

```bash
aws.api.accessKey=
aws.api.secretKey=
aws.s3.url=
aws.s3.bucketName=
```
For Postgres db config complete the following information on the <b>application.properties</b> file

```bash
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
For creating the db and generating initial data uncomment the following props
```bash
#spring.jpa.generate-ddl=true
#spring.jpa.hibernate.populate=true
#spring.jpa.hibernate.ddl-auto=create
```

For running this app you would have to clone the repository and make the following steps

+ Clone or download the project
+ Install Dependencies with Maven 
```bash
mvn clean install
```
+ Import project to IDE
+ :white_check_mark: And enjoy testing 
```bash
mvn spring-boot:run
```

## Live Demo and Doc
+ API doc can be find here [Link to docs](http://slangapp.us-east-2.elasticbeanstalk.com/swagger-ui.html)
+ Live demo can be finde here + API doc can be find here [Link to demo](http://slangapp.us-east-2.elasticbeanstalk.com/api/v1/word)