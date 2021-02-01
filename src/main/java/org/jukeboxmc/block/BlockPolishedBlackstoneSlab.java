package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

public class BlockPolishedBlackstoneSlab extends BlockSlab {

    public BlockPolishedBlackstoneSlab() {
        super("minecraft:polished_blackstone_slab");
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );

        Block targetBlock = world.getBlock( blockPosition );
        Block block = world.getBlock( placePosition );

        if ( blockFace == BlockFace.DOWN ) {
            if ( targetBlock instanceof BlockPolishedBlackstoneSlab ) {
                BlockPolishedBlackstoneSlab blockSlab = (BlockPolishedBlackstoneSlab) targetBlock;
                if ( blockSlab.isTopSlot() ) {
                    world.setBlock( blockPosition, new BlockDoublePolishedBlackstoneSlab() );
                    return true;
                }
            } else if ( block instanceof BlockPolishedBlackstoneSlab ) {
                world.setBlock( placePosition, new BlockDoublePolishedBlackstoneSlab() );
                return true;
            }
        } else if ( blockFace == BlockFace.UP ) {
            if ( targetBlock instanceof BlockPolishedBlackstoneSlab ) {
                BlockPolishedBlackstoneSlab blockSlab = (BlockPolishedBlackstoneSlab) targetBlock;
                if ( !blockSlab.isTopSlot()) {
                    world.setBlock( blockPosition, new BlockDoublePolishedBlackstoneSlab());
                    return true;
                }
            } else if ( block instanceof BlockPolishedBlackstoneSlab ) {
                world.setBlock( placePosition,  new BlockDoublePolishedBlackstoneSlab() );
                return true;
            }
        } else {
            if ( block instanceof BlockPolishedBlackstoneSlab ) {
                world.setBlock( placePosition,new BlockDoublePolishedBlackstoneSlab()  );
                return true;
            }
        }
        world.setBlock( placePosition, this );
        return true;
    }
}