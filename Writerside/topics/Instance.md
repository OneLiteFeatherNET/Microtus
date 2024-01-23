# Instance

The Instance class from Minestom represents the world object which is known from other server implementations which
relies on the vanilla server from Mojang. In comparison to the vanilla server, the Instance doesn't have the same
functionality. The project made some changes which adds additional features to the implementation of the Instance. Each
additional changes is listed below.

## Worldspawn Position

In the original version of Minestom the Instance implementation and definition doesn't contain the ability to store the
spawn position of the instance.
Microtus received this functionality which this [pull request](https://github.com/OneLiteFeatherNET/Microtus/pull/67).

The mentioned pull request adds the following methods to the Instance class:

```java
/**
 * Sets the world spawn position.
 *
 * @param position rhe position of the world spawn.
 */
public void setWorldSpawnPosition(Pos position);

/**
 * Sets the world spawn position.
 *
 * @param position   the position of the world spawn.
 * @param sendPacket whether the packet should be sent to the players.
 */
setWorldSpawnPosition(Pos position, boolean sendPacket);

/**
 * Gets the world spawn position.
 *
 * @return the world spawn position or null when no position is set.
 */
@Nullable Pos getWorldSpawn();
```

The `setWorldSpawnPosition` method is implemented in two different variants. The first one without the boolean flag
doesn't send the `SpawnPositionPacket` to the players which are currently registered in the instance. If your use case
needs the packet to be sent, you can use the second variant of the method.

If you need to track the change of the world spawn position, you can use the `WorldSpawnChangeEvent` which is called
when the world spawn position is changed.

From the event you can get the involved `Instance` and the old and new `Pos` of the world spawn position.


> The current implementation of the world spawn handling doesn't include the ability to load the world spawn position
> from the world data. This will be added in a future release.
> For more information why this is not possible at the moment, see
> the [pull request](https://github.com/OneLiteFeatherNET/Microtus/pull/67)
> {style="warning"}
