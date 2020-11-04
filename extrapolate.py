import numpy as np
import pandas as pd

class Launch_Extrapolation:
    air_ideal_gas_constant = 287.05 # J/K*kg
    kPa_to_Pa = 1000
    deg_to_rad = np.pi/180
    gravity_constant = 9.807 # m/s2
    time_step = 0.01 # size of timestep s
    max_loops = 1e5 # break iteration if more than this
    
    def __init__(self,position, velocity ,wind_array, rocket_properties):
        self.wind_array = wind_array

        #Position Array
        self.pos_x=position[0]
        self.pos_y=position[1]
        self.pos_z=position[2]

        #Velocity Array
        self.velocity_x=velocity[0]
        self.velocity_y=velocity[1]
        self.velocity_z=velocity[2]

        # Extract properties from encapsulating RocketProperties Class
        self.vertical_drag_coeff_drogue = rocket_properties.vertical_drag_coeff_drogue
        self.vertical_drag_coeff_main = rocket_properties.vertical_drag_coeff_main
        self.transverse_drag_coeff_drogue = rocket_properties.transverse_drag_coeff_drogue
        self.transverse_drag_coeff_main = rocket_properties.transverse_drag_coeff_main

        self.rocket_cx_area = rocket_properties.rocket_cx_area
        self.drogue_cx_area = rocket_properties.drogue_cx_area
        self.main_cx_area = rocket_properties.main_cx_area

        self.drogue_deploys_bool = rocket_properties.drogue_deploys_bool #boolean
        self.main_deploys_bool = rocket_properties.main_deploys_bool #boolean

        self.drogue_deploy_altitude = rocket_properties.drogue_deploy_altitude
        self.main_deploy_altitude = rocket_properties.main_deploy_altitude # metres

        self.velocity_off_rail_mag = rocket_properties.velocity_off_rail_mag # m/s
        self.rocket_mass = rocket_properties.rocket_mass

        self.apogee = rocket_properties.apogee

    def calculute_vertical_drag_coeff(self, altitude):
        if altitude > self.drogue_deploy_altitude:
            return 0
        elif altitude > self.main_deploy_altitude and self.drogue_deploys_bool == True:
            return self.vertical_drag_coeff_drogue
        elif altitude <= self.main_deploy_altitude and self.main_deploys_bool == True:
            return self.vertical_drag_coeff_main
        
    def calculate_transverse_drag_coeff(self, altitude):
        if altitude > self.drogue_deploy_altitude:
            return 0 # assume negligible effect of wind prior to parachute deployment; not great, but not awful
        elif altitude > self.main_deploy_altitude and self.drogue_deploys_bool == True:
            return self.transverse_drag_coeff_drogue
        else:
            return self.transverse_drag_coeff_main
        
    def calculate_temperature(self, altitude): # KELVIN
        if altitude < 11000:
            return 15.04-0.00649*altitude + 273.15
        elif altitude < 25000:
            return -56.46 + 273.15
        else:
            return -131.21 + 0.00299*altitude + 273.15

    def calculate_pressure(self, altitude): # Pa
        if altitude < 11000:
            return self.kPa_to_Pa*101.29*((self.calculate_temperature(altitude) + 273.1)/288.08)**5.256
        elif altitude < 25000:
            return self.kPa_to_Pa*22.65 * np.exp(1.73-0.000157*altitude)
        else:
            return self.kPa_to_Pa*2.488*((self.calculate_temperature(altitude)+273.1)/216.6)**-11.388

    def calculate_density(self, altitude): # kg/m3
        return self.calculate_pressure(altitude)/ (self.air_ideal_gas_constant*self.calculate_temperature(altitude))

    def calculate_descent_rate(self, altitude): # m/s
        Cd = self.calculate_vertical_drag_coeff(altitude)
        if Cd != 0:
            return np.sqrt(2*self.rocket_mass*self.gravity_cosntant/(self.calculate_density(altitude) * Cd))
        else:
            return np.sqrt(2*self.gravity_constant*(self.apogee-altitude))
        
    def calculate_wind_speed(self, altitude):
        # Return a vector in the x-y plane with units of wind speed
        dirn_deg = np.interp(altitude, self.wind_array[0], self.wind_array[1])
        vel = np.interp(altitude, self.wind_array[0], self.wind_array[2])

        dirn_rad = dirn_deg * self.deg_to_rad

        #Assuming x-direction is North and Y is East

        return [vel*np.cos(dirn_rad), vel*np.sin(dirn_rad)]
        
    def calculate_force_xy(self, altitude):
        Cd = self.calculate_transverse_drag_coeff(altitude)
        wind_vel = self.calculate_wind_speed(altitude)
        rho = self.calculate_density(altitude)
        A = self.calculate_transverse_cx_area(altitude)
        return Cd*rho*(wind_vel[0]**2)/2, Cd*rho*(wind_vel[1]**2)/2

    def calculate_transverse_cx_area(self,altitude):
        # Dummy function for now FIX THIS LATER!!!!!!!!!!!!!
        return 1

    def calculate_force_z(self, altitude, vel):
        Cd = self.calculute_vertical_drag_coeff(altitude)
        rho = self.calculate_density(altitude)
        m = self.rocket_mass
        g = self.gravity_constant
        # need to incorporate cross-sectional areas
        net_force = Cd*rho*(abs(vel)**2) * self.get_cx_area(altitude)/2 - m*g
        #if net_force > 0:
        #   print("Ahhhh")
        return net_force

    def get_cx_area(self, altitude):
        if altitude < self.main_deploy_altitude & self.main_deploys_bool:
            return self.main_cx_area
        elif altitude < self.drogue_deploy_altitude & self.drogue_deploys_bool:
            return self.drogue_cx_area
        else:
            return self.drogue_cx_area



    def run_extrapolation(self):
        # Initialise positions and velocities
        positions = []
        x, y, z = self.pos_x, self.pos_y, self.pos_z
        velocities = []
        v_x, v_y, v_z = self.velocity_x, self.velocity_y, self.velocity_z

        # Now set up the main iteration loop
        loops = 0
        max_loops = int(1e6)
        t = 0
        while z > 0 and loops < max_loops:
            loops = loops + 1
            dt = self.time_step
            t = t + dt

            fx, fy = self.calculate_force_xy(z)
            fz = self.calculate_force_z(z, v_z)

            delta_v_x = fx * dt/self.rocket_mass
            delta_v_y = fy * dt/self.rocket_mass
            delta_v_z = fz * dt/self.rocket_mass

            v_x += delta_v_x
            v_y += delta_v_y
            v_z += delta_v_z

            delta_x = v_x * dt
            delta_y = v_y * dt
            delta_z = v_z * dt

            x += delta_x
            y += delta_y
            z += delta_z

            positions.append((x, y, z, t))
            velocities.append((v_x, v_y, v_z, t))

        print("x = ", x)
        print("y = ", y)
        print("z = ", z)
        print("t = ", t)