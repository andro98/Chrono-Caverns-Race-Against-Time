-- reset database
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE `maazoun_book_warehouse`;
TRUNCATE `maazoun_book_supply_order`;
TRUNCATE `maazoun_book_request_info`;
TRUNCATE `maazoun_book_collection_info`;
TRUNCATE `maazoun_book_validation`;
TRUNCATE `maazoun_book_deliver_info`;
TRUNCATE `maazoun_book_stock_label`;
TRUNCATE `transaction`;
TRUNCATE `transaction_item`;
TRUNCATE `transaction_mids`;
TRUNCATE `financial_deficit`;
TRUNCATE `pull_account`;
SET FOREIGN_KEY_CHECKS = 1;
UPDATE `insurance_number` SET `sequance_number` = 0;
-- -----------------------------------------------------
DROP INDEX idx_maazoun_book_collection_info_contractNumber ON `maazoun_book_collection_info`;
INSERT INTO `menu` (`id`,`icon`,`is_privilage`,`key_view_name`,`name`,`name_ar`,`ordering`,`url`,`service_fk`) VALUES
('48','cil-money',FALSE,'refund','Refund ','الاسترجاع','0','/maazoun/maazounRefund',3),
('49','cil-money',FALSE,'refund','Refund ','الاسترجاع','0','/maazoun/collectionRefund',4),
('50','cil-money',FALSE,'maazoun.collection.pay','Maazoun   Collect Contract Pay',' دفع قيمه العقود','1','/maazoun/payCollected',4);
INSERT INTO `role_menu` VALUES (2,48),(2,49),(8,48),(8,49),(1,48),(1,49),(6,48),(6,49),(16,48),(16,49),(1,50);
insert into `setting` (`id`, `key_ops`, `value_ops`) values('1','review.contract','0');
-- -------------------------------------------------------------

