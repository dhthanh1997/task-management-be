package com.ansv.taskmanagement.dto.excel;

import com.ansv.taskmanagement.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XlsxSection {
    @XlsxSingleField(columnIndex = 0, name = "no")
    private Long no;

    @XlsxSingleField(columnIndex = 1, name = "sectionName")
    private String sectionName;
}
