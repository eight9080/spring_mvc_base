create table users(
username varchar(50) not null primary key,
password varchar(50) not null,
enabled boolean not null
);

create table authorities(
	username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users
    foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username, authority);

insert into users(username, password, enabled)
values('admin', 'secret', true);

insert into authorities(username, authority)
values('admin', 'ROLE_USER');


create table permissions(
          username varchar(50) not null,
          target varchar(50) not null,
          permission varchar(50) not null,
          constraint fk_permissions_users
          foreign key(username) references users(username)
);

create unique index ix_perm_username on permissions (username, target, permissions);

insert into permissions(username, target, permission)
values ("admin", "com.pluralsight.model.Goal", "createGoal");