package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.SignDirection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceStandingSign extends Block {

    public BlockSpruceStandingSign() {
        super( "minecraft:spruce_standing_sign" );
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}