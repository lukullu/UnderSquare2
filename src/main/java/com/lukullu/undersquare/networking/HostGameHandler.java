package com.lukullu.undersquare.networking;

import com.kilix.p2p.client.KilixP2PClient;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.game.LevelMap;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.menu.HostPauseMenu;
import com.lukullu.undersquare.menu.PauseMenu;

import static com.lukullu.undersquare.common.Constants.*;

import java.io.IOException;
import java.util.Base64;

public class HostGameHandler extends GameHandler {

    public float timeSinceLastPacket = 0;

    public final KilixP2PClient client;
    public String roomToken = "";
    public HostGameHandler(LevelMap _levelMap) {
        super(_levelMap);

        client = KilixP2PClient.create();

        try {
            client.connect("p2p.aether.net.co", 7840);
            client.createRoom();

            if (client == null); // TODO: failed

        }catch (Exception e){}
        roomToken = Base64.getEncoder().encodeToString(client.getRoomToken());
    }

    @Override
    public void init(){ super.init(); }

    public void initLobby(){ }

    @Override
    protected ProgramState getPauseMenu() {
        return new HostPauseMenu(this);
    }

    @Override
    public void update(){
        super.update();
        sendEntitiesPackage();
    }

    Vector2 lastPlayerPositionSent = ZERO_VECTOR_2;
    public void sendEntitiesPackage() {

        float timeBetweenPackets = 1/PACKET_RATE;
        if(timeSinceLastPacket < timeBetweenPackets){
            try {
                client.send("EntityUpdates", entities);
            } catch (Exception e) {}
        }

    }

}
