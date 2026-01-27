package demo.invoice.sri.authorization.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class SriResponseReceipt {
    protected String accessKeyConsulted;
    protected String numberReceipts;
    protected Authorizations authorizations;

    @Setter
    @NoArgsConstructor
    public static class Authorizations{
        protected List<SriAuthorization> authorization;

        public List<SriAuthorization> getAutorizathion() {
            if (authorization == null) {
                authorization = new ArrayList<>();
            }
            return this.authorization;
        }
    }
}
