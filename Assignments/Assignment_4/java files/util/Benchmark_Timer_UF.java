/*
 * Copyright (c) 2018. Phasmid Software
 */

package edu.neu.coe.info6205.util;





import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer_UF<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */

    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
       // logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");

        //System.out.println("warmup rounds: ");
        //warmup phase
        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        //new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);
        //  System.out.println("Actual benchmarking rounds: ");
        // Timed phase
        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer_UF with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer_UF(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;

    }

    /**
     * Constructor for a Benchmark_Timer_UF with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer_UF(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer_UF with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer_UF(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer_UF where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer_UF(String description, Consumer<T> f) {
        this(description, null, f, null);
    }

    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;


    final static LazyLogger logger = new LazyLogger(Benchmark_Timer_UF.class);


    public static void main (String args[]) throws IOException {
     /**
       *  This loop will generate doubling values for n that will be used for bench_marking
       */

		Random rand = new Random();
        for(int i=1000; i<10000000;) {
            int n = i;
            i=i*2;
			Benchmark_Timer_UF.performance(n);
            System.out.println();
            System.out.println();

            /**
             * below calls are used to find the average depth of tree created
             */



        }
    }


  public static void performance(int n) {
      /**
       * this method is for invoking the benchmark_timer used over the find and union methods
       * of path-halving and two-loop algorithms
       */
      UF_Benchmark_Client uf_benchmark_client = new UF_Benchmark_Client();
     // Consumer<Integer> fPost_path_halving = nodes -> uf_benchmark_client.average_depth_path_halving(nodes);
      Consumer<Integer> fRun_path_halving = nodes -> uf_benchmark_client.benchmark_performance_path_halving(nodes);
      Benchmark_Timer_UF<Integer> bmt_path_halving = new Benchmark_Timer_UF<Integer>("UFWQUPC path_halving: ", null, fRun_path_halving, null);
      System.out.println("Output of path-halving: ");
      bmt_path_halving.run(n, 1);




     // Consumer<Integer> fPost_two_loops = nodes -> uf_benchmark_client.average_depth_two_loops(nodes);
      Consumer<Integer> fRun_two_loops = nodes -> uf_benchmark_client.benchmark_performance_two_loops(nodes);
      Benchmark_Timer_UF<Integer> bmt_two_loops = new Benchmark_Timer_UF<Integer>("UFWQUPC two loops: ", null, fRun_two_loops, null);
      System.out.println("Output of two loops: ");
      bmt_two_loops.run(n, 1);

  }

}
