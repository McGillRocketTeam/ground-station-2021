__name__ = "wind_data_request.py"
__author__ = "Celia Hameury"

import datetime
import os
import sys

#NOTE: for all parameters, a value of -9999 means that the value does not exist. 
class Wind():

	def __init__(self):
		#Use the datetime package to get time
		now = datetime.datetime.utcnow() #Get time right now in utc time
		hour_ago = datetime.datetime.utcnow()-datetime.timedelta(hours=5) #Get time one hour ago in utc time
		yesterday = datetime.datetime.utcnow()-datetime.timedelta(days=1) #Get time yesterday in utc time

		#Set begining and end times
		end_date = now.strftime("%Y %m %d")
		begin_date = yesterday.strftime("%Y %m %d")
		print(begin_date)

		fl = open("./USM00072364_data.txt", "r")

		fl = fl.readlines()
		date = begin_date+" 12"
		i = 0
		for line in fl:
			i = i + 1
			if date in line:
				print(line)
				#print(i)
				break

		j = i
		print(j)
		num_lines = sum(1 for line in fl)
		print(num_lines)

		self.gph = []  # in in m above sea level
		self.wdir = []  # in deg from N
		self.wspd = []  # in m/s
		self.press = []  # in MPa
		self.time = []

		for index, current_set in enumerate(fl[1:]):

			val_split = current_set.split()

			if len(val_split) < 9:
				continue

			geo_potent_height = val_split[3]
			wind_direction = val_split[7]
			wind_speed = val_split[8]
			air_pressure = val_split[2]
			current_time = val_split[1]

			if geo_potent_height[-1] == 'B':
				geo_potent_height = geo_potent_height[:-1]


			try:
				t1 = int(geo_potent_height)
				t2 = int(wind_direction)
				t3 = int(wind_speed)
			except:
				continue
			self.gph.append(int(geo_potent_height))  # this list is the altitude above sea level
			self.wdir.append(int(wind_direction))  # this is wind direction
			self.wspd.append(int(wind_speed)/10)	 # wind speed
			self.time.append(int(current_time))
			if air_pressure[-1] == 'B' or air_pressure[-1] == 'A':
				air_pressure = air_pressure[:-1]
			self.press.append(int(air_pressure)/1000)

		print(len(self.gph))
		#print(time)
		print(len(self.wdir))
		print(len(self.wspd))
		#print(press)

	def get_wind_data_tuple(self):
		'''
		:return: a tuple (height, wind_dir, wind_speed)
		'''
		return self.gph, self.wdir, self.wspd


# wd = Wind()
# x = wd.get_wind_data_tuple()
# print(x)
