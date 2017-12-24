# ES02
 Prerequisite : Java 8 and Later version 

### Start ElasticSearch
1) Download elasticsearch from [here](https://www.elastic.co/downloads/elasticsearch)   
2) Extract downloaded elasticsearch     
3) cd elasticsearch-6.1.1       
4) $ bin/elasticsearch     

### Insert data into elasticsearch
     $ curl -XPOST localhost:9200/test/tweet   -d '{"name":"Satendra", "job":"consultant" , "location":"India", "age":28}'
     $ curl -XPOST localhost:9200/test/tweet   -d '{"name":"Rohit", "job":"consultant" , "location":"India", "age":22}'
     $ curl -XPOST localhost:9200/test/tweet   -d '{"name":"Mohit", "job":"consultant" , "location":"India", "age":25}'
 

### Run project 
    $ mvn package
    $ mvn exec:java -Dexec.mainClass="com.company.app.Main"

