package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedJungleLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedJungleLog extends Item {

    public ItemStrippedJungleLog() {
        super( "minecraft:stripped_jungle_log", -7 );
    }

    @Override
    public BlockStrippedJungleLog getBlock() {
        return new BlockStrippedJungleLog();
    }
}
