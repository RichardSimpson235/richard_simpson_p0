package main.java.views;

import java.io.InputStream;

public abstract class AbstractView {

    protected final InputStream inputStream;

    public AbstractView(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public abstract void render();
    public abstract String listen();

}
