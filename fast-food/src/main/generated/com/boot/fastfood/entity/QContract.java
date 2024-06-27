package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContract is a Querydsl query type for Contract
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContract extends EntityPathBase<Contract> {

    private static final long serialVersionUID = 13149048L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContract contract = new QContract("contract");

    public final QClients clients;

    public final NumberPath<Integer> ctAmount = createNumber("ctAmount", Integer.class);

    public final StringPath ctCode = createString("ctCode");

    public final DatePath<java.time.LocalDate> ctDate = createDate("ctDate", java.time.LocalDate.class);

    public final StringPath ctStatus = createString("ctStatus");

    public final DatePath<java.time.LocalDate> deliveryDate = createDate("deliveryDate", java.time.LocalDate.class);

    public final StringPath deliveryPlace = createString("deliveryPlace");

    public final QEmployee employee;

    public final QItems items;

    public QContract(String variable) {
        this(Contract.class, forVariable(variable), INITS);
    }

    public QContract(Path<? extends Contract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContract(PathMetadata metadata, PathInits inits) {
        this(Contract.class, metadata, inits);
    }

    public QContract(Class<? extends Contract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clients = inits.isInitialized("clients") ? new QClients(forProperty("clients")) : null;
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.items = inits.isInitialized("items") ? new QItems(forProperty("items")) : null;
    }

}

