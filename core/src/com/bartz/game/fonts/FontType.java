package com.bartz.game.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public enum FontType {
    START_FONT(90, Color.WHITE, 10, Color.BLACK),
    LIFE_FONT(90, Color.CHARTREUSE, 10, Color.BLACK),
    PROGRESS_FONT(90, Color.SCARLET, 10, Color.BLACK),
    FUEL_FONT(90,Color.GOLD ,10, Color.BLACK);

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    FontType(int size, Color mainColor, int borderWidth, Color borderColor){
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;
        parameter.color = mainColor;

    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter() {
        return parameter;
    }
}
