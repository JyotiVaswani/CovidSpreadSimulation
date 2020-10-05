package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_Client {


    public static void main(String[] args) {
        Random rand = new Random();
        for(int i=0; i<11;i++) {
            int n = rand.nextInt(100000);

            System.out.println("For " + n + " sites: " + count(n) + " pairs were made!");
        }
    }

    public static int count(int n)
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
        return count_conn;
    }

}
