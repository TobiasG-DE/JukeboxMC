package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeetroot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeetroot extends Item {

    public ItemBeetroot() {
        super( "minecraft:beetroot", 285 );
    }

    @Override
    public BlockBeetroot getBlock() {
        return new BlockBeetroot();
    }
}
