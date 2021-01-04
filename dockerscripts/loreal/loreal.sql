USE [master];
GO

CREATE TABLE users (
    userid varchar(255),
	username varchar(255),
    password varchar(255),
   
);

CREATE TABLE todos (
	userid varchar(255),
	todoid varchar(255),
	createdat datetime2,
	completed BIT,
	description varchar(max)
);
GO

insert into users(userid,username,password) values ('874bbeb6-c8c4-4d64-b2b7-a64a022c72db' , 'loreal' , 'y');
GO
