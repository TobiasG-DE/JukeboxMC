package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherWart;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherWart extends Item {

    public ItemNetherWart() {
        super( "minecraft:nether_wart", 294 );
    }

    @Override
    public BlockNetherWart getBlock() {
        return new BlockNetherWart();
    }
}
