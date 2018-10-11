emi-integration service
=========

emi-integration service is a configuration store which synchronizes it's store 
with latest emi scheme files from bajaj ftp server and then serves incoming 
requests with latest emi schemes. It has the following features

* Integration module which syncs Bajaj FTP server and [object-store-service](http://192.168.0.21/platform/object-store-service "object-store service repo")
* Ingestion module to process latest schemes from files
* Serve requests asking for updated schemes

Deployement Guide
==========

* Clean and Build emi-integration-service setup

    
```bash
cd {project_root_directory}
gradle clean
gradle zip
```

* Get the zip file from {project folder}/build/distributions

* Create a folder with name emi-integration-service in home/deploy/deploy location of remote server.

* Deploy the zip file inside /home/deploy/deploy/emi-integration-service location in remote server.

* unzip the emi-integration-0.0.1.zip  using the below command.


```bash
unzip -o  emi-integration-0.0.1.zip
```
* Run emi-integration-service jar  from emi-integration-service folder  location using the below command.

* Edit the config.properties file  for database and portal related information.

```bash
cd /home/deploy/deploy/emi-integration-service
sudo nohup /home/deploy/install/jdk1.8.0_73/bin/java -jar /home/deploy/deploy/emi-integration-service/emi-integration-0.0.1.jar &
```