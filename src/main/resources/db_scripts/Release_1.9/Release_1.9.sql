# INSERT NEW PRICE TIER
INSERT INTO sub_service_price_tier
(created_at, created_by, description, fees, name, sub_service_fk)
SELECT
    '2022-10-31 11:25:59', 'System', s.NAME, SUM(q.fees), s.NAME, s.id
FROM sub_service s
         JOIN sub_service_quota q ON q.sub_service_fk = s.id
WHERE s.service_fk = 3
  AND q.status_fk = 'Active'
GROUP BY s.id;

# UPDATE OLD SUB SERVICE QUOTA
UPDATE sub_service_quota q
SET q.sub_service_price_tier_fk = (
    SELECT MAX(t.id)
    FROM sub_service_price_tier t
    WHERE t.sub_service_fk = q.sub_service_fk
);

# UPDATE STOCK LABEL CODE WITH OLD PRICE TIER WITH SAME OLD QUOTA
UPDATE maazoun_book_stock_label s
SET s.book_tier_id = (
    SELECT MAX(t.id)
    FROM sub_service_price_tier t
    WHERE t.sub_service_fk = s.book_type_id
);

# UPDATE ALL SUPPLY ORDER DETAILS TIER ID WITH OLD PRICE TIER ID
UPDATE supply_order_details sd
SET sd.boot_tier_idfk = (
    SELECT MAX(t.id)
    FROM sub_service_price_tier t
    WHERE t.sub_service_fk = sd.book_typefk
);

# Match all warehouse tier id with stock label code tier id
UPDATE maazoun_book_warehouse w
SET w.book_tier_id = (
    SELECT MAX(t.id)
    FROM sub_service_price_tier t
    WHERE t.sub_service_fk = w.book_type_id
);



insert into `sub_service_price_tier`
(`id`, `created_at`		  , `created_by`,  `description` 	 , `fees`, `name`			,`sub_service_fk`)
values
    (11, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج مسلمين',  234		,  'دفتر زواج مسلمين', 	19		),
    (12, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج مسلمين',  249		,  'دفتر زواج مسلمين', 	19		),
    (13, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج مسلمين',  175.2	,  'دفتر زواج مسلمين', 	9		),
    (14, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج مسلمين',  198		,  'دفتر زواج مسلمين', 	9		),
    (15, '2022-10-25 11:25:59', 'System'  	,  'دفتر طلاق'		 ,  459		,  'دفتر طلاق'    	 , 	20		),
    (16, '2022-10-25 11:25:59', 'System'  	,  'دفتر طلاق'		 ,  469.5	,  'دفتر طلاق'    	 , 	20		),
    (17, '2022-10-25 11:25:59', 'System'  	,  'دفتر طلاق'		 ,  280		,  'دفتر طلاق'    	 , 	10		),
    (18, '2022-10-25 11:25:59', 'System'  	,  'دفتر طلاق'		 ,  295.2	,  'دفتر طلاق'    	 , 	10		),
    (19, '2022-10-25 11:25:59', 'System'  	,  'دفتر تصادق'		 ,  235.5	,  'دفتر تصادق'    	 , 	21		),
    (20, '2022-10-25 11:25:59', 'System'  	,  'دفتر تصادق'		 ,  243		,  'دفتر تصادق'    	 , 	21		),
    (21, '2022-10-25 11:25:59', 'System'  	,  'دفتر تصادق'		 ,  253.5	,  'دفتر تصادق'    	 , 	21		),
    (22, '2022-10-25 11:25:59', 'System'  	,  'دفتر تصادق'		 ,  160		,  'دفتر تصادق'    	 , 	11		),
    (23, '2022-10-25 11:25:59', 'System'  	,  'دفتر مراجعة'	 ,  232.5	,  'دفتر مراجعة'     , 	22		),
    (24, '2022-10-25 11:25:59', 'System'  	,  'دفتر مراجعة'	 ,  160	  	,  'دفتر مراجعة'     , 	12		),
    (25, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج المللي',  234	  	,  'دفتر زواج المللي', 	23		),
    (26, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج المللي',  246	  	,  'دفتر زواج المللي', 	23		),
    (27, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج المللي',  261	  	,  'دفتر زواج المللي', 	23		),
    (28, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج المللي',  160	  	,  'دفتر زواج المللي', 	13		),
    (29, '2022-10-25 11:25:59', 'System'  	,  'دفتر زواج المللي',  280	  	,  'دفتر زواج المللي', 	13		);


insert into `sub_service_quota`
(`beneficiary`, `fees`, `fees_type`, `mid_account`									, `mid_bank`			    , `name`			  , `status_fk`, `sub_service_fk`, `sub_service_price_tier_fk`)
values
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '19'			 , '11'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '19'			 , '12'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '9'			 , '13'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '9'			 , '14'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '20'			 , '15'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '20'			 , '16'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '10'			 , '17'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '10'			 , '18'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '21'			 , '19'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '21'			 , '20'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '21'			 , '21'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '11'			 , '22'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '22'			 , '23'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '12'			 , '24'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '23'			 , '25'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '23'			 , '26'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '23'			 , '27'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '13'			 , '28'						  ),
    ('حساب العميل', 0	  , 'n'		   ,  'CaMXIdro9JABliacQVoN0RyrJydyDRwEWciYW9a8uC4=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى النيابة', 'Active'   , '13'			 , '29'						  ),

    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '19'			 , '11'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '19'			 , '12'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '9'			 , '13'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '9'			 , '14'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 450	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '20'			 , '15'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 450	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '20'			 , '16'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 240	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '10'			 , '17'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 240	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '10'			 , '18'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '21'			 , '19'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '21'			 , '20'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '21'			 , '21'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '11'			 , '22'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '22'			 , '23'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '12'			 , '24'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '23'			 , '25'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '23'			 , '26'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 225	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '23'			 , '27'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '13'			 , '28'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 120	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'ضريبة دمغة', 'Active'   , '13'			 , '29'						  ),

    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '19'			 , '11'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '19'			 , '12'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '9'			 	 , '13'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '9'			 	 , '14'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '20'			 , '15'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '20'			 , '16'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 0.8	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '10'			 , '17'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '10'			 , '18'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '21'			 , '19'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '21'			 , '20'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 12	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '21'			 , '21'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 0.8	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '11'			 , '22'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '22'			 , '23'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 0.8	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '12'			 , '24'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '23'			 , '25'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 1.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '23'			 , '26'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 30	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '23'			 , '27'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 0.8	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '13'			 , '28'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'رسم تنمية', 'Active'   , '13'			 , '29'						  ),

    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '9'			 	 , '13'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '9'			 	 , '14'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '10'			 	 , '17'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '10'			 	 , '18'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '11'			 	 , '22'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '12'			 	 , '24'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	 	 , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '13'			 	 , '28'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 4	  	, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'فحص فنى', 'Active'   , '13'			 	 , '29'						  ),

    ('امان', 0 	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '19'			 , '11'						  ),
    ('امان', 0 	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '19'			 , '12'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '9'			 , '13'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '9'			 , '14'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '20'			 , '15'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '20'			 , '16'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '10'			 , '17'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '10'			 , '18'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '21'			 , '19'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '21'			 , '20'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '21'			 , '21'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '11'			 , '22'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '22'			 , '23'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '12'			 , '24'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '23'			 , '25'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '23'			 , '26'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '23'			 , '27'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '13'			 , '28'						  ),
    ('امان', 0	  , 'n'		   ,  '1h9JSvK2UQ/xgtZsr18QcLeuejSqtPkYFYTAwy0HHIA=', 'AGWCjZWTFl4PGzLZ2WyTcQ==', 'تحويل رقمى أمان', 'Active'   , '13'			 , '29'						  ),

    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 7.5 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '19'			 , '11'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 22.5 	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '19'			 , '12'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '9'			 	 , '13'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 58	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '9'			 	 , '14'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 7.5, 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '20'			 	 , '15'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 18	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '20'			 , '16'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '10'			 , '17'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '10'			 , '18'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 9	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '21'			 	 , '19'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '21'			 , '20'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 16.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '21'			 , '21'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '11'			 , '22'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 6	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '22'			 	 , '23'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '12'			 , '24'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 7.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '23'			 , '25'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 19.5	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '23'			 , '26'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 6	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '23'			 	 , '27'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 35.2	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '13'			 , '28'						  ),
    ('الأدارة المركزية لشئون الدمغة ورسم التنمية', 140	  , 'n'		   ,  'MsVR61dxqKg3ygLKJSCQSpOk5/JrzZXAtjzhCO+Tdu0=', 'd924VS94XB9tPC372JYONEk8CHcQ0TvhShc6UBLIQMI=', 'تكلفة', 'Active'   , '13'			 , '29'						  );