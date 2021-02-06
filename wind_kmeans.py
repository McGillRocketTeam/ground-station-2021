from wind_data_code import Wind
from sklearn.cluster import KMeans
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

class KWind:

    def __init__(self):
        self.wd = Wind()
    
    def get_df(self, index):
        x = self.wd.clean_data(self.wd.get_wind_data_tuple_select_index(index))
        lst = list(zip(x[0][::-1], x[1][::-1], x[2][::-1]))
        df = pd.DataFrame(lst, columns=['altitude', 'direction', 'velocity'])
        return df

    def draw(self, df):
        plt.scatter(df[['altitude']], df[['clusters']])
        plt.xlabel("altitude")
        plt.ylabel("cluster")
        plt.show()

    def run_cluster(self,df,num_clusters):
        X = df[['direction','velocity']].to_numpy()
        kmeans = KMeans(n_clusters=num_clusters).fit(X)
        df['clusters']=kmeans.labels_
        return df


kwind = KWind()
for i in range(1,8):
    kwind.draw(kwind.run_cluster(kwind.get_df(-i),3))