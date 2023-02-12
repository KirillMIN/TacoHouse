package com.example.tacohouse.tacos.Repositories.JdbcTemplate;

import com.example.tacohouse.tacos.entities.Ingredient;
import com.example.tacohouse.tacos.entities.Taco;
import com.example.tacohouse.tacos.entities.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;
@Slf4j
@Repository
public class JdbcOrderRepository implements OrderRepository{
    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    public JdbcOrderRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Override
    public TacoOrder save(TacoOrder order) {
        Date date = new Date();
        order.setPlacedAt(date);
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                        "insert into Taco_Order "
                                + "(delivery_name, delivery_street, delivery_city, "
                                + "delivery_state, delivery_zip, cc_number, "
                                + "cc_expiration, cc_cvv, placed_at) "
                                + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long orderId;
        if (Objects.requireNonNull(keyHolder.getKeys()).size() > 1) {
            orderId = (Long)keyHolder.getKeys().get("id");
        } else {
            orderId= Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        order.setId(orderId);

        for(Taco taco : order.getTacos()){
            taco.setCreatedAt(date);
            saveTaco(order, taco, taco.getName(), order.getId(),  taco.getCreatedAt());
        }

        return order;


    }
    private void saveTaco(TacoOrder order, Taco taco, String name, Long id, Date createdAt) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, order_id, created_at) values (?, ?, ?)",
                Types.VARCHAR, Types.BIGINT, Types.DATE);

        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(taco.getName(), order.getId(), taco.getCreatedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long tacoId;
        if (Objects.requireNonNull(keyHolder.getKeys()).size() > 1) {
            tacoId = (Long)keyHolder.getKeys().get("id");
        } else {
            tacoId= Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        taco.setId(tacoId);
        HashMap<String, Long> params = new HashMap<String, Long>();
        for(Ingredient ingredient : taco.getIngredients()){
            params.put("taco_id",taco.getId());
            params.put("ingredient_id", ingredient.getId());
            log.info("ingredient_id: {}", ingredient.getId());
            namedParameterJdbcTemplate.update("INSERT INTO ingredient_taco (ingredient_id, taco_id) " +
                    "values (:ingredient_id, :taco_id)", params);
            params.clear();



        }

    }


}
