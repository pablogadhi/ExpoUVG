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
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private ModelInstance modelInstance1;
    private ModelInstance modelInstance2;
    private ModelInstance modelInstance3;
    private ModelInstance modelInstance4;
    private ModelInstance modelInstance5;
    private ModelInstance modelInstance6;
    private ModelInstance modelInstance7;
    private ModelInstance modelInstance8;
    private ModelInstance modelInstance9;
    private ModelInstance modelInstanceF;
    private  Array<ModelInstance> skyboxArray = new Array<ModelInstance>();
    private  Array<ModelInstance> flagArray = new Array<ModelInstance>();
    private Environment environment;

    private Model skybox;

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
    private String EstoyVer;
    public boolean isIrActual;
    float dir;


    private boolean renMap;
    private String trayectoria;
    private Vector3 posActualMap;

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
    private Model modelGroundPOST;
    private ModelInstance instancePOST;

    private AssetManager assetManager;
    private boolean loading;
    private boolean moveback;
    private boolean moveback2;
    private String lastTray;
    private Vector3 flagPosition;

    ////

    @Override
    public void create() {

        EstoyVer = Estoy;

        flagPosition = new Vector3(0f,0f,-55f);

        moveback = true;
        moveback2 = true;

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
        camera.position.set(0f,300f,0f);
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
        assetManager.load("data/EdificioC.g3db",Model.class);//8
        assetManager.load("data/Skybox.g3db",Model.class);//9
        assetManager.load("data/goalflag.g3db",Model.class);//F


        loading = true;
        /*


		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/Extras.g3db"));
		modelInstance7 = new ModelInstance(model);
		modelInstance7.transform.translate(0f,0f,0f);
		instances.add(modelInstance7);
        */


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
        modelGroundFE = modelBuilderEF.createBox(15f, 1f, 115f,
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
        modelGroundEB2 = modelBuilderEB2.createBox(15f, 0.1f, 165f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceEB2 = new ModelInstance(modelGroundEB2);
        instanceEB2.transform.translate(180f, 0f , 125f);
        instanceEB2.transform.rotate(0f, 45f, 0f, 45f);

        posActualMap = new Vector3(0f,0f,0f);

        //POST
        ModelBuilder modelBuilderPOST = new ModelBuilder();
        modelGroundPOST = modelBuilderPOST.createBox(1f, 0.1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instancePOST = new ModelInstance(modelGroundPOST);
        instancePOST.transform.translate(0f, -1f , 0f);


    }

    /**
     * Se encarga de establecer a los booleans de las trayectorias como verdaderos segun las
     * el String ingresado.
     *
     * @param trayCompleta String con la ruta a a trazar, explicada por medio de cada uno de los
     *                     edificios por los que se pasa.
     */
    public void mapear(String trayCompleta){

        allFalse();

        trayectoria = trayCompleta;

        //Trayectoria
        int len = trayectoria.length();

        lastTray = "  ";

        if (len >= 2){

            for (int i = 0; i + 2 <= len; i++) {
                String tray = "  ";

                if (i + 2 == len)
                    tray = trayectoria.substring(i);
                else
                    tray = trayectoria.substring(i, i + 2);

                if (tray.equals("AF") || tray.equals("FA")) {
                    trayAF = true;
                    lastTray = tray;
                } else if (tray.equals("FG") || tray.equals("GF")) {
                    trayFG = true;
                    lastTray = tray;
                } else if (tray.equals("II") || tray.equals("II")) {
                    trayAI = true;
                    lastTray = tray;
                } else if (tray.equals("AK") || tray.equals("KA")) {
                    trayAK = true;
                    lastTray = tray;
                } else if (tray.equals("KH") || tray.equals("HK")) {
                    trayKH = true;
                    lastTray = tray;
                } else if (tray.equals("KG") || tray.equals("GK")) {
                    trayKH = true;
                    lastTray = tray;
                } else if (tray.equals("HI") || tray.equals("IH")) {
                    trayHI = true;
                    lastTray = tray;
                } else if (tray.equals("IJ") || tray.equals("JI")) {
                    trayIJ = true;
                    if (lastTray.equals("KJ"))
                        trayIJ = false;
                    lastTray = tray;
                } else if (tray.equals("FC") || tray.equals("CF")) {
                    trayFC = true;
                    lastTray = tray;
                } else if (tray.equals("EB") || tray.equals("BE")
                        || tray.equals("CE") || tray.equals("EC")) {
                    trayEB = true;
                    lastTray = tray;
                } else if (tray.equals("FE") || tray.equals("EF")) {
                    trayFE = true;
                    lastTray = tray;
                } else if (tray.equals("KJ") || tray.equals("JK")) {
                    trayKH = true;
                    trayHI = true;
                    trayIJ = true;
                    lastTray = "KJ";
                }
            }
        } else {
            lastTray = trayCompleta;
        }

        renMap = true;
        locateWayInitPosition();
        reset();

        modelInstanceF.transform.translate(-flagPosition.x,-flagPosition.y,-flagPosition.z);
        String s = lastTray;
        if (s.equals("AF")) {
            flagPosition.set(-40.7f, 0, -159.3f);
        } else if (s.equals("FA")) {
            flagPosition.set(60.7f, 0f, -240.7f);

        } else if (s.equals("FG")) {
            flagPosition.set(-70f - 50, 0, -165f);

        } else if (s.equals("GF")) {
            flagPosition.set(-70f + 50, 0, -165f);

        } else if (s.equals("II")) {
            flagPosition.set(185f + 42.4f, 0f, -190f - 42.4f);

        } else if (s.equals("AK")) {
            flagPosition.set(-120f - 40.7f, 0, -270f + 40.7f);

        } else if (s.equals("KA")) {
            flagPosition.set(-120f + 40.7f, 0, -270f - 40.7f);

        } else if (s.equals("KH")) {
            flagPosition.set(-160f , 0f, -195f + 27.5f);

        } else if (s.equals("HK")) {
            flagPosition.set(-160f, 0f, -195f - 27.5f);

        } else if (s.equals("KG")) {
            flagPosition.set(-160f , 0f, -195f + 27.5f);

        } else if (s.equals("GK")) {
            flagPosition.set(-160f, 0f, -195f - 27.5f);

        } else if (s.equals("HI")) {
            flagPosition.set(-305f, 0f, -165f);

        } else if (s.equals("IH")) {
            flagPosition.set(-160f , 0f, -165f);

        } else if (s.equals("IJ")) {
            flagPosition.set(-360f - 57.4f, 0f, -250f - 82f);

        } else if (s.equals("JI")) {
            flagPosition.set(-305f, 0f, -165f);

        } else if (s.equals("FC")) {
            flagPosition.set(95f + 40.7f, 0f, 65f + 40.7f);

        } else if (s.equals("CF")) {
            flagPosition.set(80f + 14.1f, 0f, 10f - 14.1f);

        } else if (s.equals("EB")) {
            flagPosition.set(180f + 60.1f, 0f, 125f +60.1f);

        } else if (s.equals("BE")) {
            flagPosition.set(150f + 60f, 0f, -35f);

        } else if (s.equals("CE")) {
            flagPosition.set(160f, 0f, 20f);

        } else if (s.equals("EC")) {
            flagPosition.set(180f, 0f, 125f);

        } else if (s.equals("FE")) {
            flagPosition.set(150f + 60f, 0f, -35f);

        } else if (s.equals("EF")) {
            flagPosition.set(150f - 60f, 0f, -35f);

        } else if (s.equals("KJ")) {
            flagPosition.set(-360f - 57.4f, 0f, -250f - 82f);

        } else if (s.equals("JK")) {
            flagPosition.set(-160f, 0f, -195f - 27.5f);
        } else {
            flagPosition.set(0f, 0f, -55f);
        }
        modelInstanceF.transform.translate(flagPosition);

    }

    /**
     * Dependiendo de los booleans de las trayectorias verdaderos de los caminos se encarga de
     * que se haga el render de estos caminos.
     */
    public void mapearTray(){

        camera.update();

        if (trayAF){
            modelBatch.render(instanceAF);
        }
        if (trayAI){
            modelBatch.render(instanceAI1);
            modelBatch.render(instanceAI2);
        }
        if (trayFG){
            modelBatch.render(instanceFG);
        }
        if (trayAK){
            modelBatch.render(instanceAK);
        }
        if (trayKH){
            modelBatch.render(instanceKH);
        }
        if (trayHI){
            modelBatch.render(instanceHI);
        }
        if (trayIJ){
            modelBatch.render(instanceIJ);
        }
        if (trayFE){
            modelBatch.render(instanceFE);
        }
        if (trayFC){
            modelBatch.render(instanceFC1);
            modelBatch.render(instanceFC2);
        }
        if (trayEB){
            modelBatch.render(instanceEB1);
            modelBatch.render(instanceEB2);
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

        model = assetManager.get("data/EdificioC.g3db");
        modelInstance8 = new ModelInstance(model);
        instances.add(modelInstance8);

        skybox = assetManager.get("data/Skybox.g3db");
        modelInstance9 = new ModelInstance(skybox);
        skyboxArray.add(modelInstance9);

        model = assetManager.get("data/goalflag.g3db");
        modelInstanceF = new ModelInstance(model);
        flagArray.add(modelInstanceF);
        modelInstanceF.transform.translate(flagPosition);

        loading = false;

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
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
        modelBatch.render(skyboxArray);

        modelBatch.render(flagArray);

        modelBatch.render(instancePOST);

        if (!Estoy.equals(EstoyVer)){
            allFalse();
            EstoyVer = Estoy;
        }

        if (renMap){
            mapearTray();
        }

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

            float deltaXMap = deltaX;
            float deltaYMap = deltaY;

            if (instancePOST.transform.getTranslation(vectorCero).x < -500f || instancePOST.transform.getTranslation(vectorCero).x > 500f ) {
                deltaXMap = 0;
                if (instancePOST.transform.getTranslation(vectorCero).x < -500f && moveback)
                    deltaXMap = -instancePOST.transform.getTranslation(vectorCero).x - 499.01f;
                else if (moveback)
                    deltaXMap = -instancePOST.transform.getTranslation(vectorCero).x + 499.01f;
            }
            if (instancePOST.transform.getTranslation(vectorCero).x < -500f && instancePOST.transform.getTranslation(vectorCero).x < 0f && deltaX > 0){
                deltaXMap = deltaX;
                moveback = false;
            }
            if (instancePOST.transform.getTranslation(vectorCero).x > 500f && instancePOST.transform.getTranslation(vectorCero).x > 0f && deltaX  < 0){
                deltaXMap = deltaX;
                moveback = false;
            }

            if (instancePOST.transform.getTranslation(vectorCero).z < -400f || instancePOST.transform.getTranslation(vectorCero).z > 400f ) {
                deltaYMap = 0;
                if (instancePOST.transform.getTranslation(vectorCero).z < -400f && moveback2)
                    deltaYMap = -instancePOST.transform.getTranslation(vectorCero).z - 399.01f;
                else if (moveback2)
                    deltaYMap = -instancePOST.transform.getTranslation(vectorCero).z + 399.01f;
            }
            if (instancePOST.transform.getTranslation(vectorCero).z < -400f && instancePOST.transform.getTranslation(vectorCero).z < 0f && deltaX > 0){
                deltaYMap = deltaY;
                moveback2 = false;
            }
            if (instancePOST.transform.getTranslation(vectorCero).z > 400f && instancePOST.transform.getTranslation(vectorCero).z > 0f && deltaX  < 0){
                deltaYMap = deltaY;
                moveback2 = false;
            }

            reLocateWay(deltaXMap,deltaYMap);


            if(modelInstance1.transform.getTranslation(vectorCero).x > -500f && modelInstance1.transform.getTranslation(vectorCero).x < 500f
                    && modelInstance1.transform.getTranslation(vectorCero).z > -400f && modelInstance1.transform.getTranslation(vectorCero).z < 400f){
                modelInstance1.transform.translate(deltaX, 0f, deltaY);
                modelInstance2.transform.translate(deltaX, 0f, deltaY);
                modelInstance3.transform.translate(deltaX, 0f, deltaY);
                modelInstance4.transform.translate(deltaX, 0f, deltaY);
                modelInstance5.transform.translate(deltaX, 0f, deltaY);
                modelInstance6.transform.translate(deltaX, 0f, deltaY);
                modelInstance7.transform.translate(deltaX, 0f, deltaY);
                modelInstance8.transform.translate(deltaX, 0f, deltaY);
                modelInstance9.transform.translate(deltaX, 0f, deltaY);
                //reLocateWay(deltaX,deltaY);

            }

            Vector3 posActual = modelInstance1.transform.getTranslation(vectorCero);

            if(modelInstance1.transform.getTranslation(vectorCero).x <= -500f){
                float dif = 500f + posActual.x -5f;
                moveALL(posActual.x - dif,posActual.y,posActual.z);
            }

            if(modelInstance1.transform.getTranslation(vectorCero).x >= 500f){
                float dif = -500f + posActual.x +5f;
                moveALL(posActual.x - dif,posActual.y,posActual.z);
            }
            if(modelInstance1.transform.getTranslation(vectorCero).z <= -400f){
                float dif = 400f + posActual.z -5f;
                moveALL(posActual.x,posActual.y,posActual.z - dif);
            }
            if(modelInstance1.transform.getTranslation(vectorCero).z >= 400f){
                float dif = -400f + posActual.z +5f;
                moveALL(posActual.x,posActual.y,posActual.z - dif);
            }


        }
        return true;
    }


    /**
     * Vuelve a las instancias de los caminos a sus posiciones originales.
     */
    public void locateWayInitPosition(){
        //A-F
        instanceAF = new ModelInstance(modelGroundAF);
        instanceAF.transform.translate(20f, 0f , -200f);
        instanceAF.transform.rotate(0f, -45f, 0f, 45f);

        //A-I
        //AI1
        instanceAI1 = new ModelInstance(modelGroundAI1);
        instanceAI1.transform.translate(95f, 0f , -200f);
        instanceAI1.transform.rotate(0f, 45f, 0f, 45f);

        //AI2
        instanceAI2 = new ModelInstance(modelGroundAI2);
        instanceAI2.transform.translate(185f, 0f , -190f);
        instanceAI2.transform.rotate(0f, -45f, 0f, 45f);

        //F-G
        instanceFG = new ModelInstance(modelGroundFG);
        instanceFG.transform.translate(-70f, 0f , -165f);
        instanceFG.transform.rotate(0f, 90f, 0f, 90f);

        //A-K
        instanceAK = new ModelInstance(modelGroundAK);
        instanceAK.transform.translate(-120f, 0f , -270f);
        instanceAK.transform.rotate(0f, -45f, 0f, 45f);

        //K-H
        instanceKH = new ModelInstance(modelGroundKH);
        instanceKH.transform.translate(-160f, 0f , -195f);
        instanceKH.transform.rotate(0f, 0f, 0f, 90f);

        //H-I
        instanceHI = new ModelInstance(modelGroundHI);
        instanceHI.transform.translate(-240f, 0f , -165f);
        instanceHI.transform.rotate(0f, 90f, 0f, 90f);

        //I-J
        instanceIJ = new ModelInstance(modelGroundIJ);
        instanceIJ.transform.translate(-360f, 0f , -250f);
        instanceIJ.transform.rotate(0f, 35f, 0f, 35f);

        //F-E
        instanceFE = new ModelInstance(modelGroundFE);
        instanceFE.transform.translate(150f, 0f , -35f);
        instanceFE.transform.rotate(0f, 90f, 0f, 90f);

        //F-C
        //FC1
        instanceFC1 = new ModelInstance(modelGroundFC1);
        instanceFC1.transform.translate(80f, 0f , 10f);
        instanceFC1.transform.rotate(0f, -45f, 0f, 45f);

        //FC2
        instanceFC2 = new ModelInstance(modelGroundFC2);
        instanceFC2.transform.translate(95f, 0f , 65f);
        instanceFC2.transform.rotate(0f, 45f, 0f, 45f);

        //E-B
        //EB1
        instanceEB1 = new ModelInstance(modelGroundEB1);
        instanceEB1.transform.translate(160f, 0f , 20f);
        instanceEB1.transform.rotate(0f, -45f, 0f, 45f);

        //EB2
        instanceEB2 = new ModelInstance(modelGroundEB2);
        instanceEB2.transform.translate(180f, 0f , 125f);
        instanceEB2.transform.rotate(0f, 45f, 0f, 45f);

        //POST
        instancePOST = new ModelInstance(modelGroundPOST);
        instanceEB2.transform.translate(0f, -1f , 0f);
    }

    /**
     * Cambia las posiciones de las instancias segun los deltas(x,y) de posicion ingresados.
     *
     * @param deltaX flaot con el cambio en el eje x en el que se esta moviendo el modelo completo.
     * @param deltaY flaot con el cambio en el eje x en el que se esta moviendo el modelo completo.
     */
    public void reLocateWay(float deltaX, float deltaY){

        //A-F
        instanceAF.transform.rotate(0f, 45f, 0f, 45f);
        instanceAF.transform.translate(deltaX, 0f, deltaY);
        instanceAF.transform.rotate(0f, -45f, 0f, 45f);

        //A-I
        //AI1
        instanceAI1.transform.rotate(0f, -45f, 0f, 45f);
        instanceAI1.transform.translate(deltaX, 0f, deltaY);
        instanceAI1.transform.rotate(0f, 45f, 0f, 45f);

        //AI2
        instanceAI2.transform.rotate(0f, 45f, 0f, 45f);
        instanceAI2.transform.translate(deltaX, 0f, deltaY);
        instanceAI2.transform.rotate(0f, -45f, 0f, 45f);

        //F-G
        instanceFG.transform.rotate(0f, -90f, 0f, 90f);
        instanceFG.transform.translate(deltaX, 0f, deltaY);
        instanceFG.transform.rotate(0f, 90f, 0f, 90f);

        //A-K
        instanceAK.transform.rotate(0f, 45f, 0f, 45f);
        instanceAK.transform.translate(deltaX, 0f, deltaY);
        instanceAK.transform.rotate(0f, -45f, 0f, 45f);

        //K-H
        instanceKH.transform.translate(deltaX, 0f, deltaY);

        //H-I
        instanceHI.transform.rotate(0f, -90f, 0f, 90f);
        instanceHI.transform.translate(deltaX, 0f, deltaY);
        instanceHI.transform.rotate(0f, 90f, 0f, 90f);

        //I-J
        instanceIJ.transform.rotate(0f, -35f, 0f, 35f);
        instanceIJ.transform.translate(deltaX, 0f, deltaY);
        instanceIJ.transform.rotate(0f, 35f, 0f, 35f);

        //F-E
        instanceFE.transform.rotate(0f, -90f, 0f, 90f);
        instanceFE.transform.translate(deltaX, 0f, deltaY);
        instanceFE.transform.rotate(0f, 90f, 0f, 90f);

        //F-C
        //FC1
        instanceFC1.transform.rotate(0f, 45f, 0f, 45f);
        instanceFC1.transform.translate(deltaX, 0f, deltaY);
        instanceFC1.transform.rotate(0f, -45f, 0f, 45f);

        //FC2
        instanceFC2.transform.rotate(0f, -45f, 0f, 45f);
        instanceFC2.transform.translate(deltaX, 0f, deltaY);
        instanceFC2.transform.rotate(0f, 45f, 0f, 45f);

        //E-B
        //EB1
        instanceEB1.transform.rotate(0f, 45f, 0f, 45f);
        instanceEB1.transform.translate(deltaX, 0f, deltaY);
        instanceEB1.transform.rotate(0f, -45f, 0f, 45f);

        //EB2
        instanceEB2.transform.rotate(0f, -45f, 0f, 45f);
        instanceEB2.transform.translate(deltaX, 0f, deltaY);
        instanceEB2.transform.rotate(0f, 45f, 0f, 45f);

        //FLAG
        modelInstanceF.transform.translate(deltaX,0f,deltaY);

        //POST
        instancePOST.transform.translate(deltaX,0f,deltaY);

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
            modelInstance8.transform.translate(0f, (initialDistance-distance)/5f,0f);
            modelInstance9.transform.translate(0f, (initialDistance-distance)/5f,0f);
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
            move(150f, 225f,250f);
            camera.lookAt(0f, 225f,-250f);
            camera.rotate(new Vector3(0f,1f,0f),-80f);
            dir = -80f;
        } else if (posicion.equals("B")||posicion.equals("b")){
            reset();
            isIrActual=true;
            move(-115f,225f,-95f);
            camera.lookAt(0f, 225f,-95f);
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
            move(-310f,225f,-35f);   //Listo
            camera.lookAt(0f, 300f,-25f);
            camera.rotate(new Vector3(0f,1f,0f),30f);
            dir = 30f;
        } else if (posicion.equals("F")||posicion.equals("f")){
            reset();
            isIrActual=true;
            move(-75f, 225f, 170f);
            camera.lookAt(0f, 300f,-50f);
            camera.rotate(new Vector3(0f,1f,0f),-230f);
            dir = -230f;
        } else if (posicion.equals("G")||posicion.equals("g")){
            reset();
            isIrActual=true;
            move(75f,225f,200f);
            camera.lookAt(0f, 300f,-50f);
            camera.rotate(new Vector3(0f,1f,0f),-220f);
            dir = -220f;
        } else if (posicion.equals("H")||posicion.equals("h")){
            reset();
            isIrActual=true;
            move(170f,225f,220f);
            camera.lookAt(0f, 300f,-75f);
            camera.rotate(new Vector3(0f,1f,0f),-230f);
            dir = -230f;
        } else if (posicion.equals("I")||posicion.equals("i")){
            reset();
            isIrActual=true;
            move(275f,225f,220f);
            camera.lookAt(0f, 300f,-80f);
            camera.rotate(new Vector3(0f,1f,0f),-250f);
            dir = -250f;
        } else if (posicion.equals("J")||posicion.equals("j")){
            reset();
            isIrActual=true;
            move(350f,225f,350f);
            camera.lookAt(0f, 300f,-200f);
            camera.rotate(new Vector3(0f,1f,0f),-200f);
            dir = -200f;
        } else if (posicion.equals("K")||posicion.equals("k")){
            reset();
            isIrActual=true;
            move(150f,225f,130f);
            camera.lookAt(0f, 300f,-240f);
            camera.rotate(new Vector3(0f,1f,0f),40f);
            dir = 30f;
        } else if (posicion.equals("II-1")||posicion.equals("ii-1")){
            reset();
            isIrActual=true;
            move(-300f,225f,120f);
            camera.lookAt(0f, 300f, -120f);
            camera.rotate(new Vector3(0f,1f,0f),0f);
            dir = 0f;
        } else if (posicion.equals("II-2")||posicion.equals("ii-2")){
            reset();
            isIrActual=true;
            move(-170f, 225f, 120f);
            camera.lookAt(0f, 300f, -250f);
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
        modelInstance8.transform.translate(v);
        modelInstance9.transform.translate(v);

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
        camera.position.set(0f,300f,0f);
        camera.rotate(Vector3.Y, -dir);
        dir = 0;

        model = assetManager.get("data/goalflag.g3db");
        modelInstanceF = new ModelInstance(model);
        flagArray.clear();
        flagArray.add(modelInstanceF);

        flagPosition.set(0,0,0f);


    }

    public void rotate(Vector3 v, float angulo){
        modelInstance1.transform.rotate(v,angulo);
        modelInstance2.transform.rotate(v,angulo);
        modelInstance3.transform.rotate(v,angulo);
        modelInstance4.transform.rotate(v,angulo);
        modelInstance5.transform.rotate(v,angulo);
        modelInstance6.transform.rotate(v,angulo);
        modelInstance7.transform.rotate(v,angulo);
        modelInstance8.transform.rotate(v,angulo);
        modelInstance9.transform.translate(v);
    }

    /**
     * Pone todos los booleans de las trayectorias en falso.
     */
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

    public void setEstoyVer(String ver){
        EstoyVer = ver;
    }
}