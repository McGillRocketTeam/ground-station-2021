class RocketProperties:
    # This class contains everything which should be constant for a given set of simulations
    # All numbers are currently incorrect.
    main_deploy_altitude = 1 # meters
    apogee = 1 # meters
    velocity_off_rail_mag = 1 # m/s

    vertical_drag_coeff_drogue = 1
    vertical_drag_coeff_main = 1
    transverse_drag_coeff_drogue = 1
    transverse_drag_coeff_main = 1

    drogue_deploys_bool = True
    main_deploys_bool = True