### All CRUD queries can be added in here for future reference and for easy to prot to other systems ###

# ExcelFile
create table excelfiles(id int(10) primary key, filename varchar(20), content BLOB);

# ArffFile
create table arfffiles(id int(10) primary key, filename varchar(20), content BLOB);

#CsvFile
create table csvfiles(id int(10) primary key, filename varchar(20), content BLOB);