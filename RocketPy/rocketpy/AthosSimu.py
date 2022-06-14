from rocketpy import Environment, Rocket, SolidMotor, Flight

Env = Environment(
    railLength=5.2,
    latitude=32.990254,
    longitude=-106.974998,
    elevation=1400,
    date=(2022, 6, 15, 12)  # Tomorrow's date in year, month, day, hour UTC format
)

Env.setAtmosphericModel(type='Forecast', file='GFS')

# help(Environment)

# Env.info()

maelstromV5 = SolidMotor(
    thrustSource="../data/motors/maelstrom/Maelstrom-V5-eng.eng",
    burnOut=3.25,
    grainNumber=1,
    grainSeparation=0,
    grainDensity=788.9,
    grainOuterRadius=0.039624,
    grainInitialInnerRadius=0.0226949,
    grainInitialHeight=0.6096,
    nozzleRadius=381/10000,
    throatRadius=233/10000,
    interpolationMethod='linear'
)

athos = Rocket(
    motor=maelstromV5,
    radius=0.07874,
    mass=47.5,
    inertiaI=57.67515388,
    inertiaZ=0.198348,
    distanceRocketNozzle=-1.872244414,
    distanceRocketPropellant=1.50523109705601,
    powerOffDrag=0.555,
    powerOnDrag=0.555
)

athos.setRailButtons([0.2, -0.5])

NoseCone = athos.addNose(length=0.914, kind="vonKarman", distanceToCM=1.455155586)

FinSet = athos.addFins(3, span=0.127, rootChord=0.4318, tipChord=0.1143, distanceToCM=-1.3617956)


def drogueTrigger(p, y):
    return True if y[5] < 0 else False


def mainTrigger(p, y):
    return True if y[5] < 0 and y[2] < 800 else False


Main = athos.addParachute('Main',
                            CdS=22.48,
                            trigger=mainTrigger,
                            samplingRate=105,
                            lag=1.5,
                            noise=(0, 8.3, 0.5))

Drogue = athos.addParachute('Drogue',
                              CdS=1.75,
                              trigger=drogueTrigger,
                              samplingRate=105,
                              lag=1.5,
                              noise=(0, 8.3, 0.5))

help(SolidMotor)

print(maelstromV5.evaluateTotalImpulse())

# TestFlight = Flight(rocket=athos, environment=Env, inclination=84, heading=6)

# TestFlight.info()

# TestFlight.allInfo()