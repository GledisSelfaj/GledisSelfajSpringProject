Create table user(
    id       int auto_increment Primary Key,
    username varchar(50) default null,
    unique (username),
    password varchar(50) default null,
    roles    varchar(50) default null
);
CREATE TABLE bank_account(
    bank_account_id INT AUTO_INCREMENT PRIMARY KEY,
    Iban            VARCHAR(50) DEFAULT NULL,
    currency        VARCHAR(50) DEFAULT NULL,
    balance         decimal     DEFAULT NULL,
    userID          INT,
    UNIQUE (iban),
    FOREIGN KEY (userID) REFERENCES User (id)
);
CREATE TABLE card(
    id              int AUTO_INCREMENT Primary key,
    bank_account_id int         default null,
    card_holder     varchar(50) default null,
    card_type       varchar(50) default null,
    card_number     varchar(50) default null,
    UNIQUE (bank_account_id, card_holder, card_number),
    FOREIGN KEY (bank_account_id) REFERENCES bank_account (bank_account_id)
);
create table transaction(
    id              int auto_increment primary key,
    amount          decimal     default null,
    currency        varchar(50) default null,
    type            varchar(50) default null,
    bank_account_id int         default null,
    foreign key (bank_account_id) references bank_account (bank_account_id)
);
create table request(
    id              int auto_increment primary key,
    bank_account_id int         default null,
    salary          decimal     default null,
    feedback        varchar(50) null,
    unique (bank_account_id),
    foreign key (bank_account_id) references bank_account (bank_account_id),
    status          varchar(50) default null
);