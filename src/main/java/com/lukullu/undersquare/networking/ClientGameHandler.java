package com.lukullu.undersquare.networking;

import com.kilix.p2p.client.KilixP2PClient;
import com.kilix.p2p.client.KilixP2PClientImpl;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.game.entity.Entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;

public class ClientGameHandler extends GameHandler {

    KilixP2PClient client;

    @SuppressWarnings("unchecked")
    public ClientGameHandler(LevelMap _levelMap) {
        super(_levelMap);

        client = KilixP2PClient.create();

        try {
            client.connect("p2p.aether.net.co", 7840);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            client.joinRoom(Base64.getDecoder().decode(bufferedReader.readLine()));

            if (client == null); // TODO: failed
    
            ((KilixP2PClientImpl) client).onReceive((ch, packet) -> System.out.printf("[%s] %s", ch, packet));
            
            client.subscribe("EntityUpdates",(packet) -> {
                if(packet instanceof ArrayList entityList){ onEntityUpdate((ArrayList<Entity>) entityList);}
            });

        }catch (Exception e){}

    }

    public void onEntityUpdate(ArrayList<Entity> _entities){

        entities = _entities;

    }

}
