package services;

import dao.OrderDAO;
import dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class OrderServiceTest {

    private OrderDAO orderDao;

    private OrderService orderService;

    private final int CUSTOMER_ID = 777;
    private final int ORDER_ID = 50013;
    private final OrderDto ORDER_DTO = new OrderDto(null, "laptop", 2, null, null);

    @BeforeEach
    public void setUp() {
        initService();
    }

    private void initService() {
        orderService = new OrderService();

        orderDao = Mockito.mock(OrderDAO.class);
        ReflectionTestUtils.setField(orderService, "orderDAO", orderDao);
    }

    @Test
    public void givenCustomerId_whenGetAllByCustomerId_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод getAllByCustomerId().
        List<OrderDto> actualOrders = orderService.getAllByCustomerId(CUSTOMER_ID);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(orderDao, Mockito.times(1)).getAllByCustomerId(anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void givenCustomerIdAndCustomerDto_whenCreateOrder_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод createOrder().
        orderService.createOrder(CUSTOMER_ID, ORDER_DTO);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(orderDao, Mockito.times(1)).createOrder(any(), anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void givenOrderId_whenFinishOrder_thenReturnSuccess() throws SQLException {
        // Вызываем тестируемый модуль - метод finishOrder().
        orderService.finishOrder(ORDER_ID);

        // Проверяем, что было только один вызов дао.
        Mockito.verify(orderDao, Mockito.times(1)).finishOrder(anyInt());

        // Проверяем, что с моками не было произведено ни одной операции более.
        Mockito.verifyNoMoreInteractions(orderDao);
    }
}




