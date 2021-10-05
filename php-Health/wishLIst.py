import mysql.connector

mydb = mysql.connector.connect(
  host="mysql02.domainhotelli.fi",
  port="3306",
  user="gwyokupv_visitor",
  password="visitor07",
  database="gwyokupv_cv",
  #table = "moto"
)

mycursor = mydb.cursor()

def crateTable():
  mycursor.execute("CREATE TABLE newJob (id INT AUTO_INCREMENT PRIMARY KEY, vaatimus VARCHAR(150), peruste VARCHAR(255), taso VARCHAR(20))")

def savetus():
    tiedosto = open("vaatimukset.txt", "w")
    mycursor.execute("SELECT * FROM newJob")
    myresult = mycursor.fetchall()
    for x in myresult:
      tiedosto.write(str(x)+"\r\n")
    tiedosto.close()

def lue():
  mycursor.execute("SELECT * FROM newJob")
  myresult = mycursor.fetchall()
  for x in myresult:
    print(x)
    savetus()

def poistaVaatimus():
  print("Vaatimus numero?")
  sql = ("""DELETE FROM newJob WHERE id=%s""")
  num = input()
  mycursor.execute(sql, (num,))
  mydb.commit()
  #print(mycursor.rowCount, " record deleted")


def add():
  print("Vaatimus?")
  v = input()
  print("Peruste?")
  p = input()
  print("Taso, toive, must?")
  t = input()
  sql = "INSERT INTO newJob (vaatimus, peruste, taso) VALUES (%s, %s, %s)"
  val = (v,p,t)
  mycursor.execute(sql, val)
  mydb.commit()
  print(mycursor.rowcount, "Vaatimus luotu")

  print("Uusi vaatimus (k)?")
  k = input()
  if (k == "k"):
    add()
  else:
      exit(0)

#Pääohjelman suoritus alkaa    
kysymys = input("Lisää vaatimus (1) lue vaatimukset (2), poista vaatimus (3)?")

if (kysymys == "1"):
  add()

elif( kysymys == "2"):
  lue()

elif (kysymys == "3"):
  poistaVaatimus()

'''
for x in mycursor:
  print(x)


mycursor.execute("SELECT * FROM moto")

myresult = mycursor.fetchall()

for x in myresult:
  print(x)
'''  