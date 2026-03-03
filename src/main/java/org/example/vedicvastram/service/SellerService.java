package org.example.vedicvastram.service;

import org.example.vedicvastram.dto.LabelValueTrend;
import org.example.vedicvastram.dto.NameValue;
import org.example.vedicvastram.dto.ProductDTO;
import org.example.vedicvastram.dto.SalesSeries;
import org.example.vedicvastram.dto.SellerAnalyticsResponse;
import org.example.vedicvastram.dto.SellerOrderDTO;
import org.example.vedicvastram.entity.Order;
import org.example.vedicvastram.entity.OrderItem;
import org.example.vedicvastram.entity.OrderStatus;
import org.example.vedicvastram.entity.Product;
import org.example.vedicvastram.entity.ProductImage;
import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.entity.User;
import org.example.vedicvastram.respository.OrderItemRepository;
import org.example.vedicvastram.respository.OrderRepository;
import org.example.vedicvastram.respository.ProductImageRepository;
import org.example.vedicvastram.respository.ProductRepository;
import org.example.vedicvastram.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductImageRepository imageRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    public String addProduct(ProductDTO dto, Long sellerId) {

        Product p = new Product();
        p.setTitle(dto.getTitle());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());
        p.setAvailableSizes(dto.getAvailableSizes());
        p.setColor(dto.getColor());
        p.setBrand(dto.getBrand());
        p.setFabric(dto.getFabric());
        p.setQuantity(dto.getQuantity());
        p.setSellerId(sellerId);
        p.setStatus(ProductStatus.PENDING);

        productRepo.save(p);

        List<String> urls = dto.getImageUrls() == null ? List.of() : dto.getImageUrls();
        urls.forEach(url -> {
            ProductImage img = new ProductImage();
            img.setProductId(p.getId());
            img.setUrl(url);
            imageRepo.save(img);
        });

        return "Product added. Awaiting admin approval.";
    }

    public List<Product> getMyProducts(Long sellerId) {
        return productRepo.findBySellerId(sellerId)
                .stream()
                .map(this::withImages)
                .toList();
    }

    public String updateProduct(Long id, ProductDTO dto, Long sellerId) {
        Product product = productRepo.findById(id).orElseThrow();
        if (!product.getSellerId().equals(sellerId)) {
            throw new RuntimeException("Not allowed to update this product");
        }

        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setAvailableSizes(dto.getAvailableSizes());
        product.setColor(dto.getColor());
        product.setBrand(dto.getBrand());
        product.setFabric(dto.getFabric());
        product.setQuantity(dto.getQuantity());
        product.setStatus(ProductStatus.PENDING);

        productRepo.save(product);

        imageRepo.deleteByProductId(product.getId());
        List<String> urls = dto.getImageUrls() == null ? List.of() : dto.getImageUrls();
        urls.forEach(url -> {
            ProductImage img = new ProductImage();
            img.setProductId(product.getId());
            img.setUrl(url);
            imageRepo.save(img);
        });

        return "Product updated.";
    }

    public List<SellerOrderDTO> getSellerOrders(Long sellerId) {
        List<Product> myProducts = productRepo.findBySellerId(sellerId);
        if (myProducts.isEmpty()) {
            return List.of();
        }

        List<Long> productIds = myProducts.stream().map(Product::getId).toList();
        Map<Long, Product> productMap = myProducts.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderItem> orderItems = orderItemRepo.findByProductIdIn(productIds);
        Set<Long> orderIds = orderItems.stream().map(OrderItem::getOrderId).collect(Collectors.toSet());

        Map<Long, Order> orders = orderRepo.findAllById(orderIds)
                .stream()
                .collect(Collectors.toMap(Order::getId, o -> o));

        Set<Long> buyerIds = orders.values().stream().map(Order::getBuyerId).collect(Collectors.toSet());
        Map<Long, User> buyers = userRepo.findAllById(buyerIds)
                .stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        List<SellerOrderDTO> result = new ArrayList<>();
        for (OrderItem item : orderItems) {
            Order order = orders.get(item.getOrderId());
            Product product = productMap.get(item.getProductId());
            if (order == null || product == null) {
                continue;
            }
            User buyer = buyers.get(order.getBuyerId());
            String customer = buyer != null ? buyer.getName() : "Customer";
            double amount = item.getPrice() * item.getQuantity();
            String status = order.getStatus() != null ? order.getStatus().name() : OrderStatus.PLACED.name();
            result.add(new SellerOrderDTO(order.getId(), product.getTitle(), customer, amount, status));
        }
        return result;
    }

    public String updateOrderStatus(Long orderId, OrderStatus status, Long sellerId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);
        List<Product> myProducts = productRepo.findBySellerId(sellerId);
        Set<Long> myProductIds = myProducts.stream().map(Product::getId).collect(Collectors.toSet());

        boolean ownsOrder = items.stream().anyMatch(i -> myProductIds.contains(i.getProductId()));
        if (!ownsOrder) {
            throw new RuntimeException("Not allowed to update this order");
        }

        order.setStatus(status);
        orderRepo.save(order);
        return "Order status updated.";
    }

    public List<LabelValueTrend> getDashboardStats(Long sellerId) {
        double totalSales = calculateTotalSales(sellerId);
        return List.of(
                new LabelValueTrend("Total Sales", String.format("$%.2f", totalSales), "+5%"),
                new LabelValueTrend("Pending Orders", String.valueOf(countOrdersByStatus(sellerId, OrderStatus.PLACED)), "+1%"),
                new LabelValueTrend("Delivered", String.valueOf(countOrdersByStatus(sellerId, OrderStatus.DELIVERED)), "+2%")
        );
    }

    public List<NameValue> getDashboardSales(Long sellerId) {
        return List.of(
                new NameValue("Mon", 1200.0),
                new NameValue("Tue", 900.0),
                new NameValue("Wed", 1500.0),
                new NameValue("Thu", 700.0),
                new NameValue("Fri", 1300.0),
                new NameValue("Sat", 800.0),
                new NameValue("Sun", 600.0)
        );
    }

    public List<NameValue> getTopProducts(Long sellerId) {
        List<Product> products = productRepo.findBySellerId(sellerId);
        return products.stream()
                .map(p -> new NameValue(p.getTitle(), p.getPrice() != null ? p.getPrice() : 0.0))
                .limit(5)
                .toList();
    }

    public SellerAnalyticsResponse getAnalytics(Long sellerId) {
        List<NameValue> weekSeries = getDashboardSales(sellerId);
        SalesSeries sales = new SalesSeries("Sales", weekSeries);
        return new SellerAnalyticsResponse(List.of(sales), getTopProducts(sellerId));
    }

    private Product withImages(Product product) {
        List<String> urls = imageRepo.findByProductId(product.getId())
                .stream()
                .map(ProductImage::getUrl)
                .toList();
        product.setImageUrls(urls);
        return product;
    }

    private double calculateTotalSales(Long sellerId) {
        List<Product> myProducts = productRepo.findBySellerId(sellerId);
        List<Long> productIds = myProducts.stream().map(Product::getId).toList();
        if (productIds.isEmpty()) {
            return 0.0;
        }
        return orderItemRepo.findByProductIdIn(productIds)
                .stream()
                .mapToDouble(oi -> oi.getPrice() * oi.getQuantity())
                .sum();
    }

    private long countOrdersByStatus(Long sellerId, OrderStatus status) {
        List<Product> myProducts = productRepo.findBySellerId(sellerId);
        List<Long> productIds = myProducts.stream().map(Product::getId).toList();
        if (productIds.isEmpty()) {
            return 0;
        }
        Set<Long> orderIds = orderItemRepo.findByProductIdIn(productIds)
                .stream()
                .map(OrderItem::getOrderId)
                .collect(Collectors.toSet());
        return orderRepo.findAllById(orderIds)
                .stream()
                .filter(o -> o.getStatus() == status)
                .count();
    }
}
