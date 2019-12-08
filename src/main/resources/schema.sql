create table users(
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


/* PRODUCTION TABLES
CREATE TABLE stacks (
    stackid INT PRIMARY KEY AUTO_INCREMENT,
    stackname VARCHAR(255) NOT NULL,
    USER VARCHAR(255) NOT NULL
);


CREATE TABLE cards (
    cardid INT PRIMARY KEY AUTO_INCREMENT,
    stackid INT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    isCorrect BOOLEAN NOT NULL DEFAULT 0,
    wrongness INT NOT NULL DEFAULT 1,
    FOREIGN KEY (stackid) REFERENCES stacks(stackid)
);
 */
