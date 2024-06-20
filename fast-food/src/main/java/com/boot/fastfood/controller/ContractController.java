package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ContractDto;
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

import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContractRepository contractRepository;


    @GetMapping("/contract")
    public String contract(Model model) {
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts" , contracts);

        List<Clients> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);

        List<Items> items = itemService.findAll();
        model.addAttribute("items", items);

        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employee", employees);

        return "contract/Contract";
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