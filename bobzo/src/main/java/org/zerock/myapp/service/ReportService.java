package org.zerock.myapp.service;


import org.zerock.myapp.domain.Report;
import org.zerock.myapp.domain.ReportDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReportService {

    public abstract void addReport(Report report);

    public abstract void reportWrite(ReportDTO reportDTO, String LoggedInUserId);

    public abstract List<Report> getAllReports();

    public abstract Report getReportByNum(Long num);

    public abstract List<Report> getFindAllByOrderByCreateDateDesc();

    public abstract Integer findHighestReportNumber();

    public abstract void addReportWithCategoryAndReference(ReportDTO reportDTO, String LoggedInUserId, Long recipeNum, Long commentsNum);

} // end interface
