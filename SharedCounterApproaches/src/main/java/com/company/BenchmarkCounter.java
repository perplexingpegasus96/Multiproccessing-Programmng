package com.company;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@State(Scope.Benchmark)
@Fork(value = 1)
public class BenchmarkCounter {

    @State(Scope.Benchmark)
    public static class CounterState {
        final Counter concurentCounter = new ConcurrentCounterImpl();
        final Counter lockCounter = new LockCounterImpl();
        final Counter mutexCounter = new MutexCounterImpl();
        final Counter magicCounter = new MagicCounterImpl();

    }

    @Benchmark
    @Group("ConcurentCounter")
    @GroupThreads(1)
    public void testConcurentCounterIncrement(final CounterState state) {
        state.concurentCounter.increment();
    }

    @Benchmark
    @Group("ConcurentCounter")
    @GroupThreads(1)
    public long testConcurentCounterGetValue(final CounterState state) {
        return state.concurentCounter.getValue();
    }

    @Benchmark
    @Group("LockCounter")
    @GroupThreads(1)
    public void testLockCounterIncrement(final CounterState state) {
        state.lockCounter.increment();
    }

    @Benchmark
    @Group("LockCounter")
    @GroupThreads(1)
    public long testLockCounterGetValue(final CounterState state) {
        return state.lockCounter.getValue();
    }

    @Benchmark
    @Group("MutexCounter")
    @GroupThreads(1)
    public void testMutexCounterIncrement(final CounterState state) {
        state.mutexCounter.increment();
    }

    @Benchmark
    @Group("MutexCounter")
    @GroupThreads(1)
    public long testMutexCounterGetValue(final CounterState state) {
        return state.mutexCounter.getValue();

    }

    @Benchmark
    @Group("MagicCounter")
    @GroupThreads(1)
    public void testMagicCounterIncrement(final CounterState state) {
        state.magicCounter.increment();
    }

    @Benchmark
    @Group("MagicCounter")
    @GroupThreads(1)
    public long testMagicCounterGetValue(final CounterState state) {
        return state.magicCounter.getValue();

    }


    public static void main(String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(BenchmarkCounter.class.getName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}