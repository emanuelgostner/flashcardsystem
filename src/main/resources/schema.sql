CREATE DATABASE IF NOT EXISTS massedterm;

CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(128) PRIMARY KEY,
    password VARCHAR(128) NOT NULL,
    enabled CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(128) NOT NULL,
    authority VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS stacks  (
    stackid INT PRIMARY KEY AUTO_INCREMENT,
    stackname VARCHAR(255) NOT NULL,
    USER VARCHAR(255) NOT NULL,
    deleted BOOLEAN DEFAULT 0,
    FOREIGN KEY (USER ) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS cards  (
    cardid INT PRIMARY KEY AUTO_INCREMENT,
    stackid INT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    isCorrect BOOLEAN NOT NULL DEFAULT 0,
    wrongness INT NOT NULL DEFAULT 1,
    FOREIGN KEY (stackid) REFERENCES stacks(stackid)
);

CREATE TABLE IF NOT EXISTS rounds  (
    roundid INT PRIMARY KEY AUTO_INCREMENT,
    stackid INT NOT NULL,
    timestamp datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    USER VARCHAR(255) NOT NULL,
    FOREIGN KEY (stackid) REFERENCES stacks(stackid)
);

CREATE TABLE IF NOT EXISTS responses  (
    responseid INT PRIMARY KEY AUTO_INCREMENT,
    roundid INT NOT NULL,
    cardid INT NOT NULL,
    isCorrect BOOLEAN NOT NULL DEFAULT 0,
    timestamp datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    USER VARCHAR(255) NOT NULL,
    FOREIGN KEY (roundid) REFERENCES rounds(roundid),
    FOREIGN KEY (cardid) REFERENCES cards(cardid)
);
/*insert into users (username, password, enabled) values (
    'admin',
    'password',
    true
);

insert into authorities (username, authority) values(
    'admin',
    'ROLE_ADMIN'
);*/


-- ALTER TABLE authorities ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (username, authority);
-- ALTER TABLE authorities ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (username) REFERENCES users (username) ENABLE;

/*
create table users (
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(50) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
*/