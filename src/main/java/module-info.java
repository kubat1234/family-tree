module tcs.familytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires org.jooq;


    opens tcs.familytree to javafx.fxml;
    exports tcs.familytree;

    opens tcs.familytree.views to javafx.fxml;
    exports tcs.familytree.views;

    exports tcs.familytree.core;
    exports tcs.familytree.services;
    exports tcs.familytree.viewmodels;
    exports tcs.familytree.core.person;
    exports tcs.familytree.core.date;
    exports tcs.familytree.core.place;
    exports tcs.familytree.core.relation;
    exports tcs.familytree.core.relationtype;
    exports tcs.familytree.services.database;
    exports tcs.familytree.views.plane;
    opens tcs.familytree.views.plane to javafx.fxml;

    exports tcs.familytree.jooq.generated.tables.records to org.jooq;
    exports tcs.familytree.jooq.generated.udt.paths to org.jooq;
    exports tcs.familytree.jooq.generated.udt.records to org.jooq;
    exports tcs.familytree.jooq.generated.udt to org.jooq;
    exports tcs.familytree.views.painter;
    opens tcs.familytree.views.painter to javafx.fxml;
    opens tcs.familytree.services to javafx.fxml;
}