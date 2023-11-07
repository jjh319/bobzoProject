package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.Report;
import org.zerock.myapp.domain.ReportDTO;
import org.zerock.myapp.service.ReportService;
import org.zerock.myapp.service.ReportServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Log4j2
@NoArgsConstructor


@RequestMapping("/help/*")
@Controller
public class ReportController {

    @Setter(onMethod_ = @Autowired)
    private ReportService reportService;




    @GetMapping("/help")
    void goHelp(){
        log.trace("goHelp() invoked.");

    } // goHelp


    @GetMapping("/report/report")
    public String listReports(Model model) {
        List<Report> reports = reportService.getFindAllByOrderByCreateDateDesc();

        model.addAttribute("reports",reports);

        return "/help/report/report";
    } // listReports

    @GetMapping("/report/register")
    public String write(Model model) {
        model.addAttribute(("reportDTO"), new ReportDTO());

        return "help/report/register";
    } // write

    @PostMapping("/report/createReport")
    public String createReport(
            @ModelAttribute("reportDTO") ReportDTO reportDTO, Principal principal,
            @RequestParam(required = false) Long recipeNum,
            @RequestParam(required = false) Long commentsNum
            ) {

        if(principal != null) {
            String LoggedInUserId = principal.getName();

            reportService.addReportWithCategoryAndReference(reportDTO,LoggedInUserId,recipeNum,commentsNum);
        } // if


        return "redirect:/help/report/report";

    } // createReport

    @GetMapping("/report/detail/{num}")
    public String reportDetail(@PathVariable Long num, Model model) {
        Report report = this.reportService.getReportByNum(num);
        model.addAttribute("report", report);

        return "help/report/detail";
    } // reportDetail



} // end class
