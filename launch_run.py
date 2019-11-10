import numpy as np
import pandas as pd

class Launch():

    def __init__(self, launch_zenith_angle_in, launch_azimith_angle_in, drogue_deploy_altitude_in,
                 wind_dataframe_in, drag_coeff_drogue_in, drag_coeff_main_in, apogee_in):
        self.launch_zenith_angle = launch_zenith_angle_in
        self.launch_azimith_angle = launch_azimith_angle_in
        self.drogue_deploy_altitude = drogue_deploy_altitude_in
        self.wind_dataframe = wind_dataframe_in
        self.drag_coeff_drogue = drag_coeff_drogue_in
        self.drag_coeff_main = drag_coeff_main_in
        self.apogee = apogee_in


    def run_launch(self):
        pass
