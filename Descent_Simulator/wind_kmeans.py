from wind_data_code import Wind
from sklearn.cluster import KMeans
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import datetime

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
        df["cos_direction"]=100*np.cos(df["direction"]* np.pi/180)
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
        return res
    
    def get_boundary(self,ls):
        if ls[0]<=0.5:
            for i in range(1,len(ls)):
                if ls[i]>0.5:
                    return i
        else:
            c=False
            for i in range(1,len(ls)):
                if ls[i]<=0.5:
                    c=True
                if c and ls[i]>0.5:
                    return i
        return 0

    def get_average_boundary(self,last):
        total=0
        last=365
        for i in range(1,last+1):
            v=kwind.get_boundary(kwind.get_centroids(kwind.get_df(-i)))
            total=total+v
        return total/last*5000

    def get_avgweek_using_date(self,date):
        initial_date=datetime.datetime.strptime("2019/10/25", '%Y/%m/%d')
        n=(initial_date-datetime.datetime.strptime(date, '%Y/%m/%d')).days +3
        total=0
        for i in range(0,7):
            v=kwind.get_boundary(kwind.get_centroids(kwind.get_df(-n-i)))
            #print(kwind.get_df(-n-i))
            total=total+v
        avg=total/7*5000
        upper=[]
        lower=[]
        for i in range(0,7):
            # Get Dataframe
            df=kwind.get_df(-n-i)
            # Find closest index
            split_index = int(df.iloc[(df['altitude']-avg).abs().argsort()[:1]].index.values[0])
            df_lower,df_upper=df.iloc[:split_index, :], df.iloc[split_index:, :]
            lower.append(df_lower)
            upper.append(df_upper)
        combination=[]
        for i in range(0,7):
            dfu=upper[i]
            for j in range(0,7):
                dfl=lower[j]
                combination.append(pd.concat([dfl,dfu]))
        return combination
                

            




# Tests:
kwind = KWind(num_clusters=2)
#for i in range(1,8):
#    kwind.draw(kwind.run_cluster(kwind.get_df(-i),3))
#kwind.draw(kwind.run_cluster(kwind.get_df(-3)))
#kwind.run_cluster(kwind.get_df(-3))
#ls=kwind.get_centroids(kwind.get_df(-250),draw_scatter=True,draw_cluster=True)
#print(ls)
#print(kwind.get_boundary(ls))

#print(kwind.get_average_boundary(365))
print(kwind.get_avgweek_using_date("2018/12/30"))
