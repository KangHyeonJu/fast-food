package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -1562356021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final QEmployee employee;

    public final QMaterials materials;

    public final NumberPath<Integer> odAmount = createNumber("odAmount", Integer.class);

    public final StringPath odCode = createString("odCode");

    public final DatePath<java.time.LocalDate> odDate = createDate("odDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> odDueDate = createDate("odDueDate", java.time.LocalDate.class);

    public final BooleanPath odState = createBoolean("odState");

    public final QProduction production;

    public final NumberPath<Integer> whStatus = createNumber("whStatus", Integer.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.materials = inits.isInitialized("materials") ? new QMaterials(forProperty("materials"), inits.get("materials")) : null;
        this.production = inits.isInitialized("production") ? new QProduction(forProperty("production"), inits.get("production")) : null;
    }

}

