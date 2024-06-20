package com.boot.fastfood.controller;


import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.MaterialRepository;
import com.boot.fastfood.repository.OrdersRepository;
import com.boot.fastfood.repository.WarehousingRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.MaterialService;
import com.boot.fastfood.service.VendorService;
import com.boot.fastfood.service.WarehousingService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MaterialController {

    private final VendorService vendorService;

    private final MaterialService materialService;


    private final WarehousingService warehousingService;


    private final OrdersRepository ordersRepository;


    private final WarehousingRepository warehousingRepository;

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;


    //자재관리
    @GetMapping("/order")
    public String contract() {
        return "material/Order";
    }

    @GetMapping("/warehousing")
    public String Warehousing(Model model) {

        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        List<Warehousing> warehousings = warehousingService.findAll();
        model.addAttribute("warehousings", warehousings);

        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);

        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        return "material/Warehousing";
    }

    @GetMapping("/searchWarehousing")
    public String searchWarehousing(
            @RequestParam(required = false, name = "odCode") String odCode,
            @RequestParam(required = false, name = "vdName") String vdName,
            @RequestParam(required = false, name = "whDate") LocalDate whDate,
            @RequestParam(required = false, name = "mtName") String mtName,
            @RequestParam(required = false, name = "emName") String emName,
            Model model) {

        List<Warehousing> warehousings = warehousingService.findAll();

        // 필터링 조건에 따라 검색 처리
        if (odCode != null && !odCode.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getOrders().getOdCode().contains(odCode))
                    .collect(Collectors.toList());
        }

        if (vdName != null && !vdName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getVendor().getVdName().contains(vdName))
                    .collect(Collectors.toList());
        }

        if (whDate != null) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getWhDate().equals(whDate))
                    .collect(Collectors.toList());
        }

        if (mtName != null && !mtName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getMaterials().getMtName().contains(mtName))
                    .collect(Collectors.toList());
        }

        if (emName != null && !emName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getEmployee().getEmName().contains(emName))
                    .collect(Collectors.toList());
        }

        model.addAttribute("warehousings", warehousings);
        model.addAttribute("materials", materialService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        return "material/Warehousing";  // HTML 템플릿 파일 이름
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
    public String updateWhStatus(@RequestParam("odCode") String odCode, @RequestParam("emName") String emName,
                                 @RequestParam("mtCode") String mtCode, @RequestParam("vdCode") String vdCode){
        Orders order = ordersRepository.findByOdCode(odCode);
        if (order != null) {
            order.setWhStatus(1);
            ordersRepository.save(order);
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String whCode = "OD" + currentTime.format(formatter);

            Warehousing warehousing = new Warehousing();
            warehousing.setOrders(order);
            warehousing.setWhDate(LocalDate.now());
            warehousing.setWhCode(whCode);


//            Optional<Employee> employeeOptional = employeeService.findByEmName(emName);

            Employee employee = employeeRepository.findByEmName(emName);

            warehousing.setEmployee(employee);

//            if (employeeOptional.isPresent()) {
//                warehousing.setEmployee(employeeOptional.get());
//            } else {
//                throw new IllegalArgumentException(emName);
//            }

            Optional<Vendor> VendorOptional = vendorService.findByVdCode(vdCode);

            if (VendorOptional.isPresent()) {
                warehousing.setVendor(VendorOptional.get());
            } else {
                throw new IllegalArgumentException(vdCode);
            }

            Optional<Materials> MaterialsOptional = materialService.findByMtCode(mtCode);

            if (MaterialsOptional.isPresent()) {
                warehousing.setMaterials(MaterialsOptional.get());
            } else {
                throw new IllegalArgumentException(mtCode);
            }

            warehousingRepository.save(warehousing);

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
