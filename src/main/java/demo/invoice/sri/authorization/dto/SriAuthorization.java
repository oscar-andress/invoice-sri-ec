package demo.invoice.sri.authorization.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SriAuthorization {
    protected String status;
    protected String authorizationNumber;
    protected LocalDate authorizationDate;
    protected String enviroment;
    protected String receipt;
    protected SriAuthorizationMessages messages;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SriAuthorizationMessages{
        protected List<SriAuthorizationMessage> message;
    }
}
