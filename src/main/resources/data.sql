insert into users (username, password, enabled) values (
    'admin',
    'password',
    true
);

insert into authorities (username, authority) values(
    'admin',
    'ROLE_ADMIN'
);
