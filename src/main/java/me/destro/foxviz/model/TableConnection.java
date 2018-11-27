package me.destro.foxviz.model;

public class TableConnection {
    public int a;
    public int b;

    public TableConnection(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        return (((TableConnection) o).a == this.a && ((TableConnection) o).b == this.b)
                || (((TableConnection) o).a == this.b && ((TableConnection) o).b == this.a);
    }
}
