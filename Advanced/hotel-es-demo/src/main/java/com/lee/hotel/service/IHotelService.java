package com.lee.hotel.service;

import com.lee.hotel.pojo.Hotel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.hotel.pojo.PageResult;
import com.lee.hotel.pojo.RequestParams;

import java.util.List;
import java.util.Map;

public interface IHotelService extends IService<Hotel> {

    PageResult search(RequestParams params);

    Map<String, List<String>> filters(RequestParams params);

    List<String> getSuggestions(String prefix);

    void insertById(Long id);

    void deleteById(Long id);
}
