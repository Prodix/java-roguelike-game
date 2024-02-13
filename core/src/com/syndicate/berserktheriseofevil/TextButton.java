package com.syndicate.berserktheriseofevil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;


public class TextButton {

    private String text;
    private Texture icon;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout textMeasurement;
    private float spaceBetween = 20f;
    private boolean isFade = true;
    private float transitionAlpha = 0f;
    private float hoverAlpha = 0f;
    private float transitionDelay = 1f;
    private Color hoverColor = Color.valueOf("#ac3232");
    private com.badlogic.gdx.math.Rectangle area;
    private ShapeRenderer shapeRenderer;
    private float cursorDelta;
    private Camera camera;
    private Viewport viewport;
    private boolean isHand = false;

    public TextButton(String text, SpriteBatch batch, BitmapFont font, Camera camera, Viewport viewport) {
        this.text = text;
        this.batch = batch;
        this.font = font;
        textMeasurement = new GlyphLayout(font, text);
        area = new com.badlogic.gdx.math.Rectangle();
        this.camera = camera;
        this.viewport = viewport;
    }

    public TextButton(String text, Texture icon, SpriteBatch batch, BitmapFont font, Camera camera, Viewport viewport) {
        this.text = text;
        this.icon = icon;
        this.batch = batch;
        this.font = font;
        textMeasurement = new GlyphLayout(font, text);
        area = new com.badlogic.gdx.math.Rectangle();
        this.camera = camera;
        this.viewport = viewport;
    }

    public GlyphLayout getTextMeasurement() {
        return textMeasurement;
    }

    public String getText() {
        return text;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Texture getIcon() {
        return icon;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setText(String text) {
        this.text = text;
        textMeasurement.setText(font, text);
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public float getSpaceBetween() {
        return spaceBetween;
    }

    public void setSpaceBetween(float spaceBetween) {
        this.spaceBetween = spaceBetween;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public float getTransitionDelay() {
        return transitionDelay;
    }

    public void setTransitionDelay(float transitionDelay) {
        this.transitionDelay = transitionDelay;
    }

    public void draw(float x, float y) {
        if (batch.isDrawing()) {
            if (transitionDelay > 0f) {
                transitionDelay -= Gdx.graphics.getDeltaTime();
                return;
            }

            recalculateArea(x, y);
            if (isFade && transitionAlpha < 1f) {
                font.setColor(font.getColor().r,font.getColor().g,font.getColor().b, transitionAlpha);
                batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, transitionAlpha);
            }

            if (transitionAlpha >= 1f) {
                if (checkHover()) {
                    if (!isHand) {
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                        isHand = true;
                    }
                    font.setColor(hoverColor.r, hoverColor.g, hoverColor.b, 1f);
                } else {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                    isHand = false;
                    font.setColor(Color.WHITE);
                }
            }

            font.draw(batch, text, x, y);

            if (icon != null) {
                batch.draw(icon, x - spaceBetween - icon.getWidth(), y - icon.getHeight());
            }

            if (transitionAlpha < 1f) {
                transitionAlpha += 0.01f;
            }

            font.setColor(font.getColor().r,font.getColor().g,font.getColor().b, 1f);
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
        }
    }

    private void recalculateArea(float x, float y) {
        if (icon != null) {
            area.setSize(textMeasurement.width + spaceBetween + icon.getWidth(), textMeasurement.height);
            area.setPosition(x - spaceBetween - icon.getWidth(), y - textMeasurement.height);
        } else {
            area.setSize(textMeasurement.width, textMeasurement.height);
            area.setPosition(x, y - textMeasurement.height);
        }
    }

    private boolean checkHover() {
        Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        return area.contains(pos.x, pos.y);
    }
}
