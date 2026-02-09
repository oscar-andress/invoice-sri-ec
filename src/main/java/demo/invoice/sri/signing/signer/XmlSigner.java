package demo.invoice.sri.signing.signer;

public interface XmlSigner {
    String sign(String xml, String path, String passwordl);
}
