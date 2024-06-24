package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ContractRepository;
import com.boot.fastfood.service.ClientService;
import com.boot.fastfood.service.ContractService;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/contract")
    public String contract(Model model) {
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts", contracts);

        // 다른 필요한 데이터들도 함께 모델에 추가
        List<Clients> clients = clientService.getAllClients();
        List<Items> items = itemsService.findAll();
        List<Employee> employees = employeeService.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("items", items);
        model.addAttribute("employees", employees);

        return "contract/Contract"; // 초기에 모든 수주가 보이는 페이지로 이동
    }

    @GetMapping("/searchcontract")
    public String searchContracts(@RequestParam(name = "ctCode", required = false) String ctCode,
                                  @RequestParam(name = "clName", required = false) String clName,
                                  @RequestParam(name = "ctDate", required = false) LocalDate ctDate,
                                  @RequestParam(name = "itName", required = false) String itName,
                                  @RequestParam(name = "emName", required = false) String emName,
                                  Model model) {

        // 검색 조건을 객체에 설정
        ContractSearchDto searchDto = new ContractSearchDto(ctCode, clName, ctDate, itName, emName);

        // 검색 결과 조회
        List<Contract> searchcontracts = contractService.searchContracts(searchDto);

        // 검색 결과와 검색 조건을 모델에 추가
        model.addAttribute("contracts", searchcontracts);
        model.addAttribute("searchDto", searchDto);

        // 다른 필요한 데이터들도 함께 모델에 추가
        List<Clients> clients = clientService.getAllClients();
        List<Items> items = itemsService.findAll();
        List<Employee> employees = employeeService.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("items", items);
        model.addAttribute("employees", employees);

        return "contract/Contract"; // 검색 결과가 보이는 페이지로 이동
    }

    @PostMapping("/contract/add")
    public String addContract(@ModelAttribute ContractDto contractDto, Model model) {
        try {

            contractService.saveContract(contractDto); // 수주 저장
            model.addAttribute("message", "수주가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "수주 등록 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/contract"; // 수주 관리 페이지로 리다이렉트
    }

}