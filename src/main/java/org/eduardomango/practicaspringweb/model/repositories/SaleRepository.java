package org.eduardomango.practicaspringweb.model.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleRepository implements IRepository<SaleEntity> {

    private final List<SaleEntity> sales;

    public SaleRepository() {
        this.sales = new ArrayList<>();

        sales.add(new SaleEntity(1L, new ProductEntity(1, "Laptop", 999.99, "Laptop i7"), 1L, new UserEntity(1, "alice", "alice@example.com", "pass"), LocalDate.now().minusDays(1)));
        sales.add(new SaleEntity(2L, new ProductEntity(2, "Smartphone", 699.99, "OLED"), 2L, new UserEntity(2, "bob", "bob@example.com", "pass"), LocalDate.now().minusDays(2)));
        sales.add(new SaleEntity(3L, new ProductEntity(3, "Teclado mecánico", 89.99, "RGB"), 1L, new UserEntity(3, "charlie", "charlie@example.com", "pass"), LocalDate.now().minusDays(3)));
        sales.add(new SaleEntity(4L, new ProductEntity(5, "Mouse gamer", 49.99, "DPI"), 5L, new UserEntity(4, "diana", "diana@example.com", "pass"), LocalDate.now().minusDays(1)));
        sales.add(new SaleEntity(5L, new ProductEntity(7, "Tablet", 329.99, "10''"), 1L, new UserEntity(5, "edgar", "edgar@example.com", "pass"), LocalDate.now().minusDays(5)));
        sales.add(new SaleEntity(6L, new ProductEntity(10, "Webcam HD", 59.99, "Mic"), 2L, new UserEntity(6, "frank", "frank@example.com", "pass"), LocalDate.now().minusDays(4)));
        sales.add(new SaleEntity(7L, new ProductEntity(12, "Memoria RAM 16GB", 74.99, "DDR4"), 4L, new UserEntity(7, "grace", "grace@example.com", "pass"), LocalDate.now().minusDays(2)));
        sales.add(new SaleEntity(8L, new ProductEntity(15, "Cargador portátil", 39.99, "20Ah"), 1L, new UserEntity(8, "hannah", "hannah@example.com", "pass"), LocalDate.now().minusDays(6)));
        sales.add(new SaleEntity(9L, new ProductEntity(19, "Altavoz Bluetooth", 59.99, "Portátil"), 3L, new UserEntity(9, "ian", "ian@example.com", "pass"), LocalDate.now().minusDays(1)));
        sales.add(new SaleEntity(10L, new ProductEntity(20, "Micrófono condensador", 99.99, "Pro"), 1L, new UserEntity(10, "julia", "julia@example.com", "pass"), LocalDate.now().minusDays(7)));
        sales.add(new SaleEntity(11L, new ProductEntity(4, "Monitor 24''", 179.99, "LED"), 1L, new UserEntity(11, "kyle", "kyle@example.com", "pass"), LocalDate.now().minusDays(2)));
        sales.add(new SaleEntity(12L, new ProductEntity(6, "Auriculares inalámbricos", 129.99, "BT"), 2L, new UserEntity(12, "laura", "laura@example.com", "pass"), LocalDate.now().minusDays(3)));
        sales.add(new SaleEntity(13L, new ProductEntity(8, "Impresora", 149.99, "Wi-Fi"), 1L, new UserEntity(13, "michael", "michael@example.com", "pass"), LocalDate.now().minusDays(8)));
        sales.add(new SaleEntity(14L, new ProductEntity(11, "Disco SSD 1TB", 119.99, "SSD"), 2L, new UserEntity(14, "nina", "nina@example.com", "pass"), LocalDate.now().minusDays(1)));
        sales.add(new SaleEntity(15L, new ProductEntity(14, "Smartwatch", 159.99, "iOS/Android"), 1L, new UserEntity(15, "oscar", "oscar@example.com", "pass"), LocalDate.now().minusDays(4)));
    }

    public List<SaleEntity> findAll() {
        return List.copyOf(sales);
    }

    public void save(SaleEntity sale) {
        sales.add(sale);
    }

    public void delete(SaleEntity sale) {
        sales.remove(sale);
    }

    public void update(SaleEntity sale) {
        int index = sales.indexOf(sale);
        sales.set(index, sale);
    }
}
