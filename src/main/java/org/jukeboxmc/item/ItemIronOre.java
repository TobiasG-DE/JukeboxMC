package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronOre extends Item {

    public ItemIronOre() {
        super( "minecraft:iron_ore", 15 );
    }

    @Override
    public BlockIronOre getBlock() {
        return new BlockIronOre();
    }
}
