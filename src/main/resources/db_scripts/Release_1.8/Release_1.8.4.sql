
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
UPDATE `maazoun_profile` SET `book_moragaa_quota` = 2, `book_talak_quota` = 2,
`book_tasadok_quota` = 2, `book_zawag_melly_quota` = 2, `book_zawag_muslim_quota` = 2,
`book_moragaa_quota_contract_count` = 2, `book_talak_quota_contract_count` = 2,
`book_tasadok_quota_contract_count` = 2, `book_zawag_melly_quota_contract_count` = 2,
`book_zawag_muslim_quota_contract_count` = 2, `has_exeption` = FALSE;

-- updating exist checklist contract and add new items
UPDATE `maazoun_check_list_lookup` SET `name` = ' (فى حالة الزوجة مطلقة) اشهاد طلاق والتأكد من انقصاء العدة' WHERE id = 37;
UPDATE `maazoun_check_list_lookup` SET `name` = '(فى حالة الزوجة مطلقة) قيد طلاق من السجل المدنى لو مطلقة بحكم محكمةوالتأكد من انقصاء العدة' WHERE id = 38;
UPDATE `maazoun_check_list_lookup` SET `name` = '(فى حالة الزوجة متوفى عنها زوجها) شهادة وفاة زوجها والتأكد من انقصاء العدة' WHERE id = 41;
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (43, 'فى حالة الزوجة موظفة او تتقاضى معاشا اخطار رسمى للجهة خلال 7 ايام', 14);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (44, 'التصريح للعسكريين من الوحدة او الجهة المختصة', 14);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (45, 'التصريح للضباط فى حالة الزواج الثانى من الوحدة او الجهة المختصة', 14);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (46, 'اخطار الزوجة خلال 7 ايام فى حالة وجود زوجات فى عصمة الزوج', 14);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (47, 'فى حالة زواج احد المحكوم عليهم بعقوبة مقيدة للحرية داخل السجن ارفاق موافقة السيد المستشار / المحامى العام', 14);

UPDATE `maazoun_check_list_lookup` SET `name` = 'وثيقة الزواج / تصادق' WHERE id = 5;
UPDATE `maazoun_check_list_lookup` SET `name` = 'صورة بطاقة المطلقة فى حالة حضورها' WHERE id = 7;
UPDATE `maazoun_check_list_lookup` SET `name` = 'فى حالة وجود توكيل : اصل التوكيل اذا كان صادر من الشهر العقارى المصرى' WHERE id = 31;
UPDATE `maazoun_check_list_lookup` SET `name` = 'فى حالة وجود توكيل : ارفاق شهادة ايداع بالشهر العقارى المختص اذا كان صادر من جهات خارجية' WHERE id = 36;
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (48, 'اعلان المطلقة غيابيا خلال 7 ايام', 15);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (49, 'فى حالة طلاق احد المحكوم عليهم بعقوبة مقيدة للحرية داخل السجن : ارفاق موافقة السيد المستشار / المحامى العام', 15);

UPDATE `maazoun_check_list_lookup` SET `name` = ' (فى حالة الزوجة مطلقة) اشهاد طلاق والتأكد من انقصاء العدة' WHERE id = 39;
UPDATE `maazoun_check_list_lookup` SET `name` = '(فى حالة الزوجة مطلقة) قيد طلاق من السجل المدنى لو مطلقة بحكم محكمةوالتأكد من انقصاء العدة' WHERE id = 40;
UPDATE `maazoun_check_list_lookup` SET `name` = '(فى حالة الزوجة متوفى عنها زوجها) شهادة وفاة زوجها والتأكد من انقصاء العدة' WHERE id = 42;
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (50, 'فى حالة الزوجة موظفة او تتقاضى معاشا اخطار رسمى للجهة خلال 7 ايام', 16);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (51, 'التصريح للعسكريين من الوحدة او الجهة المختصة', 16);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (52, 'التصريح للضباط فى حالة الزواج الثانى من الوحدة او الجهة المختصة', 16);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (53, 'اخطار الزوجة خلال 7 ايام فى حالة وجود زوجات فى عصمة الزوج', 16);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (54, 'فى حالة زواج احد المحكوم عليهم بعقوبة مقيدة للحرية داخل السجن ارفاق موافقة السيد المستشار / المحامى العام', 16);

UPDATE `maazoun_check_list_lookup` SET `name` = 'صورة بطاقة الزوجة فى حالة حضورها' WHERE id = 15;
UPDATE `maazoun_check_list_lookup` SET `name` = 'فى حالة وجود توكيل : اصل التوكيل اذا كان صادر من الشهر العقارى المصرى' WHERE id = 30;
UPDATE `maazoun_check_list_lookup` SET `name` = 'فى حالة وجود توكيل : ارفاق شهادة ايداع بالشهر العقارى المختص اذا كان صادر من جهات خارجية' WHERE id = 35;
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (55, 'اخطار المراجعة غيابيا خلال 7 ايام', 17);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (56, 'فى حالة مراجعة احد المحكوم عليهم بعقوبة مقيدة للحرية داخل السجن : ارفاق موافقة السيد المستشار / المحامى العام', 17);

INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (57, 'فى حالة الزوجة موظفة او تتقاضى معاشا اخطار رسمى للجهة خلال 7 ايام', 18);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (58, 'التصريح للعسكريين من الوحدة او الجهة المختصة', 18);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (59, 'التصريح للضباط فى حالة الزواج الثانى من الوحدة او الجهة المختصة', 18);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (60, '(فى حالة الزوجة متوفى عنها زوجها) شهادة وفاة زوجها والتأكد من انقصاء العد', 18);
INSERT INTO `maazoun_check_list_lookup` (`id`, `name`, `sub_service_id`) VALUES (61, 'فى حالة زواج احد المحكوم عليهم بعقوبة مقيدة للحرية داخل السجن : ارفاق موافقة السيد المستشار / المحامى العام', 18);

