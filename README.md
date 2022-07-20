# ground-station-2021
Ground station repo for SAC 2021

Welcome to the Ground Station Descent Simulator Project 🤩 Our subteam runs rocket simulations using the [RocketPy Engine 🚀](https://github.com/Projeto-Jupiter/RocketPy).
The engine allows us to configure simulations based on specific rocket parameters such as the rocket's mass and the rocket's motors. At the end of each simulations, many 
plots and graphs are displayed for us to view and extract information, but most importantly, it shows this.

![MonteCarlo](https://github.com/McGillRocketTeam/ground-station-2021/blob/dev-simulator/Athos_1kSim_Results/Apogee_Landing_MAP.png)

# Our Goal
Our goal is to predict where our rockets will land during competition. This allows our team on-site during competition to increase their chances in recovering the rocket.
Rocket recovery is extremely important as not only do we gain points, but we can salvage/reuse components, analyse what went wrong and what went right and learn from 
those experiences. We also keep our baby. The image above is the Monte Carlo Analysis plot of where our rocket Athos will land during the Spaceport America Cup 2021-2022.

# Team Members
### Dan Hosi (Subteam Lead)
### Adam Corbier (Member)

# What are we currently working on?
### Graphical User Interface (GUI)
The RocketPy Engine contains code that we may not want to expose as it is complex. Thus, we want users to iteract with the Engine through a graphical user interface
which is easy to use and to learn, intuitive and unique to the McGill Rocket Team. The GUI allows users to configure directly the simulations by entering parameters. 
Here is our current progress.

![image](https://user-images.githubusercontent.com/63082166/179869871-72b2f5e9-0d2d-4658-a7e6-99add76ff832.png)

### Flight Computer Tester (Proof of Concept)
The Flight Computer Tester has yet to implemented. As an early proof of concept, this requirement stems from the fact that it is to costly to create a rocket each time
the Flight Computer Subteam needs to test the Flight Computer. Therefore, by using the RocketPy Engine, we simulate a flight environment to test the Flight Computer.
We shall do this by feeding rocket data such as position, time, velocity in different axis, pressure, temperature, apogee, etc.

# Repo Structure
### `/Descent_Simulator`
Contains the old simulator that we no longer use.

### `/RocketPy`
Contains the new simulator from RocketPy.

### `/RocketPy/data`
Contains data on rocket motors to load during simulations.

### `/RocketPy/rocketpy`
Contains the python classes to intialise a simulation. Also contains configured simulations.

# How to contribute?
See this [README.MD](https://github.com/McGillRocketTeam/ground-station-2021/blob/dev-station/README.md)

# Acknowledgements
We would like to thank the people working on the [RocketPy Project 🚀](https://github.com/Projeto-Jupiter/RocketPy) as it provides fantastic features such as the plots and Monte Carlo Analysis.

