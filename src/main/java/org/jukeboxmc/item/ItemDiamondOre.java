package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDiamondOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondOre extends Item {

    public ItemDiamondOre() {
        super( "minecraft:diamond_ore", 56 );
    }

    @Override
    public BlockDiamondOre getBlock() {
        return new BlockDiamondOre();
    }
}
