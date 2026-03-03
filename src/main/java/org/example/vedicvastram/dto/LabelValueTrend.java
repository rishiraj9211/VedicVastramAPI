package org.example.vedicvastram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelValueTrend {
    private String label;
    private String value;
    private String trend;
}
