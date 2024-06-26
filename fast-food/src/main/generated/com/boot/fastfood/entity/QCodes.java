package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodes is a Querydsl query type for Codes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodes extends EntityPathBase<Codes> {

    private static final long serialVersionUID = 1046808448L;

    public static final QCodes codes = new QCodes("codes");

    public final StringPath cCode = createString("cCode");

    public final StringPath cName = createString("cName");

    public final NumberPath<Long> cNo = createNumber("cNo", Long.class);

    public final BooleanPath cState = createBoolean("cState");

    public QCodes(String variable) {
        super(Codes.class, forVariable(variable));
    }

    public QCodes(Path<? extends Codes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodes(PathMetadata metadata) {
        super(Codes.class, metadata);
    }

}

