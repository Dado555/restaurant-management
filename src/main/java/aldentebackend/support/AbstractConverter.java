package aldentebackend.support;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Getter
public abstract class AbstractConverter<TFrom, TTo> implements IConverter<TFrom, TTo> {

    private ModelMapper modelMapper;

    @Autowired
    protected final void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Collection<TTo> convert(Collection<TFrom> source) {
        return source.stream().map(this::convert).collect(Collectors.toList());
    }
}
