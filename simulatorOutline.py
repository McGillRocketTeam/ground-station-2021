# -*- coding: utf-8 -*-
"""
Created on Sun Nov 10 10:58:59 2019

@author: Charles RW
"""
import numpy as np

def getWindData(height, windArr):
    heightArr = windArr[0]
    windDir = windArr[1]
    windSpd = windArr[2]
    direcRadians = np.interp(height,heightArr,windDir)*(np.pi()/180) #in radians
    speed = np.interp(height,heightArr,windSpd) #m/s
    
    return [speed*np.sin(direcRadians), speed*np.cos(direcRadians)]


def getLaunchZenithAngleRadians():
    return launchZenithAngleDegrees*deg2Rad #Launch angle to z axis

def getLaunchAzimuthAngleRadians():
    return launchAzimuthAngleDegrees*deg2Rad #Angle from North

def getApogee():
    return 30000*foot2Meter

def getDescentRate():
    return 2



def perturbDescentRate(descentRate, sigmaDescentRate):
    return descentRate + np.random.normal(0,sigmaDescentRate)

def perturbWind(windArrXY, sigmaWindSpd):
    return windArrXY + np.random.normal(0,sigmaWindSpd,2)
    
def perturbLaunchZenithAngle(launchZenithAngle, sigmaLaunchZenithAngle):
    return launchZenithAngle + np.random.normal(0,epsilonAngle)

def perturbLaunchAzimuthAngle(launchAzimuthAngle, sigmaLaunchAzimuthAngle):
    return launchAzimuthAngle + np.random.normal(0,launchAzimuthAngle)

def perturbApogee(apogee, sigmaApogee):
    return apogee + np.random.normal(sigmaApogee)


