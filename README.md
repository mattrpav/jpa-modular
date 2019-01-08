# jpa-modular
JPA project supporting modular entities


Insert queries

insert into orders (CREATED_BY, CREATED_DATETIME) VALUES ('test', CURRENT_TIMESTAMP);
insert into order_items(ORDER_ID, ORDER_ITEM_TYPE, SKU, QUANTITY) VALUES(1, 'io.hyte.openjpa.SpecialOrderItem', 'sku-special', 3);
insert into order_items(ORDER_ID, ORDER_ITEM_TYPE, SKU, QUANTITY) VALUES(1, 'io.hyte.openjpa.ExtraSpecialOrderItem', 'sku-extraspecial', 4);
insert into order_items_special (ORDER_ITEM_ID, SPECIAL) VALUES (1, 'Special Stuff');
insert into order_items_extraspecial (ORDER_ITEM_ID, EXTRA_SPECIAL) VALUES (2, 'Extra Special Stuff');