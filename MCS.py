# -*- coding: utf-8 -*-
"""
Created on Fri Oct 25 21:12:06 2019

@author: sunge
"""
import numpy as np
import pandas as pd
#--------------------------------Part 1: Wind generator------------------------
# Altitude should be provided in a sorted ***np array***
#inputw[0]: wind dirction average & inputw[1]: wind dirction stdv
#inputw[2]: wind velocity average & inputw[3]: wind velocity stdv
'''
@input:
    wNormal: 
                generates normal distributed data when true
                otherwise chooses data according to probability
    Inputw:     
        When wNormal is TRUE:
                inputw[0]: wind dirction average & inputw[1]: wind dirction stdv
                inputw[2]: wind velocity average & inputw[3]: wind velocity stdv
        Otherwise: 2D np array
                inputw[0]: a np array of possibale data points
                inputw[1]: a np array of probabilities of the above data points
                inputw[0]&[1] mush have the same length
    wind_altitude:
                
'''
class MCGenerator:
    
    def __init__(self):
        self = self
        
        
    def wind_data_generator(self,wind_altitude,inputw,wNormal = True):
        wind_direction = np.zeros(wind_altitude.shape[0])
        wind_velocity = np.zeros(wind_altitude.shape[0])
        if wNormal:     
            wind_direction = np.random.normal(inputw[0],inputw[1],wind_altitude.reshape(1,5).shape[1] ).reshape(1,5)#.round(2)
            wind_velocity = np.random.normal(inputw[2],inputw[3],wind_altitude.reshape(1,5).shape[1]).reshape(1,5)#.round(2)
        else:
            wind_direction = np.random.choice(inputw[0],wind_altitude.shape[0] ,p=inputw[1]).reshape(1,5)
            wind_velocity = np.random.choice(inputw[2],wind_altitude.shape[0] ,p=inputw[3]).reshape(1,5)
    
        wind_data = np.concatenate((wind_altitude.reshape(1,5),wind_direction,wind_velocity), axis = 0)
        wind_df = pd.DataFrame(wind_data.T,
                               columns =['altitude','direction','velocity'])
    
        return wind_df
    #-------------------------------Part 2: Monte Carlo----------------------------
    '''
    aNormal:
        same as wNormal explained
    num_cycles: 
        number of cycles of running the simulator
    inputa: 
        same as inputw
    '''
    def MCS(self,inputa,wind_altitude,inputw,aNormal = True ,wNormal= True,num_cycles = 3 ):
        angles = []       
        wind = pd.DataFrame(columns =['altitude','direction','velocity'])
        if aNormal:
            angles = np.random.normal(inputa[0],inputa[1],num_cycles)
        else:
            angles = np.random.choice(inputa[0],num_cycles, p=inputa[1])
        
        for angle in angles:
              mock_wind = self.wind_data_generator(wind_altitude,inputw,wNormal)
#              print(angle,"\n")
#              print(mock_wind)
              wind = wind.append(mock_wind,ignore_index = True)
              
        return wind
    
    
    #Fake
    def simulator(a,b):
        a=a+b


#----------------------------Part 3: Sample Code-------------------------------
'''
VERY IMPORTANT: Notice the dimension!
'''
w_al = np.array([80,60,40,20,0])
inputw_c = np.array([[12,7,8],
                     [0.2,0.3,0.5],
                     [122,72,82],
                     [0.1,0.2,0.7]])
inputa_c = np.array([[90,89,91],[0.2,0.3,0.5]])  
inputw_n = np.array([4,3,7,10])
inputa_n = np.array([1,3])
mcs = MCGenerator()
# T&T: both normal
wind = mcs.MCS(inputa_n,w_al,inputw_n)

## T&F: normal angle
#mcs.MCS(inputa_n,w_al,inputw_c,wNormal = False)
### F&T: normal wind
#mcs.MCS(inputa_c,w_al,inputw_n,aNormal = False)
### F&F: both false
#mcs.MCS(inputa_c,w_al,inputw_c,aNormal = False,wNormal = False)
#
#
#
#
#
#
##inner logic--------------------------------don't care----------debugging
#d = np.array([[1,2,3,4,5],
#             [5,4,3,2,1]])
#wind_data = np.concatenate((w_al.reshape(1,5),d),axis = 0)
#wind_df = pd.DataFrame(wind_data.T,
#                           columns =['altidude','dirction','velocity'])
#angles = np.random.normal(inputw_n[0],inputw_n[1],5).reshape(1,5)
#a = inputw_c[0]
#b = inputw_c[1]
#x = np.random.choice(a,5,p=b)







        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        