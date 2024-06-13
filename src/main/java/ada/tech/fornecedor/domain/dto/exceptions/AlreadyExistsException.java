package ada.tech.fornecedor.domain.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    private final Class clazz;
    private final String id;
}
