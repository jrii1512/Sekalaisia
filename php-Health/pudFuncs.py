import os
import pudCon as pC
mycursor = pC.mydb.cursor()

def lueEnsimmainenReisi():
  firstRec = "select id from Pudotus order by id asc limit 1"
  mycursor.execute(firstRec)
  firstIDValue = mycursor.fetchone()
  firstId = firstIDValue[0]
  #print("First record, id value is ", str(firstId))  
  sql = "select reisi from Pudotus where id = %s" % (firstId)
  mycursor.execute(sql)
  ekaReisi = mycursor.fetchone()
  return ekaReisi[0]

def lueEnsimmainenVuotaro():
  firstRec = "select id from Pudotus order by id asc limit 1"
  mycursor.execute(firstRec)
  firstIDValue = mycursor.fetchone()
  firstId = firstIDValue[0]
  #print("First record, id value is ", str(firstId))  
  sql = "select vyotaro from Pudotus where id = %s" % (firstId)
  mycursor.execute(sql)
  ekaVyotaro = mycursor.fetchone()
  return ekaVyotaro[0]

def lueEnsimmainenRinta():
  firstRec = "select id from Pudotus order by id asc limit 1"
  mycursor.execute(firstRec)
  firstIDValue = mycursor.fetchone()
  firstId = firstIDValue[0]
  #print("First record, id value is ", str(firstId))  
  sql = "select rinta from Pudotus where id = %s" % (firstId)
  mycursor.execute(sql)
  ekaRinta = mycursor.fetchone()
  return ekaRinta[0]


def lueEnsimmainenPaino():
  firstRec = "select id from Pudotus order by id asc limit 1"
  mycursor.execute(firstRec)
  firstIDValue = mycursor.fetchone()
  firstId = firstIDValue[0]
  #print("First record, id value is ", str(firstId))  
  sql = "select paino from Pudotus where id = %s" % (firstId)
  mycursor.execute(sql)
  paino = mycursor.fetchone()
  return paino[0]


#Lue edeltävä paino
def lueEdeltava():
  lastrec = "select id from Pudotus order by id desc limit 1"
  mycursor.execute(lastrec)
  lastIDValue = mycursor.fetchone()
  prevId = lastIDValue[0]
  #print("Record to be compared, id value is ", str(prevId))  
  sql = "select paino from Pudotus where id = %s" % (prevId)
  mycursor.execute(sql)
  paino = mycursor.fetchone()
  if (paino[0] == 0.0):
    print("Edellisen painon haussa virhe, saatu arvo ", paino[0])
  return paino[0]
  
#Lue kaikki recordit  
def lue():
  mycursor.execute("SELECT * FROM Pudotus")
  myresult = mycursor.fetchall()
  for x in myresult:
    print(str(x))

#Poista yksittäinen recordi, id:een perusteella
def poistaVaatimus():
  lue()
  print("Poista recordi numero?")
  sql = ("""DELETE FROM Pudotus WHERE id=%s""")
  num = input()
  mycursor.execute(sql, (num,))
  pC.mydb.commit()

#Poista kaikki
def poistaKaikkiVaatimukset():
  sql = ("""DELETE FROM Pudotus""")
  mycursor.execute(sql)
  pC.mydb.commit()
  print("Kaikki recordit poistettu")

def mitat(p, r1, v, r2):
    f = ""
    ekaReisi = lueEnsimmainenReisi()
    print("Eka reisi ", ekaReisi)

    ekaVyotaro = lueEnsimmainenVuotaro()
    print("Eka Vyötärö ", ekaVyotaro)

    ekaRinta = lueEnsimmainenRinta()
    print("Eka rinta ", ekaRinta)

    #Conversiot / analyysit
    #Reisi
    muutosAlustaReisi = float(r1)-float(ekaReisi)
    kehitysReisi = round(muutosAlustaReisi, 2)
    print("Reiden muutos aloituksesta, ", str(kehitysReisi))

    #Vyötärö
    muutosAlustaVyotaro = float(v)-float(ekaVyotaro)
    kehitysVyotaro = round(muutosAlustaVyotaro, 2)
    print("Vyötärön muutos aloituksesta, ", str(kehitysVyotaro))
    
    #Rinta
    muutosAlustaRinta = float(r2)-float(ekaRinta)
    kehitysRinta = round(muutosAlustaRinta, 2)
    print("Rinnan muutos aloituksesta, ", str(kehitysRinta))

    #Annettu painolukema
    print("Syötit painoksi ", str(p))

    #Lue ensimmäiset recordit 
    ekaPaino = lueEnsimmainenPaino()
    print("Eka paino ", ekaPaino)
    #Lue edeltävä paino, vain painoa trackataan päivittäin
    prevPaino = lueEdeltava()
    print("Edellinen paino ", prevPaino)
  

    #Conversiot / muutokset / analyysit
    #Muutos aloituksesta + floatin pyöristys
    muutosAlustaPaino = float(p)-float(ekaPaino)
    kehitysPaino = round(muutosAlustaPaino, 2)
    print("Muutos aloituksesta, ", str(kehitysPaino))
    muutosPaino = float(p)-float(prevPaino)
    changePaino = round(muutosPaino, 2)
    print("Painon muutos edellisestä mittauksesta ", str(changePaino))
    if (muutosPaino < 0 or muutosAlustaReisi < 0 or muutosAlustaRinta < 0 or muutosAlustaVyotaro <0):
      f = "Pudotusta, uudet mitat:<br>Paino edellisesta " + str(changePaino) + " kg" + ", aloituksesta " + str(kehitysPaino) + " kg,"   + "<br>Aloituksesta reisi: " + str(kehitysReisi) + " cm," + "<br>vyotaro: " + str(kehitysVyotaro) + " cm," + "<br>rinta: " + str(kehitysRinta) + " cm"
    elif (muutosPaino == 0):
      f = "Ei muuutosta"
    else:
      f = "Nousua, uudet mitat:<br>Paino edellisesta " + str(changePaino) + " kg" + ", aloituksesta " + str(kehitysPaino) + " kg,"   + "<br>Aloituksesta reisi: " + str(kehitysReisi) + " cm," + "<br>vyotaro: " + str(kehitysVyotaro) + " cm," + "<br>rinta: " + str(kehitysRinta) + " cm"
    print(f)
    kantaan(p, r1, v, r2, f)
    os._exit(0) 


#Laskentakaavat yms
def analyysi(muut, p, r1, v, r2):
  f = ""
  ##Jos reisi on annettu, niin sitten luetaan ensimmäisen recordin reiden, vyötärön ja rinnan arvot
  if ( muut == 1 ):
    mitat(p, r1, v, r2)
  exit

  prevPaino = lueEdeltava()
  ekaPaino = lueEnsimmainenPaino()
  muutosAlustaPaino = float(p)-float(ekaPaino)
  kehitysPaino = round(muutosAlustaPaino, 2)
  print("Muutos aloituksesta, ", str(kehitysPaino))
  muutosPaino = float(p)-float(prevPaino)
  changePaino = round(muutosPaino, 2)

    #Ilmoitetaan vain painon muutos
  if (muut == 0 and muutosPaino < 0):
    f = "Paino pudonnut eilisestä, " + str(changePaino) + " kg" + ", aloituksesta " + str(kehitysPaino) + " kg"
    print(f)
  elif (muut == 0 and muutosPaino > 0):
    f = "Paino noussut, " + str(changePaino) + " kg" + ", aloituksesta " + str(kehitysPaino) + " kg"
    print(f)
  kantaan(p, r1, v, r2, f)


def kantaan(p, r1, v, r2, f):
  print("paino: "+ str(p) + ", reisi: "+ str(r1) + ", vyötärö:"+ str(v) +", rinta: "+ str(r2)+ ", tulokset "+ f)    
  sql = "INSERT INTO Pudotus (paino, reisi, vyotaro, rinta, tulos ) VALUES (%s, %s, %s, %s, %s)"
  val = (p, r1,v,r2,f)
  mycursor.execute(sql, val)
  pC.mydb.commit()
  print(mycursor.rowcount, "Recordi inessä, tietokannan sisältö")      
  lue()


def ilmoita():
  print("Paino?")
  p = input()
  print("Haluatko ilmoittaa cm mitat (k, e)?")
  c = input()
  if (c == "k"):
    muut = 1
    print("Reisi?")
    r1 = input()
    print("Vyötärö?")
    v = input()
    print("Rinta?")
    r2 = input()
  elif (c == "e"):
    muut = 0
    r1 = 0
    r2 = 0
    v = 0  
  analyysi(muut, p, r1, v, r2)

def kyssari():
    kysymys = input("Ilmoita uusi (1),\nlue olemassa olevat (2),\npoista ilmoitus (3),\npoista kaikki ilmoitukset (4)\n?")
    if (kysymys == "1"):
        ilmoita()
    elif( kysymys == "2"):
        lue()
    elif (kysymys == "3"):
        poistaVaatimus()
    elif (kysymys == "4"):
        poistaKaikkiVaatimukset()
    


