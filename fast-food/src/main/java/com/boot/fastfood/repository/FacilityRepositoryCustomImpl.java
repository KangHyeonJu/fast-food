package com.boot.fastfood.repository;

import com.boot.fastfood.constant.FcStatus;
import com.boot.fastfood.dto.FacilitySearchDto;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.QFacility;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FacilityRepositoryCustomImpl implements FacilityRepositoryCustom{
    private final JPAQueryFactory query;

    private final QFacility facility = QFacility.facility;

    private BooleanExpression searchStatus(FcStatus searchStatus){
        return searchStatus == null ? null : facility.fcStatus.eq(searchStatus);
    }

    private BooleanExpression searchByFcCode(String fcCode){
        if(fcCode == null){
            return null;
        }
        return facility.fcCode.like("%" + fcCode + "%");
    }

    private BooleanExpression searchByFcName(String fcName){
        if(fcName == null){
            return null;
        }
        return facility.fcName.like("%" + fcName + "%");
    }

    @Override
    public List<Facility> getFacilityList(FacilitySearchDto facilitySearchDto) {
        return query
                .selectFrom(facility)
                .where(searchByFcCode(facilitySearchDto.getFcCode()), searchByFcName(facilitySearchDto.getFcName()), searchStatus(facilitySearchDto.getSearchStatus()))
                .fetch();
    }
}
