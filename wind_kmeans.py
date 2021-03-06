from wind_data_code import Wind
from sklearn.cluster import KMeans
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

class KWind:

    def __init__(self,num_clusters):
        self.wd = Wind()
        self.num_clusters=num_clusters
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

    def run_cluster(self,df):
        X = df[['direction','velocity']].to_numpy()
        kmeans = KMeans(n_clusters=self.num_clusters).fit(X)
        df['clusters']=kmeans.labels_
        return df

    # Only works for num_clusters=2
    def get_centroids(self,df,draw_scatter=False,draw_cluster=False):
        # Transform direction using cosine function
        df["cos_direction"]=np.cos(df["direction"]* np.pi/180)
        # Run Kmeans
        X = df[['cos_direction','velocity']].to_numpy()
        kmeans = KMeans(n_clusters=self.num_clusters).fit(X)
        df['clusters']=kmeans.labels_
        # Plot results (Optional)
        if draw_scatter:
            plt.scatter(X[:,0],X[:,1], c=kmeans.labels_, cmap='rainbow')
            plt.show()
        if draw_cluster:
            self.draw(df)
        # Determine which cluster is lower
        avg=df.groupby(by=["clusters"]).mean().reset_index().sort_values(by=['clusters'],ascending=True)
        if avg.iloc[0].altitude > avg.iloc[1].altitude:
            df['clusters']=df['clusters'].map({0: 1, 1: 0})
        
        segment=5000
        max_alt=df["altitude"].max()
        max_alt+=(segment-max_alt%segment)
        res=[]
        for i in range(segment,max_alt+1,segment):
            res.append(df[(df.altitude>=(i-segment))  & (df.altitude<i)].clusters.mean())
        
            

# Tests:
kwind = KWind(num_clusters=2)
#for i in range(1,8):
#    kwind.draw(kwind.run_cluster(kwind.get_df(-i),3))
#kwind.draw(kwind.run_cluster(kwind.get_df(-3)))
#kwind.run_cluster(kwind.get_df(-3))
kwind.get_centroids(kwind.get_df(-250),draw_cluster=True)
