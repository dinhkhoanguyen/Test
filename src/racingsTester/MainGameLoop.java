 package racingsTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Audio.AudioMaster;
import Audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Person;
import guis.GuiRenderer;
import guis.GuiTexture;
import model.RawModel;
import model.TexturedModel;
import renderRacings.DisplayManager;
import renderRacings.Loader;
import renderRacings.MasterRenderer;
import renderRacings.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
//import water.WaterFrameBuffers;
//import water.WaterRenderer;
//import water.WaterShader;
//import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) throws IOException {
		
		DisplayManager.createDisplay();
			
		Loader loader = new Loader();
		
		//**************TERRAIN TEXTURE STUFF**********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy3"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));//dirt
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		//********MAP********
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		//*******
		RawModel modelPine = OBJLoader.loadObjModel("pine", loader);
		RawModel modelCherry = OBJLoader.loadObjModel("cherry", loader);
		RawModel modelGrass = OBJLoader.loadObjModel("grassModel", loader);
		RawModel modelFern = OBJLoader.loadObjModel("fern", loader);
		RawModel modelFlower = OBJLoader.loadObjModel("fern", loader);
		RawModel modelBox = OBJLoader.loadObjModel("box", loader);
		RawModel modelLantern = OBJLoader.loadObjModel("lantern", loader);
		
		TexturedModel pine =  new TexturedModel(modelPine, new ModelTexture(loader.loadTexture("pine")));
		TexturedModel cherry =  new TexturedModel(modelCherry, new ModelTexture(loader.loadTexture("cherry")));
		
		TexturedModel  grass = new TexturedModel(modelGrass, new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		TexturedModel  fern = new TexturedModel(modelFern, new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		
		TexturedModel flower =  new TexturedModel(modelFlower, new ModelTexture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		TexturedModel box =  new TexturedModel(modelBox, new ModelTexture(loader.loadTexture("box")));
		box.getTexture().setHasTransparency(true);
		box.getTexture().setUseFakeLighting(true);
		
		TexturedModel lantern =  new TexturedModel(modelLantern, new ModelTexture(loader.loadTexture("lantern")));
		lantern.getTexture().setHasTransparency(true);
		lantern.getTexture().setUseFakeLighting(true);
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		
		List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random(676452);
        for(int i=0;i<400;i++){
			if( i % 3 == 0){
        		float x = random.nextFloat()* 800;
        		float z = random.nextFloat() * -800;
        		float y = terrain.getHeightOfTerrain(x, z);
        		
        		entities.add(new Entity(box, new Vector3f(x, y, z),0,random.nextFloat() * 360,0, 5f));
        		
        		x = random.nextFloat()* 800;
        		z = random.nextFloat() * -800;
        		y = terrain.getHeightOfTerrain(x, z);
        		
        		entities.add(new Entity(lantern, new Vector3f(x, y, z),0,random.nextFloat() * 360,0, 0.5f));
        	}
        	if(i % 1 == 0){
        		float x = random.nextFloat()* 800;
        		float z = random.nextFloat() * -800;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(cherry, new Vector3f(x, y, z),0,random.nextFloat() * 360,0,random.nextFloat() * 0.1f +  1.5f));
        		
        		x = random.nextFloat() * 800;
        		z = random.nextFloat() * -800;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(pine, new Vector3f(x, y, z),0,random.nextFloat() * 360,0, random.nextFloat() * 0.1f + 1.2f));
        		
        		x = random.nextFloat() * 800;
        		z = random.nextFloat() * -800;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(grass, new Vector3f(x, y, z),0,random.nextFloat() * 360,0,1.5f));	
        		
        		x = random.nextFloat() * 800;
        		z = random.nextFloat() * -800;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(flower, new Vector3f(x, y, z),0,random.nextFloat() * 360,0,0.6f));	
        		
        		x = random.nextFloat() * 800;
        		z = random.nextFloat() * -800;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(fern, new Vector3f(x, y, z),0,random.nextFloat() * 360,0,random.nextFloat() * 0.3f +  0.6f));	
        	}
        }
        
		Light light = new Light(new Vector3f(0,10000,-7000), new Vector3f(1,1,1));
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		RawModel modelPerson = OBJLoader.loadObjModel("person", loader);
		TexturedModel Person = new TexturedModel(modelPerson, new ModelTexture(loader.loadTexture("playerTexture")));
		
		Person person = new Person(Person, new Vector3f(100, 5, -150), 0, 180, 0, 0.6f);
		Camera camera = new Camera(person);
		
		AudioMaster.init();
		AudioMaster.setLisnerData();

		int buffer = AudioMaster.loadSound("Audio/OneRepublic.wav");
		Source source = new Source();
		source.play(buffer);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
	    GuiTexture gui = new GuiTexture(loader.loadTexture("khoa1"),new Vector2f(-0.8f,0.9f), new Vector2f(0.2f,0.2f));
	    guis.add(gui);
	    GuiRenderer guiRenderer = new GuiRenderer(loader);
		/*
	    WaterFrameBuffers buffers = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
        List<WaterTile> waters = new ArrayList<WaterTile>();
        waters.add(new WaterTile(75, -75, -10));
        
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                waters.add(new WaterTile(i * 160, -j * 160, -1));
            }
        }
        */
	    //List<Terrain> terrains = new ArrayList<Terrain>();
		
	    while(!Display.isCloseRequested()){	
			person.move(terrain);
			camera.move();
			
			renderer.processEntity(person);;
            renderer.processTerrain(terrain);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
			renderer.render(light, camera);
			
			//renderer.renderScene(entities, terrains, light, camera);
			//waterRenderer.render(waters, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();	
		}
		source.delete();
		AudioMaster.cleanUp();
		//waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
