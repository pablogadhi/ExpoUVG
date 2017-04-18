package com.uvg.expo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.UBJsonReader;

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

	public String Adonde;
	public String Estoy;
	public boolean isIrActual;

	@Override
	public void create() {

		Adonde = "";
		Estoy = "";
		isIrActual = true;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//Cambiar la camara position.set a  (0f,10f,0f) para que este sobre pero no se mira bien por falta de texturas
		camera.position.set(0f,1000f,0f);
		camera.lookAt(0f,0f,0f);
		camera.near = 1.0f;
		camera.far = 100000.0f;;

		modelBatch = new ModelBatch();

		UBJsonReader jsonReader = new UBJsonReader();

		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/UVGCleanB1.g3db"));
		modelInstance1 = new ModelInstance(model);
		modelInstance1.transform.translate(0f,0f,0f);
		instances.add(modelInstance1);

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/UVGCleanB2.g3db"));
		modelInstance2 = new ModelInstance(model);
		modelInstance2.transform.translate(0f,0f,0f);
		instances.add(modelInstance2);

		//copia de aqui para

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/UVGCleanB3.g3db"));
		modelInstance3 = new ModelInstance(model);
		modelInstance3.transform.translate(0f,0f,0f);
		instances.add(modelInstance3);

		//aca  solo cambia modelInstance3 por modelInstance4 igual data/UVGCleanB3.g3db a data/UVGCleanB4.g3db

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/UVGCleanB4.g3db"));
		modelInstance4 = new ModelInstance(model);
		modelInstance4.transform.translate(0f,0f,0f);
		instances.add(modelInstance4);

		gestureDetector = new GestureDetector(this);
		Gdx.input.setInputProcessor(gestureDetector);

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();

		modelBatch.begin(camera);
		modelBatch.render(instances, environment);
		if (isIrActual){
			//irActual("II-2");
			irActual(Adonde);
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

		modelInstance1.transform.translate(deltaX,0f,deltaY);
		modelInstance2.transform.translate(deltaX,0f,deltaY);
		modelInstance3.transform.translate(deltaX,0f,deltaY);
		modelInstance4.transform.translate(deltaX,0f,deltaY);

		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		modelInstance1.transform.translate(0f, initialDistance-distance,0f);
		modelInstance2.transform.translate(0f, initialDistance-distance,0f);
		modelInstance3.transform.translate(0f, initialDistance-distance,0f);
		modelInstance4.transform.translate(0f, initialDistance-distance,0f);

		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {

		if (pointer1.x-initialPointer1.x==pointer2.x-initialPointer2.x){
			modelInstance1.transform.rotate(new Vector3(0f,0f,1f),pointer1.x-initialPointer1.x);
			modelInstance2.transform.rotate(new Vector3(0f,0f,1f),pointer1.x-initialPointer1.x);
			modelInstance3.transform.rotate(new Vector3(0f,0f,1f),pointer1.x-initialPointer1.x);
			modelInstance4.transform.rotate(new Vector3(0f,0f,1f),pointer1.x-initialPointer1.x);
		}
		if (pointer1.y-initialPointer1.y==pointer2.y-initialPointer2.y){
			modelInstance1.transform.rotate(new Vector3(0f,1f,0f),pointer1.y-initialPointer1.y);
			modelInstance2.transform.rotate(new Vector3(0f,1f,0f),pointer1.y-initialPointer1.y);
			modelInstance3.transform.rotate(new Vector3(0f,1f,0f),pointer1.y-initialPointer1.y);
			modelInstance4.transform.rotate(new Vector3(0f,1f,0f),pointer1.y-initialPointer1.y);

		}



		return true;
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
		if (posicion.equals("A") || posicion.equals("a")){
			move(35f,500f,250f);
		} else if (posicion.equals("B")||posicion.equals("b")){
			move(-250f,500f,-135f);
		} else if (posicion.equals("C")||posicion.equals("c")){
			//aun no esta
			move(0f,0f,0f);
		} else if (posicion.equals("D")||posicion.equals("d")){
			//aun no esta
			move(0f,0f,0f);
		} else if (posicion.equals("E")||posicion.equals("e")){
			move(-250f,500f,75f);
		} else if (posicion.equals("F")||posicion.equals("f")){
			move(10f,500f,50f);
		} else if (posicion.equals("G")||posicion.equals("g")){
			move(75f,500f,80f);
		} else if (posicion.equals("H")||posicion.equals("h")){
			move(170f,500f,75f);
		} else if (posicion.equals("I")||posicion.equals("i")){
			move(275f,500f,80f);
		} else if (posicion.equals("J")||posicion.equals("j")){
			move(400f,500f,200f);
		} else if (posicion.equals("K")||posicion.equals("k")){
			move(225f,500f,240f);
		} else if (posicion.equals("II-1")||posicion.equals("ii-1")){
			move(-265f,500f,220f);
		} else if (posicion.equals("II-2")||posicion.equals("ii-2")){
			move(-150f,500f,250f);
		}

	}

	public void move(float x,float y, float z){
		Vector3 vectorFinal = position(x,y,z);
		Vector3 vectorActual = modelInstance1.transform.getTranslation(new Vector3());
		while (!vectorActual.equals(vectorFinal)){
			/*
			modelInstance1.transform.translate(v.x,v.y,v.z);
			modelInstance2.transform.translate(v.x,v.y,v.z);
			modelInstance3.transform.translate(v.x,v.y,v.z);
			modelInstance4.transform.translate(v.x,v.y,v.z);*/
		}
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

}