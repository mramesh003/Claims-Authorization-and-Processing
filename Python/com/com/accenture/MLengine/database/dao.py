import com.accenture.MLengine.database.db_connection as db

class daoClass:

    def load_Model(self):
        connection = db_conn.dbconnection()
        cursor = connection.cursor()
        query1 = ("SELECT filecontent from modelfiles where flag=%s order by id DESC limit 1")
        cursor.execute(query1, ('python',))
        data = cursor.fetchone()
        return data;

    def save_Model(self,csv_list,modelcontent):
        connection = db_conn.dbconnection()
        cursor = connection.cursor()
        cursor.execute("insert into modelfiles(filename,filecontent,datacount,colcount,flag) values(%s,%s,%s,%s,%s)",(csv_list[0], modelcontent, csv_list[2], csv_list[1], "python"))
        connection.commit()

    def csv_Retrival(self,fileid):
     connection = db_conn.dbconnection()
     cursor = connection.cursor()
     query1 = ("SELECT * from csvfiles where id = %s")
     cursor.execute(query1, (fileid,))
     data = cursor.fetchone()
     return data;

#Object creation
db_conn = db.db_Connection()