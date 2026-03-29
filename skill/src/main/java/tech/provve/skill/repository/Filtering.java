package tech.provve.skill.repository;

import org.jooq.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Filtering {

    /**
     * @return map of functions transforming user's filter input to jooq`s Condition for a specific field of a table.
     */
    protected abstract Map<String, Function<tech.provve.api.server.generated.dto.Condition, Condition>> fieldConditionMappers();

    protected List<Condition> jooqConditions(List<tech.provve.api.server.generated.dto.Condition> conditions) {
        var result = new ArrayList<Condition>(conditions.size());
        conditions.stream()
                  .map(this::jooqCondition)
                  .forEach(result::add);
        return result;
    }

    private Condition jooqCondition(tech.provve.api.server.generated.dto.Condition condition) {
        Function<tech.provve.api.server.generated.dto.Condition, Condition> builder = fieldConditionMappers().get(condition.getField());
        if (builder == null) {
            throw new IllegalArgumentException(
                    "Unknown field: " + condition.getField() +
                    ". Supported fields: " + fieldConditionMappers().keySet()
            );
        }
        return builder.apply(condition);
    }

}
