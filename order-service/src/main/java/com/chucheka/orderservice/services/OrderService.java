package com.chucheka.orderservice.services;

import com.chucheka.orderservice.dto.CreditDebitWalletDto;
import com.chucheka.orderservice.dto.CustomerOrder;
import com.chucheka.orderservice.dto.GenericResponse;
import com.chucheka.orderservice.dto.OrderItem;
import com.chucheka.orderservice.entities.Item;
import com.chucheka.orderservice.entities.Order;
import com.chucheka.orderservice.entities.OrderLine;
import com.chucheka.orderservice.repositories.ItemRepository;
import com.chucheka.orderservice.repositories.OrderRepository;
import com.chucheka.orderservice.utils.AppUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final  PaymentService paymentService;

    public GenericResponse<Order> createOrder(@RequestBody @Valid CustomerOrder customerOrder) {

        /*

         * Calculate the total cost
         * Check if the customer has amount in wallet
         * Make payment
         * Save order
         * Send request to restaurant to process
         * Send notification to customer

         */

        Order order = new Order();

        Set<OrderLine> orderLines = new HashSet<>();

        order.setCustomerDetails(customerOrder.customerDetails());
        order.setOrderCode(AppUtils.generateUniqueCode());

        double totalPrice = 0.00;

        for (OrderItem orderItem : customerOrder.orderItems()) {

            Item item = itemRepository.findByItemCode(orderItem.getItemCode());

            OrderLine orderLine = new OrderLine();

            if (Objects.nonNull(item)) {
                orderLine.setPrice(item.getPrice());
                orderLine.setQuantity(orderItem.getQuantity());
                orderLine.setItemCode(orderItem.getItemCode());

                orderLines.add(orderLine);

                totalPrice += orderItem.getQuantity() * item.getPrice();
            }


        }
        order.setAmount(totalPrice);
        order.setOrderLines(orderLines);

      CreditDebitWalletDto creditDebitWalletDto = new CreditDebitWalletDto("0075124021",totalPrice,"Payment for orders");

        ResponseEntity<GenericResponse> res =  paymentService.makePayment(creditDebitWalletDto);

        if (res==null)
            return new GenericResponse<>(false, "Request failed try again later", null);


        if (!res.getBody().getSuccess())
            return new GenericResponse<>(false, "Request failed try again later", null);

        order = orderRepository.save(order);

        return new GenericResponse<>(true, "order created successfully", order);
    }

    public void deleteOrder(String orderCode) {

        Order order = orderRepository.findByOrderCode(orderCode);

        if (Objects.nonNull(order))
            orderRepository.delete(order);


    }
}
