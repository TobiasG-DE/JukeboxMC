package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherWartBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherWartBlock extends Item {

    public ItemNetherWartBlock() {
        super( "minecraft:nether_wart_block", 214 );
    }

    @Override
    public BlockNetherWartBlock getBlock() {
        return new BlockNetherWartBlock();
    }
}
