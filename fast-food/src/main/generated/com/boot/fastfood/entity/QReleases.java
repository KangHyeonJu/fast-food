package com.boot.fastfood.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReleases is a Querydsl query type for Releases
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReleases extends EntityPathBase<Releases> {

    private static final long serialVersionUID = 28797874L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReleases releases = new QReleases("releases");

    public final QEmployee employee;

    public final QMaterials materials;

    public final NumberPath<Integer> rsAmount = createNumber("rsAmount", Integer.class);

    public final StringPath rsCode = createString("rsCode");

    public final DatePath<java.time.LocalDate> rsDate = createDate("rsDate", java.time.LocalDate.class);

    public final QWarehousing warehousing;

    public final QWorks works;

    public QReleases(String variable) {
        this(Releases.class, forVariable(variable), INITS);
    }

    public QReleases(Path<? extends Releases> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReleases(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReleases(PathMetadata metadata, PathInits inits) {
        this(Releases.class, metadata, inits);
    }

    public QReleases(Class<? extends Releases> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.materials = inits.isInitialized("materials") ? new QMaterials(forProperty("materials"), inits.get("materials")) : null;
        this.warehousing = inits.isInitialized("warehousing") ? new QWarehousing(forProperty("warehousing"), inits.get("warehousing")) : null;
        this.works = inits.isInitialized("works") ? new QWorks(forProperty("works"), inits.get("works")) : null;
    }

}

