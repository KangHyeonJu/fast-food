package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWarehousing is a Querydsl query type for Warehousing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehousing extends EntityPathBase<Warehousing> {

    private static final long serialVersionUID = -1121632614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWarehousing warehousing = new QWarehousing("warehousing");

    public final QEmployee employee;

    public final QMaterials materials;

    public final QOrders orders;

    public final QVendor vendor;

    public final StringPath whCode = createString("whCode");

    public final DatePath<java.time.LocalDate> whDate = createDate("whDate", java.time.LocalDate.class);

    public QWarehousing(String variable) {
        this(Warehousing.class, forVariable(variable), INITS);
    }

    public QWarehousing(Path<? extends Warehousing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWarehousing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWarehousing(PathMetadata metadata, PathInits inits) {
        this(Warehousing.class, metadata, inits);
    }

    public QWarehousing(Class<? extends Warehousing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.materials = inits.isInitialized("materials") ? new QMaterials(forProperty("materials"), inits.get("materials")) : null;
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

