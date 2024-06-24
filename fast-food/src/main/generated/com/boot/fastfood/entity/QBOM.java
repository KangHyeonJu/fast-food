package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBOM is a Querydsl query type for BOM
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBOM extends EntityPathBase<BOM> {

    private static final long serialVersionUID = 1118404506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBOM bOM = new QBOM("bOM");

    public final StringPath bomCode = createString("bomCode");

    public final QItems items;

    public final QMaterials materials;

    public final NumberPath<Float> mtAmount = createNumber("mtAmount", Float.class);

    public QBOM(String variable) {
        this(BOM.class, forVariable(variable), INITS);
    }

    public QBOM(Path<? extends BOM> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBOM(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBOM(PathMetadata metadata, PathInits inits) {
        this(BOM.class, metadata, inits);
    }

    public QBOM(Class<? extends BOM> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.items = inits.isInitialized("items") ? new QItems(forProperty("items")) : null;
        this.materials = inits.isInitialized("materials") ? new QMaterials(forProperty("materials"), inits.get("materials")) : null;
    }

}

