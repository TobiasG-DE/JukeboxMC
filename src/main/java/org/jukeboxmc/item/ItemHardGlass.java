package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHardGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardGlass extends Item {

    public ItemHardGlass() {
        super( "minecraft:hard_glass", 253 );
    }

    @Override
    public BlockHardGlass getBlock() {
        return new BlockHardGlass();
    }
}
