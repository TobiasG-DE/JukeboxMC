package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWitherRose;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWitherRose extends Item {

    public ItemWitherRose() {
        super( "minecraft:wither_rose", -216 );
    }

    @Override
    public BlockWitherRose getBlock() {
        return new BlockWitherRose();
    }
}
