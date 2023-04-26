package com.lee.hotel.controller;


import com.lee.hotel.pojo.PageResult;
import com.lee.hotel.pojo.RequestParams;
import com.lee.hotel.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams params) {
        System.out.println(hotelService.search(params));
        return hotelService.search(params);
    }
}
