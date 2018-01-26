/* 
	using for the whole exercising 
	login:	mysql -h localhost -u root -p
*/

show databases;		--show the all databases

create database imooc;		--create databases;

drop database imooc;		-- delete databases

show engines;		--show 数据库存储引擎
show columns from customers;	-- equal describe customers
show status; 	--show the status of service
show create database imooc; 	--show how to create the database of imooc 
show create table customers; 	--show how to create the table of customers
show grants;	--show the security permissions
show errrors;
show warnings;


create table customers		-- create table
(
	cust_id int not null auto_increment,
	cust_name char(50) not null,
	cust_address char(50) null,
	cust_city char(50) not null default "China",
	cust_email char(220) null,
	primary key(cust_id)
)engine=Innodb;

describe customers;		--查看表结构

drop table customers;		--delete this table

/* retrieve data */
select * from customers;
select customers.cust_id,customers.cust_name from imooc.customers; --can use limited name

select * from customers limit 5; 	--return to the first five lines, begin from the zero
select * from customers limit 5 5;	--the first number 5 is start position,the secend number 5 is retrieve number
select * from customers order by cust_name desc,cust_city;	--desc(descend),default->asc(ascending)

select * from customers where cust_id betweem 2 and 5;	--: = equal ==,<> equal !=,< equal <,....,between ? and ? equal ? < result <?, what's more : and equal &&, or equal ||, not equal !, in...
select * from customers where cust_id not in (4,5);

select * from customers where cust_name like '%jake%';	-- % matching any number's word
select * from customers where cust_name  like '_jake';	-- _ jsut matching one word
select * from customers where cust_name regexp 'jake\\.' -- regexp matching








