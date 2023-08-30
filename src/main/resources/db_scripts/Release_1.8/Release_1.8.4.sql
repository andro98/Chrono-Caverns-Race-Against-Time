
--create  ROLE_FINANCIAL_REVIEW Role
NSERT INTO `role` (`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, `name`) VALUES
('17',NULL,'2023-08-9 15:57:10','System',NULL,NULL,'ROLE_FINANCIAL_REVIEW');   

--add pages to  ROLE_FINANCIAL_REVIEW Role

INSERT  INTO `role_menu`(`role_id`,`menu_id`) VALUES 
(17,32),(17,34),(17,41),(17,42);

-- remove pages from support role

DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='43';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='40';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='38';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='48';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='49';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='35';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='34';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='42';
DELETE FROM `role_menu` WHERE `role_id` ='8' AND `menu_id` ='39';

-- set default quota for maazoun profile
UPDATE `maazoun_profile` SET `book_moragaa_quota` = 1, `book_talak_quota` = 1,
`book_tasadok_quota` = 1, `book_zawag_melly_quota` = 1, `book_zawag_muslim_quota` = 1,
`book_moragaa_quota_contract_count` = 1, `book_talak_quota_contract_count` = 1,
`book_tasadok_quota_contract_count` = 1, `book_zawag_melly_quota_contract_count` = 1,
`book_zawag_muslim_quota_contract_count` = 1, `has_exeption` = FALSE;