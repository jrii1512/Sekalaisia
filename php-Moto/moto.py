import mysql.connector

mydb = mysql.connector.connect(
  host="mysql02.domainhotelli.fi",
  port="3306",
  user="gwyokupv_visitor",
  password="visitor07",
  database="gwyokupv_Moto",
  #table = "moto"
)

mycursor = mydb.cursor()

mycursor.execute("SHOW TABLES")

for x in mycursor:
  print(x)


mycursor.execute("SELECT * FROM moto")

myresult = mycursor.fetchall()

for x in myresult:
  print(x)