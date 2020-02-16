BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "i" (
	"changes_id"	INTEGER,
	"type_of_change"	TEXT,
	"product_id"	INTEGER,
	"warehouse_id"	INTEGER,
	PRIMARY KEY("changes_id"),
	FOREIGN KEY("product_id") REFERENCES "products"("product_id"),
	FOREIGN KEY("warehouse_id") REFERENCES "warehouses"("warehouse_id")
);
CREATE TABLE IF NOT EXISTS "warehouses" (
	"warehouse_id"	INTEGER,
	"name"	TEXT,
	"address"	TEXT,
	"responsible_preson_id"	INTEGER,
	PRIMARY KEY("warehouse_id"),
	FOREIGN KEY("responsible_preson_id") REFERENCES "users"("user_id")
);
CREATE TABLE IF NOT EXISTS "warehouse_products" (
	"warehouse_id"	INTEGER,
	"product_id"	INTEGER,
	FOREIGN KEY("warehouse_id") REFERENCES "warehouses"("warehouse_id"),
	FOREIGN KEY("product_id") REFERENCES "products"("product_id")
);
CREATE TABLE IF NOT EXISTS "products" (
	"product_id"	INTEGER,
	"name"	TEXT,
	"price"	INTEGER,
	"quantity"	INTEGER,
	PRIMARY KEY("product_id")
);
CREATE TABLE IF NOT EXISTS "users" (
	"user_id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("user_id")
);
INSERT INTO "changes_in_warehouse" VALUES (1,'added',1,1);
INSERT INTO "changes_in_warehouse" VALUES (2,'added',2,1);
INSERT INTO "changes_in_warehouse" VALUES (3,'added',3,2);
INSERT INTO "changes_in_warehouse" VALUES (4,'added',4,2);
INSERT INTO "changes_in_warehouse" VALUES (5,'added',5,3);
INSERT INTO "changes_in_warehouse" VALUES (6,'added',6,3);
INSERT INTO "warehouses" VALUES (1,'FarisWH','Tuzla',1);
INSERT INTO "warehouses" VALUES (2,'MuxWare','Trebinje',2);
INSERT INTO "warehouses" VALUES (3,'SareWare','Tučepi',3);
INSERT INTO "warehouse_products" VALUES (1,1);
INSERT INTO "warehouse_products" VALUES (1,2);
INSERT INTO "warehouse_products" VALUES (2,3);
INSERT INTO "warehouse_products" VALUES (2,4);
INSERT INTO "warehouse_products" VALUES (3,5);
INSERT INTO "warehouse_products" VALUES (3,6);
INSERT INTO "products" VALUES (1,'Cigle',10,5);
INSERT INTO "products" VALUES (2,'Šibice',1,20);
INSERT INTO "products" VALUES (3,'Palete',200,2);
INSERT INTO "products" VALUES (4,'Lopate',30,15);
INSERT INTO "products" VALUES (5,'Pijesak',12,100);
INSERT INTO "products" VALUES (6,'Mandoline',300,2);
INSERT INTO "users" VALUES (1,'Faris','Mehmedović','fmehmedovi1','fmehmedovi1@etf.unsa.ba','faris123');
INSERT INTO "users" VALUES (2,'Mujo','Mujić','mmujic1','mmujic1@etf.unsa.ba','mujo123');
INSERT INTO "users" VALUES (3,'Sara','Sarić','ssaric1','ssaric1@etf.unsa.ba','sara123');
COMMIT;
