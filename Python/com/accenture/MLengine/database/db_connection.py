import mysql.connector

class db_Connection:

    def dbconnection(self):
        connection = mysql.connector.connect(user='user1', password='user1',
                                      host='10.220.103.167',
                                      database='claimsauthprocess1')
        if connection.is_connected():
            return connection;