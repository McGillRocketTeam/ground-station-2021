import numpy as np
import pandas as pd
import math
import matplotlib.pyplot as plt

class Launch:
    air_ideal_gas_constant = 287.05  # J/K*kg
    kPa_to_Pa = 1000
    deg_to_rad = np.pi / 180
    gravity_constant = 9.807  # m/s2
    earth_radius = 6373231.0  # AS  https://rechneronline.de/earth-radius/
    time_step = 0.01  # size of timestep s
    max_loops = 1e5  # break iteration if more than this
    launch_latitude = 32.9925986  # AS
    launch_longitude = -106.9744309  # AS

    def __init__(self, launch_zenith_angle, launch_azimuth_angle, wind_array, rocket_properties):

        # NB, unsure about the names 'zenith' and 'azimuth'; if inconsistent, azimuth == phi in the physics convention
        # for spherical coordinates and zenith is == theta. See wikipedia.

        self.launch_zenith_angle = launch_zenith_angle
        self.launch_azimuth_angle = launch_azimuth_angle    # Measured CCW from NORTH
        self.wind_array = wind_array

        # Extract properties from encapsulating RocketProperties Class
        self.vertical_drag_coeff_drogue = rocket_properties.vertical_drag_coeff_drogue
        self.vertical_drag_coeff_main = rocket_properties.vertical_drag_coeff_main
        self.transverse_drag_coeff_drogue = rocket_properties.transverse_drag_coeff_drogue
        self.transverse_drag_coeff_main = rocket_properties.transverse_drag_coeff_main

        self.rocket_cx_area = rocket_properties.rocket_cx_area
        self.drogue_cx_area = rocket_properties.drogue_cx_area
        self.main_cx_area = rocket_properties.main_cx_area

        self.drogue_deploys_bool = rocket_properties.drogue_deploys_bool  # boolean
        self.main_deploys_bool = rocket_properties.main_deploys_bool  # boolean

        self.drogue_deploy_altitude = rocket_properties.drogue_deploy_altitude
        self.main_deploy_altitude = rocket_properties.main_deploy_altitude  # metres

        self.velocity_off_rail_mag = rocket_properties.velocity_off_rail_mag  # m/s
        self.rocket_mass = rocket_properties.rocket_mass

        self.apogee = rocket_properties.apogee

    # For the 3D Section + spherical coordinates reference system (physics)
    #     # + XY plane with z pointing out for wind data reference system
    def new_conversion_coordinates(self, x, y, z):
        radius = math.sqrt(x**2 + y**2 + z**2)
        new_latitude = math.acos(x/radius) + self.launch_latitude
        new_longitude = math.atan2(y, z) + self.launch_longitude
        return new_latitude, new_longitude

    # For the Geographic Coordinates Section + spherical coordinates reference system (physics)
    # + XY plane with z pointing out for wind data reference system
    def new_conversion_coordinates2(self, x, y, z):
        radius = math.sqrt(x**2 + y**2 + z**2)
        new_latitude = math.asin(x/radius) + self.launch_latitude
        new_longitude = math.atan2(y, z) + self.launch_longitude
        return new_latitude, new_longitude

    # Hand made conversion coordinates + spherical coordinates reference system (physics)
    #     # + XY plane with z pointing out for wind data reference system
    def conversion_coordinates(self, x, y):
        new_latitude = self.launch_latitude + (y / (2.0 * math.pi * self.earth_radius)) * 360.0
        new_longitude = self.launch_longitude + (
                    x / (2.0 * math.pi * self.earth_radius * math.cos(math.radians(self.launch_latitude)))) * 360.0
        return new_latitude, new_longitude

    def calculate_vertical_drag_coeff(self, altitude):
        if altitude > self.drogue_deploy_altitude:
            return 0
        elif altitude > self.main_deploy_altitude and self.drogue_deploys_bool == True:
            return self.vertical_drag_coeff_drogue
        elif altitude <= self.main_deploy_altitude and self.main_deploys_bool == True:
            return self.vertical_drag_coeff_main

    def calculate_transverse_drag_coeff(self, altitude):
        if altitude > self.drogue_deploy_altitude:
            return 0  # assume negligible effect of wind prior to parachute deployment; not great, but not awful
        elif altitude > self.main_deploy_altitude and self.drogue_deploys_bool == True:
            return self.transverse_drag_coeff_drogue
        else:
            return self.transverse_drag_coeff_main

    def calculate_temperature(self, altitude):  # KELVIN
        if altitude < 11000:
            return 15.04 - 0.00649 * altitude + 273.15
        elif altitude < 25000:
            return -56.46 + 273.15
        else:
            return -131.21 + 0.00299 * altitude + 273.15

    def calculate_pressure(self, altitude):  # Pa
        if altitude < 11000:
            return self.kPa_to_Pa * 101.29 * (self.calculate_temperature(altitude) / 288.08) ** 5.256   # Temperature was already in Kelvin
            # return self.kPa_to_Pa * 101.29 * ((self.calculate_temperature(altitude) +  273.15)/ 288.08) ** 5.256

        elif altitude < 25000:
            return self.kPa_to_Pa * 22.65 * np.exp(1.73 - 0.000157 * altitude)
        else:
            return self.kPa_to_Pa * 2.488 * (self.calculate_temperature(altitude) / 216.6) ** -11.388   # Temperature was already in Kelvin
            # return self.kPa_to_Pa * 2.488 * ((self.calculate_temperature(altitude) + 273.15) / 216.6) ** -11.388


    def calculate_density(self, altitude):  # kg/m3
        return self.calculate_pressure(altitude) / (self.air_ideal_gas_constant * self.calculate_temperature(altitude))

    def calculate_descent_rate(self, altitude):  # m/s
        Cd = self.calculate_vertical_drag_coeff(altitude)
        if Cd != 0:
            return np.sqrt(2 * self.rocket_mass * self.gravity_constant / (self.calculate_density(altitude) * Cd))
        else:
            return np.sqrt(2 * self.gravity_constant * (self.apogee - altitude))

    def calculate_wind_speed(self, altitude):
        # Return a vector in the x-y plane with units of wind speed
        dirn_deg = np.interp(altitude, self.wind_array[0], self.wind_array[1])
        vel = np.interp(altitude, self.wind_array[0], self.wind_array[2])

        dirn_rad = dirn_deg * self.deg_to_rad

        # Assuming x-direction is North and Y is East
        return [vel * np.sin(dirn_rad), vel * np.cos(dirn_rad)]

    def calculate_force_xy(self, altitude):
        Cd = self.calculate_transverse_drag_coeff(altitude)
        wind_vel = self.calculate_wind_speed(altitude)
        rho = self.calculate_density(altitude)
        A = self.calculate_transverse_cx_area()
        return A * Cd * rho * (wind_vel[0] ** 2) / 2, A * Cd * rho * (wind_vel[1] ** 2) / 2

    def calculate_transverse_cx_area(self):
        return 0.27043539461  # was return 1 before
        # return 1

    def calculate_force_z(self, altitude, vel):
        Cd = self.calculate_vertical_drag_coeff(altitude)
        rho = self.calculate_density(altitude)
        m = self.rocket_mass
        g = self.gravity_constant
        # need to incorporate cross-sectional areas
        net_force = Cd * rho * (abs(vel) ** 2) * self.get_cx_area(altitude) / 2 - m * g
        # if net_force > 0:
        #   print("Ahhhh")
        return net_force

    def get_cx_area(self, altitude):
        if altitude < self.main_deploy_altitude and self.main_deploys_bool:         # was & self.main_deploys_bool
            return self.main_cx_area
        elif altitude < self.drogue_deploy_altitude and self.drogue_deploys_bool:   # was & self.drogue_deploys_bool
            return self.drogue_cx_area
        else:
            return self.rocket_cx_area # Was drogue_cx_area before
            # return self.drogue_cx_area # Was drogue_cx_area before

    def run_launch(self):
        # Initialise positions and velocities
        converted_positions = []
        positions = []
        velocities = []

        # This was here before
        # x, y, z = self.apogee * np.tan(self.launch_zenith_angle) * np.sin(self.launch_azimuth_angle), \
        #                  self.apogee * np.cos(self.launch_azimuth_angle), self.apogee

        # Following the website 3D section: https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
        # x, y, z = self.apogee * np.tan((self.launch_zenith_angle)) * np.cos(self.launch_azimuth_angle), \
        #                  self.apogee * np.tan((self.launch_zenith_angle)) * np.sin(self.launch_azimuth_angle), \
        #                  self.apogee

        # Following the website Geographic Coordinates section: https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
        x, y, z = self.apogee * np.arctan((self.launch_zenith_angle - 90)) * np.cos(self.launch_azimuth_angle), \
                  self.apogee * np.arctan((self.launch_zenith_angle - 90)) * np.sin(self.launch_azimuth_angle), \
                  self.apogee

        # Swapping Z and X such that the simulation system follows the wind data system.
        # x, y, z = self.apogee * np.tan(self.launch_zenith_angle) * np.sin(self.launch_azimuth_angle), \
        #                       self.apogee * np.cos(self.launch_azimuth_angle), self.apogee

        v_x, v_y, v_z = self.velocity_off_rail_mag * np.sin(self.launch_zenith_angle) * np.sin(self.launch_azimuth_angle), \
                        self.velocity_off_rail_mag * np.sin(self.launch_zenith_angle) * np.sin(
                        self.launch_azimuth_angle), \
                        0

        # Now set up the main iteration loop
        loops = 0
        max_loops = int(1e6)
        t = 0
        dt = 0
        while z > 0 and loops < max_loops:
            fx, fy = self.calculate_force_xy(z)
            fz = self.calculate_force_z(z, v_z)

            delta_v_x = fx * dt / self.rocket_mass
            delta_v_y = fy * dt / self.rocket_mass
            delta_v_z = fz * dt / self.rocket_mass

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

            dt = self.time_step
            t = t + dt
            loops = loops + 1

            # Record the positions in lat, long coordinates to compare with Blanche data in a txt file
            # converted_position_lat, converted_position_lon = self.conversion_coordinates(x,y)
            # converted_positions.append([converted_position_lat, converted_position_lon, z, t])
            # print("Converted positions: ", converted_positions)
            # print(converted_position_lat, converted_position_lon, t)

            # converted_position_lat, converted_position_lon = self.new_conversion_coordinates2(x, y, z)
            # converted_positions.append([converted_position_lat, converted_position_lon, z, t])

            converted_position_lat, converted_position_lon = self.conversion_coordinates(x, y)
            converted_positions.append([converted_position_lat, converted_position_lon, z, t])

            dt = self.time_step
            t = t + dt
            loops = loops + 1

        new_latitude, new_longitude = self.conversion_coordinates(x, y)
        # new_latitude, new_longitude = self.new_conversion_coordinates2(x, y, z)

        # print("lat = ", new_latitude)
        # print("long = ", new_longitude)
        return new_latitude, new_longitude, converted_positions



