package com.example.component;

/**
 * Created by pyshankov on 05.08.16.
 */
public class Letter implements Component {


    Component header;
    Component body;
    Component footer;

    public Letter(Component header, Component body, Component footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    @Override
    public void print() {
        header.print();
        body.print();
        footer.print();
    }
}
