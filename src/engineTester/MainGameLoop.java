package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjMode("dragon", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));

        Light light = new Light(new Vector3f(0, -5, -20), new Vector3f(0.9529411764705882f, 0.7803921568627451f, 0.8549019607843137f));

        Camera camera  = new Camera();

        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i <= 100; i++) {
            float x = (float) Math.random() * 100 - 50;
            float y = (float) Math.random() * 100 - 50;
            float z = (float) Math.random() * 100 - 50;

            Entity entity = new Entity(staticModel, new Vector3f(x, y, z), (float) Math.random() * 180f, (float) Math.random() * 180f, 0, 1);
            entities.add(entity);
        }

        MasterRenderer renderer = new MasterRenderer();

        while(!Display.isCloseRequested()){
            camera.move();

            for(Entity entity : entities) {
                entity.increaseRotation(0, 1, 0);
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}