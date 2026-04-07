package org.eduardomango.practicaspringweb.model.repositories;

import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository implements IRepository<ProductEntity> {

    private List<ProductEntity> products;

    public ProductRepository() {
        this.products = new ArrayList<>();

        products.add(new ProductEntity(1, "Laptop", 999.99, "Laptop con procesador Intel i7 y 16GB de RAM."));
        products.add(new ProductEntity(2, "Smartphone", 699.99, "Smartphone Android con pantalla OLED de 6.5 pulgadas."));
        products.add(new ProductEntity(3, "Teclado mecánico", 89.99, "Teclado con switches azules y retroiluminación RGB."));
        products.add(new ProductEntity(4, "Monitor 24''", 179.99, "Monitor LED Full HD de 24 pulgadas."));
        products.add(new ProductEntity(5, "Mouse gamer", 49.99, "Mouse óptico con DPI ajustable y luces RGB."));
        products.add(new ProductEntity(6, "Auriculares inalámbricos", 129.99, "Auriculares Bluetooth con cancelación de ruido."));
        products.add(new ProductEntity(7, "Tablet", 329.99, "Tablet de 10 pulgadas con 64GB de almacenamiento."));
        products.add(new ProductEntity(8, "Impresora", 149.99, "Impresora multifunción Wi-Fi."));
        products.add(new ProductEntity(9, "Silla gamer", 199.99, "Silla ergonómica con apoyabrazos ajustables."));
        products.add(new ProductEntity(10, "Webcam HD", 59.99, "Cámara web con micrófono incorporado."));
        products.add(new ProductEntity(11, "Disco SSD 1TB", 119.99, "Unidad de estado sólido de 1TB."));
        products.add(new ProductEntity(12, "Memoria RAM 16GB", 74.99, "Módulo DDR4 de 16GB."));
        products.add(new ProductEntity(13, "Router WiFi 6", 89.99, "Router inalámbrico de última generación."));
        products.add(new ProductEntity(14, "Smartwatch", 159.99, "Reloj inteligente compatible con Android e iOS."));
        products.add(new ProductEntity(15, "Cargador portátil", 39.99, "Powerbank de 20.000mAh."));
        products.add(new ProductEntity(16, "Lámpara LED USB", 14.99, "Lámpara de escritorio con conector USB."));
        products.add(new ProductEntity(17, "Hub USB", 24.99, "Adaptador USB con 4 puertos."));
        products.add(new ProductEntity(18, "Cámara de seguridad", 89.99, "Cámara IP con visión nocturna."));
        products.add(new ProductEntity(19, "Altavoz Bluetooth", 59.99, "Altavoz portátil con batería de larga duración."));
        products.add(new ProductEntity(20, "Micrófono condensador", 99.99, "Micrófono profesional para grabación."));
    }

    public List<ProductEntity> findAll() {
        return List.copyOf(products);
    }

    public void save(ProductEntity product) {
        products.add(product);
    }

    public void delete(ProductEntity product) {
        products.remove(product);
    }

    public void update(ProductEntity product) {
        int index = products.indexOf(product);
        products.set(index, product);
    }
}
