/*
 * ATN PROJECT 1
 * Title - Network Design Using Shortest Path Based Solution
 * Author - Pulkit Khemka
 * */

import java.util.ArrayList;
import java.util.Random;


public class NetwTop {

	 static int N;  //Size of Network
	 int[][] a;     //Edge Weights
	 int[][] b;     //Demand
	
	NetwTop(int N)
	{
		a = new int[N][N]; 
		b = new int[N][N];
		this.N =N;
	}
	
	
	// This Function generates the edge weight matrix
	// Recieves K as input 
	void generateAij(int k)
	{
		Random rn = new Random();
		int m,j,i;
		
		for(i=0;i<N;i++)
		{
			int q = k;
			for(j=0;j<N;j++)
			{
				a[i][j] = 300;
			}
			for(int l=0;l<N;l++)
			{
				a[l][l]= 0;
			}
			while(q>0)
			{
				m =  rn.nextInt(N) + 0;
				if((a[i][m]!=0)&&(a[i][m]!=1))
				{
					a[i][m] = 1;
					q--;
				}
			}
		}		
	}
	
	// This Function generates the demand matrix 
	void generateBij()
	{
		Random rn = new Random();
		
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				b[i][j] = rn.nextInt(5) + 0;
			}
		}
	}
	
	//This function Implements the Shortest Path Using Dijkstras Algorithm
	ArrayList<Integer> Dijkstra(int START, int END)
	{
		int[] distance = new int[N];
		int[] visited  = new int[N]; 
		int[] preD  = new int[N];
		int[][] matrix = new int[N][N];
		int min;
		int p = START;
		int inf = 99999;
		int nextNode=p;
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				matrix[i][j] = a[j][i];
			}			
		}
		for(int i=0;i<N;i++)
		{
			visited[i]=0;
			preD[i]=p;
		}
		
		distance = matrix[p];
		visited[p]=1;
		for(int i=0;i<N;i++)
		{
			min = inf;
		    for(int j=0; j<N;j++)
		    {
					if(min>distance[j] && visited[j]!=1)
					{
						min = distance[j];
						nextNode = j;
					}
			}
				
			visited[nextNode] = 1;
			
			for(int k=0;k<N;k++)
			{
				if(visited[k]!=1)
				{
					if(min + matrix[nextNode][k]<distance[k] )
					{
						distance[k] = min + matrix[nextNode][k];
						preD[k]= nextNode;
					}
				}
			}
		}
		ArrayList<Integer> Path = new ArrayList<Integer>();
		Path.add(END);
		int j = END;
		while(j!=p||j<0)
		{
			j =preD[j];
			Path.add(j);
		}
		return Path;

	}
	
	
	//Main Class
	public static void main(String[] args) 
	{
		for(int k=5;k<16;k++)
		{
			N = 30; // Size of Network Topology is Set as 30.. This can also be received as Input.
			NetwTop nw = new NetwTop(N); 
			
			//Functions for generating aij and bij are called
			nw.generateAij(k); 
			nw.generateBij();
			int [][]c = new int[N][N];
			
			for(int i=0;i<nw.N;i++){
				for(int j=0;j<nw.N;j++){
					c[i][j]=0;
				}
			}
			
			//Shortest path is calculated and capacity matrix is generated
			ArrayList<Integer> Path = new ArrayList<Integer>();
		
			for(int i=0;i<nw.N;i++)
			{
				for(int j=0;j<nw.N;j++)
				{
				    Path = nw.Dijkstra(i,j);
				    int l =0;
				    while (Path.size() > l+1) 
				    {
						int m = Path.get(l);
						int o = Path.get(l+1);
						c[o][m] = c[o][m] + nw.b[i][j];
						l++;
					}
				}
			}
			
			//Capacity Matrix can be printed using block below
			/*System.out.println();
			for(int i=0;i<nw.N;i++){
				for(int j=0;j<nw.N;j++){
				System.out.print(c[i][j] +", ");
				}	
				System.out.println();
			}*/
			
			//Total cost / Optimal cost is calculated
			int TotalCost =0;
			for(int i=0;i<nw.N;i++){
				for(int j=0;j<nw.N;j++){
					TotalCost = TotalCost + c[i][j]*nw.a[i][j];
				}	 
			}
			
			//Density is calculated
			float Density = 0;
			float Count = 0;
			for(int i=0;i<nw.N;i++){
				for(int j=0;j<nw.N;j++){
					if(c[i][j]!= 0)
					{Count++;
					}
				}	 
			}
			Density = Count/(N*(N-1));
			
			System.out.println("K = "+k+" Total Cost = "+TotalCost+" Density = "+ Density);
		}
	}

}
