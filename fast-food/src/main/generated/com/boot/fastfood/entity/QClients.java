package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClients is a Querydsl query type for Clients
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClients extends EntityPathBase<Clients> {

    private static final long serialVersionUID = 879300322L;

    public static final QClients clients = new QClients("clients");

    public final NumberPath<Long> clAmount = createNumber("clAmount", Long.class);

    public final StringPath clCode = createString("clCode");

    public final StringPath clName = createString("clName");

    public final StringPath clPhone = createString("clPhone");

    public final StringPath clType = createString("clType");

    public QClients(String variable) {
        super(Clients.class, forVariable(variable));
    }

    public QClients(Path<? extends Clients> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClients(PathMetadata metadata) {
        super(Clients.class, metadata);
    }

}

