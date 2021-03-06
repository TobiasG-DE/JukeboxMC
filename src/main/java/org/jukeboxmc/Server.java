package org.jukeboxmc;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerListPacket;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.Listener;
import org.jukeboxmc.network.raknet.event.intern.PlayerCloseConnectionEvent;
import org.jukeboxmc.network.raknet.event.intern.PlayerConnectionSuccessEvent;
import org.jukeboxmc.network.raknet.event.intern.ReciveMinecraftPacketEvent;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.EmptyGenerator;
import org.jukeboxmc.world.generator.FlatGenerator;
import org.jukeboxmc.world.generator.WorldGenerator;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Server {

    @Getter
    @Setter
    private static Server instance;

    private InetSocketAddress address;
    private Listener listener;

    private Config serverConfig;

    private World defaultWorld;
    private WorldGenerator overWorldGenerator;

    private int viewDistance = 32;

    private boolean isShutdown = false;

    private Map<InetSocketAddress, Player> players = new HashMap<>();
    private Map<UUID, PlayerListPacket.Entry> playerListEntry = new HashMap<>();
    private Map<String, World> worlds = new HashMap<>();
    private Map<String, WorldGenerator> worldGenerator = new HashMap<>();

    public Server() {
        Server.setInstance( this );

        this.initServerConfig();

        this.address = new InetSocketAddress( this.serverConfig.getString( "address" ), this.serverConfig.getInt( "port" ) );

        this.listener = new Listener( this );
        if ( !this.listener.listen( this.address ) ) {
            System.out.println( "Der Server konnte nicht starten, läuft er bereits auf dem gleichen Port?" );
            return;
        }

        this.listener.getRakNetEventManager().onEvent( ReciveMinecraftPacketEvent.class, (Consumer<ReciveMinecraftPacketEvent>) event -> {
            Connection connection = event.getConnection();
            Packet packet = event.getPacket();
            Player player = this.players.get( connection.getSender() );
            player.getPlayerConnection().handlePacketSync( packet );
        } );

        this.listener.getRakNetEventManager().onEvent( PlayerConnectionSuccessEvent.class, (Consumer<PlayerConnectionSuccessEvent>) event -> {
            Connection connection = event.getConnection();

            Player player = new Player( this, connection );
            this.players.put( player.getAddress(), player );
            this.setOnlinePlayers( this.players.size() );
        } );

        this.listener.getRakNetEventManager().onEvent( PlayerCloseConnectionEvent.class, (Consumer<PlayerCloseConnectionEvent>) event -> {
            Connection connection = event.getConnection();
            Player player = this.players.get( connection.getSender() );
            if ( player != null ) {
                player.getPlayerConnection().leaveGame();
            }
        } );

        this.registerGenerator( "Flat", FlatGenerator.class );
        this.registerGenerator( "Empty", EmptyGenerator.class );
        this.overWorldGenerator = this.worldGenerator.get( this.serverConfig.getString( "generator" ) );

        String defaultWorldName = this.serverConfig.getString( "defaultworld" );
        if ( this.loadWorld( defaultWorldName ) ) {
            this.defaultWorld = this.getWorld( defaultWorldName );
        } else {
            this.defaultWorld = this.createWorld( defaultWorldName, this.overWorldGenerator );
        }

        AtomicLong startTime = new AtomicLong();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate( () -> {
            try {
                long currentTick = startTime.getAndIncrement();
                if ( this.defaultWorld != null ) {
                    this.defaultWorld.update( currentTick );
                }
                for ( Player player : this.players.values() ) {
                    player.getPlayerConnection().updateNetwork( currentTick );
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }, 0, 50, TimeUnit.MILLISECONDS );
    }


    private void initServerConfig() {
        this.serverConfig = new Config( new File( System.getProperty( "user.dir" ) ), "properties.json" );
        this.serverConfig.addDefault( "address", "127.0.0.1" );
        this.serverConfig.addDefault( "port", 19132 );
        this.serverConfig.addDefault( "maxplayers", 20 );
        this.serverConfig.addDefault( "motd", "§bJukeboxMC" );
        this.serverConfig.addDefault( "gamemode", GameMode.CREATIVE.name() );
        this.serverConfig.addDefault( "defaultworld", "world" );
        this.serverConfig.addDefault( "generator", "flat" );
        this.serverConfig.save();
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public Config getServerConfig() {
        return this.serverConfig;
    }

    public void setOnlinePlayers( int onlinePlayers ) {
        this.listener.getServerInfo().setOnlinePlayers( onlinePlayers );
    }

    public void setMaxPlayers( int maxPlayers ) {
        this.listener.getServerInfo().setMaxPlayers( maxPlayers );
    }

    public void setMotd( String motd ) {
        this.listener.getServerInfo().setMotd( motd );
    }

    public void setDefaultGamemode( GameMode defaultGamemode ) {
        this.listener.getServerInfo().setGameMode( defaultGamemode );
    }

    public GameMode getDefaultGamemode() {
        return this.listener.getServerInfo().getGameMode();
    }

    public String getMotd() {
        return this.listener.getServerInfo().getMotd();
    }

    public int getPort() {
        return this.address.getPort();
    }

    public String getHostname() {
        return this.address.getHostName();
    }

    public World getWorld( String name ) {
        for ( World world : this.worlds.values() ) {
            if ( world.getName().equalsIgnoreCase( name ) ) {
                return world;
            }
        }
        return null;
    }

    public World getDefaultWorld() {
        return this.defaultWorld;
    }

    public Collection<World> getWorlds() {
        return this.worlds.values();
    }

    public WorldGenerator getOverworldGenerator() {
        return this.overWorldGenerator;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public boolean isShutdown() {
        return this.isShutdown;
    }

    public void shutdown() {
        this.isShutdown = true;
    }

    public Collection<Player> getOnlinePlayers() {
        return this.players.values();
    }

    public void removePlayer( InetSocketAddress address ) {
        this.players.remove( address );
    }

    public Map<UUID, PlayerListPacket.Entry> getPlayerListEntry() {
        return this.playerListEntry;
    }

    public World createWorld( String worldName, WorldGenerator worldGenerator ) {
        File worldFolder = new File( "./worlds/" + worldName );

        if ( !worldFolder.exists() ) {
            if ( !worldFolder.mkdirs() ) {
                System.out.println( worldName + " could not be created because the folder could not be created" );
            }
        }

        File regionFolder = new File( worldFolder, "db" );
        if ( !regionFolder.mkdir() ) {
            System.out.println( worldName + " could not be created because the \"db\" folder could not be created" );
        }

        return new World( worldName, worldGenerator );
    }

    public boolean loadWorld( String worldName ) {
        return this.loadWorld( worldName, this.overWorldGenerator );
    }

    public boolean loadWorld( String worldName, WorldGenerator worldGenerator ) {
        if ( !this.worlds.containsKey( worldName.toLowerCase() ) ) {
            World world = new World( worldName, worldGenerator );
            if ( world.loadLevelFile() && world.open() ) {
                this.worlds.put( worldName.toLowerCase(), world );
                System.out.println( worldName + " was successfully loaded" );
                return true;
            } else {
                System.out.println( "Failed to load world: " + worldName );
            }
        } else {
            System.out.println( worldName + " was already loaded" );
        }
        return false;
    }

    public void unloadWorld( String worldName ) {
        this.unloadWorld( worldName, player -> {
            World world = this.getWorld( worldName );
            if ( world != null ) {
                if ( world == this.defaultWorld || this.defaultWorld == null ) {
                    player.disconnect( "World was unloaded" );
                } else {
                    player.disconnect( "World was unloaded" );
                    //TODO Teleport player to default world
                }
            } else {
                System.out.println( "World " + worldName + " was not found" );
            }
        } );
    }

    public void unloadWorld( String worldName, Consumer<Player> consumer ) {
        World world = this.getWorld( worldName );
        if ( world != null ) {
            for ( Player player : world.getPlayers() ) {
                consumer.accept( player );
            }
            world.close();
        } else {
            System.out.println( "World " + worldName + " was not found" );
        }
    }

    @SneakyThrows
    public void registerGenerator( String name, Class<? extends WorldGenerator> clazz ) {
        if ( !this.worldGenerator.containsKey( name ) ) {
            this.worldGenerator.put( name.toLowerCase(), clazz.newInstance() );
        }
    }

    public void broadcastMessage( String message ) {
        for ( Player onlinePlayers : this.players.values() ) {
            onlinePlayers.sendMessage( message );
        }
        System.out.println( "[Broadcast] " + message );
    }

    public void broadcastPacket( Packet packet ) {
        for ( Player onlinePlayers : this.players.values() ) {
            onlinePlayers.getPlayerConnection().sendPacket( packet );
        }
    }
}
