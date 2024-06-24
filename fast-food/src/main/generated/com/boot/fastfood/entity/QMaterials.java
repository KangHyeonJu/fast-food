package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMaterials is a Querydsl query type for Materials
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterials extends EntityPathBase<Materials> {

    private static final long serialVersionUID = 1484257926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaterials materials = new QMaterials("materials");

    public final StringPath mtCode = createString("mtCode");

    public final StringPath mtName = createString("mtName");

    public final NumberPath<Integer> mtStock = createNumber("mtStock", Integer.class);

    public final QVendor vendor;

    public QMaterials(String variable) {
        this(Materials.class, forVariable(variable), INITS);
    }

    public QMaterials(Path<? extends Materials> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMaterials(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMaterials(PathMetadata metadata, PathInits inits) {
        this(Materials.class, metadata, inits);
    }

    public QMaterials(Class<? extends Materials> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

