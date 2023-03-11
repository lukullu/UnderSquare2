package com.lukullu.undersquare.networking;

import com.kilix.p2p.client.KilixP2PClient;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.menu.HostPauseMenu;

import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

import static com.lukullu.undersquare.common.Constants.PACKET_RATE;

public class HostGameHandler extends GameHandler {

    public float timeSinceLastPacket = 0;

    public final KilixP2PClient client;
    public String roomToken = "";
    
    Timer updateTimer = new Timer(true);
    TimerTask entityUpdateTask = new TimerTask() { public void run() { sendEntitiesPackage(); }};
    
    public HostGameHandler(LevelMap _levelMap) {
        super(_levelMap);

        client = KilixP2PClient.create();

        try {
            client.connect("p2p.aether.net.co", 7840);
            client.createRoom();

        }catch (Exception e){}
        roomToken = Base64.getEncoder().encodeToString(client.getRoomToken());
        
        updateTimer.scheduleAtFixedRate(entityUpdateTask, 0, 100L);
        
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("bye bye");
        updateTimer.cancel();
    }
    
    @Override
    public void init(){ super.init(); }

    public void initLobby(){ }

    @Override
    protected ProgramState getPauseMenu() {
        return new HostPauseMenu(this);
    }

    public void sendEntitiesPackage() {

        float timeBetweenPackets = 1/PACKET_RATE;
        if(timeSinceLastPacket < timeBetweenPackets){
            try {
                client.send("EntityUpdates", entities);
            } catch (Exception e) { println(e); }
        }

    }

}
