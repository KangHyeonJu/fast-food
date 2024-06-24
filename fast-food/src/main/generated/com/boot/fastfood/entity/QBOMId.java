package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBOMId is a Querydsl query type for BOMId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBOMId extends BeanPath<BOMId> {

    private static final long serialVersionUID = 1044908629L;

    public static final QBOMId bOMId = new QBOMId("bOMId");

    public final StringPath itCode = createString("itCode");

    public final StringPath mtCode = createString("mtCode");

    public QBOMId(String variable) {
        super(BOMId.class, forVariable(variable));
    }

    public QBOMId(Path<? extends BOMId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBOMId(PathMetadata metadata) {
        super(BOMId.class, metadata);
    }

}

