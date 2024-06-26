package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouting is a Querydsl query type for Routing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouting extends EntityPathBase<Routing> {

    private static final long serialVersionUID = 1404365216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouting routing = new QRouting("routing");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItems items;

    public final QProcess process;

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QRouting(String variable) {
        this(Routing.class, forVariable(variable), INITS);
    }

    public QRouting(Path<? extends Routing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouting(PathMetadata metadata, PathInits inits) {
        this(Routing.class, metadata, inits);
    }

    public QRouting(Class<? extends Routing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.items = inits.isInitialized("items") ? new QItems(forProperty("items")) : null;
        this.process = inits.isInitialized("process") ? new QProcess(forProperty("process"), inits.get("process")) : null;
    }

}

