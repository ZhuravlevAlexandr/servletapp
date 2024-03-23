package services;

import dao.CustomerDAO;
import dao.OrderDAO;
import dto.CustomerDto;
import entities.Customer;
import entities.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;

public class CustomerServiceTest {

    private CustomerDAO customerDao;
    private OrderDAO orderDao;

    private CustomerService customerService;

    private final int CUSTOMER_ID = 777;
    private final String CUSTOMER_NAME = "Alex";
    private final String ORDER_NAME = "laptop";
    private final CustomerDto CUSTOMER_DTO = new CustomerDto(CUSTOMER_ID, CUSTOMER_NAME, null, null);

    @BeforeEach
    public void setUp() {
        initService();
    }

    private void initService() {
        customerService = new CustomerService();

        customerDao = Mockito.mock(CustomerDAO.class);
        ReflectionTestUtils.setField(customerService, "customerDAO", customerDao);

        orderDao = Mockito.mock(OrderDAO.class);
        ReflectionTestUtils.setField(customerService, "orderDAO", orderDao);
    }

    @Test
    public void givenCustomerDto_whenCreate_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод create().
        customerService.create(CUSTOMER_DTO);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(customerDao, Mockito.times(1)).create(any());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(customerDao);
    }

    @Test
    public void givenCustomerIdAndCustomerDto_whenUpdate_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод update().
        customerService.update(CUSTOMER_ID, CUSTOMER_DTO);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(customerDao, Mockito.times(1)).update(anyInt(), any());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(customerDao);
    }

    @Test
    public void givenCustomerId_whenDelete_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод delete().
        customerService.delete(CUSTOMER_ID);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(customerDao, Mockito.times(1)).delete(anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(customerDao);
    }

    @Test
    public void givenCustomerId_whenGet_thenReturnNull() throws SQLException {
        Mockito.when(customerDao.get(anyInt())).thenReturn(null);

        // Вызываем тестируемый модуль - метод get().
        CustomerDto actualCustomer = customerService.get(CUSTOMER_ID);

        // Проверяем, что было только по одному вызову к каждому дао.
        Mockito.verify(customerDao, Mockito.times(1)).get(anyInt());
        Mockito.verify(orderDao, Mockito.times(1)).getAllByCustomerId(anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(customerDao);
        Mockito.verifyNoMoreInteractions(orderDao);

        // Проверяем, что вернулся null
        Assertions.assertNull(actualCustomer);
    }

    @Test
    public void givenCustomerId_whenGet_thenReturnCustomerDtoWithOrders() throws SQLException {
        // Собираем сущность customer, которая будет возвращена при обращении к дао.
        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(CUSTOMER_ID);
        expectedCustomer.setName(CUSTOMER_NAME);

        // Собираем лист сущностей order, который будет возвращен при обращении к дао.
        Order expectedOrder = new Order();
        expectedOrder.setName(ORDER_NAME);
        List<Order> expectedOrders = Collections.singletonList(expectedOrder);

        Mockito.when(customerDao.get(anyInt())).thenReturn(expectedCustomer);
        Mockito.when(orderDao.getAllByCustomerId(anyInt())).thenReturn(expectedOrders);

        // Вызываем тестируемый модуль - метод get().
        CustomerDto actualCustomer = customerService.get(CUSTOMER_ID);

        // Проверяем, что было только по одному вызову к каждому дао.
        Mockito.verify(customerDao, Mockito.times(1)).get(anyInt());
        Mockito.verify(orderDao, Mockito.times(1)).getAllByCustomerId(anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(customerDao);
        Mockito.verifyNoMoreInteractions(orderDao);

        Assertions.assertEquals(actualCustomer.getId(), CUSTOMER_ID);
        Assertions.assertEquals(actualCustomer.getName(), CUSTOMER_NAME);
        Assertions.assertEquals(actualCustomer.getOrders().size(), 1);
        Assertions.assertEquals(actualCustomer.getOrders().get(0).getName(), ORDER_NAME);
    }
}

