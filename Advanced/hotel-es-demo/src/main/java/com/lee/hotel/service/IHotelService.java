package com.lee.hotel.service;

import com.lee.hotel.pojo.Hotel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.hotel.pojo.PageResult;
import com.lee.hotel.pojo.RequestParams;

public interface IHotelService extends IService<Hotel> {

    PageResult search(RequestParams params);
}
