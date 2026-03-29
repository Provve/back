package tech.provve.skill.repository;

import org.jooq.Condition;
import tech.provve.api.server.generated.dto.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class Filtering {

    /**
     * @return map of functions transforming user's filter input to jooq`s Condition for a specific field of a table.
     */
    protected abstract Map<String, Function<tech.provve.api.server.generated.dto.Condition, Condition>> fieldConditionMappers();

    protected List<Condition> jooqConditions(Filter filter) {
        if (Objects.isNull(filter)) {
            return new ArrayList<>();
        }

        var result = new ArrayList<Condition>(filter.getConditions()
                                                    .size());
        filter.getConditions()
              .stream()
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
