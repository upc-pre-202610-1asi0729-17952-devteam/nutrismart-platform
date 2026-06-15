package com.devteam.nutrismart.platform.shared.application.result;

import java.util.Optional;
import java.util.function.Function;

/**
 * Tipo algebraico que representa el resultado de una operación de aplicación,
 * pudiendo ser un {@link Success} con un valor de tipo {@code T} o un
 * {@link Failure} con un error de tipo {@code E}.
 * <p>
 * Permite encadenar transformaciones sin lanzar excepciones, siguiendo el
 * patrón Railway-Oriented Programming.
 * </p>
 *
 * @param <T> tipo del valor en caso de éxito
 * @param <E> tipo del error en caso de fallo
 */
public sealed interface Result<T, E> permits Result.Success, Result.Failure {

    /**
     * Variante de resultado exitoso que envuelve un valor de tipo {@code T}.
     *
     * @param <T> tipo del valor
     * @param <E> tipo del error (no utilizado en esta variante)
     * @param value el valor resultante de la operación exitosa
     */
    record Success<T, E>(T value) implements Result<T, E> {}

    /**
     * Variante de resultado fallido que envuelve un error de tipo {@code E}.
     *
     * @param <T> tipo del valor (no utilizado en esta variante)
     * @param <E> tipo del error
     * @param error el error que causó el fallo
     */
    record Failure<T, E>(E error) implements Result<T, E> {}

    /**
     * Crea un resultado exitoso con el valor proporcionado.
     *
     * @param <T>   tipo del valor
     * @param <E>   tipo del error
     * @param value el valor a envolver
     * @return un {@link Result} en estado de éxito
     */
    static <T, E> Result<T, E> success(T value) { return new Success<>(value); }

    /**
     * Crea un resultado fallido con el error proporcionado.
     *
     * @param <T>   tipo del valor
     * @param <E>   tipo del error
     * @param error el error a envolver
     * @return un {@link Result} en estado de fallo
     */
    static <T, E> Result<T, E> failure(E error)  { return new Failure<>(error); }

    /**
     * Indica si este resultado es exitoso.
     *
     * @return {@code true} si es {@link Success}
     */
    default boolean isSuccess() { return this instanceof Success; }

    /**
     * Indica si este resultado es fallido.
     *
     * @return {@code true} si es {@link Failure}
     */
    default boolean isFailure() { return this instanceof Failure; }

    /**
     * Convierte el resultado a un {@link Optional}: presente si es éxito, vacío si es fallo.
     *
     * @return optional con el valor en caso de éxito, vacío en caso de fallo
     */
    default Optional<T> toOptional() {
        return switch (this) {
            case Success<T, E> s -> Optional.of(s.value());
            case Failure<T, E> f -> Optional.empty();
        };
    }

    /**
     * Devuelve el valor en caso de éxito, o el valor por defecto en caso de fallo.
     *
     * @param defaultValue valor devuelto cuando el resultado es un fallo
     * @return el valor de éxito o {@code defaultValue}
     */
    default T getOrElse(T defaultValue) {
        return switch (this) {
            case Success<T, E> s -> s.value();
            case Failure<T, E> f -> defaultValue;
        };
    }

    /**
     * Transforma el tipo de error sin alterar el valor en caso de éxito.
     *
     * @param <E2> nuevo tipo de error
     * @param f    función de transformación del error
     * @return resultado con el error mapeado
     */
    default <E2> Result<T, E2> mapError(Function<E, E2> f) {
        return switch (this) {
            case Success<T, E> s -> Result.success(s.value());
            case Failure<T, E> failure -> Result.failure(f.apply(failure.error()));
        };
    }

    /**
     * Encadena una operación que también puede fallar, propagando el fallo si ya existe.
     *
     * @param <T2> tipo del nuevo valor
     * @param f    función que produce un nuevo {@link Result}
     * @return el resultado de aplicar {@code f} al valor, o el fallo original
     */
    default <T2> Result<T2, E> flatMap(Function<T, Result<T2, E>> f) {
        return switch (this) {
            case Success<T, E> s -> f.apply(s.value());
            case Failure<T, E> failure -> Result.failure(failure.error());
        };
    }

    /**
     * Transforma el valor en caso de éxito sin alterar el tipo de error.
     *
     * @param <T2> nuevo tipo del valor
     * @param f    función de transformación del valor
     * @return resultado con el valor mapeado
     */
    default <T2> Result<T2, E> map(Function<T, T2> f) {
        return switch (this) {
            case Success<T, E> s -> Result.success(f.apply(s.value()));
            case Failure<T, E> failure -> Result.failure(failure.error());
        };
    }

    /**
     * Intenta recuperar de un fallo aplicando una función que produce un nuevo resultado.
     *
     * @param f función de recuperación que recibe el error
     * @return el resultado original si es éxito, o el resultado de la recuperación
     */
    default Result<T, E> recover(Function<E, Result<T, E>> f) {
        return switch (this) {
            case Success<T, E> s -> this;
            case Failure<T, E> failure -> f.apply(failure.error());
        };
    }

    /**
     * Dobla el resultado en un único valor aplicando una de las dos funciones según el caso.
     *
     * @param <R>       tipo del resultado final
     * @param onSuccess función aplicada al valor en caso de éxito
     * @param onFailure función aplicada al error en caso de fallo
     * @return el resultado de aplicar la función correspondiente
     */
    default <R> R fold(Function<T, R> onSuccess, Function<E, R> onFailure) {
        return switch (this) {
            case Success<T, E> s -> onSuccess.apply(s.value());
            case Failure<T, E> f -> onFailure.apply(f.error());
        };
    }
}
