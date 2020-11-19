package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;


/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
class ParSort {

    public static int cutoff = 1000;
    public static ForkJoinPool myThreadPool = new ForkJoinPool(2);

    static {
        System.out.println("Degree of Parallelism: " + myThreadPool.getParallelism());
    }
    public static void sort(int[] array, int from, int to) {
        if (to - from < cutoff) Arrays.sort(array, from, to);
        else {
            CompletableFuture<int[]> parsort1 = parsort(array, from, from + (to - from) / 2);
            CompletableFuture<int[]> parsort2 = parsort(array, from + (to - from) / 2, to);
                CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, (xs1, xs2) -> {
                int[] result = new int[xs1.length + xs2.length];
                    {
                        int j=0,k = 0;
                        for (int i = 0; i < result.length; i++) {
                            if (k >= xs2.length || (j < xs1.length && xs1[j] < xs2[k])) {
                                result[i] = xs1[j];
                                j++;
                            } else {
                                result[i] = xs2[k];
                                k++;
                            }
                        }
                    }
                return result;
            });


            parsort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
          //  System.out.println("# threads: "+ ForkJoinPool.commonPool().getRunningThreadCount());
            parsort.join();
        }
    }

    private static CompletableFuture<int[]> parsort(int[] array, int from, int to) {

        CompletableFuture<int[]> res = CompletableFuture.supplyAsync(
                () -> {
                    //System.out.println("Async here!");
                    int[] result = new int[to - from];
                    System.arraycopy(array, from, result, 0, result.length);
                    sort(result, 0, to - from);
                    return result;
                },myThreadPool);
        //System.out.println("Async thread : "+ Thread.currentThread().getName());
        res.join();

        return res;


    }


   public static void Seq_mergeSort(int array[],int aux[],int from,int to){
        if(to <= from){
            return;
        }
        else if (to - from < cutoff) Arrays.sort(array, from, to);
        else {
        int mid = from + (to - from) / 2;
        Seq_mergeSort(array,aux,from,mid);
        Seq_mergeSort(array,aux,mid+1,to);
        Seq_merge(array,aux,from,mid,to);}
    }

    public static void Seq_mergeSort(int[] array, int from, int to)
    {
        int[] aux = new int[array.length];
        Seq_mergeSort(array, aux, 0, array.length - 1);
    }


    public static void Seq_merge (int array[],int aux[], int from, int mid, int to)
    {
        for (int k = from; k <= to; k++)
            aux[k] = array[k];
        int i = from, j = mid+1;
        for (int k = from; k <= to; k++)
        {
            if (i > mid) array[k] = aux[j++];
            else if (j > to) array[k] = aux[i++];
            else if (aux[j]< aux[i]) array[k] = aux[j++];
            else array[k] = aux[i++];
        }
    }


}