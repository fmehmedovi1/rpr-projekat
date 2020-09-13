BEGIN TRANSACTION;
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
	"expiration_date"	INTEGER,
	"status"	TEXT,
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

CREATE TABLE IF NOT EXISTS "product_updates" (
	"product_updates_id"	INTEGER,
	"product_operation"	TEXT,
	"product_id"	TEXT,
	"warehouse_id"	TEXT,
	PRIMARY KEY("product_updates_id"),
	FOREIGN KEY("product_id") REFERENCES "products"("product_id"),
	FOREIGN KEY("warehouse_id") REFERENCES "warehouses"("warehouse_id")
);
INSERT INTO "users" VALUES (1,'Korisnik','Korisnić','korisnik1','korisnik@hotmail.com','sifra123');
INSERT INTO "users" VALUES (2,'Faris','Mehmedović','fmehmedovi1','fmehmedovi1@etf.unsa.ba','faris123');

INSERT INTO "warehouses" VALUES (1,'Rajlovac WH','Sarajevo',1);
INSERT INTO "warehouses" VALUES (2,'Ware','Trebinje',2);

INSERT INTO "products" VALUES (1,'Cigle',10,5,50,'VALID');
INSERT INTO "products" VALUES (2,'Šibice',1,20,32,'VALID');
INSERT INTO "products" VALUES (3,'Palete',200,2,12,'VALID');
INSERT INTO "products" VALUES (4,'Lopate',30,15,14,'VALID');

INSERT INTO "warehouse_products" VALUES (1,1);
INSERT INTO "warehouse_products" VALUES (1,2);
INSERT INTO "warehouse_products" VALUES (2,3);
INSERT INTO "warehouse_products" VALUES (2,4);

INSERT INTO "product_updates" VALUES (1, '+Cigle: 5(10 BAM), 2020-19-05 03:19:59',1,1);
INSERT INTO "product_updates" VALUES (2, '+Šibice: 1(20 BAM), 2020-19-05 03:29:00',2,1);
INSERT INTO "product_updates" VALUES (3, '+Palete: 200(2 BAM), 2020-19-05 03:29:01',3,2);
INSERT INTO "product_updates" VALUES (4, '+Lopate: 30(15 BAM), 2020-19-05 03:29:02',4,2);

COMMIT;
