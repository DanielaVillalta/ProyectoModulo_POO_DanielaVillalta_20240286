package DanielaVillalta_20240286.DanielaVillalta_20240286.Exceptions;

import lombok.Getter;

public class ExceptionDuplicatedData extends RuntimeException {
    @Getter
    private String campoDuplicado;
    public ExceptionDuplicatedData(String message) {
        super(message);
        this.campoDuplicado = getCampoDuplicado();
    }
}
