package org.example.vedicvastram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
    private Long totalUsers;
    private Long activeUsers;
    private Long pendingProducts;
    private Long ordersToday;
    private List<LabelValueTrend> kpis;
    private List<TopSellerDTO> topSellers;
    private List<TopProductDTO> topProducts;
    private List<AlertDTO> alerts;
    private List<SalesSeries> orderTrend;
}
