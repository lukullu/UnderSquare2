package com.lukullu.undersquare.editor;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.IO;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.common.data.MapData;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.menu.MainMenu;
import com.lukullu.undersquare.widgets.*;
import com.lukullu.undersquare.widgets.button.ButtonWidget;
import com.lukullu.undersquare.widgets.button.DeleteMapButton;
import com.lukullu.undersquare.widgets.button.LoadMapButton;
import com.lukullu.undersquare.widgets.button.SaveMapButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.util.ElementScanner14;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class LevelEditor extends ProgramState implements ProcessingClass {
	
	public Grid curGrid;
	public Widget saveButton;
	public Widget deleteButton;
	public Widget loadButton;
	public Widget gridBackDrop;
	public ListWidget tileSettings;
	public ListWidget legend;
	public Widget backButton;
	public Widget scrollListFiller;
	public TextWidget textFieldWidget;
	public ScrollWidget fileList = new ScrollWidget(ZERO_VECTOR_2, ZERO_VECTOR_2,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,"Maps:");
	
	public LevelMap mapToBeLoaded;
	public File fileToBeLoaded;

	public int curItemIndex = 0;
	public int curEnemyIndex = 0;

	public MapData curMapData = new MapData();


	@Override
	public void init() {

		UnderSquare.INSTANCE.cursor(ARROW);
		setLevel(null,null);
		curGrid = new Grid(new Vector2(scaleToScreenY(950),scaleToScreenY(950)),32,mapToBeLoaded, fileToBeLoaded);
		curMapData.initMapData(mapToBeLoaded);
		initWidgets();
		displayFiles(IO.collectFiles());
		
	}
	
	
	public void initWidgets() {

		textFieldWidget = new TextFieldWidget(
				new Vector2(
						scaleToScreenX(30),
						scaleToScreenY(90)
				),
				new Vector2(
						scaleToScreenX(400),
						scaleToScreenY(40)),
				ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
				"", DEFAULT_TEXT_SIZE, CORNER);

		gridBackDrop = new TextWidget(
						new Vector2(
								curGrid.offsetX - scaleToScreenX(25),
								curGrid.offsetY - scaleToScreenY(25)
						),
						new Vector2(
								curGrid.dim.x + scaleToScreenX(50),
								curGrid.dim.y + scaleToScreenY(50)),
					ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
					"", DEFAULT_TEXT_SIZE, CORNER);

		saveButton = new SaveMapButton(
				new Vector2(
						scaleToScreenX(30),
						scaleToScreenY(1000)
				),
				new Vector2(
						scaleToScreenX(133),
						scaleToScreenY(40)),
				ROUNDEDCORNERS,0,0,0,
				DEFAULT_TEXT_SIZE,
				CENTER,
				this);

		deleteButton = new DeleteMapButton(
				new Vector2(
						scaleToScreenX(30) + scaleToScreenX(133),
						scaleToScreenY(1000)
				),
				new Vector2(
						scaleToScreenX(134),
						scaleToScreenY(40)),
				0,0,0,0,
				DEFAULT_TEXT_SIZE,
				CENTER);

		loadButton = new LoadMapButton(
				new Vector2(
						scaleToScreenX(30) + scaleToScreenX(267),
						scaleToScreenY(1000)
				),
				new Vector2(
						scaleToScreenX(133),
						scaleToScreenY(40)),
				0,ROUNDEDCORNERS,0,0,
				DEFAULT_TEXT_SIZE,
				CENTER);

		backButton = new ButtonWidget(
				new Vector2(
						scaleToScreenX(30),
						scaleToScreenY(30)
				),
				new Vector2(
						scaleToScreenX(200),
						scaleToScreenY(40)
				),
				ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
				"Return to Menu",
				DEFAULT_TEXT_SIZE,
				CENTER,
				() -> { UnderSquare.changeState(new MainMenu());}
		);

		fileList = new ScrollWidget(
				new Vector2(
						scaleToScreenX(30),
						scaleToScreenY(150)
				),
				new Vector2(
						scaleToScreenX(400),
						scaleToScreenY(800)),
				ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
				"Maps:"
		);


		scrollListFiller = new Widget(
				new Vector2(
						scaleToScreenX(30),
						scaleToScreenY(950)
				),
				new Vector2(
						scaleToScreenX(400),
						scaleToScreenY(50)),
				0,0,0,0,
				UI_CONTRAST_COLOR
		);

		tileSettings = new ListWidget(
				new Vector2(
						scaleToScreenX(1490),
						scaleToScreenY(400)
				),
				new Vector2(
						scaleToScreenX(400),
						scaleToScreenY(640)),
				ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS
		);

		legend = new ListWidget(
				new Vector2(
						scaleToScreenX(1490),
						0
				),
				new Vector2(
						scaleToScreenX(200),
						scaleToScreenY(380)
				),
				ROUNDEDCORNERS,ROUNDEDCORNERS,0,0
		);

		initLegend();

	}

	public void displayFiles(Map<String, String> files){

		files.forEach((mapName,filePath) -> { fileList.addWidget(
				new ButtonWidget(
						new Vector2(scaleToScreenX(10),0),
						new Vector2(scaleToScreenX(380),scaleToScreenY(40)),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						mapName, DEFAULT_TEXT_SIZE, CENTER,
						() -> {

							try
							{
								File file = new File(filePath);
								FileReader reader = new FileReader(file);

								UnderSquare.state.setLevel(GSON.fromJson(reader, LevelMap.class),file);
								reader.close();
							}
							catch (Exception e)
							{
								println(e);
							}

						}
				)
		);});

	}
	
	
	@Override
	public void update(){

		textFieldWidget.update();
		curGrid.update();
		saveButton.update();
		deleteButton.update();
		loadButton.update();
		backButton.update();
		fileList.update();
		tileSettings.update();
		legend.update();
		
	}
	@Override
	public void paint() {

		background(UI_BACKGROUND_COLOR.getRGB());

		textFieldWidget.paint();
		gridBackDrop.paint();
		curGrid.paint();
		backButton.paint();
		fileList.paint(ZERO_VECTOR_2);
		saveButton.paint(ZERO_VECTOR_2);
		deleteButton.paint(ZERO_VECTOR_2);
		loadButton.paint(ZERO_VECTOR_2);
		tileSettings.paint();
		legend.paint();
		scrollListFiller.paint();

	}

	@Override
	public void setLevel(LevelMap map, File file){

		mapToBeLoaded = map;
		fileToBeLoaded = file;

	}

	public void openMenu(Vector2 pos){

		char selected = UnderSquare.getLevelEditor().curGrid.map.map[(int)pos.x][(int)pos.y];
		tileSettings.widgets = new ArrayList<>();

		int defaultValue = 0;

		switch (selected){
			case 'p':

				tileSettings.widgets.add(
						new TextWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								"Player Settings:",
								DEFAULT_TEXT_SIZE,
								CORNER
						)
				);

				tileSettings.widgets.add(
						new ButtonWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								"test",
								DEFAULT_TEXT_SIZE,
								CENTER,
								() -> {}
						)
				);
				break;
			case 'i':

				if(curMapData.itemSettings.containsKey(pos))
					curItemIndex = curMapData.getItemSetting(pos, 0);
					else
					{
						curMapData.itemSettings.put(pos, new int[MapData.itemSettingsAmount]);
						curMapData.setItemSetting(pos, curItemIndex, 0);
					}

				//if(KeyHandler.shift) defaultValue = curItemIndex;
				//if(!curMapData.itemSettings.containsKey(pos)) curMapData.setItemSetting(pos, curItemIndex, 0);
				//curItemIndex = itemIndicesMap.getOrDefault(pos, defaultValue);

				tileSettings.widgets.add(
						new TextWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								"Item Settings:",
								DEFAULT_TEXT_SIZE, CORNER
						)
				);

				tileSettings.widgets.add(
						new ButtonWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								itemTypeNames[curItemIndex],
								DEFAULT_TEXT_SIZE,
								CENTER,
								() -> {itemCycle(pos);}
						)
				);
				break;
			case 'e':

				if(curMapData.itemSettings.containsKey(pos))
					curEnemyIndex = curMapData.getEnemySetting(pos, 0);
				else
				{
					curMapData.enemySettings.put(pos, new int[MapData.enemySettingsAmount]);
					curMapData.setEnemySetting(pos, curEnemyIndex, 0);
				}

				//if(KeyHandler.shift) defaultValue = curEnemyIndex;

				//if(!curMapData.enemySettings.containsKey(pos)) curMapData.setEnemySetting(pos, curItemIndex, 0);

				tileSettings.widgets.add(
						new TextWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								"Enemy Settings:",
								DEFAULT_TEXT_SIZE, CORNER
						)
				);

				tileSettings.widgets.add(
						new ButtonWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								enemyTypeNames[curEnemyIndex],
								DEFAULT_TEXT_SIZE,
								CENTER,
								() -> {enemyCycle(pos);}
						)
				);

				break;
				
			default:
				tileSettings.widgets.add(
						new TextWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								"Game Settings:",
								DEFAULT_TEXT_SIZE, CORNER
						)
				);

				tileSettings.widgets.add(
						new ButtonWidget(
								new Vector2(scaleToScreenX(10),0),
								new Vector2(scaleToScreenX(380),scaleToScreenY(30)),
								ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
								curMapData.gameSettings[0] == 1 ? "Points for Life: On " : "Points for Life: Off",
								DEFAULT_TEXT_SIZE,
								CENTER,
								() -> {togglePointsForLife();}
						)
				);

				break;
		}

	}

	public void enemyCycle(Vector2 pos){
		curEnemyIndex++;
		curEnemyIndex %= enemyTypeNames.length;

		if(curMapData.enemySettings.containsKey(pos))
			curMapData.setEnemySetting(pos, curEnemyIndex, 0);
		else
		{
		
			curMapData.enemySettings.put(pos,new int[MapData.enemySettingsAmount]);
			curMapData.setEnemySetting(pos, curEnemyIndex, 0);

		}

		if(tileSettings.widgets.get(1) instanceof ButtonWidget)
			{ButtonWidget temp = (ButtonWidget) tileSettings.widgets.get(1); temp.setText(enemyTypeNames[curEnemyIndex]);}

	}

	public void itemCycle(Vector2 pos){
		curItemIndex++;
		curItemIndex %= itemTypeNames.length;

		if(curMapData.itemSettings.containsKey(pos))
			curMapData.setItemSetting(pos, curItemIndex, 0);
		else
		{
		
			curMapData.itemSettings.put(pos,new int[MapData.itemSettingsAmount]);
			curMapData.setItemSetting(pos, curItemIndex, 0);

		}

		if(tileSettings.widgets.get(1) instanceof ButtonWidget)
			{ButtonWidget temp = (ButtonWidget) tileSettings.widgets.get(1); temp.setText(itemTypeNames[curItemIndex]);}
	}

	public void togglePointsForLife()
	{

		curMapData.gameSettings[0] = (curMapData.gameSettings[0] + 1) % 2;

		if(tileSettings.widgets.get(1) instanceof ButtonWidget)
			{ButtonWidget temp = (ButtonWidget) tileSettings.widgets.get(1); temp.setText(curMapData.gameSettings[0] == 1 ? "Points for Life: On " : "Points for Life: Off");}
	}

	public void initLegend(){

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"Wall ... #",
						DEFAULT_TEXT_SIZE,
						wallGridColor
				)
		);

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"Floor ... SPACE",
						DEFAULT_TEXT_SIZE,
						emptyGridColor
				)
		);

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"Player ... P",
						DEFAULT_TEXT_SIZE,
						playerGridColor
				)
		);

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"Enemy ... E",
						DEFAULT_TEXT_SIZE,
						enemyGridColor
				)
		);

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"ItemBox ... I",
						DEFAULT_TEXT_SIZE,
						itemBoxGridColor
				)
		);

		legend.widgets.add(
				new LegendWidget(
						new Vector2(
								scaleToScreenX(10),
								scaleToScreenY(30)
						),
						new Vector2(
								scaleToScreenX(180),
								scaleToScreenY(24)
						),
						ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
						"ERROR ... N/A",
						DEFAULT_TEXT_SIZE,
						errorGridColor
				)
		);
	}

}
