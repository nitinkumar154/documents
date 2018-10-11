#### Schema Changes
```
Date: 03 Aug, 2017
File: uniPAYNext-emi-schema-v1.1.xlsx
Remarks:
    Rajeev: 'issuer_internal_tid_mapping' column moved to 'scheme_master'
    table from 'scheme_model_utid_mapping' table.
    Note: This column is not referring Bank TID. This column will be used
    to keep unique number provided by bank for specific scheme based on
    merchant. And we require to provide same number in EMI file.
```

#### MySQL Workbench Model Files
