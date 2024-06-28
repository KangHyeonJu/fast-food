package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractRepositoryImpl extends ContractRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Contract> searchContracts(ContractSearchDto searchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contract> query = criteriaBuilder.createQuery(Contract.class);
        Root<Contract> root = query.from(Contract.class);

        List<Predicate> predicates = new ArrayList<>();

        // 조회 조건 추가 예시 - 필드가 null이 아닐 경우에만 조건을 추가하도록 설정
        if (searchDto.getCtCode() != null && !searchDto.getCtCode().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("ctCode"), searchDto.getCtCode()));
        }

        if (searchDto.getClName() != null && !searchDto.getClName().isEmpty()) {
            Join<Contract, Clients> clientsJoin = root.join("clients");
            predicates.add(criteriaBuilder.equal(clientsJoin.get("clName"), searchDto.getClName()));
        }

        // LocalDate를 다루는 방법에 따라 조건을 추가할 수 있습니다.
        if (searchDto.getCtDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("ctDate"), searchDto.getCtDate()));
        }

        if (searchDto.getItName() != null && !searchDto.getItName().isEmpty()) {
            Join<Contract, Items> itemsJoin = root.join("items");
            predicates.add(criteriaBuilder.equal(itemsJoin.get("itName"), searchDto.getItName()));
        }

        if (searchDto.getEmName() != null && !searchDto.getEmName().isEmpty()) {
            Join<Contract, Employee> employeeJoin = root.join("employee");
            predicates.add(criteriaBuilder.equal(employeeJoin.get("emName"), searchDto.getEmName()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

}
