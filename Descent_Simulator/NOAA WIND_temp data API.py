# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
import requests as req
import numpy as np
import matplotlib.pyplot as plt
# URL to Aviation Weather Center's page of Wind/Temp data for all airports
url = ("""https://www.aviationweather.gov/windtemp/data?
     level=low&fcst=06&region=all&layout=off&date=""")

# Request page and slice to useful part
r = req.get(url)
f = r.text
f = f[f.find("raw data begins here"): f.find("raw data ends here")]

# Make lsit of cities string with their data
citylines = f[f.find("ABI"): f.find("2XG")].split('\n')

# Make empty list to for full data
citylist = []
# Make list of keys for dictionary
citykeys = []

# Convert strings into numerical data and assign to citylist
for x in range(len(citylines)-1):

    # Get the city's datastring
    citydata = citylines[x]

    # Split by altitude level
    # If data at altitude step 3000 (lenght of 4) is blank, split as empty list
    citydata = citydata.split('   ', 2)
    # If data at higher altitude (lenght of 7) is blank, split as empty lists
    y = (len(citydata))-1
    citydata[y:] = citydata[y].split('        ')
    # If another data chunk has length 4, split and strip remaining blanks
    y = (len(citydata))-1
    citydata[y:] = citydata[y].strip().split('   ')
    # Split remaining data chunks based on the blank between them, strip blanks
    y = (len(citydata))-1
    citydata[y:] = citydata[y].strip().split(' ')

    # Put City abbreviation in the dictionary keys list
    citykeys.append(citydata.pop(0))

    # Make list of data by altitude
    a = []

    # Assign data by altitude
    for z in range(len(citydata)):

        # Make array for an altitude Wind direction, speed and temp data
        altdata = np.zeros((1, 3))
        strlen = len(citydata[z])

        if strlen == 0:
            pass

        else:
            altdata[0, 0] = np.int(citydata[z][0:2])
            altdata[0, 1] = np.int(citydata[z][2:4])

            if strlen == 4:
                pass

            elif strlen == 7:
                altdata[0, 2] = np.int(citydata[z][4:])

            elif strlen == 6:
                altdata[0, 2] = -np.int(citydata[z][4:])
        a.append(altdata)
    citylist.append(a)


# Assign city abbrevation to position in table
city = dict(zip(citykeys, range(len(citylines))))


# Translate data from Wind/Temp format to SI
def cwindtemp(cityname, cartesian='No', table=citylist, city=city):

    data = table[city[cityname]]
    altlist = [3000, 6000, 9000, 12000, 18000, 24000, 30000, 34000, 39000]
    cityplot = np.zeros(((len(data)), 4))

    for x in range(len(data)):

        if data[x][0, 0] < 40:
            WindDir = np.radians(10*data[x][0, 0])
            WindSpeed = 0.51444*data[x][0, 1]

        else:
            WindDir = np.radians(10*(data[x][0, 0]-50))
            WindSpeed = 0.51444*(data[x][0, 1]+100)

        # Keep data in polar coordinates
        if cartesian == 'No':
            data1 = WindDir
            data2 = WindSpeed

        # Translate data in cartesian coordinates
        elif cartesian == 'Yes':
            # Wind Speed in x (East) direction
            data1 = WindSpeed * np.cos(WindDir)
            # Wind Speed in y (North) direction
            data2 = WindSpeed * np.sin(WindDir)
        print(data1)
        cityplot[x, 0] = altlist[x]
        # wind direction in radians
        cityplot[x, 1] = data1
        # Wind speed in km/h
        cityplot[x, 2] = data2
        # Temperature in Celsius
        cityplot[x, 3] = data[x][0, 2]

    return cityplot


# Get data for Albuqueque and El Paso
ABQplot = cwindtemp('ABQ')
ELPplot = cwindtemp('ELP')

# Distance of launch site from the cities
ABQdist = 141.81
ELPdist = 233.9

ABQweight = ABQdist/(ABQdist+ELPdist)
ELPweight = ELPdist/(ABQdist+ELPdist)

localplot = np.zeros((8, 4))

for x in range(8):

    localplot[x] = ABQweight*ABQplot[x]+ELPweight*ELPplot[x]

# Test code

yterp=np.interp(range(35000),  localplot[:, 0] , localplot[:, 3])

plt.figure(figsize=(8, 8))
# plt.plot(localplot[:,0], localplot[:,2])
plt.plot(range(35000), yterp)
plt.xlabel('Altitude')
plt.ylabel('Value')
plt.show()





# searchfile = open("r.text", "r")
"""for line in f:
    if "ABQ" in line:
        print (line)"""

# searchfile.close()

"""end = time.time()
print(end - start)"""
