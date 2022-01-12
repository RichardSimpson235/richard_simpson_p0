package main.java.services;

import main.java.structures.Context;

public class ContextService {

    private Context context;

    public ContextService(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
