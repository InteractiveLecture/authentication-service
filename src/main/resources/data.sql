INSERT INTO users(id,username,password,enabled) VALUES (1,'admin','admin',true);
INSERT INTO authorities(id,user_id,authority) VALUES (1,1,'admin');