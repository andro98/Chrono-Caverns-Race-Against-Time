INSERT INTO `menu` (`id`,`icon`,`is_privilage`,`key_view_name`,`name`,`name_ar`,`ordering`,`url`,`service_fk`) VALUES
('48','cil-money',FALSE,'refund','Refund ','الاسترجاع','0','/maazoun/maazounRefund',3),
('49','cil-money',FALSE,'refund','Refund ','الاسترجاع','0','/maazoun/collectionRefund',4);

INSERT INTO `role_menu` VALUES (2,48),(2,49),(8,48),(8,49);