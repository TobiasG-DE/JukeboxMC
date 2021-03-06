package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkullBannerPattern extends Item {

    public ItemSkullBannerPattern() {
        super( "minecraft:skull_banner_pattern", 573 );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
