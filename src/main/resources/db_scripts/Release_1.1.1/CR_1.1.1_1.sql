INSERT INTO `merchant` (`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`address`, `code`, `description`, `email`, `fax`, `mobile`, `name`, `phone_1`, `phone_2`, `status_fk`, `city_fk`) 
VALUES
('3',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'3 شارع محمد فريد ','MER003',
'خدمات المأذونين','','',NULL,'النيابة',NULL,NULL,'Active','1');
-- ----------------------------------------------------------------------------------
INSERT INTO `service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, `code`, `description`, 
`name`, `status_fk`, `icon`, `tax`, `merchant_fk`) 
VALUES
('3',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'SER003',NULL,
'حدمة بيع دفاتر المأذونين','Active','transfer_within_a_station','0','3');
INSERT INTO `service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, `code`, `description`, 
`name`, `status_fk`, `icon`, `tax`, `merchant_fk`) 
VALUES
('4',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'SER004',NULL,
'خدمة تحصيل عقود المأذونين','Active','transfer_within_a_station','0','3');
-- ---------------------------------------------------------------------------------------
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('9',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'8','175.20','دفتر زواج مسلمين','0','Active','3');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('10',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'8','295.20','دفتر طلاق','0','Active','3');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('11',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'8','175.20','دفتر تصادق','0','Active','3');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('12',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'8','175.20','دفتر مراجعة','0','Active','3');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('13',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'8','175.20','دفتر زواج المللى','0','Active','3');
-- -----------------------------------------------------------------------------sub-service 2
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('14',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'2','0','تحصيل عقود زواج المسلمين','0','Active','4');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('15',NULL,'2022-09-15 11:25:59','system',NULL,NULL,NULL,'826','تحصيل إشهادات الطلاق','0','Active','4');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('16',NULL,'2022-09-15 11:25:59','system',NULL,NULL,'2','0','تحصيل عقود التصادق','0','Active','4');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('17',NULL,'2022-09-15 11:25:59','system',NULL,NULL,NULL,'826','تحصيل إشهادات المراجعة','0','Active','4');
INSERT INTO `sub_service` 
(`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, 
`description`, `fees`, `name`, `required_service`, `status_fk`, `service_fk`) 
VALUES
('18',NULL,'2022-09-15 11:25:59','system',NULL,NULL,NULL,'825.60','تحصيل عقد زواج المللى','0','Active','4');

insert into `role` (`id`, `comment`, `created_at`, `created_by`, `updated_at`, `updated_by`, `name`) values
('13',NULL,'2022-09-22 15:57:10','System',NULL,NULL,'ROLE_STOREKEEPER');
 
INSERT INTO `menu` (`id`,`icon`,`is_privilage`,`key_view_name`,`name`,`name_ar`,`ordering`,`url`,`service_fk`) VALUES
('32','cil-money',FALSE,'maazoun.transactions','Maazoun Book Request Transactions','بحث الطلبات','2','/maazoun/transaction',3),
('33','cil-money',TRUE,'refund','Refund ','الاسترجاع','0','/maazoun/Refund',3),
('34','cil-money',FALSE,'maazoun.report','Technical Reports','التقارير الفنية','6','/maazoun/report',3),
('35','cil-money',FALSE,'maazoun.request','Maazoun New Book Request','طلب شراء دفاتر','1','/maazoun/createRequest',3),
('36','cil-money',FALSE,'maazoun.create','Maazoun List','قائمه الماذونين','5','/maazoun/list',3),
('37','cil-money',FALSE,'maazoun.warehouse.list','Maazoun Books Warehouse','المخازن','4','/Warehous/list',3),
('38','cil-money',FALSE,'maazoun.books.incoming','Incoming Requests','إستلام الدفاتر','3','/Warehous/list/incoming',3),

('39','cil-money',FALSE,'maazoun.collection.create','Maazoun Collect Contract Request','خدمة تحصيل عقود المأذونيين','1','/maazoun/collection/createRequest',4),
('40','cil-money',FALSE,'maazoun.delivery.create','Maazoun Outgoing Contract Request','تسليم العقود','4','/maazoun/delivery/createRequest',4),
('41','cil-money',FALSE,'maazoun.collection.transaction','Maazoun Collection Transactions','بحث الطلبات','2','/maazoun/collection/transaction',4),
('42','cil-money',FALSE,'maazoun.collection.report','Technical Report','التقارير الفنية','5','/maazoun/collection/report',4),
('43','cil-money',FALSE,'maazoun.collection.review','Maazoun Contract Review','مراجعة العقود','3','/maazoun/collection/review',4),
('44','cil-money',FALSE,'maazoun.collection.books.recive','Maazoun Receive Books  ','استلام دفاتر المأذون ','3','/maazoun/collection/receiveBooks',4),
('45','cil-money',FALSE,'maazoun.addCustodyBookS','Add Custody BookS','إضافه الدفاتر العهده','4','/maazoun/addCustodyBooks',3),
('46','cil-money',FALSE,'maazoun.addCustodyContracts','Add Custody Contracts','إضافه العقود  العهده','3','/maazoun/collection/addCustodyContracts',4),
('47','cil-money',FALSE,'maazoun.stockLable','Stock Books Lables','انشاء الارقام الإلكترونيه للدفاتر','4','/maazoun/warehouse/stockLable',3);

  
INSERT  INTO `role_menu`(`role_id`,`menu_id`) VALUES 
(1,32),(1,35),(1,39),(1,41)
(2,32),(2,33),(2,34),(2,36),(2,37),(2,40),(2,41),(2,42),(2,43)
(6,32),(6,37),(6,38),(6,40),(6,41), 
(7,32),(7,33),(7,34),(7,36),(7,37),(7,38),(7,40),(7,41),(7,42),(7,43)
(8,32),(8,33),(8,34),(8,35),(8,36),(8,37),(8,39),(8,40),(8,41),(8,42),(8,43),
(13,37),(6,45),(6,46),(5,47);

INSERT  INTO `role_menu`(`role_id`,`menu_id`) VALUES 
(1,44),(6,44)

-- -------------------------------------

INSERT INTO `maazoun_check_list_lookup`(id, `name`, `sub_service_id`) VALUES 
(1,'صورة العقد واضحة',14),
(2,'قيمة المؤخر مذكورة وواضحة',14),
(3,'إمضاء الشهود واضح',14),
(4,'مرفقات بطاقة الزوج والزوجة',14),
(5,'صورة العقد واضحة',15),
(6,'قيمة المؤخر مذكورة وواضحة',15),
(7,'إمضاء الشهود واضح',15),
(8,'مرفقات بطاقة الزوج والزوجة',15),
(9,'صورة العقد واضحة',16),
(10,'قيمة المؤخر مذكورة وواضحة',16),
(11,'إمضاء الشهود واضح',16),
(12,'مرفقات بطاقة الزوج والزوجة',16),
(13,'صورة العقد واضحة',17),
(14,'قيمة المؤخر مذكورة وواضحة',17),
(15,'إمضاء الشهود واضح',17),
(16,'مرفقات بطاقة الزوج والزوجة',17),
(17,'صورة العقد واضحة',18),
(18,'قيمة المؤخر مذكورة وواضحة',18),
(19,'إمضاء الشهود واضح',18),
(20,'مرفقات بطاقة الزوج والزوجة',18);

-- sub-service-quota-----------------------------------------------------------------------------------------
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('1',NULL,'120','ضريبة دمغة','Active','9');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('2',NULL,'16','رسم تنمية','Active','9');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('3',NULL,'4','فحص فنى','Active','9');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('4',NULL,'35.2','تكلفة','Active','9');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('5',NULL,'1000','تحويل رقمى','Active','9');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('6',NULL,'240','ضريبة دمغة','Active','10');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('7',NULL,'16','رسم تنمية','Active','10');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('8',NULL,'4','فحص فنى','Active','10');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('9',NULL,'35.2','تكلفة','Active','10');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('10',NULL,'1000','تحويل رقمى','Active','10');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('11',NULL,'120','ضريبة دمغة','Active','11');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('12',NULL,'16','رسم تنمية','Active','11');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('13',NULL,'4','فحص فنى','Active','11');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('14',NULL,'35.2','تكلفة','Active','11');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('15',NULL,'1000','تحويل رقمى','Active','11');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('16',NULL,'120','ضريبة دمغة','Active','12');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('17',NULL,'16','رسم تنمية','Active','12');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('18',NULL,'4','فحص فنى','Active','12');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('19',NULL,'35.2','تكلفة','Active','12');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('20',NULL,'1000','تحويل رقمى','Active','12');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('21',NULL,'120','ضريبة دمغة','Active','13');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('22',NULL,'16','رسم تنمية','Active','13');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('23',NULL,'4','فحص فنى','Active','13');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('24',NULL,'35.2','تكلفة','Active','13');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('25',NULL,'1000','تحويل رقمى','Active','13');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('26',NULL,'0','1.5% على الألف الأولى','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('27',NULL,'0','2% على باقى المبلغ','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('28',NULL,'1.5','إضافى','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('29',NULL,'1.5','أبنية محاكم','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('30',NULL,'0.24','جمعية مأذونيين','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('31',NULL,'100','تأمين أسرة','Active','14');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('32',NULL,'0.25','مقرر','Active','15');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('33',NULL,'1.5','إضافى','Active','15');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('34',NULL,'1.5','أبنية محاكم','Active','15');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('35',NULL,'100','تأمين أسرة','Active','15');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('36',NULL,'0','1.5% على الألف الأولى','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('37',NULL,'0','2% على باقى المبلغ','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('38',NULL,'1.5','إضافى','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('39',NULL,'1.5','أبنية محاكم','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('40',NULL,'0.24','جمعية مأذونيين','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('41',NULL,'100','تأمين أسرة','Active','16');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('42',NULL,'0.25','مقرر','Active','17');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('43',NULL,'1.5','إضافى','Active','17');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('44',NULL,'1.5','أبنية محاكم','Active','17');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('45',NULL,'100','تأمين أسرة','Active','17');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('46',NULL,'0.2','مقرر','Active','18');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('47',NULL,'1.5','إضافى','Active','18');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('48',NULL,'1.5','أبنية محاكم','Active','18');
insert into `sub_service_quota` (`id`, `description`, `fees`, `name`, `status_fk`, `sub_service_fk`) values('49',NULL,'100','تأمين أسرة','Active','18');
