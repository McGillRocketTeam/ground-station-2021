class RocketProperties:
    # This class contains everything which should be constant for a given set of simulations

    def __init__(self, main_deploy_altitude, apogee, velocity_off_rail_mag, rocket_cx_area,
                 drogue_cx_area, main_cx_area, vertical_drag_coeff_drogue,
                 vertical_drag_coeff_main, drogue_deploy_altitude,
                 transverse_drag_coeff_drogue, transverse_drag_coeff_main,
                 rocket_mass, drogue_deploys_bool=True, main_deploys_bool=True):

        # Numerical Values which shouldn't change between sets of simulations
        self.main_deploy_altitude = main_deploy_altitude # meters
        self.drogue_deploy_altitude = drogue_deploy_altitude
        self.apogee = apogee # meters
        self.velocity_off_rail_mag = velocity_off_rail_mag # m/s

        # Define cross-sectional-areas for different descent types
        self.rocket_cx_area = rocket_cx_area
        self.drogue_cx_area = drogue_cx_area
        self.main_cx_area = main_cx_area

        # Drag coefficients
        self.vertical_drag_coeff_drogue = vertical_drag_coeff_drogue
        self.vertical_drag_coeff_main = vertical_drag_coeff_main
        self.transverse_drag_coeff_drogue = transverse_drag_coeff_drogue
        self.transverse_drag_coeff_main = transverse_drag_coeff_main

        # Situational parameters (i.e. whether or not the parachutes deploy)
        self.drogue_deploys_bool = drogue_deploys_bool
        self.main_deploys_bool = main_deploys_bool

        self.rocket_mass = rocket_mass


