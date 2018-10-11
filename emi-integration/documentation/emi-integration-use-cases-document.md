### 1. deployment - FTP, File Object Store, emi-integration, portal

    1. FTP
        get ftp_server.py from location mentioned in resources section.
        cp ftp_server.py to {remote_server}
        install ftp python library using "pip install pyftpdlib"
        configure ftp_server.py file for directory, user, ip and password
        run python file using python ftp_server.py
        check ftp server status by doing a telnet to configured ip and port

    2. Object Store docs
        Read full guide as provided in link at the end page

    3. emi-integration
        Run "gradle zip" in local machine
        copy {project_dir}/build/distributions/emi-integration-0.0.1.zip to remote machine
        unzip using command "unzip -o emi-integration-0.0.1.zip" in remote machine
        change config.properties for configurations
        run jar using "sudo nohup /home/deploy/install/jdk1.8.0_73/bin/java -jar
            /home/deploy/unipaynext/emi-integration-service/emi-integration-0.0.1.jar &"

### 2. copy bajaj files from FTP to object store - no files in FTP

    1. Copy each file mentioned in bajaj master document to configured directory in FTP server.
    2. Check log file of emi-integration-service if it has synchronized each files.
    3. Check object-store-service log for each file's upload entry.
    4. Check mongo db for the entry and validate key with returned key from service.
    6. Check physical directory for file stored as md5 of the given file.

### 3. copy bajaj files from FTP to object store - with files # 1 - # 7

    1. Copy first seven files mentioned in bajaj master document to configured directory in FTP server.
    2. Check log file of emi-integration-service if it has synchronized each files.
    3. Check object-store-service log for each file's upload entry.
    4. Check mongo db for the entry and validate key with returned key from service.
    6. Check physical directory for file stored as md5 of the given file.

### 4. copy bajaj files from FTP to object store - with files # 8 - # 14

    1. Copy last seven files mentioned in bajaj master document to configured directory in FTP server.
    2. Check log file of emi-integration-service if it has synchronized each files.
    3. Check object-store-service log for each file's upload entry.
    4. Check mongo db for the entry and validate key with returned key from service.
    6. Check physical directory for file stored as md5 of the given file.

### 5. delete files # 1 - # 4, rename files # 5 - # 10, copy all from FTP to file object store

    1. Delete files # 1 - # 4 from ftp server and verify that ftp module in emi-integration service does not pick up those files.
    2. Renaming # 5 - # 10 from ftp server, ftp module uploads the changed file to object store
    3. Changing the content of files with same name in ftp server also trigger a upload to object store service.

#### Resources
* [object-store-deployment wiki](http://192.168.0.21/platform/object-store-service)
* [ftp-server-location](http://192.168.0.21/platform/integration-services/blob/master/ftp_server.py)


### Steps to store Bajaj Files in DB

  1. Run below the script  
     ###### batch-schema-test.sql

  2. Run the below URL to store the keys of Bajaj File in emi-integration

  Request: GET

    http://localhost:5454/emi-integration/jobs/getList

  3. Run the below URL to store the Bajaj Flat File data into DB.

  Request: POST

  http://localhost:5454/emi-integration/jobs/master/launch

### Steps to store Core Data from Master Data

### Steps to generate and download csv file

  1. Use the below url to generate csv file after job is finished.
  
    HTTP method : POST
    
    URL         : https://localhost:5454/emi-integration-api/jobs/csv/launch/{utid}
    
  2. Use the below URL to see the list of generated file with date
  
    HTTP method : GET
    
    URL         : https://localhost:5454/emi-integration-api/jobs/mapping/file/list

  3. Use the below URL to download generated CSV.
  
    HTTP method : GET
    
    URL         : https://localhost:5454/emi-integration-api/jobs/download/file/{fileName}
  