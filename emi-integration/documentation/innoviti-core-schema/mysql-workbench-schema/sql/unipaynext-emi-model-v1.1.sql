-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`manufacturers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`manufacturers` (
  `inno_manufacturer_code` VARCHAR(20) NOT NULL,
  `bajaj_manufacturer_code` VARCHAR(10) NULL,
  `manufacturer_desc` VARCHAR(150) NOT NULL,
  `manufacturer_display_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`inno_manufacturer_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`products` (
  `inno_product_type_code` VARCHAR(20) NOT NULL,
  `bajaj_product_type_code` VARCHAR(10) NULL,
  PRIMARY KEY (`inno_product_type_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`categories` (
  `inno_category_code` VARCHAR(20) NOT NULL,
  `bajaj_category_code` VARCHAR(10) NULL,
  `category_desc` VARCHAR(50) NOT NULL,
  `category_display_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`inno_category_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`models`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`models` (
  `inno_model_code` VARCHAR(20) NOT NULL,
  `bajaj_model_code` VARCHAR(10) NULL,
  `bajaj_model_no` VARCHAR(150) NULL,
  `inno_product_type_code` VARCHAR(10) NULL,
  `model_display_no` VARCHAR(50) NOT NULL,
  `inno_category_code` VARCHAR(10) NOT NULL,
  `inno_manufacture_code` VARCHAR(10) NOT NULL,
  `bajaj_selling_price` VARCHAR(12) NULL,
  `bajaj_model_expiry_date` DATE NULL,
  `inno_min_selling_price` VARCHAR(12) NULL,
  `inno_max_selling_price` VARCHAR(12) NULL,
  PRIMARY KEY (`inno_model_code`),
  INDEX `fk_models_1_idx` (`inno_product_type_code` ASC),
  INDEX `fk_models_2_idx` (`inno_category_code` ASC),
  INDEX `fk_models_3_idx` (`inno_manufacture_code` ASC),
  CONSTRAINT `fk_models_1`
    FOREIGN KEY (`inno_product_type_code`)
    REFERENCES `mydb`.`products` (`inno_product_type_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_models_2`
    FOREIGN KEY (`inno_category_code`)
    REFERENCES `mydb`.`categories` (`inno_category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_models_3`
    FOREIGN KEY (`inno_manufacture_code`)
    REFERENCES `mydb`.`manufacturers` (`inno_manufacturer_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`serial_numbers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`serial_numbers` (
  `inno_model_serial_number` VARCHAR(20) NOT NULL,
  `inno_model_code` VARCHAR(10) NOT NULL,
  `manufacturer_serial_number` VARCHAR(50) NOT NULL,
  `emi_status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`inno_model_serial_number`, `manufacturer_serial_number`, `inno_model_code`),
  INDEX `fk_serial_numbers_1_idx` (`inno_model_code` ASC),
  CONSTRAINT `fk_serial_numbers_1`
    FOREIGN KEY (`inno_model_code`)
    REFERENCES `mydb`.`models` (`inno_model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`issuer_banks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`issuer_banks` (
  `inno_issuer_bank_code` VARCHAR(4) NOT NULL,
  `issuer_bank_display_name` VARCHAR(50) NOT NULL,
  `issuer_bank_desc` VARCHAR(50) NOT NULL,
  `issuer_bank_disclaimer` VARCHAR(100) NULL,
  `issuer_terms_conditions` BLOB NULL,
  `issuer_default_cashback_flag` TINYINT(1) NOT NULL,
  `issuer_min_emi_amount` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`inno_issuer_bank_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`emi_tenures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`emi_tenures` (
  `inno_tenure_code` VARCHAR(4) NOT NULL,
  `tenure_month` VARCHAR(4) NOT NULL,
  `tenure_display_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`inno_tenure_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`schemes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`schemes` (
  `inno_issuer_scheme_code` VARCHAR(20) NOT NULL,
  `bajaj_issuer_scheme_code` VARCHAR(10) NULL,
  `scheme_display_name` VARCHAR(50) NOT NULL,
  `inno_issuer_bank_code` VARCHAR(10) NOT NULL,
  `inno_tenure_code` BIGINT(4) NOT NULL,
  `processing_fees` VARCHAR(10) NULL,
  `advance_emi` VARCHAR(6) NULL,
  `brand_subvention` VARCHAR(10) NULL,
  `merchant_subvention` VARCHAR(10) NULL,
  `bank_subvention` VARCHAR(10) NULL,
  `innoviti_subvention` VARCHAR(10) NULL,
  `roi` VARCHAR(10) NULL,
  `scheme_start_date` DATE NULL,
  `scheme_end_date` DATE NULL,
  `max_amount` VARCHAR(12) NULL,
  `min_amount` VARCHAR(12) NULL,
  `gen_scheme` VARCHAR(3) NULL,
  `cashback_flag` VARCHAR(1) NOT NULL,
  `issuer_internal_tid_mapping` VARCHAR(20) NULL,
  PRIMARY KEY (`inno_issuer_scheme_code`),
  INDEX `fk_schemes_1_idx` (`inno_issuer_bank_code` ASC),
  CONSTRAINT `fk_schemes_1`
    FOREIGN KEY (`inno_issuer_bank_code`)
    REFERENCES `mydb`.`issuer_banks` (`inno_issuer_bank_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`scheme_model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`scheme_model` (
  `inno_scheme_model_code` VARCHAR(85) NOT NULL,
  `inno_issuer_scheme_code` VARCHAR(20) NOT NULL,
  `inno_model_code` VARCHAR(20) NOT NULL,
  `inno_manufacturer_code` VARCHAR(20) NOT NULL,
  `inno_category_code` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`inno_scheme_model_code`),
  INDEX `fk_scheme_model_1_idx` (`inno_issuer_scheme_code` ASC),
  INDEX `fk_scheme_model_2_idx` (`inno_model_code` ASC),
  INDEX `fk_scheme_model_3_idx` (`inno_manufacturer_code` ASC),
  INDEX `fk_scheme_model_4_idx` (`inno_category_code` ASC),
  CONSTRAINT `fk_scheme_model_1`
    FOREIGN KEY (`inno_issuer_scheme_code`)
    REFERENCES `mydb`.`schemes` (`inno_issuer_scheme_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_2`
    FOREIGN KEY (`inno_model_code`)
    REFERENCES `mydb`.`models` (`inno_model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_3`
    FOREIGN KEY (`inno_manufacturer_code`)
    REFERENCES `mydb`.`manufacturers` (`inno_manufacturer_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_4`
    FOREIGN KEY (`inno_category_code`)
    REFERENCES `mydb`.`categories` (`inno_category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`scheme_model_utid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`scheme_model_utid` (
  `utid` VARCHAR(12) NOT NULL,
  `inno_scheme_model_code` VARCHAR(85) NOT NULL,
  `acq_btid` VARCHAR(10) NULL,
  PRIMARY KEY (`utid`, `inno_scheme_model_code`),
  INDEX `fk_scheme_model_utid_1_idx` (`inno_scheme_model_code` ASC),
  CONSTRAINT `fk_scheme_model_utid_1`
    FOREIGN KEY (`inno_scheme_model_code`)
    REFERENCES `mydb`.`scheme_model` (`inno_scheme_model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
