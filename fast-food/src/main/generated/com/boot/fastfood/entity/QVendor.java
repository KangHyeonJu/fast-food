package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = -1373660882L;

    public static final QVendor vendor = new QVendor("vendor");

    public final NumberPath<Integer> alAmount = createNumber("alAmount", Integer.class);

    public final ListPath<Materials, QMaterials> materials = this.<Materials, QMaterials>createList("materials", Materials.class, QMaterials.class, PathInits.DIRECT2);

    public final StringPath vdCode = createString("vdCode");

    public final StringPath vdName = createString("vdName");

    public QVendor(String variable) {
        super(Vendor.class, forVariable(variable));
    }

    public QVendor(Path<? extends Vendor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVendor(PathMetadata metadata) {
        super(Vendor.class, metadata);
    }

}

