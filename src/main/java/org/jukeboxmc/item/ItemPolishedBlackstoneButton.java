package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneButton extends Item {

    public ItemPolishedBlackstoneButton() {
        super( "minecraft:polished_blackstone_button", -296 );
    }

    @Override
    public BlockPolishedBlackstoneButton getBlock() {
        return new BlockPolishedBlackstoneButton();
    }
}
