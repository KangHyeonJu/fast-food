package com.boot.fastfood.controller;


import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.MaterialRepository;
import com.boot.fastfood.repository.OrdersRepository;
import com.boot.fastfood.repository.WarehousingRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.MaterialService;
import com.boot.fastfood.service.VendorService;
import com.boot.fastfood.service.WarehousingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MaterialController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MaterialService materialService;

    @Autowired
    private WarehousingService warehousingService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private WarehousingRepository warehousingRepository;
    @Autowired
    private EmployeeService employeeService;


    //자재관리
    @GetMapping("/order")
    public String contract() {
        return "material/Order";
    }

    @GetMapping("/warehousing")
    public String Warehousing(Model model) {

        return "material/Warehousing";
    }

    @GetMapping("/release")
    public String Release() {
        return "material/Release";
    }
    @GetMapping("/material")
    public String getMaterialPage(Model model) {

        // 모든 입고 목록을 가져와서 모델에 추가
        List<Warehousing> warehousings = warehousingService.findAll();
        model.addAttribute("warehousings", warehousings);

        // 모든 자재 목록을 가져와서 모델에 추가
        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        // 모든 거래처 목록을 가져와서 모델에 추가
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        // 모든 주문 목록을 가져와서 모델에 추가
        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);

        List<Orders> todayOrders = ordersRepository.findByOdDueDate(LocalDate.now());
        model.addAttribute("todayOrders", todayOrders);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "material/Material";  // HTML 템플릿 파일 이름
    }
    @PostMapping("/material")
    public String updateWhStatus(@RequestParam("odCode") String odCode, @RequestParam("emName") String emName) {
        Orders order = ordersRepository.findByOdCode(odCode);
        System.out.println("11111111"+odCode);
        if (order != null) {
            order.setWhStatus(1);
            ordersRepository.save(order);
            System.out.println("222222222");
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String whCode = "OD" + currentTime.format(formatter);

            Warehousing warehousing = new Warehousing();
            warehousing.setOrders(order);
            warehousing.setWhDate(LocalDate.now());
            warehousing.setWhCode(whCode);

            Optional<Employee> employeeOptional = employeeService.findByEmName(emName);


            if (employeeOptional.isPresent()) {
                warehousing.setEmployee(employeeOptional.get());
                warehousingRepository.save(warehousing);
            } else {
                throw new IllegalArgumentException(emName);
            }
        }
        return "redirect:/material";
    }

    @GetMapping("/searchMaterial")
    public String getSearchMaterial(@RequestParam(required = false, name = "odCode") String odCode,
                                    @RequestParam(required = false, name = "mtName") String mtName,
                                    @RequestParam(required = false, name = "odDueDate") LocalDate odDueDate,
                                    Model model) {

        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        List<Orders> orders;
        if (odCode != null && !odCode.isEmpty()) {
            orders = (List<Orders>) ordersRepository.findByOdCode(odCode);
        } else if (mtName != null && !mtName.isEmpty()) {
            orders = ordersRepository.findByMaterials_MtName(mtName);
        } else if (odDueDate != null) {
            orders = ordersRepository.findByOdDueDate(odDueDate);
        } else {
            orders = ordersRepository.findAll();
        }

        // 입고 예정 자재만 필터링
        orders = orders.stream()
                .filter(order -> order.getWhStatus() == 0)
                .collect(Collectors.toList());

        model.addAttribute("orders", orders);

        // 금일 자재 입고 등록을 위한 정보 추가
        List<Orders> todayOrders = ordersRepository.findByOdDueDate(LocalDate.now());
        model.addAttribute("todayOrders", todayOrders);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "material/Material";
    }


    @GetMapping("/order_plan")
    public String order_plan() {
        return "material/Order_plan";
    }
}
