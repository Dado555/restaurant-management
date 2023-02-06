insert into authority (id, name, active) values
    (10001, 'ADMIN', true);
insert into authority (id, name, active) values
    (10002, 'MANAGER', true);
insert into authority (id, name, active) values
    (10003, 'WAITER', true);
insert into authority (id, name, active) values
    (10004, 'BARTENDER', true);
insert into authority (id, name, active) values
    (10005, 'COOK', true);


-- SUPER_USER password: sifra123
-- USER master password: MASTER

insert into users (id, user_type, first_name, last_name, phone_number, username, password, active) values
    (10006, 'SUPER_USER','Admin', 'Adminovic', '065555555', 'admin', '$2a$10$iC1gE2XqgSSkoQnITUJeSOjMMjAdpFxaWC3AcQB.vjf1s7mYCZ3jK', true);
insert into users (id, user_type, first_name, last_name, phone_number, username, password, active) values
    (10007, 'SUPER_USER','Menadzer', 'Menadzeric', '065555555', 'menadzer', '$2a$10$iC1gE2XqgSSkoQnITUJeSOjMMjAdpFxaWC3AcQB.vjf1s7mYCZ3jK', true);

insert into users (id, user_type, first_name, last_name, phone_number, username, password, active) values
    (10008, 'WAITER','Vladan', 'Perisic', '065555555', '1357', '$2a$10$0DGchQfgcrAek6f7Zy1aIuOS9tKpPJ8t4o68eC3K4sGXkFUyjj4OC', true);
insert into users (id, user_type, first_name, last_name, phone_number, username, password, active) values
    (10009, 'BARTENDER','Bart', 'Bartic', '065555555', '2468', '$2a$10$0DGchQfgcrAek6f7Zy1aIuOS9tKpPJ8t4o68eC3K4sGXkFUyjj4OC', true);
insert into users (id, user_type, first_name, last_name, phone_number, username, password, active) values
    (10010, 'COOK','Kimi', 'KKk', '065555555', '3579', '$2a$10$0DGchQfgcrAek6f7Zy1aIuOS9tKpPJ8t4o68eC3K4sGXkFUyjj4OC', true);


insert into user_authorities (authority_id, user_id) values (10001, 10006);
insert into user_authorities (authority_id, user_id) values (10002, 10007);
insert into user_authorities (authority_id, user_id) values (10003, 10008);
insert into user_authorities (authority_id, user_id) values (10004, 10009);
insert into user_authorities (authority_id, user_id) values (10005, 10010);


insert into food (id, active, description, ingredients, name, preparation_time) values
    (10011, true, 'very nice greek', 'chicken and pork', 'Giros', 20);
insert into food (id, active, description, ingredients, name, preparation_time) values
    (10012, true, 'tasteful', 'cabbage, meat', 'Sarma', 30);
insert into food (id, active, description, ingredients, name, preparation_time) values
    (10013, true, 'description3', 'meat, breed, ketchup, sour cream,', 'Burger', 50);
insert into food (id, active, description, ingredients, name, preparation_time) values
    (10069, true, 'nice', 'italiano recipe,', 'Pizza', 50);

insert into drink (id, active, description, ingredients, name, preparation_time, is_alcoholic) values
    (10014, true, 'description1', 'ingredient1', 'Voda Rosa', 20, false);
insert into drink (id, active, description, ingredients, name, preparation_time, is_alcoholic) values
    (10015, true, 'wine brandy', 'alcohol', 'Vinjak', 20, true);
insert into drink (id, active, description, ingredients, name, preparation_time, is_alcoholic) values
    (10016, true, 'so nice', 'plum', 'Sljivovica Rakija', 20, true );


insert into food_suggestion (id, active, date, description, cook_id, food_id) values
    (10017, true, 1637247604, 'description1', 10010, 10011);

insert into drink_suggestion (id, active, date, description, bartender_id, drink_id) values
    (10018, true, 1637247604, 'description1', 10009, 10014);


insert into menu_item_category (id, name, active) values (10019, 'Appetizer', true);
insert into menu_item_category (id, name, active) values (10020, 'Main course', true);
insert into menu_item_category (id, name, active) values (10021, 'Aperitif', true);
insert into menu_item_category (id, name, active) values (10022, 'Alcoholic drinks', true);
insert into menu_item_category (id, name, active) values (10023, 'Desert', true);

insert into menu_item (id, active, item_id, category_id) values (10023, true, 10011, 10020);
insert into menu_item (id, active, item_id, category_id) values (10024, true, 10012, 10020);
insert into menu_item (id, active, item_id, category_id) values (10025, true, 10013, 10020);
insert into menu_item (id, active, item_id, category_id) values (10026, true, 10014, 10021);
insert into menu_item (id, active, item_id, category_id) values (10027, true, 10015, 10022);
insert into menu_item (id, active, item_id, category_id) values (10028, true, 10016, 10022);
insert into menu_item (id, active, item_id, category_id) values (10070, true, 10069, 10021);

insert into sector (id, active, name) values (10029, true, 'Prvi sprat');
insert into sector (id, active, name) values (10030, true, 'Drugi sprat');

insert into restaurant_table (id, active, number_of_seats, table_number, sector_id, position_x, position_y) values
    (10031, true, 4, 1, 10029, 50,50);
insert into restaurant_table (id, active, number_of_seats, table_number, sector_id, position_x, position_y) values
    (10032, true, 4, 2, 10029, 50,300);
insert into restaurant_table (id, active, number_of_seats, table_number, sector_id, position_x, position_y) values
    (1100, true, 4, 3, 10029, 300, 50);

insert into restaurant_table (id, active, number_of_seats, table_number, sector_id, position_x, position_y) values
    (10033, true, 4, 4, 10030, 300, 50);
insert into restaurant_table (id, active, number_of_seats, table_number, sector_id, position_x, position_y) values
    (10034, true, 4, 5, 10030, 300, 300);


insert into orders (id, active, date, status, table_id, waiter_id) values
    (10035, true, 1641350967000, 'FOR_PREPARATION', 10031, 10008);
insert into orders (id, active, date, status, table_id, waiter_id) values
    (10036, true, 1641782967000, 'IN_PROGRESS', 10032, 10008);
insert into orders (id, active, date, status, table_id, waiter_id) values
    (10037, true, 1643165367000, 'READY', 10033, 10008);
insert into orders (id, active, date, status, table_id, waiter_id) values
    (10056, true, 1643251897000, 'FINISHED', 10034, 10008);


insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense) values
    (10038, true, 2, 'description1', 'AWAITING_APPROVAL', 10023, 10035, 350, 200);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense) values
    (10039, true, 4, 'description2', 'FOR_PREPARATION', 10024, 10035, 600, 350);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10071, true, 5, 'description4', 'IN_PROGRESS', 10027, 10036, 150, 50, 10009);

insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense) values
    (10040, true, 5, 'description4', 'FOR_PREPARATION', 10025, 10036, 600, 450);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10041, true, 1, 'description3', 'IN_PROGRESS', 10026, 10036, 600, 450, 10009);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10042, true, 5, 'description4', 'READY', 10028, 10036, 100, 50, 10009);

insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10057, true, 5, 'description4', 'DELIVERED', 10025, 10056, 600, 450, 10009);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10058, true, 1, 'description3', 'DELIVERED', 10026, 10056, 600, 450, 10009);
insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10059, true, 5, 'description4', 'DELIVERED', 10027, 10056, 150, 50, 10009);

insert into order_item (id, active, amount, description, status, menu_item_id, order_id, current_price, current_expense, worker_id) values
    (10077, true, 5, 'description4', 'READY', 10070, 10037, 1100, 700, 10009);


insert into price (id, active, date, value, expense, menu_item_id) values
    (10044, true, 1637247604, 350, 200, 10023);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10045, true, 1637247604, 600, 350, 10024);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10046, true, 1637247604, 600, 450, 10025);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10047, true, 1637247604, 600, 450, 10026);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10048, true, 1637247604, 150, 50, 10027);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10049, true, 1637247604, 100, 50, 10028);
insert into price (id, active, date, value, expense, menu_item_id) values
    (10059, true, 1637247604, 1100, 700, 10070);

insert into salary (id, active, value, date, user_id) values (10050, true, 10000, 1637247604, 10008);
insert into salary (id, active, value, date, user_id) values (10051, true, 20000, 1637227604, 10008);
insert into salary (id, active, value, date, user_id) values (10052, true, 30000, 1637297604, 10008);

insert into salary (id, active, value, date, user_id) values (10053, true, 10000, 1637247604, 10009);
insert into salary (id, active, value, date, user_id) values (10054, true, 20000, 1637227604, 10009);
insert into salary (id, active, value, date, user_id) values (10055, true, 30000, 1637297604, 10009);
