__name__ = "wind_data_request.py"
__author__ = "Celia Hameury"

import datetime
import os
import sys

import pandas as pd

#NOTE: for all parameters, a value of -9999 means that the value does not exist. 
class Wind():

	station_name = '#USM00072364'

	def __init__(self, windDataFile):
		self.windDataFile=windDataFile
		#Use the datetime package to get time
		now = datetime.datetime.utcnow() #Get time right now in utc time
		hour_ago = datetime.datetime.utcnow()-datetime.timedelta(hours=5) #Get time one hour ago in utc time
		yesterday = datetime.datetime.utcnow()-datetime.timedelta(days=1) #Get time yesterday in utc time

		#Set begining and end times
		end_date = now.strftime("%Y %m %d")
		begin_date = yesterday.strftime("%Y %m %d")
		print(begin_date)

		fl = open(windDataFile, "r")
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

		self.gph_lists = []  # in in m above sea level
		self.wdir_lists = []  # in deg from N
		self.wspd_lists = []  # in m/s
		self.press_lists = []  # in MPa
		self.time_lists = []

		temp_gph = []  # in in m above sea level
		temp_wdir = []  # in deg from N
		temp_wspd = []  # in m/s
		temp_press = []  # in MPa
		temp_time = []

		for index, current_set in enumerate(fl[:]):
			# if len(self.gph_lists) == 1312:
			# 	print('testhere')
			val_split = current_set.split()
			if len(val_split) == 11:
				# print('data reset: {}'.format(current_set))
				if index != 0:
					self.gph_lists.append(temp_gph)
					self.wdir_lists.append(temp_wdir)
					self.wspd_lists.append(temp_wspd)
					self.press_lists.append(temp_press)
					self.time_lists.append(temp_time)
				temp_gph = []  # in in m above sea level
				temp_wdir = []  # in deg from N
				temp_wspd = []  # in m/s
				temp_press = []  # in MPa
				temp_time = []
				continue

			if len(val_split) < 9:
				# print('val split less 9: {}\n actual data {}'.format(val_split, current_set))
				continue

			geo_potent_height = val_split[3]
			wind_direction = val_split[7]
			wind_speed = val_split[8]
			air_pressure = val_split[2]
			current_time = val_split[1]

			# if geo_potent_height == -9999 or wind_direction == -9999 or wind_speed == -9999 or air_pressure == -9999 or current_time == -9999:
			# 	print('val has -9999 split:: {}\n actual data:: {}'.format(val_split, current_set))

			if geo_potent_height[-1] == 'B':
				geo_potent_height = geo_potent_height[:-1]


			try:
				t1 = int(geo_potent_height)
				t2 = int(wind_direction)
				t3 = int(wind_speed)
			except:
				continue
			temp_gph.append(int(geo_potent_height))  # this list is the altitude above sea level
			temp_wdir.append(int(wind_direction))  # this is wind direction
			temp_wspd.append(int(wind_speed)/10)	 # wind speed
			temp_time.append(int(current_time))
			if air_pressure[-1] == 'B' or air_pressure[-1] == 'A':
				air_pressure = air_pressure[:-1]
			temp_press.append(int(air_pressure)/1000)
		self.gph_lists.append(temp_gph)
		self.wdir_lists.append(temp_wdir)
		self.wspd_lists.append(temp_wspd)
		self.press_lists.append(temp_press)
		self.time_lists.append(temp_time)
		print('list of lists length:: {}'.format(len(self.gph_lists)))
		# print(len(gph))
		# #print(time)
		# print(len(wdir))
		# print(len(wspd))
		# #print(press)


	def get_wind_data_tuple(self):
		'''
		:return: a tuple (height, wind_dir, wind_speed)
		'''
		return self.clean_data(self.get_wind_data_tuple_select_index(-1))

	def get_wind_data_tuple_select_index(self, index):
		# print('list lengths: gph:: {}\t dir:: {}\t speed:: {}'.format(len(self.gph_lists[index]), len(self.wdir_lists[index]), len(self.wspd_lists[index])))
		return self.gph_lists[index], self.wdir_lists[index], self.wspd_lists[index]

	def get_wind_dataframe(self):
		x = self.get_wind_data_tuple()
		lst = list(zip(x[0][::-1], x[1][::-1], x[2][::-1]))
		df = pd.DataFrame(lst, columns=['altitude', 'direction', 'velocity'])
		return df

	def clean_data(self, input_tuple):
		height, direction, speed = input_tuple
		new_height = []
		new_direction = []
		new_speed = []
		for index, current_h in enumerate(height):
			if height != -9999 and direction[index] != -9999 and speed[index] != -9999:
				new_height.append(height)
				new_direction.append(direction[index])
				new_speed.append(speed[index])
		return new_height[-1], new_direction, new_speed

# wd = Wind()
# # x = wd.get_wind_data_tuple()
# # lst = list(zip(x[0][::-1], x[1][::-1], x[2][::-1]))
# # print(x[0][::-1])
# # df = pd.DataFrame(lst, columns=['altitude', 'direction', 'velocity'])
# d = wd.get_wind_dataframe()
# print(d)

