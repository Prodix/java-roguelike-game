package com.syndicate.berserktheriseofevil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

    final BerserkGame game;

    private Texture mainMenuBackground;
    private Texture buttonOneSwordIcon;
    private Texture buttonTwoSwordIcon;
    private OrthographicCamera camera;
    private GlyphLayout titleHigh;
    private GlyphLayout titleLow;
    private Viewport viewport;

    private float alpha = 0f;
    private float textTranslation = 0;
    public TextButton singlePlayButton;
    public TextButton coopPlayButton;
    public TextButton exitButton;

    public MainMenuScreen(final BerserkGame game) {
        this.game = game;

        mainMenuBackground = new Texture(Gdx.files.internal("mainmenu-background.png"));
        buttonOneSwordIcon = new Texture(Gdx.files.internal("sword-one.png"));
        buttonTwoSwordIcon = new Texture(Gdx.files.internal("sword-two.png"));

        titleHigh = new GlyphLayout(game.font96, "BERSERK");
        titleLow = new GlyphLayout(game.font96, "The Rise of Evil");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT);
        viewport = new ExtendViewport(Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT, camera);

        singlePlayButton = new TextButton(this, "Single play", new Texture(Gdx.files.internal("sword-one.png")), game.batch, game.font96, camera, viewport);
        coopPlayButton = new TextButton(this, "Co-op play", new Texture(Gdx.files.internal("sword-two.png")), game.batch, game.font96, camera, viewport);
        coopPlayButton.setTransitionDelay(2f);
        exitButton = new TextButton(this, "Exit", game.batch, game.font96, camera, viewport);
        exitButton.setTransitionDelay(3f);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);

        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        game.batch.begin();

        if (Commons.WINDOW_HEIGHT / 2f + titleHigh.height - textTranslation < Commons.WINDOW_HEIGHT - 60) {
            textTranslation -= 5;
        }

        if (alpha <= 1f) {
            game.batch.setColor(game.batch.getColor().r, game.batch.getColor().g, game.batch.getColor().b, alpha);
            alpha += 0.01f;
        } else if (alpha > 1f && Commons.WINDOW_HEIGHT / 2f + titleHigh.height - textTranslation > Commons.WINDOW_HEIGHT - 60) {
            singlePlayButton.draw(Commons.WINDOW_WIDTH / 2f - singlePlayButton.getTextMeasurement().width / 2f + singlePlayButton.getIcon().getWidth() / 2f, Commons.WINDOW_HEIGHT / 2f - textTranslation - titleHigh.height / 2f - singlePlayButton.getTextMeasurement().height - 140f);
            coopPlayButton.draw(Commons.WINDOW_WIDTH / 2f - coopPlayButton.getTextMeasurement().width / 2f + coopPlayButton.getIcon().getWidth() / 2f, Commons.WINDOW_HEIGHT / 2f - textTranslation - titleHigh.height / 2f - coopPlayButton.getTextMeasurement().height * 2 - 240f);
            exitButton.draw(Commons.WINDOW_WIDTH / 2f - exitButton.getTextMeasurement().width / 2f, Commons.WINDOW_HEIGHT / 2f - textTranslation - titleHigh.height / 2f - exitButton.getTextMeasurement().height * 3 - 340f);
        }

        game.batch.draw(mainMenuBackground, 0,0, Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT);
        game.font96.setColor(Color.valueOf("#ac3232"));
        game.font96.draw(game.batch, "BERSERK", Commons.WINDOW_WIDTH / 2f - titleHigh.width / 2f, Commons.WINDOW_HEIGHT / 2f + titleHigh.height - textTranslation);
        game.font96.setColor(Color.WHITE);
        game.font96.draw(game.batch, "The Rise of Evil", Commons.WINDOW_WIDTH / 2f - titleLow.width / 2f, Commons.WINDOW_HEIGHT / 2f - titleLow.height / 2f - textTranslation);

        game.batch.end();

    }

    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
