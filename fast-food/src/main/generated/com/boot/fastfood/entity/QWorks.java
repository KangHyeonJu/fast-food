package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorks is a Querydsl query type for Works
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorks extends EntityPathBase<Works> {

    private static final long serialVersionUID = 1065292508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorks works = new QWorks("works");

    public final NumberPath<Integer> def = createNumber("def", Integer.class);

    public final NumberPath<Integer> defRate = createNumber("defRate", Integer.class);

    public final DateTimePath<java.util.Date> eDate = createDateTime("eDate", java.util.Date.class);

    public final QEmployee employee;

    public final ListPath<Process, QProcess> process = this.<Process, QProcess>createList("process", Process.class, QProcess.class, PathInits.DIRECT2);

    public final QProduction production;

    public final DateTimePath<java.util.Date> sDate = createDateTime("sDate", java.util.Date.class);

    public final StringPath wkCode = createString("wkCode");

    public final NumberPath<Integer> wkNo = createNumber("wkNo", Integer.class);

    public QWorks(String variable) {
        this(Works.class, forVariable(variable), INITS);
    }

    public QWorks(Path<? extends Works> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorks(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorks(PathMetadata metadata, PathInits inits) {
        this(Works.class, metadata, inits);
    }

    public QWorks(Class<? extends Works> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.production = inits.isInitialized("production") ? new QProduction(forProperty("production"), inits.get("production")) : null;
    }

}

