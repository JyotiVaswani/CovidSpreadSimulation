package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.union_find.UF_HWQUPC;
import edu.neu.coe.info6205.union_find.UF_path_halving;

import java.util.Random;

public class UF_Benchmark_Client {

    public static void benchmark_performance_two_loops(int n)
    {
        int count_conn=0;   // number of pairs/connections made
        UF_HWQUPC ufhwqupc_client = new UF_HWQUPC(n);
        while (ufhwqupc_client.components()!=1)   // till all sites are connected and are in 1 component
        {
            Random random_conn = new Random();
            {
                int p = random_conn.nextInt(n);
                int q = random_conn.nextInt(n);
                if(ufhwqupc_client.connected(p,q));
                else {
                    ufhwqupc_client.union(p, q);
                    ++count_conn;
                }
            }
        }

		System.out.println("For "+n+ " sites, number of pairs generated: "+ count_conn);
        System.out.println("the average depth of tree: " + ufhwqupc_client.averageDepthAllNodes(n));
        return;
    }



    public static void benchmark_performance_path_halving(int n)
    {
        int count_conn=0;   // number of pairs/connections made
        UF_path_halving ufPathHalving_client = new UF_path_halving(n);
        while (ufPathHalving_client.components()!=1)   // till all sites are connected and are in 1 component
        {
            Random random_conn = new Random();
            {
                int p = random_conn.nextInt(n);
                int q = random_conn.nextInt(n);
                if(ufPathHalving_client.connected(p,q));
                else {
                    ufPathHalving_client.union(p, q);
                    ++count_conn;
                }
            }
        }
        System.out.println("For "+n+ " sites, number of pairs generated: "+ count_conn);
        System.out.println("the average depth of tree: " + ufPathHalving_client.averageDepthAllNodes(n));
        return;
    }

}
