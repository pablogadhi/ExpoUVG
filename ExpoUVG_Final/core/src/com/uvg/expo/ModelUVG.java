package com.uvg.expo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.UBJsonReader;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import javax.jws.WebParam;
import javax.management.modelmbean.ModelMBeanInfoSupport;

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

	@Override
	public void create() {

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

		UBJsonReader jsonReader = new UBJsonReader();

		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/EdificioA.g3db"));
		modelInstance1 = new ModelInstance(model);
		modelInstance1.transform.translate(0f,0f,0f);
		instances.add(modelInstance1);

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/EdificioII.g3db"));
		modelInstance2 = new ModelInstance(model);
		modelInstance2.transform.translate(0f,0f,0f);
		instances.add(modelInstance2);

		//copia de aqui para

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/EdificioE.g3db"));
		modelInstance3 = new ModelInstance(model);
		modelInstance3.transform.translate(0f,0f,0f);
		instances.add(modelInstance3);

		//aca  solo cambia modelInstance3 por modelInstance4 igual data/UVGCleanB3.g3db a data/UVGCleanB4.g3db

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/EdificioB.g3db"));
		modelInstance4 = new ModelInstance(model);
		modelInstance4.transform.translate(0f,0f,0f);
		instances.add(modelInstance4);

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/MegaEdificio.g3db"));
		modelInstance5 = new ModelInstance(model);
		modelInstance5.transform.translate(0f,0f,0f);
		instances.add(modelInstance5);

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/Parqueo.g3db"));
		modelInstance6 = new ModelInstance(model);
		modelInstance6.transform.translate(0f,0f,0f);
		instances.add(modelInstance6);

		jsonReader = new UBJsonReader();

		modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.internal("data/Extras.g3db"));
		modelInstance7 = new ModelInstance(model);
		modelInstance7.transform.translate(0f,0f,0f);
		instances.add(modelInstance7);

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

		//Andrea Aqui se cambia de letra para ver cada edificio
		irActual("E");

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
		if (dentro) {
			modelInstance1.transform.translate(0f, 350f, 0f);
			dentro=false;
		} else{
			modelInstance1.transform.translate(0f, -350f,0f);
			dentro=true;
		}

		return true;
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
		modelInstance5.transform.translate(deltaX,0f,deltaY);
		modelInstance6.transform.translate(deltaX,0f,deltaY);
		modelInstance7.transform.translate(deltaX,0f,deltaY);


		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		/*
		Vector3 v = modelInstance1.transform.getTranslation(new Vector3());
		if (v.y > 50f && v.y < 100000f){
			modelInstance1.transform.translate(0f, (initialDistance-distance)/5f,0f);
			modelInstance2.transform.translate(0f, (initialDistance-distance)/5f,0f);
			modelInstance3.transform.translate(0f, (initialDistance-distance)/5f,0f);
			modelInstance4.transform.translate(0f, (initialDistance-distance)/5f,0f);
		}
		*/
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {

		float deltaX = pointer1.x-pointer2.x;
		float deltaY =pointer1.y-pointer2.y;
		float angulo = (float)Math.atan2((double)deltaY,(double)deltaX)* MathUtils.radiansToDegrees;
		angulo+=90f;
		modelInstance1.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance2.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance3.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance4.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance5.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance6.transform.rotate(Vector3.Y,-angulo/90);
		modelInstance7.transform.rotate(Vector3.Y,-angulo/90);
		/**
		 //if (pointer1.x-initialPointer1.x==pointer2.x-initialPointer2.x){
		 modelInstance1.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance2.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance3.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 modelInstance4.transform.rotate(Vector3.Z,pointer1.x-initialPointer1.x);
		 //}
		 //if (pointer1.y-initialPointer1.y==pointer2.y-initialPointer2.y){
		 modelInstance1.transform.rotate(Vector3.Y,pointer1.y-initialPointer1.y);
		 modelInstance2.transform.rotate(Vector3.Y,pointer1.y-initialPointer1.y);
		 modelInstance3.transform.rotate(Vector3.Y,pointer1.y-initialPointer1.y);
		 modelInstance4.transform.rotate(Vector3.Y,pointer1.y-initialPointer1.y);

		 //}*/

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
			isIrActual=true;
			move(150f, 625f,250f);
			camera.lookAt(0f, 625f,-250f);
			camera.rotate(new Vector3(0f,1f,0f),-80f);
		} else if (posicion.equals("B")||posicion.equals("b")){
			isIrActual=true;
			//move(-200f,0f,-115f);
			move(-115f,625f,-95f);
			camera.lookAt(0f, 625f,-95f);
			camera.rotate(new Vector3(0f,1f,0f),-85f);
		} else if (posicion.equals("C")||posicion.equals("c")){
			isIrActual=true;
			//aun no esta
			move(0f,0f,0f);
		} else if (posicion.equals("D")||posicion.equals("d")){
			isIrActual=true;
			//aun no esta
			move(0f,0f,0f);
		} else if (posicion.equals("E")||posicion.equals("e")){
			isIrActual=true;
			move(-310f,625f,-35f);   //Listo
			camera.lookAt(0f, 700f,-25f);
			camera.rotate(new Vector3(0f,1f,0f),30f);
		} else if (posicion.equals("F")||posicion.equals("f")){
			isIrActual=true;
			move(10f,500f,50f);
			camera.lookAt(0f, 500f,-50f);
			//camera.rotate(new Vector3(0f,1f,0f),-85f);

		} else if (posicion.equals("G")||posicion.equals("g")){
			isIrActual=true;
			move(75f,500f,80f);
			camera.lookAt(0f, 500f,-80f);
			//camera.rotate(new Vector3(0f,1f,0f),70f);

		} else if (posicion.equals("H")||posicion.equals("h")){
			isIrActual=true;
			move(170f,500f,75f);
			camera.lookAt(0f, 500f,-75f);
			//camera.rotate(new Vector3(0f,1f,0f),-70f);

		} else if (posicion.equals("I")||posicion.equals("i")){
			isIrActual=true;
			move(275f,500f,80f);
			camera.lookAt(0f, 500f,-80f);
			//camera.rotate(new Vector3(0f,1f,0f),30f);
		} else if (posicion.equals("J")||posicion.equals("j")){
			isIrActual=true;
			move(400f,500f,200f);
			camera.lookAt(0f, 500f,-200f);
			//camera.rotate(new Vector3(0f,1f,0f),30f);
		} else if (posicion.equals("K")||posicion.equals("k")){
			isIrActual=true;
			move(225f,500f,240f);
			camera.lookAt(0f, 500f,-240f);
			//camera.rotate(new Vector3(0f,1f,0f),30f);
		} else if (posicion.equals("II-1")||posicion.equals("ii-1")){
			isIrActual=true;
			move(-265f,500f,220f);
			camera.lookAt(0f, 500f,-220f);
			//	camera.rotate(new Vector3(0f,1f,0f),30f);
		} else if (posicion.equals("II-2")||posicion.equals("ii-2")){
			isIrActual=true;
			move(-150f,500f,250f);
			camera.lookAt(0f, 500f,-250f);
		} else {
			isIrActual = false;
		}

	}

	public void move(float x,float y,float z){
		Vector3 v = position(x,y,z);
		modelInstance1.transform.translate(v);
		modelInstance2.transform.translate(v);
		modelInstance3.transform.translate(v);
		modelInstance4.transform.translate(v);
		modelInstance5.transform.translate(v);
		modelInstance6.transform.translate(v);
		modelInstance7.transform.translate(v);
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


}