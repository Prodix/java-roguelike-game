package com.syndicate.berserktheriseofevil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class IntroScreen implements Screen {

    final BerserkGame game;
    private Texture introBackground;
    private GlyphLayout titleHigh;
    private GlyphLayout titleLow;
    private GlyphLayout loadingLayout;
    private Viewport viewport;
    private int progress = 0;
    private double clock = 0;
    private float alpha = 1f;



    OrthographicCamera camera;

    public IntroScreen(final BerserkGame game) {
        this.game = game;

        introBackground = new Texture(Gdx.files.internal("intro-background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT);
        titleHigh = new GlyphLayout(game.font96, "BERSERK");
        titleLow = new GlyphLayout(game.font96, "The Rise of Evil");
        loadingLayout = new GlyphLayout();
        viewport = new ExtendViewport(Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT, camera);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(introBackground, 0, 0, Commons.WINDOW_WIDTH, Commons.WINDOW_HEIGHT);
        game.font96.setColor(Color.valueOf("#ac3232"));
        game.font96.draw(game.batch, "BERSERK", Commons.WINDOW_WIDTH / 2f - titleHigh.width / 2f, Commons.WINDOW_HEIGHT / 2f + titleHigh.height);
        game.font96.setColor(Color.WHITE);
        game.font96.draw(game.batch, "The Rise of Evil", Commons.WINDOW_WIDTH / 2f - titleLow.width / 2f, Commons.WINDOW_HEIGHT / 2f - titleLow.height / 2f);
        if (clock >= 0.03 && progress != 100) {
            progress++;
            clock = 0;
        }
        String progressString = progress+" %";
        loadingLayout.setText(game.font64, progressString);
        game.font64.draw(game.batch, progressString, Commons.WINDOW_WIDTH - 40f - loadingLayout.width, 40f + loadingLayout.height);
        game.batch.end();
        clock += v;

        if (progress == 100) {
            game.batch.setColor(game.batch.getColor().r, game.batch.getColor().g, game.batch.getColor().b, alpha);
            alpha -= 0.01f;
            if (alpha <= 0) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
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
        introBackground.dispose();
    }
}
