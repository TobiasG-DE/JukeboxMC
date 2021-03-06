package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.StoneSlab3Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStoneSlab3;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneSlab3 extends BlockSlab {

    public BlockStoneSlab3() {
        super( "minecraft:stone_slab3" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        this.setStoneSlabType( StoneSlab3Type.values()[itemIndHand.getMeta()] );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockStoneSlab3 ) {
                BlockStoneSlab3 blockSlab = (BlockStoneSlab3) targetBlock;
                if ( blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab3 ) {
                BlockStoneSlab3 blockSlab = (BlockStoneSlab3) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockStoneSlab3 ) {
                BlockStoneSlab3 blockSlab = (BlockStoneSlab3) targetBlock;
                if ( !blockSlab.isTopSlot() && blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            } else if ( block instanceof BlockStoneSlab3 ) {
                BlockStoneSlab3 blockSlab = (BlockStoneSlab3) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        } else {
            if ( block instanceof BlockStoneSlab3 ) {
                BlockStoneSlab3 blockSlab = (BlockStoneSlab3) block;
                if ( blockSlab.getStoneSlabType() == this.getStoneSlabType() ) {
                    world.setBlock( blockPosition, new BlockDoubleStoneSlab3().setStoneSlabType( this.getStoneSlabType() ) );
                    return true;
                }
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        return new ItemStoneSlab3().setMeta( this.getStoneSlabType().ordinal() );
    }

    public void setStoneSlabType( StoneSlab3Type stoneSlabType ) {
        this.setState( "stone_slab_type_3", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlab3Type getStoneSlabType() {
        return this.stateExists( "stone_slab_type_3" ) ? StoneSlab3Type.valueOf( this.getStringState( "stone_slab_type_3" ).toUpperCase() ) : StoneSlab3Type.END_STONE_BRICK;
    }
}
