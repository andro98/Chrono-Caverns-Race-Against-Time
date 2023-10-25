# INSERT NEW PRICE TIER
INSERT INTO sub_service_price_tier
(created_at, created_by, description, fees, name, sub_service_fk)
SELECT
    '2022-10-25 11:25:59', 'System', s.NAME, SUM(q.fees), s.NAME, s.id
FROM sub_service s
         JOIN sub_service_quota q ON q.sub_service_fk = s.id
WHERE s.service_fk = 3
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