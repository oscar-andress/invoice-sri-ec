package demo.invoice.sri.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SriAuthorizationMessage {
    protected String identifyer;
    protected String message;
    protected String additionalInformtion;
    protected String type;
}
