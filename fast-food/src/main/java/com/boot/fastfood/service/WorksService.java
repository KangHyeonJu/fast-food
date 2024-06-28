package com.boot.fastfood.service;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.FacilityRepository;
import com.boot.fastfood.repository.WorksRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorksService {
    private final WorksRepository worksRepository;
    private final FacilityRepository facilityRepository;

    public List<Works> findAll() {
        return worksRepository.findAll();
    }


    public List<Works> findByProductionPmCode(String pmCode) {

        return worksRepository.findByProductionPmCode(pmCode);
    }
    
    public void saveRSDate(Works works, Employee employee){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        works.setRSDate(time);
        works.setEmployee(employee);
        worksRepository.save(works);
    }

    public void saveREDate(Works works){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        works.setREDate(time);
        worksRepository.save(works);
    }

    public void setFCWork(String wkCode, String itName){
        if(wkCode.contains("A3")){
            if(itName.contains("양배추")){
                Facility facility = facilityRepository.findByFcCode("fc003");
                facility.setFcStatus("WORK");
            }else if(itName.contains("흑마늘")){
                Facility facility = facilityRepository.findByFcCode("fc004");
                facility.setFcStatus("WORK");
            }
        }else if(wkCode.contains("A4")){
            Facility facility = facilityRepository.findByFcCode("fc008");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("A5")){
            Facility facility = facilityRepository.findByFcCode("fc005");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("A6")){
            Facility facility = facilityRepository.findByFcCode("fc001");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("A7") || wkCode.contains("B6")){
            Facility facility = facilityRepository.findByFcCode("fc010");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("A8") || wkCode.contains("B7")){
            Facility facility = facilityRepository.findByFcCode("fc009");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("B2")){
            Facility facility = facilityRepository.findByFcCode("fc007");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("B3")){
            Facility facility = facilityRepository.findByFcCode("fc006");
            facility.setFcStatus("WORK");
        }else if(wkCode.contains("B4")){
            Facility facility = facilityRepository.findByFcCode("fc002");
            facility.setFcStatus("WORK");
        }
    }

    public void setFCStop(String wkCode, String itName){
        if(wkCode.contains("A3")){
            if(itName.contains("양배추")){
                Facility facility = facilityRepository.findByFcCode("fc003");
                facility.setFcStatus("STOP");
            }else if(itName.contains("흑마늘")){
                Facility facility = facilityRepository.findByFcCode("fc004");
                facility.setFcStatus("STOP");
            }
        }else if(wkCode.contains("A4")){
            Facility facility = facilityRepository.findByFcCode("fc008");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("A5")){
            Facility facility = facilityRepository.findByFcCode("fc005");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("A6")){
            Facility facility = facilityRepository.findByFcCode("fc001");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("A7") || wkCode.contains("B6")){
            Facility facility = facilityRepository.findByFcCode("fc010");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("A8") || wkCode.contains("B7")){
            Facility facility = facilityRepository.findByFcCode("fc009");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("B2")){
            Facility facility = facilityRepository.findByFcCode("fc007");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("B3")){
            Facility facility = facilityRepository.findByFcCode("fc006");
            facility.setFcStatus("STOP");
        }else if(wkCode.contains("B4")){
            Facility facility = facilityRepository.findByFcCode("fc002");
            facility.setFcStatus("STOP");
        }
    }

}
