package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchDoorBlock extends Item {

    public ItemBirchDoorBlock() {
        super( "minecraft:item.birch_door", 194 );
    }

    @Override
    public BlockBirchDoor getBlock() {
        return new BlockBirchDoor();
    }
}
