package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProcess is a Querydsl query type for Process
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcess extends EntityPathBase<Process> {

    private static final long serialVersionUID = -290805943L;

    public static final QProcess process = new QProcess("process");

    public final ListPath<Facility, QFacility> facilities = this.<Facility, QFacility>createList("facilities", Facility.class, QFacility.class, PathInits.DIRECT2);

    public final StringPath pcCnt = createString("pcCnt");

    public final StringPath pcCode = createString("pcCode");

    public final StringPath pcName = createString("pcName");

    public QProcess(String variable) {
        super(Process.class, forVariable(variable));
    }

    public QProcess(Path<? extends Process> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProcess(PathMetadata metadata) {
        super(Process.class, metadata);
    }

}

