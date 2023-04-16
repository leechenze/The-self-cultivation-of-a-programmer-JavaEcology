package com.lee.hotel.service.impl;

import com.lee.hotel.mapper.HotelMapper;
import com.lee.hotel.pojo.Hotel;
import com.lee.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}
