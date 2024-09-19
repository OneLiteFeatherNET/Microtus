package net.minestom.server.tag;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class TagWriteBenchmark {
    static final Tag<String> TAG = Tag.String("key");
    static final String VALUE = "value";

    TagHandler tagHandler;
    Tag<String> secondTag;

    Map<String, String> map;
    Map<String, String> concurrentMap;

    @Setup
    public void setup() {
        // Tag benchmark
        this.tagHandler = TagHandler.newHandler();
        tagHandler.setTag(TAG, VALUE);
        secondTag = Tag.String("key");
        // Concurrent map benchmark
        map = new HashMap<>();
        map.put("key", VALUE);
        // Hash map benchmark
        concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("key", VALUE);
    }

    @Benchmark
    public void writeConstantTag() {
        tagHandler.setTag(TAG, VALUE);
    }

    @Benchmark
    public void writeDifferentTag() {
        tagHandler.setTag(secondTag, VALUE);
    }

    @Benchmark
    public void writeNewTag() {
        tagHandler.setTag(Tag.String("key"), VALUE);
    }

    @Benchmark
    public void writeConcurrentMap() {
        concurrentMap.put("key", VALUE);
    }

    @Benchmark
    public void writeMap() {
        map.put("key", VALUE);
    }
}
