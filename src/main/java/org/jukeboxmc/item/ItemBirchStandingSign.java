package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchStandingSign extends Item {

    public ItemBirchStandingSign() {
        super( "minecraft:birch_standing_sign", -186 );
    }

    @Override
    public BlockBirchStandingSign getBlock() {
        return new BlockBirchStandingSign();
    }
}
