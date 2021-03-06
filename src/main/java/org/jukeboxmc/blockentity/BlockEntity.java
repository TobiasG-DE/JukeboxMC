package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockEntity {

    protected Block block;
    private boolean isMoveable = true;

    public BlockEntity( Block block ) {
        this.block = block;
    }

    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        return false;
    }

    public void setCompound( NbtMap compound ) {
        this.isMoveable = compound.getBoolean( "isMovable", true );
    }

    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = NbtMap.builder();
        BlockPosition position = this.block.getBlockPosition();
        compound.put( "x", position.getX() );
        compound.put( "y", position.getY() );
        compound.put( "z", position.getZ() );
        compound.putBoolean( "isMovable", this.isMoveable );
        return compound;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setMoveable( boolean moveable ) {
        this.isMoveable = moveable;
    }

    public boolean isMoveable() {
        return this.isMoveable;
    }
}
