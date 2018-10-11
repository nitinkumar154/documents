# User Manual For EMI Integration


emi-integration service
======
emi-integration service is a configuration store which synchronizes it's store with latest emi scheme files from bajaj ftp server and then serves incoming requests with latest emi schemes. It has the following features

Integration module which syncs Bajaj FTP server and object-store-service
Ingestion module to process latest schemes from files
Serve requests asking for updated schemes
Installation Guide
======
emi-integration service uses following components and services to work properly.

* FTP server - to store all bajaj master files
* MongoDb server - database to store all object metadata of object-store-service
* Object-store service - to handle file upload and download
* MySQL server - database for emi-integration service
* Emi-integration service - emi-integration service deployment

FTP server installation
=======
FTP server installation is needed only in dev and test environments for simulating bajaj server. Production environment configuration should point to bajaj FTP server.

* Get ftp_server.py from integration-service repo.
* Copy above file to remote ftp server.
* Install ftp python library using pip install pyftpdlib.
* Configure ftp_server.py file for directory, user, ip and password.
* Run python file using nohup python ftp_server.py &.
* Check ftp server status by doing a telnet to configured ip and port.

MongoDB installation
======
Import the public key used by the package management system sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6

* Create the /etc/apt/sources.list.d/mongodb-org-3.4.list list file using the command appropriate for your version of Ubuntu:
```bash
Ubuntu 12.04:  echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu precise/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
Ubuntu 14.04:  echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
Ubuntu 16.04:  echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
Reload local package database. sudo apt-get update
```

* Install the MongoDB packages. 
```bash
sudo apt-get install -y mongodb-org
```

* Start Mongo Db:
```bash
sudo service mongod start
```

Object-store service installation
======

* Clean and Build object-store-service setup.

```bash
cd {project_root_directory}
gradle clean
gradle zip
```
* Get the distributions file

```bash
cd {project_root_directory}/build/distributions/
cp object-store-service-0.0.1.zip {remote_server_path}

```
* Deploy and run object store jar in remote
```bash
unzip object-store-service-0.0.1.zip
nohup java -jar object-store-service-0.0.1.jar &
```

Emi-integration Deployment
======
[EMI_INTEGRATION DEPLOYMENT GUIDE](http://192.168.0.21/platform/emi-integration/blob/master/documentation/emi_integration_deployment_instruction.md)

MySQL Server installation
======

* Install mysql-server in remote.

* Create database emi-integration.

EMI Integration & Object Store Data Sync
======
* To get the metadata from Object Store Service

> Request URL:http://192.168.0.40:5454/emi-integration-api/jobs/getList

> Method Type:GET

* Verify emi_integration.object_store_lookup table for all 14 entries.

* To Fill all bfl & Innoviti tables with  new Bajaj Data

> Request URL:http://192.168.0.40:5454/emi-integration-api/jobs/master/launch

> Method Type:POST

> Content-Type:JSON


> Sample Success Response
```json
{
"errorMessage": "Congrats!! you have launched your job successfully",
"code": 200
}
```