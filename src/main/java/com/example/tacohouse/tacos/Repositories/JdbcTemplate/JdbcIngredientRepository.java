package com.example.tacohouse.tacos.Repositories.JdbcTemplate;


import com.example.tacohouse.tacos.entities.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM Ingredient", (rs, rowNum) -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getLong("id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setType(Ingredient.Type.valueOf(rs.getString("type")));
            return ingredient;
        });
    }

    @Override
    public Ingredient findById(Long id) {
        String sql = "select id, name, type from Ingredient where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getLong("id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setType(Ingredient.Type.valueOf(rs.getString("type")));
            return ingredient;});
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        String sql = "INSERT INTO ingredient VALUES(?, ?, ?);";

        jdbcTemplate.update(sql, ingredient.getId(), ingredient.getName(), ingredient.getType());
        return findById(ingredient.getId());
    }

}
