package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.entities.product.entity.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.sale.dtos.SaleRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.sale.entity.SaleEntity;
import org.eduardomango.practicaspringweb.model.entities.user.entity.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {
    private final IRepository<SaleEntity> saleRepository;
    private final ProductService productService;
    private final UserService userService;

    // Inyeccion por constructor
    public SaleService(IRepository<SaleEntity> saleRepository, ProductService productService, UserService userService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.userService = userService;
    }

    /// ============================ CREATE =========================== ///
    public SaleEntity createSale(Long productId, Long userId, Long quantity) {
        // productService y userService ya lanzan sus propias excepciones si no existen
        ProductEntity product = productService.findById(productId);
        UserEntity client = userService.findById(userId);

        // Genero un ID autoincremental buscando el máximo actual
        Long newId = saleRepository.findAll().stream()
                .mapToLong(SaleEntity::getId)
                .max()
                .orElse(0L) + 1; // 0L avisa que cero represetan un dato del tipo Long

        SaleEntity newSale = SaleEntity.builder()
                .id(newId)
                .products(product)
                .client(client)
                .quantity(quantity)
                .saleDate(LocalDate.now())
                .build();

        saleRepository.save(newSale);
        return newSale;
    }

    /// ============================ READ =========================== ///
    public List<SaleEntity> findAll() {
        return saleRepository.findAll();
    }

    public SaleEntity findById(Long id) {
        return saleRepository.findAll()
                .stream()
                .filter(sale -> sale.getId().equals(id))
                .findFirst()
                .orElseThrow(SaleNotFoundException::new);
    }

    /// ============================ UPDATE =========================== ///
    public SaleEntity update(Long id, SaleRequestDTO request) {
        //Validamos que la venta a modificar exista
        SaleEntity existingSale = this.findById(id);

        //Validamos que los nuevos IDs de producto y cliente existan
        ProductEntity newProduct = productService.findById(request.getProductId());
        UserEntity newClient = userService.findById(request.getUserId());

        //Solo actualizamos los campos permitidos que no queremos que el usuario pueda tocar
        existingSale.setProducts(newProduct);
        existingSale.setClient(newClient);
        existingSale.setQuantity(request.getQuantity());

        saleRepository.update(existingSale);

        return existingSale;
    }

    /// ============================ DELETE =========================== ///
    public void delete(Long id) {
        SaleEntity existingSale = this.findById(id);
        saleRepository.delete(existingSale);
    }
}
