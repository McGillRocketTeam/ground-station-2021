__name__ = "wind_data_request.py"
__author__ = "Celia Hameury"

import datetime
import os
import sys

#NOTE: for all parameters, a value of -9999 means that the value does not exist. 

#Use the datetime package to get time
now = datetime.datetime.utcnow() #Get time right now in utc time
hour_ago = datetime.datetime.utcnow()-datetime.timedelta(hours=5) #Get time one hour ago in utc time
yesterday = datetime.datetime.utcnow()-datetime.timedelta(days=1) #Get time yesterday in utc time

#Set begining and end times 
end_date = now.strftime("%Y %m %d")
begin_date = yesterday.strftime("%Y %m %d")
print(begin_date)

fl=open("USM00072364-data.txt","r")

fl = fl.readlines()
date = begin_date+" 12"
i = 0
for line in fl:
	i = i + 1
	if date in line:
		print (line) 
		#print(i)
		break

j = i
print(j)
num_lines = sum(1 for line in fl)
print(num_lines)

gph = [] #in in m above sea level
wdir = [] #in deg from N
wspd = [] #in m/s
press = [] #in MPa
time = []

while (j < num_lines):
	x1 = fl[j].split()[3]
	x2 = fl[j].split()[7]
	x3 = fl[j].split()[8]
	x4 = fl[j].split()[2]
	x5 = fl[j].split()[1]
	
	if (x1[-1] == 'B'):
		x1 = x1[:-1]
		
	gph.append(int(x1))
	wdir.append(int(x2))
	wspd.append(int(x3)/10)
	time.append(int(x5))
	if (x4[-1]=='B'):
		x4 = x4[:-1]
	press.append(int(x4)/1000)
	
		

	j = j + 1
	
print(gph)
#print(time)
print(wdir)
print(wspd)
#print(press)


