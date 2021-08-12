# -*- coding: utf-8 -*-
"""
Created on Sun Nov 10 14:13:52 2019

@author: sunge
"""
import wind_data_code as wind_code
import numpy as np
import pandas as pd
from MCS import MCGenerator
from launch_run import Launch
from RocketProperties import RocketProperties
from mpl_toolkits import mplot3d
import matplotlib.pyplot as plt

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
                wind_data['velocity'][index], sigma_velocity, 1) # velocity
            index_2 += 1
        index += 1
        #wind_data[num+launches][altitude/direction/velocity][index]
    return wind_data_perturbed

#===================================Main=======================================
#wind_data = pd.DataFrame(columns= ['altitude','direction','velocity']) #input wind data

def main(num_launches):
    print("Running {} simulations".format(num_launches))
    launch_zenith_angle_in = 0  # from normal to rocket
    sigma_zenith_angle = 0
    zenith_angle_perturbed = np.random.normal(launch_zenith_angle_in, sigma_zenith_angle, num_launches)

    launch_azimuth_angle_in = 0  # from north to rocket
    sigma_azimuth_angle = 0
    azimuth_angle_perturbed = np.random.normal(launch_azimuth_angle_in, sigma_azimuth_angle, num_launches)

    drogue_deploy_altitude_in = 0
    wind_dataframe_in = 0

    drag_coeff_drogue_in = 0
    drag_coeff_main_in = 0
    apogee_in = 0

    # ------------------------Sample Code-------------------------------------------
    w_al = np.array([80, 60, 40, 20, 0])
    inputw_n = np.array([4, 1, 7, 1])
    inputa_n = np.array([1, 0.1])
    #mcs = MCGenerator()

    #wind = mcs.MCS(inputa_n, w_al, inputw_n, num_cycles=1)

    w_class = wind_code.Wind()
    wind = w_class.get_wind_dataframe()

    x = perturbWind(wind, 1, 2, num_launches)

    rocketProperties = RocketProperties()

    landing_location = []

    for simNumber in range(num_launches):
        sim = Launch(zenith_angle_perturbed[1], azimuth_angle_perturbed[1], x[simNumber], rocketProperties)
        lat, lon, positions = sim.run_launch()
        landing_location.append([lat, lon])

        # ------------------------------------------TESTING START----------------------------------------------------- #

        # GRAPH THE SIMULATED LATITUDE VS SIMULATED LONGITUDE VS TIME OF EACH SIMULATION #
        positions = np.array(positions)
        latData = positions[:, 0]
        lonData = positions[:, 1]
        altData = positions[:, 2]
        timeData = positions[:, 3]

        graph = plt.axes(projection='3d')
        graph.plot3D(timeData, latData, lonData, 'red')
        
        graph.set_title("Simulation " + str(simNumber + 1) + " Lat vs Lon vs Time")
        graph.set_xlabel("Time")
        graph.set_ylabel("Latitude")
        graph.set_zlabel("Longitude")
        graph.set_title("Simulation " + str(simNumber + 1) + " Lat vs Lon vs Time")
        graph.set_xlabel("Time")
        graph.set_ylabel("Latitude")
        # plt.savefig("simulation" + str(simNumber + 1) + "LatVsLonVsTimeGraph.png")
        plt.show()

        # GRAPH THE SIMULATED LATITUDE VS SIMULATED LONGITUDE OF EACH SIMULATION #
        plt.plot(latData, lonData)
        plt.title('Simulation ' + str(simNumber + 1) + ' Lat vs Lon')
        # plt.savefig("simulation" + str(simNumber + 1) + "LatVsLonGraph.png)
        plt.show()

        # GRAPH THE SIMULATED ALTITUDE VS TIME OF EACH SIMULATION #
        plt.plot(timeData, altData, 'red')
        plt.title('Simulation ' + str(simNumber + 1) + ' Altitude vs Time')
        # plt.savefig('simulation' + str(simNumber + 1) + 'AltitudevsTimeGraph.png)
        plt.show()

    # GRAPH THE REAL DATA OF BLANCHE LATITUDE VS LONGITUDE VS TIME #

    with open('BlancheLat.txt', 'r') as f:
        blancheLat = f.readlines()
    blancheLat = [x.strip() for x in blancheLat]

    with open('BlancheLon.txt', 'r') as f:
        blancheLon = f.readlines()
    blancheLon = [x.strip() for x in blancheLon]

    with open('BlancheTime.txt', 'r') as f:
        blancheTime = f.readlines()
    blancheTime = [x.strip() for x in blancheTime]

    for i in range(1272):
        blancheLat[i] = float(blancheLat[i])
        blancheLon[i] = float(blancheLon[i])
        blancheTime[i] = float(blancheTime[i])

    blancheLatData = np.array(blancheLat)
    blancheLonData = np.array(blancheLon)
    blancheTimeData = np.array(blancheTime)

    blancheGraph = plt.axes(projection='3d')
    blancheGraph.plot3D(blancheTimeData, blancheLatData, blancheLonData, 'blue')

    blancheGraph.set_title("Blanche Lat vs Lon vs Time")
    blancheGraph.set_xlabel("Time")
    blancheGraph.set_ylabel("Latitude")
    blancheGraph.set_zlabel("Longitude")
    # plt.savefig("BlancheLatVsLonVsTimeGraph.png")
    plt.show()

    # GRAPH THE ACTUAL LANDING LOCATION VS THE SIMULATED LANDING LOCATION #
    blancheLandingLocation = [32.945004, -106.89997]
    landing_location = np.array(landing_location)
    plt.plot(landing_location[:, 0], landing_location[:, 1], 'o', color='red', label='Simulated Landing Location')
    plt.plot(blancheLandingLocation[0], blancheLandingLocation[1], 'o', color='black', label='Blanche Landing Location')
    plt.legend()
    # plt.savefig("SimulatedVsBlancheLandingLocation.png")
    plt.show()

    # ----------------------------------------------TESTING END------------------------------------------------------- #

    save_file = False  # If you want to save a csv file of the coordinates
    if save_file:
        np.savetxt("validation_compare_with_Blanche.csv", landing_location, delimiter=",")
        print("FIN CSV READY")






