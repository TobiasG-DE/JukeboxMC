package org.jukeboxmc.entity;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.SetEntityDataPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    private long entityId = 0;
    protected Location location;
    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;

    public Entity() {
        this.metadata = new Metadata();
        this.metadata.setLong( MetadataFlag.INDEX, 0 );
        this.metadata.setShort( MetadataFlag.MAX_AIR, (short) 400 );
        this.metadata.setFloat( MetadataFlag.SCALE, 1 );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_WIDTH, this.getWidth() );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_HEIGHT, this.getHeight() );
        this.metadata.setShort( MetadataFlag.AIR, (short) 0 );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.AFFECTED_BY_GRAVITY, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, true );

        this.location = new Location( Server.getInstance().getDefaultWorld(), 0, 7, 0, 0, 0 );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalcBoundingBox();
    }

    public abstract String getName();

    public abstract String getEntityType();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract float getEyeHeight();

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId( long entityId ) {
        this.entityId = entityId;
    }

    public String getNameTag() {
        return this.metadata.getString( MetadataFlag.NAMETAG );
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
        this.recalcBoundingBox();
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public Direction getDirection() {
        double rotation = this.location.getYaw() % 360;
        if ( rotation < 0 ) {
            rotation += 360.0;
        }

        if ( 45 <= rotation && rotation < 135 ) {
            return Direction.WEST;
        } else if ( 135 <= rotation && rotation < 225 ) {
            return Direction.NORTH;
        } else if ( 225 <= rotation && rotation < 315 ) {
            return Direction.EAST;
        } else {
            return Direction.SOUTH;
        }
    }

    //NOT READY ONLY FOR TESTS
    public SignDirection getSignDirection() {
        return SignDirection.values()[(int) Math.floor( ( ( this.location.getYaw() + 180 ) * 16 / 360 ) + 0.5 ) & 0x0f];
    }

    public void setNameTag( String value ) {
        this.metadata.setString( MetadataFlag.NAMETAG, value );
    }

    public boolean isNameTagVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG );
    }

    public void setNameTagVisible( boolean value ) {
        if ( value != this.isNameTagVisible() ) {
            this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG, value );
        }
    }

    public boolean isNameTagAlwaysVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG );
    }

    public void setNameTagAlwaysVisible( boolean value ) {
        if ( value != this.isNameTagAlwaysVisible() ) {
            this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG, value );
        }
    }

    public boolean canClimb() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB );
    }

    public void setCanClimb( boolean value ) {
        if ( value != this.canClimb() ) {
            this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, value );
        }
    }

    public void updateMetadata( Metadata metadata ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.entityId );
        setEntityDataPacket.setMetadata( metadata );
        setEntityDataPacket.setTick( 0 );
        for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
            onlinePlayer.getPlayerConnection().sendPacket( setEntityDataPacket );
        }
    }

    public void recalcBoundingBox() {
        Location location = this.getLocation();
        this.boundingBox.setBounds(
                location.getX() - ( this.getWidth() / 2 ),
                location.getY(),
                location.getZ() - ( this.getWidth() / 2 ),
                location.getX() + ( this.getWidth() / 2 ),
                location.getY() + this.getHeight(),
                location.getZ() + ( this.getWidth() / 2 )
        );
    }

}
