package com.example.postover.Model;

public abstract class Component {

    private float[] position;
    private boolean asText;

    public Component(boolean asText){
        this.position = new float[]{0, 0};
        this.asText = asText;
    }

    public Component(boolean asText, float[] position){
        this.position = position;
        this.asText = asText;
    }

    public abstract void render();

    public abstract void update(int n, String[] args);

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float[] getPosition() {
        return position;
    }

    public boolean isAsText() {
        return asText;
    }
}
