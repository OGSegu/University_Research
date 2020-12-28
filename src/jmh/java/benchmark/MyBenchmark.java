package benchmark;

import hashtable.HashMapNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class MyBenchmark {
    @State(Scope.Benchmark)
    @SuppressWarnings("unused")
    public static class putBenchmark {
        private Map<String, String> defaultMap;
        private HashMapNode<String, String> customMap;

        @SuppressWarnings("unused")
        @Setup(Level.Trial)
        public void init() {
            defaultMap = new HashMap<>();
            customMap = new HashMapNode<>();
        }

        @SuppressWarnings("unused")
        @Fork(value = 1, warmups = 1)
        @Warmup(iterations = 1)
        @Benchmark
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(value = MILLISECONDS)
        @Measurement(iterations = 3)
        public void addDefault() {
            for (int i = 0; i < 50000; i++) {
                String key = RandomStringUtils.random(5);
                String value = RandomStringUtils.random(100);
                defaultMap.put(key, value);
            }
        }

        @SuppressWarnings("unused")
        @Fork(value = 1, warmups = 1)
        @Warmup(iterations = 1)
        @Benchmark
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(value = MILLISECONDS)
        @Measurement(iterations = 3)
        public void addCustom() {
            for (int i = 0; i < 50000; i++) {
                String key = RandomStringUtils.random(5);
                String value = RandomStringUtils.random(100);
                customMap.put(key, value);
            }
        }
    }
    @State(Scope.Benchmark)
    @SuppressWarnings("unused")
    public static class getBenchmark {
        private Map<String, String> defaultMap;
        private HashMapNode<String, String> customMap;
        private String[] array = new String[50000];

        @SuppressWarnings("unused")
        @Setup
        public void init() {
            defaultMap = new HashMap<>();
            customMap = new HashMapNode<>();
            for (int i = 0; i < 50000; i++) {
                String key = RandomStringUtils.random(5);
                String value = RandomStringUtils.random(100);
                array[i] = key;
                defaultMap.put(key, value);
                customMap.put(key, value);
            }
        }

        @SuppressWarnings("unused")
        @Fork(value = 1, warmups = 1)
        @Warmup(iterations = 1)
        @Benchmark
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(value = MILLISECONDS)
        @Measurement(iterations = 3)
        public void getDefault(Blackhole blackhole) {
            for (int i = 0; i < 50000; i++) {
                blackhole.consume(defaultMap.get(array[i]));
            }
        }

        @SuppressWarnings("unused")
        @Fork(value = 1, warmups = 1)
        @Warmup(iterations = 1)
        @Benchmark
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(value = MILLISECONDS)
        @Measurement(iterations = 3)
        public void getCustom(Blackhole blackhole) {
            for (int i = 0; i < 50000; i++) {
                blackhole.consume(customMap.get(array[i]));
            }
        }
    }
}
