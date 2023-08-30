ALTER TABLE `pos` DROP FOREIGN KEY FKboj08nlg4v7gfldt2ausa4oqa;
ALTER TABLE `pos` DROP COLUMN sector_fk;
-- run below on prod area------------------------------------------------------------
SET SQL_SAFE_UPDATES = 0;
UPDATE `pos` SET sector_fk = NULL;
SET SQL_SAFE_UPDATES = 1;
ALTER TABLE `pos` DROP INDEX FKboj08nlg4v7gfldt2ausa4oqa;
ALTER TABLE `pos` DROP INDEX idx_name_sectorFk;
ALTER TABLE `pos` DROP COLUMN sector_fk;

SET SQL_SAFE_UPDATES = 0;
UPDATE `sub_service_quota` s SET s.status_fk = 'InActive' WHERE s.name = 'ضريبة مبيعات';
UPDATE `sub_service_quota` s SET s.status_fk = 'InActive' WHERE s.name = 'تحويل رقمى النيابة';
UPDATE `sub_service_quota` s SET s.status_fk = 'InActive' WHERE s.name = 'تحويل رقمى أمان';
SET SQL_SAFE_UPDATES = 1;

ALTER TABLE `transactionecr` MODIFY `ecr_response` TEXT;
ALTER TABLE `transactionecr` MODIFY `ecr_request` TEXT;
-- -----------------------------


