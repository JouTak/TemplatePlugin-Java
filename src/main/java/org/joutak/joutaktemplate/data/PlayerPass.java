package org.joutak.joutaktemplate.data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerPass {
    private String nickname;
    private String lastPaymentDate;
    private String passValidUntil;
}
