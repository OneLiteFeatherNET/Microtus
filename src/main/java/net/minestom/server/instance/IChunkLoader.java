package net.minestom.server.instance;

import net.minestom.server.ServerFlag;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.utils.async.NamedThreadFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Interface implemented to change the way chunks are loaded/saved.
 * <p>
 * See {@link AnvilLoader} for the default implementation used in {@link InstanceContainer}.
 */
public interface IChunkLoader {

    static @NotNull IChunkLoader noop() {
        return NoopChunkLoaderImpl.INSTANCE;
    }

    /**
     * Loads instance data from the loader.
     *
     * @param instance the instance to retrieve the data from
     */
    default void loadInstance(@NotNull Instance instance) {
    }

    /**
     * Loads a {@link Chunk}, all blocks should be set since the {@link net.minestom.server.instance.generator.Generator} is not applied.
     *
     * @param instance the {@link Instance} where the {@link Chunk} belong
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @return a {@link CompletableFuture} containing the chunk, or null if not present
     */
    @NotNull CompletableFuture<@Nullable Chunk> loadChunk(@NotNull Instance instance, int chunkX, int chunkZ);

    @NotNull CompletableFuture<Void> saveInstance(@NotNull Instance instance);

    /**
     * Saves a {@link Chunk} with an optional callback for when it is done.
     *
     * @param chunk the {@link Chunk} to save
     * @return a {@link CompletableFuture} executed when the {@link Chunk} is done saving,
     * should be called even if the saving failed (you can throw an exception).
     */
    @NotNull CompletableFuture<Void> saveChunk(@NotNull Chunk chunk);

    /**
     * Saves multiple chunks with an optional callback for when it is done.
     * <p>
     * Implementations need to check {@link #supportsParallelSaving()} to support the feature if possible.
     *
     * @param chunks the chunks to save
     * @return a {@link CompletableFuture} executed when the {@link Chunk} is done saving,
     * should be called even if the saving failed (you can throw an exception).
     */
    default @NotNull CompletableFuture<Void> saveChunks(@NotNull Collection<Chunk> chunks) {
        NamedThreadFactory threadFactory = NamedThreadFactory.of("Chunk-Save-Thread");
        try(ExecutorService executor = supportsParallelSaving() ? Executors.newFixedThreadPool(ServerFlag.PARALLEL_CHUNK_SAVE_SIZE, threadFactory) : Executors.newSingleThreadExecutor(threadFactory)) {;
            var toSaveChunks = chunks.stream().map(chunk -> CompletableFuture.completedFuture(chunk).thenComposeAsync(this::saveChunk, executor)).toArray(CompletableFuture[]::new);
            return CompletableFuture.allOf(toSaveChunks);
        }
    }

    /**
     * Does this {@link IChunkLoader} allow for multi-threaded saving of {@link Chunk}?
     *
     * @return true if the chunk loader supports parallel saving
     */
    default boolean supportsParallelSaving() {
        return false;
    }

    /**
     * Does this {@link IChunkLoader} allow for multi-threaded loading of {@link Chunk}?
     *
     * @return true if the chunk loader supports parallel loading
     */
    default boolean supportsParallelLoading() {
        return false;
    }

    /**
     * Called when a chunk is unloaded, so that this chunk loader can unload any resource it is holding.
     * Note: Minestom currently has no way to determine whether the chunk comes from this loader, so you may get
     * unload requests for chunks not created by the loader.
     *
     * @param chunk the chunk to unload
     */
    default void unloadChunk(Chunk chunk) {}
}
