# Steps to get the bank emi data from portal #
1. Make sure Scheme Configuration, Scheme Model Configuration, Terminal configuration is done in Emi-Inegration through portal
2. Migrate the configuration from Emi-Inegration to Portal-Configuration
3. In portal_configuration databse, There is a view on name 'emi_data_denormalized'.
4. create view if not present by following script

CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `root`@`localhost`
    SQL SECURITY DEFINER
VIEW `portal_configuration`.`emi_data_denormalized` AS
    SELECT DISTINCT
        `portal_configuration`.`issuer_scheme_model_terminal`.`utid` AS `utid`,
        `portal_configuration`.`model_serial_numbers`.`model_serial_number` AS `model_serial_number`,
        `portal_configuration`.`model_serial_numbers`.`is_emi_used` AS `is_emi_used`,
        `portal_configuration`.`issuer_scheme_model`.`issuer_scheme_model_code` AS `issuer_scheme_model_code`,
        `portal_configuration`.`issuer_scheme_model_terminal`.`bajaj_product_type_code` AS `bajaj_product_type_code`,
        `portal_configuration`.`issuer_scheme_model_terminal`.`dealer_id` AS `dealer_id`,
        `portal_configuration`.`issuer_scheme_model_terminal`.`issuer_custom_field` AS `issuer_custom_field`,
        `portal_configuration`.`issuer_scheme_model_terminal`.`issuer_scheme_terminal_sync_status` AS `issuer_scheme_terminal_sync_status`,
        `portal_configuration`.`issuer_scheme_model_terminal`.`issuer_scheme_onus_offus` AS `issuer_scheme_onus_offus`,
        `portal_configuration`.`categories`.`category_code` AS `category_code`,
        `portal_configuration`.`categories`.`category_display_name` AS `category_display_name`,
        `portal_configuration`.`models`.`model_code` AS `model_code`,
        `portal_configuration`.`models`.`model_display_number` AS `model_display_number`,
        `portal_configuration`.`manufacturers`.`manufacturer_display_name` AS `manufacturer_display_name`,
        `portal_configuration`.`manufacturers`.`manufacturer_code` AS `manufacturer_code`,
        `portal_configuration`.`manufacturers`.`bajaj_manufacturer_code` AS `bajaj_manufacturer_code`,
        `portal_configuration`.`issuer_schemes`.`issuer_scheme_code` AS `issuer_scheme_code`,
        `portal_configuration`.`issuer_schemes`.`issuer_scheme_display_name` AS `issuer_scheme_display_name`,
        `portal_configuration`.`issuer_schemes`.`issuer_rate_of_interest` AS `issuer_rate_of_interest`,
        `portal_configuration`.`issuer_banks`.`issuer_bank_code` AS `issuer_bank_code`,
        `portal_configuration`.`issuer_banks`.`emi_bank_code` AS `emi_bank_code`,
        `portal_configuration`.`issuer_banks`.`issuer_bank_display_name` AS `issuer_bank_display_name`,
        `portal_configuration`.`emi_tenures`.`emi_tenure_code` AS `emi_tenure_code`,
        `portal_configuration`.`emi_tenures`.`emi_tenure_display_name` AS `emi_tenure_display_name`,
        `portal_configuration`.`emi_tenures`.`emi_tenure_months` AS `emi_tenure_months`,
        `portal_configuration`.`issuer_schemes`.`advance_emi` AS `advance_emi`,
        `portal_configuration`.`issuer_schemes`.`bajaj_issuer_scheme_code` AS `bajaj_issuer_scheme_code`,
        `portal_configuration`.`issuer_schemes`.`innoviti_subvention` AS `innoviti_subvention`,
        `portal_configuration`.`issuer_schemes`.`general_scheme` AS `general_scheme`,
        `portal_configuration`.`issuer_schemes`.`max_amount` AS `max_amount`,
        `portal_configuration`.`issuer_schemes`.`min_amount` AS `min_amount`,
        `portal_configuration`.`issuer_schemes`.`scheme_start_date` AS `scheme_start_date`,
        `portal_configuration`.`issuer_schemes`.`scheme_end_date` AS `scheme_end_date`
    FROM
        ((((((((`portal_configuration`.`issuer_scheme_model_terminal`
        LEFT JOIN `portal_configuration`.`issuer_scheme_model` ON ((`portal_configuration`.`issuer_scheme_model_terminal`.`issuer_scheme_model_code` = `portal_configuration`.`issuer_scheme_model`.`issuer_scheme_model_code`)))
	LEFT JOIN `portal_configuration`.`models` ON ((`portal_configuration`.`models`.`model_code` = `portal_configuration`.`issuer_scheme_model`.`model_code`)))
        LEFT JOIN `portal_configuration`.`categories` ON ((`portal_configuration`.`categories`.`category_code` = `portal_configuration`.`models`.`category_code`)))
        LEFT JOIN `portal_configuration`.`manufacturers` ON ((`portal_configuration`.`manufacturers`.`manufacturer_code` = `portal_configuration`.`models`.`manufacturer_code`)))
        LEFT JOIN `portal_configuration`.`issuer_schemes` ON ((`portal_configuration`.`issuer_schemes`.`issuer_scheme_code` = `portal_configuration`.`issuer_scheme_model`.`issuer_scheme_code`)))
        LEFT JOIN `portal_configuration`.`issuer_banks` ON ((`portal_configuration`.`issuer_banks`.`issuer_bank_code` = `portal_configuration`.`issuer_schemes`.`issuer_bank_code`)))
        LEFT JOIN `portal_configuration`.`emi_tenures` ON ((`portal_configuration`.`emi_tenures`.`emi_tenure_code` = `portal_configuration`.`issuer_schemes`.`emi_tenure_code`)))
        LEFT JOIN `portal_configuration`.`model_serial_numbers` ON ((`portal_configuration`.`model_serial_numbers`.`model_code` = `portal_configuration`.`models`.`model_code`)))

  5. Then run the below query to get the emiData

    SELECT DISTINCT
        manufacturer_display_name manufacturer_display_name,
        category_display_name category_display_name,
        model_display_number model_display_name,
        model_code model_code,
        issuer_scheme_display_name scheme_display_name,
        general_scheme general_scheme,
        issuer_scheme_code emi_scheme_code,
        emi_bank_code emi_bank_code,
        issuer_bank_display_name emi_bank_display_name,
        issuer_rate_of_interest Rate_of_Interest,
        advance_emi advance_emi,
        max_amount max_amount,
        min_amount min_amount,    
        scheme_start_date scheme_start_date,
        scheme_end_date scheme_end_date,
        issuer_scheme_model_code scheme_model_code
    FROM
        emi_data_denormalized
    WHERE utid ='R0000508'
    
  6. export the data as EmiData.csv