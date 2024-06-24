package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItems is a Querydsl query type for Items
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItems extends EntityPathBase<Items> {

    private static final long serialVersionUID = 1052499738L;

    public static final QItems items = new QItems("items");

    public final StringPath itCode = createString("itCode");

    public final NumberPath<Integer> itEa = createNumber("itEa", Integer.class);

    public final StringPath itName = createString("itName");

    public final NumberPath<Integer> itStock = createNumber("itStock", Integer.class);

    public final StringPath itType = createString("itType");

    public QItems(String variable) {
        super(Items.class, forVariable(variable));
    }

    public QItems(Path<? extends Items> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItems(PathMetadata metadata) {
        super(Items.class, metadata);
    }

}

