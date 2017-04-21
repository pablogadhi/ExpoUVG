package com.uvg.expo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

public class ModelUVG extends ApplicationAdapter implements GestureDetector.GestureListener{

    //public class StartGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener{
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    private Model second;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private ModelInstance modelInstance1;
    private ModelInstance modelInstance2;
    private ModelInstance modelInstance3;
    private ModelInstance modelInstance4;
    private ModelInstance modelInstance5;
    private ModelInstance modelInstance6;
    private ModelInstance modelInstance7;
    private Environment environment;

    private GestureDetector gestureDetector;

    private Vector3 vectorCero= new Vector3(0.0f,0.0f,0.0f);
    private Vector3 vectorRotacionX= new Vector3(1.0f,0.0f,0.0f);
    private Vector3 vectorRotacionY= new Vector3(0.0f,1.0f,0.0f);

    private Vector3 vectorZoomZp = new Vector3(0.0f,0.0f,0.01f);
    private Vector3 vectorZoomZn = new Vector3(0.0f,0.0f,-0.01f);
    private Vector3 vectorMovimientoXp = new Vector3(0.01f,0.0f,0.0f);
    private Vector3 vectorMovimientoXn = new Vector3(-0.01f,0.0f,0.0f);
    private Vector3 vectorMovimientoYp = new Vector3(0.0f,0.01f,0.0f);
    private Vector3 vectorMovimientoYn = new Vector3(0.0f,-0.01f,0.0f);
    private float zoomfactor;
    private boolean dentro=true;

    public String Adonde;
    public String Estoy;
    public boolean isIrActual;
    float dir;

    ////
    private Array<ModelInstance> instancesTray;
    private boolean renMap;
    private String trayectoria;

    private boolean trayAF;
    private boolean trayFG;
    private boolean trayAK;
    private boolean trayKH;
    private boolean trayHI;
    private boolean trayIJ;
    private boolean trayFC;
    private boolean trayEB;
    private boolean trayFE;
    private boolean trayAI;

    private Model modelGroundAF;
    private ModelInstance instanceAF;
    private Model modelGroundFG;
    private ModelInstance instanceFG;
    private Model modelGroundAK;
    private ModelInstance instanceAK;
    private Model modelGroundKH;
    private ModelInstance instanceKH;
    private Model modelGroundHI;
    private ModelInstance instanceHI;
    private Model modelGroundIJ;
    private ModelInstance instanceIJ;
    private Model modelGroundFC1;
    private ModelInstance instanceFC1;
    private Model modelGroundFC2;
    private ModelInstance instanceFC2;
    private Model modelGroundCB;
    private ModelInstance instanceCB;
    private AssetManager assetManager;
    private boolean loading;
    private Model modelGroundEB1;
    private ModelInstance instanceEB1;
    private Model modelGroundEB2;
    private ModelInstance instanceEB2;
    private Model modelGroundFE;
    private ModelInstance instanceFE;
    private Model modelGroundAI1;
    private ModelInstance instanceAI1;
    private Model modelGroundAI2;
    private ModelInstance instanceAI2;

    ////

    @Override
    public void create() {

        instancesTray = new Array();

        trayectoria = "";

        trayAF = false;
        trayFG = false;
        trayAK = false;
        trayKH = false;
        trayHI = false;
        trayIJ = false;
        trayFC = false;
        trayEB = false;

        renMap = false;

        Adonde = "";
        Estoy = "";
        isIrActual = false;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));



        camera = new PerspectiveCamera(100,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //Cambiar la camara position.set a  (0f,10f,0f) para que este sobre pero no se mira bien por falta de texturas
        camera.position.set(0f,700f,0f);
        camera.lookAt(0f,0f,0f);
        camera.near = 1.0f;
        camera.far = 100000.0f;;

        modelBatch = new ModelBatch();

        /*
		UBJsonReader jsonReader = new UBJsonReader();

		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/EdificioA.g3db"));
		modelInstance1 = new ModelInstance(model);
		modelInstance1.transform.translate(0f,0f,0f);

		instances.add(modelInstance1);
        */
        assetManager = new AssetManager();
        assetManager.load("data/EdificioA.g3db",Model.class); //1
        assetManager.load("data/EdificioII.g3db",Model.class); //2
        assetManager.load("data/EdificioE.g3db",Model.class);  //3
        assetManager.load("data/EdificioB.g3db",Model.class);  //4
        assetManager.load("data/MegaEdificio.g3db",Model.class); //5
        assetManager.load("data/Parqueo.g3db",Model.class);  //6
        assetManager.load("data/Extras.g3db",Model.class); //7



        loading = true;
        /*


		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/Extras.g3db"));
		modelInstance7 = new ModelInstance(model);
		modelInstance7.transform.translate(0f,0f,0f);
		instances.add(modelInstance7);
        */
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        //Se crean todos los caminos posibles a utilizar (el camino esta indicado en un comment arriba de cada codigo)


        //A-F
        ModelBuilder modelBuilderAF = new ModelBuilder();
        modelGroundAF = modelBuilderAF.createBox(15f, 1f, 115f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceAF = new ModelInstance(modelGroundAF);
        instanceAF.transform.translate(20f, 0f , -200f);
        instanceAF.transform.rotate(0f, -45f, 0f, 45f);

        //A-I
        //AI1
        ModelBuilder modelBuilderAI1 = new ModelBuilder();
        modelGroundAI1 = modelBuilderAI1.createBox(15f, 0.1f, 125f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceAI1 = new ModelInstance(modelGroundAI1);
        instanceAI1.transform.translate(95f, 0f , -200f);
        instanceAI1.transform.rotate(0f, 45f, 0f, 45f);

        //AI2
        ModelBuilder modelBuilderAI2 = new ModelBuilder();
        modelGroundAI2 = modelBuilderAI2.createBox(15f, 0.1f, 120f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceAI2 = new ModelInstance(modelGroundAI2);
        instanceAI2.transform.translate(185f, 0f , -190f);
        instanceAI2.transform.rotate(0f, -45f, 0f, 45f);

        //F-G
        ModelBuilder modelBuilderFG = new ModelBuilder();
        modelGroundFG = modelBuilderFG.createBox(15f, 1f, 100f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceFG = new ModelInstance(modelGroundFG);
        instanceFG.transform.translate(-70f, 0f , -165f);
        instanceFG.transform.rotate(0f, 90f, 0f, 90f);

        //A-K
        ModelBuilder modelBuilderAK = new ModelBuilder();
        modelGroundAK = modelBuilderAK.createBox(15f, 1f, 115f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceAK = new ModelInstance(modelGroundAK);
        instanceAK.transform.translate(-120f, 0f , -270f);
        instanceAK.transform.rotate(0f, -45f, 0f, 45f);

        //K-H
        ModelBuilder modelBuilderKH = new ModelBuilder();
        modelGroundKH = modelBuilderKH.createBox(15f, 1f, 75f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceKH = new ModelInstance(modelGroundKH);
        instanceKH.transform.translate(-160f, 0f , -195f);
        instanceKH.transform.rotate(0f, 0f, 0f, 90f);

        //H-I
        ModelBuilder modelBuilderHI = new ModelBuilder();
        modelGroundHI = modelBuilderHI.createBox(15f, 1f, 145f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceHI = new ModelInstance(modelGroundHI);
        instanceHI.transform.translate(-240f, 0f , -165f);
        instanceHI.transform.rotate(0f, 90f, 0f, 90f);

        //I-J
        ModelBuilder modelBuilderIJ = new ModelBuilder();
        modelGroundIJ = modelBuilderIJ.createBox(15f, 1f, 200f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceIJ = new ModelInstance(modelGroundIJ);
        instanceIJ.transform.translate(-360f, 0f , -250f);
        instanceIJ.transform.rotate(0f, 35f, 0f, 35f);

        //F-E
        ModelBuilder modelBuilderEF = new ModelBuilder();
        modelGroundFE = modelBuilderEF.createBox(15f, 1f, 120f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceFE = new ModelInstance(modelGroundFE);
        instanceFE.transform.translate(150f, 0f , -35f);
        instanceFE.transform.rotate(0f, 90f, 0f, 90f);

        //F-C
        //FC1
        ModelBuilder modelBuilderFC1 = new ModelBuilder();
        modelGroundFC1 = modelBuilderFC1.createBox(15f, 0.1f, 40f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceFC1 = new ModelInstance(modelGroundFC1);
        instanceFC1.transform.translate(80f, 0f , 10f);
        instanceFC1.transform.rotate(0f, -45f, 0f, 45f);

        //FC2
        ModelBuilder modelBuilderFC2 = new ModelBuilder();
        modelGroundFC2 = modelBuilderFC2.createBox(15f, 0.1f, 115f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceFC2 = new ModelInstance(modelGroundFC2);
        instanceFC2.transform.translate(95f, 0f , 65f);
        instanceFC2.transform.rotate(0f, 45f, 0f, 45f);

        //E-B
        //EB1
        ModelBuilder modelBuilderEB1 = new ModelBuilder();
        modelGroundEB1 = modelBuilderEB1.createBox(15f, 1f, 145f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceEB1 = new ModelInstance(modelGroundEB1);
        instanceEB1.transform.translate(160f, 0f , 20f);
        instanceEB1.transform.rotate(0f, -45f, 0f, 45f);

        //EB2
        ModelBuilder modelBuilderEB2 = new ModelBuilder();
        modelGroundEB2 = modelBuilderEB2.createBox(15f, 0.1f, 160f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceEB2 = new ModelInstance(modelGroundEB2);
        instanceEB2.transform.translate(180f, 0f , 125f);
        instanceEB2.transform.rotate(0f, 45f, 0f, 45f);

    }

    public void mapear(String trayCompleta){
        trayAF = false;
        trayFG = false;
        trayAK = false;
        trayKH = false;
        trayHI = false;
        trayIJ = false;
        trayFC = false;
        trayEB = false;
        trayFE = false;
        trayAI = false;

        trayectoria = trayCompleta;

        //Trayectoria
        int len = trayectoria.length();

        for (int i = 0; i + 2 <= len; i++){
            String tray = "";
            if (i +2 == len)
                tray = trayectoria.substring(i);
            else
                tray = trayectoria.substring(i,i + 2);

            if (tray.equals("AF") || tray.equals("FA"))
                trayAF = true;
            else if (tray.equals("FG") || tray.equals("GF"))
                trayFG = true;
            else if (tray.equals("II") || tray.equals("II"))
                trayAI = true;
            else if (tray.equals("AK") || tray.equals("KA"))
                trayAK = true;
            else if (tray.equals("KH") || tray.equals("HK"))
                trayKH = true;
            else if (tray.equals("KG") || tray.equals("GK"))
                trayKH = true;
            else if (tray.equals("HI") || tray.equals("IH"))
                trayHI = true;
            else if (tray.equals("IJ") || tray.equals("JI"))
                trayIJ = true;
            else if (tray.equals("FC") || tray.equals("CF"))
                trayFC = true;
            else if (tray.equals("EB") || tray.equals("BE")
                    ||tray.equals("CE") || tray.equals("EC"))
                trayEB = true;
            else if (tray.equals("FE") || tray.equals("EF"))
                trayFE = true;
            else if (tray.equals("KJ") || tray.equals("JK")){
                trayKH = true;
                trayHI = true;
                trayIJ = true;
            }
        }

        renMap = true;
        reset();
        //render();
    }

    public void mapearTray(){

        camera.update();

        if (trayAF){
            modelBatch.render(instanceAF);
            instancesTray.add(instanceAF);
        }
        if (trayAI){
            modelBatch.render(instanceAI1);
            modelBatch.render(instanceAI2);
            instancesTray.add(instanceAI1);
            instancesTray.add(instanceAI2);
        }
        if (trayFG){
            modelBatch.render(instanceFG);
            instancesTray.add(instanceFG);
        }
        if (trayAK){
            modelBatch.render(instanceAK);
            instancesTray.add(instanceAK);
        }
        if (trayKH){
            modelBatch.render(instanceKH);
            instancesTray.add(instanceKH);
        }
        if (trayHI){
            modelBatch.render(instanceHI);
            instancesTray.add(instanceHI);
        }
        if (trayIJ){
            modelBatch.render(instanceIJ);
            instancesTray.add(instanceIJ);
        }
        if (trayFE){
            modelBatch.render(instanceFE);
            instancesTray.add(instanceFE);
        }
        if (trayFC){
            modelBatch.render(instanceFC1);
            modelBatch.render(instanceFC2);
            instancesTray.add(instanceFC1);
            instancesTray.add(instanceFC2);
        }
        if (trayEB){
            modelBatch.render(instanceEB1);
            modelBatch.render(instanceEB2);
            instancesTray.add(instanceEB1);
            instancesTray.add(instanceEB2);
        }

        modelBatch.end();
    }


    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();
        instances.clear();
        assetManager.dispose();
    }


    public String getEstoy(){
        return Estoy;
    }

    public float getProgress(){
        return assetManager.getProgress();
    }

    public boolean getLoading(){
        return loading;
    }

    public void doneLoading(){
        model = assetManager.get("data/EdificioA.g3db");
        modelInstance1 = new ModelInstance(model);
        instances.add(modelInstance1);

        model = assetManager.get("data/EdificioII.g3db");
        modelInstance2 = new ModelInstance(model);
        instances.add(modelInstance2);

        model = assetManager.get("data/EdificioE.g3db");
        modelInstance3 = new ModelInstance(model);
        instances.add(modelInstance3);

        model = assetManager.get("data/EdificioB.g3db");
        modelInstance4 = new ModelInstance(model);
        instances.add(modelInstance4);

        model = assetManager.get("data/MegaEdificio.g3db");
        modelInstance5 = new ModelInstance(model);
        instances.add(modelInstance5);

        model = assetManager.get("data/Parqueo.g3db");
        modelInstance6 = new ModelInstance(model);
        instances.add(modelInstance6);

        model = assetManager.get("data/Extras.g3db");
        modelInstance7 = new ModelInstance(model);
        instances.add(modelInstance7);

        loading = false;
    }

    @Override
    public void render() {
        if (loading && assetManager.update()){
            doneLoading();
        }
        camera.update();


        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);



        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
/*
		trayAF = true;
		trayFG = true;
		trayAK = true;
		trayKH = true;
		trayHI = true;
		trayIJ = true;
		trayFC = true;
		trayEB = true;
		trayFE = true;
		trayAI = true;
		mapearTray();
*/

        if (renMap){
            mapearTray();
        }


        //irActual("E");

        modelBatch.end();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!isIrActual) {

            trayAF = false;
            trayFG = false;
            trayAK = false;
            trayKH = false;
            trayHI = false;
            trayIJ = false;
            trayFC = false;
            trayEB = false;
            trayFE = false;
            trayAI = false;

            modelInstance1.transform.translate(deltaX, 0f, deltaY);
            modelInstance2.transform.translate(deltaX, 0f, deltaY);
            modelInstance3.transform.translate(deltaX, 0f, deltaY);
            modelInstance4.transform.translate(deltaX, 0f, deltaY);
            modelInstance5.transform.translate(deltaX, 0f, deltaY);
            modelInstance6.transform.translate(deltaX, 0f, deltaY);
            modelInstance7.transform.translate(deltaX, 0f, deltaY);
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        Vector3 v = modelInstance1.transform.getTranslation(new Vector3());
        if (v.y > 350f && v.y < 700f){
            modelInstance1.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance2.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance3.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance4.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance5.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance6.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance7.transform.translate(0f, (initialDistance-distance)/5f,0f);
        }
        Vector3 pos = modelInstance1.transform.getTranslation(new Vector3());
        if (pos.y>350f && pos.y<1050f) {
            modelInstance1.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance2.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance3.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance4.transform.translate(0f, (initialDistance-distance)/5f,0f);
        }
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		/*
<<<<<<< HEAD
=======

>>>>>>> b490a81559304ed40edb3b13b092a762cca86386
		if (!isIrActual){
			float deltaX = pointer1.x-pointer2.x;
			float deltaY =pointer1.y-pointer2.y;
			float angulo = (float)Math.atan2((double)deltaY,(double)deltaX)* MathUtils.radiansToDegrees;
			angulo+=90f;
			modelInstance1.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance2.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance3.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance4.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance5.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance6.transform.rotate(Vector3.Y, -angulo/90);
			modelInstance7.transform.rotate(Vector3.Y, -angulo/90);

		}
		 //if (pointer1.x-initialPointer1.x==pointer2.x-initialPointer2.x){
		 modelInstance1.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance2.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance3.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance4.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 //}*/

        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void setAdonde(String donde){
        Adonde = donde;
    }

    public void setEstoy(String estoy){
        Estoy = estoy;
    }

    public void irActual(String posicion){
        dir = 0f;
        if (posicion.equals("A") || posicion.equals("a")){
            reset();
            isIrActual=true;
            move(150f, 625f,250f);
            camera.lookAt(0f, 625f,-250f);
            camera.rotate(new Vector3(0f,1f,0f),-80f);
            dir = -80f;
        } else if (posicion.equals("B")||posicion.equals("b")){
            reset();
            isIrActual=true;
            move(-115f,625f,-95f);
            camera.lookAt(0f, 625f,-95f);
            camera.rotate(new Vector3(0f,1f,0f),-85f);
            dir = -85f;
        } else if (posicion.equals("C")||posicion.equals("c")){
            reset();
            isIrActual=true;
            move(0f,0f,0f);
        } else if (posicion.equals("D")||posicion.equals("d")){
            reset();
            isIrActual=true;
            move(0f,0f,0f);
        } else if (posicion.equals("E")||posicion.equals("e")){
            reset();
            isIrActual=true;
            move(-310f,625f,-35f);   //Listo
            camera.lookAt(0f, 700f,-25f);
            camera.rotate(new Vector3(0f,1f,0f),30f);
            dir = 30f;
        } else if (posicion.equals("F")||posicion.equals("f")){
            reset();
            isIrActual=true;
            move(-75f, 625f, 170f);
            camera.lookAt(0f, 700f,-50f);
            camera.rotate(new Vector3(0f,1f,0f),-230f);
            dir = -230f;
        } else if (posicion.equals("G")||posicion.equals("g")){
            reset();
            isIrActual=true;
            move(75f,625f,200f);
            camera.lookAt(0f, 700f,-50f);
            camera.rotate(new Vector3(0f,1f,0f),-220f);
            dir = -220f;


        } else if (posicion.equals("H")||posicion.equals("h")){
            reset();
            isIrActual=true;
            move(170f,625f,220f);
            camera.lookAt(0f, 700f,-75f);
            camera.rotate(new Vector3(0f,1f,0f),-230f);
            dir = -230f;


        } else if (posicion.equals("I")||posicion.equals("i")){
            reset();
            isIrActual=true;
            move(275f,625f,220f);
            camera.lookAt(0f, 700f,-80f);
            camera.rotate(new Vector3(0f,1f,0f),-250f);
            dir = -250f;
        } else if (posicion.equals("J")||posicion.equals("j")){
            reset();
            isIrActual=true;
            move(350f,625f,350f);
            camera.lookAt(0f, 700f,-200f);
            camera.rotate(new Vector3(0f,1f,0f),-200f);
            dir = -200f;
        } else if (posicion.equals("K")||posicion.equals("k")){
            reset();
            isIrActual=true;
            move(150f,625f,130f);
            camera.lookAt(0f, 700f,-240f);
            camera.rotate(new Vector3(0f,1f,0f),40f);
            dir = 30f;
        } else if (posicion.equals("II-1")||posicion.equals("ii-1")){
            reset();
            isIrActual=true;
            move(-300f,650f,120f);
            camera.lookAt(0f, 700f, -120f);
            camera.rotate(new Vector3(0f,1f,0f),0f);
            dir = 0f;
        } else if (posicion.equals("II-2")||posicion.equals("ii-2")){
            reset();
            isIrActual=true;
            move(-170f, 650f, 120f);
            camera.lookAt(0f, 700f, -250f);
            camera.rotate(new Vector3(0f,1f,0f),0f);
            dir = -0f;
        } else {
            isIrActual = false;
        }
    }

    public void move(float x,float y,float z){
        Vector3 v = position(x,y,z);
        moveALL(v.x,v.y,v.z);
        /*
		modelInstance1.transform.translate(v);
		modelInstance2.transform.translate(v);
		modelInstance3.transform.translate(v);
		modelInstance4.transform.translate(v);
		modelInstance5.transform.translate(v);
		modelInstance6.transform.translate(v);
		modelInstance7.transform.translate(v);
		*/
    }

    public Vector3 position(float x,float y, float z){
        Vector3 v = modelInstance1.transform.getTranslation(new Vector3());
        x = x - v.x;
        y = y - v.y;
        z = z - v.z;
        return new Vector3(x,y,z);
    }

    public String mostrarPosicion(){
        StringBuilder builder = new StringBuilder();
        builder.append(modelInstance1.transform.toString());
		/*
		builder.append(";");
		builder.append(modelInstance2.transform.getValues());
		builder.append(";");
		builder.append(modelInstance3.transform.getValues());
		builder.append(";");
		builder.append(modelInstance4.transform.getValues());
		builder.append(";");*/
        return  builder.toString();
    }

    public void setViewFalse(){
        isIrActual = false;
    }

    public void setViewTrue(){
        isIrActual = true;
    }

    public boolean getisIrActual(){
        return  isIrActual;
    }

    public String getAdonde(){
        return Adonde;
    }

    public void moveALL(float x, float y, float z){
        Vector3 v = position(x,y,z);
        modelInstance1.transform.translate(v);
        modelInstance2.transform.translate(v);
        modelInstance3.transform.translate(v);
        modelInstance4.transform.translate(v);
        modelInstance5.transform.translate(v);
        modelInstance6.transform.translate(v);
        modelInstance7.transform.translate(v);

    }

    public void setIsActual(boolean flag){
        isIrActual = false;
    }

    public void reset(){
        isIrActual = false;
        Quaternion q = modelInstance1.transform.getRotation(new Quaternion());
        float angulo = q.getAngleAround(Vector3.Y);
        rotate(Vector3.Y, -angulo);
        moveALL(0f,0f,0f);
        camera.lookAt(0,0,0);
        camera.position.set(0f,700f,0f);
        camera.rotate(Vector3.Y, -dir);
        dir = 0;

    }

    public void rotate(Vector3 v, float angulo){
        modelInstance1.transform.rotate(v,angulo);
        modelInstance2.transform.rotate(v,angulo);
        modelInstance3.transform.rotate(v,angulo);
        modelInstance4.transform.rotate(v,angulo);
        modelInstance5.transform.rotate(v,angulo);
        modelInstance6.transform.rotate(v,angulo);
        modelInstance7.transform.rotate(v,angulo);
    }

    public void allFalse(){
        trayAF = false;
        trayFG = false;
        trayAK = false;
        trayKH = false;
        trayHI = false;
        trayIJ = false;
        trayFC = false;
        trayEB = false;
        trayFE = false;
        trayAI = false;
    }

}