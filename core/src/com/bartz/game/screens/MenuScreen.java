package com.bartz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bartz.game.LawnMowerGame;

public class MenuScreen implements Screen{

    private LawnMowerGame game;
    private SpriteBatch batch;
    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;
    private Image titleImage;
    private Texture startTexture;
    private Texture optionsTexture;
    private Texture exitTexture;
    private Stage stage;
    private Texture titleTexture;
    private Table table;

    private OrthographicCamera camera;

    public MenuScreen(LawnMowerGame game, OrthographicCamera camera) {
        //assigns the game we passed to the placeholder
        this.game = game;
        this.camera = camera;

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        // initialize textures
        startTexture = new Texture("start.png");
        optionsTexture = new Texture("options.png");
        exitTexture = new Texture("exit.png");
        titleTexture = new Texture("title.png");

        // initialize actors
        titleImage = new Image(titleTexture);
        startButton = new ImageButton(new TextureRegionDrawable(startTexture));
        optionsButton = new ImageButton(new TextureRegionDrawable(optionsTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));

        stage.addActor(titleImage);
        float space = (float) (Gdx.graphics.getHeight() / 2 - startTexture.getHeight() -
                optionsTexture.getHeight() - exitTexture.getHeight()) / 8;
        // add table to stage and buttons to to table
        stage.addActor(table);

        table.align(Align.center);
        table.add(titleImage).spaceBottom(2*space);
        table.row();
        table.add(startButton).spaceBottom(space);
        table.row();
        //table.add(optionsButton).spaceBottom(space);
        //table.row();
        table.add(exitButton).spaceBottom(space);


        startButton.setBounds(Gdx.graphics.getWidth() / 2 - startTexture.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - startTexture.getHeight() / 2, startTexture.getWidth(), startTexture.getHeight());
        optionsButton.setBounds(Gdx.graphics.getWidth() / 2 - optionsTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 -  optionsTexture.getHeight() / 2, optionsTexture.getWidth(), optionsTexture.getHeight());
        exitButton.setBounds(Gdx.graphics.getWidth() / 2 - exitTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 - exitTexture.getHeight() / 2, exitTexture.getWidth(), exitTexture.getHeight());
    }

    @Override
    public void render(float deltaTime){
        batch.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        table.draw(batch, 1);
        titleImage.draw(batch, 1);

        camera.update();

        //makes the camera fit onto the available screen
        batch.setProjectionMatrix(camera.combined);
        batch.end();
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void show() {
        //prevent closing application on back key pressing
        Gdx.input.setCatchBackKey(true);

        startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, camera));
                return true;
            }
        });

        optionsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //game.setScreen(new OptionsScreen(game, camera));
                return true;
            }
        });

        exitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }
        });
    }

    @Override
    public void hide(){
        dispose();
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
        batch.dispose();
    }
}