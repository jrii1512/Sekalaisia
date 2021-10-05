#ssh -L 3306:mysql02.domainhotelli.fi:3306 gwyokupv@hotelli02.domainhotelli.fi
import os
import mysql.connector
mydb = mysql.connector.connect(
  host="mysql02.domainhotelli.fi",
  port="3306",
  user="gwyokupv_visitor",
  password="visitor_07",
  database="gwyokupv_terveys",
)

#password="visitor07",

def ssh():
  ssh = "ssh -L 3306:mysql02.domainhotelli.fi:3306 gwyokupv@hotelli02.domainhotelli.fi"
  os.system(ssh)
