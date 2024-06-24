package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduction is a Querydsl query type for Production
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduction extends EntityPathBase<Production> {

    private static final long serialVersionUID = 880116127L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduction production = new QProduction("production");

    public final QContract contract;

    public final QItems itName;

    public final NumberPath<Integer> pmAmount = createNumber("pmAmount", Integer.class);

    public final StringPath pmCode = createString("pmCode");

    public final DateTimePath<java.util.Date> pmEDate = createDateTime("pmEDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> pmSDate = createDateTime("pmSDate", java.util.Date.class);

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public QProduction(String variable) {
        this(Production.class, forVariable(variable), INITS);
    }

    public QProduction(Path<? extends Production> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduction(PathMetadata metadata, PathInits inits) {
        this(Production.class, metadata, inits);
    }

    public QProduction(Class<? extends Production> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contract = inits.isInitialized("contract") ? new QContract(forProperty("contract"), inits.get("contract")) : null;
        this.itName = inits.isInitialized("itName") ? new QItems(forProperty("itName")) : null;
    }

}

