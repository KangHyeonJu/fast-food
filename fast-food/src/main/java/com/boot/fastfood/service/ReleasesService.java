package com.boot.fastfood.service;

import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReleasesService {

    private final ReleasesRepository releasesRepository;
    private final WorksRepository worksRepository;
    private final MaterialRepository materialRepository;
    private final EmployeeRepository employeeRepository;
    private final BOMRepository bomRepository;

    private final ProductionRepository productionRepository;
    public List<Releases> findAll() {
        return releasesRepository.findAll();
    }

    // 출고 등록 및 계산 로직 추가
    public void saveRelease(String wkCode, String emCode) {
        Works work = worksRepository.findByWkCode(wkCode);
        Optional<Employee> employeeOptional = employeeRepository.findByEmCode(emCode);


        if (work != null && employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Production production = productionRepository.findByPmCode(work.getProduction().getPmCode());

            List<BOM> boms = bomRepository.findByItems(production.getItName());

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String rsCodeBase = "RS" + currentTime.format(formatter);

            for (BOM bom : boms) {
                String rsCode = rsCodeBase + bom.getMaterials().getMtCode();

                Releases existingRelease = releasesRepository.findByWorksAndMaterials(work, bom.getMaterials());
                if (existingRelease != null) {
                    throw new IllegalArgumentException("이미 존재하는 작업 코드입니다.");
                }


                Releases release = new Releases();
                release.setWorks(work);
                release.setMaterials(bom.getMaterials());

                release.setRsAmount(RSAmount(production.getPmAmount(),production.getItName().getItEa()
                        , bom.getMaterials().getMtName()));

                release.setRsCode(rsCode);
                release.setEmployee(employee);
                release.setRsDate(LocalDate.now());

                Materials materials = bom.getMaterials();
                int newStock = materials.getMtStock() - release.getRsAmount();

                System.out.println("11111111111"+materials.getMtStock());
                System.out.println("22222222222"+release.getRsAmount());
                System.out.println("33333333333"+newStock);

                if (newStock < 0) {
                    throw new IllegalArgumentException("재고가 부족합니다");
                }
                materials.setMtStock(newStock);
                materialRepository.save(materials);

                releasesRepository.save(release);
            }
        } else {
            throw new IllegalArgumentException("해당 작업 코드나 직원 코드를 찾을 수 없습니다.");
        }
    }



    // 출고량 계산 메서드 추가 (재료 이름으로 구분)
    private int RSAmount(int pmAmount,int itEa , String mtName) {
        double releaseAmount = 0.0;
        switch (mtName) {
            case "양배추":
            case "흑마늘":
                releaseAmount = (((pmAmount * itEa) * 1.04) * 0.01 * 10 * 1.4);
                break;
            case "석류농축액":
            case "매실농축액":
                releaseAmount = (((pmAmount * itEa) * 1.04)*0.05);
                break;
            case "벌꿀":
                releaseAmount = (((pmAmount * itEa)  * 1.04) * 0.005);
                break;
            case "콜라겐":
                releaseAmount = (((pmAmount * itEa) * 1.04) * 0.002);
                break;
            case "포장지":
                releaseAmount = pmAmount * 1.04 * itEa;
                break;
            case "Box":
                releaseAmount = (double) pmAmount * 1.04;
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 재료입니다.");
        }

        // 올림 처리
        int roundedAmount = (int) Math.ceil(releaseAmount);

        return roundedAmount;
    }

}
