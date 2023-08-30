UPDATE `sub_service_quota` s SET s.status_fk = 'Active' WHERE s.name = 'ضريبة مبيعات';

INSERT INTO `role_menu` VALUES (16, 44);
INSERT INTO `role_menu` VALUES (16, 50);

INSERT INTO `menu` (`id`,`icon`,`is_privilage`,`key_view_name`,`name`,`name_ar`,`ordering`,`url`,`service_fk`) VALUES
('51','cil-money',TRUE,'maazoun.collection.edit.contract','Update Contract','تعديل بيانات العقود','1','/maazoun/contract/edit',4);

INSERT INTO `role_menu` VALUES(2,51),(8,51);

ALTER TABLE `transactionecr` MODIFY `ecr_response` TEXT;
