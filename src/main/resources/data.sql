
INSERT INTO app_user (username, password, role) VALUES ('admin', '{bcrypt}admin123', 'ROLE_ADMIN');
INSERT INTO app_user (username, password, role) VALUES ('customer1', '{bcrypt}customer123', 'ROLE_USER');


INSERT INTO asset (customer_id, asset_name, size, usable_size) VALUES (1, 'TRY', 100000.0, 100000.0);
INSERT INTO asset (customer_id, asset_name, size, usable_size) VALUES (2, 'TRY', 50000.0, 50000.0);
