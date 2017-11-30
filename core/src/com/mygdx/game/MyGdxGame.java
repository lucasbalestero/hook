package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private Rectangle hook;
	private SpriteBatch batch;
	private Texture img;
	private Texture dropImage;
	private Array<Rectangle> raindrops;
	private Vector3 touchPos = new Vector3();;
	private long lastDropTime;
	
	private Image hookImg;
	private Stage stage;
	
	private float GRAPPLING_HOOK_SPEED = 0.5f;
	private float distance;
	private Vector2 initialPos;
	private Vector2 direction;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		
		img = new Texture(Gdx.files.internal("hook.png"));
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		
		hookImg = new Image(img);
		hookImg.setPosition(800 / 2 - 21 / 2, 20);
		hookImg.setScale(0.1f);
		
		stage = new Stage();
		stage.addActor(hookImg);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		raindrops = new Array<Rectangle>();
		
		for (int i=0; i < 10; i++) {
			spawnRaindrop();
		}
		
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
		camera.update();
		
		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			initialPos = new Vector2(hookImg.getX(),hookImg.getY());

			hookImg.addAction(Actions.moveTo(touchPos.x, touchPos.y, GRAPPLING_HOOK_SPEED));
//			hookImg.addAction(Actions.moveTo(initialPos.x, initialPos.y, GRAPPLING_HOOK_SPEED));
				
		}
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		dropImage.dispose();
	}
	
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0,800-64);
		raindrop.y = MathUtils.random(0,480-64);
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
