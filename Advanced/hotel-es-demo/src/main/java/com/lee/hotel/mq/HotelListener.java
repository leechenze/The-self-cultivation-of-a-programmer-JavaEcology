package com.lee.hotel.mq;

import com.lee.hotel.constants.MqConstants;
import com.lee.hotel.service.IHotelService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelListener {

    @Autowired
    private IHotelService hotelService;

    /**
     * 监听酒店新增或修改的业务
     * @param id 酒店ID
     */
    @RabbitListener(queues = MqConstants.HOTEL_INSERT_QUEUE)
    public void listenHotelInsertOrUpdate(Long id) {
        hotelService.insertById(id);
    }

    /**
     * 监听酒店删除的业务
     * @param id 酒店ID
     */
    @RabbitListener(queues = MqConstants.HOTEL_DELETE_QUEUE)
    public void listenHotelDelete(Long id) {
        hotelService.deleteById(id);
    }


}
