package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHardStainedGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardStainedGlassPane extends Item {

    public ItemHardStainedGlassPane() {
        super( "minecraft:hard_stained_glass_pane", 191 );
    }

    @Override
    public BlockHardStainedGlassPane getBlock() {
        return new BlockHardStainedGlassPane();
    }
}
