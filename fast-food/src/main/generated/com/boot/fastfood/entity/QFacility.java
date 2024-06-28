package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFacility is a Querydsl query type for Facility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacility extends EntityPathBase<Facility> {

    private static final long serialVersionUID = 1081213193L;

    public static final QFacility facility = new QFacility("facility");

    public final StringPath cycleHour = createString("cycleHour");

    public final StringPath fcCapa = createString("fcCapa");

    public final StringPath fcCode = createString("fcCode");

    public final DatePath<java.time.LocalDate> fcDate = createDate("fcDate", java.time.LocalDate.class);

    public final StringPath fcName = createString("fcName");

    public final StringPath fcPreTime = createString("fcPreTime");

    public final StringPath fcStatus = createString("fcStatus");

    public QFacility(String variable) {
        super(Facility.class, forVariable(variable));
    }

    public QFacility(Path<? extends Facility> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFacility(PathMetadata metadata) {
        super(Facility.class, metadata);
    }

}

