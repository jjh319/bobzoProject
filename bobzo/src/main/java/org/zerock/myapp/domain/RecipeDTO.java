package org.zerock.myapp.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class RecipeDTO {
    private Long num;
    private Long writer;
    private String title;
    private String content;
    private Date createDate;
    private Date alterDate;
    private String Categories;
    private Date processedDate;
    private ReportStatus reportStatus = ReportStatus.REPORT_NOTRECEIVED;
    private Integer enabled = 1;
    private Integer recipeNumber;
    private Integer cnt = 0;


} // end class
