package com.example.tacohouse.tacos.Repositories.JdbcTemplate;

import com.example.tacohouse.tacos.entities.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
