-- All CRUD queries can be added in here for future reference and for easy to prot to other systems ###

-- ExcelFile
create table excelfiles(id int(10) AUTO_INCREMENT, filename varchar(50), filecontent BLOB, primary key(id));

--CsvFile
create table csvfiles(id int(10) AUTO_INCREMENT, filename varchar(50), filecontent BLOB, excelId int(10), primary key(id), FOREIGN KEY (excelId) REFERENCES excelfiles(id));

-- ArffFile
create table arfffiles(id int(10) AUTO_INCREMENT, filename varchar(50), filecontent BLOB, excelId int(10),csvid int(10), primary key(id), FOREIGN KEY (excelId) REFERENCES excelfiles(id),FOREIGN KEY (csvid) REFERENCES csvfiles(id));

---adding rowcount column in excelfiles table
alter table excelfiles add column rowcount int(10);

--Csv RowCount
alter table csvfiles add column rowcount integer(1000);

--Arff RowCount
alter table arfffiles add column rowcount integer(1000);

--Excel attributeCount, active status
-- This query can be used after the implementation of activestatus logic
--alter table excelfiles add activestatus bit;
alter table excelfiles add colcount int(10);

--ModelFile
create table modelfiles(id int(10) AUTO_INCREMENT, filename varchar(50), filecontent BLOB, arffId int(10),datacount int(10),colcount int(10), primary key(id), FOREIGN KEY (arffId) REFERENCES arfffiles(id));

--Arff ColumnCount 
alter table arfffiles add column colcount integer(1000); 

--Roles
create table roles(id int(8) AUTO_INCREMENT, rolename varchar(50), primary key(id));

--User
create table user(id int(8) AUTO_INCREMENT, username varchar(50), password varchar(50), roleId int(8), primary key(id), FOREIGN KEY (roleId) REFERENCES roles(id));

--Roles
insert into roles values(1,'admin')
insert into roles values(2,'user')

--User
insert into user values(1, 'user','user',2)
insert into user values(2, 'admin','admin',1)
