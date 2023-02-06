package aldentebackend.support;

import org.springframework.core.convert.converter.Converter;

import java.util.Collection;

public interface IConverter<TFrom, TTo> extends Converter<TFrom, TTo> {

    Collection<TTo> convert(Collection<TFrom> source);
}
