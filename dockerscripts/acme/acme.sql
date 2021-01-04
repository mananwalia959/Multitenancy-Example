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


insert into users(userid,username,password) values ('955d5e6b-6a9f-43f5-8c57-7350839fce1d' , 'acme' , 'x');

GO