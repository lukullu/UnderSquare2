package com.lukullu.undersquare.common;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.common.data.MapData;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.enemy.Bouncer;
import com.lukullu.undersquare.game.entity.enemy.Enemy;
import com.lukullu.undersquare.game.entity.enemy.Spawner;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.game.geometry.LevelGeometry;
import com.lukullu.undersquare.game.item.Item;
import com.lukullu.undersquare.game.item.ItemBox;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.game.item.Potion.*;
import static com.lukullu.undersquare.game.item.Weapon.*;

public class IO implements ProcessingClass {

    public static boolean[][] convertMapDataToCollisionData(char[][] inputMap){

        boolean[][] output = new boolean[inputMap.length][inputMap[0].length];
        for(int i = 0; i < output.length; i++){
            for(int j = 0; j < output[0].length; j++){
                if(inputMap[i][j] == '#'){
                    output[i][j] = true;
                }
            }
        }
        return output;
    }

    public static String[] loadDeathMessages(){
        try {
            InputStream data = IO.class.getClassLoader().getResourceAsStream("misc/deathMessages.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(data));
            String[] out = reader.lines().toList().toArray(new String[0]);
            reader.close();
            return out;
        }catch (Exception e){}

        return new String[0];

    }

    public static Map<Integer, Item> loadItemIndicesMap(){
        Map<Integer, Item> itemIndicesMap = new HashMap<>();
        itemIndicesMap.put(0,SNIPER);
        itemIndicesMap.put(1,MACHINEGUN);
        itemIndicesMap.put(2,FLAMETHROWER);
        itemIndicesMap.put(3,QUADSHOT);
        itemIndicesMap.put(4,SHOTGUN);
        itemIndicesMap.put(5,ORBIT);
        itemIndicesMap.put(6,SMALL_POTION);
        itemIndicesMap.put(7,MEDIUM_POTION);
        itemIndicesMap.put(8,LARGE_POTION);

        return itemIndicesMap;
    }

    public static Map<Integer, Enemy> loadEnemyIndicesMap(){
        return null; //TODO: implement if there are more enemies
    }

    //TODO: Read item and enemy types from pos and index in int array
    public static LevelGeometry[][] createMapElements(char[][] map, boolean[][] collisionData, LevelMap levelMap){

        Map<Integer, Item> itemIndicesMap = loadItemIndicesMap();

        LevelGeometry[][] output = new LevelGeometry[map.length][map[0].length];
        UnderSquare.getGameHandler().entities = new ArrayList<>();

        int itemCounter = 0;
        int enemyCounter = 0;
        int playerCounter = 0;

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(collisionData[i][j]){
                    output[j][i] = new LevelGeometry(new Vector2(j * mapGridSize, i * mapGridSize),new Vector2(mapGridSize,mapGridSize), Color.black, true);
                }else{
                    
                    if(map[j][i] == 'p')
                    { 
                        
                        UnderSquare.getGameHandler().entities.add(
                            new Player(
                                new Vector2(
                                    i * mapGridSize  + mapGridSize/2 - playerDimensions/2,
                                    j * mapGridSize  + mapGridSize/2 - playerDimensions/2
                                ), 
                                new Vector2(
                                    playerDimensions,
                                    playerDimensions
                                )   
                            )
                        ); 

                        playerCounter++;
                    }
                    
                    
                    if(map[j][i] == 'i')
                    { 
                        
                        UnderSquare.getGameHandler().entities.add(
                            new ItemBox(
                                new Vector2(
                                    i * mapGridSize + mapGridSize/2 - itemBoxDimensions/2, 
                                    j * mapGridSize + mapGridSize/2 - itemBoxDimensions/2
                                ),
                                new Vector2(
                                    itemBoxDimensions,
                                    itemBoxDimensions
                                ), 
                                itemIndicesMap.get(
                                    levelMap.settings.itemSettings.get(
                                        new Vector2(
                                            j, 
                                            i
                                        )
                                    )[0]
                                )
                            )
                        ); 
                            
                        itemCounter++;
                    
                    }
                    

                    if(map[j][i] == 'e')
                    {   

                        switch(levelMap.settings.enemySettings.get(new Vector2(j, i))[0])
                        {

                            case 0: 
                                UnderSquare.getGameHandler().entities.add(new Bouncer(new Vector2(i * mapGridSize  + mapGridSize/2 - enemyDimensions/2, j * mapGridSize  + mapGridSize/2 - enemyDimensions/2), new Vector2(enemyDimensions,enemyDimensions)));
                                break;
                            case 1:
                            UnderSquare.getGameHandler().entities.add(new Spawner(new Vector2(i * mapGridSize  + mapGridSize/2 - enemyDimensions/2, j * mapGridSize  + mapGridSize/2 - enemyDimensions/2), new Vector2(enemyDimensions,enemyDimensions), -1, 0));
                                break;

                        }

                        enemyCounter++;
                    }

                }
            }
        }
        return output;
    }

    public static Map<String, String> collectFiles() {

        File[] mapFiles = MAPS_BASE_DIR.listFiles((file) -> file.isFile() && file.getName().toLowerCase(Locale.ROOT).endsWith(".json"));

        System.out.println(mapFiles.length);

        Map<String,String> output = new HashMap<>();

        for(File file : mapFiles) {
            try (FileReader reader = new FileReader(file)) {
                String mapName;
                mapName = GSON.fromJson(reader, LevelMap.class).name;

                if(output.containsKey(mapName))
                    for(int i = 0; output.containsKey(mapName + "-" + i); i++) mapName = mapName + "-" + i;

                output.put(mapName,file.getPath());

            } catch(Exception e){e.printStackTrace();}
        }

        return output;
    }

    public static void saveLevelMapAsJson(LevelMap levelmap, File file) {

        try {
            String json = GSON.toJson(levelmap);
            if (file == null) {
                file = new File(MAPS_BASE_DIR, levelmap.name + ".json");
            }

            // makin' sure the file is a file - kilix
            if (!file.isFile()) {
                if (file.exists()) throw new RuntimeException("is not file you fucknut!");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // try-with-resource closes, even when there was a CATASTROPHIC failure - kilix
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(json);
                writer.flush();
            }

        }catch(Exception e){}

    }


}
