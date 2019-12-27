# -*- coding: utf-8 -*-
"""
Created on Sun Nov 10 14:13:52 2019

@author: sunge
"""
import wind_data_code as wind_code
import numpy as np
import pandas as pd
from MCS import MCGenerator
#This is the new Monte Carlo
def perturbWind(wind_data, sigma_direction, sigma_velocity, num_launches_u):
    index = 0
    index_2 = 0
    wind_data_perturbed = np.zeros((num_launches_u, wind_data.shape[1], wind_data.shape[0]))
    for element in wind_data['altitude']:
        for index_2 in range(num_launches_u):
            wind_data_perturbed[index_2][0][index] = element # altitude
            wind_data_perturbed[index_2][1][index] = np.random.normal(
                wind_data['direction'][index], sigma_direction, 1) # direction
            wind_data_perturbed[index_2][2][index] = np.random.normal(
                wind_data['velocity'][index], sigma_velocity, 1) # direction
            index_2 += 1
        index += 1
        #wind_data[num+launches][altitude/direction/velocity][index]
    return wind_data_perturbed

#===================================Main=======================================
#wind_data = pd.DataFrame(columns= ['altitude','direction','velocity']) #input wind data

num_launches = 100

launch_zenith_angle_in = 0      #from normal to rocket
sigma_zenith_angle = 0
zenith_angle_perturbed = np.random.normal(launch_zenith_angle_in, sigma_zenith_angle, num_launches)

launch_azimuth_angle_in = 0     #from north to rocket
sigma_azimuth_angle = 0
azimuth_angle_perturbed = np.random.normal(launch_azimith_angle_in, sigma_azimith_angle, num_launches)

drogue_deploy_altitude_in = 0
wind_dataframe_in = 0

drag_coeff_drogue_in = 0
drag_coeff_main_in = 0
apogee_in = 0

#------------------------Sample Code-------------------------------------------
w_al = np.array([80, 60, 40, 20, 0])
inputw_n = np.array([4, 1, 7, 1])
inputa_n = np.array([1, 0.1])
mcs = MCGenerator()

wind = mcs.MCS(inputa_n,w_al,inputw_n, num_cycles=1)
w_class = wind_code.Wind()
wind = w_class.get_wind_dataframe()

x = perturbWind(wind, 1, 2, num_launches)
