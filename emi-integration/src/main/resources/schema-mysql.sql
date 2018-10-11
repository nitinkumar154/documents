
/* BATCH TABLES*/
/*
 * NOTE : Sequence of table is important
 * */
CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME NOT NULL,
	START_TIME DATETIME DEFAULT NULL ,
	END_TIME DATETIME DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR(250) ,
	DATE_VAL DATETIME DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME DATETIME NOT NULL ,
	END_TIME DATETIME DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE IF NOT EXISTS BATCH_JOB_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);

/* EMI CORE TABLES */
create table if not exists asset_category_bfl (
        record_update_date datetime not null,
        catgid integer not null,
        cibil_check varchar(1) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        catgdesc varchar(35) not null,
        digital_flag varchar(1) not null,
        is_record_active bit not null,
        risk_classification varchar(1) not null,
        primary key (record_update_date, catgid)
) ENGINE=InnoDB;

create table if not exists branch_bfl (
        branch_code varchar(20) not null,
        record_update_date datetime not null,
        bank_code varchar(5) not null,
        branch_addr_1 varchar(32) not null,
        branch_addr_2 varchar(32) not null,
        branch_addr_3 varchar(32) not null,
        branch_addr_4 varchar(32) not null,
        branch_area_code varchar(2) not null,
        branch_city varchar(24) not null,
        branch_contact_person_name varchar(32) not null,
        branch_email_id varchar(50) not null,
        branch_name varchar(30) not null,
        branch_phone_number varchar(12) not null,
        branch_pin varchar(6) not null,
        branch_region_code varchar(3) not null,
        branch_state varchar(15) not null,
        branch_state_code varchar(5) not null,
        branch_type varchar(4) not null,
        branch_zone_code varchar(2) not null,
        corporate_id varchar(15) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        main_branch varchar(6) not null,
        main_branch_flag varchar(1) not null,
        primary key (branch_code, record_update_date)
) ENGINE=InnoDB;

create table if not exists categories (
        record_update_timestamp datetime not null,
        category_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        bajaj_category_code varchar(10),
        category_description varchar(50) not null,
        category_display_name varchar(50) not null,
        primary key (record_update_timestamp, category_code)
) ENGINE=InnoDB;



create table if not exists city_bfl (
        cityid varchar(20) not null,
        record_update_timestamp datetime not null,
        city_category varchar(3),
        cityname varchar(50) not null,
        citytype varchar(105),
        citytypeid integer,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        risk_plloc varchar(105),
        stateid integer not null,
        primary key (cityid, record_update_timestamp)
) ENGINE=InnoDB;

create table if not exists dealer_bfl (
        record_update_date datetime not null,
        supplierid integer not null,
        address1 varchar(120),
        address2 varchar(120),
        address3 varchar(120),
        address4 varchar(120),
        asset_catg_id integer not null,
        city varchar(105),
        classification varchar(30),
        co_brand_card_code varchar(2) not null,
        co_brand_limit_flag varchar(1) not null,
        contact_person varchar(100),
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        dealer_email varchar(105),
        dealer_grp_desc varchar(105),
        dealer_grp_id integer,
        is_record_active bit not null,
        loyality_prog_applicable varchar(3),
        mobile varchar(105),
        pan varchar(30),
        phone1 varchar(48),
        preferred_limit_flag varchar(1) not null,
        process_type varchar(20) not null,
        serving_cities text not null,
        state varchar(20) not null,
        stdisd varchar(48),
        store_id varchar(10) not null,
        supplier_branch varchar(10),
        supplier_dealer_flag varchar(1),
        supplierdesc varchar(100) not null,
        supplier_type varchar(5),
        zip_code integer,
        primary key (record_update_date, supplierid)
) ENGINE=InnoDB;

create table if not exists dealer_manufacturer_bfl (
        record_update_date datetime not null,
        manufacturer_id integer not null,
        supplierid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        primary key (record_update_date, manufacturer_id, supplierid)
) ENGINE=InnoDB;

create table if not exists dealer_product_bfl (
        code varchar(8) not null,
        record_update_date datetime not null,
        supplierid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        primary key (code, record_update_date, supplierid)
) ENGINE=InnoDB;

create table if not exists emi_tenures (
        record_update_timestamp datetime not null,
        emi_tenure_code varchar(5) not null,
       record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        emi_tenure_display_name varchar(50) not null,
        emi_tenure_months varchar(4),
        primary key (record_update_timestamp, emi_tenure_code)
) ENGINE=InnoDB;

create table if not exists hibernate_sequence (
        next_val bigint
) ENGINE=InnoDB;

insert into hibernate_sequence (next_val) select * from (select 1 as next_val) as hib where not exists(select * from hibernate_sequence);

create table if not exists issuer_banks (
        record_update_timestamp datetime not null,
        issuer_bank_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        emi_bank_code INT(5) not null,
        issuer_bank_description varchar(50) not null,
        issuer_bank_disclaimer varchar(100),
        issuer_bank_display_name varchar(50) not null,
        issuer_default_cashback_flag varchar(5),
        issuer_min_emi_amount decimal(10,2),
        issuer_terms_conditions LONGTEXT,
        primary key (record_update_timestamp, issuer_bank_code)
) ENGINE=InnoDB;
   
create table if not exists issuer_schemes (
        record_update_timestamp datetime not null,
        issuer_scheme_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        advance_emi varchar(6),
        bajaj_issuer_scheme_code varchar(10),
        bank_subvention varchar(10),
        brand_subvention varchar(10),
        issuer_cashback_flag varchar(5) not null,
        general_scheme varchar(3),
        innoviti_subvention varchar(10),
        issuer_scheme_description varchar(45),
        max_amount decimal(18,2),
        merchant_subvention varchar(10),
        min_amount decimal(18,2),
        issuer_scheme_processing_fees varchar(10),
        issuer_rate_of_interest varchar(10),
        issuer_scheme_display_name varchar(50) not null,
        scheme_end_date datetime,
        scheme_start_date datetime,
        issuerBank_record_update_timestamp datetime not null,
        issuer_bank_code varchar(20) not null,
        tenure_record_update_timestamp datetime not null,
        emi_tenure_code varchar(5) not null,
        primary key (record_update_timestamp, issuer_scheme_code),
		KEY `FKl9w9k6veb92lcuvd4jclr2iyw` (`issuerBank_record_update_timestamp`,`issuer_bank_code`),
		KEY `FKjg17vmqkwkw7n8a1odrhti2ir` (`tenure_record_update_timestamp`,`emi_tenure_code`),
		CONSTRAINT `FKjg17vmqkwkw7n8a1odrhti2ir` FOREIGN KEY (`tenure_record_update_timestamp`, `emi_tenure_code`) 
		REFERENCES `emi_tenures` (`record_update_timestamp`, `emi_tenure_code`),
		CONSTRAINT `FKl9w9k6veb92lcuvd4jclr2iyw` FOREIGN KEY (`issuerBank_record_update_timestamp`, `issuer_bank_code`) 
		REFERENCES `issuer_banks` (`record_update_timestamp`, `issuer_bank_code`)
) ENGINE=InnoDB;
    
create table if not exists manufacturer_bfl (
        record_update_date datetime not null,
        manufacture_id integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        manufacture_desc varchar(150) not null,
       primary key (record_update_date, manufacture_id)
) ENGINE=InnoDB;

create table if not exists manufacturers (
        record_update_timestamp datetime not null,
        manufacturer_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        bajaj_manufacturer_code varchar(10),
        manufacturer_description varchar(150) not null,
        manufacturer_display_name varchar(50) not null,
        primary key (record_update_timestamp, manufacturer_code)
) ENGINE=InnoDB;

create table if not exists model_master_bfl (
        record_update_date datetime not null,
        modelid integer not null,
        categoryid integer not null,
       record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        make varchar(35) not null,
       manufacturerid integer not null,
        model_expiry_date date not null,
        modelno varchar(150) not null,
        product_code varchar(3),
        selling_price decimal(18,4) not null,
        size_id varchar(20) not null,
        primary key (record_update_date, modelid)
) ENGINE=InnoDB;

create table if not exists model_product_bfl (
        code varchar(8) not null,
        record_update_date datetime not null,
        modelid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        primary key (code, record_update_date, modelid)
) ENGINE=InnoDB;

create table if not exists products (
        record_update_timestamp datetime not null,
        product_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
      is_record_active bit not null,
        bajaj_product_type_code varchar(10),
        product_type_code varchar(20) not null,
        issuer_product_display_name varchar(50),
        primary key (record_update_timestamp, product_code)
) ENGINE=InnoDB;
  
create table if not exists models (
        record_update_timestamp datetime not null,
        model_code varchar(20) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        bajaj_model_code varchar(10),
        bajaj_model_expiry_date date,
        bajaj_model_number varchar(150),
        bajaj_model_selling_price decimal(10,2),
        model_max_selling_price decimal(10,2),
        model_min_selling_price decimal(10,2),
        model_display_number varchar(50) not null,
        category_record_update_timestamp datetime not null,
        category_code varchar(20) not null,
        manufacturer_record_update_timestamp datetime not null,
        manufacturer_code varchar(20) not null,
        product_record_update_timestamp datetime not null,
        product_code varchar(20) not null,
        primary key (record_update_timestamp, model_code),
		KEY `FKhbca1j819vsrdjdjgrnuodfut` (`category_record_update_timestamp`,`category_code`),
		KEY `FKpn2jjehx7bybs6rwox73684cs` (`manufacturer_record_update_timestamp`,`manufacturer_code`),
		KEY `FK2bgqt3o8kp026mri2881ugd8m` (`product_record_update_timestamp`,`product_code`),
		CONSTRAINT `FK2bgqt3o8kp026mri2881ugd8m` FOREIGN KEY (`product_record_update_timestamp`, `product_code`) 
		REFERENCES `products` (`record_update_timestamp`, `product_code`),
		CONSTRAINT `FKhbca1j819vsrdjdjgrnuodfut` FOREIGN KEY (`category_record_update_timestamp`, `category_code`) 
		REFERENCES `categories` (`record_update_timestamp`,`category_code`),
		CONSTRAINT `FKpn2jjehx7bybs6rwox73684cs` FOREIGN KEY (`manufacturer_record_update_timestamp`, `manufacturer_code`) 
		REFERENCES `manufacturers` (`record_update_timestamp`, `manufacturer_code`)
) ENGINE=InnoDB;


create table if not exists model_serial_numbers (
        record_update_timestamp datetime not null,
        model_serial_number varchar(20) not null,
        manufacturer_model_serial_number varchar(50) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        is_emi_used bit not null,
        model_record_update_timestamp datetime not null,
        model_code varchar(20) not null,
        primary key (record_update_timestamp, model_serial_number),
		KEY `FKbrbwee5kdlgaprq1xe1cvnepw` (`model_record_update_timestamp`,`model_code`),
		CONSTRAINT `FKbrbwee5kdlgaprq1xe1cvnepw` FOREIGN KEY (`model_record_update_timestamp`, `model_code`) 
		REFERENCES `models` (`record_update_timestamp`, `model_code`)
) ENGINE=InnoDB;
    
  
create table if not exists objectstore_lookup (
        id integer not null,
        created_date datetime not null,
        file_name varchar(255) not null,
        file_type varchar(255),
        is_processed VARCHAR(1) default 'N',
        file_key varchar(255) not null,
       last_modified varchar(255),
        md5 varchar(255) not null,
        user_type varchar(255),
        primary key (id),
		UNIQUE KEY `UK_f7xg9c9bxj17ibrv0mrvcrdh7` (`file_name`),
		UNIQUE KEY `UK_3ex2m7jiu8gyl1qrt3lkju9u8` (`file_key`),
		UNIQUE KEY `UK_hub4ih9i123egocvdb93plmdd` (`md5`)
) ENGINE=InnoDB;

    
create table if not exists scheme_bfl (
        record_update_date datetime not null,
        schemeid integer not null,
        advance_emi varchar(6) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        dealer_mapping varchar(1) not null,
        dbd varchar(43) not null,
        gen_sch varchar(3) not null,
        intrate double precision,
        is_record_active bit not null,
        mbd varchar(43) not null,
        maxamount decimal(18,0) not null,
        minamount decimal(18,0) not null,
        model_mapping varchar(1) not null,
        portal_description varchar(200) not null,
        processing_fees varchar(43),
        product varchar(50) not null,
        schemedesc varchar(200) not null,
        scheme_expiry_date date not null,
        scheme_start_date date not null,
        spl_sch varchar(1) not null,
        tenure varchar(5) not null,
        primary key (record_update_date, schemeid)
) ENGINE=InnoDB;

create table if not exists scheme_branch_bfl (
        branchid INT(8) NOT NULL COMMENT ' Refers to DimBranch' not null,
        record_update_date datetime not null,
        schemeid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        primary key (branchid, record_update_date, schemeid)
) ENGINE=InnoDB;

create table if not exists scheme_dealer_bfl (
        record_update_date datetime not null,
        scheme_id integer not null,
        supplier_id integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
       primary key (record_update_date, scheme_id, supplier_id)
) ENGINE=InnoDB;

create table if not exists scheme_model_bfl (
        categoryid varchar(20) not null,
        record_update_date datetime not null,
        manufacturerid varchar(8) not null,
        modelid varchar(8) not null,
        schemeid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        primary key (categoryid, record_update_date, manufacturerid, modelid, schemeid)
) ENGINE=InnoDB;

create table if not exists sequence_generator (
        seq_name varchar(255) not null,
        seq_value bigint not null,
        primary key (seq_name)
) ENGINE=InnoDB;

create table if not exists data_move_keeper (
        table_name varchar(255) not null,
        time_stamp datetime null,
        primary key (table_name)
) ENGINE=InnoDB;

create table if not exists state_bfl (
        record_update_date datetime not null,
        stateid integer not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        statedesc varchar(105) not null,
        primary key (record_update_date, stateid)
) ENGINE=InnoDB;
    
create table if not exists issuer_scheme_model (
        record_update_timestamp datetime not null,
        issuer_scheme_model_code varchar(85) not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        model_record_update_timestamp datetime not null,
        model_code varchar(20) not null,
        scheme_record_update_timestamp datetime not null,
        issuer_scheme_code varchar(20) not null,
        primary key (record_update_timestamp, issuer_scheme_model_code),
		KEY `FKt7d4spt7e4xppbt70boeh9ixe` (`model_record_update_timestamp`,`model_code`),
		KEY `FK9pgrlu4l3q030y5588u3y5wwp` (`scheme_record_update_timestamp`,`issuer_scheme_code`),
		CONSTRAINT `FK9pgrlu4l3q030y5588u3y5wwp` FOREIGN KEY (`scheme_record_update_timestamp`, `issuer_scheme_code`) 
		REFERENCES `issuer_schemes` (`record_update_timestamp`, `issuer_scheme_code`),
		CONSTRAINT `FKt7d4spt7e4xppbt70boeh9ixe` FOREIGN KEY (`model_record_update_timestamp`, `model_code`) 
		REFERENCES `models` (`record_update_timestamp`, `model_code`)
) ENGINE=InnoDB;
   
  
create table if not exists issuer_scheme_model_terminal (
        record_update_timestamp datetime not null,
        utid varchar(12) NOT NULL not null,
        record_update_reason varchar(60) not null,
        record_update_status char(1) not null,
        record_update_user varchar(32) not null,
        is_record_active bit not null,
        bajaj_product_type_code varchar(10),
        dealer_id varchar(10),
        issuer_custom_field varchar(10) NOT NULL,
        issuer_scheme_terminal_sync_status varchar(255) not null,
        issuer_scheme_onus_offus varchar(255),
        fk_scheme_model_date datetime not null,
        issuer_scheme_model_code varchar(85) not null,
        primary key (record_update_timestamp, fk_scheme_model_date, issuer_scheme_model_code, utid),
		KEY `FK7cjwaev45eismf18nhjadivdj` (`fk_scheme_model_date`,`issuer_scheme_model_code`),
		CONSTRAINT `FK7cjwaev45eismf18nhjadivdj` FOREIGN KEY (`fk_scheme_model_date`,`issuer_scheme_model_code`) 
		REFERENCES `issuer_scheme_model` (`record_update_timestamp`, `issuer_scheme_model_code`)
) ENGINE=InnoDB;