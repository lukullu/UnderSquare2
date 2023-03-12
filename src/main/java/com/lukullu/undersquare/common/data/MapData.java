package com.lukullu.undersquare.common.data;

import java.util.HashMap;

public class MapData {
    
    public void initMapData(LevelMap levelMap)
    {

        if(levelMap != null){

            for(int i = 0; i < levelMap.map.length; i++)
            {
            
                for(int j = 0; j < levelMap.map[0].length; j++)
                {
                    switch(levelMap.map[j][i])
                    {
                        case 'p':
                            this.playerSettings.put(new Vector2(j, i), new int[playerSettingsAmount]);
                            break;

                        case 'i':
                            this.itemSettings.put(new Vector2(j, i), new int[itemSettingsAmount]);
                            break;

                        case 'e':
                            this.enemySettings.put(new Vector2(j, i), new int[enemySettingsAmount]);
                            break;

                        default:
                            break;

                    }
                }
            
            }

        }
        else
        {



        }
    }

    // ---------------------------------------- ENEMY

    public HashMap<Vector2,int[]> enemySettings = new HashMap<Vector2,int[]>();

    public static int enemySettingsAmount = 1;
    // [0] ... type 

    public void setEnemySetting(Vector2 pos, int value, int setting)
    {
        int[] settings = enemySettings.get(pos);

        settings[setting] = value;

        enemySettings.put(pos, settings);
    }

    public int getEnemySetting(Vector2 pos, int setting)
    {
        return enemySettings.get(pos)[setting];
    }

    // ---------------------------------------- ITEM

    public HashMap<Vector2,int[]> itemSettings = new HashMap<Vector2,int[]>();
    
    public static int itemSettingsAmount = 1;
    // [0] ... type

    public void setItemSetting(Vector2 pos, int value, int setting)
    {
        int[] settings = itemSettings.get(pos);

        settings[setting] = value;

        itemSettings.put(pos, settings);
    }

    public int getItemSetting(Vector2 pos, int setting)
    {
        return itemSettings.get(pos)[setting];
    }

    // ---------------------------------------- PLAYER

    public HashMap<Vector2,int[]> playerSettings = new HashMap<Vector2,int[]>();

    public static int playerSettingsAmount = 1;
    // [0] ... type

    public void setPlayerSetting(Vector2 pos, int value, int setting)
    {
        int[] settings = playerSettings.get(pos);

        settings[setting] = value;

        playerSettings.put(pos, settings);
    }

    public int getPlayerSetting(Vector2 pos, int setting)
    {
        return playerSettings.get(pos)[setting];
    }

    // ---------------------------------------- ACTOR

    public HashMap<Vector2,int[]> actorSettings = new HashMap<Vector2,int[]>();

    public static int actorSettingsAmount = 1;
    // [0] ... type

    public void setActorSetting(Vector2 pos, int value, int setting)
    {
        int[] settings = actorSettings.get(pos);

        settings[setting] = value;

        actorSettings.put(pos, settings);
    }

    public int getActorSetting(Vector2 pos, int setting)
    {
        return actorSettings.get(pos)[setting];
    }

    // ---------------------------------------- Sensor

    public HashMap<Vector2,int[]> sensorSettings = new HashMap<Vector2,int[]>();

    public static int sensorSettingsAmount = 1;
    // [0] ... id

    public void setSensorSetting(Vector2 pos, int value, int setting)
    {
        int[] settings = sensorSettings.get(pos);

        settings[setting] = value;

        sensorSettings.put(pos, settings);
    }

    public int getSensorSetting(Vector2 pos, int setting)
    {
        return sensorSettings.get(pos)[setting];
    }

    // ---------------------------------------- GAME

    public int[] gameSettings = new int[1];
    // [0] ... pointsForLife

}
