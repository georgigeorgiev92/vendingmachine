package com.example.vendingmachine;

import com.example.vendingmachine.controller.VendingController;
import com.example.vendingmachine.entities.Product;
import com.example.vendingmachine.entities.VendingMachine;
import com.example.vendingmachine.repos.CoinRepo;
import com.example.vendingmachine.repos.ProductRepo;
import com.example.vendingmachine.repos.VendingMachineRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@EnableJpaRepositories(basePackages = {"com.example.vendingmachine.repos*"})
@RunWith(SpringRunner.class)
@WebMvcTest(VendingController.class)
@Configuration
@EnableAutoConfiguration
class VendingmachineApplicationTests {

/*    @PersistenceContext
    private EntityManager em;*/
    @Autowired
    private final ProductRepo productRepo;

    @Autowired
    private final VendingMachineRepo vendingRepo;
    @Autowired
    private final CoinRepo coinRepo;

    @MockBean
    private VendingController controller;

    VendingmachineApplicationTests(ProductRepo productRepo, VendingMachineRepo vendingRepo, CoinRepo coinRepo) {
        this.productRepo = productRepo;
        this.vendingRepo = vendingRepo;
        this.coinRepo = coinRepo;
    }


    @Test
    void contextLoads() {
    }

    @SpringBootTest
    public class SmokeTest {

        @Test
        public void contextLoads() throws Exception {
            assertThat(controller).isNotNull();
        }
    }

    @Test
    public void testAddMachine() {
        VendingMachine vm = new VendingMachine("vm1", 0);
        ResponseEntity<?> result = controller.addMachine(vm);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1, vendingRepo.count());
    }

    @Test
    public void testAddMachineWithInvalidInput() {
        VendingMachine vm = new VendingMachine("", 0);
        ResponseEntity<?> result = controller.addMachine(vm);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(0, vendingRepo.count());
    }
    @Test
    public void testBuyProduct() {
        VendingMachine vm = new VendingMachine("vm1", 10);
        vendingRepo.save(vm);
        Product product = new Product(vm, "product1", 5, 2);
        productRepo.save(product);

        Product result = controller.buyProduct("vm1", product.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(1, (long) productRepo.findById(product.getId()).get().getQuantity());
        assertEquals(5, (long) vendingRepo.findByName("vm1").get().getCurrentAmount());
    }
}
