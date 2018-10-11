| SN | md5sum | File Name |
|---|---|---|
| 1 | 09bf33fd1a717679570197d930ce6c83 | ASTM-00059-080316-165037.txt |
| 2 | 2d45f0e9cd22cf98033b9504bc8488d4 | BRNM-00059-070316-193637.TXT |
| 3 | 38c6ec0bc03fd7d49c306d4139983c4a | CITM-00059-070316-193637.TXT |
| 4 | 53f255159003e1d14366348836ceb3e5 | DLRM-00059-080316-194548.txt |
| 5 | ab81e970ef201f9ec67195dd24056069 | DMFM-00059-150617-193638.txt |
| 6 | 551695779d9d3400d4a93c5d7343e1f8 | DPRM-00059-150617-193649.txt |
| 7 | df84f89af52cb60872a3c0b6b7658fdb | MDLM-00059-070316-193636.txt |
| 8 | e141bc236d4865a97f32208ea9ad64ce | MDPM-00059-070316-193650.txt |
| 9 | cbb46a26559a16cd15602b43ed2d13b5 | MNFM-00059-070316-193645.txt |
| 10 | 6b4aa60ceca36eacf02c0f0b30bf4fba | SCHM-00059-150617-114934.txt |
| 11 | 4688d8b473b6ed5fe0daaa4a00a4a43e | SHBM-00059-070316-193649.txt |
| 12 | 2ff30cbb843809b2ac472e69870bdcfe | SHDM-00059-150617-193643.txt |
| 13 | ebc8a2e143b2e224b63cb71e11ea1aeb | SHMM-00059-150617--193646.txt |
| 14 | 02ccb969b7960a48ff7236de4bd486a9 | STAM-00059-070316-193636.TXT |

| SN | Use Case | Dev Status | Test Status | Documentation | jMeter Script | Remarks | Owner |
|---|---|---|---|---|---|---|---|
| 1 | deployment - FTP, File Object Store, emi-integration, portal | Complete | Complete | [Usecase document] | | Portal Deployment complete with instructions [Portal deployment instructions] | Suryakanta/Anand |
| 2 | copy bajaj files from FTP to object store - no files in FTP | Complete | Complete | [Usecase document] | | | Suryakanta |
| 3 | copy bajaj files from FTP to object store - with files #1 - #7 | Complete | Complete | [Usecase document] | | | Suryakanta |
| 4 | copy bajaj files from FTP to object store - with files #8 - #14 | Complete | Complete | [Usecase document] | | | Suryakanta |
| 5 | delete files #1 - #4, rename files #5 - #10, copy all from FTP to file object store | Complete | Complete | [Usecase document] | | | Suryakanta |
| 6 | for each UC #1 - #5, ingest files from object store to bajaj tables, and create response files(original and renamed files) - empty Response file if no error |Completed |Completed | | |Job has to be integrated  with the Storing of the Masters data in DB | Amal,Tariq|
| 7 | for UC #6, translate and copy data from bajaj master tables to innoviti master tables |InProgress | | | | Manufacturer,Model, Scheme,AssestCategory|Amal,Jasmine,Vinayak |
| 8 | configure a new merchant, chain, store, 10 UTIDs with regular acquirers | | | | | | Aditya|
| 9 | Create a sales request in SFA for the account(merchant in UC #8) for new service(BFL EMI) with QTY=10(# of UTIDs) | | | | | | |
| 10 | Generate and share the BTID procurement request files(for 10 UTIDs) from osTicket in FTP | | | | | | |
| 11 | Create a BTID procurement response file with BTID details for 5 UTIDs(out of 10) | | | | | | |
| 12 | Add BFL BTID for respective UTIDs - through add BTID flow(using portal API) from osTicket | | | | | | |
| 13 | As a result of UC #7, view scheme-model and scheme-model-terminal mapping[*] in portal | | | | | sequence this UC appropriately | |
| 14 | repeate UC #8 | | | | | | |
| 15 | create new manufacturer, product type, model, category, model serial number from portal UI | | | | |Done(testing & fixing some issues) |Aditya |
| 16 | create issuer bank, emi tenures, issuer schemes  from portal UI | | | | | | Aditya/Amith |
| 17 | create scheme model mapping from portal UI | | | | |In progress(testing) | Aditya|
| 18 | create scheme model terminal(UTID) mapping for UTIDs created in UC #14 from portal UI | | | | || |
| 19 | create scheme model terminal mapping from portal UI for - Invoice Financing | | | | | | |
| 20 | create scheme model terminal mapping for brand EMI | | | | | | |
| 21 | For every UC ending with scheme mapping, copy data from staging to configuration for jPOS consumption - validate configuration that is copied | | | | | | |
| 22 | replicate(app level[?]) configuration data from non-PCI to PCI environment - validate configuration data that got replicated | | | | | | |
| 23 | after jPOS sends scheme details to terminal, update scheme sync status in configuration DB - validate all three - NOT_SENT, SENT ACKD status - using portal API | | | | | | |
| 24 | handle invalidate scheme model mapping UC  | | | | | | |

[Usecase document]: <http://192.168.0.21/platform/emi-integration/blob/master/documentation/emi-integration-use-cases-document.md>
[Portal deployment instructions]: <http://192.168.0.21/platform/emi-integration/blob/master/documentation/portal_deployment_instructions.md>