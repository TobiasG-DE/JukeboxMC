package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityContainer extends BlockEntity {

    private String customName = null;

    public BlockEntityContainer( Block block ) {
        super( block );
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
        this.customName = compound.getString( "CustomName" );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        if ( this.customName != null ) {
            compound.put( "CustomName", this.customName );
        }
        return compound;
    }

    public void spawn() {
        World world = this.block.getWorld();
        BlockPosition location = this.block.getBlockPosition();

        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( location );
        blockEntityDataPacket.setNbt( this.toCompound().build() );
        world.sendWorldPacket( blockEntityDataPacket );

        world.setBlockEntity( location, this );
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName( String customName ) {
        this.customName = customName;
    }
}
