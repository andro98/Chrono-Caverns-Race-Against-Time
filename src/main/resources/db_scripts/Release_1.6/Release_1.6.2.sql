ALTER TABLE `sub_service` MODIFY NAME VARCHAR(255) NULL;
UPDATE `sub_service` SET contracts_count = 8 WHERE id IN (9, 10, 11, 12, 13);
INSERT INTO `sub_service` (id, created_at, created_by, fees, NAME, status_fk, service_fk, fees_quota, contracts_count) 
VALUES (19, NOW(), 'system', 0, 'دفتر زواج مسلمين', 'Active', 3, 0, 15);
INSERT INTO `sub_service` (id, created_at, created_by, fees, NAME, status_fk, service_fk, fees_quota, contracts_count) 
VALUES (20, NOW(), 'system', 0, 'دفتر طلاق', 'Active', 3, 0, 15);
INSERT INTO `sub_service` (id, created_at, created_by, fees, NAME, status_fk, service_fk, fees_quota, contracts_count) 
VALUES (21, NOW(), 'system', 0, 'دفتر تصادق', 'Active', 3, 0, 15);
INSERT INTO `sub_service` (id, created_at, created_by, fees, NAME, status_fk, service_fk, fees_quota, contracts_count) 
VALUES (22, NOW(), 'system', 0, 'دفتر مراجعة', 'Active', 3, 0, 15);
INSERT INTO `sub_service` (id, created_at, created_by, fees, NAME, status_fk, service_fk, fees_quota, contracts_count) 
VALUES (23, NOW(), 'system', 0, 'دفتر زواج المللى', 'Active', 3, 0, 15);
-- ----------------------------------
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (225, 'ضريبة دمغة', 'Active', 19, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (30, 'رسم تنمية', 'Active', 19, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (22.5, 'تكلفة', 'Active', 19, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى أمان', 'Active', 19, 'n', '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'امان');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى النيابة', 'Active', 19, 'n', 'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'حساب العميل');
-- -----
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (225, 'ضريبة دمغة', 'Active', 20, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (30, 'رسم تنمية', 'Active', 20, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (16.5, 'تكلفة', 'Active', 20, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى أمان', 'Active', 20, 'n', '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'امان');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى النيابة', 'Active', 20, 'n', 'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'حساب العميل');

INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (450, 'ضريبة دمغة', 'Active', 21, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (30, 'رسم تنمية', 'Active', 21, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (18, 'تكلفة', 'Active', 21, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى أمان', 'Active', 21, 'n', '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'امان');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى النيابة', 'Active', 21, 'n', 'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'حساب العميل');

INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (225, 'ضريبة دمغة', 'Active', 22, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (30, 'رسم تنمية', 'Active', 22, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (6, 'تكلفة', 'Active', 22, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى أمان', 'Active', 22, 'n', '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'امان');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى النيابة', 'Active', 22, 'n', 'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'حساب العميل');

INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (225, 'ضريبة دمغة', 'Active', 23, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (30, 'رسم تنمية', 'Active', 23, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (7.5, 'تكلفة', 'Active', 23, 'n', 'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'الأدارة المركزية لشئون الدمغة ورسم التنمية');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى أمان', 'Active', 23, 'n', '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'امان');
INSERT INTO `sub_service_quota` (fees, NAME, status_fk, sub_service_fk, fees_type, mid_account, mid_bank, beneficiary)
VALUE (0, 'تحويل رقمى النيابة', 'Active', 23, 'n', 'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'حساب العميل');
-- -------------------------------------
UPDATE `maazoun_book_warehouse` SET book_type_id = 19 WHERE contract_count = 15 AND book_type_id = 9;
UPDATE `maazoun_book_warehouse` SET book_type_id = 20 WHERE contract_count = 15 AND book_type_id = 10;
UPDATE `maazoun_book_warehouse` SET book_type_id = 21 WHERE contract_count = 15 AND book_type_id = 11;
UPDATE `maazoun_book_warehouse` SET book_type_id = 22 WHERE contract_count = 15 AND book_type_id = 12;
UPDATE `maazoun_book_warehouse` SET book_type_id = 23 WHERE contract_count = 15 AND book_type_id = 13;
