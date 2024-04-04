package dev.bangbang.loom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	PerspectiveCamera camera;
	DecalBatch batch;
	Decal someDude;
	Decal floor;

	float speed;
	float angle;
	float radius;

	@Override
	public void create () {
		Texture badLogicTexture = new Texture("badlogic.jpg");
		TextureRegion badLogicTextureRegion = new TextureRegion(badLogicTexture);

		Texture floorTexture = new Texture("floor.png");
		TextureRegion floorTextureRegion = new TextureRegion(floorTexture);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 1;
		camera.far = 300;
		camera.position.set(0f, 10f, 0);
		camera.lookAt(5f, 0f, 5f);

		batch = new DecalBatch(new CameraGroupStrategy(camera));

		floor = Decal.newDecal(floorTextureRegion);
		floor.setPosition(0f, 0f, 0f);
		floor.lookAt(Vector3.Y, camera.up);

		someDude = Decal.newDecal(1, 1, badLogicTextureRegion);
		someDude.setPosition(0f, someDude.getScaleY(), 3);

		speed = 0.2f;
		angle = 0f;
		radius = 10f;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1, true);
		camera.update();


		batch.add(floor);
		batch.add(someDude);
		batch.flush();

		// BOUNCE :D
		if (camera.position.y > 20f || camera.position.y < 5f)
			speed = -speed;

		camera.position.y += (speed);
		someDude.lookAt(camera.position, camera.up);

		// CIRCLE EM
		someDude.setX(radius * MathUtils.cos(angle));
		someDude.setZ(radius * MathUtils.sin(angle));

		angle += 0.1;

		if (angle > (360 * MathUtils.degreesToRadians)) {
			angle = 0f;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
