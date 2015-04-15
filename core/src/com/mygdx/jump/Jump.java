package com.mygdx.jump;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Jump extends ApplicationAdapter implements GestureDetector.GestureListener {
    BitmapFont font;
    private World world;
    private Box2DDebugRenderer b2dr;
    public static final float fPpm = 100;
    public static final short shGround = 2, shPlayer = 4;
    public static final int nWidth = 320, nHeight = 240;
    private OrthographicCamera b2dCam;
    private CollisionDetector collisionDetector;
    TextureAtlas textureAtlas;
    Skin skin;
    private Body playerBody;
    GestureDetector gestureDetector;
    float w, h;
    Vector2 vPlat1, vPlat2;
    EdgeShape edgeShape;
    TextButton.TextButtonStyle tbsRight, tbsLeft;
    TextButton tbRight, tbLeft;
    Stage stage;

    //  vPlat2(15,0);
    //private MyContactListener cl;
    // public Play(GameStateManager gsm) {
    public void create() {
        font = new BitmapFont();
        stage = new Stage();
        textureAtlas = new TextureAtlas(Gdx.files.internal("arrows.pack"));
        skin = new Skin();
        skin.addRegions(textureAtlas);
        tbsRight = new TextButton.TextButtonStyle();
        tbsLeft = new TextButton.TextButtonStyle();
        tbsRight.up = skin.getDrawable("right");
        tbsRight.down = skin.getDrawable("rightdown");
        tbsLeft.up = skin.getDrawable("left");
        tbsLeft.down = skin.getDrawable("lettdown");
        tbsRight.font = font;
        tbsLeft.font = font;
        tbLeft = new TextButton("", tbsLeft);
        tbRight = new TextButton("", tbsRight);
        tbRight.setPosition(100, 200);
        tbLeft.setPosition(10, 200);
        stage.addActor(tbLeft);
        stage.addActor(tbRight);
        vPlat1 = new Vector2(-15, 0);
        vPlat2 = new Vector2(15, 0);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
//        super(gsm);
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
        world = new World(new Vector2(0, -9.81f), true);

        // cl = new MyContactListener();
        world.setContactListener(collisionDetector);

        b2dr = new Box2DDebugRenderer();

        // create platform
        BodyDef bdef = new BodyDef();
        //  bdef.position.set(160 / fPpm, 120 / fPpm);7
        bdef.position.set(160 / fPpm, 10 / fPpm);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / fPpm, 5 / fPpm);
        //  edgeShape = new EdgeShape();
        //edgeShape.set(vPlat1, vPlat2);
        // shape.setasEdge(vPlat1, vPlat2 ); //ends of the line);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = shGround;
        fdef.filter.maskBits = shPlayer;

        body.createFixture(fdef).setUserData("ground");
        FixtureDef ground = new FixtureDef();


        // create player
        bdef.position.set(160 / fPpm, 200 / fPpm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);

        shape.setAsBox(5 / fPpm, 5 / fPpm);
        fdef.shape = shape;
        fdef.filter.categoryBits = shPlayer;
        fdef.filter.maskBits = shGround;
        playerBody.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(2 / fPpm, 2 / fPpm, new Vector2(0, -5 / fPpm), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = shPlayer;
        fdef.filter.maskBits = shGround;
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData("foot");


        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, nWidth / fPpm, nHeight / fPpm);

    }

    public void handleInput() {

        // player jump
        //   if (MyInput.isPressed(MyInput.BUTTON1)) {
        //     if (collisionDetector.isHit()) {
        //       playerBody.applyForceToCenter(0, 200, true);
        // }
        //}
    }

    public void update(float dt) {
        dt = Game.STEP;
        handleInput();
        world.step(dt, 6, 2);
    }

    public void render() {
        //  playerBody.setLinearVelocity(0f, -5f);
        //  world.step();
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Game.STEP);


        // draw box2d world
        b2dr.render(world, b2dCam.combined);
        //super.render();
        //stage.draw();
        if (Input.MoveLeft()) {
            playerBody.setLinearVelocity(10f, 0f);
        }

    }

    public void dispose() {
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (CollisionDetector.hitTest()) {
            System.out.println("tap");
            playerBody.applyForceToCenter(0, 200, true);
        }
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
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}









