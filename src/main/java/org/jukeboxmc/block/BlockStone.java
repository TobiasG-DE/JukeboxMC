package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStone extends Block {

    public BlockStone() {
        super( "minecraft:stone" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setStoneType( StoneType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getStoneType().ordinal() );
    }

    public void setStoneType( StoneType stoneType ) {
        this.setState( "stone_type", stoneType.name().toLowerCase() );
    }

    public StoneType getStoneType() {
        return this.stateExists( "stone_type" ) ? StoneType.valueOf( this.getStringState( "stone_type" ).toUpperCase() ) : StoneType.STONE;
    }

    public enum StoneType {
        STONE,
        GRANITE,
        GRANITE_SMOOTH,
        DIORITE,
        DIORITE_SMOOTH,
        ANDESITE,
        ANDESITE_SMOOTH
    }

}
