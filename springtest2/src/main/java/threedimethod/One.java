package threedimethod;

public class One
{
    int i;
    int j;

    public One(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public One() {
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "One{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
