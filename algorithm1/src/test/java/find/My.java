package find;

public class My implements Comparable {

    int value;

    public My(int value) {
        this.value = value;
    }

    public My() {
    }

    @Override
    public int compareTo(Object o) {
        o = (My)o;
        if (((My) o).value < this.value)return 1;
        else return 0;
    }
}
