public class Nice implements Cloneable{

    int x = 100;

    int[] xs = {3,3,3};

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
