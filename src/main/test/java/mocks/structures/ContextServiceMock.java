package main.test.java.mocks.structures;

import main.java.services.ContextService;
import main.java.structures.Context;

public class ContextServiceMock extends ContextService {

    public ContextServiceMock() {
        super(null);
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setContext(Context context) {
    }
}
