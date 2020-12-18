package org.jukeboxmc.block;

import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.nbt.NbtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPalette {

    private static final Map<Integer, NbtMap> BLOCK_PALETTE = new HashMap<>();
    private static final AtomicInteger RUNTIME_COUNTER = new AtomicInteger( 0 );

    static {
        try ( NBTInputStream nbtReader = NbtUtils.createReader( JukeboxMC.class.getClassLoader().getResourceAsStream( "blockpalette.nbt" ) ) ) {
            NbtMap nbtMap = (NbtMap) nbtReader.readTag();
            for ( NbtMap blockMap : nbtMap.getList( "blocks", NbtType.COMPOUND ) )
                BLOCK_PALETTE.put( RUNTIME_COUNTER.getAndIncrement(), blockMap.getCompound( "block" ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        System.out.println("LOAD " + BLOCK_PALETTE.size() + " BLOCK STATES");
    }

    public static Integer getRuntimeId( NbtMap blockMap ) {
        return BLOCK_PALETTE.keySet().stream().filter( runtimeId -> BLOCK_PALETTE.get( runtimeId ).equals( blockMap ) ).findFirst().orElseThrow( () -> new RuntimeException( "Block was not found" ) );
    }

    public static NbtMap getBlockMap( Integer runtimeId ) {
        return Objects.requireNonNull( BLOCK_PALETTE.get( runtimeId ), "Block was not found" );
    }

    public static NbtMap searchBlock( Predicate<NbtMap> predicate ) {
        return BLOCK_PALETTE.values().stream().filter( predicate ).findFirst().orElseThrow( () -> new RuntimeException( "Block was not found" ) );
    }

}