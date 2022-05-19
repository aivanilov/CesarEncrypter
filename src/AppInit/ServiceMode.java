package AppInit;

public enum ServiceMode {
    MODE;
    private int mode;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ServiceMode{" +
                "mode=" + mode +
                '}';
    }
}
