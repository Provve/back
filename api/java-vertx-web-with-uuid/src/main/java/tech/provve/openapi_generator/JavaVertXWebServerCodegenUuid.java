package tech.provve.openapi_generator;

import io.swagger.v3.oas.models.media.Schema;
import org.openapitools.codegen.languages.JavaVertXWebServerCodegen;

/**
 * Generator with schema for UUID type mapping.
 */
public class JavaVertXWebServerCodegenUuid extends JavaVertXWebServerCodegen {

    @Override
    public String getName() {
        return "java-vertx-web-uuid";
    }

    @Override
    public String getSchemaType(Schema schema) {
        if ("string".equals(schema.getType()) && "uuid".equals(schema.getFormat())) {
            return "java.util.UUID";
        }
        return super.getSchemaType(schema);
    }

}
