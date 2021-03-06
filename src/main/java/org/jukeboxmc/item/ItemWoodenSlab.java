package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenSlab extends Item {

    public ItemWoodenSlab() {
        super( "minecraft:wooden_slab", 158 );
    }

    @Override
    public BlockWoodenSlab getBlock() {
        return new BlockWoodenSlab();
    }

    public void setWoodType( WoodType woodType ) {
        this.setMeta( woodType.ordinal() );
    }

    public WoodType getWoodType() {
        return WoodType.values()[this.getMeta()];
    }

    public enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
