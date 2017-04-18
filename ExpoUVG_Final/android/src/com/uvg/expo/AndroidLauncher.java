package com.uvg.expo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

public class AndroidLauncher extends AppCompatActivity{

	GLSurfaceView surface;
	FloatingActionButton qrfab;
	SearchView searchView;
	Toolbar toolbar;
	ModelUVG modelUVG;

	private String mostrar;
	private SharedPreferences prefs;

	TextView debbug;//Para debuggear unicamente

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_ui);

		prefs = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
		prefs.edit().putString("Lugar", "Empty").apply();

		searchView = (SearchView) findViewById(R.id.searchview);
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		modelUVG = RenderCreation.uvgModel;
		modelUVG.setAdonde(null);
		modelUVG.setEstoy(null);
		View render = RenderCreation.renderView;

		//surface = (GLSurfaceView) findViewById(R.id.surface);
		ViewGroup group = (ViewGroup) surface.getParent();
		int index = group.indexOfChild(surface);
		group.removeView(surface);
		group.addView(render, index);

		qrfab = (FloatingActionButton) findViewById(R.id.QRFAB);
		qrfab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(AndroidLauncher.this, QRReader.class);
				startActivity(intent);
			}
		});

		//Textview para debuggear
		debbug = (TextView) findViewById(R.id.debugg);

		//Listener para las busquedas
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				String busqueda = searchView.getQuery().toString();
				modelUVG.setAdonde(busqueda);
				debbug.setText(busqueda);
				//Para ocultar el teclado al realizar la busqueda:
				InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				return false;
			}
		});


	}


	@Override
	protected void onResume() {
		super.onResume();
		setMostrar(prefs.getString("Lugar","Empty"));
		debbug.setText(mostrar);
		cambiarPosicion();
	}

	public void setMostrar(String qranswer){
		mostrar = qranswer;
	}

	public void cambiarPosicion(){
		modelUVG.irActual(mostrar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
