package me.destro.foxviz.model;

public class Connection {
    public int a;
    public int b;

    @Override
    public boolean equals(Object o) {
        return (((Connection) o).a == this.a && ((Connection) o).b == this.b)
                || (((Connection) o).a == this.b && ((Connection) o).b == this.a);
    }
}
