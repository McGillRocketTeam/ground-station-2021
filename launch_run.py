import numpy as np
import pandas as pd

class Launch():
    air_ideal_gas_constant = 287.05 #J/K*kg
    kPa_2_Pa = 1000
    rocket_mass = 100 #kg
    gravity_constant = 9.807 #m/s2
    time_step = 1 #s
    
    def __init__(self, launch_zenith_angle_in, launch_azimith_angle_in, drogue_deploy_altitude_in, main_deploy_altitude_in,
                 wind_dataframe_in, vertical_drag_coeff_drogue_in, vertical_drag_coeff_main_in, apogee_in, drogue_deploys_in, main_deploys_in,
                 transverse_drag_coeff_drogue_in, transverse_drag_coeff_main_in, velocity_off_rail_mag_in):
        self.launch_zenith_angle = launch_zenith_angle_in
        self.launch_azimith_angle = launch_azimith_angle_in
        self.drogue_deploy_altitude = drogue_deploy_altitude_in
        self.main_deploy_altitude = main_deploy_altitude_in
        self.wind_dataframe = wind_dataframe_in
        self.vertical_drag_coeff_drogue = vertical_drag_coeff_drogue_in
        self.vertical_drag_coeff_main = vertical_drag_coeff_main_in
        self.apogee = apogee_in
        self.drogue_deploys = drogue_deploys_in #boolean
        self.main_deploys = main_deploys_in #boolean
        self.transverse_drag_coeff_drogue = transverse_drag_coeff_drogue_in
        self.transverse_drag_coeff_main = transverse_drag_coeff_main_in
        self.velocity_off_rail_mag = velocity_off_rail_mag_in
    
    
    def vertical_drag_coeff(self, altitude):
        if altitude > self.drogue_deploy_altitude:
            return 0
        elif altitude > self.main_deploy_altitude and self.drogue_deploys == True:
            return self.vertical_drag_coeff_drogue
        elif altitude <= self.main_deploy_altitude and :
            return self.vertical_drag_coeff_main
        
    def transverse_drag_coeff(self): 
        if altitude > self.drogue_deploy_altitude:
            return 0 #assume negligible effect of wind prior to parachute deployment; not great, but not awful
        elif altitude > self.main_deploy_altitude and self.drogue_deploys == True:
            return self.transverse_drag_coeff_drogue
        else:
            return self.transverse_drag_coeff_main
        
    def calculate_temperature(self, altitude): #K
        if altitude < 11000:
            return 15.04-0.00649*altitude
        elif altitude < 25000:
            return -56.46
        else:
            return -131.21 + 0.00299*altitude


    def calculate_pressure(self, altitude): #Pa
        if altitude < 11000:
            return self.kPa_2_Pa*101.29*((self.calculate_temperature(altitude) +273.1)/288.08)**5.256
        elif altitude < 25000:
            return self.kPa_2_Pa*22.65 * np.exp(1.73-0.000157*altitude)
        else:
            return self.kPa_2_Pa*2.488*((self.calculate_temperature(altitude)+273.1)/216.6)**-11.388
        
        
    def calculate_density(self,altitude): #kg/m3
        return self.calculate_pressure(altitude)/ (self.air_deal_gas_constant*self.calculate_temperature(altitude))
    
    
    def calculate_descent_rate(self,altitude): #m/s
        Cd = self.vertical_drag_coeff(altitude)
        if Cd != 0:
            return np.sqrt(2*self.rocket_mass*self.gravity_cosntant/(self.calculate_density(altitude) * Cd))
        else:
            return np.sqrt(2*self.gravity_constant*(self.apogee-altitude))
        
    def calculate_wind(altitude):
        
    def calculate_force(altitude):
        Cd = 
        forceVec 
    
    def run_launch(self):
        postions = []
        positions.append[self.apogee*np.sin(self.launch_azimuth_angle),
                            self.apogee*np.cos(self.launch_azimuth_angle), self.apogee]
        velocity = []
        velocity.append[self.velocity_off_rail_mag*np.sin(self.launch_zenith_angle)*np.sin(self.launch_azimith_angle),
                        self.velocity_off_rail_mag*np.sin(self.launch_zenith_angle)*np.sin(self.launch_azimith_angle),
                        0]
        #[x,y,z] with t = index*time_step
        
        lv_1 = 1
        time = 0
        
        while positions[lv_1][2] > 0:
            
        
        