-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema emi_integration
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema emi_integration
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `emi_integration` DEFAULT CHARACTER SET utf8 ;
USE `emi_integration` ;

-- -----------------------------------------------------
-- Table `emi_integration`.`manufacturers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`manufacturers` (
  `manufacturer_code` VARCHAR(20) NOT NULL,
  `manufacturer_display_name` VARCHAR(50) NOT NULL,
  `manufacturer_description` VARCHAR(150) NOT NULL,
  `bajaj_manufacturer_code` VARCHAR(10) NULL,
  PRIMARY KEY (`manufacturer_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`products` (
  `product_type_code` VARCHAR(20) NOT NULL,
  `bajaj_product_type_code` VARCHAR(10) NULL,
  PRIMARY KEY (`product_type_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`categories` (
  `category_code` VARCHAR(20) NOT NULL,
  `category_display_name` VARCHAR(50) NOT NULL,
  `category_description` VARCHAR(50) NOT NULL,
  `bajaj_category_code` VARCHAR(10) NULL,
  PRIMARY KEY (`category_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`models`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`models` (
  `model_code` VARCHAR(20) NOT NULL,
  `manufacturer_code` VARCHAR(10) NOT NULL,
  `product_type_code` VARCHAR(10) NOT NULL,
  `category_code` VARCHAR(10) NOT NULL,
  `model_display_number` VARCHAR(50) NOT NULL,
  `model_min_selling_price` DECIMAL(10,2) NULL,
  `model_max_selling_price` DECIMAL(10,2) NULL,
  `bajaj_model_code` VARCHAR(10) NULL,
  `bajaj_model_number` VARCHAR(150) NULL,
  `bajaj_model_selling_price` DECIMAL(10,2) NULL,
  `bajaj_model_expiry_date` DATE NULL,
  PRIMARY KEY (`model_code`),
  INDEX `fk_models_1_idx` (`product_type_code` ASC),
  INDEX `fk_models_2_idx` (`category_code` ASC),
  INDEX `fk_models_3_idx` (`manufacturer_code` ASC),
  CONSTRAINT `fk_models_1`
    FOREIGN KEY (`product_type_code`)
    REFERENCES `emi_integration`.`products` (`product_type_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_models_2`
    FOREIGN KEY (`category_code`)
    REFERENCES `emi_integration`.`categories` (`category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_models_3`
    FOREIGN KEY (`manufacturer_code`)
    REFERENCES `emi_integration`.`manufacturers` (`manufacturer_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`model_serial_numbers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`model_serial_numbers` (
  `model_serial_number` VARCHAR(20) NOT NULL,
  `model_code` VARCHAR(10) NOT NULL,
  `manufacturer_model_serial_number` VARCHAR(50) NOT NULL,
  `is_emi_used` TINYINT(1) NOT NULL,
  PRIMARY KEY (`model_serial_number`, `manufacturer_model_serial_number`, `model_code`),
  INDEX `fk_serial_numbers_1_idx` (`model_code` ASC),
  CONSTRAINT `fk_serial_numbers_1`
    FOREIGN KEY (`model_code`)
    REFERENCES `emi_integration`.`models` (`model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`issuer_banks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`issuer_banks` (
  `issuer_bank_code` VARCHAR(4) NOT NULL,
  `issuer_bank_display_name` VARCHAR(50) NOT NULL,
  `issuer_bank_description` VARCHAR(50) NOT NULL,
  `issuer_bank_disclaimer` VARCHAR(100) NULL,
  `issuer_terms_conditions` BLOB NULL,
  `issuer_default_cashback_flag` TINYINT(1) NOT NULL,
  `issuer_min_emi_amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`issuer_bank_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`emi_tenures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`emi_tenures` (
  `emi_tenure_code` VARCHAR(4) NOT NULL,
  `emi_tenure_months` VARCHAR(4) NOT NULL,
  `emi_tenure_display_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`emi_tenure_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`issuer_schemes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`issuer_schemes` (
  `issuer_scheme_code` VARCHAR(20) NOT NULL,
  `issuer_bank_code` VARCHAR(10) NOT NULL,
  `emi_tenure_code` VARCHAR(4) NOT NULL,
  `issuer_scheme_display_name` VARCHAR(50) NOT NULL,
  `issuer_scheme_description` VARCHAR(45) NULL,
  `issuer_scheme_processing_fees` VARCHAR(10) NULL,
  `issuer_rate_of_interest` VARCHAR(10) NULL,
  `scheme_start_date` DATE NULL,
  `scheme_end_date` DATE NULL,
  `min_amount` DECIMAL(10,2) NULL,
  `max_amount` DECIMAL(10,2) NULL,
  `advance_emi` VARCHAR(6) NULL,
  `brand_subvention` VARCHAR(10) NULL,
  `merchant_subvention` VARCHAR(10) NULL,
  `innoviti_subvention` VARCHAR(10) NULL,
  `bank_subvention` VARCHAR(10) NULL,
  `general_scheme` VARCHAR(3) NULL,
  `issuer_cashback_flag` TINYINT(1) NOT NULL,
  `bajaj_issuer_scheme_code` VARCHAR(10) NULL,
  PRIMARY KEY (`issuer_scheme_code`),
  INDEX `fk_schemes_1_idx` (`issuer_bank_code` ASC),
  INDEX `fk_issuer_schemes_1_idx` (`emi_tenure_code` ASC),
  CONSTRAINT `fk_schemes_1`
    FOREIGN KEY (`issuer_bank_code`)
    REFERENCES `emi_integration`.`issuer_banks` (`issuer_bank_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_issuer_schemes_1`
    FOREIGN KEY (`emi_tenure_code`)
    REFERENCES `emi_integration`.`emi_tenures` (`emi_tenure_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`issuer_scheme_model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`issuer_scheme_model` (
  `issuer_scheme_model_code` VARCHAR(85) NOT NULL,
  `issuer_scheme_code` VARCHAR(20) NOT NULL,
  `manufacturer_code` VARCHAR(20) NOT NULL,
  `category_code` VARCHAR(20) NOT NULL,
  `model_code` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`issuer_scheme_model_code`),
  INDEX `fk_scheme_model_1_idx` (`issuer_scheme_code` ASC),
  INDEX `fk_scheme_model_2_idx` (`model_code` ASC),
  INDEX `fk_scheme_model_3_idx` (`manufacturer_code` ASC),
  INDEX `fk_scheme_model_4_idx` (`category_code` ASC),
  UNIQUE INDEX `issuer_scheme_model_UNIQUE` (`issuer_scheme_code` ASC, `manufacturer_code` ASC, `category_code` ASC, `model_code` ASC),
  CONSTRAINT `fk_scheme_model_1`
    FOREIGN KEY (`issuer_scheme_code`)
    REFERENCES `emi_integration`.`issuer_schemes` (`issuer_scheme_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_2`
    FOREIGN KEY (`model_code`)
    REFERENCES `emi_integration`.`models` (`model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_3`
    FOREIGN KEY (`manufacturer_code`)
    REFERENCES `emi_integration`.`manufacturers` (`manufacturer_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scheme_model_4`
    FOREIGN KEY (`category_code`)
    REFERENCES `emi_integration`.`categories` (`category_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `emi_integration`.`issuer_scheme_model_terminal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emi_integration`.`issuer_scheme_model_terminal` (
  `utid` VARCHAR(12) NOT NULL,
  `issuer_scheme_model_code` VARCHAR(85) NOT NULL,
  `issuer_custom_field` VARCHAR(10) NULL,
  `issuer_scheme_terminal_sync_status` ENUM('NOT_SENT', 'SENT', 'ACKD') NOT NULL COMMENT 'In following sequence\nNOT_SENT\nSENT\nACKD',
  `dealer_id` VARCHAR(45) NULL COMMENT '	',
  PRIMARY KEY (`utid`, `issuer_scheme_model_code`),
  INDEX `fk_scheme_model_utid_1_idx` (`issuer_scheme_model_code` ASC),
  CONSTRAINT `fk_scheme_model_utid_1`
    FOREIGN KEY (`issuer_scheme_model_code`)
    REFERENCES `emi_integration`.`issuer_scheme_model` (`issuer_scheme_model_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
