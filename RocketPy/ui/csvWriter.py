import csv

profileDict = {"railLength": 0,
               "latitude": 0,
               "longitude": 0,
               "elevation": 0,
               "dateTime": 0,
               "atmosphericModel": 0,
               "thrustSource": 0,
               "burnOutTime": 0,
               "grainNumber": 0,
               "grainSeparation": 0,
               "grainDensity": 0,
               "grainOuterRadius": 0,
               "grainInnerRadius": 0,
               "grainInitialHeight": 0,
               "nozzleRadius": 0,
               "throatRadius": 0,
               "interpolationMethod": 0,
               "radius": 0,
               "mass": 0,
               "inertiai": 0,
               "inertiaz": 0,
               "distanceRocketNozzle": 0,
               "distanceRocketPropellant": 0,
               "ONDrag": 0,
               "OFFDrag": 0,
               "inclinationAngle": 0,
               "headingAngle": 69,
               "NCLength": 0,
               "NCKind": 0,
               "NCDistanceCM": 0,
               "finsNumber": 0,
               "finSpan": 0,
               "rootChord": 0,
               "tipChord": 0,
               "finsDistanceCM": 0,
               "mainCdS": 0,
               "mainCd": 0,
               "mainReferenceArea": "hellohttps://youtu.be/Yvrg378euus",
               "drogueCdS": 0,
               "drogueCd": 0,
               "drogueReferenceArea": 0
               }

print(profileDict["railLength"])

fileName = ".././profiles/" + "something" + ".csv"

with open(fileName, 'w', newline='') as csvfile:
    fieldnames = ["railLength","latitude","longitude","elevation","dateTime","atmosphericModel","thrustSource","burnOutTime","grainNumber","grainSeparation","grainDensity","grainOuterRadius","grainInnerRadius","grainInitialHeight","nozzleRadius","throatRadius","interpolationMethod","radius","mass","inertiai","inertiaz","distanceRocketNozzle","distanceRocketPropellant","ONDrag","OFFDrag","inclinationAngle","headingAngle","NCLength","NCKind","NCDistanceCM","finsNumber","finSpan","rootChord","tipChord","finsDistanceCM","mainCdS","mainCd","mainReferenceArea","drogueCdS","drogueCd","drogueReferenceArea"]
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

    writer.writeheader()
    writer.writerow(profileDict)