1) Run `mvn clean install` on the `uniPAYNEXT_Portal` project.  
2) Move the `uniPAYNEXT_Portal.war` file generated to the tomcat webapp location.  
3) Extract the war file inside the webapp.  
4) Edit the properties file respectively, 

        - database.properties - portal_staging, portal_configuration , portal_mis database related information  
        - transactionListJobAlert.properties - Reports, Notifications schedules related information  
        - terminal_connection.properties - GPRS, PC-LAN and others connectivity related information  
        - ipConfigSettlement.properties - Manual Settlement jar related information  
        - mcdDetails.properties - SFTP details for McDonalds transactions reporting
        - sample_files_location.properties - File path for Store/BTID configuration
        
5) Install the node.js and bower components  

        - yum install -y gcc-c++ make  
        - curl -sL https://rpm.nodesource.com/setup_6.x | sudo -E bash -  
        - yum install nodejs  
        - npm install -g bower  
6) Run the bower install command inside the project resources.  
7) Start the tomcat server.