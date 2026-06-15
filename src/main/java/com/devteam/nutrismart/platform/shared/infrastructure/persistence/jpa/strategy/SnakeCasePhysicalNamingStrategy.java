package com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Estrategia de nomenclatura física de Hibernate que convierte los nombres lógicos
 * de tablas y columnas a formato {@code snake_case} y pluraliza los nombres de tabla.
 * <p>
 * Por ejemplo, la entidad {@code UserJpaEntity} se mapea a la tabla {@code users},
 * y el campo {@code firstName} se mapea a la columna {@code first_name}.
 * </p>
 * <p>
 * Registrar esta estrategia mediante la propiedad
 * {@code spring.jpa.hibernate.naming.physical-strategy} en el archivo de configuración.
 * </p>
 */
public class SnakeCasePhysicalNamingStrategy implements PhysicalNamingStrategy {

    /**
     * Convierte el nombre lógico de la tabla a {@code snake_case} y lo pluraliza.
     *
     * @param logicalName nombre lógico de la tabla
     * @param context     contexto JDBC del entorno
     * @return identificador físico en {@code snake_case} plural
     */
    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
        String snake = toSnakeCase(logicalName.getText());
        return Identifier.toIdentifier(pluralize(snake));
    }

    /**
     * Convierte el nombre lógico de la columna a {@code snake_case}.
     *
     * @param logicalName nombre lógico de la columna
     * @param context     contexto JDBC del entorno
     * @return identificador físico en {@code snake_case}
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment context) {
        return Identifier.toIdentifier(toSnakeCase(logicalName.getText()));
    }

    /**
     * Devuelve el nombre del catálogo sin transformación.
     *
     * @param logicalName nombre lógico del catálogo
     * @param context     contexto JDBC del entorno
     * @return el mismo identificador sin cambios
     */
    @Override
    public Identifier toPhysicalCatalogName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }

    /**
     * Devuelve el nombre del esquema sin transformación.
     *
     * @param logicalName nombre lógico del esquema
     * @param context     contexto JDBC del entorno
     * @return el mismo identificador sin cambios
     */
    @Override
    public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }

    /**
     * Devuelve el nombre de la secuencia sin transformación.
     *
     * @param logicalName nombre lógico de la secuencia
     * @param context     contexto JDBC del entorno
     * @return el mismo identificador sin cambios
     */
    @Override
    public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }

    /**
     * Convierte un nombre en {@code camelCase} o {@code PascalCase} a {@code snake_case}.
     *
     * @param name nombre a convertir
     * @return cadena en {@code snake_case} en minúsculas
     */
    private String toSnakeCase(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Pluraliza un nombre en {@code snake_case} siguiendo reglas básicas del inglés.
     *
     * @param snake nombre en {@code snake_case} singular
     * @return nombre pluralizado
     */
    private String pluralize(String snake) {
        if (snake.endsWith("s")) {
            return snake;
        }
        if (snake.endsWith("y") && snake.length() > 1 && !isVowel(snake.charAt(snake.length() - 2))) {
            return snake.substring(0, snake.length() - 1) + "ies";
        }
        return snake + "s";
    }

    /**
     * Indica si el carácter dado es una vocal (a, e, i, o, u).
     *
     * @param c carácter a evaluar
     * @return {@code true} si es vocal
     */
    private boolean isVowel(char c) {
        return "aeiou".indexOf(c) >= 0;
    }
}
